package org.learning.todo.config;

import org.learning.todo.service.AppUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.concurrent.ConcurrentHashMap;

@Configuration
@EnableWebSecurity
public class Security {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
            .cors(Customizer.withDefaults())
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests((authorize) ->
                    authorize.requestMatchers("/api/auth/register").permitAll()
                            .requestMatchers("/login").permitAll()
                            .anyRequest().authenticated())
            .formLogin(login -> login.loginProcessingUrl("/login"));
    return httpSecurity.build();
  }

  @Bean
  public AppUserDetailService appUserDetailService(PasswordEncoder passwordEncoder) {
    ConcurrentHashMap<String, UserDetails> users = new ConcurrentHashMap<>();
    UserDetails userDetails = User.builder()
            .passwordEncoder(passwordEncoder::encode)
            .username("admin")
            .password("admin")
            .roles("USER","ADMIN")
            .build();
    users.put(userDetails.getUsername(), userDetails);
    return new AppUserDetailService(users, passwordEncoder);

  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
