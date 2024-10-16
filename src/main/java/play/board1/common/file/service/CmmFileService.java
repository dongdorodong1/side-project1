package play.board1.common.file.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import play.board1.common.dto.CmmFileDto;
import play.board1.common.entity.CmmFile;
import play.board1.common.file.repository.CmmFileRepository;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CmmFileService {

    @Value("${file.dir}")
    private String fileDir;
    private final CmmFileRepository fileRepository;

    public CmmFileDto saveFile(MultipartFile file) throws IOException {
        String realFileName = file.getOriginalFilename();
        String localFileName = createLocalFileName(realFileName);
        String fileUrl = getFullPath(localFileName);
        file.transferTo(new File(fileUrl));
        long fileSize = file.getSize();
        CmmFile cmmFile = new CmmFile(fileSize, realFileName, localFileName, fileUrl);
        fileRepository.saveFile(cmmFile);

        return new CmmFileDto(realFileName,localFileName,fileUrl,fileSize);
    }

    private String getFullPath(String localFileName) {
        return fileDir + localFileName;
    }

    private String createLocalFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
}
