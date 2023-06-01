package cn.tedu.csmall.product.service;

import cn.tedu.csmall.commons.pojo.vo.PageData;
import cn.tedu.csmall.product.pojo.param.SpuAddNewParam;
import cn.tedu.csmall.product.pojo.vo.SpuFullInfoVO;
import cn.tedu.csmall.product.pojo.vo.SpuListItemVO;
import cn.tedu.csmall.product.pojo.vo.SpuStandardVO;
import org.springframework.transaction.annotation.Transactional;

/**
 * 处理SPU的业务接口
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Transactional
public interface ISpuService {

    /**
     * 新增SPU
     *
     * @param spuAddNewParam SPU数据
     */
    void addNew(SpuAddNewParam spuAddNewParam);

    /**
     * 根据ID获取SPU详情
     *
     * @param id 品牌ID
     * @return 匹配的SPU数据详情，如果没有匹配的数据，则返回null
     */
    SpuStandardVO getStandardById(Long id);

    /**
     * 根据ID查询SPU完整信息
     *
     * @param id SPU ID
     * @return 匹配的SPU的完整信息，如果没有匹配的数据，则返回null
     */
    SpuFullInfoVO getFullInfoById(Long id);

    /**
     * 查询SPU列表，将使用默认的每页记录数
     *
     * @param pageNum 页码
     * @return SPU列表，如果没有匹配的SPU，将返回长度为0的列表
     */
    PageData<SpuListItemVO> list(Integer pageNum);

    /**
     * 查询SPU列表
     *
     * @param pageNum  页码
     * @param pageSize 每页记录数
     * @return SPU列表，如果没有匹配的SPU，将返回长度为0的列表
     */
    PageData<SpuListItemVO> list(Integer pageNum, Integer pageSize);

}
