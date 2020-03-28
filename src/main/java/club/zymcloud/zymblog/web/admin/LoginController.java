package club.zymcloud.zymblog.web.admin;

import club.zymcloud.zymblog.pojo.User;
import club.zymcloud.zymblog.service.UserService;
import club.zymcloud.zymblog.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @author
 * @date 2020/3/24-17:12
 */
@Controller
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public String toLogin(){
        return "admin/login";
    }


    @PostMapping(value = "/login")
    public String login(@RequestParam(name = "username") String username,
                        @RequestParam(name = "password") String password,
                        HttpSession session,
                        RedirectAttributes attributes){
        User user = userService.checkUser(username);
        if (user != null){
            if (MD5Utils.code(password).equals(user.getPassword())){
                user.setPassword(null);
                session.setAttribute("user",user);
                return "admin/index";
            }else {
                attributes.addFlashAttribute("message","用户名或者密码错误");
                return "redirect:/admin";
            }
        }
        attributes.addFlashAttribute("message","此用户不存在");
        return "redirect:/admin";

    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin";
    }



}
