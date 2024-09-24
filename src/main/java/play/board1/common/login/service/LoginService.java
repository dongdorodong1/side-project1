package play.board1.common.login.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import play.board1.common.dto.MemberDto;
import play.board1.common.dto.SignInMemberDto;
import play.board1.common.dto.SignUpMemberDto;
import play.board1.board.entity.Member;
import play.board1.common.login.repository.LoginRepository;
import play.board1.common.login.repository.MemberRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LoginService {

    private final LoginRepository loginRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void signUp(SignUpMemberDto memberDto) {
        Member member = memberDto.toEntity();
        loginRepository.signUp(member);
    }

    public Member signIn(SignInMemberDto member) {
        Member findMember = memberRepository.findByUserId(member.getUserId());

        return findMember;
    }
}
