package cn.tedu.csmall.resource.controller;


import cn.tedu.csmall.commons.ex.ServiceException;
import cn.tedu.csmall.commons.web.JsonResult;
import cn.tedu.csmall.commons.web.ServiceCode;
import cn.tedu.csmall.resource.pojo.vo.UploadResult;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/upload")
@Api(tags = "文件上传模块")
public class uploadProductImage {

    //访问的主机端口
    @Value("${csmall.upload.host}")
    private String host;

    //上传的根级文件夹
    @Value("${csmall.upload.base-dir}")
    private String uploadBaseDir;

    //最大文件上传数量
    @Value("${csmall.upload.product-image.max-size}")
    private Integer productImageMaxSize;

    //文件的类型
    @Value("${csmall.upload.product-image.types}")
    private List<String> productImageValidTypes;

    //文件目录名
    private String productImageDirName = "product-image/";
    //日期格式
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd/");

    @PostMapping("/upload/image/product")
    public JsonResult<UploadResult> uploadProductImage(@RequestParam("file") MultipartFile multipartFile) throws IOException {

        //检查客户端是否提交了上传的文件（例如是否选择了文件再点击提交表单）
        if (multipartFile==null || multipartFile.isEmpty()){
            String message = "上传图片失败，请选择你要上传的文件!";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_UPLOAD_EMPTY, message);
        }

        //检查文件大小是否超标
        if (multipartFile.getSize()>productImageMaxSize){
            String message = "上传图片失败，不允许超过" +productImageMaxSize+"MB的图片文件!";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_UPLOAD_EXCEED_MAX_SIZE, message);
        }

        //检查文件类型是否在允许的范围内
        if (!productImageValidTypes.contains(multipartFile.getContentType())){
            String message = "上传商品图片失败，请使用以下类型的图片文件：" + productImageValidTypes;
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_UPLOAD_INVALID_TYPE, message);
        }

        //尽可能把文件夹分得更加清楚
        String dirName = simpleDateFormat.format(new Date());
        File productImageDir = new File(uploadBaseDir, productImageDirName);
        File uploadDir = new File(productImageDir, dirName);

        if (!uploadDir.exists()){
            uploadDir.mkdirs();
        }

        //避免文件名重复
        String newFileName = UUID.randomUUID().toString();
        String originalFilename = multipartFile.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFullFileName = newFileName + suffix;
        File newFile = new File(uploadDir, newFullFileName);

        multipartFile.transferTo(newFile);

        //上传成功后，返回相关数据，至少需要返回访问此文件的URL
        String url = new StringBuilder()
                .append(host)
                .append(productImageDirName)
                .append(dirName)
                .append(newFullFileName)
                .toString();

        UploadResult uploadResult = new UploadResult();
        uploadResult.setUrl(url);
        uploadResult.setFileSize(multipartFile.getSize());
        uploadResult.setContentType(multipartFile.getContentType());
        uploadResult.setFileName(newFullFileName);
        return JsonResult.ok(uploadResult);
    }
}
