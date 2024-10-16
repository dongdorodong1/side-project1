package play.board1.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CmmFileDto {
    private String realFileName;
    private String localFileName;
    private String fileUrl;
    private Long fileSize;


}
