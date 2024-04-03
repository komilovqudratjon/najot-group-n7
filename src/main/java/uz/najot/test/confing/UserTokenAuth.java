package uz.najot.test.confing;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @description: TODO
 * @date: 27 March 2024 $
 * @time: 7:52 PM 29 $
 * @author: Qudratjon Komilov
 */
@Slf4j
public class UserTokenAuth extends AbstractAuthenticationToken {

    private final PrincipleUser principleUser;

    public UserTokenAuth(PrincipleUser principleUser) {
        super(principleUser.getAuthorities());
        this.principleUser = principleUser;
        log.info("user active "+principleUser.isActive());
        setAuthenticated(principleUser.isActive());
//        setAuthenticated(true);
    }
    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return principleUser;
    }
}
