package play.board1.common.login.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import play.board1.post.entity.Member;

@Repository
@RequiredArgsConstructor
public class LoginRepository {
    private final EntityManager em;

    public void signUp(Member member) {
        em.persist(member);
    }

    public Member signIn(String userId) {
        return null;
    }
}
