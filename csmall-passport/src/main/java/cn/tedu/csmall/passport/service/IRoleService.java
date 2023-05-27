package cn.tedu.csmall.passport.service;

import cn.tedu.csmall.passport.pojo.vo.PageData;
import cn.tedu.csmall.passport.pojo.vo.RoleListItemVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface IRoleService {

    List<RoleListItemVO> list();

//    PageData<RoleListItemVO> list (Integer pageNum);

//    PageData<RoleListItemVO> list (Integer pageNum, Integer pageSize);
}
