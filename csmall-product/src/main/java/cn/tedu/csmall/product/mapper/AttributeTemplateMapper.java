package cn.tedu.csmall.product.mapper;

import cn.tedu.csmall.product.pojo.entity.AttributeTemplate;
import cn.tedu.csmall.product.pojo.vo.AttributeTemplateStandardVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;

@Repository
public interface AttributeTemplateMapper extends BaseMapper<AttributeTemplate> {

    @NotNull(message = "id不可为0!")
    int deleteById(Long id);

    AttributeTemplateStandardVO getStandardById(Long id);
}
