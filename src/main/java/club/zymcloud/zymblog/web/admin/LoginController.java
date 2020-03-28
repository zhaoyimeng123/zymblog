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
    @ResponseBody
    public String updateUserInfo(@RequestParam(name = "file") MultipartFile file, Model model) {
        System.out.println(file);
        System.out.println("文件的名字:" + file.getName()); //拿到文本框的name值
        System.out.println("文件的名字:" + file.getOriginalFilename());//拿到文件真正的名字
        // 文件保存
        if (!file.isEmpty()) {
            try{
                /*
                 * 这段代码执行完毕之后，图片上传到了工程的根路径； 如果我们想把图片上传到
                 * d:/files大家是否能实现呢？ 等等;
                 *  1、文件路径； 2、文件名； 3、文件格式; 4、文件大小的限制;
                 */
                BufferedOutputStream out = new BufferedOutputStream(
                        new FileOutputStream(new File(UUID.randomUUID().toString().substring(0,6)+"_"+file.getOriginalFilename())));
                System.out.println(out);
                out.write(file.getBytes());
                out.flush();
                out.close();
                return "success";
            }catch (Exception e){
                e.printStackTrace();
                return "失败";
            }
        }
        return "失败";
    }


}
