package club.zymcloud.zymblog.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * @author
 * @date 2020/3/24-14:22
 * 博客分类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Type {

    private Long id;
    @NotBlank(message = "分类名称不能为空")
    private String name;//分类名
    private Long blogCount;//属于这个分类的博客数量

    private List<Blog> blogs = new ArrayList<>();//一个类型有多个博客

}
