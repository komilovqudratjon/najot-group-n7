package uz.najot.test.model;

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
public class LoginRequestDTO {
    private String username;
    private String password;
}
