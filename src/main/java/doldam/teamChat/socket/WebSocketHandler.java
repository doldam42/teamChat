package doldam.teamChat.socket;

import com.fasterxml.jackson.databind.ObjectMapper;
import doldam.teamChat.chat.ChatDTO;
import doldam.teamChat.chat.ChatService;
import doldam.teamChat.room.RoomDTO;
import doldam.teamChat.room.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

/**
 * 웹소켓 통신을 처리하는 핸들러
 * 연결된 클라이언트들의 text message를 중개한다.
 *
 */
@Component
public class WebSocketHandler extends TextWebSocketHandler {
    private final ObjectMapper objectMapper;
    private final ChatService chatService;
    private final RoomService roomService;

    @Autowired
    public WebSocketHandler(ObjectMapper objectMapper, ChatService chatService, RoomService roomService) {
        this.objectMapper = objectMapper;
        this.chatService = chatService;
        this.roomService = roomService;
    }

    /**
     * 웹소켓을 통해 메세지가 들어오면
     * 메세지가 입장 메세지면 채팅방에 입장 시켜주고,
     * 채팅 메세지이면 채팅방에 연결된 모든 세션에 채팅 전파
     *
     * @param session 웹소켓 세션
     * @param message 웹소켓으로 오는 메세지 객체
     * @throws Exception
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        // wanted: 로깅 코드 추가
        ChatDTO chatDTO = objectMapper.readValue(payload, ChatDTO.class);
        RoomDTO chatRoom = roomService.enter(chatDTO.getRoomId());

        if (chatDTO.getType().equals(ChatDTO.MessageType.ENTER)) {
            chatRoom.addSession(session);
            chatDTO.setMessage(chatDTO.getSender() + "님이 입장했습니다.");
        }
        sendMessage(chatDTO, chatRoom);
        chatService.saveChat(chatDTO);
    }

    /**
     * 채팅방에 연결된 모든 세션에 메세지를 전달하는 함수
     * @param message 채팅 메세지 (주로 ChatDTO)
     * @param chatRoom ChatRoomDTO 객체, 저장된 세션을 사용함
     * @param <T> 메세지 객체를 의미 주로 채팅 메세지
     */
    private <T> void sendMessage(T message, RoomDTO chatRoom) {
        chatRoom.getSessions().parallelStream()
                .forEach(session -> {
                    try {
                        session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }

}
