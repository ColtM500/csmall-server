package cn.tedu.csmall.product.mapper;

import cn.tedu.csmall.product.pojo.entity.Spu;
import cn.tedu.csmall.product.pojo.vo.SpuFullInfoVO;
import cn.tedu.csmall.product.pojo.vo.SpuListItemVO;
import cn.tedu.csmall.product.pojo.vo.SpuStandardVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Repository
public interface SpuMapper extends BaseMapper<Spu> {

    /**
     * 根据类别id统计SPU的数量
     * @param categoryId
     * @return
     */
    int countByCategoryId(Long categoryId);

    int countByBrandId(Long brandId);

    int countByAttributeTemplateId(Long attributeTemplateId);

    SpuStandardVO getStandardById(Long id);

    SpuFullInfoVO getFullInfoById(Long id);

    List<SpuListItemVO> list();
}
