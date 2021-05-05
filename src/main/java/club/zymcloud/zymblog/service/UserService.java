package club.zymcloud.zymblog.service;

import club.zymcloud.zymblog.pojo.User;
import org.springframework.stereotype.Service;

/**
 * @author
 * @date 2020/3/24-16:08
 */

public interface UserService {
    User checkUser(String username);
    User getUserById(Long id);
    int updateUserInfo(User user);
}
