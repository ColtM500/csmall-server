package cn.tedu.csmall.product.service;


import cn.tedu.csmall.commons.ex.ServiceException;
import cn.tedu.csmall.commons.pojo.vo.PageData;
import cn.tedu.csmall.product.pojo.param.AttributeTemplateAddNewParam;
import cn.tedu.csmall.product.pojo.vo.AttributeTemplateStandardVO;
import cn.tedu.csmall.product.pojo.vo.AttributeTemplateUpdateInfoParam;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
public class AttributeTemplateServiceTests {

    @Autowired
    IAttributeTemplateService service;

    @Test
    void addNew(){
        AttributeTemplateAddNewParam attributeTemplateAddNewParam = new AttributeTemplateAddNewParam();
        attributeTemplateAddNewParam.setName("测试数据-00003");
        attributeTemplateAddNewParam.setPinyin("测试数据简介-00003");
        attributeTemplateAddNewParam.setKeywords("www,ww,eee");
        attributeTemplateAddNewParam.setSort(99);

        try {
            service.addNew(attributeTemplateAddNewParam);
            System.out.println("添加成功!");
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
        } catch (Throwable throwable) {
            System.out.println("添加失败！出现了某种异常！");
            throwable.printStackTrace();
        }
    }

    @Test
    void getStandardById(){
        Long id = 1L;
        try {
            AttributeTemplateStandardVO standardById = service.getStandardById(id);
            log.debug("根据id【{}】查询完成，查询结果:{}",standardById);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void list() {
        try {
            Integer pageNum = 1;
            Integer pageSize = 5;
            PageData<?> pageData = service.list(pageNum, pageSize);
            List<?> list = pageData.getList();
            log.debug("查询列表完成，结果：{}", pageData);
            for (Object item : list) {
                log.debug("列表项：{}", item);
            }
        } catch (ServiceException e) {
            log.debug("捕获到异常，其中的消息：{}", e.getMessage());
        }
    }

    @Test
    void updateInfoById() {
        Long id = 14L;
        AttributeTemplateUpdateInfoParam attributeTemplateUpdateInfoParam = new AttributeTemplateUpdateInfoParam();
        attributeTemplateUpdateInfoParam.setName("新-属性模板");
        attributeTemplateUpdateInfoParam.setKeywords("新-关键词");
        attributeTemplateUpdateInfoParam.setSort(188);

        try {
            service.updateInfoById(id, attributeTemplateUpdateInfoParam);
            log.debug("测试修改数据成功！");
        } catch (ServiceException e) {
            log.debug(e.getMessage());
        }
    }
}
