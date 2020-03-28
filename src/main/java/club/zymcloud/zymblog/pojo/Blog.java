package club.zymcloud.zymblog.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author
 * @date 2020/3/24-14:12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Blog {
    private Long id;
    private String title;//博客标题
    private String content;//博客内容
    private String description;//博客描述
    private String firstPicture;//博客首图
    private String  flag;//转载 原创 翻译
    private Integer viewCount;//观看次数
    private boolean appreciation;//是否开启赞赏功能
    private boolean shareStatement;//是否开启版权声明
    private boolean commentabled;//是否开启评论功能
    private boolean publish;//保存还是发布博客
    private boolean recommend;//是否推荐
    private Date createTime;//创建时间
    private Date updateTime;//修改时间
    private Long userId;//博客所属的userId
    private Long typeId;//博客所属的typeId

    private String tagIds;
    private Type type;//博客类型 一个博客一个类型
    private List<Tag> tags = new ArrayList<>();//一个博客有多个标签
    private User user;//一个博客对应一个作者
    private List<Comment> comments;//一篇博客有多条评论


}
