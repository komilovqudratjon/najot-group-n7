package uz.najot.test.confing;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import uz.najot.test.entity.RefreshToken;
import uz.najot.test.entity.Roles;
import uz.najot.test.model.JwtTokenDTO;
import uz.najot.test.repository.RefreshTokenRepository;

import java.time.Duration;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

/**
 * @description: TODO
 * @date: 26 March 2024 $
 * @time: 1:46 AM 46 $
 * @author: Qudratjon Komilov
 */
@Component
@RequiredArgsConstructor
public class JwtService {

    @Value("${app.jwt.secret}")
    private String secret;

    @Value("${app.jwt.secret.refresh}")
    private String refreshSecret;


    @Value("${app.jwt.secret.expire}")
    private Long expire;

    private final RefreshTokenRepository refreshTokenRepository;

    public String generate(Long userId, String username, Set<Roles> roles, Date now, long hour) {

        return JWT.create()
                .withSubject(String.valueOf(userId))
                .withIssuedAt(now.toInstant())
                .withExpiresAt(now.toInstant().plus(Duration.ofMillis(hour)))
                .withClaim("username", username)
                .withClaim("role",roles.stream().map(roles1 -> roles1.getRole().name()).toList())
                .sign(Algorithm.HMAC256(secret));
    }

    public JwtTokenDTO generateToken(Long userId, String username, Set<Roles> roles) {
        Date now = new Date();

        String accessToken = generate(userId, username, roles, now, expire);
        UUID uuid = UUID.randomUUID();
        String refreshToken = refreshToken(userId, uuid.toString(), now, expire * 2);
        refreshTokenRepository.save(RefreshToken.builder()
                .id(uuid)
                .userId(userId)
                .refreshToken(refreshToken)
                .accessToken(accessToken)
                .expireAccessToken(new Date().getTime() + expire)
                .expireRefreshToken(new Date().getTime() + expire * 2)
                .build());

        return JwtTokenDTO.builder()
                .refreshToken(refreshToken)
                .userId(userId)
                .accessToken(accessToken)
                .expireAccessToken(new Date().getTime() + expire)
                .expireRefreshToken(new Date().getTime() + expire * 2).build();
    }

    public JwtTokenDTO generateRefresh(UUID uuid, Long userId, String username, Set<Roles> roles) {

        Date now = new Date();
        String accessToken = generate(userId, username, roles, now, expire);
        String refreshToken = refreshToken(userId, uuid.toString(), now, expire * 2);
        refreshTokenRepository.save(RefreshToken.builder()
                .id(uuid)
                .userId(userId)
                .refreshToken(refreshToken)
                .accessToken(accessToken)
                .expireAccessToken(new Date().getTime() + expire)
                .expireRefreshToken(new Date().getTime() + expire * 2)
                .build());

        return JwtTokenDTO.builder()
                .refreshToken(refreshToken)
                .userId(userId)
                .accessToken(accessToken)
                .expireAccessToken(new Date().getTime() + expire)
                .expireRefreshToken(new Date().getTime() + expire * 2).build();
    }

    public String refreshToken(Long userId, String tokenId, Date now, long hour) {
        return JWT.create()
                .withSubject(tokenId)
                .withIssuedAt(now.toInstant())
                .withExpiresAt(now.toInstant().plus(Duration.ofMillis(hour)))
                .withClaim("userId", userId)
                .sign(Algorithm.HMAC256(refreshSecret));
    }

    public DecodedJWT decode(String token) {
        return JWT.require(Algorithm.HMAC256(secret))
                .build()
                .verify(token);
    }

    public DecodedJWT decodeRefresh(String token) {
        return JWT.require(Algorithm.HMAC256(refreshSecret))
                .build()
                .verify(token);
    }

}
