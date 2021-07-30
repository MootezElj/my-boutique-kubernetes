package com.labs.targa.auth.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.labs.targa.auth.security.domain.User;

public class UserPrincipal implements UserDetails {
    private User user;

    public UserPrincipal(User user){
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
    	List<String> permissionsList = new ArrayList<>();
    	List<String> rolesList= new ArrayList<>();
    	
    	if (this.user.getRoles().length()>0) {
    		rolesList= Arrays.asList(this.user.getRoles().split(","));
			
		}
    	
		if (this.user.getPermissions().length()>0) {
			permissionsList= Arrays.asList(this.user.getPermissions().split(","));
		}

    	
        List<GrantedAuthority> authorities = new ArrayList<>();

        // Extract list of permissions (name)
        if (permissionsList.size()>0)
        permissionsList.forEach(p -> {
            GrantedAuthority authority = new SimpleGrantedAuthority(p);
            authorities.add(authority);
        });

        // Extract list of roles (ROLE_name)
        if (rolesList.size()>0)
        	rolesList.forEach(r -> {
            GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + r);
            authorities.add(authority);
        });

        return authorities;
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.user.getActive();
    }
}
