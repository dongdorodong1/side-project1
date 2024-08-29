package play.board1.board.controller;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import play.board1.board.dto.BoardCmtInsertDto;
import play.board1.board.dto.BoardDto;
import play.board1.board.dto.MemberDto;
import play.board1.board.dto.Paging;
import play.board1.board.entity.Board;
import play.board1.board.service.BoardService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/board",method = {RequestMethod.GET,RequestMethod.POST})
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/boardList")
    public String boardList() {
        return "boardList";
    }
    @GetMapping("/boardMain")
    public String boardMain() {
        return "boardMain";
    }

    @PostMapping("/selectBoardList")
    @ResponseBody
    public HashMap<String,Object> selectBoardList(Paging paging) {
        return boardService.selectAllAtcl(paging);
    }
    @GetMapping("/boardReg")
    public String boardReg() {
        return "boardReg";
    }

    @PostMapping("/insertAtcl")
    public @ResponseBody String insertAtcl(BoardDto board) {
        boardService.insertAtcl(board);
        return "ok";
    }
    @PostMapping("/boardView/{id}")
    public String boardView(@PathVariable Long id,Model model) {
        BoardDto board = boardService.findBoard(id);
        model.addAttribute("board",board);
        return "boardView";
    }
    @PostMapping("/boardDelete/{id}")
    public String boardDelete(@PathVariable Long id,Model model) {
        Long deletedBoard = boardService.deleteBoard(id);
        return "redirect:/board/boardList";
    }
    @GetMapping("/boardUpdate/{id}")
    public String boardUpdate(@PathVariable Long id,Model model) {
        BoardDto board = boardService.findBoard(id);
        model.addAttribute("board",board);
        return "boardUpdate";
    }
    @PostMapping("/updateAtcl")
    public @ResponseBody String updateAtcl(BoardDto boardDto) {
        Long updateBoard = boardService.updateBoard(boardDto);
        return "ok";
    }
    @PostMapping("/insertComment")
    public @ResponseBody ResponseEntity<Long> insertComment(@RequestBody BoardCmtInsertDto cmtDto) {
        Long commentId = boardService.insertComment(cmtDto);
        return new ResponseEntity<>(commentId, HttpStatusCode.valueOf(200));
    }

    @GetMapping("/selectComment")
    public ResponseEntity<List<BoardCmtInsertDto>> selectComment(Long boardId) {
        List<BoardCmtInsertDto> comments = boardService.selectComment(boardId);
        return new ResponseEntity<>(comments, HttpStatusCode.valueOf(200));
    }
}
