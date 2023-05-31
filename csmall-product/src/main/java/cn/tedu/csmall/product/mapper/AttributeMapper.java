package cn.tedu.csmall.product.mapper;


import cn.tedu.csmall.product.pojo.entity.Attribute;
import cn.tedu.csmall.product.pojo.vo.AttributeListItemVO;
import cn.tedu.csmall.product.pojo.vo.AttributeStandardVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttributeMapper {

    /**
     * 批量插入属性数据
     *
     * @param attributeList 若干个属性数据的集合
     * @return 受影响的行数
     */
    int insertBatch(List<Attribute> attributeList);

    /**
     * 根据id查询属性数据详情
     *
     * @param id 属性ID
     * @return 匹配的属性数据详情，如果没有匹配的数据，则返回null
     */
    AttributeStandardVO getStandardById(Long id);

    /**
     * 查询属性数据列表
     *
     * @return 属性数据列表
     */
    List<AttributeListItemVO> list();

    /**
     * 根据属性模板id查询属性列表
     *
     * @param templateId 属性模板ID
     * @return 属性列表的集合
     */
    List<AttributeListItemVO> listByTemplateId(Long templateId);

    int countByNameAndTemplate(String name, Long templateId);

}
