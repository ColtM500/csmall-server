package cn.tedu.csmall.product.controller;

import cn.tedu.csmall.commons.pojo.vo.PageData;
import cn.tedu.csmall.commons.web.JsonResult;
import cn.tedu.csmall.product.pojo.param.AlbumAddNewParam;
import cn.tedu.csmall.product.pojo.param.AlbumUpdateInfoParam;
import cn.tedu.csmall.product.pojo.vo.AlbumListItemVO;
import cn.tedu.csmall.product.pojo.vo.AlbumStandardVO;
import cn.tedu.csmall.product.security.LoginPrincipal;
import cn.tedu.csmall.product.service.IAlbumService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;


@Slf4j
@RestController
@RequestMapping("/album")
@Api(tags = "04.相册管理模块")
@Validated
public class AlbumController {

    @Autowired
    private IAlbumService albumService;

    // http://localhost:9180/album/add-new?name=TestName001&description=TestDescription001&sort=99
    @PostMapping("/add-new")
    @ApiOperation("添加相册")
    @ApiOperationSupport(order = 100)
    public JsonResult addNew(@Valid @RequestBody AlbumAddNewParam albumAddNewParam,
                             @ApiIgnore @AuthenticationPrincipal LoginPrincipal loginPrincipal){
            log.debug("开始处理【添加相册】的请求，参数：{}", albumAddNewParam);
            log.debug("当事人:{}", loginPrincipal);
            albumService.addNew(albumAddNewParam);
            log.debug("处理【添加相册】的请求，完成！");

            return JsonResult.ok();
    }

    // http://localhost:9180/album/delete
    @PostMapping("/delete")
    @ApiOperation("根据ID删除相册")
    @ApiOperationSupport(order = 200)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "相册ID", required = true, dataType = "long"),
    })
    public JsonResult delete(@Range(min = 1, message = "根据ID删除相册失败，请提交合法的ID值！") @RequestParam Long id){
        log.debug("开始处理【根据ID删除相册】的请求，参数：{}",id);
        albumService.delete(id);
        return JsonResult.ok();
    }

    // http://localhost:9180/album/list
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('/pms/album/read')")
    @ApiOperation("查询相册列表")
    @ApiOperationSupport(order = 420)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", defaultValue = "1", paramType = "query", dataType = "long"),
            @ApiImplicitParam(name = "queryType", value = "查询类型", example = "all")
    })
    public JsonResult<PageData<AlbumListItemVO>> list(@Range(min = 1, message = "请提交有效的页码值！") Integer page,
                                                      String queryType) {
        log.debug("开始处理【查询相册列表】的请求，页码：{}", page);
        Integer pageNum = page == null ? 1 : page;
        PageData<AlbumListItemVO> pageData;
        if ("all".equals(queryType)) {
            pageData = albumService.list(pageNum, Integer.MAX_VALUE);
        } else {
            pageData = albumService.list(pageNum);
        }
        return JsonResult.ok(pageData);
    }

    // http://localhost:9180/album/getStandardById
    @GetMapping("/getStandardById")
    @ApiOperation("根据ID查询相册详情")
    @ApiOperationSupport(order = 410)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "相册ID", required = true, dataType = "long")
    })
    public JsonResult getStandardById(@Range(min = 1, message = "根据ID查询相册详情失败，请提交合法的ID值！")
                                      @RequestParam Long id) {
        log.debug("开始处理【根据ID查询相册详情】的请求，参数：{}", id);
        AlbumStandardVO result = albumService.getStandardById(id);
        return JsonResult.ok(result);//因为要把后端查询得到的东西传到前端去 所以要这个放置这个result对象
    }

    // http://localhost:9180/album/updateInfoById
    @PostMapping("/updateInfoById")
    @ApiOperation("修改相册")
    @ApiOperationSupport(order = 300)
    public JsonResult updateInfoById(
            @RequestParam
            @Range(min = 1, message = "请提交有效的相册ID值！")
            @Valid @RequestBody Long id, AlbumUpdateInfoParam albumUpdateInfoParam){
        log.debug("开始处理【修改加相册】的请求，参数：{}", albumUpdateInfoParam);
        albumService.updateInfoById(id,albumUpdateInfoParam);
        log.debug("处理【修改相册】的请求，完成！");
        return JsonResult.ok();
    }
}
