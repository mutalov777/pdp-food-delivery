package uz.pdp.pdp_food_delivery.rest.config.security;

/*
 * @author johnl
 * @since 2/24/2022
 */

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import uz.pdp.pdp_food_delivery.rest.config.security.filters.CustomAuthenticationFilter;
import uz.pdp.pdp_food_delivery.rest.config.security.filters.CustomAuthorizationFilter;
import uz.pdp.pdp_food_delivery.rest.service.auth.AuthUserService;

import static uz.pdp.pdp_food_delivery.rest.config.security.utils.SecurityUtils.WHITE_LIST;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

    private final AuthUserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.cors().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests()
                .antMatchers(WHITE_LIST)
                .permitAll()
                .anyRequest().authenticated();

        http.addFilter(new CustomAuthenticationFilter(authenticationManagerBean()));
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}

