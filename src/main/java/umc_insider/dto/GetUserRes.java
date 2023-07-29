package umc_insider.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetUserRes {
    private Long id;
    private String userId;
    private String nickname;
    private String password;
    private String email;



}
