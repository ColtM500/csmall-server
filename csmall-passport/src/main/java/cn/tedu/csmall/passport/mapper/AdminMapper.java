package cn.tedu.csmall.passport.mapper;

import cn.tedu.csmall.passport.pojo.entity.Admin;
import cn.tedu.csmall.passport.pojo.vo.AdminListItemVO;
import cn.tedu.csmall.passport.pojo.vo.AdminLoginInfoVO;
import cn.tedu.csmall.passport.pojo.vo.AdminStandardVO;
import cn.tedu.csmall.passport.pojo.vo.RoleListItemVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminMapper extends BaseMapper<Admin> {

    List<AdminListItemVO> list();

    AdminLoginInfoVO getLoginInfoByUsername(String username);

    AdminStandardVO getStandardById(Long id);
}
