package doldam.teamChat.domain;

import doldam.teamChat.domain.Room;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;


/**
 * Chat 을 정의한 jpa entity 클래스
 *
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "chat")
public class Chat {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column private String nick;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @Column private String message;
}
