package cn.tedu.csmall.passport.service;

import cn.tedu.csmall.passport.pojo.vo.PageData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class RoleServiceTests {

    @Autowired
    IRoleService service;

    @Test
    void list() {
        List<?> list = service.list();
        System.out.println("查询列表完成，结果集中的数据量：" + list.size());
        for (Object item : list) {
            System.out.println(item);
        }
    }

//    @Test
//    void PageList(){
//        Integer pageNum = 1;
//        Integer pageSize = 6;
//        PageData<?> pageData = service.list(pageNum, pageSize);
//        List<?> list = pageData.getList();
//        System.out.println("集合类型: " + list.getClass().getName());
//        System.out.println("查询列表完成，结果集中的数据量: " + list.size());
//        System.out.println("总记录数: " + pageData.getTotal());
//        System.out.println("当前页码: " + pageData.getCurrentPage());
//        System.out.println("最大页码: " + pageData.getMaxPage());
//        System.out.println("每页记录数: " + pageData.getPageSize());
//        for (Object item : list) {
//            System.out.println(item);
//        }
//    }
}
