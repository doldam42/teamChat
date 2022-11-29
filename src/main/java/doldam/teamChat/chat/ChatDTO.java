package doldam.teamChat.chat;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChatDTO {
    public enum MessageType {
        @JsonProperty("enter")
        ENTER,
        @JsonProperty("talk")
        TALK
    }

    public MessageType type;
    private String roomId;
    private String sender;
    private String message;
}
