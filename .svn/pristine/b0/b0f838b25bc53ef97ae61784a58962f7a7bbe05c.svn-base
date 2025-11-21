package com.jsdc.gsgwxb.dao.sys;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.gsgwxb.vo.SysUserVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;


@Repository
public class SysUserDao {

    public String getUserInfo(String userName, String passWord) {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from sys_user where isDel=0 and states = 1 ");
        if (StrUtil.isNotEmpty(userName)) {
            sql.append(" and userName = '").append(userName).append("' ");
            sql.append(" and passWord = '").append(passWord).append("' ");
            sql.append(" or account = '").append(userName).append("' ");
            sql.append(" and passWord = '").append(passWord).append("' ");
            sql.append(" and  isDel=0 and states = 1");
        }
        return sql.toString();
    }


    public String selectPageList(Page page, SysUserVo bean) {
        StringBuilder builder = new StringBuilder();
        builder.append("select us.*,GROUP_CONCAT(DISTINCT r.roleName) as  role_names from sys_user us LEFT JOIN sys_user_role urs " +
                "ON us.id = urs.userId and urs.isDel=0 ");
        builder.append("LEFT JOIN sys_role r ON r.id = urs.roleId ");
        builder.append("where us.isDel =0 ");
        if (null != bean) {
            if (StringUtils.isNotEmpty(bean.getUserName())) {
                builder.append(" and (us.userName like '%" + bean.getUserName() + "%' or us.realName like '%" + bean.getUserName() + "%' )");
            }
            if (StringUtils.isNotEmpty(bean.getAccount())) {
                builder.append(" and us.account like'%" + bean.getAccount() + "%' ");
            }
            if (StringUtils.isNotEmpty(bean.getPhone())) {
                builder.append(" and us.phone like'%" + bean.getPhone() + "%' ");
            }
            if (StringUtils.isNotEmpty(bean.getRoleId())) {
                builder.append(" and urs.roleId='" + bean.getRoleId() + "' ");
            }
            if (bean.getStates() != null) {
                builder.append(" and us.getStates='" + bean.getStates() + "' ");
            }
            if (StrUtil.isNotEmpty(bean.getStartTime()) && StrUtil.isNotEmpty(bean.getEndTime())) {
                builder.append(" and us.createTime between '" + bean.getStartTime() + "' and '" + bean.getEndTime() + "' ");
            }
        }
        builder.append(" GROUP BY us.id ");
        builder.append(" ORDER BY us.createTime desc ");
        return builder.toString();
    }

    public String selectListExport(SysUserVo bean) {
        StringBuilder builder = new StringBuilder();
        builder.append("select us.*,GROUP_CONCAT(DISTINCT r.roleName) as  role_names from sys_user us LEFT JOIN sys_user_role urs " +
                "ON us.id = urs.userId and urs.isDel=0 ");
        builder.append("LEFT JOIN sys_role r ON r.id = urs.roleId ");
        builder.append("where us.isDel =0 ");
        if (null != bean) {
            if (StringUtils.isNotEmpty(bean.getUserName())) {
                builder.append(" and (us.userName like '%" + bean.getUserName() + "%' or us.realName like '%" + bean.getUserName() + "%' )");
            }
            if (StringUtils.isNotEmpty(bean.getAccount())) {
                builder.append(" and us.account like'%" + bean.getAccount() + "%' ");
            }
            if (StringUtils.isNotEmpty(bean.getRoleId())) {
                builder.append(" and urs.roleId='" + bean.getRoleId() + "' ");
            }
            if (StrUtil.isNotEmpty(bean.getStartTime()) && StrUtil.isNotEmpty(bean.getEndTime())) {
                builder.append(" and us.createTime between '" + bean.getStartTime() + "' and '" + bean.getEndTime() + "' ");
            }
        }
        builder.append(" GROUP BY us.id ");
        builder.append(" ORDER BY us.createTime desc ");
        return builder.toString();
    }

    public String getUserMap(){
        return "SELECT\n" +
                "\tsu.id AS userId,\n" +
                "\tsu.userName AS realName,\n" +
                "\toc.plate AS carPlate,\n" +
                "\tsu.longitude,\n" +
                "\tsu.latitude,\n" +
                "\tsu.onlineStatus,\n" +
                "\toi.title,\n" +
                "\t(\n" +
                "\tSELECT\n" +
                "\t\tGROUP_CONCAT( u.realName SEPARATOR ', ' ) \n" +
                "\tFROM\n" +
                "\t\torder_user o1\n" +
                "\t\tLEFT JOIN order_info o2 ON o2.id = o1.orderId\n" +
                "\t\tLEFT JOIN sys_user u ON u.id = o1.userId \n" +
                "\tWHERE\n" +
                "\t\toi.id = o2.id  AND o1.id != ou.id \n" +
                "\t) AS partners \n" +
                "FROM\n" +
                "\tsys_user su\n" +
                "\tLEFT JOIN order_user ou ON su.id = ou.userId\n" +
                "\tLEFT JOIN order_info oi ON ou.orderId = oi.id\n" +
                "\tLEFT JOIN operation_car oc ON oc.id = oi.carId \n" +
                "WHERE\n" +
                "\t1 = 1 \n" +
                "\tAND oi.`status` = '3' \n" +
                "\tAND su.onlineStatus != 1";
    }

}
