package cn.tedu.csmall.product.service.impl;

import cn.tedu.csmall.commons.ex.ServiceException;
import cn.tedu.csmall.commons.pojo.vo.PageData;
import cn.tedu.csmall.commons.util.PageInfoToPageDataConvert;
import cn.tedu.csmall.commons.web.ServiceCode;
import cn.tedu.csmall.product.mapper.AttributeTemplateMapper;
import cn.tedu.csmall.product.pojo.entity.AttributeTemplate;
import cn.tedu.csmall.product.pojo.param.AttributeTemplateAddNewParam;
import cn.tedu.csmall.product.pojo.vo.*;
import cn.tedu.csmall.product.service.IAttributeTemplateService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

    @Override
    public void updateInfoById(Long id, AttributeTemplateUpdateInfoParam attributeTemplateUpdateInfoParam) {
        log.debug("开始处理【修改属性模板详情】的业务，参数ID：{}, 新数据：{}", id, attributeTemplateUpdateInfoParam);
        // 检查名称是否被占用
        {
            int count = attributeTemplateMapper.countByNameAndNotId( attributeTemplateUpdateInfoParam.getName(),id);
            if (count > 0) {
                String message = "修改属性模板详情失败，属性模板名称已经被占用！";
                log.warn(message);
                throw new ServiceException(ServiceCode.ERROR_CONFLICT, message);
            }
        }

        // 调用adminMapper根据参数id执行查询
        AttributeTemplateStandardVO queryResult = attributeTemplateMapper.getStandardById(id);
        // 判断查询结果是否为null
        if (queryResult == null) {
            // 抛出ServiceException，业务状态码：40400
            String message = "修改属性模板详情失败！尝试访问的数据不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
        }

        // 创建Admin对象，将作为修改时的参数
        AttributeTemplate attributeTemplate = new AttributeTemplate();
        BeanUtils.copyProperties(attributeTemplateUpdateInfoParam, attributeTemplate);
        attributeTemplate.setId(id);

        // 调用Mapper对象的update()修改属性模板基本资料，并获取返回值
        log.debug("即将修改属性模板详情：{}", attributeTemplate);
        int rows = attributeTemplateMapper.updateById(attributeTemplate);
        // 判断返回值是否不等于1
        if (rows != 1) {
            // 是：抛出ServiceException（ERROR_INSERT）
            String message = "修改属性模板详情失败，服务器忙，请稍后再尝试！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_UPDATE, message);
        }
    }


    @Override
    public PageData<AttributeTemplateListItemVO> list(Integer pageNum) {
        log.debug("开始处理【查询品牌列表】的业务，页码：{}", pageNum);
        return list(pageNum, 5);
    }

    @Override
    public PageData<AttributeTemplateListItemVO> list(Integer pageNum, Integer pageSize) {
        log.debug("开始处理【查询品牌列表】的业务，页码：{}，每页记录数：{}", pageNum, pageSize);
        //开始分页
        PageHelper.startPage(pageNum, pageSize);
        //调用mapper进行数据库处理的分页查询
        List<AttributeTemplateListItemVO> list = attributeTemplateMapper.list();
        //将所查信息用pageInfo装起来
        PageInfo<AttributeTemplateListItemVO> pageInfo = new PageInfo<>(list);
        //用Convert的api将pageInfo转程pageData
        PageData<AttributeTemplateListItemVO> pageData = PageInfoToPageDataConvert.convert(pageInfo);
        log.debug("查询完成，即将返回：{}", pageData);
        return pageData;
    }
}
