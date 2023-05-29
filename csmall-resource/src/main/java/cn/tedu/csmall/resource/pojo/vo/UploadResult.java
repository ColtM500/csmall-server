package cn.tedu.csmall.resource.pojo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 文件上传的结果
 */
@Data
public class UploadResult implements Serializable {

    private String url;
    private Long fileSize;
    private String contentType;
    private String fileName;
}
