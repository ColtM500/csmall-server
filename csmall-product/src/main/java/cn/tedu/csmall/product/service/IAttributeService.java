package cn.tedu.csmall.product.service;

import cn.tedu.csmall.commons.pojo.vo.PageData;
import cn.tedu.csmall.product.pojo.param.AttributeAddNewParam;
import cn.tedu.csmall.product.pojo.vo.AttributeListItemVO;
import cn.tedu.csmall.product.pojo.vo.AttributeStandardVO;
import cn.tedu.csmall.product.pojo.vo.AttributeUpdateInfoParam;
import cn.tedu.csmall.product.pojo.vo.CategoryListItemVO;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface IAttributeService {

    /**
     * 添加属性
     *
     * @param attributeAddNewParam 添加的属性对象
     */
    void addNew(AttributeAddNewParam attributeAddNewParam);

    /**
     * 根据ID删除属性
     *
     * @param id 属性ID
     */
    void delete(Long id);

    /**
     * 修改属性基本资料
     *
     * @param id                       属性ID
     * @param attributeUpdateInfoParam 封装了新基本资料的对象
     */
    void updateInfoById(Long id, AttributeUpdateInfoParam attributeUpdateInfoParam);

    /**
     * 根据id获取属性的标准信息
     *
     * @param id 属性id
     * @return 匹配的属性的标准信息，如果没有匹配的数据，将返回null
     */
    AttributeStandardVO getStandardById(Long id);

    PageData<AttributeListItemVO> listByTemplateId(Long parentId, Integer pageNum);

    PageData<AttributeListItemVO> listByTemplateId(Long parentId, Integer pageNum, Integer pageSize);
}
