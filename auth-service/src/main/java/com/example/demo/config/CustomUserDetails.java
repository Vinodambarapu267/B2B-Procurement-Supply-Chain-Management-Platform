package com.example.demo.config;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.entity.Users;

public class CustomUserDetails implements UserDetails {

	private String username;
	private String password;
	private List<GrantedAuthority> authorities;

	public CustomUserDetails(Users users) {
		this.username = users.getUsername();
		this.password = users.getPassword();

		this.authorities = Stream.concat(Stream.of(new SimpleGrantedAuthority("ROLE_" + users.getRole().name())),
						users.getRole().getPermissions().stream()
								.map(permission -> new SimpleGrantedAuthority(permission.name())))
				.collect(Collectors.toList());
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
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
		return true;
	}
}