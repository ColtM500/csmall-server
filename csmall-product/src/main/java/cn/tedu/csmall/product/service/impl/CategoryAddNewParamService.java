package cn.tedu.csmall.product.service.impl;

import cn.tedu.csmall.product.ex.ServiceException;
import cn.tedu.csmall.product.mapper.CategoryMapper;
import cn.tedu.csmall.product.pojo.entity.Category;
import cn.tedu.csmall.product.pojo.param.CategoryAddNewParam;
import cn.tedu.csmall.product.service.ICategoryService;
import cn.tedu.csmall.product.web.ServiceCode;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class CategoryAddNewParamService implements ICategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public void addNew(CategoryAddNewParam categoryAddNewParam) {
        log.debug("开始处理【添加类别模板】的业务，参数:{}", categoryAddNewParam);
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", categoryAddNewParam.getName());
        int countByName = categoryMapper.selectCount(queryWrapper);
        log.debug("根据类别名称统计匹配的类别数量，结果:{}", countByName);
        if (countByName>0){
            String message = "添加类别失败，类别名称已被占用";
            throw new ServiceException(ServiceCode.ERR_CONFLICT,message);
        }

        //将类别名称添加到数据库中
        Category category = new Category();
        BeanUtils.copyProperties(categoryAddNewParam, category);
        category.setGmtCreate(LocalDateTime.now());
        category.setGmtModified(LocalDateTime.now());

        categoryMapper.insert(category);
        log.debug("将类别名称写入到数据库中，完成!");
    }
}
