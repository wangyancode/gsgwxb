package com.jsdc.gsgwxb.service.sys;


import com.jsdc.gsgwxb.mapper.sys.SysRoleMenuMapper;
import com.jsdc.gsgwxb.model.sys.SysMenu;
import com.jsdc.gsgwxb.model.sys.SysRoleMenu;
import com.jsdc.gsgwxb.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class SysRoleMenuService extends BaseService<SysRoleMenu> {

    @Autowired
    private SysRoleMenuMapper roleMenuMapper;

    //根据登录用户id获取菜单
    public List<SysMenu> selectUserMenuList(String userId) {
        return roleMenuMapper.selectUserMenuList(userId);
    }
}
