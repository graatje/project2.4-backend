package Application.chat;

import Application.jwt.Authenticator;
import Application.users.User;
import Application.users.UserRepository;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("chatroom/sendmessage")
    public ResponseEntity sendMessage(@RequestParam("jwt") String webtoken, @RequestParam("chatroomid") int chatroomid,
                                      @RequestParam("message") String message){
        Authenticator auth = new Authenticator(userRepository);
        String name = auth.getNameFromJWT(webtoken);
        if(name.equals("")){
            return new ResponseEntity("no name found in web token", HttpStatus.NOT_FOUND);
        }
        User user = userRepository.findByName(name);
        ChatMessage chatMessage = new ChatMessage(user.getId(), chatroomid, message, System.currentTimeMillis());
        repository.save(chatMessage);
        System.out.println("OK!");
        return new ResponseEntity(HttpStatus.OK);
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
            //User user = this.userRepository.findByName("test");
            User user = this.userRepository.findById(message.getSenderid()).get();
            System.out.println(user.getId());
            msg.put("sender", user.getName());
            msg.put("content", message.getMessage());
            msg.put("timestamp", message.getTimestamp());
            messagelist.add(msg);
        }
        return messagelist;
    }
}
