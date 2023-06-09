package cn.tedu.csmall.product.controller;

import cn.tedu.csmall.commons.pojo.vo.PageData;
import cn.tedu.csmall.commons.web.JsonResult;
import cn.tedu.csmall.product.pojo.param.SpuAddNewParam;
import cn.tedu.csmall.product.pojo.vo.SpuFullInfoVO;
import cn.tedu.csmall.product.pojo.vo.SpuListItemVO;
import cn.tedu.csmall.product.pojo.vo.SpuStandardVO;
import cn.tedu.csmall.product.service.ISpuService;
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
 * 处理SPU相关请求的控制器类
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Slf4j
@RestController
@RequestMapping("/spu")
@Validated
@Api(tags = "07. SPU管理模块")
public class SpuController {

    @Autowired
    private ISpuService spuService;

    public SpuController() {
        log.debug("创建控制器类对象：SpuController");
    }

    // http://localhost:9180/spu/add-new
    @PostMapping("/add-new")
    @PreAuthorize("hasAuthority('/pms/product/add-new')")
    @ApiOperation("新增SPU")
    @ApiOperationSupport(order = 100)
    public JsonResult<Void> addNew(@Valid SpuAddNewParam spuAddNewParam) {
        log.debug("开始处理【新增SPU】的请求，参数：{}", spuAddNewParam);
        spuService.addNew(spuAddNewParam);
        return JsonResult.ok();
    }

    // http://localhost:9180/spu/9527
    @GetMapping("/{id:[0-9]+}")
    @PreAuthorize("hasAuthority('/pms/product/read')")
    @ApiOperation("根据ID查询SPU信息")
    @ApiOperationSupport(order = 410)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "SPU ID", required = true, dataType = "long")
    })
    public JsonResult<SpuStandardVO> getStandardById(@PathVariable @Range(min = 1, message = "请提交有效的SPU ID值！") Long id) {
        log.debug("开始处理【根据ID查询SPU完整信息】的请求，参数：{}", id);
        SpuStandardVO spu = spuService.getStandardById(id);
        return JsonResult.ok(spu);
    }

    // http://localhost:9180/spu/9527/full-info
    @GetMapping("/{id:[0-9]+}/full-info")
    @PreAuthorize("hasAuthority('/pms/product/read')")
    @ApiOperation("根据ID查询SPU完整信息")
    @ApiOperationSupport(order = 411)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "SPU ID", required = true, dataType = "long")
    })
    public JsonResult<SpuFullInfoVO> getFullInfoById(@PathVariable @Range(min = 1, message = "请提交有效的SPU ID值！") Long id) {
        log.debug("开始处理【根据ID查询SPU完整信息】的请求，参数：{}", id);
        SpuFullInfoVO spuFullInfo = spuService.getFullInfoById(id);
        return JsonResult.ok(spuFullInfo);
    }

    // http://localhost:9180/spu/list
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('/pms/product/read')")
    @ApiOperation("查询SPU列表")
    @ApiOperationSupport(order = 420)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", defaultValue = "1", paramType = "query", dataType = "long"),
            @ApiImplicitParam(name = "queryType", value = "查询类型", example = "all")
    })
    public JsonResult<PageData<SpuListItemVO>> list(@Range(min = 1, message = "请提交有效的页码值！") Integer page,
                                                    String queryType) {
        log.debug("开始处理【查询SPU列表】的请求，页码：{}", page);
        Integer pageNum = page == null ? 1 : page;
        PageData<SpuListItemVO> pageData;
        if ("all".equals(queryType)) {
            pageData = spuService.list(pageNum, Integer.MAX_VALUE);
        } else {
            pageData = spuService.list(pageNum);
        }
        return JsonResult.ok(pageData);
    }

}
