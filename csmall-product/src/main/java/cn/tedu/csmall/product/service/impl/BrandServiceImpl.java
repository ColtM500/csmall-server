package cn.tedu.csmall.product.service.impl;

import cn.tedu.csmall.commons.ex.ServiceException;
import cn.tedu.csmall.commons.pojo.vo.PageData;
import cn.tedu.csmall.commons.util.PageInfoToPageDataConvert;
import cn.tedu.csmall.commons.web.ServiceCode;
import cn.tedu.csmall.product.mapper.BrandCategoryMapper;
import cn.tedu.csmall.product.mapper.BrandMapper;
import cn.tedu.csmall.product.mapper.SpuMapper;
import cn.tedu.csmall.product.pojo.entity.Brand;
import cn.tedu.csmall.product.pojo.param.BrandAddNewParam;
import cn.tedu.csmall.product.pojo.vo.BrandListItemVO;
import cn.tedu.csmall.product.pojo.vo.BrandStandardVO;
import cn.tedu.csmall.product.pojo.vo.BrandUpdateInfoParam;
import cn.tedu.csmall.product.pojo.vo.CategoryListItemVO;
import cn.tedu.csmall.product.service.IBrandService;
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
public class BrandServiceImpl implements IBrandService {

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private BrandCategoryMapper brandCategoryMapper;

    @Autowired
    private SpuMapper spuMapper;


    @Override
    public void addNew(BrandAddNewParam brandAddNewParam) {
        log.debug("开始处理【添加品牌模板】的业务，参数:{}", brandAddNewParam);
        //检查品牌名称是否被占用 如果被占用 则抛出异常
        QueryWrapper<Brand> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", brandAddNewParam.getName());
        int countByName = brandMapper.selectCount(queryWrapper);
        log.debug("根据品牌名称统计匹配的属性模板数量，结果:{}", countByName);
        if (countByName>0){
            String message = "添加品牌失败，品牌名称已被占用";
            throw new ServiceException(ServiceCode.ERROR_CONFLICT,message);
        }

        //将品牌名称写入到数据库中
        Brand brand = new Brand();
        BeanUtils.copyProperties(brandAddNewParam, brand);
        brand.setGmtCreate(LocalDateTime.now());
        brand.setGmtModified(LocalDateTime.now());

        int rows = brandMapper.insert(brand);
        if (rows!=1){
            String message = "添加品牌失败，品牌名称已被占用!";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_INSERT, message);
        }
        log.debug("将品牌名称写入到数据库中，完成!");
    }

    @Override
    public void delete(Long id) {
        //检查品牌信息是否存在
        BrandStandardVO queryResult = brandMapper.getStandardById(id);
        if (queryResult==null){
            String message = "删除品牌失败，尝试删除的数据不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
        }

        //检查此品牌是否关联了种类
        {
            int count = brandCategoryMapper.countByBrand(id);
            if (count>0){
                String message = "删除品牌失败！当前品牌仍关联了品牌！";
                log.warn(message);
                throw new ServiceException(ServiceCode.ERROR_CONFLICT, message);
            }
        }

        //检查此品牌是否关联了SPU
        {
            int count = spuMapper.countByBrandId(id);
            if (count > 0) {
                String message = "删除品牌失败！当前品牌仍关联了商品！";
                log.warn(message);
                throw new ServiceException(ServiceCode.ERROR_CONFLICT, message);
            }
        }

        //执行删除
        {
            log.debug("即将执行删除数据，参数：{}", id);
            int rows = brandMapper.deleteById(id);
            if (rows != 1) {
                String message = "删除品牌失败，服务器忙，请稍后再次尝试！";
                log.warn(message);
                throw new ServiceException(ServiceCode.ERROR_DELETE, message);
            }
        }
    }

    @Override
    public BrandStandardVO getStandardById(Long id) {
        log.debug("开始处理【根据ID查询品牌详情】的业务，参数：{}", id);
        BrandStandardVO queryResult = brandMapper.getStandardById(id);
        if (queryResult==null){
            String message = "根据id查询品牌详情失败，尝试访问的数据不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
        }
        log.debug("即将返回品牌详情：{}", queryResult);
        return queryResult;
    }

    @Override
    public void updateInfoById(Long id, BrandUpdateInfoParam brandUpdateInfoParam) {
        //检查品牌名称是否被占用
        {
            int count = brandMapper.countByNameAndNotId(id, brandUpdateInfoParam.getName());
            if (count>0){
                String message = "修改品牌详情失败，品牌名称已经被占用！";
                log.warn(message);
                throw new ServiceException(ServiceCode.ERROR_CONFLICT, message);
            }
        }

        //检查品牌内容是否不存在
        {
            BrandStandardVO queryResult = brandMapper.getStandardById(id);
            if (queryResult == null) {
                // 是：此id对应的数据不存在，则抛出异常(ERROR_NOT_FOUND)
                String message = "修改品牌详情失败，尝试访问的数据不存在！";
                log.warn(message);
                throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
            }
        }

        //执行修改
        Brand brand = new Brand();
        BeanUtils.copyProperties(brandUpdateInfoParam, brand);
        brand.setId(id);
        int rows = brandMapper.updateById(brand);
        if (rows != 1) {
            String message = "修改品牌详情失败，服务器忙，请稍后再次尝试！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_UPDATE, message);
        }
    }

    @Override
    public void setEnable(Long id) {
        updateEnableInfoById(id, 1);
    }

    @Override
    public void setDisable(Long id) {
        updateEnableInfoById(id, 0);
    }

    @Override
    public PageData<BrandListItemVO> list(Integer pageNum) {
        log.debug("开始处理【查询品牌列表】的业务，页码：{}", pageNum);
        return list(pageNum, 5);
    }

    @Override
    public PageData<BrandListItemVO> list(Integer pageNum, Integer pageSize) {
        log.debug("开始处理【查询品牌列表】的业务，页码：{}，每页记录数：{}", pageNum, pageSize);
        //开始分页
        PageHelper.startPage(pageNum, pageSize);
        //调用mapper进行数据库处理的分页查询
        List<BrandListItemVO> list = brandMapper.list();
        //将所查信息用pageInfo装起来
        PageInfo<BrandListItemVO> pageInfo = new PageInfo<>(list);
        //用Convert的api将pageInfo转程pageData
        PageData<BrandListItemVO> pageData = PageInfoToPageDataConvert.convert(pageInfo);
        log.debug("查询完成，即将返回：{}", pageData);
        return pageData;
    }

    private void updateEnableInfoById(Long id, Integer enable){
        log.debug("开始处理【{}品牌】的业务，ID：{}，目标状态：{}", ENABLE_TEXT[enable], id, enable);
        //检查数据是否存在
        BrandStandardVO queryResult = brandMapper.getStandardById(id);
        if (queryResult == null) {
            String message = ENABLE_TEXT[enable] + "品牌失败，尝试访问的数据不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
        }

        //检查当前启用状态
        if (queryResult.getEnable().equals(enable)){
            String message = ENABLE_TEXT[enable] + "品牌失败，当前品牌已经处于" + ENABLE_TEXT[enable] + "状态！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_CONFLICT, message);
        }

        //修改启用状态
        Brand brand = new Brand();
        brand.setId(id);
        brand.setEnable(enable);
        int rows = brandMapper.updateById(brand);
        if (rows != 1) {
            String message = ENABLE_TEXT[enable] + "品牌失败，服务器忙，请稍后再次尝试！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_UPDATE, message);
        }
    }


}
