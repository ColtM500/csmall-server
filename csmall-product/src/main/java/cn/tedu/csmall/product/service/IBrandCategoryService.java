package cn.tedu.csmall.product.service;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface IBrandCategoryService {

    int countByBrandId(Long BrandId);
}
