package play.board1.post.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import play.board1.post.dto.PostCommentDto;
import play.board1.post.entity.Post;
import play.board1.post.entity.PostComment;
import play.board1.post.entity.PostViewLog;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.List;
import java.util.Optional;

import static play.board1.post.entity.QMember.member;
import static play.board1.post.entity.QPost.post;
import static play.board1.post.entity.QPostViewLog.postViewLog;

@Repository
@RequiredArgsConstructor
public class PostCustomRepositoryImpl implements PostCustomRepository{

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

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
        return queryFactory
                .select(post)
                .from(post)
                .leftJoin(post.comments).fetchJoin()
                .where(post.id.eq(postId))
                .fetchOne();
    }


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
    public PostViewLog existViewLog(Long postId, Long memberId) {

        try {
            return queryFactory
                    .selectFrom(postViewLog)
                    .where(
                      postViewLog.post.id.eq(postId),
                        postViewLog.member.id.eq(memberId)
                    )
                    .fetchOne();
        } catch (NoResultException e) {
            return null;  // 결과가 없을 경우 Optional.empty() 반환
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

    @Override
    public PostComment findCommentById(Long id) {
        return em.find(PostComment.class, id);
    }

    @Override
    public void updatePostComment(PostCommentDto cmntDto) {
        PostComment comment = findCommentById(cmntDto.getId());
        comment.updateComment(cmntDto.getContent());
    }


}
