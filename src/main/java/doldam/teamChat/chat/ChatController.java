package doldam.teamChat.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 *
 */
@RestController
public class ChatController {
    private final ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

//    @GetMapping("/chat")
//    public List<RoomDTO> findAllRoom() {
//        return chatService.findAllRoom();
//    }

    @GetMapping("/room/{roomId}/me/chat")
    @ResponseBody
    public List<ChatDTO> findAllChat(@PathVariable(value = "roomId") String roomId) {
        return chatService.findChats(roomId);
    }
}
