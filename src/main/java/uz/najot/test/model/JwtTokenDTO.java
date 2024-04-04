package uz.najot.test.model;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

/**
 * @description: TODO
 * @date: 26 March 2024 $
 * @time: 1:05 AM 44 $
 * @author: Qudratjon Komilov
 */
@Getter
@Builder
public class JwtTokenDTO {
    private String refreshToken;
    private String accessToken;
    private long expireRefreshToken;
    private long expireAccessToken;

    private Long userId;
}
