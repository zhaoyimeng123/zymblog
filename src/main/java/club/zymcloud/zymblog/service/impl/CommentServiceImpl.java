package club.zymcloud.zymblog.service.impl;

import club.zymcloud.zymblog.dao.CommentDao;
import club.zymcloud.zymblog.pojo.Comment;
import club.zymcloud.zymblog.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author
 * @date 2020/3/27-8:33
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentDao commentDao;

    @Override
    public List<Comment> getCommentsByBlogId(Long blogId) {
        List<Comment> comments = commentDao.getCommentsByBlogId(blogId);
        for (Comment comment : comments) {
            List<Comment> commentByParentId = commentDao.getCommentByParentId(comment.getId());
            comment.setReplyComments(commentByParentId);
        }
        System.out.println(eachComment(comments));
        return comments;
    }

    @Override
    public int saveComment(Comment comment) {
        Long parentId = comment.getParentId();
        if (parentId != -1){
            comment.setParentComment(commentDao.getCommentById(parentId));
        }else {
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentDao.saveComment(comment);
    }

    @Override
    public List<Comment> getCommentByParentId(Long parentId) {
        return commentDao.getCommentByParentId(parentId);
    }

    @Override
    public Comment getCommentById(Long id) {
        return commentDao.getCommentById(id);
    }

    /**
     * 循环每个顶级的评论节点
     * @param comments
     * @return
     */
    private List<Comment> eachComment(List<Comment> comments) {
        List<Comment> commentsView = new ArrayList<>();
        for (Comment comment : comments) {
            Comment c = new Comment();
            BeanUtils.copyProperties(comment,c);
            commentsView.add(c);
        }
        //合并评论的各层子代到第一级子代集合中
        combineChildren(commentsView);
        return commentsView;
    }

    /**
     *
     * @param comments root根节点，blog不为空的对象集合
     * @return
     */
    private void combineChildren(List<Comment> comments) {

        for (Comment comment : comments) {
            List<Comment> replys1 = comment.getReplyComments();
            for(Comment reply1 : replys1) {
                //循环迭代，找出子代，存放在tempReplys中
                recursively(reply1);
            }
            //修改顶级节点的reply集合为迭代处理后的集合
            comment.setReplyComments(tempReplys);
            //清除临时存放区
            tempReplys = new ArrayList<>();
        }
    }

    //存放迭代找出的所有子代的集合
    private List<Comment> tempReplys = new ArrayList<>();
    /**
     * 递归迭代，剥洋葱
     * @param comment 被迭代的对象
     * @return
     */
    private void recursively(Comment comment) {
        tempReplys.add(comment);//顶节点添加到临时存放集合
        if (comment.getReplyComments().size()>0) {
            List<Comment> replys = comment.getReplyComments();
            for (Comment reply : replys) {
                tempReplys.add(reply);
                if (reply.getReplyComments().size()>0) {
                    recursively(reply);
                }
            }
        }
    }

}
