package cn.tedu.csmall.product.mapper;

import cn.tedu.csmall.product.pojo.entity.AttributeTemplate;
import cn.tedu.csmall.product.pojo.entity.Brand;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class BrandMapperTests {

    @Autowired
    BrandMapper mapper;

    @Test
    void insert(){
        Brand brand = new Brand();
        brand.setName("测试数据0100");
        brand.setPinyin("ceshishuju0100");
        brand.setLogo("wwww");
        brand.setDescription("wwwww");
        brand.setKeywords("关键词1，关键词2，关键词3");
        brand.setSort(100);
        brand.setSales(11);
        brand.setProductCount(222);
        brand.setCommentCount(333);
        brand.setPositiveCommentCount(344);
        brand.setEnable(0);
        brand.setGmtCreate(LocalDateTime.now());
        brand.setGmtModified(LocalDateTime.now());

        System.out.println("插入数据之前，参数：" + brand);
        int rows = mapper.insert(brand);
        System.out.println("插入数据完成，受影响的行数：" + rows);
        System.out.println("插入数据之后，参数：" + brand);
    }
}
