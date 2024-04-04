package uz.najot.test.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import uz.najot.test.confing.JwtService;
import uz.najot.test.confing.PrincipleUser;
import uz.najot.test.entity.*;
import uz.najot.test.enums.RoleName;
import uz.najot.test.model.*;
import uz.najot.test.repository.AttachmentRepository;
import uz.najot.test.repository.RefreshTokenRepository;
import uz.najot.test.repository.UserRepository;
import uz.najot.test.repository.UserTokenRepository;

import java.util.*;

/**
 * @description: TODO
 * @date: 26 March 2024 $
 * @time: 7:11 PM 34 $
 * @author: Qudratjon Komilov
 */
@Service
@RequiredArgsConstructor
@Slf4j
@EnableCaching
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserRepository usersRepository;
    private final UserTokenRepository userTokenRepository;
    private final AttachmentRepository attachmentRepository;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public JwtTokenDTO login(LoginRequestDTO request) {

        // step 1 get user authentication
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        PrincipleUser principleUser = (PrincipleUser) authenticate.getPrincipal();

        // step 2 check user is status
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        // step 3 generate token

        return jwtService.generateToken(principleUser.getId(), principleUser.getUsername(), principleUser.getRoles());

    }

    @Override
    public String register(RegisterRequestDTO request) {
        String uuid = UUID.randomUUID().toString();



        String code = RandomStringUtils.random(10, false, true);

        if (usersRepository.existsByUsername(request.getUsername())) {
            return "username already exist";
        }

        emailService.sendSimpleMessage(request.getUsername(), "Account activation", "your activation code: " + code);

        UsersToken save = userTokenRepository.save(UsersToken.builder()
                .token(uuid)
                .code(code)
                .lastName(request.getLastName())
                .firstName(request.getFirstName())
                .username(request.getUsername())
                .password(request.getPassword())
                .count(0)
                .build());


        return save.getToken();

    }

    @Override
    public RegisterResponseDTO updateUser(UpdateUserDTO request, PrincipleUser principleUser) throws Exception {
        String oldPassword = request.getOldPassword();
        String newPassword = request.getNewPassword();
        Optional<Users> byUsername = usersRepository.findByUsername(principleUser.getUsername());
        if (byUsername.isPresent()) {
            Users users = byUsername.get();
            boolean matches = passwordEncoder.matches(oldPassword, users.getPassword());
            if (!matches) {
                throw new Exception("parol yoki username");
            }
            users.setPassword(passwordEncoder.encode(newPassword));
            usersRepository.save(users);
        }

        return null;
    }

    @Override
    public JwtTokenDTO verify(String code, String token) {
        Optional<UsersToken> byToken = userTokenRepository.findByToken(token);
        if (byToken.isPresent()) {
            UsersToken usersToken = byToken.get();
            if (usersToken.getCount() > 3) {
                return null;
            }

            if (usersToken.getCode().equals(code)) {
                Users user = Users.builder()
                        .username(usersToken.getUsername())
                        .password(passwordEncoder.encode(usersToken.getPassword()))
                        .firstName(usersToken.getFirstName())
                        .lastName(usersToken.getLastName())
                        .active(true)
                        .role(Set.of(new Roles(RoleName.ROLE_USER)))
                        .build();
                usersRepository.save(user);
                userTokenRepository.deleteByUsername(usersToken.getUsername());

                return jwtService.generateToken(user.getId(), user.getUsername(), user.getRole());
            } else {
                usersToken.setCount(usersToken.getCount() + 1);
                userTokenRepository.save(usersToken);
                return null;

            }
        }
        return null;
    }

    @Override
    public List<FileDTO> getProfilePhoto(PrincipleUser principleUser) {
        List<Attachment> allByUsersUsername = attachmentRepository.findAllByUsersUsername(principleUser.getUsername());

        List<FileDTO> fileDTOList = new ArrayList<>();
        allByUsersUsername.forEach(attachment -> {
            fileDTOList.add(FileDTO.builder()
                    .id(attachment.getId())
                    .name(attachment.getName())
                    .size(attachment.getSize())
                    .contentType(attachment.getContentType())
                    .extension(attachment.getExtension())
                    .url(ServletUriComponentsBuilder.fromCurrentContextPath().path("/attachment/download/" + attachment.getId()).toUriString())
                    .build());
        });


        return fileDTOList;
    }

    @Override
    public Users me(PrincipleUser principleUser) {
        Optional<Users> byUsername = usersRepository.findByUsername(principleUser.getUsername());
        return byUsername.orElse(null);
    }

    @Override
    public JwtTokenDTO refresh(String token) {
        DecodedJWT decode = jwtService.decode(token);
        String subject = decode.getSubject();
        RefreshToken referenceById = refreshTokenRepository.getReferenceById(UUID.fromString(subject));
        long time = new Date().getTime();
        long expireRefreshToken = referenceById.getExpireRefreshToken();
        Users byId = usersRepository.findById(referenceById.getUserId()).orElse(null);
        if (expireRefreshToken > time) {
            return jwtService.generateRefresh(referenceById.getId(), referenceById.getUserId(), byId.getUsername(), byId.getRole());
        }
        return null;
    }

}
