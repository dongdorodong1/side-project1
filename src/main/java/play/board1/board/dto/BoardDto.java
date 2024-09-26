package play.board1.board.dto;

import lombok.Getter;
import lombok.Setter;
import play.board1.board.entity.Board;
import play.board1.common.dto.MemberDto;

import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.util.stream.Collectors.toList;


@Getter@Setter
public class BoardDto {

    private Long id;
    private String content;
    private String subject;
    private int recommend;
    private MemberDto member;
    private List<BoardCmtInsertDto> comments;
    private String regDt;

    public BoardDto(String subject,String content, MemberDto member) {
        this.subject = subject;
        this.content = content;
        this.member = member;

    }
    public BoardDto(Board board) {
        this.id = board.getId();
        this.subject = board.getSubject();
        this.content = board.getContent();
        this.recommend = board.getRecommend();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        this.regDt = board.getRegDt().format(formatter);
    }
    public BoardDto() {
    }
}
