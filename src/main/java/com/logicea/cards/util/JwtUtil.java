package com.logicea.cards.util;

import com.logicea.cards.domain.entity.User;
import com.logicea.cards.dto.LoginResponseDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import jakarta.servlet.http.HttpServletRequest;

import javax.crypto.spec.SecretKeySpec;

@Component
public class JwtUtil {

    private final JwtParser jwtParser;

    private static final String SECRET_KEY = "3723f9a0eioeu1iu2oi12wdjoi2ueoielqsl8b7a4e4a190231jwiowekwjckewnineroicjei3238eu12oeuw9b0a5b5a4a0a0a0a";
    private static final Long ACCESS_TOKEN_VALIDITY = 3600L;

    public JwtUtil() {
        SecretKeySpec secretKeySpec = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS256.getJcaName());
        this.jwtParser = Jwts.parser()
                .setSigningKey(secretKeySpec)
                .build();
    }

    public LoginResponseDto generateResponse(User user) {
        String token = createToken(user);
        return LoginResponseDto.builder()
                .token(token)
                .expiresIn(ACCESS_TOKEN_VALIDITY)
                .build();
    }

    public String createToken(final User user) {
        long currentTimeMillis = System.currentTimeMillis();
        return Jwts.builder()
                .subject(user.getEmail())
                .claim("email", user.getEmail())
                .claim("roles", user.getRole())
                .issuedAt(new Date(currentTimeMillis))
                .expiration(new Date(currentTimeMillis + TimeUnit.SECONDS.toMillis(ACCESS_TOKEN_VALIDITY)))
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims resolveClaims(HttpServletRequest req) {
        Claims claims = null;
        try {
            String token = resolveToken(req);
            if (token != null) {
                claims = parseJwtClaims(token);
            }
            return claims;
        } catch (ExpiredJwtException ex) {
            req.setAttribute("expired", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            req.setAttribute("invalid", ex.getMessage());
            throw ex;
        }
    }

    private Claims parseJwtClaims(final String token) {
        return jwtParser.parseClaimsJws(token).getBody();
    }

    public String resolveToken(final HttpServletRequest request) {

        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateClaims(Claims claims) throws AuthenticationException {
        try {
            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            throw e;
        }
    }

    public String getEmail(Claims claims) {
        return claims.getSubject();
    }

    public String getRole(Claims claims) {
        return (String) claims.get("roles");
    }
}
