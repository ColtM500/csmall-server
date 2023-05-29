package cn.tedu.csmall.product.service;

import cn.tedu.csmall.product.pojo.param.AttributeTemplateAddNewParam;
import cn.tedu.csmall.product.pojo.vo.AttributeTemplateStandardVO;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface IAttributeTemplateService {
    void addNew(AttributeTemplateAddNewParam attributeTemplateAddNewParam);

    void deleteById(Long id);

    AttributeTemplateStandardVO getStandardById(Long id);
}
