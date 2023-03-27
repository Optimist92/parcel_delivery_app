package util;

import exception.JwtTokenMalformedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;


public class JwtUtil {

    private String jwtSecret = "6A576E5A7234753778214125432A462D4A614E645267556B5870327335763879";

    public Claims getClaims(final String token){
        try {
            return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
        } catch (Exception e) {
            throw new JwtTokenMalformedException("JWT parsing issue");
        }
    }

    public String getUsernameFromToken(String token) {
        token = token.substring(7);
        Claims claims = getClaims(token);
        return claims.getSubject();
    }

    public String getPublicIdFromToken(String token) {
        token = token.substring(7);
        Claims claims = getClaims(token);
        return (String) claims.get("PublicId");
    }

    public String getRoleFromToken(String token) {
        token = token.substring(7);
        Claims claims = getClaims(token);
        return (String) claims.get("Role");
    }

    private SecretKey getSignInKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

}