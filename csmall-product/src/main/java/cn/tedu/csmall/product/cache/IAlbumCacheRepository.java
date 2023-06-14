package cn.tedu.csmall.product.cache;

import cn.tedu.csmall.product.pojo.vo.AlbumListItemVO;
import cn.tedu.csmall.product.pojo.vo.AlbumStandardVO;

import java.util.List;

/**
 * 处理相册缓存数据的数据访问接口
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
public interface IAlbumCacheRepository {

    /**
     * 向Redis中新增相册详情数据
     *
     * @param albumStandardVO 相册详情数据
     */
    void save(AlbumStandardVO albumStandardVO);

    /**
     * 向Redis中追加相册列表数据
     *
     * @param albumList 相册列表数据
     */
    void save(List<AlbumListItemVO> albumList);

    /**
     * 删除Redis中的相册详情数据
     *
     * @param id 尝试删除的相册数据的id
     * @return 删除成功时将返回true，否则返回false
     */
    Boolean delete(Long id);

    /**
     * 删除Redis中的相册列表数据
     *
     * @return 删除成功时将返回true，否则返回false
     */
    Boolean deleteList();

    /**
     * 删除Redis中的全部相册详情数据
     *
     * @return 成功删除的数据的数量
     */
    long deleteAllItem();

    /**
     * 从Redis中读取出相册详情数据
     *
     * @param id 相册数据的id
     * @return 匹配的相册详情数据，如果没有匹配的数据，则返回null
     */
    AlbumStandardVO getStandardById(Long id);

    /**
     * 从Redis中读取相册列表数据
     *
     * @return 相册列表
     */
    List<AlbumListItemVO> list();

    /**
     * 从Redis中读取相册列表数据
     *
     * @param start 起始位置的下标，如果从头开始读取，则取值为0
     * @param end   结果位置的下标，如果读取至最后一条数据，则取值为-1
     * @return 相册列表
     */
    List<AlbumListItemVO> list(long start, long end);


}
