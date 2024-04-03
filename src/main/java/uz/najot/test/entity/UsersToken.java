package uz.najot.test.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

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
public class UsersToken implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    private String code;

    private int count;

    private String username;
    private String firstName;
    private String lastName;
    private String password;
}
