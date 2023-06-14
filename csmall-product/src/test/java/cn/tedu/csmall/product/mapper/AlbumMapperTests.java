package cn.tedu.csmall.product.mapper;

import cn.tedu.csmall.product.pojo.entity.Album;
import cn.tedu.csmall.product.pojo.vo.AlbumListItemVO;
import cn.tedu.csmall.product.pojo.vo.AlbumStandardVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@SpringBootTest
public class AlbumMapperTests {

    @Autowired
    AlbumMapper mapper;

    @Test
    void insertBatch() {
        List<Album> albums = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Album album = new Album();
            album.setName("批量插入测试数据" + i);
            album.setDescription("批量插入测试数据的简介" + i);
            album.setSort(200);
            albums.add(album);
        }

        int rows = mapper.insertBatch(albums);
        log.debug("批量插入完成，受影响的行数：{}", rows);
    }

    @Test
    void insert() {
        Album album = new Album();
        album.setName("测试数据0100");
        album.setDescription("测试数据简介0100");
        album.setSort(100);
        album.setGmtCreate(LocalDateTime.now());
        album.setGmtModified(LocalDateTime.now());

        System.out.println("插入数据之前，参数：" + album);
        int rows = mapper.insert(album);
        System.out.println("插入数据完成，受影响的行数：" + rows);
        System.out.println("插入数据之后，参数：" + album);
    }

    @Test
    void list(){
        List<?> list = mapper.list();
        System.out.println("查询列表完成，结果集中的数据量: " + list.size());
        for (Object item : list) {
            System.out.println(item);
        }
    }

    @Test
    void deleteById(){
        Long id = 35L;
        mapper.deleteById(id);
        System.out.println("删除数据完成!");
    }

    @Test
    void updateById(){
        Album album = new Album();
        album.setId(30L);
        album.setName("nmsl666");
        album.setDescription("xxx1,xxx2,xxx3");
        album.setSort(199);
        album.setGmtModified(LocalDateTime.now());
        int rows = mapper.updateById(album);
        log.debug("受影响的行数为:{}",rows);
    }

    @Test
    void getStandardById(){
        Long id = 1L;
        Object queryResult = mapper.getStandardById(id);
        System.out.println("根据【ID="+id+"】查询数据完成，结果： "+queryResult);

    }

}
