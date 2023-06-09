package cn.tedu.csmall.product.mapper;

import cn.tedu.csmall.commons.pojo.vo.PageData;
import cn.tedu.csmall.product.pojo.entity.Category;
import cn.tedu.csmall.product.pojo.vo.CategoryListItemVO;
import cn.tedu.csmall.product.pojo.vo.CategoryStandardVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryMapper extends BaseMapper<Category> {

    int insertBatch(List<Category> categories);

    CategoryStandardVO getStandardById(Long id);

    List<CategoryListItemVO> list();

    List<CategoryListItemVO> listByParentId(Long parentId);

    int countByNameAndParentId(String name, Long parentId);

    int update(Category category);

    int countByParentId(Long parentId);

    int countByNameAndNotId(@Param("id") Long id,@Param("name") String name);

}
