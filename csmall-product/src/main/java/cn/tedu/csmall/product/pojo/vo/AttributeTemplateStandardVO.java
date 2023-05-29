package cn.tedu.csmall.product.pojo.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class AttributeTemplateStandardVO implements Serializable {

    private Long id;
    private String name;
    private String pinyin;
    private String keywords;
    private Integer sort;

}
