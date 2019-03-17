package com.jbaldwin.flowist.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jbaldwin.flowist.domain.JwtUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

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
                    .setSigningKey(getPem())
                    .parseClaimsJws(token)
                    .getBody();

            user = new JwtUser();
            user.setUsername(body.getSubject());
            user.setId(Long.parseLong((String) body.get("userId")));
            user.setRole((String) body.get("role"));

        } catch (JwtException | IOException e) {
            // Simply print the exception and null will be returned
            e.printStackTrace();
        }
        return user;
    }

    public String getPem() throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();
        String response = restTemplate.getForObject(url, String.class);
//        JsonNode root = mapper.readTree(response.getBody());
//        JsonNode name = root.path("name");
        System.out.println("response = " + response);
        return response.toString();
//        return RestTemplate
//        return requestify.request(jwkUrl, { method: 'get', dataType: 'json'})
//    .then(res => res.getBody()['keys'].shift())
//    .then(jwk => jwkToPem(jwk))
//        ;
    }
}

