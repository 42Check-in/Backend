package check_in42.backend.auth.jwt;

import check_in42.backend.auth.exception.AuthorizationException;
import check_in42.backend.auth.exception.TokenException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
@Slf4j
public class TokenProvider {

    private final long accessTokenValidateTime;
    private final long refreshTokenValidateTime;
    private final Key secretKey;

    public TokenProvider(@Value("${jwt.access-valid-time}") final long accessTokenValidateTime,
                         @Value("${jwt.refresh-valid-time}") final long refreshTokenValidateTime,
                         @Value("${jwt.secret-code}") final String secretCode) {
        this.accessTokenValidateTime = accessTokenValidateTime * 100;
        this.refreshTokenValidateTime = refreshTokenValidateTime * 1000;
        this.secretKey = generateSecretKey(secretCode);
    }

    private Key generateSecretKey(final String secretCode) {
        final String encodedSecretCode = Base64.getEncoder().encodeToString(secretCode.getBytes());
        return Keys.hmacShaKeyFor(encodedSecretCode.getBytes());
    }

    public String createAccessToken(final String intraId, final boolean staff, final String grade) {
            return createToken(intraId, staff, grade, accessTokenValidateTime);
    }

    public String createRefreshToken(final String intraId, final boolean staff, final String grade) {
        return createToken(intraId, staff, grade, refreshTokenValidateTime);
    }

    private String createToken(final String intraId, final boolean staff,
                               final String grade, final long validateTime) {
        final Claims claims = Jwts.claims().setSubject("user");
        claims.put("intraId", intraId);
        claims.put("staff", staff);
        claims.put("grade", grade);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + validateTime))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims parseAccessTokenClaim(final String accessToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (MalformedJwtException e) {
            throw new TokenException.NotIssuedTokenException();
        } catch (ExpiredJwtException e) {
            throw new TokenException.ExpiredAccessTokenException();
        }
    }

    public Claims parseRefreshTokenClaim(final String refreshToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(refreshToken)
                    .getBody();
        } catch (MalformedJwtException e) {
            throw new AuthorizationException.RefreshTokenNotFoundException();
        } catch (ExpiredJwtException e) {
            throw new TokenException.ExpiredRefreshTokenException();
        }
    }
}
