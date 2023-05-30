package cn.tedu.csmall.product.mapper;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class SpuMapperTests {

    @Autowired
    SpuMapper mapper;

    @Test
    void countByCategoryId(){
        Long categoryId = 7L;
        int count = mapper.countByCategoryId(categoryId);
        log.debug("根据类别【{}】统计关联数据的数量：{}", categoryId, count);
    }
}
