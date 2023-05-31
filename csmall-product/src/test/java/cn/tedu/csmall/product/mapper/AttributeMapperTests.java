package cn.tedu.csmall.product.mapper;

import cn.tedu.csmall.commons.pojo.vo.PageData;
import cn.tedu.csmall.product.pojo.entity.Attribute;
import cn.tedu.csmall.product.pojo.vo.AttributeListItemVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Slf4j
public class AttributeMapperTests {

    @Autowired
    private AttributeMapper mapper;

    @Test
    void insertBatch() {
        List<Attribute> attributes = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Attribute attribute = new Attribute();
            attribute.setName("批量插入测试数据" + i);
            attribute.setSort(200);
            attributes.add(attribute);
        }

        int rows = mapper.insertBatch(attributes);
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

    @Test
    void listByTemplateId() {
        Long templateId = 1L;
        List<AttributeListItemVO> list = mapper.listByTemplateId(templateId);
        log.debug("查询列表完成，结果：{}", list);
        for (Object item : list) {
            log.debug("列表项：{}", item);
        }
    }

    @Test
    void countByNameAndTemplate(){
        String name = "批量插入测试数据5";
        Long id = 1L;
        int count = mapper.countByNameAndTemplate(name, id);
        log.debug("根据id【{}】查询数据详情完成，查询结果：{}", id, count);
    }
}
