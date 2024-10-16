package play.board1.common.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@SequenceGenerator(
        name = "cmm_file_seq_gen",
        sequenceName = "cmm_file_seq",
        initialValue = 1
)
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CmmFile {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "cmm_file_seq_gen")
    @Column(name = "cmm_file_id")
    private Long id;

    private Long size;
    private String realFileName;
    private String localFileName;
    private String fileUrl;

    public CmmFile(Long size, String realFileName, String localFileName, String fileUrl) {
        this.size = size;
        this.realFileName = realFileName;
        this.localFileName = localFileName;
        this.fileUrl = fileUrl;
    }
}
