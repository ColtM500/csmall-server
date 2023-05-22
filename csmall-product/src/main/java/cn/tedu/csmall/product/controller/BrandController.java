package cn.tedu.csmall.product.controller;

import cn.tedu.csmall.product.ex.ServiceException;
import cn.tedu.csmall.product.pojo.param.AttributeTemplateAddNewParam;
import cn.tedu.csmall.product.pojo.param.BrandAddNewParam;
import cn.tedu.csmall.product.service.IBrandService;
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
@RequestMapping("/brand")
@Validated
@Api(tags = "02. 品牌管理模块")
public class BrandController {

    @Autowired
    private IBrandService brandService;

    // http://localhost:8080/brand/add-new?name=TestName001
    @PostMapping("/add-new")
    @ApiOperation("添加品牌模板")
    @ApiOperationSupport(order = 100)
    public String addNew(BrandAddNewParam brandAddNewParam){
        try {
            brandService.addNew(brandAddNewParam);
            return "添加成功!";
        } catch (ServiceException e) {
            return e.getMessage();
        } catch (Throwable throwable){
            return "添加失败！出现了某种异常！";
        }
    }

}
