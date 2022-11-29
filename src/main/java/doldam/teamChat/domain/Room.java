package doldam.teamChat.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table(name = "Room")
public class Room {
    @Id
    private String roomId;
    private String title;

    @OneToMany(mappedBy = "room")
    private List<Chat> chats;
}
