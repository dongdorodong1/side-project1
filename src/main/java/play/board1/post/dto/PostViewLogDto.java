package play.board1.post.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PostViewLogDto {
    private long viewCnt;

    public PostViewLogDto(long viewCnt) {
        this.viewCnt = viewCnt;
    }
}
