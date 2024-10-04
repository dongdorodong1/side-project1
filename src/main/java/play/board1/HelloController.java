package play.board1;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import play.board1.common.session.SessionConst;

@Controller
public class HelloController {
    @GetMapping("")
    public String index(Model model, HttpSession session) {
        session.getAttribute(SessionConst.LOGIN_MEMBER);

        return "/post/postList";
    }

    @GetMapping("/layout")
    public String layout(Model model, HttpSession session) {
        session.getAttribute(SessionConst.LOGIN_MEMBER);

        return "layout/content";
    }
}
