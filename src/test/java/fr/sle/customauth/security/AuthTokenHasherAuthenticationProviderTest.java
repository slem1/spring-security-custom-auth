package fr.sle.customauth.security;

import fr.sle.customauth.Device;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author slemoine
 */
@RunWith(JUnit4.class)
public class AuthTokenHasherAuthenticationProviderTest {

    private static final String RAW = "a13451e0-aac3-4d1f-9ecf-4430816e108a";

    @Test
    public void authenticate() {

        AuthTokenService authTokenService = Mockito.mock(AuthTokenService.class);
        AuthTokenAuthenticationProvider authorizationTokenAuthenticationProvider =
                new AuthTokenAuthenticationProvider(authTokenService);

        AuthTokenAuthenticationToken authenticationToken = new AuthTokenAuthenticationToken("deviceIP", RAW);

        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_SUPER_DEVICE"));

        Mockito.when(authTokenService.authenticate(RAW)).thenReturn(Optional.of(new Device("device0001",
                authorities)));
        Authentication authenticate = authorizationTokenAuthenticationProvider.authenticate(authenticationToken);
        Assert.assertTrue(authenticate.isAuthenticated());
        Assert.assertEquals(authenticate.getAuthorities(), authorities);
    }

    @Test(expected = BadCredentialsException.class)
    public void authenticateBadCredentials() {

        AuthTokenService authorizationTokenService = Mockito.mock(AuthTokenService.class);
        AuthTokenAuthenticationProvider authorizationTokenAuthenticationProvider =
                new AuthTokenAuthenticationProvider(authorizationTokenService);
        AuthTokenAuthenticationToken authenticationToken = new AuthTokenAuthenticationToken("deviceIP", RAW);
        Mockito.when(authorizationTokenService.authenticate(RAW)).thenReturn(Optional.ofNullable(null));
        authorizationTokenAuthenticationProvider.authenticate(authenticationToken);
    }


}
