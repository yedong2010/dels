package com.dels.service.impl;

import com.dels.dao.UserMapper;
import com.dels.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dels.model.sys.User;

@Service
public class UserService implements IUserService {
    @Autowired
    UserMapper userDao;
    @Override
    public User selectUserByName(String name) {
        return this.userDao.selectByUserName(name);
    }

    @Override
    public User selectUserByPhone(String phoneNum) {
        return userDao.selectByUserPhone(phoneNum);
    }

}
