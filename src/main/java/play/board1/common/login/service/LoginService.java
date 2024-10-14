package play.board1.common.login.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import play.board1.common.dto.MemberDto;
import play.board1.common.dto.SignUpMemberDto;
import play.board1.common.exception.MemberException;
import play.board1.post.entity.Member;
import play.board1.common.login.repository.LoginRepository;
import play.board1.common.login.repository.MemberRepository;

import java.util.Optional;

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

    public Member signIn(MemberDto member) {
        Optional<Member> findMember = memberRepository.findByUserId(member.getUserId());
        findMember.orElseThrow(MemberException::new);

        return findMember.get();
    }

    /**
     * id를 가진 사용자가 있을 경우
     * @param userId
     * @return
     */
    public boolean isExistUserByUserId(String userId) {
        Optional<Member> findMember = memberRepository.findByUserId(userId);
        findMember.orElseThrow(IllegalStateException::new);

        return true;
    }
    public Optional<Member> findMemberByUserId(String userId) {
        return memberRepository.findByUserId(userId);
    }
}
