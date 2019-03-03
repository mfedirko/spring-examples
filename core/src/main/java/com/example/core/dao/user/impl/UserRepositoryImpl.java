package com.example.core.dao.user.impl;

import com.example.core.dao.user.UserRepositoryCustom;
import com.example.core.domain.user.User;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User findByUsername(String username) {
        List<User> userList = entityManager
                .createQuery("from User u WHERE u.username = :username",User.class)
                .setParameter("username",username)
                .getResultList();
        if (userList != null && userList.size() > 0){
            return userList.get(0);
        }
        return null;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }
}
