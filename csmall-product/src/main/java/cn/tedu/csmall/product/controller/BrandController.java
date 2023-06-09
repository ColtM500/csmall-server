package cn.tedu.csmall.product.controller;

import cn.tedu.csmall.commons.ex.ServiceException;
import cn.tedu.csmall.commons.pojo.vo.PageData;
import cn.tedu.csmall.commons.web.JsonResult;
import cn.tedu.csmall.product.pojo.param.BrandAddNewParam;
import cn.tedu.csmall.product.pojo.vo.BrandListItemVO;
import cn.tedu.csmall.product.pojo.vo.BrandStandardVO;
import cn.tedu.csmall.product.pojo.vo.BrandUpdateInfoParam;
import cn.tedu.csmall.product.service.IBrandService;
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
@RequestMapping("/brand")
@Validated
@Api(tags = "02. 品牌管理模块")
public class BrandController {

    @Autowired
    private IBrandService brandService;

    public BrandController() {
        log.info("创建控制器对象：BrandController");
    }

    // http://localhost:9180/brands/add-new
    @PostMapping("/add-new")
    @PreAuthorize("hasAuthority('/pms/brand/add-new')")
    @ApiOperation("添加品牌")
    @ApiOperationSupport(order = 100)
    public JsonResult<Void> addNew(@Valid BrandAddNewParam brandAddNewParam) {
        log.debug("开始处理【添加品牌】的请求，参数：{}", brandAddNewParam);
        brandService.addNew(brandAddNewParam);
        return JsonResult.ok();
    }

    // http://localhost:9180/brands/9527/delete
    @PostMapping("/{id:[0-9]+}/delete")
    @PreAuthorize("hasAuthority('/pms/brand/delete')")
    @ApiOperation("根据ID删除品牌")
    @ApiOperationSupport(order = 200)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "品牌ID", required = true, dataType = "long")
    })
    public JsonResult<Void> delete(@PathVariable @Range(min = 1, message = "请提交有效的品牌ID值！") Long id) {
        log.debug("开始处理【根据ID删除品牌】的请求，参数：{}", id);
        brandService.delete(id);
        return JsonResult.ok();
    }

    // http://localhost:9180/brands/9527/update
    @PostMapping("/{id:[0-9]+}/update")
    @PreAuthorize("hasAuthority('/pms/brand/update')")
    @ApiOperation("修改品牌详情")
    @ApiOperationSupport(order = 300)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "品牌ID", required = true, dataType = "long")
    })
    public JsonResult<Void> updateById(@PathVariable @Range(min = 1, message = "请提交有效的品牌ID值！") Long id,
                                       @Valid BrandUpdateInfoParam brandUpdateInfoParam) {
        log.debug("开始处理【修改品牌详情】的请求，参数ID：{}, 新数据：{}", id, brandUpdateInfoParam);
        brandService.updateInfoById(id, brandUpdateInfoParam);
        return JsonResult.ok();
    }

    // http://localhost:9180/brands/9527/enable
    @PostMapping("/{id:[0-9]+}/enable")
    @PreAuthorize("hasAuthority('/pms/brand/update')")
    @ApiOperation("启用品牌")
    @ApiOperationSupport(order = 310)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "品牌ID", required = true, dataType = "long")
    })
    public JsonResult<Void> setEnable(@PathVariable @Range(min = 1, message = "请提交有效的品牌ID值！") Long id) {
        log.debug("开始处理【启用品牌】的请求，参数：{}", id);
        brandService.setEnable(id);
        return JsonResult.ok();
    }

    // http://localhost:9180/brands/9527/disable
    @PostMapping("/{id:[0-9]+}/disable")
    @PreAuthorize("hasAuthority('/pms/brand/update')")
    @ApiOperation("禁用品牌")
    @ApiOperationSupport(order = 311)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "品牌ID", required = true, dataType = "long")
    })
    public JsonResult<Void> setDisable(@PathVariable @Range(min = 1, message = "请提交有效的品牌ID值！") Long id) {
        log.debug("开始处理【禁用品牌】的请求，参数：{}", id);
        brandService.setDisable(id);
        return JsonResult.ok();
    }

    // http://localhost:9180/brands/9527
    @GetMapping("/{id:[0-9]+}")
    @PreAuthorize("hasAuthority('/pms/brand/read')")
    @ApiOperation("根据ID查询品牌详情")
    @ApiOperationSupport(order = 400)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "品牌ID", required = true, dataType = "long")
    })
    public JsonResult<BrandStandardVO> getStandardById(@PathVariable @Range(min = 1, message = "请提交有效的品牌ID值！") Long id) {
        log.debug("开始处理【根据ID查询品牌详情】的请求，参数：{}", id);
        BrandStandardVO brand = brandService.getStandardById(id);
        return JsonResult.ok(brand);
    }

    // http://localhost:9180/brands/list
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('/pms/brand/read')")
    @ApiOperation("查询品牌列表")
    @ApiOperationSupport(order = 420)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", defaultValue = "1", paramType = "query", dataType = "long"),
            @ApiImplicitParam(name = "queryType", value = "查询类型", example = "all")
    })
    public JsonResult<PageData<BrandListItemVO>> list(@Range(min = 1, message = "请提交有效的页码值！") Integer page,
                                                      String queryType) {
        log.debug("开始处理【查询品牌列表】的请求，页码：{}", page);
        Integer pageNum = page == null ? 1 : page;
        PageData<BrandListItemVO> pageData;
        if ("all".equals(queryType)) {
            pageData = brandService.list(pageNum, Integer.MAX_VALUE);
        } else {
            pageData = brandService.list(pageNum);
        }
        return JsonResult.ok(pageData);
    }

}
