package com.targa.labs.myBoutique.order.security.configuration;

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

import com.targa.labs.myBoutique.order.security.JwtAuthenticationFilter;
import com.targa.labs.myBoutique.order.security.JwtAuthorizationFilter;
import com.targa.labs.myBoutique.order.security.repository.UserRepository;
import com.targa.labs.myBoutique.order.security.service.UserPrincipalDetailsService;

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
		
		http.csrf().disable()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		//add JWT Filters (1.auth 2.Autorization)
		.addFilter(new JwtAuthenticationFilter(authenticationManager()))
        .addFilter(new JwtAuthorizationFilter(authenticationManager(),  this.userRepository))
        .authorizeRequests()
        //login 
        .antMatchers(HttpMethod.POST, "/login").permitAll()
        
        //payments resources
        .antMatchers(HttpMethod.GET,"/api/payments/").hasRole("ADMIN")
        .antMatchers(HttpMethod.POST,"/api/payments/").hasRole("USER")
        .antMatchers(HttpMethod.GET,"/api/payments/{id}/").hasRole("ADMIN")
        .antMatchers(HttpMethod.DELETE,"/api/payments/{id}/").hasRole("ADMIN")
       
        //temporary
        .antMatchers("/swagger-ui.html").permitAll()
        
        //orders resources
        .antMatchers(HttpMethod.GET,"/api/orders/").hasAnyRole("USER","ADMIN")
        .antMatchers(HttpMethod.POST,"/api/orders/").hasRole("USER")
        .antMatchers(HttpMethod.GET,"/api/orders/{id}/").hasAnyRole("USER","ADMIN")
        .antMatchers(HttpMethod.DELETE,"/api/orders/{id}/").hasRole("ADMIN")
		
		//order-item resources
        .antMatchers(HttpMethod.GET,"/api/order-items/").hasAnyRole("USER","ADMIN")
        .antMatchers(HttpMethod.POST,"/api/order-items/").hasRole("USER")
        .antMatchers(HttpMethod.GET,"/api/order-items/{id}/").hasAnyRole("USER","ADMIN")
        .antMatchers(HttpMethod.DELETE,"/api/order-items/{id}/").hasRole("ADMIN");

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

}
