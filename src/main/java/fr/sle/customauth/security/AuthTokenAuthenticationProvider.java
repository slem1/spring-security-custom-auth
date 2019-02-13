package fr.sle.customauth.security;

import fr.sle.customauth.Device;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.Assert;

/**
 * Authentication Provider for {@link AuthTokenFilter}
 *
 * @author slemoine
 */
public class AuthTokenAuthenticationProvider implements AuthenticationProvider {

    public final AuthTokenService authTokenService;

    public AuthTokenAuthenticationProvider(AuthTokenService authTokenService) {
        this.authTokenService = authTokenService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        Assert.notNull(authentication, "authentication.is.missing");

        AuthTokenAuthenticationToken tokenAuthentication = (AuthTokenAuthenticationToken) authentication;

        Device device = authTokenService.authenticate((String) tokenAuthentication.getCredentials())
                .orElseThrow(() -> new BadCredentialsException("Failed authentication !"));

        return new AuthTokenAuthenticationToken(authentication.getPrincipal(),
                null,
                device.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return AuthTokenAuthenticationToken.class.equals(authentication);
    }
}
