package cn.tedu.csmall.passport.controller;

import cn.tedu.csmall.commons.security.LoginPrincipal;
import cn.tedu.csmall.commons.web.JsonResult;
import cn.tedu.csmall.commons.web.ServiceCode;
import cn.tedu.csmall.passport.pojo.param.AdminAddNewParam;
import cn.tedu.csmall.passport.pojo.param.AdminLoginInfoParam;
import cn.tedu.csmall.passport.pojo.vo.AdminListItemVO;
import cn.tedu.csmall.commons.pojo.vo.PageData;
import cn.tedu.csmall.passport.service.IAdminService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Slf4j
@RestController
@RequestMapping("/admin")
@Validated
@Api(tags = "01. 管理员管理模块")
public class AdminController {

    @Autowired
    private IAdminService service;

    // http://localhost:9181/admin/login
    @PostMapping("/login")
    @ApiOperation("管理员登录")
    @ApiOperationSupport(order = 50)
    public JsonResult login(AdminLoginInfoParam adminLoginInfoParam){
        log.debug("开始处理【管理员登录】的请求，参数:{}", adminLoginInfoParam);
        String jwt = service.login(adminLoginInfoParam);
        return JsonResult.ok(jwt);
    }

    // http://localhost:9181/admin/add-new
    @PostMapping("/add-new")
    @ApiOperation("添加管理员")
    @ApiOperationSupport(order = 100)
    public JsonResult addNew(AdminAddNewParam albumAddNewParam) {
        log.debug("开始处理【添加管理员】的请求，参数：{}", albumAddNewParam);
        service.addNew(albumAddNewParam);
        return JsonResult.ok();
    }

    // http://localhost:9181/admin/list
    @GetMapping("list")
    @PreAuthorize("hasAuthority('/ams/admin/read')")
    @ApiOperation("查询管理员列表")
    @ApiOperationSupport(order = 420)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", defaultValue = "1", dataType = "long")
    })
    public JsonResult<PageData<AdminListItemVO>> list(Integer page) {
        log.debug("开始处理【查询管理员列表】的请求，页码：{}", page);
        Integer pageNum = page == null ? 1 : page;
        PageData<AdminListItemVO> pageData = service.list(pageNum);
        return JsonResult.ok(pageData);
    }

    // http://localhost:9181/admin/list1
    @GetMapping("/list1")
    @ApiOperation("查询管理员列表")
    @ApiOperationSupport(order = 420)
    //API文档会把userDetails误认为传递的值 故会要你填值 所以要加上@ApiIgnore
    public JsonResult list1(@ApiIgnore  @AuthenticationPrincipal LoginPrincipal loginPrincipal){
        log.debug("当事人ID:{}", loginPrincipal.getId());
        log.debug("当事人用户名:{}", loginPrincipal.getUsername());
        return JsonResult.fail(ServiceCode.ERROR_UNKNOWN, "此功能尚未实现");
    }
}
