package uz.najot.test.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;
import uz.najot.test.entity.Users;
import uz.najot.test.repository.UserRepository;
import uz.najot.test.service.UserDB;

import java.util.Date;
import java.util.Optional;

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
public class UserDBImpl implements UserDB {

    private final UserRepository usersRepository;

    @Cacheable(value = "users")
    public Users getUserById(Long id) {
        log.info("postgres is working");
        Date date = new Date();
        Optional<Users> byId = usersRepository.findById(id);
        System.out.println(new Date().getTime()-date.getTime()  );
        return byId.orElse(null);
    }
}
