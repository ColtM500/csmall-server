package cn.tedu.csmall.product.pojo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 类别与属性模板关联的标准VO类
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Data
public class CategoryAttributeTemplateStandardVO implements Serializable {

    /**
     * 数据id
     */
    private Long id;

    /**
     * 类别id
     */
    private Long categoryId;

    /**
     * 属性模板id
     */
    private Long attributeTemplateId;

}
