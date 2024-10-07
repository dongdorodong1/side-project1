package play.board1.post.dto;

import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import play.board1.common.config.Chrono;
import play.board1.common.dto.MemberDto;
import play.board1.post.entity.Member;
import play.board1.post.entity.PostComment;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
public class PostCommentDto {

    private Long id;
    private String content;
    private Long postId;
    private String regDt;
    private MemberDto memberDto;
    private HttpSession session;

    public PostCommentDto(PostComment cmt) {
        this.content = cmt.getContent();
        this.id = cmt.getId();
        this.regDt = Chrono.timesAgo(cmt.getRegDt());
        Member member = cmt.getMember();
        MemberDto memberDto = new MemberDto();
        memberDto.setUsername(member.getUsername());
        this.memberDto = memberDto;
    }

}
