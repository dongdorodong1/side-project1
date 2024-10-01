package play.board1.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import play.board1.post.entity.PostLike;

public interface PostLikeRepository extends JpaRepository<PostLike,Long> {


}
