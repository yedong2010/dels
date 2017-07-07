package com.dels.dao;

import com.dels.model.sys.User;

public interface UserMapper {

    User selectByUserName(String name);

    User selectByUserPhone(String phone);

}
