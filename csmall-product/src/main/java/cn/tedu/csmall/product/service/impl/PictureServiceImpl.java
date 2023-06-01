package cn.tedu.csmall.product.service.impl;

import cn.tedu.csmall.commons.ex.ServiceException;
import cn.tedu.csmall.commons.pojo.vo.PageData;
import cn.tedu.csmall.commons.util.PageInfoToPageDataConvert;
import cn.tedu.csmall.commons.web.ServiceCode;
import cn.tedu.csmall.product.mapper.AlbumMapper;
import cn.tedu.csmall.product.mapper.PictureMapper;
import cn.tedu.csmall.product.pojo.entity.Picture;
import cn.tedu.csmall.product.pojo.param.PictureAddNewParam;
import cn.tedu.csmall.product.pojo.vo.AlbumStandardVO;
import cn.tedu.csmall.product.pojo.vo.PictureListItemVO;
import cn.tedu.csmall.product.pojo.vo.PictureStandardVO;
import cn.tedu.csmall.product.service.IPictureService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PictureServiceImpl implements IPictureService {

    @Autowired
    private PictureMapper pictureMapper;
    @Autowired
    private AlbumMapper albumMapper;

    @Override
    public void addNew(PictureAddNewParam pictureAddNewParam) {
        log.debug("开始处理【添加图片】的业务，参数：{}", pictureAddNewParam);
        // 调用AlbumMapper对象的int countByName(String name)方法统计此名称的相册的数量
        Long albumId = pictureAddNewParam.getAlbumId();
        AlbumStandardVO album = albumMapper.getStandardById(albumId);
        // 判断图片所属相册是否存在
        if (album == null) {
            // 是：相册名称已经存在，抛出RuntimeException异常
            String message = "添加图片失败！相册不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
        }

        // 准备需要添加到数据库的对象
        Picture picture = new Picture();
        BeanUtils.copyProperties(pictureAddNewParam, picture);
        picture.setIsCover(0);

        // 执行插入数据
        log.debug("即将向数据库中插入数据：{}", picture);
        int rows = pictureMapper.insert(picture);
        if (rows != 1) {
            String message = "添加图片失败！服务器忙，请稍后再次尝试！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_INSERT, message);
        }
    }

    @Override
    public void delete(Long id) {
        log.debug("开始处理【删除图片】的业务，参数：{}", id);
        // 检查尝试删除的图片是否存在
        Object queryResult = pictureMapper.getStandardById(id);
        if (queryResult == null) {
            String message = "删除图片失败，尝试访问的数据不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
        }

        // 执行删除
        log.debug("即将执行删除数据，参数：{}", id);
        int rows = pictureMapper.deleteById(id);
        if (rows != 1) {
            String message = "删除图片失败，服务器忙，请稍后再次尝试！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_DELETE, "删除图片失败，服务器忙，请稍后再次尝试！");
        }
    }

    @Override
    public void setCover(Long id) {
        log.debug("开始处理【根据ID将图片设置为封面】的业务，参数：{}", id);
        //检查图片是否存在
        PictureStandardVO picture = pictureMapper.getStandardById(id);
        if (picture==null){
            String message = "设置封面图片失败，尝试访问的数据不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
        }

        //检查图片是否已经是封面
        if (picture.getIsCover()==1){
            String message = "设置封面图片失败，此图片已经是所属相册的封面！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_CONFLICT, message);
        }

        //查找图片对应的相册
        AlbumStandardVO album = albumMapper.getStandardById(picture.getAlbumId());
        if (album==null){
            String message = "设置封面图片失败，此图片的相册的数据不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
        }

        //将此相册中所有图片设置为“非封面”

//        pictureMapper.updateNotCoverByAlbumId(picture1.getAlbumId());

        //将指定图片设置为封面
    }

    @Override
    public PageData<PictureListItemVO> listByAlbumId(Long albumId, Integer pageNum) {
        return listByAlbumId(albumId, pageNum, 5);
    }

    @Override
    public PageData<PictureListItemVO> listByAlbumId(Long albumId, Integer pageNum, Integer pageSize) {
        log.debug("开始处理【查询Picture列表】的业务，页码：{}，每页记录数：{}", pageNum, pageSize);
        PageHelper.startPage(pageNum, pageSize);
        List<PictureListItemVO> list = pictureMapper.listByAlbumId(albumId);
        PageInfo<PictureListItemVO> pageInfo = new PageInfo<>(list);
        PageData<PictureListItemVO> pageData = PageInfoToPageDataConvert.convert(pageInfo);
        log.debug("查询完成，即将返回：{}", pageData);
        return pageData;
    }
}
