package uz.najot.test.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import uz.najot.test.confing.PrincipleUser;
import uz.najot.test.entity.Users;
import uz.najot.test.model.*;
import uz.najot.test.service.AuthService;

import java.util.List;

/**
 * @description: TODO
 * @date: 25 March 2024 $
 * @time: 6:02 PM 40 $
 * @author: Qudratjon Komilov
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO request) {
        return authService.login(request);
    }

    @PostMapping("/register")
    public String register(@RequestBody @Valid  RegisterRequestDTO request) {
        return authService.register(request);
    }

    @PostMapping("/verify/{token}/{code}")
    public LoginResponseDTO verify(@PathVariable String code, @PathVariable String token) {
        return authService.verify(code, token);
    }

    @GetMapping("/profile-photo")
    public List<FileDTO> getProfilePhoto(@AuthenticationPrincipal PrincipleUser principleUser) {
        return authService.getProfilePhoto(principleUser);
    }

    @PutMapping("/update-user")
    public RegisterResponseDTO updateUser(@RequestBody UpdateUserDTO request, @AuthenticationPrincipal PrincipleUser principleUser) throws Exception {
        return authService.updateUser(request, principleUser);
    }

    @GetMapping("/me")
    public Users me(@AuthenticationPrincipal PrincipleUser principleUser) {
        return authService.me(principleUser);
    }

}
