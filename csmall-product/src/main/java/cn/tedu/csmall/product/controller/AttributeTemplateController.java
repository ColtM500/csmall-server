package cn.tedu.csmall.product.controller;

import cn.tedu.csmall.commons.ex.ServiceException;
import cn.tedu.csmall.commons.web.JsonResult;
import cn.tedu.csmall.product.pojo.param.AttributeTemplateAddNewParam;
import cn.tedu.csmall.product.pojo.vo.AttributeTemplateStandardVO;
import cn.tedu.csmall.product.service.IAttributeTemplateService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/attribute-template")
@Validated
@Api(tags = "06.属性模板管理模块")
public class AttributeTemplateController {

    @Autowired
    private IAttributeTemplateService attributeTemplateService;

    // http://localhost:9180/attribute-template/add-new?name=TestName001
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


    // http://localhost:9180/attribute-template/8
    @GetMapping("/{id:[0-9]+}")
    @PreAuthorize("hasAuthrity('/pms/product/read')")
    @ApiOperation("根据ID查询属性模板详情")
    @ApiOperationSupport(order = 400)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "属性模板ID", required = true, dataType = "Long")
    })
    public JsonResult<AttributeTemplateStandardVO> getStandardById(
            @PathVariable
            @Range(min = 1, message = "请提交有效的属性模板ID值!")
            Long id){
        log.debug("开始处理【根据id查询属性模板详情】的请求，参数:{}", id);
        AttributeTemplateStandardVO attributeTemplate = attributeTemplateService.getStandardById(id);
        return JsonResult.ok(attributeTemplate);
    }


    // http://localhost:9180/attribute-template/8/delete
    @GetMapping("/{id:[0-9]+}/delete")
    @PreAuthorize("hasAuthority('pms/product/delete')")
    @ApiOperation("根据ID删除属性模板")
    @ApiOperationSupport(order = 200)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "属性模板ID", required = true, dataType = "Long")
    })
    public JsonResult delete(
            @PathVariable
            @Range(min = 1, message = "请提交有效的属性模板ID值!")
            Long id
    ){
        log.debug("开始处理【根据id查询属性模板详情】的请求，参数:{}", id);
        attributeTemplateService.deleteById(id);
        return JsonResult.ok();
    }

}
