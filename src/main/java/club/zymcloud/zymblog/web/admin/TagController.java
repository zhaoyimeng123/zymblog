package club.zymcloud.zymblog.web.admin;

import club.zymcloud.zymblog.pojo.Tag;
import club.zymcloud.zymblog.service.TagService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

/**
 * @author
 * @date 2020/3/24-23:28
 */
@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    private TagService tagService;

    //查询到所有的标签,并返回到标签页面
    @GetMapping("/tags")
    public String types(Model model,
                        @RequestParam(value = "page",defaultValue = "1")Integer page,
                        @RequestParam(value = "size",defaultValue = "5")Integer size){
        PageHelper.startPage(page,6);
        List<Tag> tags = tagService.getTags();
        PageInfo pageInfo = new PageInfo(tags,5);
        model.addAttribute("pageInfo",pageInfo);
        return "admin/tags";
    }

    //跳转到新增类型页面
    @GetMapping("/tags/input")
    public String input(){
        return "admin/tag-input.html";
    }

    //新增标签
    @PostMapping("/tags")
    public String post(@Valid Tag tag, BindingResult result, RedirectAttributes attributes,Model model){

        if (result.hasErrors()){
            return "admin/tag-input.html";
        }

        //判断该类型是否存在
        Tag tagByName = tagService.getTagByName(tag.getName());
        if (tagByName != null){
            model.addAttribute("message","该分类已存在,请重新添加分类");
            return "admin/tag-input";
        }

        int i = tagService.saveTag(tag);
        if (i > 0){
            attributes.addFlashAttribute("message","新增标签成功");
        }else {
            attributes.addFlashAttribute("message","操作失败");
        }
        return "redirect:/admin/tags";
    }

    //修改标签
    @GetMapping("/tags/edit/{id}")
    public String editInput(@PathVariable("id") long id, Model model){
        model.addAttribute("tag",tagService.getTagById(id));
        return "admin/tag-edit.html";
    }
    //修改标签
    @PostMapping("/tags/{id}")
    public String editPost(@Valid Tag tag, BindingResult result, RedirectAttributes attributes, Model model){

        if (result.hasErrors()){
            return "admin/tag-edit.html";
        }

        //判断该标签是否存在
        Tag tagByName = tagService.getTagByName(tag.getName());
        if (tagByName != null){
            model.addAttribute("message","该标签已存在,请重新添加标签");
            return "admin/tag-edit.html";
        }

        int i = tagService.updateTag(tag);
        if (i > 0){
            attributes.addFlashAttribute("message","修改标签成功");
        }else {
            attributes.addFlashAttribute("message","操作失败");
        }
        return "redirect:/admin/tags";
    }

    //删除标签
    @GetMapping("/tags/delete/{id}")
    public String deleteTag(@PathVariable long id, RedirectAttributes attributes){
        int i = tagService.deleteTag(id);
        if (i>0){
            attributes.addFlashAttribute("message","删除标签成功");
            return "redirect:/admin/tags";
        }else {
            attributes.addFlashAttribute("message","操作失败,请重试");
            return "redirect:/admin/tags";
        }
    }
}
