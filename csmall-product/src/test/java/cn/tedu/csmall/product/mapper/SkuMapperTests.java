package cn.tedu.csmall.product.mapper;

import cn.tedu.csmall.commons.pojo.vo.PageData;
import cn.tedu.csmall.product.pojo.entity.Sku;
import cn.tedu.csmall.product.pojo.vo.PictureListItemVO;
import cn.tedu.csmall.product.pojo.vo.SkuListItemVO;
import cn.tedu.csmall.product.pojo.vo.SpuFullInfoVO;
import cn.tedu.csmall.product.pojo.vo.SpuListItemVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
public class SkuMapperTests {

    @Autowired
    SkuMapper mapper;

    @Test
    void getStandardById() {
        Long id = 1L;
        Object queryResult = mapper.getStandardById(id);
        log.debug("根据id【{}】查询数据详情完成，查询结果：{}", id, queryResult);
    }

    @Test
    void listBySpuId() {
        Long albumId = 1L;
        List<SkuListItemVO> list = mapper.listBySpuId(albumId);
        log.debug("查询列表完成，结果：{}", list);
        for (Object item : list) {
            log.debug("列表项：{}", item);
        }
    }
}
