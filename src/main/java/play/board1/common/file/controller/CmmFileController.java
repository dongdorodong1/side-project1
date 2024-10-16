package play.board1.common.file.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import play.board1.common.dto.CmmFileDto;
import play.board1.common.file.service.CmmFileService;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/common",method = {RequestMethod.GET,RequestMethod.POST})
public class CmmFileController {


    @Value("${file.dir}")
    private String fileDir;
    private final CmmFileService fileService;

    @PostMapping("/uploadFile")
    public ResponseEntity<CmmFileDto> uploadFile(@RequestParam("files") List<MultipartFile> files, HttpServletRequest request) throws IOException {
        // 파일 저장 로직 처리
        CmmFileDto cmmFileDto = null;


        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                 cmmFileDto = fileService.saveFile(file);
            }
            log.info("request={}", request);
            log.info("multipartFile={}", file);
            String fullPath = fileDir + file.getOriginalFilename();
            log.info("파일 저장 fullPath={}", fullPath);
            file.transferTo(new File(fullPath));
        }

        // 업로드된 파일에 대한 응답 (예: 파일 ID 리스트 반환)
        return ResponseEntity.ok(cmmFileDto);
    }
}
