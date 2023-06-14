package cn.tedu.csmall.product.mapper;

import cn.tedu.csmall.product.pojo.entity.BrandCategory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@SpringBootTest
public class BrandCategoryMapperTests {

    @Autowired
    BrandCategoryMapper mapper;

    @Test
    void countByCategoryId(){
        int count = mapper.countByCategoryId(7L);
        log.debug("根据类别id查询品牌个数为:{}", count);
    }

    @Test
    void countByBrandId(){
        int count = mapper.countByBrandId(7L);
        log.debug("根据类别id查询品牌个数为:{}", count);
    }

    @Test
    void insertBatch() {
        List<BrandCategory> brandCategoryList = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            BrandCategory brandCategory = new BrandCategory();
            brandCategory.setBrandId(i + 0L);
            brandCategory.setCategoryId(i + 0L);
            brandCategoryList.add(brandCategory);
        }

        int rows = mapper.insertBatch(brandCategoryList);
        log.debug("批量插入完成，受影响的行数：{}", rows);
    }
}
