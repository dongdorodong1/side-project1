package play.board1.post.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import play.board1.post.entity.Post;
import play.board1.post.entity.PostComment;
import play.board1.post.entity.PostViewLog;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PostCustomRepositoryImpl implements PostCustomRepository{

    private final EntityManager em;
    public void insertPost(Post post) {
        em.persist(post);
    }

    public List<Post> selectAllAtcl(int offset, int limit) {
        return em.createQuery("select b from Post b order by b.id desc", Post.class)
                .setFirstResult(offset) //몇번째부터
                .setMaxResults(limit) //몇개 가져올꺼야
                .getResultList();
    }

    public long totalCount(int age) {
        return em.createQuery("select count(b) from Post b",Long.class)
                .getSingleResult();
    }

    public Post findBoard(Long postId) {
//        em.createQuery("select b from Board b join fetch b.comments where b.id = :postId");
//        Board post = em.find(Board.class, postId);
//        return post;

        return em.createQuery("select b from Post b left join fetch b.comments where b.id = :postId", Post.class)
                .setParameter("postId", postId)
                .getSingleResult();
    }

    public Long deleteBoard(Long id) {
        Post findPost = em.find(Post.class, id);
        em.remove(findPost);
        return findPost.getId();
    }

 /*   public Long updatePost(Board post) {
        Board updateBoard = em.find(Board.class, post.getId());
        updateBoard.updatePost(post);
        em.persist(updateBoard);
        return updateBoard.getId();
    }*/

    public Post findBoardById(Long postId) {
        return em.find(Post.class, postId);
    }

    public Long insertComment(PostComment comment) {
        em.persist(comment);
        return comment.getId();
    }

    public List<PostComment> selectComment(Long postId) {
        return em.createQuery("select c from PostComment c where c.post.id = :postId order by c.id desc", PostComment.class)
                .setParameter("postId", postId)
                .getResultList();
    }

    @Override
    public Optional<PostViewLog> existViewLog(Long postId, Long memberId) {

        // TODO 쿼리 dsl로 is null 적용하기
        try {
            PostViewLog log = em.createQuery("select v from PostViewLog v where v.post.id =:postId and v.member.id = :memberId", PostViewLog.class)
                    .setParameter("postId", postId)
                    .setParameter("memberId", memberId)
                    .getSingleResult();
            return Optional.of(log);
        } catch (NoResultException e) {
            return Optional.empty();  // 결과가 없을 경우 Optional.empty() 반환
        }
    }

    @Override
    public void updateViewLog(PostViewLog postViewLog) {
        postViewLog.updateViewLog();
    }

    @Override
    public void saveViewLog(PostViewLog postViewLog) {
        em.persist(postViewLog);
    }

    /**
     * 게시물의 댓글을 삭제한다.
     * @param cmntId
     * @return
     */
    @Override
    public void deleteComment(String cmntId) {
        PostComment postComment = em.find(PostComment.class, Long.parseLong(cmntId));
        em.remove(postComment);
    }
}
