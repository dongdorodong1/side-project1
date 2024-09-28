package play.board1;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
    @GetMapping("")
    public String index(Model model, HttpSession session) {
        session.getAttribute("loginMember");

        return "/post/postList";
    }

    @GetMapping("/layout")
    public String layout(Model model, HttpSession session) {
        session.getAttribute("loginMember");

        return "layout/content";
    }
}
