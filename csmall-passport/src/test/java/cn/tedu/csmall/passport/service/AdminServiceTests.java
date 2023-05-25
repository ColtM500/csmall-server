package cn.tedu.csmall.passport.service;

import cn.tedu.csmall.passport.ex.ServiceException;
import cn.tedu.csmall.passport.pojo.param.AdminAddNewParam;
import cn.tedu.csmall.passport.pojo.vo.PageData;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
public class AdminServiceTests {

    @Autowired
    IAdminService service;

    @Test
    void addNew() {
        AdminAddNewParam adminAddNewParam = new AdminAddNewParam();
        adminAddNewParam.setUsername("测试用户名0004");
        adminAddNewParam.setPassword("测试密码0004");
        adminAddNewParam.setDescription("测试简介0004");
        adminAddNewParam.setRoleIds(new Long[]{3L, 4L, 6L});

        try {
            service.addNew(adminAddNewParam);
            System.out.println("添加成功！");
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
        } catch (Throwable e) {
            System.out.println("添加失败！出现了某种异常！");
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
}
