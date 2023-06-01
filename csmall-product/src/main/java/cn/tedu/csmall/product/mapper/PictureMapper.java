package cn.tedu.csmall.product.mapper;

import cn.tedu.csmall.product.pojo.entity.Picture;
import cn.tedu.csmall.product.pojo.vo.PictureListItemVO;
import cn.tedu.csmall.product.pojo.vo.PictureStandardVO;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PictureMapper extends BaseMapper<Picture> {

    /**
     * 批量插入图片数据
     *
     * @param pictureList 若干个图片数据的集合
     * @return 受影响的行数
     */
    int insertBatch(List<Picture> pictureList);

    /**
     * 根据ID查询图片数据详情
     *
     * @param id 图片ID
     * @return 匹配的图片数据详情，如果没有匹配的数据，则返回null
     */
    PictureStandardVO getStandardById(Long id);

    /**
     * 根据相册ID查询图片数据列表
     *
     * @return 图片数据列表
     */
    List<PictureListItemVO> listByAlbumId(Long albumId);
}
