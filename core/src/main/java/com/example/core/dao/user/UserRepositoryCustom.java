package com.example.core.dao.user;

import com.example.core.domain.user.User;

public interface UserRepositoryCustom {
    User findByUsername(String username);
}
