package uz.najot.test.entity;

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
public class Attachment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String path;
    private String contentType;
    private String extension;
    private Long size;
    @ManyToOne
    private Users users;
}
