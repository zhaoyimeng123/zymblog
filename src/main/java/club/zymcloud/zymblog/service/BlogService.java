package club.zymcloud.zymblog.service;

import club.zymcloud.zymblog.pojo.Blog;

import java.util.List;
import java.util.Map;

/**
 * @author
 * @date 2020/3/25-8:38
 * 博客管理接口
 */
public interface BlogService {

    Blog getBlogById(long id);
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

    //归档
    Map<String,List<Blog>> archiveBlog();

    int blogCount();
}
