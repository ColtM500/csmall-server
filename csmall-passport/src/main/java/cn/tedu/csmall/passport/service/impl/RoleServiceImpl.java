package cn.tedu.csmall.passport.service.impl;

import cn.tedu.csmall.passport.mapper.RoleMapper;
import cn.tedu.csmall.passport.pojo.vo.PageData;
import cn.tedu.csmall.passport.pojo.vo.RoleListItemVO;
import cn.tedu.csmall.passport.service.IRoleService;
import cn.tedu.csmall.passport.util.PageInfoToPageDataConvert;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private RoleMapper mapper;

    @Override
    public List<RoleListItemVO> list() {
        log.debug("开始处理【查询角色列表】的业务，无参数");
        List<RoleListItemVO> roleListItemVOList = mapper.list();
        return roleListItemVOList;
    }

    @Override
    public PageData<RoleListItemVO> list(Integer pageNum) {
        Integer pageSize = 5;
        return list(pageNum, pageSize);
    }

    @Override
    public PageData<RoleListItemVO> list(Integer pageNum, Integer pageSize) {
        log.debug("开始处理【查询相册列表】的业务，页码：{}，每页记录数：{}", pageNum, pageSize);
        PageHelper.startPage(pageNum, pageSize);
        List<RoleListItemVO> roleList = mapper.list();
        PageInfo<RoleListItemVO> pageInfo = new PageInfo<>(roleList);
        PageData<RoleListItemVO> pageData = PageInfoToPageDataConvert.convert(pageInfo);
        log.debug("查询完成，即将返回：{}", pageData);
        return pageData;
    }
}
