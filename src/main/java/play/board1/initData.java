package play.board1;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
       /*     for (int i = 0; i < 10; i++) {
                em.persist(new Board("test게시글","test내용",new Member("test"+ UUID.randomUUID().toString(),"user"+i,"testuser"+i,"1234","테스트"+i,"010-0000-0000"), LocalDateTime.now()));
            }*/
        }
    }
}
