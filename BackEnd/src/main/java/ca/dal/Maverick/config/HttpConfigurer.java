package ca.dal.Maverick.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class HttpConfigurer extends AbstractHttpConfigurer<HttpConfigurer, HttpSecurity> {
    private AuthenticationProviders authenticationProviders;

    public HttpConfigurer(AuthenticationProviders authenticationProviders) {
        this.authenticationProviders = authenticationProviders;
    }

    @Override
    public void configure(HttpSecurity http) {
        http.addFilterBefore(new AuthenticationFilter(authenticationProviders),
                UsernamePasswordAuthenticationFilter.class);

    }

}
