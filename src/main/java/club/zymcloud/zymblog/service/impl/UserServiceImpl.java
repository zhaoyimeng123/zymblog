package club.zymcloud.zymblog.service.impl;

import club.zymcloud.zymblog.dao.UserDao;
import club.zymcloud.zymblog.pojo.User;
import club.zymcloud.zymblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author
 * @date 2020/3/24-16:09
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User checkUser(String username) {
        User user = userDao.checkUser(username);
        return user;
    }

    @Override
    public User getUserById(Long id) {
        return userDao.getUserById(id);
    }

    @Override
    public int updateUserInfo(User user) {
        return userDao.updateUserInfo(user);
    }
}
