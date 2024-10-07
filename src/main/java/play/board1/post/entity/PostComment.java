package play.board1.post.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@SequenceGenerator(
        name="post_comment_gen",
        sequenceName="post_comment_seq",
        initialValue = 1,
        allocationSize = 1
)
@Getter
public class PostComment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "post_comment_seq")
    @Column(name = "comment_id")
    private Long id;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    /**
     * 수정일
     */
    private LocalDateTime regDt;

    /**
     * 작성자
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public PostComment(String content, Post post, Member member) {
        this.content = content;
        this.post = post;
        this.regDt = LocalDateTime.now();
        this.member = member;
    }

    public PostComment() {
    }
}
