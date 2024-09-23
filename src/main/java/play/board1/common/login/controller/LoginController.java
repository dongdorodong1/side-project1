package play.board1.common.login.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import play.board1.board.dto.SignUpMemberDto;
import play.board1.common.login.service.LoginService;

@Controller
@RequestMapping(value = "/common")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/signUp")
    public String signUp() {
        return "common/signUp";
    }

    @PostMapping("/signUp")
    @ResponseBody
    public String signUp(@RequestBody SignUpMemberDto member) {
        loginService.signUp(member);

        return "ok";
    }
}
