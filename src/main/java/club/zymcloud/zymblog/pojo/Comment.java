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
 * @date 2020/3/24-14:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Comment {

    private Long id;
    private String nickname;//昵称
    private String email;//邮箱
    private String avatar;//头像
    private String content;//内容
    private Date createTime;//时间
    private Long parentId;//所评论对象的id
    private Long blogId;//评论的博客id

    private boolean adminComment;//是否是博主

    private List<Comment> replyComments = new ArrayList<>();//
    private Comment parentComment;//
    private Blog blog;//所评论的博客


}
