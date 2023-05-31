package cn.tedu.csmall.product.service.impl;

import cn.tedu.csmall.commons.pojo.vo.PageData;
import cn.tedu.csmall.product.pojo.param.AttributeAddNewParam;
import cn.tedu.csmall.product.pojo.vo.AttributeListItemVO;
import cn.tedu.csmall.product.pojo.vo.AttributeStandardVO;
import cn.tedu.csmall.product.pojo.vo.AttributeUpdateInfoParam;
import cn.tedu.csmall.product.service.IAttributeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AttributeServiceImpl implements IAttributeService {
    @Override
    public void addNew(AttributeAddNewParam attributeAddNewParam) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void updateInfoById(Long id, AttributeUpdateInfoParam attributeUpdateInfoParam) {

    }

    @Override
    public AttributeStandardVO getStandardById(Long id) {
        return null;
    }

    @Override
    public PageData<AttributeListItemVO> listByTemplateId(Long templateId) {
        return null;
    }
}
