package play.board1.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import play.board1.board.dto.BoardCmtInsertDto;
import play.board1.board.dto.BoardDto;
import play.board1.board.dto.Paging;
import play.board1.board.entity.Board;
import play.board1.board.entity.BoardComment;
import play.board1.board.repository.BoardCommentRepository;
import play.board1.board.repository.BoardJpaRepository;
import play.board1.board.repository.BoardRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {
//    private final BoardRepository boardRepository;
    private final BoardJpaRepository boardRepository;
    private final BoardCommentRepository commentRepository;

    @Transactional
    public void insertAtcl(BoardDto boardDto) {
        Board board = new Board(boardDto.getSubject(),boardDto.getContent(), LocalDateTime.now());
        boardRepository.save(board);
    }

    public HashMap<String, Object> selectAllAtcl(Paging paging) {
        PageRequest pageRequest = PageRequest.of(paging.getPageNumber(), paging.getPageSize(), Sort.by(Sort.Direction.DESC, "id"));

        Page<Board> boardList = boardRepository.findByPage(pageRequest);
        List<BoardDto> result = boardList.getContent().stream()
                .map(b -> new BoardDto(b))
                .collect(toList());

        HashMap<String, Object> resultMap = new HashMap<>();
        paging.setPageSize(boardList.getSize());
        paging.setTotalPages(boardList.getTotalPages());
        paging.setFirst(boardList.isFirst());
        paging.setTotalCount(boardList.getTotalElements());

        paging.setNext(boardList.hasNext());
        resultMap.put("boardList",result);
        resultMap.put("paging",paging);

//        System.out.println(boardList.getTotalPages()); // 전체 페이지 수 8
//        System.out.println(boardList.getSize()); // 한페이지에 출력할 갯수15
//        System.out.println(boardList.getTotalElements()); // 목록갯수 111
//        System.out.println(boardList.getNumberOfElements()); //가져온 수  15
//        for (Board board : boardList.getContent()) {
//            System.out.println(board);
//        }


        resultMap.put("paging",paging);
        return resultMap;
    }

    public BoardDto findBoard(Long boardId) {
        /*Optional<Board> findBoard = boardRepository.findById(boardId);
        BoardDto boardDto = new BoardDto();
        if(findBoard.isPresent()) {
             boardDto = new BoardDto(findBoard.get());
        }

        return boardDto;*/
        return boardRepository.findById(boardId)
                .map(BoardDto::new)
                .orElse(new BoardDto());
    }

    @Transactional
    public Long deleteBoard(Long id) {
//        return boardRepository.deleteBoard(id);
        boardRepository.deleteById(id);
        return id;
    }

    @Transactional
    public Long updateBoard(BoardDto boardDto) {
        Optional<Board> findBoardOptional = boardRepository.findById(boardDto.getId());
        findBoardOptional.ifPresent(board -> {
            board.updateAtcl(boardDto);
        });
        return 1L;
    }
    @Transactional
    public Long insertComment(BoardCmtInsertDto cmtDto) {
        Optional<Board> findBoard = boardRepository.findById(cmtDto.getBoardId());
        BoardComment comment = new BoardComment();
        if(findBoard.isPresent()) {
            comment = new BoardComment(cmtDto.getContent(), findBoard.get());
            commentRepository.save(comment);
            return 1L;
        }
        return 0L;
    }

    public List<BoardCmtInsertDto> selectComment(Long boardId) {
        List<BoardComment> comments = commentRepository.findCommentListByBoardId(boardId);
        List<BoardCmtInsertDto> cDtos = comments.stream().map(c -> new BoardCmtInsertDto(c)).collect(toList());
        return cDtos;
    }
}
