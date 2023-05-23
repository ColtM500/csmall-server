package cn.tedu.csmall.product.util;

import cn.tedu.csmall.product.pojo.vo.AlbumListItemVO;
import cn.tedu.csmall.product.pojo.vo.PageData;
import com.github.pagehelper.PageInfo;

/**
 * 分页信息的转换器，因为PageHelper框架的PageInfo中的部分属性不需要使用，则使用此类将PageInfo类型转换成自定义的PageData类型
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */

public class PageInfoToPageDataConvert<T> {
    public static <T> PageData<T> convert(PageInfo<T> pageInfo){
        PageData<T> pageData = new PageData<T>()
                .setCurrentPage(pageInfo.getPageNum())
                .setMaxPage(pageInfo.getPages())
                .setPageSize(pageInfo.getSize())
                .setTotal(pageInfo.getTotal())
                .setList(pageInfo.getList());
        return pageData;
    }
 }
