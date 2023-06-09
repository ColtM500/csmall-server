package cn.tedu.csmall.product.controller;

import cn.tedu.csmall.commons.ex.ServiceException;
import cn.tedu.csmall.commons.pojo.vo.PageData;
import cn.tedu.csmall.commons.web.JsonResult;
import cn.tedu.csmall.product.pojo.param.AttributeTemplateAddNewParam;
import cn.tedu.csmall.product.pojo.vo.AttributeTemplateListItemVO;
import cn.tedu.csmall.product.pojo.vo.AttributeTemplateStandardVO;
import cn.tedu.csmall.product.pojo.vo.AttributeTemplateUpdateInfoParam;
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

import javax.validation.Valid;

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
    @PreAuthorize("hasAuthority('/pms/product/add-new')")
    @ApiOperation("添加属性模板")
    @ApiOperationSupport(order = 100)
    public JsonResult<Void> addNew(AttributeTemplateAddNewParam attributeTemplateAddNewParam) {
        log.debug("开始处理【添加属性模板】的请求，参数：{}", attributeTemplateAddNewParam);
        attributeTemplateService.addNew(attributeTemplateAddNewParam);
        return JsonResult.ok();
    }


    // http://localhost:9180/attribute-template/8
    @GetMapping("/{id:[0-9]+}")
    @PreAuthorize("hasAuthority('/pms/product/read')")
    @ApiOperation("根据ID查询属性模板详情")
    @ApiOperationSupport(order = 400)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "属性模板ID", required = true, dataType = "long")
    })
    public JsonResult<AttributeTemplateStandardVO> getStandardById(@PathVariable @Range(min = 1, message = "请提交有效的属性模板ID值！") Long id) {
        log.debug("开始处理【根据ID查询属性模板详情】的请求，参数：{}", id);
        AttributeTemplateStandardVO attributeTemplate = attributeTemplateService.getStandardById(id);
        return JsonResult.ok(attributeTemplate);
    }


    // http://localhost:9180/attribute-template/8/delete
    @PostMapping("/{id:[0-9]+}/delete")
    @PreAuthorize("hasAuthority('/pms/product/delete')")
    @ApiOperation("根据ID删除属性模板")
    @ApiOperationSupport(order = 200)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "属性模板ID", required = true, dataType = "long")
    })
    public JsonResult<Void> delete(@PathVariable @Range(min = 1, message = "请提交有效的属性模板ID值！") Long id) {
        log.debug("开始处理【根据ID删除属性模板】的请求，参数：{}", id);
        attributeTemplateService.deleteById(id);
        return JsonResult.ok();
    }

    // http://localhost:9180/attribute-templates/9527/update
    @PostMapping("/{id:[0-9]+}/update")
    @PreAuthorize("hasAuthority('/pms/product/update')")
    @ApiOperation("修改属性模板详情")
    @ApiOperationSupport(order = 300)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "属性模板ID", required = true, dataType = "long")
    })
    public JsonResult<Void> updateInfoById(@PathVariable @Range(min = 1, message = "请提交有效的属性模板ID值！") Long id,
                                           @Valid AttributeTemplateUpdateInfoParam attributeTemplateUpdateInfoParam) {
        log.debug("开始处理【修改属性模板详情】的请求，参数ID：{}, 新数据：{}", id, attributeTemplateUpdateInfoParam);
        attributeTemplateService.updateInfoById(id, attributeTemplateUpdateInfoParam);
        return JsonResult.ok();
    }

    // http://localhost:9180/attribute-templates/list
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('/pms/product/read')")
    @ApiOperation("查询属性模板列表")
    @ApiOperationSupport(order = 420)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", defaultValue = "1", paramType = "query", dataType = "long"),
            @ApiImplicitParam(name = "queryType", value = "查询类型", example = "all")
    })
    public JsonResult<PageData<AttributeTemplateListItemVO>> list(@Range(min = 1, message = "请提交有效的页码值！") Integer page,
                                                                  String queryType) {
        log.debug("开始处理【查询属性模板列表】的请求，查询类型：{}, 页码：{}", queryType, page);
        Integer pageNum = page == null ? 1 : page;
        PageData<AttributeTemplateListItemVO> pageData;
        if ("all".equals(queryType)) {
            pageData = attributeTemplateService.list(pageNum, Integer.MAX_VALUE);
        } else {
            pageData = attributeTemplateService.list(pageNum);
        }
        return JsonResult.ok(pageData);
    }
}
