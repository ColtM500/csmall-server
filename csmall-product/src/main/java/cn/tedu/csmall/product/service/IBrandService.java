package cn.tedu.csmall.product.service;

import cn.tedu.csmall.product.pojo.param.BrandAddNewParam;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface IBrandService {
    void addNew(BrandAddNewParam brandAddNewParam);
}
