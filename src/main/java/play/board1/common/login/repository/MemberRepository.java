package play.board1.common.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import play.board1.post.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{

    Member findByUserId(String userId);
}
