package fr.sle.customauth.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Objects;

/**
 * Authentication token Spring Security compatible {@link AuthTokenFilter}
 *
 * @author slemoine
 */
public class AuthTokenAuthenticationToken extends AbstractAuthenticationToken {


    private final Object principal;

    private Object credentials;

    public AuthTokenAuthenticationToken(Object principal, Object credentials) {
        super(null);
        this.principal = principal;
        this.credentials = credentials;
    }

    public AuthTokenAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthTokenAuthenticationToken that = (AuthTokenAuthenticationToken) o;
        return Objects.equals(principal, that.principal) &&
                Objects.equals(credentials, that.credentials);
    }

    @Override
    public int hashCode() {
        return Objects.hash(principal, credentials);
    }
}
