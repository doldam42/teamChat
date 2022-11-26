package doldam.teamChat.auth;

import doldam.teamChat.domain.Member;
import doldam.teamChat.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@Service
public class AuthService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 회원가입 메서드
     * todo: 예외처리를 더 분명히 해야함.
     * @param newMember 회원가입 멤버 form
     * @return 성공여부
     */
    public boolean join(JoinForm newMember) {

        Member member = new Member();
        member.setEmail(newMember.getEmail());
        member.setPassword(passwordEncoder.encode(newMember.getPassword()));
        member.setNick(newMember.getNick());
        member.setRole(Role.ROLE_MEMBER);
        member.setEnabled(true);

        System.out.println(member);
        if (memberRepository.findById(member.getEmail()).isEmpty()) {
            memberRepository.save(member);
            return true;
        }

        return false;
    }

}
