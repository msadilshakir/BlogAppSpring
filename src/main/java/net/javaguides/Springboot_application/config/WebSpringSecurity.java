package net.javaguides.Springboot_application.config;


import net.javaguides.Springboot_application.repository.UserRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSpringSecurity {
    private UserDetailsService userDetailsService;

    public WebSpringSecurity(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // CSRF Configuration
                .csrf(csrf -> csrf.disable()) // Disable CSRF protection if not needed

                // Authorization Configuration
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/resources/**", "/register/**").permitAll() // Public paths
                        .requestMatchers("/admin/**").hasRole("ADMIN") // Restricted to ADMIN role
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/page/**").permitAll()
                        .requestMatchers("/comments/**").permitAll()// Allow public access to home
                        .requestMatchers("/post/**").permitAll() // Public access to posts
                        .anyRequest().hasAnyRole("ADMIN", "GUEST") // Restrict all other requests
                )

                .sessionManagement(sessionManagement ->
                        sessionManagement
                                .invalidSessionUrl("/login?error=sessionExpired") // Redirect for invalid sessions
                                .sessionFixation().migrateSession() // Prevent session fixation attacks
                )

                // Login Configuration
                .formLogin(form ->
                        form
                                .loginPage("/login") // Custom login page
                                .defaultSuccessUrl("/") // Redirect after successful login
                                .loginProcessingUrl("/login") // Endpoint for login form submission
                                .permitAll() // Allow everyone to access login-related endpoints
                )

                // Logout Configuration
                .logout(logout ->
                        logout
                                .logoutUrl("/logout") // URL to trigger logout
                                .logoutSuccessUrl("/login?logout") // Redirect URL after successful logout
                                .deleteCookies("JSESSIONID") // Remove JSESSIONID cookie
                                .invalidateHttpSession(true) // Invalidate the session
                                .clearAuthentication(true) // Clear authentication
                                .permitAll() // Allow everyone to access logout
                );

        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder builder) throws Exception{
        builder.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());

    }

    public static boolean isUserLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return false;
        }
        return true;
    }

}



