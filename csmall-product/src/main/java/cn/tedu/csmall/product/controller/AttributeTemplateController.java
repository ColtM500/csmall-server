package cn.tedu.csmall.product.controller;

import cn.tedu.csmall.commons.ex.ServiceException;
import cn.tedu.csmall.product.pojo.param.AttributeTemplateAddNewParam;
import cn.tedu.csmall.product.service.IAttributeTemplateService;
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
@RequestMapping("/attribute-template")
@Validated
@Api(tags = "06.属性模板管理模块")
public class AttributeTemplateController {

    @Autowired
    private IAttributeTemplateService attributeTemplateService;

    // http://localhost:8080/attribute-template/add-new?name=TestName001
    @PostMapping("/add-new")
    @ApiOperation("添加属性模板")
    @ApiOperationSupport(order = 100)
    public String addNew(AttributeTemplateAddNewParam attributeTemplateAddNewParam){
        try {
            attributeTemplateService.addNew(attributeTemplateAddNewParam);
            return "添加成功!";
        } catch (ServiceException e) {
            return e.getMessage();
        } catch (Throwable throwable){
            return "添加失败！出现了某种异常！";
        }
    }


}
