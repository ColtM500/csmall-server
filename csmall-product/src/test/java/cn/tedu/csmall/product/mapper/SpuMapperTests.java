package cn.tedu.csmall.product.mapper;

import cn.tedu.csmall.product.pojo.vo.PictureListItemVO;
import cn.tedu.csmall.product.pojo.vo.SpuFullInfoVO;
import cn.tedu.csmall.product.pojo.vo.SpuListItemVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

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

    @Test
    void countByBrandId(){
        Long brandId = 1L;
        int count = mapper.countByBrandId(brandId);
        log.debug("根据类别【{}】统计关联数据的数量：{}", brandId, count);
    }

    @Test
    void countByAttributeTemplateId(){
        Long templateId = 1L;
        int count = mapper.countByAttributeTemplateId(templateId);
        log.debug("根据类别【{}】统计关联数据的数量：{}", templateId, count);
    }

    @Test
    void getStandardById() {
        Long id = 1L;
        Object queryResult = mapper.getStandardById(id);
        log.debug("根据id【{}】查询数据详情完成，查询结果：{}", id, queryResult);
    }

    @Test
    void getFullInfoById(){
        Long id = 1L;
        SpuFullInfoVO fullInfoById = mapper.getFullInfoById(id);
        log.debug("根据id【{}】查询数据详情完成，查询结果：{}", id, fullInfoById);
    }

    @Test
    void list() {
        List<SpuListItemVO> list = mapper.list();
        log.debug("查询列表完成，结果：{}", list);
        for (Object item : list) {
            log.debug("列表项：{}", item);
        }
    }
}
