package play.board1.post.repository;

import play.board1.post.entity.Post;
import play.board1.post.entity.PostComment;
import play.board1.post.entity.PostViewLog;

import java.util.List;
import java.util.Optional;

public interface PostCustomRepository {

    void insertPost(Post post);

    List<Post> selectAllAtcl(int offset, int limit);

    long totalCount(int age);

    Post findBoard(Long postId);

    Long deleteBoard(Long id);

    Post findBoardById(Long postId);
    Long insertComment(PostComment comment);

    List<PostComment> selectComment(Long postId);

    Optional<PostViewLog> existViewLog(Long postId, Long memberId);

    void updateViewLog(PostViewLog postViewLog);

    void saveViewLog(PostViewLog postViewLog);
}
