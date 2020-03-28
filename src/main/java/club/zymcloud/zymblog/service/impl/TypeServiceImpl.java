package club.zymcloud.zymblog.service.impl;

import club.zymcloud.zymblog.dao.TypeDao;
import club.zymcloud.zymblog.pojo.Type;
import club.zymcloud.zymblog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author
 * @date 2020/3/24-20:18
 */
@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeDao typeDao;

    @Transactional
    @Override
    public int saveType(Type type) {
        return typeDao.saveType(type);
    }

    @Transactional
    @Override
    public Type getTypeById(long id) {
        return typeDao.getTypeById(id);
    }

    @Override
    public Type getTypeByName(String name) {
        return typeDao.getTypeByName(name);
    }

    @Transactional
    @Override
    public List<Type> getTypes() {
        return typeDao.getTypes();
    }

    @Transactional
    @Override
    public int updateType(Type type) {
        return typeDao.updateType(type);
    }

    @Transactional
    @Override
    public int deleteType(long id) {
        return typeDao.deleteType(id);
    }

    @Override
    public List<Type> getBlogCount() {
        return typeDao.getBlogCount();
    }

}
