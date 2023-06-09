package cn.tedu.csmall.product.mapper;

import cn.tedu.csmall.product.pojo.entity.Sku;
import cn.tedu.csmall.product.pojo.vo.PictureListItemVO;
import cn.tedu.csmall.product.pojo.vo.SkuListItemVO;
import cn.tedu.csmall.product.pojo.vo.SkuStandardVO;
import cn.tedu.csmall.product.pojo.vo.SpuStandardVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkuMapper extends BaseMapper<Sku> {

    SkuStandardVO getStandardById(Long id);

    List<SkuListItemVO> listBySpuId(Long spuId);

}
