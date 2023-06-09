package cn.tedu.csmall.product.service;

import cn.tedu.csmall.commons.ex.ServiceException;
import cn.tedu.csmall.commons.pojo.vo.PageData;
import cn.tedu.csmall.product.pojo.param.SkuAddNewParam;
import cn.tedu.csmall.product.pojo.param.SpuAddNewParam;
import cn.tedu.csmall.product.pojo.vo.CategoryStandardVO;
import cn.tedu.csmall.product.pojo.vo.SkuStandardVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class SkuServiceTests {

    @Autowired
    private ISkuService service;

    @Test
    void addNew() {
        SkuAddNewParam skuAddNewParam = new SkuAddNewParam();
        skuAddNewParam.setSpuId(1L);
        skuAddNewParam.setBarCode("sb");
        skuAddNewParam.setAlbumId(1L);
        skuAddNewParam.setSort(20);

        try {
            service.addNew(skuAddNewParam);
        } catch (ServiceException e) {
            log.error(e.getMessage());
        }
    }

    @Test
    void getStandardById() {
        Long id = 1L;

        try {
            SkuStandardVO skuStandardVO = service.getStandardById(id);
            log.debug("查询类别详情成功！结果：{}", skuStandardVO);
        } catch (ServiceException e) {
            log.debug("捕获到异常，其中的消息：{}", e.getMessage());
        }
    }

    @Test
    void list() {
        try {
            Integer pageNum = 1;
            Integer pageSize = 5;
            Long spuId = 1L;
            PageData<?> pageData = service.listBySpuId(spuId,pageNum, pageSize);
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
