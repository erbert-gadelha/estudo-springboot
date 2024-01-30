package com.example.estudo.estudo.configuration;

import com.example.estudo.estudo.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserRepository userRepository;
    private static final String [] patterns =
            new String[] {
                "/public/**",
                "/static/**",
                "/api/**",
                "/login",
                "/register",
                "/",
                    "/yourpage"
            };
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        //http.authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll());

        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(patterns).permitAll()
                        //.requestMatchers(patterns).permitAll()
                        .anyRequest().authenticated()

                )
                //.csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(authz -> authz
                    .loginPage("/login").permitAll()                    // Página de login personalizada
                    .loginProcessingUrl("/api/v1/auth/authenticate")    // Endpoint de processamento de login
                    .defaultSuccessUrl("/")                              // Página para redirecionar após um login bem-sucedido
                    .failureUrl("/login?error")      // Página para redirecionar após um login falhado
                )
                //.formLogin(form -> form.loginPage("/login").permitAll())
                //.formLogin(withDefaults())
                .cors(withDefaults())
                .logout(logout -> logout.logoutSuccessUrl("/").permitAll())
                .httpBasic(withDefaults());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {

        /*return username -> userRepository
                .findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));*/

        User.UserBuilder userBuilder  = User.builder();

        UserDetails user = userBuilder
                .username("user")
                .password(passwordEncoder().encode("secret"))
                .roles("USER")
                .build();

        UserDetails admin = userBuilder
                .username("admin")
                .password(passwordEncoder().encode("secret"))
                .roles("ADMIN","USER")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}
