package club.zymcloud.zymblog.web;

import club.zymcloud.zymblog.dao.BlogTagDao;
import club.zymcloud.zymblog.pojo.Blog;
import club.zymcloud.zymblog.pojo.Tag;
import club.zymcloud.zymblog.pojo.Type;
import club.zymcloud.zymblog.service.BlogService;
import club.zymcloud.zymblog.service.TagService;
import club.zymcloud.zymblog.service.TypeService;
import club.zymcloud.zymblog.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * @author
 * @date 2020/3/27-14:17
 * 用户显示的tag页面
 */
@Controller
public class TagShowController {
    @Autowired
    private TagService tagService;
    @Autowired
    private BlogService blogService;
    @Autowired
    private UserService userService;
    @Autowired
    private BlogTagDao blogTagDao;
    @Autowired
    private TypeService typeService;

    //去到tag页面
    @GetMapping("/tags/{id}")
    public String type(@PathVariable long id,
                       @RequestParam(name = "page",defaultValue = "1")Integer page,
                       @RequestParam(name = "size",defaultValue = "5")Integer size,
                       Model model){
        List<Tag> tags = tagService.getTags();
        model.addAttribute("tags",tags);
        if (id==-1){
            id=tags.get(0).getId();
        }
        List<Blog> blogs = new ArrayList<>();
        List<Long> blogIdByTagId = blogTagDao.getBlogIdByTagId(id);
        for (Long aLong : blogIdByTagId) {
            Blog blogById = blogService.getBlogById(aLong);
            blogById.setUser(userService.getUserById(blogById.getUserId()));
            blogById.setType(typeService.getTypeById(blogById.getTypeId()));
            List<Tag> tags1 = new ArrayList<>();
            List<Long> tagIdByBlogId = blogTagDao.getTagIdByBlogId(aLong);
            for (Long aLong1 : tagIdByBlogId) {
                Tag tagById = tagService.getTagById(aLong1);
                tags1.add(tagById);
            }
            blogById.setTags(tags1);
            blogs.add(blogById);
        }
        model.addAttribute("blogs",blogs);
        model.addAttribute("activeTagId",id);
        return "tags";
    }

}
