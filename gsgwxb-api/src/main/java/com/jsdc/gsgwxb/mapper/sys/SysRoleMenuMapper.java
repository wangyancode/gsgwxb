package com.jsdc.gsgwxb.mapper.sys;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.gsgwxb.dao.sys.SysRoleMenuDao;
import com.jsdc.gsgwxb.model.sys.SysMenu;
import com.jsdc.gsgwxb.model.sys.SysRoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;


@Mapper
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {

    //根据登录用户id获取菜单
    @SelectProvider(method = "selectUserMenuList", type = SysRoleMenuDao.class)
    List<SysMenu> selectUserMenuList(String userId);
    @SelectProvider(method = "countRoleMenu", type = SysRoleMenuDao.class)
    Integer countRoleMenu(String menuId);
}
