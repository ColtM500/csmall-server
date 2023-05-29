package cn.tedu.csmall.passport.service.impl;

import cn.tedu.csmall.passport.mapper.RoleMapper;
import cn.tedu.csmall.passport.pojo.vo.RoleListItemVO;
import cn.tedu.csmall.passport.service.IRoleService;
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

//    @Override
//    public PageData<RoleListItemVO> list(Integer pageNum) {
//        Integer pageSize = 5;
//        return list(pageNum, pageSize);
//    }

//    @Override
//    public PageData<RoleListItemVO> list(Integer pageNum, Integer pageSize) {
//        log.debug("开始处理【查询角色列表】的业务，页码：{}，每页记录数：{}", pageNum, 6);
//        PageHelper.startPage(pageNum, 6);
//        List<RoleListItemVO> roleList = mapper.list();
//        PageInfo<RoleListItemVO> pageInfo = new PageInfo<>(roleList);
//        PageData<RoleListItemVO> pageData = PageInfoToPageDataConvert.convert(pageInfo);
//        log.debug("查询完成，即将返回：{}", pageData);
//        return pageData;
//    }
}
