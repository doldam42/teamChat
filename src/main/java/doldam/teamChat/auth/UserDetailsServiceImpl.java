package doldam.teamChat.auth;

import doldam.teamChat.domain.Member;
import doldam.teamChat.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final MemberRepository memberRepository;
    @Autowired
    public UserDetailsServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> op = memberRepository.findById(username);
        if (op.isEmpty()) {
            throw new UsernameNotFoundException("사용자 없음");
        }
        Member member = op.get();
        System.out.println(member);

        return new MyUserDetails(member);
    }
}
