package play.board1.board.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class SignUpMemberDto {
    private String userId;
    private String username;
    private String password;
    private String phoneNumber;

    private LocalDateTime joinDate;

    public SignUpMemberDto() {
    }

    public SignUpMemberDto(String userId, String username, String password, String phoneNumber, LocalDateTime joinDate) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.joinDate = joinDate;
    }
}
