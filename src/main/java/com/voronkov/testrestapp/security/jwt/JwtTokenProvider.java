package com.voronkov.testrestapp.security.jwt;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

/**Defines the contract of a token provider
 * @author A.Voronkov
 * @since 29.08.2020
 * @version 1.0
 */
@Component
public class JwtTokenProvider {

    @Value("${jwt.token.secret}")
    private String secret;

    @Value("${jwt.token.expired}")
    private long validateTokenTime;

    private static final SignatureAlgorithm ALGORITHM = SignatureAlgorithm.HS256;

    private final UserDetailsService userDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Autowired
    public JwtTokenProvider(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public String createToken(String email) {
        Date now = new Date();
        Date validateDate = new Date(now.getTime() + validateTokenTime);

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(validateDate)
                .signWith(ALGORITHM, secret)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getEmail(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

    public boolean validateToken(String token) {
        Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
        if (SignatureAlgorithm.forName(claims.getHeader().getAlgorithm()) != ALGORITHM) {
            return false;
        }
        return true;
    }
}
