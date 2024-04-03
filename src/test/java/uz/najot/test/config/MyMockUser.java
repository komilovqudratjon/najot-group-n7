package uz.najot.test.config;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

/**
 * @description: TODO
 * @date: 02 April 2024 $
 * @time: 8:07 PM 32 $
 * @author: Qudratjon Komilov
 */
@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = SecurityContextFactory.class)
public @interface MyMockUser {
    long id() default 1L;
    String username() default "username";

    String[] role() default {"ROLE_USER"};

}
