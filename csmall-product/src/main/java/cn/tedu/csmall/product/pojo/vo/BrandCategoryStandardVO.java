package cn.tedu.csmall.product.pojo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 品牌类别关联的标准VO类
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Data
public class BrandCategoryStandardVO implements Serializable {

    /**
     * 数据id
     */
    private Long id;

    /**
     * 品牌id
     */
    private Long brandId;

    /**
     * 类别id
     */
    private Long categoryId;

}
