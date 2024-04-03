package uz.najot.test.service;

import org.springframework.stereotype.Service;
import uz.najot.test.entity.Users;

/**
 * @description: TODO
 * @date: 26 March 2024 $
 * @time: 7:11 PM 34 $
 * @author: Qudratjon Komilov
 */

public interface UserDB {

    Users getUserById(Long id);
}
