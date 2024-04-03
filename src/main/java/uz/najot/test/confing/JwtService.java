package uz.najot.test.confing;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import uz.najot.test.entity.Roles;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Set;

/**
 * @description: TODO
 * @date: 26 March 2024 $
 * @time: 1:46 AM 46 $
 * @author: Qudratjon Komilov
 */
@Component
public class JwtService {

    @Value("${app.jwt.secret}")
    private String secret;

    public String generate(Long userId, String username, Set<Roles> roles) {
        Instant now = Instant.now();
        return JWT.create()
                .withSubject(String.valueOf(userId))
                .withIssuedAt(now)
                .withExpiresAt(now.plus(Duration.ofHours(1)))
                .withClaim("username", username)
                .withClaim("role",roles.stream().map(roles1 -> roles1.getRole().name()).toList())
                .sign(Algorithm.HMAC256(secret));
    }

    public DecodedJWT decode(String token) {
        return JWT.require(Algorithm.HMAC256(secret))
                .build()
                .verify(token);
    }


}
