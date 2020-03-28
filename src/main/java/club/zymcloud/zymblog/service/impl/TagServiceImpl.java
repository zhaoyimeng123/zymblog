package club.zymcloud.zymblog.service.impl;

import club.zymcloud.zymblog.dao.TagDao;
import club.zymcloud.zymblog.pojo.Tag;
import club.zymcloud.zymblog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author
 * @date 2020/3/24-20:18
 */
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagDao tagDao;

    @Transactional
    @Override
    public int saveTag(Tag tag) {
        return tagDao.saveTag(tag);
    }

    @Transactional
    @Override
    public Tag getTagById(long id) {
        return tagDao.getTagById(id);
    }

    @Override
    public Tag getTagByName(String name) {
        return tagDao.getTagByName(name);
    }

    @Transactional
    @Override
    public List<Tag> getTags() {
        return tagDao.getTags();
    }

    @Transactional
    @Override
    public int updateTag(Tag tag) {
        return tagDao.updateTag(tag);
    }

    @Transactional
    @Override
    public int deleteTag(long id) {
        return tagDao.deleteTag(id);
    }

    @Override
    public List<Tag> getBlogCount() {
        return tagDao.getBlogCount();
    }
}
