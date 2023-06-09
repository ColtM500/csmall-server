package cn.tedu.csmall.product.mapper;

import cn.tedu.csmall.product.pojo.entity.Album;
import cn.tedu.csmall.product.pojo.vo.AlbumListItemVO;
import cn.tedu.csmall.product.pojo.vo.AlbumStandardVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;

@Repository
public interface AlbumMapper extends BaseMapper<Album> {

    /**
     * 批量插入相册数据
     *
     * @param albums 相册列表
     * @return 受影响的行数
     */
    int insertBatch(List<Album> albums);

    List<AlbumListItemVO> list();

    AlbumStandardVO getStandardById(Long id);

    @NotNull(message = "id不可为0!")
    int deleteById(Long id);
}
