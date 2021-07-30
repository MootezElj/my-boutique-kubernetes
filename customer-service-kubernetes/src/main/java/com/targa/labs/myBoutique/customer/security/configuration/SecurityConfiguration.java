package com.targa.labs.myBoutique.customer.security.configuration;

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

import com.targa.labs.myBoutique.customer.security.JwtAuthenticationFilter;
import com.targa.labs.myBoutique.customer.security.JwtAuthorizationFilter;
import com.targa.labs.myBoutique.customer.security.repository.UserRepository;
import com.targa.labs.myBoutique.customer.security.service.UserPrincipalDetailsService;

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
        
        //customers resources
        .antMatchers(HttpMethod.GET,"/api/customers/").hasRole("ADMIN")
        .antMatchers(HttpMethod.POST,"/api/customers/").hasRole("USER")
        .antMatchers(HttpMethod.DELETE,"/api/customers/{id}/").hasRole("ADMIN")
        .antMatchers(HttpMethod.GET,"/api/customers/inactive/").hasRole("ADMIN")
        .antMatchers(HttpMethod.GET,"/api/customers/active/").permitAll()
        .antMatchers(HttpMethod.GET,"/api/customers/{id}/").permitAll()
        
        //temporary
        .antMatchers("/swagger-ui.html").permitAll()
        
        //carts resources
        .antMatchers(HttpMethod.POST,"/api/carts/customer/{id}/").hasRole("USER")
        .antMatchers(HttpMethod.GET,"/api/carts/customer/{id}/").hasAnyRole("USER","ADMIN")
        .antMatchers(HttpMethod.GET,"/api/carts/active/").hasRole("ADMIN")
        .antMatchers(HttpMethod.DELETE,"/api/carts/{id}/").hasRole("ADMIN")
        .antMatchers(HttpMethod.GET,"/api/carts/{id}/").hasAnyRole("USER","ADMIN")
        .antMatchers(HttpMethod.GET,"/api/carts/").hasRole("ADMIN");
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
