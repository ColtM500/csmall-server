package cn.tedu.csmall.product.mapper;

import cn.tedu.csmall.product.pojo.entity.BrandCategory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class BrandCategoryTests {

    @Autowired
    BrandCategoryMapper brandCategoryMapper;

    @Test
    void countByBrand() {
        Long brandId = 1L;
        int count = brandCategoryMapper.countByBrand(brandId);
        log.debug("根据品牌【{}】统计关联数据的数量：{}", brandId, count);
    }
}
