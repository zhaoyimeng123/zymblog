package club.zymcloud.zymblog.dao;

import club.zymcloud.zymblog.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author
 * @date 2020/3/24-16:10
 */
@Repository
@Mapper
public interface UserDao {

    User checkUser(String username);
    User getUserById(Long id);
    int updateUserInfo(User user);

}
