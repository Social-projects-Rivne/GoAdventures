package io.softserve.goadventures.auth.service;

import static io.softserve.goadventures.auth.models.SecurityConstants.EXPIRATION_TIME;
import static io.softserve.goadventures.auth.models.SecurityConstants.SECRET;
import static io.softserve.goadventures.auth.models.SecurityConstants.TOKEN_PREFIX;

import static com.auth0.jwt.algorithms.Algorithm.HMAC256;

import io.softserve.goadventures.user.model.User;


import com.auth0.jwt.JWT;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service("jwtService")
public class JWTService {

  /**
  * Get email from token
  */
  public String parseToken(String token) {  // Change type to boolean
    String userEmail = JWT.require(HMAC256(SECRET.getBytes()))
            .build()
            .verify(token.replace(TOKEN_PREFIX, ""))
            .getSubject();
    if (userEmail != null) {
      return "Verified";                    // Return true
    }
    return null;                            // Return false
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

}
