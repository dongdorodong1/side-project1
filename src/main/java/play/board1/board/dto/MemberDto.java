package play.board1.board.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter@Setter
public class MemberDto {
    private Long id;
    private String username;
    private String password;
    private LocalDateTime joinDate;

    public MemberDto(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.joinDate = LocalDateTime.now();
    }
}
