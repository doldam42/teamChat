package doldam.teamChat.repository;

import doldam.teamChat.domain.Chat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class JpaChatRepository implements ChatRepository{
    private final EntityManager em;

    @Autowired
    public JpaChatRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Chat> findAllByRoomId(String roomId) {
        return em.createQuery("select c from Chat c inner join c.room where c.room.roomId = :roomId", Chat.class)
                .setParameter("roomId", roomId)
                .getResultList();
    }

    @Override
    public Chat save(Chat chat) {
        em.persist(chat);
        return chat;
    }
}
