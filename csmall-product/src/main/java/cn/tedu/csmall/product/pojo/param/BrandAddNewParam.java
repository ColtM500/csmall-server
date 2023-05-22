package cn.tedu.csmall.product.pojo.param;

import lombok.Data;


@Data
public class BrandAddNewParam {

    private String name;
    private String pinyin;
    private String logo;
    private String description;
    private String keywords;
    private Integer sort;
    private Integer sales;
    private Integer productCount;
    private Integer commentCount;
    private Integer positiveCommentCount;
    private Integer enable;
}
