package doldam.teamChat.home;

import doldam.teamChat.auth.MyUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/main")
    public String main(Model model, @AuthenticationPrincipal MyUserDetails customUser) {
        model.addAttribute("nick", customUser.getNick());
        return "home";
    }
}
