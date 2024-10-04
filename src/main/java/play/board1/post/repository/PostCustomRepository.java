package play.board1.post.repository;

import play.board1.post.entity.Post;
import play.board1.post.entity.PostComment;

import java.util.List;

public interface PostCustomRepository {

    void insertPost(Post post);

    List<Post> selectAllAtcl(int offset, int limit);

    long totalCount(int age);

    Post findBoard(Long postId);

    Long deleteBoard(Long id);

    Post findBoardById(Long postId);
    Long insertComment(PostComment comment);

    List<PostComment> selectComment(Long postId);
}
