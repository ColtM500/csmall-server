package cn.tedu.csmall.product.mapper;

import cn.tedu.csmall.product.pojo.entity.Spu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

@Repository
public interface SpuMapper extends BaseMapper<Spu> {

    /**
     * 根据类别id统计SPU的数量
     * @param categoryId
     * @return
     */
    int countByCategoryId(Long categoryId);
}
