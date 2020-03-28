package club.zymcloud.zymblog.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author
 * @date 2020/3/25-22:17
 */
@Mapper
@Repository
public interface BlogTagDao {

    //通过blogId插入blog的所有标签id
    int save(@Param("tagId") Long tagId, @Param("blogId") Long blogId);

    //通过tagId查询blogId
    List<Long> getBlogIdByTagId(Long tagId);
    //通过blogId查询此blog所有的tagId
    List<Long> getTagIdByBlogId(Long blogId);

    int updateTagByBlog(@Param("tagId")Long tagId,@Param("blogId")Long blogId);

    int deleteTag(Long blogId);

}
