package uz.najot.test.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uz.najot.test.entity.UsersToken;
import uz.najot.test.model.RegisterRequestDTO;
import uz.najot.test.repository.UserRepository;
import uz.najot.test.repository.UserTokenRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * @description: TODO
 * @date: 02 April 2024 $
 * @time: 6:25 PM 13 $
 * @author: Qudratjon Komilov
 */
@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    UserTokenRepository userTokenRepository;

    @Mock
    EmailService emailService;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    AuthServiceImpl authService;


    @Test
    void auth_register() {

        //Arrange
        RegisterRequestDTO build = RegisterRequestDTO.builder()
                .username("test")
                .lastName("testov")
                .firstName("test")
                .password("test")
                .build();

        UsersToken build1 = UsersToken.builder().username("test")
                .lastName("testov")
                .firstName("test")
                .password("test")
                .code("351235")
                .token("fhbswekhfbrjgfbrjhyegfbekyejkrekfry").build();


        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userTokenRepository.save(any(UsersToken.class))).thenReturn(build1);
        doNothing().when(emailService).sendSimpleMessage(anyString(), anyString(), anyString());

        //Act
        String register = authService.register(build);

        //Check
        Assertions.assertNotNull(register);

    }
}