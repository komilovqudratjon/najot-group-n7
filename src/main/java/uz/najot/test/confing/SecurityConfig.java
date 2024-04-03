package uz.najot.test.confing;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @description: TODO
 * @date: 25 March 2024 $
 * @time: 7:35 PM 18 $
 * @author: Qudratjon Komilov
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    public final SecurityFilter securityFilter;

    public final CustomUserDetailService customUserDetailService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .addFilterAfter(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf().disable()
                .cors().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()
                .securityMatcher("/**")
                .authorizeHttpRequests(register ->
                        register.
                                requestMatchers("/auth/**").permitAll()
//                                .requestMatchers("/gratings/**").permitAll()
                                .requestMatchers("/attachment/**").permitAll()
                                .requestMatchers("/gratings/user").hasAnyRole("USER","ADMIN")
                                .requestMatchers("/gratings/admin").hasRole("ADMIN")
                                .anyRequest().authenticated()
                );
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        AuthenticationManagerBuilder sharedObject = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        sharedObject
                .userDetailsService(customUserDetailService)
                .passwordEncoder(passwordEncoder());

        return sharedObject.build();
    }

}
