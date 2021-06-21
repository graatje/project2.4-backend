package jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.util.Date;

@RestController
@SpringBootApplication
public class Authenticator {
    @RequestMapping(value = "/authenticate")
    public String available() throws UnsupportedEncodingException {
        System.out.println("test");
        String token = Jwts.builder()
                .setSubject("users/TzMUocMF4p")
                .setExpiration(new Date(1300819380))
                .claim("name", "henker")
                .claim("scope", "self groups/admins")
                .signWith(
                        SignatureAlgorithm.HS256,
                        "secret".getBytes("UTF-8")
                )
                .compact();
        return token;
    }
}
