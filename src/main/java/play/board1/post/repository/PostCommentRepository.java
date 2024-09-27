package play.board1.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import play.board1.post.entity.PostComment;

import java.util.List;

public interface PostCommentRepository extends JpaRepository<PostComment,Long> {

    @Query("select c from PostComment c where c.post.id = :postId order by c.id desc")
    List<PostComment> findCommentListByPostId(Long postId);
}
