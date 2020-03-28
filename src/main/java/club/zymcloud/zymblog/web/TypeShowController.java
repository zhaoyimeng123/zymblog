package club.zymcloud.zymblog.web;

import club.zymcloud.zymblog.pojo.Blog;
import club.zymcloud.zymblog.pojo.Type;
import club.zymcloud.zymblog.service.BlogService;
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

import java.util.List;

/**
 * @author
 * @date 2020/3/27-14:17
 * 用户显示的type页面
 */
@Controller
public class TypeShowController {
    @Autowired
    private TypeService typeService;
    @Autowired
    private BlogService blogService;
    @Autowired
    private UserService userService;

    //去到type页面
    @GetMapping("/types/{id}")
    public String type(@PathVariable long id,
                       @RequestParam(name = "page",defaultValue = "1")Integer page,
                       @RequestParam(name = "size",defaultValue = "5")Integer size,
                       Model model){

        List<Type> types = typeService.getTypes();
        model.addAttribute("types",types);
        if (id == -1){
            id = types.get(0).getId();
        }
        PageHelper.startPage(page,size);
        List<Blog> blogByTypeId = blogService.getBlogByTypeId(id);
        for (Blog blog : blogByTypeId) {
            blog.setUser(userService.getUserById(blog.getUserId()));
            blog.setType(typeService.getTypeById(id));
        }
        PageInfo pageInfo = new PageInfo(blogByTypeId,5);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("activeTypeId",id);
        return "types";
    }

}
