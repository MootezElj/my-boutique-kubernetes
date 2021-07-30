package com.targa.labs.myBoutique.product.security.configuration;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.google.common.collect.ImmutableList;
import com.targa.labs.myBoutique.product.security.JwtAuthenticationFilter;
import com.targa.labs.myBoutique.product.security.JwtAuthorizationFilter;
import com.targa.labs.myBoutique.product.security.repository.UserRepository;
import com.targa.labs.myBoutique.product.security.service.UserPrincipalDetailsService;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

	@Autowired
	private UserPrincipalDetailsService UserPrincipalDetailsService;

	private UserRepository userRepository;
	@Override
	protected void configure(AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(authenticationProvider());

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
		.csrf().disable()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().
		cors()
		.and()
		//add JWT Filters (1.auth 2.Autorization)
		.addFilter(new JwtAuthenticationFilter(authenticationManager()))
		.addFilter(new JwtAuthorizationFilter(authenticationManager(),  this.userRepository))
		.authorizeRequests()

		//login 
		.antMatchers(HttpMethod.POST, "/login").permitAll()

		//products resources
		.antMatchers(HttpMethod.POST,"/api/products/").hasRole("ADMIN")
		.antMatchers(HttpMethod.DELETE,"/api/products/{id}/").hasRole("ADMIN")
		.antMatchers(HttpMethod.GET,"/api/products/{id}/").permitAll()
		.antMatchers(HttpMethod.GET,"/api/products/").permitAll()

		//temporary
		.antMatchers("/swagger-ui.html").permitAll()

		//categories resources
		.antMatchers(HttpMethod.POST,"/api/categories/").hasRole("ADMIN")
		.antMatchers(HttpMethod.GET,"/api/categories/{id}/").hasRole("ADMIN")
		.antMatchers(HttpMethod.DELETE,"/api/categories/{id}/").hasRole("ADMIN")
		.antMatchers(HttpMethod.GET,"/api/categories/").permitAll()

		//reviews resources
		.antMatchers(HttpMethod.POST,"/api/reviews/").hasAnyRole("ADMIN","USER")
		.antMatchers(HttpMethod.DELETE,"/api/reviews/{id}/").hasRole("ADMIN")
		.antMatchers(HttpMethod.GET,"/api/reviews/{id}/").permitAll()
		.antMatchers(HttpMethod.GET,"/api/reviews/").permitAll();
	}



	@Bean
	DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setPasswordEncoder(this.passwordEncoder());
		daoAuthenticationProvider.setUserDetailsService(UserPrincipalDetailsService);
		return daoAuthenticationProvider;
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("HEAD",
                "GET", "POST", "PUT", "DELETE", "PATCH"));
        // setAllowCredentials(true) is important, otherwise:
        // The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '*' when the request's credentials mode is 'include'.
        configuration.setAllowCredentials(true);
        // setAllowedHeaders is important! Without it, OPTIONS preflight request
        // will fail with 403 Invalid CORS request
        configuration.setAllowedHeaders(Arrays.asList("*"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
