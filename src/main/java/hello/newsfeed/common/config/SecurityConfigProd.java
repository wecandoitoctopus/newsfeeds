package hello.newsfeed.common.config;

import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Profile("!dev") // prod && test
public class SecurityConfigProd {
    @Bean
    SecurityFilterChain security(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable());
        http.authorizeHttpRequests(a -> a
                .requestMatchers("/auth/**").permitAll()
                .anyRequest().authenticated());
        return http.build();
    }
}
