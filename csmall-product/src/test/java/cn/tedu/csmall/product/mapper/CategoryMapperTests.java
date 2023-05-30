package cn.tedu.csmall.product.mapper;

import cn.tedu.csmall.commons.pojo.vo.PageData;
import cn.tedu.csmall.product.pojo.entity.Category;
import cn.tedu.csmall.product.pojo.vo.CategoryListItemVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@SpringBootTest
public class CategoryMapperTests {

    @Autowired
    CategoryMapper mapper;

    @Test
    void insert(){
        Category category = new Category();
        category.setName("测试数据002");
        category.setParentId(1L);
        category.setDepth(2);
        category.setKeywords("ww");
        category.setSort(1);
        category.setIcon("ww");
        category.setEnable(0);
        category.setIsParent(1);
        category.setIsDisplay(1);
        category.setGmtCreate(LocalDateTime.now());
        category.setGmtModified(LocalDateTime.now());

        System.out.println("插入数据之前，参数：" + category);
        int rows = mapper.insert(category);
        System.out.println("插入数据完成，受影响的行数：" + rows);
        System.out.println("插入数据之后，参数：" + category);
    }

    @Test
    void insertBatch(){
        List<Category> categories = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Category category  = new Category();
            category.setName("批量插入测试数据" + i);
            category.setSort(200);
            categories.add(category);
        }

        int rows = mapper.insertBatch(categories);
        log.debug("批量插入完成，受影响的行数：{}", rows);
    }

    @Test
    void deleteById(){
        Long id = 88L;
        int rows = mapper.deleteById(id);
        log.debug("删除完成，受影响的行数：{}", rows);
    }

    @Test
    void deleteByIds(){
        List<Long> idList = new ArrayList<>();
        idList.add(85L);
        idList.add(86L);
        idList.add(87L);
        int rows = mapper.deleteBatchIds(idList);
        log.debug("批量删除完成，受影响的行数：{}", rows);
    }

    @Test
    void update(){
        Category category = new Category();
        category.setId(90L);
        category.setName("sbbbbb");

        int rows = mapper.update(category);
        System.out.println("更新完成，受影响的行数：" + rows);
    }

    @Test
    void list(){
        List<CategoryListItemVO> list = mapper.list();
        log.debug("查询列表完成，结果:{}", list);
        for (Object item: list ) {
            log.debug("列表项:{}", item);
        }
    }

    @Test
    void listByParentId(){
        Long parentId = 0L;
        List<CategoryListItemVO> categoryListItemVOS = mapper.listByParentId(parentId);
        for (Object item: categoryListItemVOS) {
            log.debug("列表项:{}", item);
        }
    }

    @Test
    void countByParentId(){
        Long parentId = 4L;
        int count = mapper.countByParentId(parentId);
        log.debug("根据父类的id{}查询到的数量有{}",parentId, count);
    }

//    @Test
//    void PageList() {
//        Integer pageNum = 1;
//        Integer pageSize = 5;
//        PageData<?> pageData = mapper.list(pageNum, pageSize);
//        List<?> list = pageData.getList();
//        log.debug("查询列表完成，结果：{}", pageData);
//        for (Object item : list) {
//            log.debug("列表项：{}", item);
//        }
//    }
//
//    @Test
//    void PageListByParentId() {
//        Long parentId = 0L;
//        Integer pageNum = 1;
//        Integer pageSize = 5;
//        PageData<?> pageData = mapper.listByParentId(parentId, pageNum, pageSize);
//        List<?> list = pageData.getList();
//        log.debug("查询列表完成，结果：{}", pageData);
//        for (Object item : list) {
//            log.debug("列表项：{}", item);
//        }
//    }
}
