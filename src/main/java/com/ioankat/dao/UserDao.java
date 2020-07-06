package com.ioankat.dao;

import com.ioankat.entity.User;

public interface UserDao {

    User findByUserName(String userName);
    
    void save(User user);
    
}
