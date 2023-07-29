package com.example.umc_insider.service;

import com.example.umc_insider.domain.User;
import com.example.umc_insider.dto.PostUserReq;
import com.example.umc_insider.dto.PostUserRes;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.umc_insider.dto.GetUserRes;
import com.example.umc_insider.repository.UserRepository;


import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public PostUserRes createUser(PostUserReq postUserReq) {
        User user = new User();
        user.createUser(postUserReq.getUserId(), postUserReq.getNickname(), postUserReq.getEmail(), postUserReq.getPw() );
        userRepository.save(user);
        return new PostUserRes(user.getId(), user.getNickname());
    }

    public List<GetUserRes> getAllUsers() {
        List<User> users = userRepository.findAll();
        return mapToUserResponseList(users);
    }

    private List<GetUserRes> mapToUserResponseList(List<User> users) {
        List<GetUserRes> userResponses = new ArrayList<>();
        for (User user : users) {
            userResponses.add(new GetUserRes(user.getId(), user.getUser_id(), user.getNickname(), user.getEmail(), user.getPw()));
        }
        return userResponses;
    }
    /**
     * 모든 회원 조회
     */
//    public List<GetUserRes> getUsers() throws BaseException {
//        try{
//            List<User> users = userRepository.findUsers(); //Member List로 받아 GetMemberRes로 바꿔줌
//            List<GetUserRes> GetUserRes = users.stream()
//                    .map(user -> new GetUserRes())
//                    .collect(Collectors.toList());
//            return GetUserRes;
//        } catch (Exception exception) {
//            throw new BaseException(DATABASE_ERROR);
//        }
//    }

//    /**
//     * 특정 닉네임 조회
//     */
//    public List<GetMemberRes> getMembersByNickname(String nickname) throws BaseException {
//        try{
//            List<Member> members = memberRepository.findMemberByNickName(nickname);
//            List<GetMemberRes> GetMemberRes = members.stream()
//                    .map(member -> new GetMemberRes(member.getId(), member.getNickName(), member.getEmail(), member.getPassword()))
//                    .collect(Collectors.toList());
//            return GetMemberRes;
//        } catch (Exception exception) {
//            throw new BaseException(DATABASE_ERROR);
//        }
//    }
//
//    public PostLoginRes login(PostLoginReq postLoginReq) throws BaseException {
//       User user = this.userRepository.findUserByUserId(postLoginReq.getUserId());
//
//        String password;
//        try {
//            password = (new AES128(Secret.USER_INFO_PASSWORD_KEY)).decrypt(user.getPassword());
//        } catch (Exception var5) {
//            throw new BaseException(BaseResponseStatus.PASSWORD_DECRYPTION_ERROR);
//        }
//
//        if (postLoginReq.getPassword().equals(password)) {
//            String jwt = this.jwtService.createJwt(user.getId());
//            return new PostLoginRes(user.getId(), jwt);
//        } else {
//            throw new BaseException(BaseResponseStatus.FAILED_TO_LOGIN);
//        }
//    }


}
