package cn.tedu.csmall.product.service;

import cn.tedu.csmall.commons.pojo.vo.PageData;
import cn.tedu.csmall.product.pojo.param.AttributeTemplateAddNewParam;
import cn.tedu.csmall.product.pojo.vo.*;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface IAttributeTemplateService {
    void addNew(AttributeTemplateAddNewParam attributeTemplateAddNewParam);

    void deleteById(Long id);

    AttributeTemplateStandardVO getStandardById(Long id);

    void updateInfoById(Long id, AttributeTemplateUpdateInfoParam attributeTemplateUpdateInfoParam);

    /**
     * 查询属性模板列表，将使用默认的每页记录数
     *
     * @param pageNum 页码
     * @return 属性模板列表的集合
     */
    PageData<AttributeTemplateListItemVO> list(Integer pageNum);

    /**
     * 查询属性模板列表
     *
     * @param pageNum  页码
     * @param pageSize 每页记录数
     * @return 属性模板列表的集合
     */
    PageData<AttributeTemplateListItemVO> list(Integer pageNum, Integer pageSize);
}
