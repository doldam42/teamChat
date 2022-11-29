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

    public Optional<RoomDTO> findActiveRoomById(String roomId) {
        if (chatRooms.containsKey(roomId)) {
            return Optional.of(chatRooms.get(roomId));
        }
        return Optional.empty();
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
     * @param roomId
     */
    public RoomDTO enter(String roomId) {
        Optional<RoomDTO> op1 = findActiveRoomById(roomId);
        // 메모리상에 채팅방이 존재하지 않으면 db에 방이 존재하는지 찾은 후 반환
        if (op1.isEmpty()) {
            Optional<Room> op2 = roomRepository.findById(roomId);
            if (op2.isEmpty()) {
                throw new NoSuchElementException();
            }
            RoomDTO chatRoom = RoomDTO.builder()
                    .roomId(roomId)
                    .title(op2.get().getTitle())
                    .build();
            chatRooms.put(roomId, chatRoom);
            return chatRoom;
        } else {
            return op1.get();
        }
    }
}
