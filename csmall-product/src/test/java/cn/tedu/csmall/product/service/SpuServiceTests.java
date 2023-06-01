package cn.tedu.csmall.product.service;

import cn.tedu.csmall.commons.ex.ServiceException;
import cn.tedu.csmall.commons.pojo.vo.PageData;
import cn.tedu.csmall.product.pojo.param.SpuAddNewParam;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

import java.util.List;

@SpringBootTest
@Slf4j
public class SpuServiceTests {

    @Autowired
    private ISpuService service;

    @Test
    void addNew() {
        SpuAddNewParam spuAddNewParam = new SpuAddNewParam();
        spuAddNewParam.setBrandId(2L);
        spuAddNewParam.setCategoryId(71L);
        spuAddNewParam.setAlbumId(1L);
        spuAddNewParam.setName("Test-002");

        try {
            service.addNew(spuAddNewParam);
        } catch (ServiceException e) {
            log.error(e.getMessage());
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
