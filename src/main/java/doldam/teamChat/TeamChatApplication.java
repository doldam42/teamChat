package doldam.teamChat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class TeamChatApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeamChatApplication.class, args);
	}

}
