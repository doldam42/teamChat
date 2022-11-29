package doldam.teamChat.room;

import doldam.teamChat.domain.Room;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;


@Getter
@Builder
public class RoomDTO {
    private String roomId;
    private String title;
    @Builder.Default private Set<WebSocketSession> sessions = new HashSet<>();

    public void addSession(WebSocketSession session) {
        sessions.add(session);
    }

    public Set<WebSocketSession> getSessions() {
        return sessions;
    }

    public Room toEntity() {
        Room room = new Room();
        room.setRoomId(roomId);
        room.setTitle(title);
        return room;
    }
}
