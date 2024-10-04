package play.board1.post.entity;

import jakarta.persistence.*;
import lombok.*;
import play.board1.post.dto.PostDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SequenceGenerator(
        name="post_seq_gen",
        sequenceName="post_seq",
        initialValue = 1,
        allocationSize = 1
)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
    generator = "post_seq_gen")
    @Column(name = "post_id")
    private Long id;
    /**
     * 게시글 제목
     */
    private String subject;
    /**
     * 게시글 내용
     */
    private String content;
    /**
     * 추천수
     */
    private int recommend;
    /**
     * 조회수
     */
    private int viewCnt;
    /**
     * 작성자
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    private LocalDateTime regDt;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostComment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostLike> likes = new ArrayList<>();

    private Integer likeCnt;

    public Post(String subject, String content, Member member, LocalDateTime regDt) {
        this.member = member;
        this.subject = subject;
        this.content = content;
        this.regDt = regDt;
    }
    public Post(Long id, String subject, String content, LocalDateTime regDt) {
        this.id = id;
        this.subject = subject;
        this.content = content;
        this.regDt = regDt;
    }

    /**
     * 게시글을 수정한다.
     */
    public void updatePost(PostDto dto) {
        subject = dto.getSubject();
        content = dto.getContent();
    }
}
