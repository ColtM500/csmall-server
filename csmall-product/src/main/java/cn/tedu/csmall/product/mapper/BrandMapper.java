package cn.tedu.csmall.product.mapper;

import cn.tedu.csmall.product.pojo.entity.Brand;
import cn.tedu.csmall.product.pojo.vo.BrandListItemVO;
import cn.tedu.csmall.product.pojo.vo.BrandStandardVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandMapper extends BaseMapper<Brand> {

    BrandStandardVO getStandardById(Long id);

    int countByNameAndNotId(@Param("id")Long id, @Param("name")String name);

    List<BrandListItemVO> list();

    /**
     * 批量插入品牌数据
     *
     * @param brandList 若干个品牌数据的集合
     * @return 受影响的行数
     */
    int insertBatch(List<Brand> brandList);
}
