package cn.tedu.csmall.product.mapper;

import cn.tedu.csmall.product.pojo.entity.CategoryAttributeTemplate;
import cn.tedu.csmall.product.pojo.vo.CategoryAttributeTemplateListItemVO;
import cn.tedu.csmall.product.pojo.vo.CategoryAttributeTemplateStandardVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryAttributeTemplateMapper extends BaseMapper<CategoryAttributeTemplate> {

    int countByCategoryId(Long categoryId);

    int countByAttributeTemplateId(Long attributeTemplateId);

    /**
     * 批量插入类别与属性模板的关联数据
     *
     * @param categoryAttributeTemplateList 若干个类别与属性模板的关联数据的集合
     * @return 受影响的行数
     */
    int insertBatch(List<CategoryAttributeTemplate> categoryAttributeTemplateList);

    /**
     * 根据id查询类别与属性模板的关联数据详情
     *
     * @param id 类别与属性模板的关联数据的ID
     * @return 匹配的类别与属性模板的关联数据详情，如果没有匹配的数据，则返回null
     */
    CategoryAttributeTemplateStandardVO getStandardById(Long id);

    /**
     * 查询类别与属性模板的关联的数据列表
     *
     * @return 类别与属性模板的关联的数据列表
     */
    List<CategoryAttributeTemplateListItemVO> list();
}
