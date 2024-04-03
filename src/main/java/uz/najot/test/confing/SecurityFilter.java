package uz.najot.test.confing;

import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.najot.test.entity.Roles;
import uz.najot.test.entity.Users;
import uz.najot.test.enums.RoleName;
import uz.najot.test.service.AuthService;
import uz.najot.test.service.UserDB;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @description: TODO
 * @date: 25 March 2024 $
 * @time: 6:19 PM 35 $
 * @author: Qudratjon Komilov
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final UserDB userDB;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("i am working");

        // log request
//        request.getHeaderNames().asIterator().forEachRemaining(headerName -> {
//            log.info("Header name: {}, value: {}", headerName, request.getHeader(headerName));
//        });

        // step 1 get token from request
        Optional<String> token = getTokenFromRequest(request);
        if (token.isPresent()) {
            DecodedJWT decode = jwtService.decode(token.get());
//            Long id = decode.getClaim("id").asLong();
            Users userById = userDB.getUserById(Long.valueOf(decode.getSubject()));
            // step 3 get principalUser from decoded token
            userById.getRole().forEach(roles -> log.info("Role: {}", roles.getRole()));

            PrincipleUser user = PrincipleUser.builder()
                    .id(Long.valueOf(decode.getSubject()))
                    .username(userById.getUsername())
                    .password(userById.getPassword())
                    .roles(userById.getRole())
                    .active(userById.isActive())
                    .build();

            // step 4 check user
            SecurityContextHolder.getContext().setAuthentication(new UserTokenAuth(user));
        }
        filterChain.doFilter(request, response);


    }

    public Optional<String> getTokenFromRequest(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        if (bearer != null && bearer.startsWith("Bearer ")) {
            return Optional.of(bearer.substring(7));
        }
        return Optional.empty();
    }

    public Optional<String> getTokenFromRequest1(HttpServletRequest request) {
        String bearer = request.getHeader("tem-uuid");
        if (bearer != null && bearer.startsWith("Bearer ")) {
            return Optional.of(bearer.substring(7));
        }
        return Optional.empty();
    }

}
