package cn.tedu.csmall.product.mapper;

import cn.tedu.csmall.product.pojo.entity.Picture;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@SpringBootTest
public class PictureMapperTests {

    @Autowired
    PictureMapper mapper;

    @Test
    void insert() {
        Picture picture = new Picture();
        picture.setDescription("测试数据001");
        picture.setSort(255);

        log.debug("插入数据之前，参数：{}", picture);
        int rows = mapper.insert(picture);
        log.debug("插入数据完成，受影响的行数：{}", rows);
        log.debug("插入数据之后，参数：{}", picture);
    }

    @Test
    void insertBatch() {
        List<Picture> pictures = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Picture attribute = new Picture();
            attribute.setDescription("批量插入测试数据" + i);
            attribute.setSort(200);
            pictures.add(attribute);
        }

        int rows = mapper.insertBatch(pictures);
        log.debug("批量插入完成，受影响的行数：{}", rows);
    }

//    @Test
//    void countByAlbumId() {
//        Long albumId = 21L;
//        int count = mapper.countByAlbum(albumId);
//        log.debug("统计完成，根据相册【{}】统计图片的数量，结果：{}", albumId, count);
//    }

    @Test
    void getStandardById() {
        Long id = 1L;
        Object queryResult = mapper.getStandardById(id);
        log.debug("根据id【{}】查询数据详情完成，查询结果：{}", id, queryResult);
    }

//    @Test
//    void list() {
//        Long albumId = 1L;
//        Integer pageNum = 1;
//        Integer pageSize = 5;
//        PageData<?> pageData = repository.listByAlbumId(albumId, pageNum, pageSize);
//        List<?> list = pageData.getList();
//        log.debug("查询列表完成，结果：{}", pageData);
//        for (Object item : list) {
//            log.debug("列表项：{}", item);
//        }
//    }

}