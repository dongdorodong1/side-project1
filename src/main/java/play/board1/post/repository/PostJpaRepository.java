package play.board1.post.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import play.board1.post.entity.Post;

public interface PostJpaRepository extends JpaRepository<Post,Long> {

    @Query(value = "select m from Post m")
    Page<Post> findByPage(Pageable pageable);
}
