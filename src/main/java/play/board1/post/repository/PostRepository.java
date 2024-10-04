package play.board1.post.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import play.board1.post.entity.Post;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long>, PostCustomRepository {
    @Query(value = "select p from Post p left join fetch p.likes")
    Page<Post> findByPage(Pageable pageable);
    @Modifying
    @Query(value = "INSERT INTO post_like (post_like_id, post_id, member_id) VALUES (post_like_seq.NEXTVAL, :postId, :memberId)", nativeQuery = true)
    int addPostLike(@Param("postId") Long postId, @Param("memberId") Long memberId);
    @Query(value = "select p from Post p left join fetch p.likes pl where p.id = :postId")
    Optional<Post> findByPostId(Long postId);

    @Modifying
    @Query(value = "delete from post_like where  post_id=:postId and member_id=:memberId", nativeQuery = true)
    int deletePostLike(@Param("postId") Long postId, @Param("memberId") Long memberId);
}
