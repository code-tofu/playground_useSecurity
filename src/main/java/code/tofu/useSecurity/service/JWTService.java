package code.tofu.useSecurity.service;

import code.tofu.useSecurity.exception.InvalidJWTException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.security.Key;
import java.util.Date;
import java.util.Optional;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import code.tofu.useSecurity.entity.UserDetailsImpl;

@Service
@Slf4j
public class JWTService {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String ISSUER_NAME = "tofucode";


    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${jwt.expiration.ms}")
    private long expireTimeMs;

    @Value("${jwt.refresh-expiration.ms:604800000}")
    private long refreshExpireTimeMs;

    public Claims extractAndValidateToken(HttpServletRequest request) {
        try {
            final String authHeader = request.getHeader(AUTHORIZATION_HEADER);
            if(null == authHeader || !authHeader.startsWith(BEARER_PREFIX)){
                throw new InvalidJWTException("Token does not start with Bearer Prefix");
            }
            String jwtToken = authHeader.substring(7);

            Claims jwtClaims = Jwts.parserBuilder()
                    .requireIssuer(ISSUER_NAME)
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(jwtToken).getBody();

            if (null == jwtClaims.getSubject()) {
                throw new InvalidJWTException("Jwt Token Subject is null");
            }

            return jwtClaims;
        } catch (JwtException | InvalidJWTException exception){
            log.info("JWT Parse Exception - {}: {}",exception.getClass(), exception.getMessage());
//            throw new ServletException("ServletException during JWT Parsing:", exception);
        /*
        UnsupportedJwtException – if the claimsJws argument does not represent Claims JWS
        MalformedJwtException – if the claimsJws string is not a valid JWS
        SignatureException – if the claimsJws JWS signature validation fails
        ExpiredJwtException – if the specified JWT is a Claims JWT and the Claims has an expiration time before the time this method is invoked.
        // JWT will already throw  JWT Parse Exception: JWT expired at 2024-05-19T04:55:31Z,
        IllegalArgumentException – if the claimsJws string is null or empty or only whitespace
        MissingClaimException
        IncorrectClaimException
        */
        }
        return null;
    }

    private Key getSecretKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(UserDetailsImpl user) {
        log.info("Generating Token for UserId:{}",user.getUserId());
        Claims claims = new DefaultClaims();
        claims.putIfAbsent("role",user.getRole());
        return buildToken(user,claims, expireTimeMs);
    }
    public String generateRefreshToken(UserDetailsImpl user) {
        log.info("Generating Refresh Token for UserId:{}",user.getUserId());
        Claims claims = new DefaultClaims();
        return buildToken(user, claims, refreshExpireTimeMs);
    }

    public String buildToken(UserDetailsImpl user, Claims claims, Long expiration){
        return Jwts
                .builder()
                .setClaims(claims) //overrides setSubject, since setSubject is a convenience method
                .setSubject(Long.toString(user.getUserId())) //use UserId instead, more secure, more performant since PRIKEY
                .setIssuer(ISSUER_NAME)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }

}
