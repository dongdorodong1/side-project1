package play.board1.post.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import play.board1.post.entity.Post;

import java.util.Optional;

public interface PostJpaRepository extends JpaRepository<Post,Long> {

    @Query(value = "select m from Post m")
    Page<Post> findByPage(Pageable pageable);
    @Modifying
    @Query(value = "INSERT INTO Likes (likes_id, post_id, member_id) VALUES (likes_seq.NEXTVAL, :postId, :memberId)", nativeQuery = true)
    int updateRecommend(@Param("postId") Long postId, @Param("memberId") Long memberId);
}
