package club.zymcloud.zymblog.service.impl;

import club.zymcloud.zymblog.dao.BlogDao;
import club.zymcloud.zymblog.dao.BlogTagDao;
import club.zymcloud.zymblog.dao.TagDao;
import club.zymcloud.zymblog.dao.TypeDao;
import club.zymcloud.zymblog.pojo.Blog;
import club.zymcloud.zymblog.service.BlogService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author
 * @date 2020/3/25-8:42
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogDao blogDao;
    @Autowired
    private BlogTagDao blogTagDao;
    @Autowired
    private TagDao tagDao;
    @Autowired
    private TypeDao typeDao;

    @Override
    public Blog getBlogById(long id) {
        return blogDao.getBlogById(id);
    }

    @Override
    public List<Blog> getBlog() {
        return blogDao.getBlog();
    }

    @Override
    public List<Blog> getBlogsByExample(Blog blog) {
        Blog b = new Blog();
        BeanUtils.copyProperties(blog,b);
        return blogDao.getBlogsByExample(b);
    }

    //新增博客
    @Override
    public int saveBlog(Blog blog) {
        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        blog.setViewCount(0);
        int i = blogDao.saveBlog(blog);
        List<Blog> blogByUserId = blogDao.getBlogsByExample(blog);
        Blog blog1 = blogByUserId.get(0);
        //将对应的类型的博客数+1
        typeDao.updateAddBlogCount(blog1.getTypeId());
        String tagIds = blog.getTagIds();
        String[] split = tagIds.split(",");
        for (String s : split) {
            Long tag = Long.valueOf(s);
            //将对应的标签的博客数+1
            tagDao.updateAddBlogCount(tag);
            blogTagDao.save(tag,blog1.getId());
        }
        return i;
    }

    @Override
    public int updateBlog(Blog blog) {
        return blogDao.updateBlog(blog);
    }

    @Override
    public int deleteBlog(long id) {
        return blogDao.deleteBlog(id);
    }

    @Override
    public List<Blog> getRecommentBlog() {
        return blogDao.getRecommentBlog();
    }

    @Override
    public List<Blog> getBlogsSearch(String query) {
        return blogDao.getBlogsSearch(query);
    }

    @Transactional
    @Override
    public int updateViewCount(Long id) {
        return blogDao.updateViewCount(id);
    }

    @Override
    public List<Blog> getBlogByTypeId(Long typeId) {
        return blogDao.getBlogByTypeId(typeId);
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {

        //拿到年份的分组
        List<String> years = blogDao.findGroupYear();
        Map<String,List<Blog>> map = new HashMap<>();
        for (String year : years) {
            //拿到每个年份的博客
            map.put(year,blogDao.findBlogsByYear(year));
        }
        return map;
    }

    @Override
    public int blogCount() {
        return blogDao.blogCount();
    }

}
