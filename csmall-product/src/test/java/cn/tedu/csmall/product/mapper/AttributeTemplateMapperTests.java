package cn.tedu.csmall.product.mapper;

import cn.tedu.csmall.product.pojo.entity.Album;
import cn.tedu.csmall.product.pojo.entity.AttributeTemplate;
import cn.tedu.csmall.product.pojo.vo.AttributeTemplateListItemVO;
import cn.tedu.csmall.product.pojo.vo.BrandListItemVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
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

    @Test
    void deleteById() {
        Long id = 15L;
        int rows = mapper.deleteById(id);
        System.out.println(rows);
    }

    @Test
    void countByNameAndNotId(){
        String name = "测试数据0100";
        Long attributeTemplateId = 18L;
        int count = mapper.countByNameAndNotId(name,attributeTemplateId);
        log.debug("根据名字【{}】，,attributeTemplateId【{}】查询数据详情完成，查询结果：{}",name, attributeTemplateId,count);
    }

    @Test
    void list() {
        List<AttributeTemplateListItemVO> list = mapper.list();
        log.debug("查询列表完成，结果：{}", list);
        for (Object item : list) {
            log.debug("列表项：{}", item);
        }
    }
}
