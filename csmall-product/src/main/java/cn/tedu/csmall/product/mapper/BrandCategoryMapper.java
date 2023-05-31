package cn.tedu.csmall.product.mapper;

import cn.tedu.csmall.product.pojo.entity.BrandCategory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandCategoryMapper extends BaseMapper<BrandCategory> {

    int countByCategoryId(Long categoryId);

    int countByBrand(Long brandId);
}
