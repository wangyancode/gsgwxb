package com.jsdc.gsgwxb.dao.company;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.gsgwxb.model.company.CompanyDetail;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;


@Repository
public class CompanyDetailDao {
    public String queryPage(Page page, CompanyDetail bean) {
        return query(bean);
    }

    public String queryList(CompanyDetail bean) {
        return query(bean);
    }

    public String queryBean(CompanyDetail bean) {
        return query(bean);
    }

    public String query(CompanyDetail bean) {
        String sql = "select main.*, createUser.realName create_user_name from news_info main";
        sql += " left join sys_user createUser ON createUser.id = main.createUser";

        sql += " where main.isDel = '0'";
        if (bean != null) {
            if (null != bean.getId()) {
                sql += " and main.id = '" + bean.getId() + "'";
            }

            if (StringUtils.isNotEmpty(bean.getTitle())) {
                sql += " and main.title like '%" + bean.getTitle() + "%'";
            }

            if (StringUtils.isNotEmpty(bean.getQuery_create_time_start()) && StringUtils.isNotEmpty(bean.getQuery_create_time_end())) {
                sql += " and main.createTime >= '" + bean.getQuery_create_time_start() + " 00:00:00'";
                sql += " and main.createTime <= '" + bean.getQuery_create_time_end() + " 23:59:59'";
            }

        }
        sql += " order by main.sort, main.createTime desc";
        return sql;
    }

}
