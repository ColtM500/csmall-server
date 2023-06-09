package cn.tedu.csmall.product.cache.impl;

import cn.tedu.csmall.product.cache.IAlbumCacheRepository;
import cn.tedu.csmall.product.consts.AlbumCacheConsts;
import cn.tedu.csmall.product.pojo.vo.AlbumStandardVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public class AlbumCacheRepositoryImpl implements IAlbumCacheRepository {

    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;

//    public static final String ITEM_KEY_PREFIX = "album:item";

    @Override
    public void save(AlbumStandardVO albumStandardVO) {
        String key = AlbumCacheConsts.ITEM_KEY_PREFIX + albumStandardVO.getId();//get方法取得所有id
        redisTemplate.opsForValue().set(key, albumStandardVO);//然后再set值进去
    }

    @Override
    public AlbumStandardVO getStandardById(Long id) {
        //从redis中取值
        Serializable serializable = redisTemplate.opsForValue().get(AlbumCacheConsts.ITEM_KEY_PREFIX + id);
        //强转一下类型
        AlbumStandardVO queryResult = (AlbumStandardVO) serializable;
        return queryResult;
    }

    @Override
    public boolean delete(Long id) {
        String key = AlbumCacheConsts.ITEM_KEY_PREFIX + id;
        return redisTemplate.delete(key);
    }
}
