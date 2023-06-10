package cn.tedu.csmall.product.pojo.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Accessors(chain = true)
@Data
public class CategoryTreeItemVO implements Serializable {

    /**
     * 数据id，Element UI控件要求名为value
     */
    private Long value;

    /**
     * 类别名称，Element UI控件要求名为label
     */
    private String label;

    /**
     * 子级类别列表，Element UI控件要求名为children
     */
    private List<CategoryTreeItemVO> children;
}
