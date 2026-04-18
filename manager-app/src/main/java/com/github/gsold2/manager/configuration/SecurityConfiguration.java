package com.github.gsold2.manager.configuration;

import com.github.gsold2.manager.entity.StoreUser;
import com.github.gsold2.manager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfiguration {

    @Autowired
    private UserRepository userRepository;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http.authorizeHttpRequests(
                        (authorize) -> authorize
                                .requestMatchers("/login").permitAll()
                                .anyRequest().hasRole("USER")
                )
                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll);
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return userName -> {
            StoreUser storeUser = userRepository
                    .findUserByName(userName)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with userName: " + userName));

            return User.builder()
                    .username(storeUser.getName())
                    .password(storeUser.getPassword())
                    .roles(storeUser.getRolesAsStrings().toArray(new String[0]))
                    .build();
        };
    }
}
