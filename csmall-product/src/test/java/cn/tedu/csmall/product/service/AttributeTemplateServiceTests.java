package cn.tedu.csmall.product.service;


import cn.tedu.csmall.commons.ex.ServiceException;
import cn.tedu.csmall.product.pojo.param.AttributeTemplateAddNewParam;
import cn.tedu.csmall.product.pojo.vo.AttributeTemplateStandardVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
}
