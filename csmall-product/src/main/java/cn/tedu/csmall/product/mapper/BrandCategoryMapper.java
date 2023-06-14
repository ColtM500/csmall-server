package cn.tedu.csmall.product.mapper;

import cn.tedu.csmall.product.pojo.entity.BrandCategory;
import cn.tedu.csmall.product.pojo.vo.BrandCategoryListItemVO;
import cn.tedu.csmall.product.pojo.vo.BrandCategoryStandardVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandCategoryMapper extends BaseMapper<BrandCategory> {

    int countByCategoryId(Long categoryId);

    int countByBrandId(Long brandId);

    /**
     * 批量插入品牌与类别的关联数据
     *
     * @param brandCategoryList 若干个品牌与类别的关联数据的集合
     * @return 受影响的行数
     */
    int insertBatch(List<BrandCategory> brandCategoryList);

    /**
     * 根据id查询品牌与类别的关联数据详情
     *
     * @param id 品牌与类别的关联数据的ID
     * @return 匹配的品牌与类别的关联数据详情，如果没有匹配的数据，则返回null
     */
    BrandCategoryStandardVO getStandardById(Long id);

    /**
     * 查询品牌与类别的关联数据列表
     *
     * @return 品牌与类别的关联数据列表
     */
    List<BrandCategoryListItemVO> list();
}
