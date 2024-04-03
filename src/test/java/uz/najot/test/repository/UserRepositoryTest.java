package uz.najot.test.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import uz.najot.test.entity.Roles;
import uz.najot.test.entity.Users;
import uz.najot.test.enums.RoleName;

import java.util.Set;

/**
 * @description: TODO
 * @date: 02 April 2024 $
 * @time: 6:05 PM 59 $
 * @author: Qudratjon Komilov
 */
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void user_jpa_save() {
        //Arrange

        Users build = Users.builder().username("test")
                .active(true)
                .lastName("testov")
                .firstName("test")
                .password("test")
                .role(Set.of(new Roles(RoleName.ROLE_USER))).build();
        //Act
        Users save = userRepository.save(build);
        //Check
        Assertions.assertNotNull(save, "it should not be null");
    }

    @Test
    void existsByUsername() {
    }
}