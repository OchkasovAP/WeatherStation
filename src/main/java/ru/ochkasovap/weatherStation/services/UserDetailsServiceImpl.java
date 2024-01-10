package ru.ochkasovap.weatherStation.services;

import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService{
	@Value("${admin.login}")
	private String username;
	@Value("${admin.password}")
	private String password;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if(!this.username.equals(username)) {
			throw new UsernameNotFoundException("Not correct username");
		}
		return new UserDetails() {
			@Override
			public boolean isEnabled() {
				return true;
			}
			
			@Override
			public boolean isCredentialsNonExpired() {
				return true;
			}
			
			@Override
			public boolean isAccountNonLocked() {
				return true;
			}
			
			@Override
			public boolean isAccountNonExpired() {
				return true;
			}
			
			@Override
			public String getUsername() {
				return UserDetailsServiceImpl.this.username;
			}
			
			@Override
			public String getPassword() {
				return password;
			}
			
			@Override
			public Collection<? extends GrantedAuthority> getAuthorities() {
				return Collections.emptyList();
			}
		};
	}

}
