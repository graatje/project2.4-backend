package Application.chat;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<ChatMessage, Long> {
    public List<ChatMessage> findByTimestampGreaterThanAndChatroomidOrderByTimestamp(Long timestamp, int chatroomid);
}
