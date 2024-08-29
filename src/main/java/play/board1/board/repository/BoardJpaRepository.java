package play.board1.board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import play.board1.board.entity.Board;

import java.util.List;

public interface BoardJpaRepository extends JpaRepository<Board,Long> {

    @Query(value = "select m from Board m")
    Page<Board> findByPage(Pageable pageable);
}
