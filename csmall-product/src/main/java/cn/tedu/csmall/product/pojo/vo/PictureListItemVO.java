package cn.tedu.csmall.product.pojo.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class PictureListItemVO implements Serializable {

    /**
     * 数据id
     */
    private Long id;

    /**
     * 相册id
     */
    private Long albumId;

    /**
     * 图片url
     */
    private String url;

    /**
     * 是否为封面图片，1=是，0=否
     */
    private Integer isCover;

    /**
     * 图片简介
     */
    private String description;

    /**
     * 图片宽度，单位：px
     */
    private Integer width;

    /**
     * 图片高度，单位：px
     */
    private Integer height;

    /**
     * 排序序号
     */
    private Integer sort;

}