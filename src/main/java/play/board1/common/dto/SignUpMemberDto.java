package play.board1.common.dto;

import lombok.*;
import play.board1.post.entity.Member;

import java.time.LocalDateTime;

@Getter @Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignUpMemberDto {
    private String userId;
    private String username;
    private String password;
    private String phoneNumber;

    private LocalDateTime joinDate;

    public Member toEntity() {
        return Member.builder()
                .userId(userId)
                .username(username)
                .password(password)
                .phoneNumber(phoneNumber)
                .build();
    }
}
