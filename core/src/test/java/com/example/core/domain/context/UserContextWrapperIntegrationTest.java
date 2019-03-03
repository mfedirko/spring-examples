package com.example.core.domain.context;

import com.example.core.context.UserContextWrapper;
import java.util.Arrays;
import java.util.HashSet;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.mock;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class UserContextWrapperIntegrationTest {
    @Test
    @WithMockUser(username = "anyuser1", password = "abc456", roles = {"RO","LE"})
    public void userContextMatchesSpringContextHolder(){
        UserContextWrapper userContextWrapper = UserContextWrapper.getCurrentUserContext();
        assertEquals(new HashSet(Arrays.asList(userContextWrapper.getRoles())),new HashSet(Arrays.asList(new String[]{"ROLE_RO","ROLE_LE"})));
        assertEquals(userContextWrapper.getUsername(),"anyuser1");
        UserDetails expectedUD =
                new org.springframework.security.core.userdetails.User( "anyuser1","abc456",
                        true,true,true,true,new HashSet<GrantedAuthority>(Arrays.asList(new SimpleGrantedAuthority("ROLE_RO"),new SimpleGrantedAuthority("ROLE_LE"))));
        assertEquals(userContextWrapper.getUser(),expectedUD);
    }

    @Test
    @WithAnonymousUser
    public void anonymousUser(){
        assertTrue(UserContextWrapper.getCurrentUserContext().isAnonymous());
        assertNull(UserContextWrapper.getCurrentUserContext().getUser());
        assertNull(UserContextWrapper.getCurrentUserContext().getUsername());
    }

//    @Test
//    public void changingSecurityContextUpdatesUserWrapper(){
//
//    }
}
