package play.board1.post.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@SequenceGenerator(
        name = "postLike_seq_gen",
        sequenceName = "postLike_seq",
        initialValue = 1
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostLike {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
    generator = "postLike_seq_gen")
    @Column(name = "postLike_id")
    private Long id;

    private boolean likeCheck; //중복확인
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public PostLike(Member member, Post post) {
        this.member = member;
        this.post = post;
    }
}
