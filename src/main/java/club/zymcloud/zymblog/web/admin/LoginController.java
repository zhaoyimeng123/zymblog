package club.zymcloud.zymblog.web.admin;

import club.zymcloud.zymblog.pojo.User;
import club.zymcloud.zymblog.service.UserService;
import club.zymcloud.zymblog.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.Date;
import java.util.UUID;

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
    public String toLogin() {
        return "admin/login";
    }


    @PostMapping(value = "/login")
    public String login(@RequestParam(name = "username") String username,
                        @RequestParam(name = "password") String password,
                        @RequestParam(name = "kaptcha") String kaptcha,
                        HttpSession session,
                        RedirectAttributes attributes,
                        HttpServletRequest request) {
        String verifyCode = (String) request.getSession().getAttribute("verifyCode");
        System.out.println("verifyCode:" + verifyCode);
        System.out.println("kaptcha:" + kaptcha);
        User user = userService.checkUser(username);
        if (user != null) {
            if (MD5Utils.code(password).equals(user.getPassword())) {
                if (!kaptcha.equals(verifyCode)) {
                    user.setPassword(null);
                    session.setAttribute("user", user);
                    return "admin/index";
                } else {
                    attributes.addFlashAttribute("message", "验证码错误");
                    return "redirect:/admin";
                }

            } else {
                attributes.addFlashAttribute("message", "用户名或者密码错误");
                return "redirect:/admin";
            }
        }
        attributes.addFlashAttribute("message", "此用户不存在");
        return "redirect:/admin";

    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/admin";
    }

    @GetMapping("/userDetail")
    public String userDetail(HttpServletRequest request,Model model) {
        User user = (User) request.getSession().getAttribute("user");
        model.addAttribute("user",user);
        return "admin/user_detail";
    }

    @PostMapping("/updateUserInfo")
    public String updateUserInfo(@RequestParam(name = "file") MultipartFile file,
                                 @RequestParam(name = "nickname")String nickname,
                                 @RequestParam(name = "email")String email,
                                 HttpSession session,
                                 Model model) {
        System.out.println(nickname);
        System.out.println(email);
        System.out.println(file);
        System.out.println("文件的名字:" + file.getName()); //拿到文本框的name值
        System.out.println("文件的名字:" + file.getOriginalFilename());//拿到文件真正的名字
        // 文件保存
        File avatar = new File(UUID.randomUUID().toString().substring(0,6)+"_"+file.getOriginalFilename());
        if (!file.isEmpty()) {
            try{
                BufferedOutputStream out = new BufferedOutputStream(
                        new FileOutputStream(avatar));
                System.out.println(out);
                out.write(file.getBytes());
                out.flush();
                out.close();

            }catch (Exception e){
                e.printStackTrace();
                model.addAttribute("message","头像修改失败");
                return "admin/user_detail";
            }
        }
        User user = (User) session.getAttribute("user");
        user.setUpdateTime(new Date());
        user.setNickname(nickname);
        user.setEmail(email);
        user.setAvatar(avatar.getName());
        System.out.println("avatar.getName()"+avatar.getName());
        userService.updateUserInfo(user);

        model.addAttribute("message","修改成功");
        return "admin/index";
    }


}
