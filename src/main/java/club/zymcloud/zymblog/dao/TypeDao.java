package club.zymcloud.zymblog.dao;

import club.zymcloud.zymblog.pojo.Type;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author
 * @date 2020/3/24-20:18
 */
@Mapper
@Repository
public interface TypeDao {

    //新增分类
    int saveType(Type type);

    //根据id查询类型
    Type getTypeById(long id);
    //根据名称查询
    Type getTypeByName(String name);

    //查询所有
    List<Type> getTypes();

    //修改类型
    int updateType(Type type);

    //删除分类
    int deleteType(long id);

    int updateAddBlogCount(Long id);
    int updateSubBlogCount(Long id);


    List<Type> getBlogCount();
}
