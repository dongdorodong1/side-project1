package play.board1.common.dto;

import lombok.Getter;
import lombok.Setter;
import play.board1.board.entity.Member;
import play.board1.common.config.CustomBCryptPasswordEncoder;

@Setter @Getter
public class SignInMemberDto {
    private String userId;
    private String password;
}
