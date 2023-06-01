package cn.tedu.csmall.product.mapper;

import cn.tedu.csmall.product.pojo.entity.Picture;
import cn.tedu.csmall.product.pojo.vo.PictureListItemVO;
import cn.tedu.csmall.product.pojo.vo.PictureUpdateListItemVO;
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

    @Test
    void updateNotCoverByAlbumId() {
        PictureUpdateListItemVO pictureUpdateListItemVO = mapper.update();
        pictureUpdateListItemVO.setIsCover(1);
        System.out.println("更新完成，受影响的行数：" + pictureUpdateListItemVO);
    }

    @Test
    void getStandardById() {
        Long id = 1L;
        Object queryResult = mapper.getStandardById(id);
        log.debug("根据id【{}】查询数据详情完成，查询结果：{}", id, queryResult);
    }

    @Test
    void list() {
        Long albumId = 1L;
        List<PictureListItemVO> list = mapper.listByAlbumId(albumId);
        log.debug("查询列表完成，结果：{}", list);
        for (Object item : list) {
            log.debug("列表项：{}", item);
        }
    }

}