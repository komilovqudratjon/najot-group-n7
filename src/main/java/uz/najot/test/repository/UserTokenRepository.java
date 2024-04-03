package uz.najot.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.najot.test.entity.Users;
import uz.najot.test.entity.UsersToken;

import java.util.Optional;

/**
 * @description: TODO
 * @date: 26 March 2024 $
 * @time: 7:54 PM 45 $
 * @author: Qudratjon Komilov
 */
@Repository
public interface UserTokenRepository extends JpaRepository<UsersToken, Long> {
    Optional<UsersToken> findByToken(String token);

    void deleteByUsername(String username);
}
