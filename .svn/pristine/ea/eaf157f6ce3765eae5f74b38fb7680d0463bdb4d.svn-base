package com.jsdc.gsgwxb.mapper.news;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.gsgwxb.dao.news.NewsInfoDao;
import com.jsdc.gsgwxb.news.NewsInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface NewsInfoMapper extends BaseMapper<NewsInfo> {
    @SelectProvider(type = NewsInfoDao.class, method = "queryPage")
    Page<NewsInfo> queryPage(Page page, NewsInfo bean);

    @SelectProvider(type = NewsInfoDao.class, method = "queryList")
    List<NewsInfo> queryList(NewsInfo bean);

    @SelectProvider(type = NewsInfoDao.class, method = "queryBean")
    NewsInfo queryBean(NewsInfo bean);
}
