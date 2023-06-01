package cn.tedu.csmall.product.util;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Random;

/**
 * ID工具类
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Component
public class IdUtils {
    /**
     * 生成SPU的ID
     *
     * @return 自动编号的新ID
     */
    public synchronized long generateSpuId() {
        Random random = new Random();
        Long id = Math.abs(random.nextLong());
        return id;
    }

}
