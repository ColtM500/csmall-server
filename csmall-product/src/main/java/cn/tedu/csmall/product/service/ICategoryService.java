package cn.tedu.csmall.product.service;

import cn.tedu.csmall.commons.pojo.vo.PageData;
import cn.tedu.csmall.product.pojo.param.BrandAddNewParam;
import cn.tedu.csmall.product.pojo.param.CategoryAddNewParam;
import cn.tedu.csmall.product.pojo.vo.CategoryListItemVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ICategoryService {
    void addNew(CategoryAddNewParam categoryAddNewParam);

    void delete(Long id);

//    List<CategoryListItemVO> list();

    PageData<CategoryListItemVO> listByParentId(Long parentId, Integer pageNum);

    PageData<CategoryListItemVO> listByParentId(Long parentId, Integer pageNum, Integer pageSize);
}


