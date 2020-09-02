package com.voronkov.testrestapp.security.jwt;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**Jwt token filter - pass the token from the request to the authentication manager and set the application security context
 * @author A.Voronkov
 * @since 29.08.2020
 * @version 1.0
 */
public class JwtTokenFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) servletRequest);
        try {
            if (token != null && jwtTokenProvider.validateToken(token)) {
                SecurityContextHolder.getContext().setAuthentication(jwtTokenProvider.getAuthentication(token));
            }
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
