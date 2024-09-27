package play.board1.post.dto;

import lombok.Getter;
import lombok.Setter;
import play.board1.post.entity.PostComment;

import java.time.LocalDateTime;

@Getter @Setter
public class PostCmtInsertDto {

    private Long id;
    private String content;
    private Long postId;
    private LocalDateTime regDt;

    public PostCmtInsertDto(String content, Long postId, LocalDateTime regDt) {
        this.content = content;
        this.postId = postId;
        this.regDt = regDt;
    }

    public PostCmtInsertDto(PostComment cmt) {
        this.content = cmt.getContent();
        this.id = cmt.getId();
        this.regDt = cmt.getRegDt();
    }

}
