package chat;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> getMessageByChatroom(int chatroomid);

    List<ChatMessage> getMessagesPastTimestamp(int chatroomid, long timestamp);
}
