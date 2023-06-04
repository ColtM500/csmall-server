package cn.tedu.csmall.product;

import cn.tedu.csmall.product.mapper.AlbumMapper;
import cn.tedu.csmall.product.pojo.entity.Brand;
import cn.tedu.csmall.product.pojo.vo.AlbumStandardVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.management.monitor.StringMonitor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootTest
public class RedisTests {

    @Autowired
    RedisTemplate<String, Serializable> redisTemplate;

    //存入字符串类型的值
    @Test
    void setValue(){
        ValueOperations<String, Serializable> ops = redisTemplate.opsForValue();
        ops.set("username1", "张三");
        System.out.println("向Redis中存入数据，完成！");
    }

    //取出字符串类型的值
    @Test
    void getValue(){
        ValueOperations<String, Serializable> ops = redisTemplate.opsForValue();
        String key = "username1";
        Serializable username1 = ops.get(key);
        System.out.println("根据Key=" + key + "取出数据：" + username1);
    }

    @Autowired
    AlbumMapper albumMapper;

    //存入对象的值
    @Test
    void setObjectValue(){
        ValueOperations<String, Serializable> ops = redisTemplate.opsForValue();
        //从数据库里查到得数据
        AlbumStandardVO album = albumMapper.getStandardById(1L);
        //往redis里面写
        ops.set("album1", album);
        System.out.println("向Redis中存入数据，完成！");
    }

    //取出对象值
    @Test
    void getObjectValue(){
        ValueOperations<String, Serializable> ops = redisTemplate.opsForValue();
        String key = "brand666";
        Serializable serializable = ops.get(key);
        Brand brand666 = (Brand) serializable;
        System.out.println("根据Key=" + key + "取出数据：" + brand666);
    }

    //使用keys获取各个key
    @Test
    void keys(){
        String pattern = "*";
        Set<String> keys = redisTemplate.keys(pattern);
        System.out.println(keys);
    }

    //删除某个数据
    @Test
    void delete(){
        String key = "username1";
        Boolean delete = redisTemplate.delete(key);
        System.out.println("根据Key=" + key + "执行删除，结果：" + delete);
    }

    @Test
    void deleteBatch(){
        Set<String> keys = new HashSet<>();
        keys.add("username2");
        keys.add("username4");
        keys.add("username5");
        keys.add("username6");
        Long delete = redisTemplate.delete(keys);
        System.out.println("根据Keys=" + keys + "执行删除，结果：" + delete);
    }

    @Test
    void rightPush(){
        List<Brand> brandList = new ArrayList<>();
        for (int i = 1; i < 8; i++) {
            Brand brand = new Brand();
            brand.setId(i + 0L);
            brand.setName("测试品牌" + i);
            brandList.add(brand);
        }

        ListOperations<String, Serializable> ops = redisTemplate.opsForList();
        String key = "brandList";
        for (Brand brand : brandList ) {
            ops.rightPush(key, brand);
        }
        System.out.println("存入list数据，完成！");
    }

    // 读取list数据
    // 起始位置和结束位置都可以使用正数的下标或负数的下标
    // 但是，必须保证起始位置对应的元素在结束位置的元素的左侧
    @Test
    void range(){
        String key = "brandList";
        long start = 0;
        long end = -1;
        ListOperations<String, Serializable> ops = redisTemplate.opsForList();
        List<Serializable> list = ops.range(key, start, end);
        System.out.println("从Redis中读取list完成，数据量：" + list.size());
        for (Serializable serializable: list) {
            System.out.println(serializable);
        }
    }
}
