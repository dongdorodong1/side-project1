package play.board1.post.dto;

import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.Setter;
import play.board1.common.config.Chrono;
import play.board1.post.entity.Member;
import play.board1.post.entity.Post;
import play.board1.common.dto.MemberDto;

import java.time.format.DateTimeFormatter;
import java.util.List;


@Getter@Setter
public class PostDto {

    private Long postId;
    private String content;
    private String subject;
    private int likeCnt;
    private int viewCnt;
    private MemberDto member;
    private List<PostCmtInsertDto> comments;
    private String regDt;
    private HttpSession session;
    private PostViewLogDto postViewLogDto;

    public PostDto(String subject, String content, MemberDto member) {
        this.subject = subject;
        this.content = content;
        this.member = member;

    }
    public PostDto(Post post) {
        this.postId = post.getId();
        this.subject = post.getSubject();
        this.content = post.getContent();
        this.likeCnt = post.getLikes().size();
        // TODO 이렇게 엔티티를 DTO로 바꿔서 넣는게 맞나..? Mapper 패턴으로 리팩토링
        Member member = post.getMember();
        MemberDto memberDto = new MemberDto(member.getUserId(), member.getUsername());
        this.member = memberDto;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//        this.regDt = post.getRegDt().format(formatter);
        this.regDt = Chrono.timesAgo(post.getRegDt());
        this.viewCnt = post.getViewCnt();
    }
    public PostDto() {
    }
}
