package play.board1.common.login.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import play.board1.post.entity.Member;
import play.board1.common.dto.MemberDto;
import play.board1.common.dto.SignUpMemberDto;
import play.board1.common.login.service.LoginService;

@Controller
@RequestMapping(value = "/login")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 회원가입 페이지
     * @return
     */
    @GetMapping("/signUp")
    public String signUp() {
        return "common/signUp";
    }

    /**
     * 회원가입 처리
     * @return
     */
    @PostMapping("/signUp")
    @ResponseBody
    public String signUp(@RequestBody SignUpMemberDto member) {
//        member.setPassword(bCryptPasswordEncoder.encode(member.getPassword()));
        loginService.signUp(member);

        return "ok";
    }

    /**
     * 로그인 화면으로 이동
     * @return
     */
    @GetMapping("/signIn")
    public String signIn() {
        return "common/signIn";
    }

    /**
     * 로그인을 처리한다.
     * @return
     */
    @PostMapping("/signIn")
    @ResponseBody
    public ResponseEntity<MemberDto> signIn(@RequestBody MemberDto requestMember, HttpSession session) {

        //요청받은 회원 Id로 조회
        Member findMember = loginService.signIn(requestMember);
        //
        if(null == findMember) return null;
        if(null != findMember && requestMember.pwdCheck(findMember.getPassword())) {
            // TODO DTO 정보 업데이트하기
            requestMember.setUsername(findMember.getUsername());
            requestMember.setUserId(findMember.getUserId());
            session.setAttribute("loginMember",requestMember);

            return ResponseEntity.ok(requestMember);
        }
        return null;
    }

    /**
     * 로그아웃
     * @return
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("loginMember");
        return "redirect:/";
    }

    /**
     * 아이디 중복검사
     * @return
     */
    @GetMapping("/checkId")
    public ResponseEntity<Boolean> checkId(String userId) {
        return ResponseEntity.ok(loginService.isExistUserByUserId(userId));
    }
}
