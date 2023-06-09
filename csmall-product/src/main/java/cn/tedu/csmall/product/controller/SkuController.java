package cn.tedu.csmall.product.controller;

import cn.tedu.csmall.commons.pojo.vo.PageData;
import cn.tedu.csmall.commons.web.JsonResult;
import cn.tedu.csmall.product.pojo.param.SkuAddNewParam;
import cn.tedu.csmall.product.pojo.vo.CategoryListItemVO;
import cn.tedu.csmall.product.pojo.vo.SkuListItemVO;
import cn.tedu.csmall.product.pojo.vo.SkuStandardVO;
import cn.tedu.csmall.product.service.ISkuService;
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
 * 处理SKU相关请求的控制器类
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Slf4j
@RestController
@RequestMapping("/sku")
@Validated
@Api(tags = "08. SKU管理模块")
public class SkuController {

    @Autowired
    private ISkuService skuService;

    public SkuController() {
        log.debug("创建控制器类对象：SpuController");
    }

    // http://localhost:9180/sku/add-new
    @PostMapping("/add-new")
    @PreAuthorize("hasAuthority('/pms/product/add-new')")
    @ApiOperation("新增SKU")
    @ApiOperationSupport(order = 100)
    public JsonResult<Void> addNew(@Valid SkuAddNewParam skuAddNewParam) {
        log.debug("开始处理【新增SKU】的请求，参数：{}", skuAddNewParam);
        skuService.addNew(skuAddNewParam);
        return JsonResult.ok();
    }

    // http://localhost:9180/sku/9527
    @GetMapping("/{id:[0-9]+}")
    @PreAuthorize("hasAuthority('/pms/product/read')")
    @ApiOperation("根据ID查询SKU信息")
    @ApiOperationSupport(order = 410)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "SPU ID", required = true, dataType = "long")
    })
    public JsonResult<SkuStandardVO> getStandardById(@PathVariable @Range(min = 1, message = "请提交有效的SKU ID值！") Long id) {
        log.debug("开始处理【根据ID查询SPU完整信息】的请求，参数：{}", id);
        SkuStandardVO sku = skuService.getStandardById(id);
        return JsonResult.ok(sku);
    }

    // http://localhost:9180/sku/list-by-spu
    @GetMapping("/list-by-spu")
    @PreAuthorize("hasAuthority('/pms/product/read')")
    @ApiOperation("根据SPU查询SKU列表")
    @ApiOperationSupport(order = 420)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "spuId", value = "SPU ID", required = true, paramType = "query", dataType = "long")
    })
    public JsonResult<PageData<SkuListItemVO>> list(@Range(message = "请提交有效的SPU的ID值！") Long spuId,
                                                         @Range(min = 1, message = "请提交有效的页码值！") Integer page) {
        log.debug("开始处理【根据SPU查询SKU列表】的请求，spuId：{}，页码：{}", spuId, page);
        Integer pageNum = page == null ? 1 : page;
        PageData<SkuListItemVO> pageData = skuService.listBySpuId(spuId, pageNum);
        return JsonResult.ok(pageData);
    }

}