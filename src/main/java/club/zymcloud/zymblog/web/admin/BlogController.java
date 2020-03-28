package club.zymcloud.zymblog.web.admin;

import club.zymcloud.zymblog.dao.BlogTagDao;
import club.zymcloud.zymblog.dao.TagDao;
import club.zymcloud.zymblog.dao.TypeDao;
import club.zymcloud.zymblog.pojo.Blog;
import club.zymcloud.zymblog.pojo.Tag;
import club.zymcloud.zymblog.pojo.Type;
import club.zymcloud.zymblog.pojo.User;
import club.zymcloud.zymblog.service.BlogService;
import club.zymcloud.zymblog.service.TagService;
import club.zymcloud.zymblog.service.TypeService;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author
 * @date 2020/3/24-18:03
 */
@Controller
@RequestMapping("/admin")
public class BlogController {

    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;
    @Autowired
    private BlogService blogService;
    @Autowired
    private BlogTagDao blogTagDao;
    @Autowired
    private TagDao tagDao;
    @Autowired
    private TypeDao typeDao;


    //返回到博客列表页面
    @GetMapping("/blogs")
    public String toBlogs(@RequestParam(name = "page",defaultValue = "1")Integer page,
                          @RequestParam(name = "size",defaultValue = "2")Integer size,
                          Model model){
        //查询所有博客到blogs页面
        PageHelper.startPage(page,6);
        List<Blog> blogs = blogService.getBlog();
        for (Blog blog : blogs) {
            Type typeById = typeService.getTypeById(blog.getTypeId());
            blog.setType(typeById);
        }
        PageInfo pageInfo = new PageInfo(blogs,5);
        model.addAttribute("pageInfo",pageInfo);
        //查询到所有分类返回给博客分类页
        List<Type> types = typeService.getTypes();
        model.addAttribute("types",types);
        return "admin/blogs";
    }

    //查询博客
    @GetMapping("/blogs/search")
    //@ResponseBody
    public String search(@RequestParam(name = "page",defaultValue = "1")Integer page,
                          @RequestParam(name = "size",defaultValue = "2")Integer size,
                          @RequestParam(name = "title",required = false) String title,
                          @RequestParam(name = "typeId",required = false) Long typeId,
                          @RequestParam(name = "recommend",required = false) String recommend,
                          Model model){
        Blog blog = new Blog();
        blog.setTitle(title);
        blog.setTypeId(typeId);
        if (recommend.equals("true")){
            blog.setRecommend(true);
        }else {
            blog.setRecommend(false);
        }
        System.out.println(blog);

        PageHelper.startPage(page,200);
        List<Blog> blogs = blogService.getBlogsByExample(blog);
        for (Blog blog1 : blogs) {
            Type typeById = typeService.getTypeById(blog1.getTypeId());
            blog1.setType(typeById);
        }
        //System.out.println("blogs:"+blogs);
        PageInfo pageInfo = new PageInfo(blogs,5);
        model.addAttribute("pageInfo",pageInfo);
        //System.out.println("--------========"+pageInfo);
        return "admin/blogs::searchBlog";
    }

    //去到博客新增页面
    @GetMapping("/blogs/input")
    public String toBlogInput(Model model){
        //查询到所有分类返回给博客新增页
        List<Type> types = typeService.getTypes();
        model.addAttribute("types",types);
        //查询到所有标签返回给博客新增页
        List<Tag> tags = tagService.getTags();
        model.addAttribute("tags",tags);
        return "admin/blogs-input";
    }
    //新增博客
    @PostMapping("blogs/input")
    public String blogInput(Blog blog, HttpSession session, RedirectAttributes attributes){
        User user = (User) session.getAttribute("user");
        blog.setUserId(user.getId());
        int i = blogService.saveBlog(blog);
        if (i>0){
            attributes.addFlashAttribute("message","新增博客成功");
        }else {
            attributes.addFlashAttribute("message","操作失败");
        }
        return "redirect:/admin/blogs";
    }

    //到博客编辑页面
    @GetMapping("/blogs/edit/{id}")
    public String toBlogEdit(@PathVariable("id")Long id,Model model){
        Blog blogById = blogService.getBlogById(id);
        model.addAttribute("blog",blogById);
        //在修改博客类型的时候,先将对应的类型的博客数量-1
        typeDao.updateSubBlogCount(blogById.getTypeId());
        //通过blogId找到对应所有的tagId
        List<Long> tagIdByBlogId = blogTagDao.getTagIdByBlogId(id);
        //在修改博客标签的时候,先将对应的标签的博客数量-1
        for (Long aLong : tagIdByBlogId) {
            tagDao.updateSubBlogCount(aLong);
        }
        //将blogtag表中blogId对应的所有tagId记录删除
        blogTagDao.deleteTag(id);
        model.addAttribute("blog",blogById);
        //查询到所有分类返回给博客新增页
        model.addAttribute("types",typeService.getTypes());
        //查询到所有标签返回给博客新增页
        model.addAttribute("tags",tagService.getTags());
        return "admin/blogs-edit";
    }
    //修改博客
    @PostMapping("blogs/edit")
    public String blogEdit(Blog blog, RedirectAttributes attributes){
        blog.setUpdateTime(new Date());
        String tagIds = blog.getTagIds();
        String[] split = tagIds.split(",");
        //增加blogtag表中blogId与之对应的tagId
        for (String s : split) {
            blogTagDao.save(Long.valueOf(s),blog.getId());
            //对应tagId的博客数量+1
            tagDao.updateAddBlogCount(Long.valueOf(s));
        }
        //将博客对应的类型的博客数+1
        typeDao.updateAddBlogCount(blog.getTypeId());
        int i = blogService.updateBlog(blog);
        if (i>0){
            attributes.addFlashAttribute("message","修改博客成功");
        }else {
            attributes.addFlashAttribute("message","操作失败");
        }
        return "redirect:/admin/blogs";
    }

    //删除
    @GetMapping("/blogs/delete/{id}")
    public String deleteBlog(@PathVariable("id")Long id,RedirectAttributes attributes){
        blogTagDao.deleteTag(id);
        int i = blogService.deleteBlog(id);
        if (i>0){
            attributes.addFlashAttribute("message","删除成功");
        }else {
            attributes.addFlashAttribute("message","删除失败");
        }
        return "redirect:/admin/blogs";
    }


    @RequestMapping("/uploadImg")
    @ResponseBody
    public JSONObject editormdPic (@RequestParam(value = "editormd-image-file", required = true) MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws Exception{

        String trueFileName = file.getOriginalFilename();

        String suffix = trueFileName.substring(trueFileName.lastIndexOf("."));

        String fileName = System.currentTimeMillis()+"_"+ UUID.randomUUID().toString().substring(0,4) +suffix;
        System.out.println("filename:"+fileName);
        String path = request.getContextPath()+"/img/upload";
        System.out.println("path:"+path);

        File targetFile = new File(path, fileName);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }

        //保存
        try {
            file.transferTo(targetFile);
        } catch (Exception e) {
            e.printStackTrace();
        }


        JSONObject res = new JSONObject();
        res.put("url", request.getContextPath()+"/assets/msg/upload/"+fileName);
        res.put("success", 1);
        res.put("message", "upload success!");

        return res;

    }


}
