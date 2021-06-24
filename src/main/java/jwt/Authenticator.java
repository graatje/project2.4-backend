package jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.DefaultJwtSignatureValidator;
import net.minidev.json.JSONObject;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import users.User;
import users.UserRepository;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

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

    @RequestMapping(value = "/api")
    public ResponseEntity<Object> ok(){
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("message", "ok");
        return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
    }

    @PostMapping(value = "/api/login")
    public ResponseEntity<Object> login(HttpServletResponse response, @RequestParam String name, @RequestParam String password) throws IOException {
        JSONObject jsonResponse = new JSONObject();
        HttpStatus httpStatus;
        if(!checkValid(name, password)){
            jsonResponse.put("message", "invalid username/password combination.");
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
            jsonResponse.put("message", "error, invalid format.");
        }
        else if(isValidToken(token)){
            jsonResponse.put("message", "Success! You can not see this without a token");
            httpStatus = HttpStatus.OK;
        }
        else{
            jsonResponse.put("message", "invalid token");
            httpStatus = HttpStatus.UNAUTHORIZED;
        }
        return new ResponseEntity<>(jsonResponse, httpStatus);
    }

    private boolean checkValid(String username, String password){
        // @todo verify username/password combination.
        User user =  this.userRepository.findByName(username);
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
    private String generateToken(String name, long expiresintimestamp) throws UnsupportedEncodingException {
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
}
