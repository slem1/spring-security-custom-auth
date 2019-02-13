package fr.sle.customauth;

import fr.sle.customauth.security.TokenHasher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author slemoine
 */
@Configuration
public class ApplicationConfig {

    private final TokenHasher tokenHasher;

    public ApplicationConfig(TokenHasher tokenHasher) {
        this.tokenHasher = tokenHasher;
    }

    @Bean
    public DeviceDao deviceDao() {
        return new DeviceDao(tokenHasher);
    }
}
