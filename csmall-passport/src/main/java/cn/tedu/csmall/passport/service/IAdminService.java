package cn.tedu.csmall.passport.service;

import cn.tedu.csmall.passport.pojo.param.AdminAddNewParam;
import cn.tedu.csmall.passport.pojo.param.AdminLoginInfoParam;
import cn.tedu.csmall.passport.pojo.vo.AdminListItemVO;
import cn.tedu.csmall.commons.pojo.vo.PageData;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface IAdminService {

    /**
     * 管理员登录
     * @param adminLoginInfoParam
     * @return 此管理员的信息对应的JWT数据
     */
    String login(AdminLoginInfoParam adminLoginInfoParam);

    void addNew(AdminAddNewParam adminAddNewParam);

    /**
     * 查询管理员数据列表
     *
     * @param pageNum  页码
     * @param pageSize 每页记录数
     * @return 管理员数据列表
     */
    PageData<AdminListItemVO> list(Integer pageNum, Integer pageSize);
    PageData<AdminListItemVO> list(Integer pageNum);
}
