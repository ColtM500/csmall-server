package cn.tedu.csmall.product.service.impl;

import cn.tedu.csmall.commons.ex.ServiceException;
import cn.tedu.csmall.commons.pojo.vo.PageData;
import cn.tedu.csmall.commons.util.PageInfoToPageDataConvert;
import cn.tedu.csmall.commons.web.ServiceCode;
import cn.tedu.csmall.product.mapper.*;
import cn.tedu.csmall.product.pojo.entity.Spu;
import cn.tedu.csmall.product.pojo.entity.SpuDetails;
import cn.tedu.csmall.product.pojo.param.SpuAddNewParam;
import cn.tedu.csmall.product.pojo.vo.*;
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
public class SpuServiceImpl implements ISpuService {

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private AlbumMapper albumMapper;

    @Autowired
    private SpuDetailsMapper spuDetailsMapper;

    @Autowired
    private IdUtils idUtils;

    @Override
    public void addNew(SpuAddNewParam spuAddNewParam) {
        log.debug("开始处理【新增SPU】业务，参数:{}", spuAddNewParam);
        //检查品牌是否存在 存在则启用
        Long brandId = spuAddNewParam.getBrandId();
        BrandStandardVO brandResult = brandMapper.getStandardById(brandId);
        if (brandResult==null){
            String message = "新增SPU失败，品牌数据不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
        }
        if (brandResult.getEnable()==0){
            String message = "新增SPU失败，品牌已经被禁用！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_CONFLICT, message);
        }

        //检查类别是否存在 存在则启用
        Long categoryId = spuAddNewParam.getCategoryId();
        CategoryStandardVO categoryResult = categoryMapper.getStandardById(categoryId);
        if (categoryResult==null){
            String message = "新增SPU失败，类别数据不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
        }
        if (categoryResult.getEnable()==0){
            String message = "新增SPU失败，类别已经被禁用！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_CONFLICT, message);
        }
        if (categoryResult.getIsParent()==1){//有子级不能再包含了
            String message = "新增SPU失败，选择的类别不允许包含子级类别！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_CONFLICT, message);
        }

        //检查相册是否存在 存在则启用
        Long albumId = spuAddNewParam.getAlbumId();
        AlbumStandardVO albumResult = albumMapper.getStandardById(albumId);
        if (albumResult == null) {
            String message = "新增SPU失败，相册数据不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
        }

        //执行增加SPU
//        Long spuId = Math.abs(new Random().nextLong());
        Long spuId = idUtils.generateSpuId();
        Spu spu = new Spu();
        BeanUtils.copyProperties(spuAddNewParam, spu);
        // 补全属性值：id >>> 暂时随便写
        spu.setId(spuId);
        // 补全属性值：brand_name, category_name >>> 此前检查时的查询结果
        spu.setBrandName(brandResult.getName());
        spu.setCategoryName(categoryResult.getName());
        // 补全属性值：sales, comment_count, positive_comment_count >>> 0
        spu.setSales(0);
        spu.setCommentCount(0);
        spu.setPositiveCommentCount(0);
        // 补全属性值：is_deleted, is_published, is_new_arrival, is_recommend >>> 0
        spu.setIsDeleted(0);
        spu.setIsPublished(0);
        spu.setIsNewArrival(0);
        spu.setIsRecommend(0);
        // 补全属性值：is_checked >>> 0, check_user >>> null, gmt_check >>> null
        spu.setIsChecked(0);

        //插入SPU
        int rows = spuMapper.insert(spu);
        if (rows != 1) {
            String message = "新增SPU失败，服务器忙，请稍后再尝试！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_INSERT, message);
        }

        // 插入SpuDetails
        SpuDetails spuDetail = new SpuDetails();
        spuDetail.setSpuId(spuId);
        spuDetail.setDetail(spuAddNewParam.getDetail());
        rows = spuDetailsMapper.insert(spuDetail);
        if (rows != 1) {
            String message = "新增SPU失败，服务器忙，请稍后再尝试！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_INSERT, message);
        }

    }

    @Override
    public SpuStandardVO getStandardById(Long id) {
        log.debug("开始处理【根据ID查询SPU详情】的业务，参数：{}", id);
        SpuStandardVO queryResult = spuMapper.getStandardById(id);
        if (queryResult == null) {
            String message = "根据ID查询SPU详情失败，尝试访问的数据不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
        }
        log.debug("即将返回SPU详情：{}", queryResult);
        return queryResult;
    }

    @Override
    public SpuFullInfoVO getFullInfoById(Long id) {
        log.debug("开始处理【根据ID查询SPU完整信息】的业务，参数：{}", id);
        SpuFullInfoVO queryResult = spuMapper.getFullInfoById(id);
        if (queryResult == null) {
            String message = "根据ID查询SPU详情失败，尝试访问的数据不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
        }
        log.debug("即将返回SPU详情：{}", queryResult);
        return queryResult;
    }

    @Override
    public PageData<SpuListItemVO> list(Integer pageNum) {
        log.debug("开始处理【查询品牌列表】的业务，页码：{}", pageNum);
        return list(pageNum, 5);
    }

    @Override
    public PageData<SpuListItemVO> list(Integer pageNum, Integer pageSize) {
        log.debug("开始处理【查询SPU列表】的业务，页码：{}，每页记录数：{}", pageNum, pageSize);
        PageHelper.startPage(pageNum, pageSize);
        List<SpuListItemVO> list = spuMapper.list();
        PageInfo<SpuListItemVO> pageInfo = new PageInfo<>(list);
        PageData<SpuListItemVO> pageData = PageInfoToPageDataConvert.convert(pageInfo);
        log.debug("查询完成，即将返回：{}", pageData);
        return pageData;
    }
}
