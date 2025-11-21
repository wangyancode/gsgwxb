package com.jsdc.gsgwxb.mapper.sys;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.gsgwxb.dao.sys.SysUserDao;
import com.jsdc.gsgwxb.model.sys.SysUser;
import com.jsdc.gsgwxb.vo.SysUserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;


@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    @SelectProvider(type = SysUserDao.class, method = "selectPageList")
    Page<SysUserVo> selectPageList(Page page, SysUserVo bean);
    @SelectProvider(type = SysUserDao.class, method = "selectListExport")
    List<SysUserVo> selectListExport(SysUserVo bean);
    @SelectProvider(type = SysUserDao.class, method = "getUserInfo")
    List<SysUser> getUserInfo(String userName, String passWord);
}
