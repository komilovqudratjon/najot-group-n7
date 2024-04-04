package uz.najot.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.najot.test.entity.RefreshToken;
import uz.najot.test.entity.UsersToken;

import java.util.Optional;
import java.util.UUID;

/**
 * @description: TODO
 * @date: 26 March 2024 $
 * @time: 7:54 PM 45 $
 * @author: Qudratjon Komilov
 */
@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {

}
