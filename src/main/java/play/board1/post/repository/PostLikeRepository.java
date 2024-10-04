package play.board1.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import play.board1.post.entity.PostLike;

import java.util.List;

public interface PostLikeRepository extends JpaRepository<PostLike,Long> {

    @Query("SELECT COUNT(pl) FROM PostLike pl WHERE pl.post.id = :postId")
    int countLikesByPostId(Long postId);

    boolean existsByPostIdAndMemberId(Long postId, Long memberId);

}
