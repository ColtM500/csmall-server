package cn.tedu.csmall.product.mapper;

import cn.tedu.csmall.product.pojo.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class CategoryMapperTests {

    @Autowired
    CategoryMapper mapper;

    @Test
    void insert(){
        Category category = new Category();
        category.setName("测试数据002");
        category.setParentId(1L);
        category.setDepth(2);
        category.setKeywords("ww");
        category.setSort(1);
        category.setIcon("ww");
        category.setEnable(0);
        category.setIsParent(1);
        category.setIsDisplay(1);
        category.setGmtCreate(LocalDateTime.now());
        category.setGmtModified(LocalDateTime.now());

        System.out.println("插入数据之前，参数：" + category);
        int rows = mapper.insert(category);
        System.out.println("插入数据完成，受影响的行数：" + rows);
        System.out.println("插入数据之后，参数：" + category);
    }
}
