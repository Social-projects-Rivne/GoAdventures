package io.softserve.goadventures.services;

import com.auth0.jwt.JWT;
import org.springframework.stereotype.Service;
import java.util.Date;
import static com.auth0.jwt.algorithms.Algorithm.HMAC256;
import static io.softserve.goadventures.utils.SecurityConstants.*;

@Service("jwtService")
public class JWTService {
    public JWTService(){}

    public String parseToken(String token) {
        String userEmail = JWT.require(HMAC256(SECRET.getBytes()))
                .build()
                .verify(token.replace(TOKEN_PREFIX, ""))
                .getSubject();
        if (userEmail != null) {
            return userEmail;
        }
        else {
            return null;
        }
    }

    public String createToken(String email) {
        return JWT.create()
                .withSubject(email)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC256(SECRET.getBytes()));
    }

    public String refreshToken(String email) {
        return JWT.create()
                .withSubject(email)
                .withExpiresAt(new Date(System.currentTimeMillis() + REFRESH_TIME))
                .sign(HMAC256(REFRESH_SECRET.getBytes()));
    }

    public String parseRefreshToken(String token) {
        String userEmail = JWT.require(HMAC256(REFRESH_SECRET.getBytes()))
                .build()
                .verify(token)
                .getSubject();
        if (userEmail != null) {
            return userEmail;
        }
        else {
            return null;
        }
    }
}
