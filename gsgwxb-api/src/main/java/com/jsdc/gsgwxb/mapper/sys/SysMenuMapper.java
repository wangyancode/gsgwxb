package com.jsdc.gsgwxb.mapper.sys;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.gsgwxb.dao.sys.SysMenuDao;
import com.jsdc.gsgwxb.model.sys.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;


@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    @SelectProvider(type = SysMenuDao.class, method = "selectRoleMenuList")
    List<SysMenu> selectRoleMenuList(String user_id);
}
