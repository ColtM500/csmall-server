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
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    @Transactional // 添加此注解，可使得插入的数据不保存，以避免不修改测试数据时反复执行导致的主键冲突错误
    @Test
    void insertBatch() {
        List<Sku> skuList = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Sku sku = new Sku();
            sku.setId(i + 0L);
            sku.setTitle("批量插入测试数据" + i);
            skuList.add(sku);
        }

        int rows = mapper.insertBatch(skuList);
        log.debug("批量插入完成，受影响的行数：{}", rows);
    }
}
