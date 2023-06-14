package cn.tedu.csmall.product.mapper;

import cn.tedu.csmall.commons.pojo.vo.PageData;
import cn.tedu.csmall.product.pojo.entity.CategoryAttributeTemplate;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@SpringBootTest
public class CategoryAttributeTemplateMapperTests {

    @Autowired
    CategoryAttributeTemplateMapper mapper;

    @Test
    void countByCategoryId(){
        int count = mapper.countByCategoryId(7L);
        log.debug("根据类别id查询品牌个数为:{}", count);
    }

    @Test
    void countByAttributeTemplateId(){
        int count = mapper.countByAttributeTemplateId(1L);
        log.debug("根据类别id查询品牌个数为:{}", count);
    }

    @Test
    void insertBatch() {
        List<CategoryAttributeTemplate> categoryAttributeTemplateList = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            CategoryAttributeTemplate categoryAttributeTemplate = new CategoryAttributeTemplate();
            categoryAttributeTemplate.setCategoryId(i + 0L);
            categoryAttributeTemplate.setAttributeTemplateId(i + 0L);
            categoryAttributeTemplateList.add(categoryAttributeTemplate);
        }

        int rows = mapper.insertBatch(categoryAttributeTemplateList);
        log.debug("批量插入完成，受影响的行数：{}", rows);
    }

    @Test
    void getStandardById() {
        Long id = 1L;
        Object queryResult = mapper.getStandardById(id);
        log.debug("根据id【{}】查询数据详情完成，查询结果：{}", id, queryResult);
    }

    @Test
    void list(){
        List<?> list = mapper.list();
        System.out.println("查询列表完成，结果集中的数据量: " + list.size());
        for (Object item : list) {
            System.out.println(item);
        }
    }
}
