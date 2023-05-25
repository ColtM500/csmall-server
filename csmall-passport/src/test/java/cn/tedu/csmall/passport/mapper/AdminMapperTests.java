package cn.tedu.csmall.passport.mapper;

import cn.tedu.csmall.passport.pojo.entity.Admin;
import cn.tedu.csmall.passport.pojo.vo.AdminListItemVO;
import cn.tedu.csmall.passport.pojo.vo.PageData;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@SpringBootTest
public class AdminMapperTests {

    @Autowired
    AdminMapper mapper;

    @Test
    void insert() {
        Admin admin = new Admin();
        admin.setUsername("测试用户名002");
        admin.setPassword("测试密码001");
        admin.setDescription("测试简介001");
        admin.setGmtCreate(LocalDateTime.now());
        admin.setGmtModified(LocalDateTime.now());

        System.out.println("插入数据之前，参数：" + admin);
        int rows = mapper.insert(admin);
        System.out.println("插入完成,受影响的行数: " + rows);
        System.out.println("插入数据之后，参数：" + admin);

//        long start = System.currentTimeMillis();
//        for (int i=0; i<10; i++){
//            mapper.insert(admin);
//        }
//        long end = System.currentTimeMillis();
//        System.out.println(end-start);
//    }
    }

    @Test
    void list() {
        List<AdminListItemVO> list = mapper.list();
        log.debug("查询列表完成，结果：{}", list);
        for (Object item : list) {
            log.debug("列表项：{}", item);
        }
    }

}