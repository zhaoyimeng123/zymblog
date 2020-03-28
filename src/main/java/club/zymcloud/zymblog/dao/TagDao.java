package club.zymcloud.zymblog.dao;

import club.zymcloud.zymblog.pojo.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author
 * @date 2020/3/24-20:18
 */
@Mapper
@Repository
public interface TagDao {

    //新增分类
    int saveTag(Tag tag);

    //根据id查询类型
    Tag getTagById(long id);
    //根据名称查询
    Tag getTagByName(String name);

    //查询所有
    List<Tag> getTags();

    //修改类型
    int updateTag(Tag tag);

    //删除分类
    int deleteTag(long id);

    int updateAddBlogCount(Long id);
    int updateSubBlogCount(Long id);

    List<Tag> getBlogCount();
}
