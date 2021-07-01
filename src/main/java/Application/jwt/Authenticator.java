package Application.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.DefaultJwtSignatureValidator;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import Application.users.User;
import Application.users.UserRepository;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@CrossOrigin
public class Authenticator {
    private String SECRETKEYFILEPATH;
    private UserRepository userRepository;
    public Authenticator(UserRepository userRepository){
        this.userRepository = userRepository;
        this.SECRETKEYFILEPATH = "private.pem";
    }

    @PostMapping(value = "/api/login")
    public ResponseEntity<Object> login(HttpServletResponse response, @RequestParam String name, @RequestParam String password) throws IOException {
        JSONObject jsonResponse = new JSONObject();
        HttpStatus httpStatus;
        if(!checkValid(name, password)){
            jsonResponse.put("message", "ongeldige gebruikersnaam/wachtwoord combinatie!");
            httpStatus = HttpStatus.UNAUTHORIZED;
        }
        else{
            long expiresin = getExpirationTime(10);
            jsonResponse.put("message", "ok");
            jsonResponse.put("expiresIn", expiresin);
            jsonResponse.put("token", this.generateToken(name, expiresin));
            httpStatus = HttpStatus.OK;
        }
        return new ResponseEntity<>(jsonResponse, httpStatus);
    }

    @GetMapping(value = "/api")
    public ResponseEntity<Object> ok(){
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("message", "ok");
        return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
    }
    @PostMapping(value = "/api/isvalidtoken")
    public ResponseEntity checkValidToken(@RequestParam("token") String token){
        JSONObject jsonResponse = new JSONObject();
        if(isValidToken(token)){
            jsonResponse.put("message", "ok");
            return new ResponseEntity(jsonResponse, HttpStatus.OK);
        }else{
            jsonResponse.put("message", "ongeldige token");
            return new ResponseEntity(jsonResponse, HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping(value = "/api/secret")
    public ResponseEntity<Object> loggedin(@RequestHeader HttpHeaders headers){
        String token = "";
        JSONObject jsonResponse = new JSONObject();
        HttpStatus httpStatus = null;
        try {
            // { "Authorization": "Bearer here.some.token" }
            token = headers.get("Authorization").get(0).split(" ")[1];
        }
        catch (Exception e){
            httpStatus = HttpStatus.BAD_REQUEST;
        }

        if(httpStatus == HttpStatus.BAD_REQUEST){
            jsonResponse.put("message", "error, ongeldig format.");
        }
        else if(isValidToken(token)){
            jsonResponse.put("message", "Succes! Je kan dit niet zien zonder token");
            httpStatus = HttpStatus.OK;
        }
        else{
            jsonResponse.put("message", "ongeldige token");
            httpStatus = HttpStatus.UNAUTHORIZED;
        }
        return new ResponseEntity<>(jsonResponse, httpStatus);
    }

    private boolean checkValid(String username, String password){
        // @todo verify username/password combination.
        User user;
        if(this.isEmail(username)){
            user = this.userRepository.findByEmail(username);
        }
        else {
            user = this.userRepository.findByName(username);
        }
        if(user != null){
            return user.getPassword().equals(password);
        }
        return false;
    }

    /**
     *
     * @param days, the amount of days after which the token should expire.
     * @return long current time in seconds + days in seconds.
     */
    private long getExpirationTime(int days){
        long currentTime = System.currentTimeMillis();  // current time in milliseconds.
        return 86400000L * days + currentTime;
    }

    public String getNameFromJWT(String token){
        if(isValidToken(token)){
            Base64.Decoder decoder = Base64.getDecoder();
            String[] chunks = token.split("\\.");
            String payload = new String(decoder.decode(chunks[1]));
            System.out.println(payload);
            Pattern pattern = Pattern.compile("(?<=\"name\":\")([A-Za-z0-9]+)(?=\")", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(payload);
            if(matcher.find()) {
                return matcher.group();
            }else{
                return "";
            }
        }
        return "";
    }

    private String generateToken(String name, long expiresintimestamp) throws UnsupportedEncodingException {
        if(this.isEmail(name)){
            name = this.userRepository.findByEmail(name).getName();
        }
        String token = Jwts.builder()
                .setExpiration(new Date(expiresintimestamp))
                .claim("name", name)
                .signWith(
                        SignatureAlgorithm.HS256,
                        this.getSecretKey().getBytes("UTF-8")
                )
                .compact();
        return token;
    }
    private boolean isValidToken(String token){
        try {
            String[] chunks = token.split("\\.");
            Base64.Decoder decoder = Base64.getDecoder();

            String header = new String(decoder.decode(chunks[0]));
            String payload = new String(decoder.decode(chunks[1]));
            // won't get it from the header since we only use this encryption algorithm.
            // if its not this algorithm its not ours.
            SignatureAlgorithm sa = SignatureAlgorithm.HS256;
            String secretKey = getSecretKey();
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), sa.getJcaName());
            String tokenWithoutSignature = chunks[0] + "." + chunks[1];
            String signature = chunks[2];
            DefaultJwtSignatureValidator validator = new DefaultJwtSignatureValidator(sa, secretKeySpec);

            if (!validator.isValid(tokenWithoutSignature, signature)) {
                return false;
            } else {
                return true;
            }
        }
        catch (Exception e){
            return false;
        }
    }
    private String getSecretKey(){

        try {
            String secretKey = "";
            File myObj = new File(this.SECRETKEYFILEPATH);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                secretKey +=  myReader.nextLine();
            }
            myReader.close();
            return secretKey;
        } catch (FileNotFoundException e) {
            System.out.println("check file path of private key.");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param text
     * @return boolean, true if the given text is an email
     */
    private boolean isEmail(String text){
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        return matcher.matches();
    }
}
