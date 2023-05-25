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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.util.JAXBResult;
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
    @ApiOperation("查询角色列表")
    @ApiOperationSupport(order = 420)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", paramType = "query")
    })
    public JsonResult list(){
        log.debug("开始处理【查询相册列表】的请求，无参数");
        List<RoleListItemVO> list = service.list();
        return JsonResult.ok(list);
    }

}
