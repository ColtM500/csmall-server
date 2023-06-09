package cn.tedu.csmall.product.mapper;

import cn.tedu.csmall.commons.pojo.vo.PageData;
import cn.tedu.csmall.product.pojo.entity.Brand;
import cn.tedu.csmall.product.pojo.vo.BrandListItemVO;
import cn.tedu.csmall.product.pojo.vo.BrandStandardVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@SpringBootTest
public class BrandMapperTests {

    @Autowired
    BrandMapper mapper;

    @Test
    void insert(){
        Brand brand = new Brand();
        brand.setName("测试数据0100");
        brand.setPinyin("ceshishuju0100");
        brand.setLogo("wwww");
        brand.setDescription("wwwww");
        brand.setKeywords("关键词1，关键词2，关键词3");
        brand.setSort(100);
        brand.setSales(11);
        brand.setProductCount(222);
        brand.setCommentCount(333);
        brand.setPositiveCommentCount(344);
        brand.setEnable(0);
        brand.setGmtCreate(LocalDateTime.now());
        brand.setGmtModified(LocalDateTime.now());

        System.out.println("插入数据之前，参数：" + brand);
        int rows = mapper.insert(brand);
        System.out.println("插入数据完成，受影响的行数：" + rows);
        System.out.println("插入数据之后，参数：" + brand);
    }

    @Test
    void insertBatch() {
        List<Brand> brands = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Brand brand = new Brand();
            brand.setName("批量插入测试数据" + i);
            brands.add(brand);
        }

        int rows = mapper.insertBatch(brands);
        log.debug("批量插入完成，受影响的行数：{}", rows);
    }

    @Test
    void getStandardById(){
        Long id = 1L;
        BrandStandardVO result = mapper.getStandardById(id);
        log.debug("根据id【{}】查询数据详情完成，查询结果：{}", id, result);
    }

    @Test
    void countByNameAndNotId() {
        Long id = 2L;
        String name = "华为";
        int count = mapper.countByNameAndNotId(id, name);
        log.debug("根据名称【{}】且非ID【{}】统计数量完成，统计结果：{}", name, id, count);
    }

    @Test
    void list() {
        List<BrandListItemVO> list = mapper.list();
        log.debug("查询列表完成，结果：{}", list);
        for (Object item : list) {
            log.debug("列表项：{}", item);
        }
    }
}
