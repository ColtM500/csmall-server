package cn.tedu.csmall.product.service.impl;

import cn.tedu.csmall.commons.ex.ServiceException;
import cn.tedu.csmall.commons.web.ServiceCode;
import cn.tedu.csmall.product.mapper.AttributeTemplateMapper;
import cn.tedu.csmall.product.pojo.entity.AttributeTemplate;
import cn.tedu.csmall.product.pojo.param.AttributeTemplateAddNewParam;
import cn.tedu.csmall.product.pojo.vo.AttributeTemplateStandardVO;
import cn.tedu.csmall.product.service.IAttributeTemplateService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class AttributeTemplateService implements IAttributeTemplateService {

    @Autowired
    private AttributeTemplateMapper attributeTemplateMapper;

    @Override
    public void addNew(AttributeTemplateAddNewParam attributeTemplateAddNewParam) {
        log.debug("开始处理【添加属性模板】的业务，参数:{}", attributeTemplateAddNewParam);
        //检查属性模板名称是否被占用 如果被占用 则抛出异常
        QueryWrapper<AttributeTemplate> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", attributeTemplateAddNewParam.getName());  //name='参数中的属性模板名称'
        int countByName = attributeTemplateMapper.selectCount(queryWrapper);
        log.debug("根据属性模板名称统计匹配的属性模板数量，结果:{}", countByName);
        if (countByName>0){
            String message = "添加属性模板失败，属性模板名称已被占用";
            throw new ServiceException(ServiceCode.ERROR_CONFLICT,message);
        }

        //将属性模板数据写入到数据库中
        AttributeTemplate attributeTemplate = new AttributeTemplate();
        BeanUtils.copyProperties(attributeTemplateAddNewParam, attributeTemplate);
        attributeTemplate.setGmtCreate(LocalDateTime.now());
        attributeTemplate.setGmtModified(LocalDateTime.now());

        int rows = attributeTemplateMapper.insert(attributeTemplate);
        if (rows!=1){
            String message = "添加属性模板失败，服务器忙，请稍后再试！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_INSERT, message);
        }
        log.debug("将新的属性模板数据写入到数据库中，完成!");
    }

    @Override
    public void deleteById(Long id) {
        log.debug("开始处理【根据ID删除属性模板】的业务，参数：{}", id);
        //检查尝试删除的属性是否存在

    }

    @Override
    public AttributeTemplateStandardVO getStandardById(Long id) {
        log.debug("开始处理【根据ID查询属性模板详情】的业务");
        AttributeTemplateStandardVO attributeTemplate = attributeTemplateMapper.getStandardById(id);
        if (attributeTemplate==null){
            String message = "查询属性模板详情失败，尝试访问的数据不存在!";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
        }
        return attributeTemplate;
    }
}
