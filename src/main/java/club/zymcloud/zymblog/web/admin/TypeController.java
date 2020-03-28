package club.zymcloud.zymblog.web.admin;

import club.zymcloud.zymblog.pojo.Type;
import club.zymcloud.zymblog.service.TypeService;
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
 * @date 2020/3/24-20:37
 */
@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    //查询到所有的分类,并返回到分类页面
    @GetMapping("/types")
    public String types(Model model,
                        @RequestParam(value = "page",defaultValue = "1")Integer page,
                        @RequestParam(value = "size",defaultValue = "5")Integer size){
        PageHelper.startPage(page,size);
        List<Type> types = typeService.getTypes();
        PageInfo pageInfo = new PageInfo(types,5);
        model.addAttribute("pageInfo",pageInfo);
        return "admin/types";
    }

    //跳转到新增类型页面
    @GetMapping("/types/input")
    public String input(){
        return "admin/type-input.html";
    }

    //新增分类
    @PostMapping("/types")
    public String post(@Valid Type type, BindingResult result, RedirectAttributes attributes,Model model){

        if (result.hasErrors()){
            return "admin/type-input.html";
        }

        //判断该类型是否存在
        Type typeByName = typeService.getTypeByName(type.getName());
        if (typeByName != null){
            model.addAttribute("message","该分类已存在,请重新添加分类");
            return "admin/type-input.html";
        }

        int i = typeService.saveType(type);
        if (i > 0){
            attributes.addFlashAttribute("message","新增分类成功");
        }else {
            attributes.addFlashAttribute("message","操作失败");
        }
        return "redirect:/admin/types";
    }

    //修改分类
    @GetMapping("/types/edit/{id}")
    public String editInput(@PathVariable("id") long id, Model model){
        model.addAttribute("type",typeService.getTypeById(id));
        return "admin/type-edit.html";
    }
    //修改分类
    @PostMapping("/types/{id}")
    public String editPost(@Valid Type type, BindingResult result, RedirectAttributes attributes,Model model){

        if (result.hasErrors()){
            return "admin/type-edit.html";
        }

        //判断该类型是否存在
        Type typeByName = typeService.getTypeByName(type.getName());
        if (typeByName != null){
            model.addAttribute("message","该分类已存在,请重新添加分类");
            return "admin/type-edit.html";
        }

        int i = typeService.updateType(type);
        if (i > 0){
            attributes.addFlashAttribute("message","修改分类成功");
        }else {
            attributes.addFlashAttribute("message","操作失败");
        }
        return "redirect:/admin/types";
    }

    //删除分类
    @GetMapping("/types/delete/{id}")
    public String deleteType(@PathVariable long id,RedirectAttributes attributes){
        int i = typeService.deleteType(id);
        if (i>0){
            attributes.addFlashAttribute("message","删除分类成功");
            return "redirect:/admin/types";
        }else {
            attributes.addFlashAttribute("message","操作失败,请重试");
            return "redirect:/admin/types";
        }
    }
}
