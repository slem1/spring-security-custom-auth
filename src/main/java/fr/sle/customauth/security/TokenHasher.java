package fr.sle.customauth.security;

import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Component for hashing token. Algorithm is SHA-256.
 *
 * @author slemoine
 */
@Component
public class TokenHasher {

    private static final String SALT = "$5$aSalt";

    private MessageDigest instance = MessageDigest.getInstance("SHA-256");

    public TokenHasher() throws NoSuchAlgorithmException {
    }


    public String hashToken(String raw) {
        instance.update((SALT + raw).getBytes(StandardCharsets.UTF_8));
        byte[] hashed = instance.digest();
        return Base64.getEncoder().encodeToString(hashed);
    }
}

