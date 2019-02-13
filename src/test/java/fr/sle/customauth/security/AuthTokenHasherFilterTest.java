package fr.sle.customauth.security; /**
 * @author slemoine
 */

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Collections;

/**
 * @author slemoine
 */
@RunWith(JUnit4.class)
public class AuthTokenHasherFilterTest {

    private static final String RAW = "a13451e0-aac3-4d1f-9ecf-4430816e108a";

    @Test
    public void filterAndAuthenticate() throws ServletException, IOException {

        AuthenticationManager authenticationManager = Mockito.mock(AuthenticationManager.class);

        AuthTokenAuthenticationToken authenticationToken = new AuthTokenAuthenticationToken("deviceIP", RAW);

        Mockito.when(authenticationManager.authenticate(ArgumentMatchers.eq(authenticationToken)))
                .thenReturn(new AuthTokenAuthenticationToken("deviceIP", null, Collections.emptyList()));

        AuthTokenFilter filter = new AuthTokenFilter(authenticationManager);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRemoteUser("deviceIP");
        request.addHeader("Auth-Token", RAW);

        filter.doFilterInternal(request, new MockHttpServletResponse(), new MockFilterChain());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Assert.assertNotNull(authentication);
        Assert.assertTrue(authentication.isAuthenticated());

    }


    @Test
    public void failedAuthentication() throws ServletException, IOException {

        AuthenticationManager authenticationManager = Mockito.mock(AuthenticationManager.class);

        AuthTokenAuthenticationToken authenticationToken = new AuthTokenAuthenticationToken("deviceIP", RAW);

        Mockito.when(authenticationManager.authenticate(ArgumentMatchers.eq(authenticationToken)))
                .thenThrow(new BadCredentialsException("failed"));

        AuthTokenFilter filter = new AuthTokenFilter(authenticationManager);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRemoteUser("deviceIP");
        request.addHeader("Auth-Token", RAW);

        filter.doFilterInternal(request, new MockHttpServletResponse(), new MockFilterChain());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Assert.assertNull(authentication);
    }

    @Test
    public void noHeaderShouldContinue() throws ServletException, IOException {

        AuthTokenFilter filter = new AuthTokenFilter(Mockito.mock(AuthenticationManager.class));
        FilterChain chain = Mockito.mock(FilterChain.class);

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        filter.doFilterInternal(request, response, chain);

        Mockito.verify(chain).doFilter(request, response);

    }
}
