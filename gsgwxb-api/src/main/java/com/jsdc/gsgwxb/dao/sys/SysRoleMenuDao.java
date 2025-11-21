package com.jsdc.gsgwxb.dao.sys;

import org.springframework.stereotype.Repository;


@Repository
public class SysRoleMenuDao {


    public String selectUserMenuList(String userId) {
        StringBuilder sql = new StringBuilder();
        sql.append("  select DISTINCT m.* from sys_menu m LEFT JOIN sys_role_menu rm on rm.menuId=m.id and rm.isDel=0 ");
        sql.append(" LEFT JOIN sys_user_role ur on rm.roleId=ur.roleId and ur.isDel =0 ");
        sql.append(" where m.isDel=0 and ur.userId='" + userId + "'  ");
        sql.append(" ORDER BY m.sort  ");
        return sql.toString();
    }

    public String countRoleMenu(String menuId) {
        StringBuilder sql = new StringBuilder();
        sql.append("  SELECT count(*) FROM `sys_role_menu` srm LEFT JOIN sys_role sr ON srm.roleId = sr.id WHERE srm.isDel=0 AND sr.isDel = 0 AND srm.menuId = '" + menuId+"'");
        return sql.toString();
    }
}
