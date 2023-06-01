package cn.tedu.csmall.product.service;

import cn.tedu.csmall.commons.pojo.vo.PageData;
import cn.tedu.csmall.product.pojo.param.PictureAddNewParam;
import cn.tedu.csmall.product.pojo.vo.PictureListItemVO;
import org.springframework.transaction.annotation.Transactional;

/**
 * 图片业务接口
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Transactional
public interface IPictureService {

    /**
     * 添加图片
     *
     * @param pictureAddNewParam 图片
     */
    void addNew(PictureAddNewParam pictureAddNewParam);

    /**
     * 根据ID删除图片
     *
     * @param id 图片ID
     */
    void delete(Long id);

    /**
     * 根据ID将图片设置为封面
     *
     * @param id 图片ID
     */
    void setCover(Long id);

    /**
     * 根据相册ID查询图片数据列表，将使用默认的每页记录数
     *
     * @param albumId 相册ID
     * @param pageNum 页码
     * @return 类别列表
     */
    PageData<PictureListItemVO> listByAlbumId(Long albumId, Integer pageNum);

    /**
     * 根据相册ID查询图片数据列表，将使用默认的每页记录数
     *
     * @param albumId  相册ID
     * @param pageNum  页码
     * @param pageSize 每页记录数
     * @return 类别列表
     */
    PageData<PictureListItemVO> listByAlbumId(Long albumId, Integer pageNum, Integer pageSize);

}
