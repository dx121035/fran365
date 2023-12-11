package com.example.fran365.member;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@Entity
@Data
public class Member implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String username; //아이디를 이메일로 강제

    private String password;

    private Integer enabled;

    private String name;

    private String phone;

    private String image;

    private String address;

    private String bid; //가입한 사람 매장주소

    private String Role;

    private String belong;

    private Integer position;
    
    private String department;

    private  String depart;

    private LocalDateTime createDate;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();

        return authList;
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