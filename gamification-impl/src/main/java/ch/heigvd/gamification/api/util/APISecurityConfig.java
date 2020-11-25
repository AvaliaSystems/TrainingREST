package ch.heigvd.gamification.api.util;
import ch.heigvd.gamification.repositories.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableWebSecurity
@Order(1)
public class APISecurityConfig extends WebSecurityConfigurerAdapter {

    // default Hardcoded value but can be set in properties file
    @Value("X-API-KEY")
    private String principalRequestHeader;

    @Autowired
    private ApplicationRepository applicationRepository;

    private Set<String> keyList = new HashSet<>();

    @Override
    public void init(WebSecurity web) throws Exception {
        applicationRepository.findAll().forEach(application -> this.keyList.add(application.getApiKey()));
        super.init(web);
    }


    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        // TODO
        // get API-key from Database or any persistence storage
        // It would be nice if application A cannot send request to application B

        APIKeyAuthFilter filter = new APIKeyAuthFilter(principalRequestHeader);
        filter.setAuthenticationManager(new AuthenticationManager() {

            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                String principal = (String) authentication.getPrincipal();
                if (!keyList.contains(principal))
                {
                    throw new BadCredentialsException("The API key was not found or not the expected value.");
                }
                authentication.setAuthenticated(true);
                return authentication;
            }
        });
        httpSecurity.
                antMatcher("/bob/**").
                csrf().disable().
                sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).
                and().addFilter(filter).authorizeRequests().anyRequest().authenticated();
    }

}