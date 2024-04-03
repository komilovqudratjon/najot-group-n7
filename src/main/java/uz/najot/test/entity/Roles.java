package uz.najot.test.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.najot.test.enums.RoleName;

import java.io.Serializable;

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
//@Table(name = "roles",uniqueConstraints = {
//        @UniqueConstraint(columnNames = "role")
//})
public class Roles implements Serializable {
    @Id
    @Enumerated(EnumType.STRING)
    private RoleName role;
}
