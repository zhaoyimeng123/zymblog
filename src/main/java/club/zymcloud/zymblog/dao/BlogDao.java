package club.zymcloud.zymblog.dao;

import club.zymcloud.zymblog.pojo.Blog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author
 * @date 2020/3/25-8:42
 */
@Mapper
@Repository
public interface BlogDao {

    Blog getBlogById(long id);
    Blog getBlogByUserId(long userId);
    List<Blog> getBlog();

    //根据条件查询博客,将查询条件封装到一个blog对象中
    List<Blog> getBlogsByExample(Blog blog);

    int saveBlog(Blog blog);

    int updateBlog(Blog blog);

    int deleteBlog(long id);

    List<Blog> getRecommentBlog();

    List<Blog> getBlogsSearch(String query);

    int  updateViewCount(Long id);

    List<Blog> getBlogByTypeId(Long typeId);

    List<String> findGroupYear();
    List<Blog> findBlogsByYear(String year);

    int blogCount();
}
