package doldam.teamChat.repository;

import doldam.teamChat.domain.Chat;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface ChatRepository {
    List<Chat> findAllByRoomId(String roomId);
    Chat save(Chat chat);
}
