package cn.tedu.csmall.product.pojo.param;

import lombok.Data;



@Data
public class CategoryAddNewParam {

    private String name;
    private Long parentId;
    private String keywords;
    private Integer sort;
    private String icon;
    private Integer enable;
    private Integer isDisplay;

}
