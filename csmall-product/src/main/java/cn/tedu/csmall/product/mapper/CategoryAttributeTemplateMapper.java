package cn.tedu.csmall.product.mapper;

import cn.tedu.csmall.product.pojo.entity.CategoryAttributeTemplate;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryAttributeTemplateMapper extends BaseMapper<CategoryAttributeTemplate> {

    int countByCategoryId(Long categoryId);

    int countByAttributeTemplateId(Long attributeTemplateId);
}
