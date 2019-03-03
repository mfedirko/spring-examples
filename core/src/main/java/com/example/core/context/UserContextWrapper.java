package com.example.core.context;

import java.util.Arrays;
import java.util.stream.Collectors;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

public class UserContextWrapper {
    private static final ThreadLocal<UserContextWrapper> userContext = new ThreadLocal<>();


    public static UserContextWrapper getCurrentUserContext() {
        UserContextWrapper userContextWrapper = userContext.get();
        if (userContextWrapper == null){
            setUserContext(initializeUserContext());
            userContextWrapper = userContext.get();
        }
        updateUserSecurityContext();
        return userContextWrapper;
    }
    public static void setUserContext(UserContextWrapper userContext){
        Assert.notNull(userContext,"User context cannot be null");
        UserContextWrapper.userContext.set(userContext);
    }

    private static UserContextWrapper initializeUserContext(){
        return  new UserContextWrapper();
    }

    public UserContextWrapper(){
    }
    protected void updateAuthorities(Authentication authentication){
        if (authentication.getAuthorities() != null){
            roles = authentication.getAuthorities().stream()
                    .map(g -> g.getAuthority())
                    .collect(Collectors.toList()).toArray(new String[0]);
        }
    }
    public static void updateUserSecurityContext(){
        if (SecurityContextHolder.getContext() != null){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null ){
                UserContextWrapper currentUserContext = userContext.get();
                if (authentication.getPrincipal() instanceof String || !authentication.isAuthenticated()){
                    currentUserContext.anonymous = true;
                }else {
                    UserDetails currentUser = (UserDetails) authentication.getPrincipal();

                    currentUserContext.username = currentUser.getUsername();
                    currentUserContext.user = currentUser;
                }
                currentUserContext.updateAuthorities(authentication);
            }
        }
    }

    @Override
    public String toString() {
        return "UserContextWrapper{" +
                "username='" + username + '\'' +
                ", roles=" + Arrays.toString(roles) +
                ", user=" + user +
                ", anonymous=" + anonymous +
                '}';
    }

    private String username;
    private String[] roles;
    private UserDetails user;
    private boolean anonymous;

    public boolean isAnonymous() {
        return anonymous;
    }

    public String getUsername() {
        return username;
    }

    public String[] getRoles() {
        return roles;
    }

    public UserDetails getUser() {
        return user;
    }

}
