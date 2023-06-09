package cn.tedu.csmall.product.controller;

import cn.tedu.csmall.commons.pojo.vo.PageData;
import cn.tedu.csmall.commons.web.JsonResult;
import cn.tedu.csmall.product.pojo.param.PictureAddNewParam;
import cn.tedu.csmall.product.pojo.vo.PictureListItemVO;
import cn.tedu.csmall.product.service.IPictureService;
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
 * 处理图片数据相关请求的控制器
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Slf4j
@RestController
@RequestMapping("/pictures")
@Validated
@Api(tags = "05. 图片管理模块")
public class PictureController {

    @Autowired
    private IPictureService pictureService;

    public PictureController() {
        log.info("创建控制器对象：PictureController");
    }

    // http://localhost:9180/pictures/add-new
    @PostMapping("/add-new")
    @PreAuthorize("hasAuthority('/pms/picture/add-new')")
    @ApiOperation("添加图片")
    @ApiOperationSupport(order = 100)
    public JsonResult<Void> addNew(@Valid PictureAddNewParam pictureAddNewParam) {
        log.debug("开始处理【添加图片】的请求，参数：{}", pictureAddNewParam);
        pictureService.addNew(pictureAddNewParam);
        return JsonResult.ok();
    }

    // http://localhost:9180/pictures/9527/delete
    @PostMapping("/{id:[0-9]+}/delete")
    @PreAuthorize("hasAuthority('/pms/picture/delete')")
    @ApiOperation("删除图片")
    @ApiOperationSupport(order = 200)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "图片ID", required = true, dataType = "long")
    })
    public JsonResult<Void> delete(@PathVariable @Range(min = 1, message = "请提交有效的图片ID值！") Long id) {
        log.debug("开始处理【根据ID删除图片】的请求，参数：{}", id);
        pictureService.delete(id);
        return JsonResult.ok();
    }

    // http://localhost:9180/pictures/9527/set-cover
    @PostMapping("/{id:[0-9]+}/set-cover")
    @PreAuthorize("hasAuthority('/pms/picture/update')")
    @ApiOperation("根据ID将图片设置为封面")
    @ApiOperationSupport(order = 310)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "图片ID", required = true, dataType = "long")
    })
    public JsonResult<Void> setEnable(@PathVariable @Range(min = 1, message = "请提交有效的图片ID值！") Long id) {
        log.debug("开始处理【根据ID将图片设置为封面】的请求，参数：{}", id);
        pictureService.setCover(id);
        return JsonResult.ok();
    }

    // http://localhost:9180/pictures/list-by-album
    @GetMapping("/list-by-album")
    @PreAuthorize("hasAuthority('/pms/picture/read')")
    @ApiOperation("根据相册ID查询图片数据列表")
    @ApiOperationSupport(order = 421)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "albumId", value = "相册ID", required = true, paramType = "query", dataType = "long"),
            @ApiImplicitParam(name = "page", value = "页码", defaultValue = "1", paramType = "query", dataType = "long"),
            @ApiImplicitParam(name = "queryType", value = "查询类型", example = "all")
    })
    public JsonResult<PageData<PictureListItemVO>> list(@Range(message = "请提交有效的相册ID值！") Long albumId,
                                                        @Range(min = 1, message = "请提交有效的页码值！") Integer page,
                                                        String queryType) {
        log.debug("开始处理【根据相册ID查询图片数据列表】的请求，相册：{}，页码：{}", albumId, page);
        Integer pageNum = page == null ? 1 : page;
        PageData<PictureListItemVO> pageData;
        if ("all".equals(queryType)) {
            pageData = pictureService.listByAlbumId(albumId, pageNum, Integer.MAX_VALUE);
        } else {
            pageData = pictureService.listByAlbumId(albumId, pageNum);
        }
        return JsonResult.ok(pageData);
    }

}
