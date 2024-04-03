package uz.najot.test.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import uz.najot.test.entity.Roles;
import uz.najot.test.enums.RoleName;

import java.io.Serializable;
import java.util.List;

/**
 * @description: TODO
 * @date: 26 March 2024 $
 * @time: 1:02 AM 00 $
 * @author: Qudratjon Komilov
 */

@Builder
@Getter
public class UserInfoDTO implements Serializable {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private List<String> role;
}
