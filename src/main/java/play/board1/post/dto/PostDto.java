package play.board1.post.dto;

import lombok.Getter;
import lombok.Setter;
import play.board1.post.entity.Post;
import play.board1.common.dto.MemberDto;

import java.time.format.DateTimeFormatter;
import java.util.List;


@Getter@Setter
public class PostDto {

    private Long id;
    private String content;
    private String subject;
    private int recommend;
    private MemberDto member;
    private List<PostCmtInsertDto> comments;
    private String regDt;

    public PostDto(String subject, String content, MemberDto member) {
        this.subject = subject;
        this.content = content;
        this.member = member;

    }
    public PostDto(Post post) {
        this.id = post.getId();
        this.subject = post.getSubject();
        this.content = post.getContent();
        this.recommend = post.getRecommend();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        this.regDt = post.getRegDt().format(formatter);
    }
    public PostDto() {
    }
}
