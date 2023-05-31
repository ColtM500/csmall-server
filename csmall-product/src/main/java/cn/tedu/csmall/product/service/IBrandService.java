package cn.tedu.csmall.product.service;

import cn.tedu.csmall.commons.pojo.vo.PageData;
import cn.tedu.csmall.product.pojo.param.BrandAddNewParam;
import cn.tedu.csmall.product.pojo.vo.BrandListItemVO;
import cn.tedu.csmall.product.pojo.vo.BrandStandardVO;
import cn.tedu.csmall.product.pojo.vo.BrandUpdateInfoParam;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface IBrandService {

    String ENABLE_TEXT[] = {"禁用", "启用"};

    void addNew(BrandAddNewParam brandAddNewParam);

    void delete(Long id);

    BrandStandardVO getStandardById(Long id);

    void updateInfoById(Long id, BrandUpdateInfoParam brandUpdateInfoParam);

    void setEnable(Long id);

    void setDisable(Long id);

    PageData<BrandListItemVO> list(Integer pageNum);

    PageData<BrandListItemVO> list(Integer pageNum, Integer pageSize);
}
