package cn.tedu.csmall.product.cache.impl;

import cn.tedu.csmall.product.cache.IAlbumCacheRepository;
import cn.tedu.csmall.product.consts.AlbumCacheConsts;
import cn.tedu.csmall.product.pojo.vo.AlbumListItemVO;
import cn.tedu.csmall.product.pojo.vo.AlbumStandardVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 处理相册缓存数据的数据访问实现类
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Slf4j
@Repository
public class AlbumRedisRepositoryImpl implements IAlbumCacheRepository, AlbumCacheConsts {

    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;

    @Override
    public void save(AlbumStandardVO albumStandardVO) {
        // 将相册详情数据存入到Redis中
        String key = ITEM_KEY_PREFIX + albumStandardVO.getId();
        log.debug("即将向Redis中写入数据，Key={}，Value={}", key, albumStandardVO);
        redisTemplate.opsForValue().set(key, albumStandardVO);
        // 将以上使用的Key也存入到Redis中的Set中
        redisTemplate.opsForSet().add(ALL_ITEM_KEYS_KEY, key);
        log.debug("已经在Redis中的{}中记录下当前相册详情数据的Key：{}", ALL_ITEM_KEYS_KEY, key);
    }

    @Override
    public void save(List<AlbumListItemVO> albumList) {
        String key = LIST_KEY;
        ListOperations<String, Serializable> opsForList = redisTemplate.opsForList();
        for (AlbumListItemVO albumListItemVO : albumList) {
            log.debug("即将向Redis中写入列表项数据，Key={}，列表项={}", key, albumListItemVO);
            opsForList.rightPush(key, albumListItemVO);
        }
    }

    @Override
    public Boolean delete(Long id) {
        String key = ITEM_KEY_PREFIX + id;
        log.debug("即将删除Redis中的数据，Key={}", key);
        return redisTemplate.delete(key);
    }

    @Override
    public Boolean deleteList() {
        String key = LIST_KEY;
        log.debug("即将删除Redis中的列表数据，Key={}", key);
        return redisTemplate.delete(key);
    }

    @Override
    public long deleteAllItem() {
        // Set<String> keys = redisTemplate.keys("album:item:*");
        Set<Serializable> members = redisTemplate.opsForSet().members(ALL_ITEM_KEYS_KEY);
        Set<String> keys = new HashSet<>();
        for (Serializable member : members) {
            keys.add(member.toString());
        }
        keys.add(ALL_ITEM_KEYS_KEY);
        return redisTemplate.delete(keys);
    }

    @Override
    public AlbumStandardVO getStandardById(Long id) {
        String key = ITEM_KEY_PREFIX + id;
        log.debug("即将向Redis中取出数据，Key={}", key);
        Serializable serializable = redisTemplate.opsForValue().get(key);
        AlbumStandardVO albumStandardVO = null;
        if (serializable != null) {
            albumStandardVO = (AlbumStandardVO) serializable;
        }
        return albumStandardVO;
    }

    @Override
    public List<AlbumListItemVO> list() {
        long start = 0;
        long end = -1;
        return list(start, end);
    }

    @Override
    public List<AlbumListItemVO> list(long start, long end) {
        String key = LIST_KEY;
        List<Serializable> serializableList = redisTemplate.opsForList().range(key, start, end);
        List<AlbumListItemVO> albumList = new ArrayList<>();
        for (Serializable serializable : serializableList) {
            albumList.add((AlbumListItemVO) serializable);
        }
        return albumList;
    }
}