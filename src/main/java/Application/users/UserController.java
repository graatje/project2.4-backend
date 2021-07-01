package Application.users;

import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class UserController {
    private UserRepository userRepository;
    UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("api/register")
    public ResponseEntity register(@RequestParam("username") String username, @RequestParam("password") String password,
                                   @RequestParam("email") String email){
        JSONObject jsonResponse = new JSONObject();
        if(!this.isEmail(email)) {
            jsonResponse.put("message", "e-mail is ongeldig!");
            return new ResponseEntity(jsonResponse, HttpStatus.BAD_REQUEST);

        }else if(userRepository.findByName(username) != null){
            jsonResponse.put("message", "gebruiker met deze naam bestaat al!");
            return new ResponseEntity(jsonResponse, HttpStatus.CONFLICT);
        }else if(userRepository.findByEmail(email) != null){
            jsonResponse.put("message", "gebruiker met dit e-mail adres bestaat al!");
            return new ResponseEntity(jsonResponse, HttpStatus.CONFLICT);
        }else{
            userRepository.save(new User(username, password, email));
            jsonResponse.put("message", "succesvol geregistreerd!");
            return new ResponseEntity(jsonResponse, HttpStatus.OK);
        }
    }

    private boolean isEmail(String text){
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        return matcher.matches();
    }
}
