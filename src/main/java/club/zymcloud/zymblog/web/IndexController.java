package club.zymcloud.zymblog.web;

import club.zymcloud.zymblog.NotFoundBlogException;
import club.zymcloud.zymblog.dao.BlogTagDao;
import club.zymcloud.zymblog.pojo.Blog;
import club.zymcloud.zymblog.pojo.Tag;
import club.zymcloud.zymblog.pojo.Type;
import club.zymcloud.zymblog.pojo.User;
import club.zymcloud.zymblog.service.BlogService;
import club.zymcloud.zymblog.service.TagService;
import club.zymcloud.zymblog.service.TypeService;
import club.zymcloud.zymblog.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @author
 * @date 2020/3/23-23:23
 */
@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private TagService tagService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private UserService userService;
    @Autowired
    private BlogTagDao blogTagDao;

    //在index页面显示所有的博客
    @GetMapping("/")
    public String index(@RequestParam(name = "page",defaultValue = "1")Integer page,
                        @RequestParam(name = "size",defaultValue = "5")Integer size,
                        Model model){

        //拿到热门的6个type
        List<Type> types = new ArrayList<>();
        List<Type> blogCount = typeService.getBlogCount();
        for (int i = 0; i < 6; i++) {
            types.add(blogCount.get(i));
        }
        model.addAttribute("types",types);
        //拿到5个热门标签
        List<Tag> tags = new ArrayList<>();
        List<Tag> blogCount1 = tagService.getBlogCount();
        for (int i = 0; i < 5; i++) {
            tags.add(blogCount1.get(i));
        }
        model.addAttribute("tags",tags);

        //拿到10条推荐的最新博客
        List<Blog> hotBlogs = new ArrayList<>();
        List<Blog> recommentBlog = blogService.getRecommentBlog();
        for (int i = 0; i < 3; i++) {
            hotBlogs.add(recommentBlog.get(i));
        }
        model.addAttribute("hotBlogs",hotBlogs);

        PageHelper.startPage(page,6);
        List<Blog> blogs = blogService.getBlog();
        for (Blog blog : blogs) {
            blog.setUser(userService.getUserById(blog.getUserId()));
            Type typeById = typeService.getTypeById(blog.getTypeId());
            blog.setType(typeById);
        }
        PageInfo pageInfo = new PageInfo(blogs,5);
        model.addAttribute("pageInfo",pageInfo);
        return "index";
    }

    //搜索博客
    @RequestMapping("/search")
    public String search(@RequestParam(name = "query")String query,
                         @RequestParam(name = "page",defaultValue = "1")Integer page,
                         @RequestParam(name = "size",defaultValue = "5")Integer size,
                         Model model){
        System.out.println("query:"+query);
        PageHelper.startPage(page,2);
        List<Blog> blogsSearch = blogService.getBlogsSearch(query);
        for (Blog blog : blogsSearch) {
            blog.setUser(userService.getUserById(blog.getUserId()));
            Type typeById = typeService.getTypeById(blog.getTypeId());
            blog.setType(typeById);
        }
        PageInfo pageInfo = new PageInfo(blogsSearch,5);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("blogCount",pageInfo.getTotal());
        model.addAttribute("query",query);
        return "search";
    }

    //显示博客详情
    @RequestMapping("/blog/{id}")
    public String showBlog(@PathVariable("id")Long id,
                           Model model){
        Blog blogById = blogService.getBlogById(id);
        blogById.setType(typeService.getTypeById(blogById.getTypeId()));
        blogById.setUser(userService.getUserById(blogById.getUserId()));
        List<Long> tagIdByBlogId = blogTagDao.getTagIdByBlogId(blogById.getId());
        List<Tag> tags = new ArrayList<>();
        for (Long aLong : tagIdByBlogId) {
            Tag tagById = tagService.getTagById(aLong);
            tags.add(tagById);
        }
        blogById.setTags(tags);
        model.addAttribute("blog",blogById);
        blogService.updateViewCount(id);
        return "blog";
    }

    //关于我页面
    @GetMapping("/about")
    public String about(){
        return "about";
    }

    @GetMapping("/footer/newblog")
    public String newblogs(Model model){
        List<Blog> blog = blogService.getBlog();
        List<Blog> blog1 = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            blog1.add(blog.get(i));
        }
        model.addAttribute("newblogs",blog1);
        return "_fragment::newblogList";
    }

}
