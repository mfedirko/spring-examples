package com.example.core.security;

import com.example.core.domain.user.User;
import com.example.core.domain.role.Role;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class MyUserPrincipal implements UserDetails {
    private User user;

    private static final String SPRING_ROLE_PREFIX = "ROLE_";

    public MyUserPrincipal(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> grantedAuthorities = null;
        Set<Role> roles = user.getRoles();
        if (roles != null){
            grantedAuthorities = roles.stream()
                    .map( role -> new SimpleGrantedAuthority(addPrefixToRoleName(role.getName())))
                    .collect(Collectors.toSet());
        }
        return grantedAuthorities;
    }

    private String addPrefixToRoleName(String roleName){
        if (roleName != null){
            if (!roleName.startsWith(SPRING_ROLE_PREFIX)){
                roleName = SPRING_ROLE_PREFIX + roleName;
            }
        }
        return roleName;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
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
        return user.isActive();
    }
}
