package cn.tedu.csmall.product.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
@TableName("pms_brand")
public class Brand implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;
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

    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;
}
