package Application.chat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ChatMessage {
    private @Id @GeneratedValue Long messageid;
    private int chatroomid;
    private int senderid;
    private String message;
    private long timestamp;

    public int getSenderid() {
        return senderid;
    }

    public void setSenderid(int senderid) {
        this.senderid = senderid;
    }

    public int getChatroomid() {
        return chatroomid;
    }

    public void setChatroomid(int chatroomid) {
        this.chatroomid = chatroomid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
