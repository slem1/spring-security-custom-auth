package fr.sle.customauth.security;

import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Component for hashing token. Algorithm is SHA-256.
 *
 * @author slemoine
 */
@Component
public class TokenHasher {

    private static final String SALT = "aSalt";

    private MessageDigest instance = MessageDigest.getInstance("SHA-256");

    public TokenHasher() throws NoSuchAlgorithmException {
    }


    public String hashToken(String raw) {

        instance.update((SALT + raw).getBytes(StandardCharsets.UTF_8));
        return new String(instance.digest(), StandardCharsets.UTF_8);
    }
}

