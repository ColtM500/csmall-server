package cn.tedu.csmall.product.cache;

import cn.tedu.csmall.product.pojo.vo.AlbumStandardVO;

public interface IAlbumCacheRepository {

    void save(AlbumStandardVO albumStandardVO);

    AlbumStandardVO getStandardById(Long id);

    boolean delete(Long id);
}
