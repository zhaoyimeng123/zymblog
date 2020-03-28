package club.zymcloud.zymblog.service;

import club.zymcloud.zymblog.pojo.Tag;

import java.util.List;

/**
 * @author
 * @date 2020/3/24-20:11
 */
public interface TagService {

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

    List<Tag> getBlogCount();
}
