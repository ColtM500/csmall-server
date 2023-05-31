package cn.tedu.csmall.product.pojo.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Accessors(chain = true)
@Data
public class CategoryTreeItemVO implements Serializable {

    private Long value;
    private String label;
    private List<CategoryTreeItemVO> children;
}
