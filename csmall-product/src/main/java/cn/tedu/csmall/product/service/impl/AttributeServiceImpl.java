package cn.tedu.csmall.product.service.impl;

import cn.tedu.csmall.commons.ex.ServiceException;
import cn.tedu.csmall.commons.pojo.vo.PageData;
import cn.tedu.csmall.commons.util.PageInfoToPageDataConvert;
import cn.tedu.csmall.commons.web.ServiceCode;
import cn.tedu.csmall.product.mapper.AttributeMapper;
import cn.tedu.csmall.product.mapper.AttributeTemplateMapper;
import cn.tedu.csmall.product.pojo.entity.Attribute;
import cn.tedu.csmall.product.pojo.param.AttributeAddNewParam;
import cn.tedu.csmall.product.pojo.vo.AttributeListItemVO;
import cn.tedu.csmall.product.pojo.vo.AttributeStandardVO;
import cn.tedu.csmall.product.pojo.vo.AttributeUpdateInfoParam;
import cn.tedu.csmall.product.pojo.vo.CategoryListItemVO;
import cn.tedu.csmall.product.service.IAttributeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AttributeServiceImpl implements IAttributeService {

    @Autowired
    private AttributeMapper attributeMapper;
    @Autowired
    private AttributeTemplateMapper attributeTemplateMapper;
    @Override
    public void addNew(AttributeAddNewParam attributeAddNewParam) {
        log.debug("开始处理【添加属性】的业务，参数：{}", attributeAddNewParam);
        // 调用Mapper对象的int countByName(String name)方法统计此名称的属性的数量
        String name = attributeAddNewParam.getName();
        Long templateId = attributeAddNewParam.getTemplateId();
        int countByName = attributeMapper.countByNameAndTemplate(name, templateId);
        log.debug("尝试在属性模板【{}】中添加属性【{}】，在数据库中此名称的属性数量为：{}", templateId, name, countByName);
        // 判断统计结果是否大于0
        if (countByName > 0) {
            // 是：属性名称已经存在，抛出RuntimeException异常
            String message = "添加属性失败！此属性模板中已经存在名称为【" + name + "】的属性！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_CONFLICT, message);
        }

        // 检查属性模板是否存在
        {
            Object queryResult = attributeTemplateMapper.getStandardById(templateId);
            if (queryResult == null) {
                String message = "添加属性失败！属性模板不存在！";
                log.warn(message);
                throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
            }
        }

        // 创建对象
        Attribute attribute = new Attribute();
        // 复制属性
        BeanUtils.copyProperties(attributeAddNewParam, attribute);
        // 插入数据
        log.debug("即将向数据库中插入数据：{}", attribute);
        int rows = attributeMapper.insert(attribute);
        if (rows != 1) {
            String message = "添加属性失败！服务器忙，请稍后再次尝试！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_INSERT, message);
        }
    }

    @Override
    public void delete(Long id) {
        log.debug("开始处理【根据id删除属性】的业务，参数：{}", id);
        // 检查尝试删除的数据是否存在
        Object queryResult = attributeMapper.getStandardById(id);
        if (queryResult == null) {
            String message = "删除属性失败，尝试删除的数据不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
        }

        // 执行删除
        log.debug("即将执行删除数据，参数：{}", id);
        int rows = attributeMapper.deleteById(id);
        if (rows != 1) {
            String message = "删除属性失败，服务器忙，请稍后再次尝试！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_DELETE, message);
        }
    }

    @Override
    public void updateInfoById(Long id, AttributeUpdateInfoParam attributeUpdateInfoParam) {
        log.debug("开始处理【修改属性详情】的业务，参数ID：{}, 新数据：{}", id, attributeUpdateInfoParam);
        // 调用adminMapper根据参数id执行查询
        AttributeStandardVO queryResult = attributeMapper.getStandardById(id);
        // 判断查询结果是否为null
        if (queryResult == null) {
            // 抛出ServiceException，业务状态码：40400
            String message = "修改属性详情失败！尝试访问的数据不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
        }

        // 检查名称是否被占用
        {
            Long templateId = queryResult.getTemplateId();
            int count = attributeMapper.countByNameAndTemplateAndNotId(id, attributeUpdateInfoParam.getName(), templateId);
            if (count > 0) {
                String message = "修改属性详情失败，属性名称已经被占用！";
                log.warn(message);
                throw new ServiceException(ServiceCode.ERROR_CONFLICT, message);
            }
        }

        // Attribute，将作为修改时的参数
        Attribute attribute = new Attribute();
        BeanUtils.copyProperties(attributeUpdateInfoParam, attribute);
        attribute.setId(id);
        // 调用Mapper对象的update()修改属性基本资料，并获取返回值
        log.debug("即将修改数据：{}", attribute);
        int rows = attributeMapper.updateById(attribute);
        // 判断返回值是否不等于1
        if (rows != 1) {
            // 是：抛出ServiceException（ERROR_INSERT）
            String message = "修改属性详情失败，服务器忙，请稍后再尝试！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_UPDATE, message);
        }
    }

    @Override
    public AttributeStandardVO getStandardById(Long id) {
        log.debug("开始处理【根据id查询属性详情】的业务，参数：{}", id);
        AttributeStandardVO attribute = attributeMapper.getStandardById(id);
        if (attribute == null) {
            // 是：此id对应的数据不存在，则抛出异常(ERROR_NOT_FOUND)
            String message = "查询属性详情失败，尝试访问的数据不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
        }
        return attribute;
    }

    @Override
    public PageData<AttributeListItemVO> listByTemplateId(Long templateId, Integer pageNum) {
        log.debug("开始处理【根据父级类别查询子级类别列表】的业务，父级类别：{}, 页码：{}", templateId, pageNum);
        return listByTemplateId(templateId, pageNum, 5);
    }

    @Override
    public PageData<AttributeListItemVO> listByTemplateId(Long templateId, Integer pageNum, Integer pageSize) {
        log.debug("开始处理【根据templateId查询子级类别列表】的业务，templateId：{}, 页码：{}，每页记录数：{}", templateId, pageNum, pageSize);
        //开始分页
        PageHelper.startPage(pageNum, pageSize);
        //调用mapper进行数据库处理的分页查询
        List<AttributeListItemVO> list = attributeMapper.listByTemplateId(templateId);
        //将所查信息用pageInfo装起来
        PageInfo<AttributeListItemVO> pageInfo = new PageInfo<>(list);
        //用Convert的api将pageInfo转程pageData
        PageData<AttributeListItemVO> pageData = PageInfoToPageDataConvert.convert(pageInfo);
        log.debug("查询完成，即将返回：{}", pageData);
        return pageData;
    }


}
