package club.zymcloud.zymblog.web;

import club.zymcloud.zymblog.service.BlogService;
import com.sun.org.apache.regexp.internal.RE;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author
 * @date 2020/3/27-16:12
 */
@Controller
@Slf4j
public class ArchivesShowController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/archives")
    public String archives(Model model){
        System.out.println("--------------"+blogService.archiveBlog());
        model.addAttribute("archiveMap",blogService.archiveBlog());
        model.addAttribute("blogCount",blogService.blogCount());
        return "archives";
    }
}
