package play.board1.common.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import play.board1.post.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>{

    Optional<Member> findByUserId(String userId);
}
