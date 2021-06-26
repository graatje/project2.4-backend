package Application.chat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ChatMessage {
    public ChatMessage(long senderid, int chatroomid, String message, long timestamp){
        this.senderid = senderid;
        this.chatroomid = chatroomid;
        this.message = message;
        this.timestamp = timestamp;
    }
    public ChatMessage(){}
    private @Id @GeneratedValue Long messageid;
    private int chatroomid;
    private long senderid;
    private String message;
    private long timestamp;

    public long getMessageId(){
        return this.messageid;
    }

    public long getSenderid() {
        return senderid;
    }

    public void setSenderid(long senderid) {
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
