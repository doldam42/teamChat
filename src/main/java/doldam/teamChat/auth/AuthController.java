package doldam.teamChat.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    public final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/auth/join")
    public String join() {
        return "join";
    }
    @PostMapping("/auth/join")
    public String joinMember(JoinForm newMember) {
        authService.join(newMember);
        return "redirect:/auth/login";
    }


    @GetMapping("/auth/login")
    public String login() {
        return "login";
    }

//    @GetMapping("/auth/logout")
//    public String logout() {
//        return "redirect:/auth/login";
//    }

}
