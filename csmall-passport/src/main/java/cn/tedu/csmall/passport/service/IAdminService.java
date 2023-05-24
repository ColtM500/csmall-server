package cn.tedu.csmall.passport.service;

import cn.tedu.csmall.passport.pojo.param.AdminAddNewParam;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface IAdminService {

    void addNew(AdminAddNewParam adminAddNewParam);
}
