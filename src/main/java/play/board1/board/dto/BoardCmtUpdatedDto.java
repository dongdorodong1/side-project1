package play.board1.board.dto;

import play.board1.board.entity.Board;

public class BoardCmtUpdatedDto {


    private Long id;
    private String content;
    private Board board;

    public BoardCmtUpdatedDto(String content, Board board) {
        this.content = content;
        this.board = board;
    }
}
