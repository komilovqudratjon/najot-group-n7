package uz.najot.test.componet;

import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.najot.test.entity.Roles;
import uz.najot.test.entity.Users;
import uz.najot.test.enums.RoleName;
import uz.najot.test.repository.RoleRepository;
import uz.najot.test.repository.UserRepository;

import java.util.List;
import java.util.Set;

/**
 * @description: TODO
 * @date: 28 March 2024 $
 * @time: 5:52 PM 43 $
 * @author: Qudratjon Komilov
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    private final Lorem lorem = LoremIpsum.getInstance();

    @Override
    public void run(String... args) throws Exception {
        log.info("DataLoader is working");

        if (roleRepository.count() == 0) {
            for (RoleName roleName : RoleName.values()) {
                roleRepository.save(new Roles(roleName));
            }
            log.info("role data is loaded");
        }

        if (userRepository.count() == 0) {
            log.info("DataLoader is working");
            for (int i = 0; i < 3; i++) {
                Users users = Users.builder()
                        .username(lorem.getEmail())
                        .firstName(lorem.getFirstName())
                        .lastName(lorem.getLastName())
                        .role(Set.of(new Roles(RoleName.ROLE_USER)))
                        .password(passwordEncoder.encode("password"))
                        .build();

                userRepository.save(users);
            }
            log.info("user data is loaded");
        }

        log.info("DataLoader is finished");
    }
}
