package club.zymcloud.zymblog.service;

import club.zymcloud.zymblog.pojo.Type;

import java.util.List;

/**
 * @author
 * @date 2020/3/24-20:11
 */
public interface TypeService {

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

    List<Type> getBlogCount();

}
