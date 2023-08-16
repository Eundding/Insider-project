package com.example.umc_insider.controller;
import com.example.umc_insider.config.BaseException;
import com.example.umc_insider.config.BaseResponse;
import com.example.umc_insider.config.BaseResponseStatus;
import com.example.umc_insider.domain.Users;
import com.example.umc_insider.dto.request.PutUserImgReq;
import com.example.umc_insider.dto.response.GetUserRes;
import com.example.umc_insider.repository.UserRepository;
import com.example.umc_insider.service.UsersService;
import com.example.umc_insider.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/mypage")
public class MyPageController {

    private final UsersService usersService;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Autowired
    public MyPageController(UsersService usersService, UserRepository userRepository, JwtService jwtService) {
        this.usersService = usersService;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    // 특정 유저정보 조회
    @GetMapping("/{id}")
    public ResponseEntity<List<GetUserRes>> findById(@PathVariable("id") long id) throws BaseException {
        List<GetUserRes> users = usersService.getAllById(id);
        return ResponseEntity.ok(users);
    }

    // 이미지 업데이트
//    @PutMapping("/imageupdate")
//    public BaseResponse<String> modifyUserImg(@RequestParam String email, @RequestParam String img_url) {
//        try {
//            Users user = userRepository.findUserByEmail(email);
//            //jwt에서 idx 추출.
//            Long userIdxByJwt = jwtService.getId();
//            //userIdx와 접근한 유저가 같은지 확인
//            if(user.getId() != userIdxByJwt){
//                return new BaseResponse<>(BaseResponseStatus.INVALID_USER_JWT);
//            }
//            //같다면 유저이미지 변경
//            PutUserImgReq putUserImgReq = new PutUserImgReq(user.getId(), img_url);
//            usersService.putUserImg(putUserImgReq);
//            String result = "이미지가 등록(변경)되었습니다";
//            return new BaseResponse<>(result);
//        }  catch (BaseException exception) {
//            return new BaseResponse<>((exception.getStatus()));
//        }
//
//    }
}
