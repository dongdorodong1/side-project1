package play.board1.post.dto;

import play.board1.post.entity.Post;

public class PostCmtUpdatedDto {


    private Long id;
    private String content;
    private Post post;

    public PostCmtUpdatedDto(String content, Post post) {
        this.content = content;
        this.post = post;
    }
}
