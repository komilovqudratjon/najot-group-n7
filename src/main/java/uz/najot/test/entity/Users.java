package uz.najot.test.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;
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
public class Users implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Roles> role;
    private boolean active = true;
    @OneToMany(mappedBy = "users")
    @JsonIgnore
    private List<Attachment> attachment;
}
