package doldam.teamChat.repository;

import doldam.teamChat.domain.Member;
import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<Member, String> {

}
