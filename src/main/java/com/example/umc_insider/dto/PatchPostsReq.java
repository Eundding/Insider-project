package com.example.umc_insider.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter // 해당 클래스에 대한 접근자 생성
@Setter // 해당 클래스에 대한 설정자 생성
@AllArgsConstructor
public class PatchPostsReq {
    private Long id; // post id(AI)
    private String title;
    private String content;
}

// Posts




//service
//@Transactional
//public void modifyPosts(PatchPostsReq patchPostsReq) {
//    Posts posts = postsRepository.getReferenceById(patchPostsReq.getId());
//    posts.updateNickName(patchMemberReq.getNickname());
//}


//controller
//@PatchMapping("/update")
//public BaseResponse<String> modifyPosts(@RequestParam Long id) {
//
        //같다면 유저네임 변경
//        PatchPostsReq patchPostsReq = new PatchPostsReq(posts.getId());
//        postsService.modifyPosts(patchPostsReq);
//        String result = "회원정보가 수정되었습니다.";
//        return new BaseResponse<>(result);

//    }
//}