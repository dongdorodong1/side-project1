package play.board1.post.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@SequenceGenerator(
        name="post_view_seq_gen",
        sequenceName="post_view_seq",
        initialValue = 1,
        allocationSize = 1
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PostViewLog {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_view_seq_gen")
    @Column(name = "post_view_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;
    /**
     * 조회수
     */
    private long viewCnt;
    /**
     * 최초 조회 날짜
     */
    private LocalDateTime firstViewDt ;
    /**
     * 최근 조회 날짜
     */
    private LocalDateTime lastViewDt ;

    public void updateViewLog() {
        this.viewCnt++;
        this.lastViewDt = LocalDateTime.now();
    }

    public PostViewLog(Member member, Post post, LocalDateTime firstViewDt, LocalDateTime lastViewDt) {
        this.member = member;
        this.post = post;
        this.viewCnt = 1;
        this.firstViewDt = firstViewDt;
        this.lastViewDt = lastViewDt;
    }
}
