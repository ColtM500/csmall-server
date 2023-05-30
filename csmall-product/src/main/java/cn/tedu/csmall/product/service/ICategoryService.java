package cn.tedu.csmall.product.service;

import cn.tedu.csmall.commons.pojo.vo.PageData;
import cn.tedu.csmall.product.pojo.param.BrandAddNewParam;
import cn.tedu.csmall.product.pojo.param.CategoryAddNewParam;
import cn.tedu.csmall.product.pojo.vo.CategoryListItemVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ICategoryService {

    String Enable_Text[]={"禁用", "启用"};

    String Display_Text[]={"显示", "隐藏"};

    void addNew(CategoryAddNewParam categoryAddNewParam);

    void delete(Long id);

    void setEnable(Long id);//固定将enable设为1

    void setDisable(Long id);//固定将disable设为0

//    void setDisplay(Long id);//固定将display设为1

//    void setHidden(Long id);//固定将Hidden设为0

//    List<CategoryListItemVO> list();

    PageData<CategoryListItemVO> listByParentId(Long parentId, Integer pageNum);

    PageData<CategoryListItemVO> listByParentId(Long parentId, Integer pageNum, Integer pageSize);
}


