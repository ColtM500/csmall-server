package cn.tedu.csmall.product.controller;

import cn.tedu.csmall.commons.pojo.vo.PageData;
import cn.tedu.csmall.commons.web.JsonResult;
import cn.tedu.csmall.product.pojo.param.AttributeAddNewParam;
import cn.tedu.csmall.product.pojo.vo.AttributeListItemVO;
import cn.tedu.csmall.product.pojo.vo.AttributeStandardVO;
import cn.tedu.csmall.product.pojo.vo.AttributeUpdateInfoParam;
import cn.tedu.csmall.product.service.IAttributeService;
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

/**
 * 处理属性相关请求的控制器
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Slf4j
@RestController
@RequestMapping("/attributes")
@Validated
@Api(tags = "03. 属性管理模块")
public class AttributeController {

    @Autowired
    private IAttributeService attributeService;

    public AttributeController() {
        log.info("创建控制器：AttributeController");
    }

    // http://localhost:9180/attributes/add-new
    @PostMapping("/add-new")
    @PreAuthorize("hasAuthority('/pms/product/add-new')")
    @ApiOperation("添加属性")
    @ApiOperationSupport(order = 100)
    public JsonResult<Void> addNew(@Valid AttributeAddNewParam attributeAddNewParam) {
        log.debug("开始处理【添加属性】的请求，参数：{}", attributeAddNewParam);
        attributeService.addNew(attributeAddNewParam);
        return JsonResult.ok();
    }

    // http://localhost:9180/attributes/9527/delete
    @PostMapping("/{id:[0-9]+}/delete")
    @PreAuthorize("hasAuthority('/pms/product/delete')")
    @ApiOperation("根据ID删除属性")
    @ApiOperationSupport(order = 200)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "属性ID", required = true, dataType = "long")
    })
    public JsonResult<Void> delete(@PathVariable @Range(min = 1, message = "请提交有效的属性ID值！") Long id) {
        log.debug("开始处理【根据id删除属性】的请求，参数：{}", id);
        attributeService.delete(id);
        return JsonResult.ok();
    }

    // http://localhost:9180/attributes/9527/update
    @PostMapping("/{id:[0-9]+}/update")
    @PreAuthorize("hasAuthority('/pms/product/update')")
    @ApiOperation("修改属性详情")
    @ApiOperationSupport(order = 300)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "属性ID", required = true, dataType = "long")
    })
    public JsonResult<Void> updateInfoById(
            @PathVariable @Range(min = 1, message = "请提交有效的属性ID值！") Long id,
            @Valid AttributeUpdateInfoParam attributeUpdateInfoParam) {
        log.debug("开始处理【修改属性详情】的请求，参数ID：{}, 新数据：{}", id, attributeUpdateInfoParam);
        attributeService.updateInfoById(id, attributeUpdateInfoParam);
        return JsonResult.ok();
    }

    // http://localhost:9180/attributes/9527
    @GetMapping("/{id:[0-9]+}")
    @PreAuthorize("hasAuthority('/pms/product/read')")
    @ApiOperation("根据ID查询属性详情")
    @ApiOperationSupport(order = 400)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "属性ID", required = true, dataType = "long")
    })
    public JsonResult<AttributeStandardVO> getStandardById(
            @PathVariable @Range(min = 1, message = "请提交有效的属性ID值！") Long id) {
        log.debug("开始处理【根据ID查询属性详情】的请求，参数：{}", id);
        AttributeStandardVO attribute = attributeService.getStandardById(id);
        return JsonResult.ok(attribute);
    }

    // http://localhost:9180/attributes/list-by-template
    @GetMapping("/list-by-template")
    @PreAuthorize("hasAuthority('/pms/product/read')")
    @ApiOperation("根据属性模板查询属性列表")
    @ApiOperationSupport(order = 410)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "templateId", value = "属性模板ID", required = true, paramType = "query", dataType = "long")
    })
    public JsonResult<PageData<AttributeListItemVO>> listByTemplateId(
            @Range(min = 1, message = "请提交有效的属性模板ID值！") Long templateId,
            @Range(min = 1, message = "请提交有效的页码值！") Integer page) {
        log.debug("开始处理【根据属性模板查询属性列表】的请求，属性模板：{}", templateId);
        Integer pageNum = page == null ? 1 : page;
        PageData<AttributeListItemVO> pageData = attributeService.listByTemplateId(templateId, pageNum);
        return JsonResult.ok(pageData);
    }
}
