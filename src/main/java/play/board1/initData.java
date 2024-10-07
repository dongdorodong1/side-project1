package play.board1;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import play.board1.common.dto.MemberDto;
import play.board1.common.dto.SignUpMemberDto;
import play.board1.common.login.service.LoginService;
import play.board1.post.dto.PostDto;
import play.board1.post.entity.Member;
import play.board1.post.service.PostService;

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
        private final LoginService loginService;
        private final PostService postService;

        public void dbInit1() {
            loginService.signUp(new SignUpMemberDto("111","유동환","1234","111", LocalDateTime.now()));
            postService.insertPost(new PostDto("test","111",new MemberDto("111","유동환")));
        }
    }
}
