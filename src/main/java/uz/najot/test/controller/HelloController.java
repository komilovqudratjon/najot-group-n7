package uz.najot.test.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.najot.test.confing.PrincipleUser;
import uz.najot.test.entity.Users;
import uz.najot.test.exceptions.UserNotFoundEx;
import uz.najot.test.repository.UserRepository;
import uz.najot.test.service.AuthService;

import java.util.Optional;

/**
 * @description: TODO
 * @date: 25 March 2024 $
 * @time: 6:02 PM 40 $
 * @author: Qudratjon Komilov
 */
@RestController
@RequestMapping("/gratings")
@RequiredArgsConstructor
@Slf4j
public class HelloController {

    private final AuthService authService;

    private final UserRepository userRepository;


    @GetMapping("/hello")
    public String login() {
        return "Hello World!";
    }

    @GetMapping("/admin")
    public String admin() {
        return "Hello World!";
    }

    @GetMapping("/user")
//    @PreAuthorize("hasRole('ROLE_USER')")
    public String user(@RequestParam String username) {
        userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundEx("User not found"));
        return "Hello World!";
    }


}
