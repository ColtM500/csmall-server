package cn.tedu.csmall.product.service.impl;

import cn.tedu.csmall.product.ex.ServiceException;
import cn.tedu.csmall.product.mapper.AlbumMapper;
import cn.tedu.csmall.product.pojo.entity.Album;
import cn.tedu.csmall.product.pojo.param.AlbumAddNewParam;
import cn.tedu.csmall.product.pojo.param.AlbumUpdateInfoParam;
import cn.tedu.csmall.product.pojo.vo.AlbumListItemVO;
import cn.tedu.csmall.product.pojo.vo.PageData;
import cn.tedu.csmall.product.service.IAlbumService;
import cn.tedu.csmall.product.util.PageInfoToPageDataConvert;
import cn.tedu.csmall.product.web.ServiceCode;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class AlbumServiceImpl implements IAlbumService {

    @Autowired(required = false)
    private AlbumMapper albumMapper;

    @Override
    public void addNew(AlbumAddNewParam albumAddNewParam) {
        log.debug("开始处理【添加相册】的业务，参数:{}", albumAddNewParam);
        //检查相册名称是否被占用 如果被占用 则抛出异常
        QueryWrapper<Album> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", albumAddNewParam.getName());  //name='参数中的相册名称'
        int countByName = albumMapper.selectCount(queryWrapper);
        log.debug("根据相册名称统计匹配的相册数量，结果:{}", countByName);
        if (countByName>0){
            String message = "添加相册失败，相册名称已被占用";
            throw new ServiceException(ServiceCode.ERR_CONFLICT,message);
        }

        //将相册数据写入到数据库中
        Album album = new Album();
        BeanUtils.copyProperties(albumAddNewParam, album);
        album.setGmtCreate(LocalDateTime.now());
        album.setGmtModified(LocalDateTime.now());

        albumMapper.insert(album);
        log.debug("将新的相册数据写入到数据库中，完成!");
    }

    @Override
    public void updateInfoById(Long id, AlbumUpdateInfoParam albumUpdateInfoParam) {
        log.warn("等待施工……");
    }

    @Override
    public PageData<AlbumListItemVO> list(Integer pageNum) {
        Integer pageSize = 5;
        return list(pageNum, pageSize);
    }

    @Override
    public PageData<AlbumListItemVO> list(Integer pageNum, Integer pageSize) {
        log.debug("开始处理【查询相册列表】的业务，页码：{}，每页记录数：{}", pageNum, pageSize);
        PageHelper.startPage(pageNum, pageSize);
        List<AlbumListItemVO> list = albumMapper.list();
        PageInfo<AlbumListItemVO> pageInfo = new PageInfo<>(list);
        PageData<AlbumListItemVO> pageData = PageInfoToPageDataConvert.convert(pageInfo);
        log.debug("查询完成，即将返回：{}", pageData);
        return pageData;
    }

}
