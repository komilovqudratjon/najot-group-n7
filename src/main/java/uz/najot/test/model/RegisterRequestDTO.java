package uz.najot.test.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;

/**
 * @description: TODO
 * @date: 26 March 2024 $
 * @time: 1:02 AM 00 $
 * @author: Qudratjon Komilov
 */

@Builder
@Getter
public class RegisterRequestDTO {
    @NotNull
    @Email
    private String username;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$", message = "password must be at least 8 characters and contain at least one uppercase letter, one lowercase letter and one number")
    private String password;
}
