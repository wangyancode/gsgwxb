package com.jsdc.gsgwxb.mapper.sys;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.gsgwxb.dao.sys.SysRoleDao;
import com.jsdc.gsgwxb.model.sys.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;


@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    @SelectProvider(type = SysRoleDao.class, method = "selectPageList")
    Page<SysRole> selectPageList(Page page, SysRole bean);
}
