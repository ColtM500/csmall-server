package cn.tedu.csmall.product.service.impl;

import cn.tedu.csmall.commons.ex.ServiceException;
import cn.tedu.csmall.commons.pojo.vo.PageData;
import cn.tedu.csmall.commons.util.PageInfoToPageDataConvert;
import cn.tedu.csmall.commons.web.ServiceCode;
import cn.tedu.csmall.product.mapper.SkuMapper;
import cn.tedu.csmall.product.mapper.SpuMapper;
import cn.tedu.csmall.product.pojo.entity.Sku;
import cn.tedu.csmall.product.pojo.param.SkuAddNewParam;
import cn.tedu.csmall.product.pojo.vo.PictureListItemVO;
import cn.tedu.csmall.product.pojo.vo.SkuListItemVO;
import cn.tedu.csmall.product.pojo.vo.SkuStandardVO;
import cn.tedu.csmall.product.pojo.vo.SpuStandardVO;
import cn.tedu.csmall.product.service.ISkuService;
import cn.tedu.csmall.product.service.ISpuService;
import cn.tedu.csmall.product.util.IdUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class SkuServiceImpl implements ISkuService {

    @Autowired
    private IdUtils idUtils;

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private SkuMapper skuMapper;

    @Override
    public void addNew(SkuAddNewParam skuAddNewParam) {
        log.debug("开始处理【新增sku】的业务，参数:{}", skuAddNewParam);
        log.debug("开始处理【新增SKU】的业务，参数：{}", skuAddNewParam);
        // 检查品牌：是否存在，是否启用
        Long brandId = skuAddNewParam.getSpuId();
        SpuStandardVO spu = spuMapper.getStandardById(brandId);
        if (spu == null) {
            String message = "新增SKU失败，SPU数据不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
        }

        // 获取SKU ID
        Long skuId = idUtils.generateSpuId();

        // 创建SKu对象
        Sku sku = new Sku();
        // 复制属性值
        BeanUtils.copyProperties(skuAddNewParam, sku);
        // 补全属性值：id >>> 暂时随便写
        sku.setId(skuId);
        // 补全属性值
        sku.setAttributeTemplateId(spu.getAttributeTemplateId());
        sku.setAlbumId(spu.getAlbumId());
        sku.setPictures(spu.getPictures());
        sku.setSales(0);
        sku.setCommentCount(0);
        sku.setPositiveCommentCount(0);
        // 插入SKu数据
        int rows = skuMapper.insert(sku);
        if (rows != 1) {
            String message = "新增SKU失败，服务器忙，请稍后再尝试！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_INSERT, message);
        }
    }

    @Override
    public SkuStandardVO getStandardById(Long id) {
        log.debug("开始处理【根据ID查询SPU详情】的业务，参数：{}", id);
        SkuStandardVO queryResult = skuMapper.getStandardById(id);
        if (queryResult == null) {
            String message = "根据ID查询SPU详情失败，尝试访问的数据不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
        }
        log.debug("即将返回SPU详情：{}", queryResult);
        return queryResult;
    }

    @Override
    public PageData<SkuListItemVO> listBySpuId(Long spuId, Integer pageNum) {
        return listBySpuId(spuId, pageNum, 5);
    }

    @Override
    public PageData<SkuListItemVO> listBySpuId(Long spuId, Integer pageNum, Integer pageSize) {
        log.debug("开始处理【查询Sku列表】的业务，页码：{}，每页记录数：{}", pageNum, pageSize);
        PageHelper.startPage(pageNum, pageSize);
        List<SkuListItemVO> list = skuMapper.listBySpuId(spuId);
        PageInfo<SkuListItemVO> pageInfo = new PageInfo<>(list);
        PageData<SkuListItemVO> pageData = PageInfoToPageDataConvert.convert(pageInfo);
        log.debug("查询完成，即将返回：{}", pageData);
        return pageData;
    }
}
