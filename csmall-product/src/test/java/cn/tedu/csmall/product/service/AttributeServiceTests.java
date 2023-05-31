package cn.tedu.csmall.product.service;

import cn.tedu.csmall.commons.ex.ServiceException;
import cn.tedu.csmall.commons.pojo.vo.PageData;
import cn.tedu.csmall.product.pojo.param.AttributeAddNewParam;
import cn.tedu.csmall.product.pojo.vo.AttributeUpdateInfoParam;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
public class AttributeServiceTests {

    @Autowired
    IAttributeService service;

    @Test
    void addNew() {
        AttributeAddNewParam attributeAddNewParam = new AttributeAddNewParam();
        attributeAddNewParam.setName("大米手机的颜色属性");
        attributeAddNewParam.setTemplateId(14L);
        try {
            service.addNew(attributeAddNewParam);
            log.debug("测试添加数据成功！");
        } catch (ServiceException e) {
            log.debug(e.getMessage());
        }
    }

    @Test
    void delete() {
        Long id = 18L;

        try {
            service.delete(id);
            log.debug("测试删除数据成功！");
        } catch (ServiceException e) {
            log.debug(e.getMessage());
        }
    }

    @Test
    void updateInfoById() {
        Long id = 17L;
        AttributeUpdateInfoParam attributeUpdateInfoParam = new AttributeUpdateInfoParam();
        attributeUpdateInfoParam.setName("新-属性");
        attributeUpdateInfoParam.setSort(188);

        try {
            service.updateInfoById(id, attributeUpdateInfoParam);
            log.debug("测试修改数据成功！");
        } catch (ServiceException e) {
            log.debug(e.getMessage());
        }
    }

    @Test
    void getStandardById() {
        Long id = 1L;
        try {
            Object queryResult = service.getStandardById(id);
            log.debug("根据id【{}】查询完成，查询结果：{}", id, queryResult);
        } catch (ServiceException e) {
            log.debug(e.getMessage());
        }
    }

    @Test
    void listByTemplateId() {
        try {
            Long templateId = 1L;
            PageData<?> pageData = service.listByTemplateId(templateId,1);
            List<?> list = pageData.getList();
            log.debug("查询列表完成，结果：{}", pageData);
            for (Object item : list) {
                log.debug("列表项：{}", item);
            }
        } catch (ServiceException e) {
            log.debug("捕获到异常，其中的消息：{}", e.getMessage());
        }
    }



}
