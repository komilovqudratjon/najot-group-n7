package uz.najot.test.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * @description: TODO
 * @date: 26 March 2024 $
 * @time: 7:21 PM 34 $
 * @author: Qudratjon Komilov
 */
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RefreshToken implements Serializable {
    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(columnDefinition = "TEXT")
    private String refreshToken;
    @Column(columnDefinition = "TEXT")
    private String accessToken;
    private long expireRefreshToken;
    private long expireAccessToken;

    private Long userId;
}
