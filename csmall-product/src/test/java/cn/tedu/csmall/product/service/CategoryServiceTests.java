package cn.tedu.csmall.product.service;

import cn.tedu.csmall.commons.ex.ServiceException;
import cn.tedu.csmall.product.pojo.param.CategoryAddNewParam;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CategoryServiceTests {

    @Autowired
    ICategoryService service;

    @Test
    void addNew(){
        CategoryAddNewParam categoryAddNewParam = new CategoryAddNewParam();
        categoryAddNewParam.setName("测试数据-00003");
        categoryAddNewParam.setDepth(1);
        categoryAddNewParam.setKeywords("www,ww,eee");
        categoryAddNewParam.setSort(99);

        try {
            service.addNew(categoryAddNewParam);
            System.out.println("添加成功!");
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
        } catch (Throwable throwable) {
            System.out.println("添加失败！出现了某种异常！");
            throwable.printStackTrace();
        }
    }
}
