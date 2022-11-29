package doldam.teamChat.room;

import doldam.teamChat.auth.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Controller
public class RoomController {
    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/room/{roomId}")
    public String room(@PathVariable(value = "roomId") String roomId, Model model, @AuthenticationPrincipal MyUserDetails customUser) {
        model.addAttribute("sender", customUser.getNick());
        model.addAttribute("roomId", roomId);
        roomService.enter(roomId);
        return "room";
    }

    /**
     * 방 생성 요청
     *
     * @param newRoom 새로 생성한 방
     * @return room id
     */
    @ResponseBody
    @PostMapping("/room")
    public String createRoom(RoomForm newRoom) {
        return roomService.createRoom(newRoom.getTitle()).getRoomId();
    }
}
