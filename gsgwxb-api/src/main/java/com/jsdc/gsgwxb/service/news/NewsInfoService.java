package com.jsdc.gsgwxb.service.news;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.gsgwxb.mapper.news.NewsInfoMapper;
import com.jsdc.gsgwxb.model.sys.SysFile;
import com.jsdc.gsgwxb.model.sys.SysUser;
import com.jsdc.gsgwxb.news.NewsInfo;
import com.jsdc.gsgwxb.service.BaseService;
import com.jsdc.gsgwxb.service.sys.SysFileService;
import com.jsdc.gsgwxb.service.sys.SysUserService;
import com.jsdc.gsgwxb.utils.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class NewsInfoService extends BaseService<NewsInfo> {
    @Autowired
    private NewsInfoMapper mainMapper;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysFileService fileService;


    /**
     * 分页
     */
    public Page<NewsInfo> selectPage(NewsInfo bean) {
        SysUser sysUser = sysUserService.getUser();
        Page<NewsInfo> page = mainMapper.queryPage(new Page<>(bean.getPageNo(), bean.getPageSize()), bean);
        for (NewsInfo testBug : page.getRecords()) {
            convert(testBug, sysUser);
        }
        return page;
    }

    /**
     * 列表
     */
    public List<NewsInfo> selectList(NewsInfo bean) {
        SysUser sysUser = sysUserService.getUser();
        List<NewsInfo> list = mainMapper.queryList(bean);
        for (NewsInfo testBug : list) {
            convert(testBug, sysUser);
        }
        return list;
    }

    /**
     * 详情
     */
    public NewsInfo selectBean(NewsInfo bean) {
        SysUser sysUser = sysUserService.getUser();
        NewsInfo res = mainMapper.queryBean(bean);
        if (res != null) {
            convert(res, sysUser);
        }
        return res;
    }

    public void convert(NewsInfo bean, SysUser sysUser) {

        //创建人
        if (null != bean.getCreateUser()) {
            SysUser sysUserConvert = sysUserService.getById(bean.getCreateUser());
            bean.setCreate_user_name(sysUserConvert.getRealName());
        }


        //附件
        List<SysFile> sysFiles = fileService.getBaseMapper().selectList(new LambdaQueryWrapper<SysFile>().eq(SysFile::getBizId, bean.getId()).eq(SysFile::getBizType, 9));
        bean.setFiles(sysFiles);
    }


    /**
     * 新增修改
     */
    public ResultInfo addUpdateInfo(NewsInfo bean) {
        SysUser sysUser = sysUserService.getUser();
        if (null != bean.getId()) {
            mainMapper.updateById(bean);
        } else {
            bean.setCreateUser(sysUser.getId());
            bean.setCreateTime(new Date());
            bean.setIsDel(0);
            mainMapper.insert(bean);
        }

        List<SysFile> files = bean.getFiles();

        if (CollUtil.isNotEmpty(files)) {
            //文件不为空时，先做删除。后添加
            List<SysFile> sysFiles = fileService.getBaseMapper().selectList(new LambdaQueryWrapper<SysFile>().eq(SysFile::getBizId, bean.getId()).eq(SysFile::getBizType, "9"));
            sysFiles.forEach(sysFile -> {
                fileService.removeById(sysFile);
            });

            for (SysFile file : files) {
                file.setBizId(bean.getId());
                file.setId(IdUtil.simpleUUID());
                file.setBizType(1);
                file.setCreateUser(sysUserService.getUser().getId());
                file.setCreateTime(new Date());
                fileService.save(file);
            }
        }

        return ResultInfo.success();
    }


    public boolean checkOnly(NewsInfo bean) {
        boolean checkOnly = true; //没有重复
//        QueryWrapper<NewsInfo> queryWrapper = new QueryWrapper<>() ;
//        queryWrapper.eq("code",bean.getCode()) ;
//        queryWrapper.eq("is_del","0") ;
//        List<NewsInfo> info = mainMapper.selectList(queryWrapper) ;
//        if(!info.isEmpty()){
//            checkOnly = false ;
//            if(null!=bean.getId()){
//                if(bean.getId().equals(info.get(0).getId())){
//                    checkOnly = true ;
//                }
//            }
//        }
        return checkOnly;
    }

    /**
     * 删除
     */
    public boolean delData(String id) {
        NewsInfo bean = new NewsInfo();
        bean.setId(id);
        bean.setIsDel(1);
        //清空图片
        List<SysFile> sysFiles = fileService.getBaseMapper().selectList(new LambdaQueryWrapper<SysFile>().eq(SysFile::getBizId, bean.getId()).eq(SysFile::getBizType, 1));
        sysFiles.forEach(sysFile -> {
            sysFile.deleteById();
        });
        return bean.updateById();
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    public ResultInfo batchDeletion(String ids) {
        String[] idList = ids.split(",");
        for (String id : idList) {
            delData(id);
        }
        return ResultInfo.success();
    }

}
