package cn.tedu.csmall.product.controller;

import cn.tedu.csmall.commons.ex.ServiceException;
import cn.tedu.csmall.commons.pojo.vo.PageData;
import cn.tedu.csmall.commons.web.JsonResult;
import cn.tedu.csmall.product.pojo.param.CategoryAddNewParam;
import cn.tedu.csmall.product.pojo.param.CategoryUpdateInfoParam;
import cn.tedu.csmall.product.pojo.vo.CategoryListItemVO;
import cn.tedu.csmall.product.pojo.vo.CategoryStandardVO;
import cn.tedu.csmall.product.pojo.vo.CategoryTreeItemVO;
import cn.tedu.csmall.product.service.ICategoryService;
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
import java.util.List;

@Slf4j
@RestController
@Validated
@RequestMapping("/category")
@Api(tags = "01. 类别管理模块")
public class CategoryController {
    @Autowired
    private ICategoryService categoryService;

    // http://localhost:9180/category/add-new?name=TestName001
    @PostMapping("/add-new")
    @ApiOperation("添加类别")
    @ApiOperationSupport(order = 100)
    public JsonResult<Void> addNew(CategoryAddNewParam categoryAddNewParam){
        log.debug("开始处理【添加类别】的请求，参数：{}", categoryAddNewParam);
        categoryService.addNew(categoryAddNewParam);
        return JsonResult.ok();
    }

    // http://localhost:9180/categories/1/delete
    @PostMapping("/{id:[0-9]+}/delete")
    @PreAuthorize("hasAuthority('/pms/category/delete')")
    @ApiOperation("根据ID删除类别")
    @ApiOperationSupport(order = 200)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "类别ID", required = true, dataType = "long")
    })
    public JsonResult<Void> delete(@PathVariable @Range(min = 1, message = "请提交有效的类别ID值！") Long id) {
        log.debug("开始处理【根据ID删除类别】的请求，参数：{}", id);
        categoryService.delete(id);
        return JsonResult.ok();
    }

    // http://localhost:9180/categories/1/enable
    @PostMapping("/{id:[0-9]+}/enable")
    @PreAuthorize("hasAuthority('/pms/category/update')")
    @ApiOperation("启用类别")
    @ApiOperationSupport(order = 310)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "类别ID", required = true, dataType = "long")
    })
    public JsonResult<Void> setEnable(@PathVariable @Range(min = 1, message = "请提交有效的类别ID值！") Long id) {
        log.debug("开始处理【启用类别】的请求，参数：{}", id);
        categoryService.setEnable(id);
        return JsonResult.ok();
    }

    // http://localhost:9180/categories/1/disable
    @PostMapping("/{id:[0-9]+}/disable")
    @PreAuthorize("hasAuthority('/pms/category/update')")
    @ApiOperation("禁用类别")
    @ApiOperationSupport(order = 311)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "类别ID", required = true, dataType = "long")
    })
    public JsonResult<Void> setDisable(@PathVariable @Range(min = 1, message = "请提交有效的类别ID值！") Long id) {
        log.debug("开始处理【禁用类别】的请求，参数：{}", id);
        categoryService.setDisable(id);
        return JsonResult.ok();
    }

    // http://localhost:9180/categories/1/display
    @PostMapping("/{id:[0-9]+}/display")
    @PreAuthorize("hasAuthority('/pms/category/update')")
    @ApiOperation("显示")
    @ApiOperationSupport(order = 312)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "类别ID", required = true, dataType = "long")
    })
    public JsonResult<Void> setDisplay(@PathVariable @Range(min = 1, message = "请提交有效的类别ID值！") Long id) {
        log.debug("开始处理【显示】的请求，参数：{}", id);
        categoryService.setDisplay(id);
        return JsonResult.ok();
    }

    // http://localhost:9180/categories/1/hidden
    @PostMapping("/{id:[0-9]+}/hidden")
    @PreAuthorize("hasAuthority('/pms/category/update')")
    @ApiOperation("隐藏")
    @ApiOperationSupport(order = 313)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "类别ID", required = true, dataType = "long")
    })
    public JsonResult<Void> setHidden(@PathVariable @Range(min = 1, message = "请提交有效的类别ID值！") Long id) {
        log.debug("开始处理【隐藏】的请求，参数：{}", id);
        categoryService.setHidden(id);
        return JsonResult.ok();
    }

    // http://localhost:9180/categories/tree
    @GetMapping("/tree")
    @PreAuthorize("hasAuthority('/pms/category/read')")
    @ApiOperation("查询类别树")
    @ApiOperationSupport(order = 420)
    public JsonResult<List<CategoryTreeItemVO>> listTree() {
        log.debug("开始处理【获取类别树】的业务，参数：无");
        List<CategoryTreeItemVO> categoryTree = categoryService.listTree();
        return JsonResult.ok(categoryTree);
    }

    // http://localhost:9180/categories/list-by-parent
    @GetMapping("/list-by-parent")
    @PreAuthorize("hasAuthority('/pms/category/read')")
    @ApiOperation("根据父级查询子级列表")
    @ApiOperationSupport(order = 421)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parentId", value = "父级类别ID", required = true, paramType = "query", dataType = "long"),
            @ApiImplicitParam(name = "page", value = "页码", defaultValue = "1", paramType = "query", dataType = "long")
    })
    public JsonResult<PageData<CategoryListItemVO>> list(@Range(message = "请提交有效的父级类别ID值！") Long parentId,
                                                         @Range(min = 1, message = "请提交有效的页码值！") Integer page) {
        log.debug("开始处理【根据父级类别查询子级类别列表】的请求，父级类别：{}，页码：{}", parentId, page);
        Integer pageNum = page == null ? 1 : page;
        PageData<CategoryListItemVO> pageData = categoryService.listByParentId(parentId, pageNum);
        return JsonResult.ok(pageData);
    }


    // http://localhost:9180/categories/9527/update
    @PostMapping("/{id:[0-9]+}/update")
    @PreAuthorize("hasAuthority('/pms/category/update')")
    @ApiOperation("修改类别详情")
    @ApiOperationSupport(order = 300)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "类别ID", required = true, dataType = "long")
    })
    public JsonResult<Void> updateInfoById(@PathVariable @Range(min = 1, message = "请提交有效的类别ID值！") Long id,
                                           @Valid CategoryUpdateInfoParam categoryUpdateInfoParam) {
        log.debug("开始处理【修改类别详情】的请求，ID：{}，新数据：{}", id, categoryUpdateInfoParam);
        categoryService.updateStandardById(id, categoryUpdateInfoParam);
        return JsonResult.ok();
    }

    // http://localhost:9180/categories/9527
    @GetMapping("/{id:[0-9]+}")
    @PreAuthorize("hasAuthority('/pms/category/read')")
    @ApiOperation("根据ID查询类别详情")
    @ApiOperationSupport(order = 410)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "类别ID", required = true, dataType = "long")
    })
    public JsonResult<CategoryStandardVO> getStandardById(
            @PathVariable @Range(min = 1, message = "请提交有效的类别ID值！") Long id) {
        log.debug("开始处理【根据ID查询类别详情】的请求，参数：{}", id);
        CategoryStandardVO queryResult = categoryService.getStandardById(id);
        return JsonResult.ok(queryResult);
    }
}
