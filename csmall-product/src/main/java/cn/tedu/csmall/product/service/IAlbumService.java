package cn.tedu.csmall.product.service;

import cn.tedu.csmall.product.pojo.param.AlbumAddNewParam;
import cn.tedu.csmall.product.pojo.param.AlbumUpdateInfoParam;
import cn.tedu.csmall.product.pojo.vo.AlbumListItemVO;
import cn.tedu.csmall.product.pojo.vo.PageData;
import com.github.pagehelper.Page;

public interface IAlbumService {

    void addNew(AlbumAddNewParam albumAddNewParam);

    void updateInfoById(Long id, AlbumUpdateInfoParam albumUpdateInfoParam);

    PageData<AlbumListItemVO> list(Integer pageNum);

    PageData<AlbumListItemVO> list(Integer pageNum, Integer pageSize);
}
