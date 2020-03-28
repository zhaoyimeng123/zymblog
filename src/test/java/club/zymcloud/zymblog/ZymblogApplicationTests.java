package club.zymcloud.zymblog;

import club.zymcloud.zymblog.dao.BlogDao;
import club.zymcloud.zymblog.dao.TypeDao;
import club.zymcloud.zymblog.pojo.Type;
import club.zymcloud.zymblog.service.TypeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ZymblogApplicationTests {

    @Autowired
    private TypeService typeService;
    @Autowired
    private BlogDao blogDao;

    //测试type
    @Test
    void contextLoads() {
        Type type = new Type();
        type.setName("日志记录");
        //typeService.saveType(type);
        //List<Type> types = typeService.getTypes();
        //System.out.println(types);
        type.setName("测试修改type");
        type.setId((long) 1);
        //typeService.updateType(type);
        typeService.deleteType(2);

    }

    @Test
    void contextLoads1() {
        List<String> groupYear = blogDao.findGroupYear();

        System.out.println(groupYear);
    }

}
