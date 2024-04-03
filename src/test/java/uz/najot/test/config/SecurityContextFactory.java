package uz.najot.test.config;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import uz.najot.test.confing.PrincipleUser;
import uz.najot.test.entity.Roles;
import uz.najot.test.enums.RoleName;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @description: TODO
 * @date: 02 April 2024 $
 * @time: 8:14 PM 01 $
 * @author: Qudratjon Komilov
 */
public class SecurityContextFactory implements WithSecurityContextFactory<MyMockUser> {
    @Override
    public SecurityContext createSecurityContext(MyMockUser annotation) {
        PrincipleUser user = PrincipleUser.builder()
                .id(annotation.id())
                .username(annotation.username())
                .password("password")
                .roles(Arrays.stream(annotation.role()).map(role -> new Roles(RoleName.valueOf(role))).collect(Collectors.toSet()))
                .active(true)
                .build();

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()));
        return context;
    }
}
