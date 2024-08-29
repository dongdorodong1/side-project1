package play.board1.board.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import play.board1.board.dto.BoardCmtInsertDto;
import play.board1.board.entity.Board;
import play.board1.board.entity.BoardComment;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardRepository {
    private final EntityManager em;
    public void insertAtcl(Board board) {
        em.persist(board);
    }

    public List<Board> selectAllAtcl(int offset,int limit) {
        return em.createQuery("select b from Board b order by b.id desc",Board.class)
                .setFirstResult(offset) //몇번째부터
                .setMaxResults(limit) //몇개 가져올꺼야
                .getResultList();
    }

    public long totalCount(int age) {
        return em.createQuery("select count(b) from Board b",Long.class)
                .getSingleResult();
    }

    public Board findBoard(Long boardId) {
//        em.createQuery("select b from Board b join fetch b.comments where b.id = :boardId");
//        Board board = em.find(Board.class, boardId);
//        return board;

        return em.createQuery("select b from Board b left join fetch b.comments where b.id = :boardId", Board.class)
                .setParameter("boardId", boardId)
                .getSingleResult();
    }

    public Long deleteBoard(Long id) {
        Board findBoard = em.find(Board.class, id);
        em.remove(findBoard);
        return findBoard.getId();
    }

 /*   public Long updateAtcl(Board board) {
        Board updateBoard = em.find(Board.class, board.getId());
        updateBoard.updateAtcl(board);
        em.persist(updateBoard);
        return updateBoard.getId();
    }*/

    public Board findBoardById(Long boardId) {
        return em.find(Board.class, boardId);
    }

    public Long insertComment(BoardComment comment) {
        em.persist(comment);
        return comment.getId();
    }

    public List<BoardComment> selectComment(Long boardId) {
        return em.createQuery("select c from BoardComment c where c.board.id = :boardId order by c.id desc", BoardComment.class)
                .setParameter("boardId", boardId)
                .getResultList();
    }
}
