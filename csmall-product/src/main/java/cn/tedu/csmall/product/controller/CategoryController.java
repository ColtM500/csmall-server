package cn.tedu.csmall.product.controller;

import cn.tedu.csmall.commons.ex.ServiceException;
import cn.tedu.csmall.product.pojo.param.CategoryAddNewParam;
import cn.tedu.csmall.product.service.ICategoryService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Validated
@RequestMapping("/category")
@Api(tags = "01. 类别管理模块")
public class CategoryController {
    @Autowired
    private ICategoryService categoryService;

    // http://localhost:8080/category/add-new?name=TestName001
    @PostMapping("/add-new")
    @ApiOperation("添加类别")
    @ApiOperationSupport(order = 100)
    public String addNew(CategoryAddNewParam categoryAddNewParam){
        try {
            categoryService.addNew(categoryAddNewParam);
            return "添加成功!";
        } catch (ServiceException e) {
            return e.getMessage();
        } catch (Throwable throwable){
            return "添加失败！出现了某种异常！";
        }
    }
}
