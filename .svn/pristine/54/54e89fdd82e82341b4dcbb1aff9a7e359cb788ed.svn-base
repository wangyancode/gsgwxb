package com.jsdc.gsgwxb.dao.sys;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.gsgwxb.model.sys.SysRole;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;


@Repository
public class SysRoleDao {


    public String selectPageList(Page page, SysRole bean) {
        StringBuilder builder = new StringBuilder();
        builder.append("select role.*,GROUP_CONCAT(DISTINCT m.menuName)as menuName  from sys_role role LEFT JOIN sys_role_menu rm on rm.roleId=role.id and rm.isDel=0 ");
        builder.append("LEFT JOIN sys_menu m on m.id=rm.menuId where role.isDel='0' ");
        if (null != bean) {
            if (StringUtils.isNotEmpty(bean.getRoleName())) {
                builder.append(" and role.roleName like '%" + bean.getRoleName() + "%' ");
            }
            if (StringUtils.isNotEmpty(bean.getRoleCode())) {
                builder.append(" and role.roleCode like '%" + bean.getRoleCode() + "%' ");
            }
            if (null != bean.getIsFlag()) {
                builder.append(" and role.isFlag=" + bean.getIsFlag() + " ");
            }
            if (StrUtil.isNotEmpty(bean.getStartTime())&&StrUtil.isNotEmpty(bean.getEndTime())){
                builder.append(" and role.createTime between '"+bean.getStartTime()+"' and '"+bean.getEndTime()+"' ");
            }
        }
        builder.append(" GROUP BY role.id Order by role.sort asc");
        return builder.toString();
    }
}
