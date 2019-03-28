package io.softserve.goadventures.auth.service;

import com.auth0.jwt.JWT;
import io.softserve.goadventures.user.model.User;
import org.springframework.stereotype.Service;
import java.util.Date;
import static com.auth0.jwt.algorithms.Algorithm.HMAC256;
import static io.softserve.goadventures.auth.models.SecurityConstants.*;

@Service("jwtService")
public class JWTService {
    public JWTService(){}
    /**
     * Get email from token
     */
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

    /**
     * Create token from user email
     */
    public String createToken(User user) {
        return JWT.create()
                .withSubject(user.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC256(SECRET.getBytes()));
    }

    /**
     * Create refresh token from email
     */
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
