package cn.tedu.csmall.product.mapper;

import cn.tedu.csmall.product.pojo.entity.AttributeTemplate;
import cn.tedu.csmall.product.pojo.vo.AttributeTemplateListItemVO;
import cn.tedu.csmall.product.pojo.vo.AttributeTemplateStandardVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;

@Repository
public interface AttributeTemplateMapper extends BaseMapper<AttributeTemplate> {

    @NotNull(message = "id不可为0!")
    int deleteById(Long id);

    AttributeTemplateStandardVO getStandardById(Long id);

    int countByNameAndNotId(String name ,Long id);

    List<AttributeTemplateListItemVO> list();

    /**
     * 批量插入属性模板数据
     *
     * @param albumList 若干个属性模板数据的集合
     * @return 受影响的行数
     */
    int insertBatch(List<AttributeTemplate> albumList);
}
