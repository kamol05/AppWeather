package com.example.appweather.config;


import com.example.appweather.microservice.config.CustomAuthenticationManager;
import com.example.appweather.microservice.config.CustomFilter;
import com.example.appweather.model.Role;
import com.example.appweather.security.JwtTokenFilter;
import com.example.appweather.security.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private JwtTokenFilter jwtTokenFilter;
    private CustomFilter customFilter;
    private UserDetailsServiceImpl userDetailsService;
    private CustomAuthenticationManager customAuthenticationManager;
    public SecurityConfig(JwtTokenFilter jwtTokenFilter, CustomFilter customFilter, UserDetailsServiceImpl userDetailsService, CustomAuthenticationManager customAuthenticationManager) {
        this.jwtTokenFilter = jwtTokenFilter;
        this.customFilter = customFilter;
        this.userDetailsService = userDetailsService;
        this.customAuthenticationManager = customAuthenticationManager;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)

                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/auth/login").permitAll()

                .antMatchers("/users").hasAnyAuthority(Role.ADMIN.getName())
                .antMatchers("/user").hasAnyAuthority(Role.ADMIN.getName(),Role.USER.getName())

                .antMatchers("/agents").hasAnyAuthority(Role.ADMIN.getName())
                .antMatchers("/agent/**").hasAnyAuthority(Role.AGENT.getName(),Role.ADMIN.getName())
                .anyRequest()
                .authenticated();
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider());
        auth.authenticationProvider(customAuthenticationManager);
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userDetailsService);
        return authenticationProvider;
    }


}
