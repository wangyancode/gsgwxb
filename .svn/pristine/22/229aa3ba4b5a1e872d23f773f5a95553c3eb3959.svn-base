package com.jsdc.gsgwxb.mapper.sys;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.gsgwxb.dao.sys.SysFileDao;
import com.jsdc.gsgwxb.model.sys.SysFile;
import com.jsdc.gsgwxb.vo.sys.SysFileVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

@Mapper
public interface SysFileMapper extends BaseMapper<SysFile> {

    @SelectProvider(type = SysFileDao.class, method = "getPage")
    Page<SysFile> getPage(Page page, SysFileVo vo);

}