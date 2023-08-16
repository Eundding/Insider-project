package com.example.umc_insider.service;

import com.example.umc_insider.config.BaseException;
import com.example.umc_insider.domain.Address;
import com.example.umc_insider.domain.Users;
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


import java.util.ArrayList;
import java.util.List;

import static com.example.umc_insider.config.BaseResponseStatus.*;

@RequiredArgsConstructor
@Service
public class UsersService {
    private UserRepository userRepository;
    private AddressRepository addressRepository;
    private final JwtService jwtService;

    @Autowired
    public UsersService(UserRepository userRepository, JwtService jwtService, AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.addressRepository = addressRepository;
    }

    public PostUserRes createUser(PostUserReq postUserReq) throws BaseException{
//        Users users = new Users();
//        Address newAddress = new Address();
        Users newUser = postUserReq.createUserWithAddress();
        Address newAddress = newUser.getAddress();

        Users savedUser = saveUserWithAddress(newUser, newAddress);
//        users.createUser(postUserReq.getUserId(), postUserReq.getNickname(), postUserReq.getEmail(), postUserReq.getPw(), postUserReq.getZipCode() );
//
//        userRepository.save(users);
        return new PostUserRes(savedUser.getId(), savedUser.getNickname());
    }

    public List<GetUserRes> getAllUsers() {
        List<Users> users = userRepository.findAll();
        return mapToUserResponseList(users);
    }

    public List<GetUserRes> getReferenceById(long id) throws BaseException {
        List<Users> users = userRepository.findAllById(id);
        return mapToUserResponseList(users);
    }



    private List<GetUserRes> mapToUserResponseList(List<Users> users) {
        List<GetUserRes> userResponses = new ArrayList<>();
        for (Users user : users) {
            userResponses.add(new GetUserRes(user.getId(), user.getUser_id(), user.getNickname(), user.getEmail(), user.getPw(), user.getAddress().getZipCode()));
        }
        return userResponses;
    }


    /**
     * 유저 로그인
     */
    public PostLoginRes logIn(PostLoginReq postLoginReq) throws BaseException {
        Users users = userRepository.findUserByUserId(postLoginReq.getUserId());
        String encryptPw;
        try{
            encryptPw = new SHA256().encrypt(postLoginReq.getPw());
        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }


        String originalEncryptPw = new SHA256().encrypt(users.getPw());
        if(originalEncryptPw.equals(encryptPw)){
            String jwt = jwtService.createJwt(users.getId());
            return new PostLoginRes(users.getId(), jwt);
        } else{
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


}
