package play.board1.common.login.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import play.board1.board.dto.MemberDto;
import play.board1.board.dto.SignUpMemberDto;
import play.board1.board.entity.Member;
import play.board1.common.login.repository.LoginRepository;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final LoginRepository loginRepository;
    public void signUp(SignUpMemberDto memberDto) {
        Member member = memberDto.toEntity();
        loginRepository.signUp(member);
    }
}
