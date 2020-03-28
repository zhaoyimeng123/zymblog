package club.zymcloud.zymblog.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * @author
 * @date 2020/3/24-14:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {

    private Long id;
    private String nickname;//昵称
    private String username;//用户名
    private String password;//密码
    private String email;//邮箱
    private String avatar;//头像
    private Integer type;//类型
    private Date createTime;//创建时间
    private Date updateTime;//修改时间

    private List<Blog> blogs;//一个user有多篇博客

}
