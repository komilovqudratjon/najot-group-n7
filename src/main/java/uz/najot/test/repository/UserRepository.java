package uz.najot.test.repository;

import org.reactivestreams.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.najot.test.entity.Users;

import java.util.Optional;

/**
 * @description: TODO
 * @date: 26 March 2024 $
 * @time: 7:54 PM 45 $
 * @author: Qudratjon Komilov
 */
@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUsername(String username);

    boolean existsByUsername(String username);
}
