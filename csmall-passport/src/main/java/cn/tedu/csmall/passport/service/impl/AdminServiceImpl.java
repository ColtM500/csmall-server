package cn.tedu.csmall.passport.service.impl;

import cn.tedu.csmall.passport.ex.ServiceException;
import cn.tedu.csmall.passport.mapper.AdminMapper;
import cn.tedu.csmall.passport.mapper.AdminRoleMapper;
import cn.tedu.csmall.passport.pojo.entity.Admin;
import cn.tedu.csmall.passport.pojo.entity.AdminRole;
import cn.tedu.csmall.passport.pojo.param.AdminAddNewParam;
import cn.tedu.csmall.passport.service.IAdminService;
import cn.tedu.csmall.passport.web.ServiceCode;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class AdminServiceImpl implements IAdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private AdminRoleMapper adminRoleMapper;

    @Override
    public void addNew(AdminAddNewParam adminAddNewParam) {
        log.debug("开始处理【添加管理员】的业务，参数：{}", adminAddNewParam);
        //检查相册名称是否被占用 如果被占用 则抛出异常
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", adminAddNewParam.getUsername());  //name='参数中的相册名称'
        int countByUsername = adminMapper.selectCount(queryWrapper);
        log.debug("根据管理员用户名统计匹配的管理员数量，结果：{}", countByUsername);
        if (countByUsername>0){
            String message = "添加相册失败，相册名称已被占用";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_CONFLICT,message);
        }

        //TODO: 检查管理员手机号码是否被占用，如果被占用，则抛出异常
        //TODO: 检查管理员电子邮箱是否被占用，如果被占用，则抛出异常

        //将管理员数据写入到数据库中
        Admin admin = new Admin();
        BeanUtils.copyProperties(adminAddNewParam, admin);
        admin.setGmtCreate(LocalDateTime.now());
        admin.setGmtModified(LocalDateTime.now());
        adminMapper.insert(admin);
        log.debug("将新的管理员数据写入到数据库中，完成!");

        //将管理员与角色的关联数据写入到数据库中
        Long[] roleIds = adminAddNewParam.getRoleIds();
        AdminRole[] adminRoleList = new AdminRole[roleIds.length];
        for (int i = 0; i < adminRoleList.length; i++) {
            AdminRole adminRole = new AdminRole();
            adminRole.setRoleId(roleIds[i]);
            adminRoleList[i] = adminRole;
        }
        adminRoleMapper.insertBatch(adminRoleList);
        log.debug("将新的管理员与角色关联的数据插入到数据库中，完成!");
    }
}
