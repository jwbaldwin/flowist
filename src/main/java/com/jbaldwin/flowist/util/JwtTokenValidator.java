package com.jbaldwin.flowist.util;

import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkException;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.UrlJwkProvider;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.jbaldwin.flowist.domain.JwtUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.security.interfaces.RSAPublicKey;

@Component
public class JwtTokenValidator {

    @Value("${cognito.url}")
    private String url;

    /**
     * Tries to parse specified String as a JWT token. If successful, returns User object with username, id and role prefilled (extracted from token).
     * If unsuccessful (token is invalid or not containing all required user properties), simply returns null.
     *
     * @param token the JWT token to parse
     * @return the User object extracted from specified token or null if a token is invalid.
     */
    public JwtUser parseToken(String token) {
        JwtUser user = null;
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(getPem(token))
                    .parseClaimsJws(token)
                    .getBody();

            user = new JwtUser();

            user.setId((String) body.get("cognito:username"));
            user.setUsername((String) body.get("email"));
            user.setRole((String) body.get("role"));

        } catch (JwtException | JwkException | IOException e) {
            // Simply print the exception and null will be returned
            e.printStackTrace();
        }
        return user;
    }

    public RSAPublicKey getPem(String token) throws IOException, JwkException {
        DecodedJWT jwt = JWT.decode(token);
        JwkProvider provider = new UrlJwkProvider(new URL(url));
        Jwk jwk = provider.get(jwt.getKeyId());
        RSAPublicKey publicKey = (RSAPublicKey) jwk.getPublicKey();
        Algorithm algorithm = Algorithm.RSA256(publicKey,null);

        algorithm.verify(jwt);
        return publicKey;
    }
}

