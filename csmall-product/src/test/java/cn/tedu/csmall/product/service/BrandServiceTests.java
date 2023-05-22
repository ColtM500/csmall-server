package cn.tedu.csmall.product.service;

import cn.tedu.csmall.product.ex.ServiceException;
import cn.tedu.csmall.product.pojo.entity.Brand;
import cn.tedu.csmall.product.pojo.param.AttributeTemplateAddNewParam;
import cn.tedu.csmall.product.pojo.param.BrandAddNewParam;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BrandServiceTests {

    @Autowired
    IBrandService service;

    @Test
    void addNew(){
        BrandAddNewParam brandAddNewParam = new BrandAddNewParam();
        brandAddNewParam.setName("测试数据-00003");
        brandAddNewParam.setPinyin("测试数据简介-00003");
        brandAddNewParam.setKeywords("www,ww,eee");
        brandAddNewParam.setSort(99);

        try {
            service.addNew(brandAddNewParam);
            System.out.println("添加成功!");
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
        } catch (Throwable throwable) {
            System.out.println("添加失败！出现了某种异常！");
            throwable.printStackTrace();
        }
    }
}
