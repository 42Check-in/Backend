package check_in42.backend.auth.jwt;

import check_in42.backend.auth.exception.TokenException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
@Configuration
public class TokenProvider {

    private final long accessTokenValidateTime;
    private final long refreshTokenValidateTime;
    private final Key secretKey;

    public TokenProvider(@Value("${jwt.access-valid-time}") final long accessTokenValidateTime,
                         @Value("${refresh-valid-time}") final long refreshTokenValidateTime,
                         @Value("${secret-code}") final String secretCode) {
        this.accessTokenValidateTime = accessTokenValidateTime;
        this.refreshTokenValidateTime = refreshTokenValidateTime;
        this.secretKey = generateSecretKey(secretCode);
    }

    private Key generateSecretKey(final String secretCode) {
        final String encodedSecretCode = Base64.getEncoder().encodeToString(secretCode.getBytes());
        return Keys.hmacShaKeyFor(encodedSecretCode.getBytes());
    }

    public String createAccessToken(final String intraId) {
            return createToken(intraId, accessTokenValidateTime);
    }

    public String createRefreshToken(final String intraId) {
        return createToken(intraId, refreshTokenValidateTime);
    }

    private String createToken(String intraId, final long validateTime) {
        final Claims claims = Jwts.claims().setSubject("user");
        claims.put("intraId", intraId);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + validateTime))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    private Claims parseClaim(final String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (MalformedJwtException e) {
            throw new TokenException.NotIssuedTokenException();
        } catch (ExpiredJwtException e) {
            throw new TokenException.ExpiredTokenException();
        }
    }
}
