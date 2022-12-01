package doldam.teamChat.room;

import doldam.teamChat.domain.Room;
import doldam.teamChat.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.*;

@Transactional
@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final Map<String, RoomDTO> chatRooms;
    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
        chatRooms = new LinkedHashMap<>();
    }

//    @PostConstruct
//    private void init() {
//        chatRooms = new LinkedHashMap<>();
//    }

    public List<RoomDTO> findAllRoom() {
        return new ArrayList<>(chatRooms.values());
    }

    public RoomDTO createRoom(String title) {
        String randomId = UUID.randomUUID().toString();
        RoomDTO chatRoom = RoomDTO.builder()
                .roomId(randomId)
                .title(title)
                .build();
        chatRooms.put(randomId, chatRoom);
        roomRepository.save(chatRoom.toEntity());
        return chatRoom;
    }

    /**
     * 메모리에 방이 존재하지 않지만 데이터베이스 상에 존재할 경우 chatRoom 생성
     */
    public RoomDTO enter(String roomId) {
        // 메모리상에 채팅방이 존재하면 반환
        if (chatRooms.containsKey(roomId)) {
            return chatRooms.get(roomId);
        }
        // 메모리상에 채팅방이 존재하지 않으면 db에 방이 존재하는지 찾은 후 반환
        Optional<Room> op = roomRepository.findById(roomId);
        if (op.isEmpty()) {
            throw new NoSuchElementException("채팅방이 존재하지 않습니다.");
        }
        RoomDTO chatRoom = RoomDTO.builder()
                .roomId(roomId)
                .title(op.get().getTitle())
                .build();
        chatRooms.put(roomId, chatRoom);
        return chatRoom;
    }
}
