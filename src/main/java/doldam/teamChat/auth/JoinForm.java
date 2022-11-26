package doldam.teamChat.auth;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class JoinForm {
    private String email;
    private String password;
    private String nick;
    private String desiredJob;
}
