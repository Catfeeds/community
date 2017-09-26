package com.tongwii.security.jwt;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * 认证对象
 *
 * @author Zeral
 * @date 2017-08-02
 */
public class JwtUser implements UserDetails {
    private String id;
    private String username;
    private String password;
    private boolean enabled;
    private List<GrantedAuthority> roles;

    @Override
    public String getUsername () {
        return this.username;
    }

    @Override
    public boolean isEnabled () {
        return this.enabled;
    }

    @Override
    public Collection< ? extends GrantedAuthority > getAuthorities () {
        return this.roles;
    }

    @Override
    public boolean isAccountNonExpired () {
        return true;
    }

    @Override
    public boolean isAccountNonLocked () {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired () {
        return true;
    }

    public JwtUser(String id, String username, String password, List<GrantedAuthority> roles, boolean enabled) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.roles = roles;
    }

    public JwtUser(String id, String username, String password, List<GrantedAuthority> roles) {
        this(id, username, password, roles, true);
    }

    @Override
    public String getPassword () {
        return this.password;
    }

    public String getId() {
        return id;
    }

    public List<GrantedAuthority> getRoles() {
        return roles;
    }
}
