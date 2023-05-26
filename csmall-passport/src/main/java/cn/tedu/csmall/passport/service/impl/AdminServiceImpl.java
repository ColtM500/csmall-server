package cn.tedu.csmall.passport.service.impl;

import cn.tedu.csmall.passport.ex.ServiceException;
import cn.tedu.csmall.passport.mapper.AdminMapper;
import cn.tedu.csmall.passport.mapper.AdminRoleMapper;
import cn.tedu.csmall.passport.pojo.entity.Admin;
import cn.tedu.csmall.passport.pojo.entity.AdminRole;
import cn.tedu.csmall.passport.pojo.param.AdminAddNewParam;
import cn.tedu.csmall.passport.pojo.param.AdminLoginInfoParam;
import cn.tedu.csmall.passport.pojo.vo.AdminListItemVO;
import cn.tedu.csmall.passport.pojo.vo.PageData;
import cn.tedu.csmall.passport.security.AdminDetails;
import cn.tedu.csmall.passport.service.IAdminService;
import cn.tedu.csmall.passport.util.PageInfoToPageDataConvert;
import cn.tedu.csmall.passport.web.ServiceCode;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@Transactional
public class AdminServiceImpl implements IAdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private AdminRoleMapper adminRoleMapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public String login(AdminLoginInfoParam adminLoginInfoParam) {
        log.debug("开始处理【管理员登录】的业务，参数:{}", adminLoginInfoParam);
        //创建认证时所需的参数对象
            //此处是service拿出controller传过来的用户名和密码
            // 之所以要创建这个Authentication 是因为下面的authenticationManager.authenticate（）只装authentication对象
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                adminLoginInfoParam.getUsername(),
                adminLoginInfoParam.getPassword()
        );
        //执行认证 并获取认证结果
        Authentication authenticateResult = authenticationManager.authenticate(authentication);
        log.debug("验证登录完成,认证结果:{}", authenticateResult);

        //从认证结果中取出所需的数据->当事人 认证结果的当事人就是UserDetailService返回的数据 AdminDetails里的数据
        Object principal = authenticateResult.getPrincipal();
        AdminDetails adminDetails = (AdminDetails) principal;

        //生成JWT
        String secretKey = "sbbbcccccccccccccccccc";
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", adminDetails.getId());
        claims.put("username", adminDetails.getUsername());
        String jwt = Jwts.builder()
                //Header
                .setHeaderParam("alg", "HS256")
                .setHeaderParam("typ", "JWT")
                //Payload
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis()+30L*24*60*60*1000))//30后不加L超出了上线 直接过期
                //Verify Signature
                .signWith(SignatureAlgorithm.HS256, secretKey)
                //生成
                .compact();
        log.debug("生成了此管理员的信息对应的JWT:{}", jwt);
        return jwt;

        //==============================使用JWT之后这部分就不用了
        //将认证结果存入到SecurityContext中
//        SecurityContext securityContext = SecurityContextHolder.getContext();
//        securityContext.setAuthentication(authenticateResult);


    }

    @Override
    public void addNew(AdminAddNewParam adminAddNewParam) {
        log.debug("开始处理【添加管理员】的业务，参数：{}", adminAddNewParam);
        //检查管理员名称是否被占用 如果被占用 则抛出异常
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", adminAddNewParam.getUsername());  //name='参数中的相册名称'
        int countByUsername = adminMapper.selectCount(queryWrapper);
        log.debug("根据管理员用户名统计匹配的管理员数量，结果：{}", countByUsername);
        if (countByUsername>0){
            String message = "添加管理员失败，管理员名称已被占用";
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
        int rows = adminMapper.insert(admin);
        if (rows != 1) {
            String message = "添加管理员失败，服务器忙，请稍后再试！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_INSERT, message);
        }
        log.debug("将新的管理员数据写入到数据库中，完成!");

        //将管理员与角色的关联数据写入到数据库中
        Long[] roleIds = adminAddNewParam.getRoleIds();
        AdminRole[] adminRoleList = new AdminRole[roleIds.length];
        for (int i = 0; i < adminRoleList.length; i++) {
            AdminRole adminRole = new AdminRole();
            adminRole.setAdminId(admin.getId());
            adminRole.setRoleId(roleIds[i]);
            adminRole.setGmtCreate(LocalDateTime.now());
            adminRole.setGmtModified(LocalDateTime.now());
            adminRoleList[i] = adminRole;
        }
        rows = adminRoleMapper.insertBatch(adminRoleList);
        if (rows != adminRoleList.length) {
            String message = "添加管理员失败，服务器忙，请稍后再试！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_INSERT, message);
        }
        log.debug("将新的管理员与角色关联的数据插入到数据库中，完成!");
    }

    @Override
    public PageData<AdminListItemVO> list(Integer pageNum) {
        log.debug("开始处理【查询管理员列表】的业务，页码:{}", pageNum);
        Integer pageSize = 5;
        return list(pageNum, pageSize);
    }

    @Override
    public PageData<AdminListItemVO> list(Integer pageNum, Integer pageSize) {
        log.debug("开始处理【查询管理员列表】的业务，页码：{}，每页记录数：{}", pageNum, pageSize);
        PageHelper.startPage(pageNum, pageSize);
        List<AdminListItemVO> list = adminMapper.list();
        PageInfo<AdminListItemVO> pageInfo = new PageInfo<>(list);
        PageData<AdminListItemVO> pageData = PageInfoToPageDataConvert.convert(pageInfo);
        log.debug("查询完成，即将返回：{}", pageData);
        return pageData;
    }

}
