package play.board1.board.entity;

import jakarta.persistence.*;
import lombok.Getter;
import play.board1.board.dto.BoardDto;
import play.board1.board.dto.MemberDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@SequenceGenerator(
        name="board_seq_gen",
        sequenceName="board_seq",
        initialValue = 1,
        allocationSize = 1
)
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
    generator = "board_seq_gen")
    @Column(name = "board_id")
    private Long id;
    private String subject;
    private String content;
    private int recommend;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    private LocalDateTime regDt;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoardComment> comments = new ArrayList<>();

    public Board(String subject, String content, LocalDateTime regDt) {
        this.subject = subject;
        this.content = content;
        this.regDt = regDt;
    }
    public Board(Long id,String subject, String content, LocalDateTime regDt) {
        this.id = id;
        this.subject = subject;
        this.content = content;
        this.regDt = regDt;
    }
    public Board() {
    }

    /* 게시글을 수정한다. */
    public void updateAtcl(BoardDto dto) {
        subject = dto.getSubject();
        content = dto.getContent();

    }


}
