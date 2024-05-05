package com.logicea.cards.util;

import com.logicea.cards.domain.entity.User;
import com.logicea.cards.dto.LoginResponseDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtUtil {

    private final JwtParser jwtParser;

    private static final String SECRET_KEY = "3723f9a0eioeu1iu2oi12wdjoi2ueoielqsl8b7a4e4a190231jwiowekwjckewnineroicjei3238eu12oeuw9b0a5b5a4a0a0a0a";
    private static final Long ACCESS_TOKEN_VALIDITY = 3600L;

    public JwtUtil() {
        this.jwtParser = Jwts.parser().setSigningKey(SECRET_KEY);
    }

    public LoginResponseDto generateResponse(User user) {
        String token = createToken(user);
        return LoginResponseDto.builder()
                .token(token)
                .expiresIn(ACCESS_TOKEN_VALIDITY)
                .build();
    }

    public String createToken(final User user) {
        Claims claims = Jwts.claims().setSubject(user.getEmail());
        claims.put("email", user.getEmail());
        claims.put("roles", user.getRole());
        Date tokenCreateTime = new Date();
        Date tokenValidity = new Date(tokenCreateTime.getTime() + TimeUnit.SECONDS.toMillis(ACCESS_TOKEN_VALIDITY));
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getEmail())
                .setIssuedAt(tokenCreateTime)
                .setExpiration(tokenValidity)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
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
