package com.jsdc.gsgwxb.dao.sys;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.gsgwxb.vo.sys.SysFileVo;
import org.springframework.stereotype.Repository;

@Repository
public class SysFileDao {


    public String getPage(Page page, SysFileVo vo) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT id, bizType, bizId, originalName, fileType, fileSize, fileName, bucket, objectName, createUser, updateUser, createTime, updateTime, isDel FROM sys_file");
        sql.append(" WHERE isDel = 0");
        return sql.toString();
    }

}