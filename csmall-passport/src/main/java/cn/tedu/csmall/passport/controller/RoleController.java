package cn.tedu.csmall.passport.controller;

import cn.tedu.csmall.commons.web.JsonResult;
import cn.tedu.csmall.passport.pojo.vo.RoleListItemVO;
import cn.tedu.csmall.passport.service.IRoleService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


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
    @PreAuthorize("hasAuthority('/ams/admin/read')")
    @ApiOperation("查询角色列表")
    @ApiOperationSupport(order = 420)
    public JsonResult list() {
        log.debug("开始处理【查询角色列表】的请求，无参数");
        List<RoleListItemVO> list = service.list();
        return JsonResult.ok(list);
    }

}
