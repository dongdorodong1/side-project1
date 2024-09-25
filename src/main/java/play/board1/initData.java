package play.board1;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import play.board1.board.entity.Board;
import play.board1.board.repository.BoardRepository;
import play.board1.board.service.BoardService;

import java.time.LocalDateTime;

//@Component
@RequiredArgsConstructor
public class initData {

    private final InitService initService;

    @PostConstruct
    public void initData() {
        initService.dbInit1();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;

        public void dbInit1() {
            for (int i = 0; i < 10; i++) {
                em.persist(new Board("test게시글","test내용", LocalDateTime.now()));
            }
        }
    }
}
