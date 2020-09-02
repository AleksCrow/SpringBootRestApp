package com.voronkov.testrestapp.configuration;

import com.voronkov.testrestapp.security.jwt.JwtAuthenticationEntryPoint;
import com.voronkov.testrestapp.security.jwt.JwtTokenFilter;
import com.voronkov.testrestapp.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**Class for spring security configuration
 * @author A.Voronkov
 * @since 29.08.2020
 * @version 1.0
 */
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    private final JwtAuthenticationEntryPoint unauthorizedHandler;

    private static final String LOGIN_ENDPOINT = "/login";

    @Autowired
    public SecurityConfiguration(JwtTokenProvider jwtTokenProvider, JwtAuthenticationEntryPoint unauthorizedHandler) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.unauthorizedHandler = unauthorizedHandler;
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
                .and()
                .authorizeRequests()
                .antMatchers(LOGIN_ENDPOINT).permitAll()
                .antMatchers("/v2/api-docs",
                        "/*.html",
                        "/webjars/**",
                        "/swagger-resources/**",
                        "/configuration/**").permitAll()
                .anyRequest().authenticated();

        http.addFilterBefore(new JwtTokenFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.csrf().disable().cors().disable();
   }
}
