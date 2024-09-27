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
    private LocalDateTime regDt;

    public PostComment(String content, Post post) {
        this.content = content;
        this.post = post;
        this.regDt = LocalDateTime.now();
    }

    public PostComment() {
    }
}
