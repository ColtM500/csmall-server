package cn.tedu.csmall.passport.service;

import cn.tedu.csmall.passport.pojo.param.AdminAddNewParam;
import cn.tedu.csmall.passport.pojo.param.AdminLoginInfoParam;
import cn.tedu.csmall.passport.pojo.vo.AdminListItemVO;
import cn.tedu.csmall.passport.pojo.vo.PageData;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface IAdminService {

    /**
     * 管理员登录
     * @param adminLoginInfoParam
     * @return 此管理员的信息对应的JWT数据
     */
    String login(AdminLoginInfoParam adminLoginInfoParam);

    void addNew(AdminAddNewParam adminAddNewParam);

    PageData<AdminListItemVO> list(Integer pageNum);

    PageData<AdminListItemVO> list(Integer pageNum, Integer pageSize);
}
