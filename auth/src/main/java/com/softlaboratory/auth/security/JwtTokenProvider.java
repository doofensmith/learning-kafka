package com.softlaboratory.auth.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.spec.SecretKeySpec;
import java.io.Serializable;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider implements Serializable {

    private static final long serialVersionUID = -7535147790025631190L;

    @Value("${jwt.token.validity:600000}")
    private long validity;

    @Value("${jwt.token.authorities.key:roles}")
    private String authKey;

    @Value("${jwt.token.signature.key.secret}")
    private String secretKey;

    private Key key;

    @PostConstruct
    private void init() {
        final byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        key = new SecretKeySpec(keyBytes, 0, keyBytes.length, SignatureAlgorithm.HS256.getJcaName());
    }

    public String generateToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(authKey, authorities)
                .signWith(key)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + validity))
                .compact();
    }

    public Authentication getAuthenticationToken(final String token, final Authentication authentication, final UserDetails userDetails) {
        final JwtParser jwtParser = Jwts.parserBuilder()
                .setSigningKey(key)
                .build();

        final Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);

        final Claims claims = claimsJws.getBody();

        final Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get(authKey).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), authorities);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsername(token);
        return (username.equals(userDetails.getUsername()) && !isExpired(token));
    }

    private boolean isExpired(String token) {
        final Date expirationDate = getExpirationDate(token);
        return expirationDate.before(new Date());
    }

    public String getUsername(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    private Date getExpirationDate(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
