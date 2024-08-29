package play.board1.board.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@SequenceGenerator(
        name="board_comment_gen",
        sequenceName="board_comment_seq",
        initialValue = 1,
        allocationSize = 1
)
@Getter
public class BoardComment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "board_comment_seq")
    @Column(name = "comment_id")
    private Long id;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;
    private LocalDateTime regDt;

    public BoardComment(String content, Board board) {
        this.content = content;
        this.board = board;
        this.regDt = LocalDateTime.now();
    }

    public BoardComment() {
    }
}
