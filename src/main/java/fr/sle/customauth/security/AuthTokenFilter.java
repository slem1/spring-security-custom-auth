package fr.sle.customauth.security;

import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * Token Authentication from http header.
 *
 * @author slemoine
 */
public class AuthTokenFilter extends OncePerRequestFilter {

    private final AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();

    private final AuthenticationManager authenticationManager;

    /**
     * {@inheritDoc}
     */
    public AuthTokenFilter(AuthenticationManager authenticationManager) {
        Assert.notNull(authenticationManager, "authenticationManager cannot be null");
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        final boolean debug = this.logger.isDebugEnabled();

        String header = request.getHeader("Auth-Token");

        if (header == null) {
            chain.doFilter(request, response);
            return;
        }

        try {
            if (debug) {
                this.logger
                        .debug("Authentication-Token header found for '" + request.getRemoteUser() + "'");
            }

            AuthTokenAuthenticationToken authRequest = new AuthTokenAuthenticationToken(
                    request.getRemoteUser(), header);

            authRequest.setDetails(
                    this.authenticationDetailsSource.buildDetails(request));

            Authentication authResult = this.authenticationManager
                    .authenticate(authRequest);

            if (debug) {
                this.logger.debug("Authentication success: " + authResult);
            }

            SecurityContextHolder.getContext().setAuthentication(authResult);

            onSuccessfulAuthentication(request, response, authResult);

        } catch (AuthenticationException failed) {
            SecurityContextHolder.clearContext();

            if (debug) {
                this.logger.debug("Authentication request for failed: " + failed);
            }

            onUnsuccessfulAuthentication(request, response, failed);

            chain.doFilter(request, response);

            return;
        }

        chain.doFilter(request, response);
    }

    protected void onSuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response, Authentication authResult) throws IOException {
    }

    protected void onUnsuccessfulAuthentication(HttpServletRequest request,
                                                HttpServletResponse response, AuthenticationException failed)
            throws IOException {
    }


}
