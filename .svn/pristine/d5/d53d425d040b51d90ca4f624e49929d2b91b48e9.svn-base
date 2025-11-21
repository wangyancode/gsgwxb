package com.jsdc.gsgwxb.service.sys;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jsdc.common.minio.service.MinioTemplate;
import com.jsdc.gsgwxb.enums.G;
import com.jsdc.gsgwxb.model.sys.SysFile;
import com.jsdc.gsgwxb.vo.sys.SysFileVo;
import com.jsdc.gsgwxb.mapper.sys.SysFileMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Slf4j
@Service
@Transactional
public class SysFileService extends ServiceImpl<SysFileMapper,SysFile>  {


    @Autowired
    private SysFileMapper sysFileMapper;

    @Autowired
    private MinioTemplate minioTemplate;


    public Page<SysFile> selectPageList(SysFileVo bean) {
        Page<SysFile> page = sysFileMapper.selectPage(new Page(bean.getPageNo(), bean.getPageSize()),
                Wrappers.<SysFile>lambdaQuery().eq(SysFile::getIsDel, 0)
                .between(StringUtils.isNotBlank(bean.getStartTime()), SysFile::getCreateTime, bean.getStartTime(), bean.getEndTime())
                .orderByDesc(SysFile::getCreateTime));
        List<SysFile> records = page.getRecords();
        if (CollUtil.isNotEmpty(records)){
            //TODO
        }
        return page;
    }


    public void saveOrUp(SysFile bean) {
        if (StringUtils.isBlank(bean.getId())) {
            bean.setId(G.getUUID());
            bean.setIsDel(0);
            sysFileMapper.insert(bean);
        }else {
            sysFileMapper.updateById(bean);
        }
    }

    public SysFile getById(String id) {
        return sysFileMapper.selectById(id);
    }

    public void delById(String id) {
            sysFileMapper.update(null, Wrappers.<SysFile>lambdaUpdate()
                .eq(SysFile::getId, id)
                .eq(SysFile::getIsDel, 0)
                .set(SysFile::getIsDel, 1)
        );

    }


    public SysFile uploadFile(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        if (StringUtils.isBlank(originalFilename)) {
            throw new RuntimeException("上传文件失败，文件名不能为空");
        }
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String filename = IdUtil.fastSimpleUUID() + suffix;
        try {
            minioTemplate.putObject(G.MINIO_BUCKET, filename, file.getInputStream());
            SysFile entity = new SysFile();
            entity.setId(IdUtil.fastSimpleUUID());
            entity.setOriginalName(originalFilename);
            entity.setFileType(file.getContentType());
            entity.setFileSize(String.valueOf(file.getSize()));
            entity.setFileName(filename);
            entity.setBucket(G.MINIO_BUCKET);
            entity.setIsDel(0);
            entity.insert();
            return entity;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("上传文件失败");
        }
    }

}