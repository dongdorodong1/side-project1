package play.board1.board.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@SequenceGenerator(
        name = "member_seq_gen",
        sequenceName = "member_seq",
        initialValue = 1
)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "member_seq_gen")
    @Column(name = "member_id")
    private Long id;

    private String userId;
    private String password;
    private String username;
    private String phoneNumber;
    private LocalDateTime joinDate;
}
