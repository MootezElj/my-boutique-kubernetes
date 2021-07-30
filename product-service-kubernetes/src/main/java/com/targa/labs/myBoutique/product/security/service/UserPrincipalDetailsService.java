package com.targa.labs.myBoutique.product.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.targa.labs.myBoutique.product.security.UserPrincipal;
import com.targa.labs.myBoutique.product.security.domain.User;

@Service
public class UserPrincipalDetailsService implements UserDetailsService {



    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
    	
        RestTemplate restTemplate = new RestTemplate();
        User user = restTemplate.getForObject("http://localhost:8083/jwt/"+s+"/", User.class);
    	
    	
        //****************** OLD
        //User user = this.userRepository.findByUsername(s);
        UserPrincipal userPrincipal = new UserPrincipal(user);

        return userPrincipal;
    }
    
 // This method is used by JWTAuthenticationFilter
    @Transactional
    public UserDetails loadUserById(Long id) {
    	RestTemplate restTemplate = new RestTemplate();
        User user = restTemplate.getForObject("http://localhost:8083/jwt/id/"+id+"/", User.class);
		/*
		 * User user = userRepository.findById(id).orElseThrow( () -> new
		 * UsernameNotFoundException("User not found with id : " + id) );
		 */
        return new UserPrincipal(user);
    }
}
