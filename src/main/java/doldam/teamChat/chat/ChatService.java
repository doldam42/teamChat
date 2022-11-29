package doldam.teamChat.chat;

import doldam.teamChat.domain.Chat;
import doldam.teamChat.domain.Room;
import doldam.teamChat.repository.ChatRepository;
import doldam.teamChat.repository.MemberRepository;
import doldam.teamChat.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional
@Service
public class ChatService {
    private final ChatRepository chatRepository;
    private final RoomRepository roomRepository;

    @Autowired
    public ChatService(ChatRepository chatRepository, RoomRepository roomRepository) {
        this.chatRepository = chatRepository;
        this.roomRepository = roomRepository;
    }

    /**
     * 채팅방에 적힌 이전 채팅들을 불러온다.
     *
     * @param roomId
     * @return List<ChatDTO>
     */
    public List<ChatDTO> findChats(String roomId) throws DataAccessException {
        List<Chat> chats = chatRepository.findAllByRoomId(roomId);
        List<ChatDTO> ret = new ArrayList<>();
        chats.forEach(chat -> {
            ChatDTO chatDTO = new ChatDTO();
            chatDTO.setType(ChatDTO.MessageType.TALK);
            chatDTO.setMessage(chat.getMessage());
            chatDTO.setSender(chat.getNick());
            ret.add(chatDTO);
        });
        return ret;
    }

    /**
     * 채팅을 저장한다.
     * @param chatDTO
     * @return
     */
    public Long saveChat(ChatDTO chatDTO) throws DataAccessException {
        Chat chat = new Chat();

        chat.setMessage(chatDTO.getMessage());
        chat.setNick(chatDTO.getSender());
        Room room = roomRepository.getReferenceById(chatDTO.getRoomId());
        chat.setRoom(room);

        chatRepository.save(chat);
        return chat.getId();
    }
}
