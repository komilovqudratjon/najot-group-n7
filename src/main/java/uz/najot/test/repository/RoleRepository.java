package uz.najot.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.najot.test.enums.RoleName;
import uz.najot.test.entity.Roles;

/**
 * @description: TODO
 * @date: 26 March 2024 $
 * @time: 7:54 PM 45 $
 * @author: Qudratjon Komilov
 */
@Repository
public interface RoleRepository extends JpaRepository<Roles, RoleName> {
}
