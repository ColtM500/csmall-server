package cn.tedu.csmall.product.pojo.param;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class SkuAddNewParam implements Serializable {

    /**
     * SPU id
     */
    private Long spuId;

    /**
     * 标题
     */
    private String title;

    /**
     * 条型码
     */
    private String barCode;

    /**
     * 属性模板id
     */
    private Long attributeTemplateId;

    /**
     * 全部属性，使用JSON格式表示
     */
    private String specifications;

    /**
     * 相册id
     */
    private Long albumId;

    /**
     * 组图URLs，使用JSON格式表示
     */
    private String pictures;

    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 当前库存
     */
    private Integer stock;

    /**
     * 库存预警阈值
     */
    private Integer stockThreshold;

    /**
     * 排序序号
     */
    private Integer sort;

}
