package com.jsdc.gsgwxb.dao.sys;

import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Repository;


@Repository
public class SysMenuDao {


    public String selectRoleMenuList(String userId) {
        StringBuilder sql = new StringBuilder();
        sql.append("select m.* from sys_user u LEFT JOIN sys_user_role ur on u.id=ur.userId and ur.isDel = 0 ");
        sql.append("LEFT JOIN sys_role_menu rm on rm.roleId =ur.roleId and rm.isDel = 0 ");
        sql.append("LEFT JOIN sys_menu m on m.id =rm.menuId ");
        sql.append("where u.isDel=0 and m.isFlag =0 ");
        if (StrUtil.isNotEmpty(userId)) {
            sql.append(" and ur.userId='").append(userId).append("'   ");
        }
        sql.append("GROUP BY m.id ");
        sql.append("order by m.sort ");
        return sql.toString();
    }
}
