package Application.chat;

import Application.users.User;
import Application.users.UserRepository;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ChatController {
    private final ChatRepository repository;
    private UserRepository userRepository;
    ChatController(ChatRepository repository, UserRepository userRepository){
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("chatroom/{chatroomid}/{timestamp}")
    public ResponseEntity<List<JSONObject>> getChatMessages(@PathVariable int chatroomid, @PathVariable long timestamp){
        List<ChatMessage> messages = repository.findByTimestampGreaterThanAndChatroomidOrderByTimestamp(timestamp, chatroomid);
        return new ResponseEntity<>(jsonifyChatMessages(messages), HttpStatus.OK);
    }

    private List<JSONObject> jsonifyChatMessages(List<ChatMessage> messages){
        List<JSONObject> messagelist = new ArrayList<JSONObject>();
        for(ChatMessage message: messages){
            JSONObject msg = new JSONObject();
            msg.put("msgid", message.getMessageId());
            User user = this.userRepository.findByName("test");
          //  User user = this.userRepository.findById(message.getSenderid()).get();
            System.out.println(user.getId());
            msg.put("sender", user.getName());
            msg.put("content", message.getMessage());
            msg.put("timestamp", message.getTimestamp());
            messagelist.add(msg);
        }
        return messagelist;
    }
}
