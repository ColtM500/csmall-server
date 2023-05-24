package cn.tedu.csmall.product.service;

import cn.tedu.csmall.product.pojo.param.BrandAddNewParam;
import cn.tedu.csmall.product.pojo.param.CategoryAddNewParam;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ICategoryService {
    void addNew(CategoryAddNewParam categoryAddNewParam);
}
