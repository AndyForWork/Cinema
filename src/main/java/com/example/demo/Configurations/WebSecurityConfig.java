package com.example.demo.Configurations;

import com.example.demo.CustomAccessDeniedHandler;
import com.example.demo.CustomAuthenticationEntryPoint;
import com.example.demo.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig{


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .csrf()
                    .disable()
                .authorizeHttpRequests(
                        authorize -> {
                            try {
                                authorize
                                        .requestMatchers("/registration").permitAll()
                                        .requestMatchers("/films/all").permitAll()
                                        .requestMatchers("/film/get/**").hasRole("USER")
                                        .requestMatchers("/search").permitAll()
                                        .requestMatchers("/film/**").hasRole("ADMIN")
                                        .requestMatchers("/hall/**").hasRole("ADMIN")
                                        .requestMatchers("/genre/**").hasRole("ADMIN")
                                        .requestMatchers("/staff/**").hasRole("ADMIN")
                                        .requestMatchers("/session/**").hasRole("ADMIN")
                                        .requestMatchers("/cinema/all").permitAll()
                                        .requestMatchers("/cinema/**").hasRole("ADMIN")
                                        .requestMatchers("/admin/**").hasRole("ADMIN")
                                        .anyRequest().authenticated()
                                        .and()
                                        .formLogin()
                                        .loginPage("/login")
                                        .defaultSuccessUrl("/search")
                                        .permitAll()
                                        .and()
                                        .logout()
                                        .permitAll()
                                        .logoutSuccessUrl("/search")
                                        /*.and()
                                        .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                                        .and()
                                        .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler())*/;
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                );

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web) -> web.ignoring().requestMatchers("/resources/**");
    }

}
