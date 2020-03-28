package club.zymcloud.zymblog.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author
 * @date 2020/3/24-14:23
 * 博客标签
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Tag {
    private Long id;
    private String name;//标签名称
    private Long blogCount;//属于这个标签的博客数量

}
