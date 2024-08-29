package play.board1.board.dto;

import lombok.Getter;
import lombok.Setter;
import play.board1.board.entity.Board;
import play.board1.board.entity.BoardComment;

import java.time.LocalDateTime;

@Getter @Setter
public class BoardCmtInsertDto {

    private Long id;
    private String content;
    private Long boardId;
    private LocalDateTime regDt;

    public BoardCmtInsertDto(String content, Long boardId, LocalDateTime regDt) {
        this.content = content;
        this.boardId = boardId;
        this.regDt = regDt;
    }

    public BoardCmtInsertDto(BoardComment cmt) {
        this.content = cmt.getContent();
        this.id = cmt.getId();
        this.regDt = cmt.getRegDt();
    }

}
