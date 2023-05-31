package cn.tedu.csmall.product.service;

import cn.tedu.csmall.commons.ex.ServiceException;
import cn.tedu.csmall.commons.pojo.vo.PageData;
import cn.tedu.csmall.product.pojo.param.CategoryAddNewParam;
import cn.tedu.csmall.product.pojo.param.CategoryUpdateInfoParam;
import cn.tedu.csmall.product.pojo.vo.CategoryStandardVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
public class CategoryServiceTests {

    @Autowired
    ICategoryService service;

    @Test
    void addNew() {
        CategoryAddNewParam categoryAddNewParam = new CategoryAddNewParam();
        categoryAddNewParam.setName("测试数据-00004");
        categoryAddNewParam.setParentId(0L);
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

    @Test
    void delete() {
        Long id = 92L;
        try {
            service.delete(id);
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void listByParentId() {
        try {
            Long parentId = 0L;
            Integer pageNum = 1;
            Integer pageSize = 5;
            PageData<?> pageData = service.listByParentId(parentId, pageNum, pageSize);
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
    void setEnable() {
        Long id = 1L;

        try {
            service.setEnable(id);
            log.debug("启用类别成功！");
        } catch (ServiceException e) {
            log.debug("捕获到异常，其中的消息：{}", e.getMessage());
        }
    }

    @Test
    void setDisable() {
        Long id = 1L;

        try {
            service.setDisable(id);
            log.debug("禁用类别成功！");
        } catch (ServiceException e) {
            log.debug("捕获到异常，其中的消息：{}", e.getMessage());
        }
    }

    @Test
    void listTree() {
        List<?> list = service.listTree();
        log.debug("查询列表完成");
        for (Object item : list) {
            log.debug("列表项：{}", item);
        }
    }

    @Test
    void updateInfoById() {
        Long id = 90L;
        CategoryUpdateInfoParam categoryUpdateInfoParam = new CategoryUpdateInfoParam();
        categoryUpdateInfoParam.setName("新-类别");
        categoryUpdateInfoParam.setKeywords("新-关键词");
        categoryUpdateInfoParam.setSort(188);

        try {
            service.updateStandardById(id, categoryUpdateInfoParam);
            log.debug("测试修改数据成功！");
        } catch (ServiceException e) {
            log.debug(e.getMessage());
        }
    }

    @Test
    void getStandardById() {
        Long id = 90L;

        try {
            CategoryStandardVO category = service.getStandardById(id);
            log.debug("查询类别详情成功！结果：{}", category);
        } catch (ServiceException e) {
            log.debug("捕获到异常，其中的消息：{}", e.getMessage());
        }
    }
}
