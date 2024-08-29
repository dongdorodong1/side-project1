package play.board1.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import play.board1.board.entity.BoardComment;

import java.util.List;

public interface BoardCommentRepository extends JpaRepository<BoardComment,Long> {

    @Query("select c from BoardComment c where c.board.id = :boardId order by c.id desc")
    List<BoardComment> findCommentListByBoardId(Long boardId);
}
