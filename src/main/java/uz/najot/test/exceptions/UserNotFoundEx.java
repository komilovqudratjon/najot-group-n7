package uz.najot.test.exceptions;

/**
 * @description: TODO
 * @date: 31 March 2024 $
 * @time: 5:18 PM 38 $
 * @author: Qudratjon Komilov
 */
public class UserNotFoundEx extends RuntimeException {
    public UserNotFoundEx(String message) {
        super(message);
    }
}
