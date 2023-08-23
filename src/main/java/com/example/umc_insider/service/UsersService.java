package com.example.umc_insider.service;

import com.example.umc_insider.config.BaseException;
import com.example.umc_insider.config.BaseResponseStatus;
import com.example.umc_insider.domain.Address;
import com.example.umc_insider.domain.Users;
import com.example.umc_insider.dto.request.*;
import com.example.umc_insider.dto.response.GetUserByIdRes;
import com.example.umc_insider.dto.request.PostLoginReq;
import com.example.umc_insider.dto.request.PostUserReq;
import com.example.umc_insider.dto.response.GetUserRes;
import com.example.umc_insider.dto.response.PostLoginRes;
import com.example.umc_insider.dto.response.PostUserRes;
import com.example.umc_insider.repository.AddressRepository;
import com.example.umc_insider.utils.JwtService;
import com.example.umc_insider.utils.SHA256;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.umc_insider.repository.UserRepository;
import org.springframework.web.multipart.MultipartFile;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

import static com.example.umc_insider.config.BaseResponseStatus.*;

@RequiredArgsConstructor
@Service
public class UsersService {
    private UserRepository userRepository;
    private AddressRepository addressRepository;
    private final JwtService jwtService;
    private final S3Service s3Service;

    @Autowired
    public UsersService(UserRepository userRepository, JwtService jwtService, AddressRepository addressRepository, S3Service s3Service) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.addressRepository = addressRepository;
        this.s3Service = s3Service;
    }

    public PostUserRes createUser(PostUserReq postUserReq) throws BaseException {
        Users newUser = postUserReq.createUserWithAddress();
        Address newAddress = newUser.getAddress();

        Users savedUser = saveUserWithAddress(newUser, newAddress);
        return new PostUserRes(savedUser.getId(), savedUser.getNickname());
    }

    public List<GetUserRes> getAllUsers() {
        List<Users> users = userRepository.findAll();
        return mapToUserResponseList(users);
    }


    private List<GetUserRes> mapToUserResponseList(List<Users> users) {
        List<GetUserRes> userResponses = new ArrayList<>();
        for (Users user : users) {
            userResponses.add(new GetUserRes(user.getId(), user.getUser_id(), user.getNickname(), user.getEmail(), user.getPw(), user.getAddress()));
        }
        return userResponses;
    }

    // 특정 유저조회
    public List<GetUserRes> getReferenceById(long id) throws BaseException {
        List<Users> users = userRepository.findAllById(id);
        return mapToUserResponseList(users);
    }


    /**
     * 유저 로그인
     */
    public PostLoginRes logIn(PostLoginReq postLoginReq) throws BaseException {
        Users users = userRepository.findUserByUserId(postLoginReq.getUserId());
        String encryptPw;
        try {
            encryptPw = new SHA256().encrypt(postLoginReq.getPw());
        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }


        String originalEncryptPw = new SHA256().encrypt(users.getPw());
        if (originalEncryptPw.equals(encryptPw)) {
            String jwt = jwtService.createJwt(users.getId());
            return new PostLoginRes(users.getId(), jwt);
        } else {
            throw new BaseException(FAILED_TO_LOGIN);
        }
    }

    // 사용자 주소를 가져오는 메소드
    public Address getAddressForUser(Long userId) {
        Users user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return user.getAddress();
    }

    @Transactional
    public Users saveUserWithAddress(Users user, Address address) {
        address.setUser(user);
        user.setAddress(address);
        addressRepository.save(address);
        return userRepository.save(user);
    }

    // 유저 프로필 이미지 등록
    public Users registerProfile(PutUserProfileReq putUserProfileReq, MultipartFile file){
        Users user = userRepository.findUsersById(putUserProfileReq.getId());
        userRepository.save(user);
        // S3에 이미지 업로드 및 URL 받기
        String imageUrl = s3Service.uploadProfileS3(file, user);

        // 이미지 URL 설정 후, 객체 업데이트
        user.setImageUrl(imageUrl);

        userRepository.save(user);
        return user;

    }

    // 유저 정보 수정
    public PostUserRes modifyUser(PutUserReq putUserReq) throws BaseException {
        // 요청 객체에서 유저 ID 값 가져오기
        Long id = putUserReq.getId();
        // 유저 ID를 기반으로 유저 정보 가져오기
        Optional<Users> optionalUser = userRepository.findById(id);
        Users userToModify;

        if (optionalUser.isPresent()) {
            userToModify = optionalUser.get();
        } else {
            throw new BaseException(BaseResponseStatus.REQUEST_ERROR);
        }

        // 요청 본문에서 수정할 필드 값들 가져오기
        String nickname = putUserReq.getNickname();
        String password = putUserReq.getPw();
        String userId = putUserReq.getUserId();
        String email = putUserReq.getEmail();
        Integer zipCode = putUserReq.getZipCode();
        String detailAddress = putUserReq.getDetailAddress();

        // 업데이트할 필드만 적용하기
        if (nickname != null) {
            userToModify.setNickname(nickname);
        }
        if (password != null) {
            userToModify.setPw(password);
        }
        if (email != null) {
            userToModify.setEmail(email);
        }
        if (userId != null) {
            userToModify.setUser_id(userId);
        }
        Address newAddress = new Address();
        if (zipCode != null && detailAddress != null) {
            newAddress.setZipCode(zipCode);
            newAddress.setDetailAddress(detailAddress);
            userToModify.setAddress(newAddress);
        }
        userRepository.save(userToModify);
        return new PostUserRes(userToModify.getId(), userToModify.getNickname());
    }

    // id로 유저 조회
    public GetUserByIdRes getUserById(Long id){
        Users user = userRepository.findUsersById(id);
        return new GetUserByIdRes(user.getNickname(), user.getUser_id(), user.getPw(), user.getEmail(), user.getAddress().getZipCode(), user.getAddress().getDetailAddress(), user.getImage_url());
    }

//    // 우편주소로 위도 경도
//    private static Map<String, String> getGeoDataByAddress(String completeAddress) {
//        try {
//            String API_KEY = "구글 API Key";
//            String surl = "https://maps.googleapis.com/maps/api/geocode/json?address="+ URLEncoder.encode(completeAddress, "UTF-8")+"&key="+API_KEY;
//            URL url = new URL(surl);
//            InputStream is = url.openConnection().getInputStream();
//
//            BufferedReader streamReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
//
//            StringBuilder responseStrBuilder = new StringBuilder();
//            String inputStr;
//            System.out.println(">>>>>>>>>> >>>>>>>>>> InputStream Start <<<<<<<<<< <<<<<<<<<<");
//            while ((inputStr = streamReader.readLine()) != null) {
//                System.out.println(">>>>>>>>>>     "+inputStr);
//                responseStrBuilder.append(inputStr);
//            }
//            System.out.println(">>>>>>>>>> >>>>>>>>>> InputStream End <<<<<<<<<< <<<<<<<<<<");
//
//            JSONObject jo = new JSONObject(responseStrBuilder.toString());
//            JSONArray results = jo.getJSONArray("results");
//            String region = null;
//            String province = null;
//            String zip = null;
//            Map<String, String> ret = new HashMap<String, String>();
//            if(results.length() > 0) {
//                JSONObject jsonObject;
//                jsonObject = results.getJSONObject(0);
//                Double lat = jsonObject.getJSONObject("geometry").getJSONObject("location").getDouble("lat");
//                Double lng = jsonObject.getJSONObject("geometry").getJSONObject("location").getDouble("lng");
//                ret.put("lat", lat.toString());
//                ret.put("lng", lng.toString());
//                System.out.println("LAT:\t\t"+lat);
//                System.out.println("LNG:\t\t"+lng);
//                JSONArray ja = jsonObject.getJSONArray("address_components");
//                for(int l=0; l<ja.length(); l++) {
//                    JSONObject curjo = ja.getJSONObject(l);
//                    String type = curjo.getJSONArray("types").getString(0);
//                    String short_name = curjo.getString("short_name");
//                    if(type.equals("postal_code")) {
//                        System.out.println("POSTAL_CODE: "+short_name);
//                        ret.put("zip", short_name);
//                    }
//                    else if(type.equals("administrative_area_level_3")) {
//                        System.out.println("CITY: "+short_name);
//                        ret.put("city", short_name);
//                    }
//                    else if(type.equals("administrative_area_level_2")) {
//                        System.out.println("PROVINCE: "+short_name);
//                        ret.put("province", short_name);
//                    }
//                    else if(type.equals("administrative_area_level_1")) {
//                        System.out.println("REGION: "+short_name);
//                        ret.put("region", short_name);
//                    }
//                }
//                return ret;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }



}




