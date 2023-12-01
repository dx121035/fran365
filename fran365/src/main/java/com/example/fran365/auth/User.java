package com.example.fran365.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.fran365.member.Member;




public class User implements UserDetails {


	private Member member;

	public User(Member member) {
		this.member = member;
	}




	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		String role = (String) member.getRole();

		ArrayList<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();

		authList.add(new GrantedAuthority() {
			public String getAuthority() {
				return role;
			}
		});

		return authList;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return member.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return member.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}



}

