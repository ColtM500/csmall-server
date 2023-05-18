package cn.tedu.csmall.product.mapper;

import cn.tedu.csmall.product.pojo.entity.Album;
import cn.tedu.csmall.product.pojo.entity.AttributeTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class AttributeTemplateMapperTests {

    @Autowired(required = false)
    AttributeTemplateMapper mapper;

    @Test
    void insert() {
        AttributeTemplate attributeTemplate = new AttributeTemplate();
        attributeTemplate.setName("测试数据0100");
        attributeTemplate.setPinyin("ceshishuju0100");
        attributeTemplate.setKeywords("关键词1，关键词2，关键词3");
        attributeTemplate.setSort(100);
        attributeTemplate.setGmtCreate(LocalDateTime.now());
        attributeTemplate.setGmtModified(LocalDateTime.now());

        System.out.println("插入数据之前，参数：" + attributeTemplate);
        int rows = mapper.insert(attributeTemplate);
        System.out.println("插入数据完成，受影响的行数：" + rows);
        System.out.println("插入数据之后，参数：" + attributeTemplate);
    }

}
