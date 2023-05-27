package cn.tedu.csmall.passport.controller;

import cn.tedu.csmall.passport.pojo.param.AdminAddNewParam;
import cn.tedu.csmall.passport.pojo.param.AdminLoginInfoParam;
import cn.tedu.csmall.passport.pojo.vo.AdminListItemVO;
import cn.tedu.csmall.passport.pojo.vo.PageData;
import cn.tedu.csmall.passport.pojo.vo.RoleListItemVO;
import cn.tedu.csmall.passport.security.AdminDetails;
import cn.tedu.csmall.passport.security.LoginPrincipal;
import cn.tedu.csmall.passport.service.IAdminService;
import cn.tedu.csmall.passport.web.JsonResult;
import cn.tedu.csmall.passport.web.ServiceCode;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

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
    @GetMapping("/list")
    @ApiOperation("查询管理员列表")
    @ApiOperationSupport(order = 420)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", paramType = "query")
    })
    public JsonResult list(@Range(min = 1, message = "查询管理员列表失败，请提供正确的页码值!") Integer page){
        log.debug("开始处理【查询管理员列表】的请求，页码：{}", page);
        if (page == null || page < 1){
            page = 1;
        }
        PageData<AdminListItemVO> pageData = service.list(page);
        //ok返回JsonResult对象 由于返回了链式写法 每个set方法都返回了当前对象 可作为当前JsonResult方法的返回值
        //否则平时set方法的返回值都是void
        return JsonResult.ok().setData(pageData);
    }

    // http://localhost:9181/admin/list1
    @GetMapping("/list1")
    @ApiOperation("查询管理员列表")
    @ApiOperationSupport(order = 420)
    //API文档会把userDetails误认为传递的值 故会要你填值 所以要加上@ApiIgnore
    public JsonResult list1(@ApiIgnore  @AuthenticationPrincipal LoginPrincipal loginPrincipal){
        log.debug("当事人ID:{}", loginPrincipal.getId());
        log.debug("当事人用户名:{}", loginPrincipal.getUsername());
        return JsonResult.fail(ServiceCode.ERR_UNKNOWN, "此功能尚未实现");
    }
}
