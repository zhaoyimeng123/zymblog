package club.zymcloud.zymblog.service;

import club.zymcloud.zymblog.pojo.Comment;

import java.util.List;

/**
 * @author
 * @date 2020/3/27-8:31
 */
public interface CommentService {

    //根据blogId获取该blog的所有评论
    List<Comment> getCommentsByBlogId(Long blogId);

    //保存评论
    int saveComment(Comment comment);

    //通过父评论id拿到所有子评论
    List<Comment> getCommentByParentId(Long parentId);

    Comment getCommentById(Long id);
}
