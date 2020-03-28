package club.zymcloud.zymblog.dao;

import club.zymcloud.zymblog.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author
 * @date 2020/3/27-8:33
 */
@Mapper
@Repository
public interface CommentDao {

    //根据blogId获取该blog的所有评论
    List<Comment> getCommentsByBlogId(Long blogId);
    //通过父评论id拿到所有的子评论
    List<Comment> getCommentByParentId(Long parentId);

    //保存评论
    int saveComment(Comment comment);

    Comment getCommentById(Long id);
}
