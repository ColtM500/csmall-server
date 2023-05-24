package cn.tedu.csmall.product.controller;

import cn.tedu.csmall.product.ex.ServiceException;
import cn.tedu.csmall.product.pojo.param.AlbumAddNewParam;
import cn.tedu.csmall.product.pojo.param.AlbumUpdateInfoParam;
import cn.tedu.csmall.product.pojo.vo.AlbumListItemVO;
import cn.tedu.csmall.product.pojo.vo.AlbumStandardVO;
import cn.tedu.csmall.product.pojo.vo.PageData;
import cn.tedu.csmall.product.service.IAlbumService;
import cn.tedu.csmall.product.web.JsonResult;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;


@Slf4j
@RestController
@RequestMapping("/album")
@Api(tags = "04.相册管理模块")
@Validated
public class AlbumController {

    @Autowired
    private IAlbumService albumService;

    // http://localhost:8080/album/add-new?name=TestName001&description=TestDescription001&sort=99
    @PostMapping("/add-new")
    @ApiOperation("添加相册")
    @ApiOperationSupport(order = 100)
    public JsonResult addNew(@Valid @RequestBody AlbumAddNewParam albumAddNewParam){
            log.debug("开始处理【添加相册】的请求，参数：{}", albumAddNewParam);
            albumService.addNew(albumAddNewParam);
            log.debug("处理【添加相册】的请求，完成！");

            return JsonResult.ok();
    }

    // http://localhost:8080/album/delete
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

    // http://localhost:8080/album/list
    @GetMapping("/list")
    @ApiOperation("查询相册列表")
    @ApiOperationSupport(order = 420)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", paramType = "query")
    })
    public JsonResult list(@Range(min = 1, message = "查询相册列表失败，请提供正确的页码值!") Integer page){
        log.debug("开始处理【查询相册列表】的请求，页码：{}", page);
        if (page == null || page < 1){
            page = 1;
        }
        PageData<AlbumListItemVO> pageData = albumService.list(page);
        //ok返回JsonResult对象 由于返回了链式写法 每个set方法都返回了当前对象 可作为当前JsonResult方法的返回值
        //否则平时set方法的返回值都是void
        return JsonResult.ok().setData(pageData);
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

    // http://localhost:8080/album/updateInfoById
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
