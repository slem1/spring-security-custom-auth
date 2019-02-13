package fr.sle.customauth.security;

import fr.sle.customauth.DeviceDao;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.security.NoSuchAlgorithmException;

/**
 * @author slemoine
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final DeviceDao deviceDao;

    private final TokenHasher tokenHasher;

    public SecurityConfig(DeviceDao deviceDao, TokenHasher tokenHasher) {
        this.deviceDao = deviceDao;
        this.tokenHasher = tokenHasher;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .anyRequest().authenticated();

        http.addFilterBefore(new AuthTokenFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public AuthTokenAuthenticationProvider provider() throws NoSuchAlgorithmException {
        return new AuthTokenAuthenticationProvider(authTokenService());
    }

    @Bean
    public AuthTokenServiceImpl authTokenService() throws NoSuchAlgorithmException {
        return new AuthTokenServiceImpl(deviceDao, tokenHasher);
    }
}
