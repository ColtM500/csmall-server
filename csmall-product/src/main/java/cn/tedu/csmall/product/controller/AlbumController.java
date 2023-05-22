package cn.tedu.csmall.product.controller;

import cn.tedu.csmall.product.ex.ServiceException;
import cn.tedu.csmall.product.pojo.param.AlbumAddNewParam;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@Slf4j
@RestController
@RequestMapping("/album")
@Api(tags = "04.相册管理模块")
//@Validated
public class AlbumController {

    @Autowired
    private IAlbumService albumService;

//    @ExceptionHandler
//    public String handleServiceException(ServiceException e){
//        log.warn("程序运行过程中出现了ServiceException, 将统一处理！");
//        log.warn("异常信息:{}", e.getMessage());
//        return  e.getMessage();
//    }

    // http://localhost:8080/album/add-new?name=TestName001&description=TestDescription001&sort=99
    @PostMapping("/add-new")
    @ApiOperation("添加相册")
    @ApiOperationSupport(order = 100)
    public JsonResult addNew(@Valid AlbumAddNewParam albumAddNewParam){
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
            @ApiImplicitParam(name = "albumId", value = "相册ID", required = true, dataType = "long"),
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "long")
    })
    public String delete(@Range(min = 1, message = "根据ID删除相册失败，请提交合法的ID值！") @RequestParam Long albumId, Long userId){
        throw new RuntimeException("别急，还没做!");
    }
}
