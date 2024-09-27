package play.board1.post.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Paging {

    private int pageNumber; //조회할 페이지 넘버
    private int pageSize; // 한꺼번에 가져올 목록 갯수
    private long totalCount; //전체 목록 갯수
    private int totalPages; //전체 페이지 수
    private int numberOfElements; //실제 가져온 수
    private boolean isFirst;
    private boolean isNext;

    public Paging(int pageNumber, int pageSize, int totalCount) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
    }
}
