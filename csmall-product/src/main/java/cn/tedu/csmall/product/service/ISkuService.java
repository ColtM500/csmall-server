package cn.tedu.csmall.product.service;

import cn.tedu.csmall.commons.pojo.vo.PageData;
import cn.tedu.csmall.product.pojo.param.SkuAddNewParam;
import cn.tedu.csmall.product.pojo.vo.PictureListItemVO;
import cn.tedu.csmall.product.pojo.vo.SkuListItemVO;
import cn.tedu.csmall.product.pojo.vo.SkuStandardVO;
import org.springframework.transaction.annotation.Transactional;

/**
 * 处理SKU的业务接口
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Transactional
public interface ISkuService {

    /**
     * 新增SPU
     *
     * @param skuAddNewParam SKU数据
     */
    void addNew(SkuAddNewParam skuAddNewParam);

    /**
     * 根据ID查询SKU数据详情
     *
     * @param id SKU ID
     * @return 匹配的SKU数据详情，如果没有匹配的数据，则返回null
     */
    SkuStandardVO getStandardById(Long id);

    /**
     * 根据SPU查询SKU列表
     *
     * @param spuId SPU ID
     * @return SPU列表，如果没有匹配的SPU，将返回长度为0的列表
     */
    PageData<SkuListItemVO> listBySpuId(Long spuId, Integer pageNum);

    /**
     * 根据相册ID查询图片数据列表，将使用默认的每页记录数
     *
     * @param spuId  SPU ID
     * @param pageNum  页码
     * @param pageSize 每页记录数
     * @return 类别列表
     */
    PageData<SkuListItemVO> listBySpuId(Long spuId, Integer pageNum, Integer pageSize);

}
