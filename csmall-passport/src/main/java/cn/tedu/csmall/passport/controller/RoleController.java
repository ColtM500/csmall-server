package cn.tedu.csmall.passport.controller;

import cn.tedu.csmall.passport.pojo.vo.PageData;
import cn.tedu.csmall.passport.pojo.vo.RoleListItemVO;
import cn.tedu.csmall.passport.service.IRoleService;
import cn.tedu.csmall.passport.web.JsonResult;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@RequestMapping("/roles")
@Validated
@Api(tags = "02. 角色管理模块")
public class RoleController {

    @Autowired
    private IRoleService service;

    // http://localhost:9181/roles/list
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
        PageData<RoleListItemVO> pageData = service.list(page);
        //ok返回JsonResult对象 由于返回了链式写法 每个set方法都返回了当前对象 可作为当前JsonResult方法的返回值
        //否则平时set方法的返回值都是void
        return JsonResult.ok().setData(pageData);
    }

}
