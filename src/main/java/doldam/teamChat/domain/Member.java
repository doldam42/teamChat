package doldam.teamChat.domain;

import doldam.teamChat.auth.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "member")
public class Member {
    @Id
    private String email;
    private String password;
    private String nick;
    @Enumerated(EnumType.STRING)
    private Role role;
    private boolean enabled;
}
