package org.example.Bus;

import org.example.Dao.UserDao;
import org.example.Dto.User;

public class UserBus {
    private UserDao userDao = new UserDao();
    public User login(String username, String pawword) {
        return userDao.login(username,pawword);
    }
}
