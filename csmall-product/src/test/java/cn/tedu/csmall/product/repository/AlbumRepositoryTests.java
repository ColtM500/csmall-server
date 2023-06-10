package cn.tedu.csmall.product.repository;

import cn.tedu.csmall.commons.pojo.vo.PageData;
import cn.tedu.csmall.product.cache.IAlbumCacheRepository;

import cn.tedu.csmall.product.pojo.entity.Album;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@SpringBootTest
public class AlbumRepositoryTests {

    @Autowired
    IAlbumCacheRepository repository;


}