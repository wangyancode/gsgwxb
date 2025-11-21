package com.jsdc.gsgwxb.controller.news;

import com.jsdc.gsgwxb.news.NewsInfo;
import com.jsdc.gsgwxb.service.news.NewsInfoService;
import com.jsdc.gsgwxb.utils.ResultInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 新闻中心
 */
@RestController
@RequestMapping("/news")
public class NewsInfoController {
    @Autowired
    private NewsInfoService mainService;

    /**
     * 分页
     */
    @RequestMapping("/selectPage")
    public ResultInfo selectPage(NewsInfo bean) {
        return ResultInfo.success(mainService.selectPage(bean));
    }

    /**
     * 列表
     *
     * @return
     */
    @RequestMapping("/selectList")
    public ResultInfo getList(NewsInfo bean) {
        return ResultInfo.success(mainService.selectList(bean));
    }

    /**
     * 详情
     *
     * @return
     */
    @RequestMapping("/selectById")
    public ResultInfo getList(String id) {
        NewsInfo bean = new NewsInfo();
        bean.setId(id == null ? "0" : id);
        return ResultInfo.success(mainService.selectBean(bean));
    }


    /**
     * 新增修改
     */
    @RequestMapping("/addUpdateInfo")
    public ResultInfo addUpdateInfo(@RequestBody NewsInfo bean) {
        return ResultInfo.success(mainService.addUpdateInfo(bean));
    }

    /**
     * 删除
     */
    @RequestMapping("/delData")
    public ResultInfo delData(String id) {
        if(StringUtils.isEmpty(id)){
            return ResultInfo.error("id不能未空，请选择后删除！");
        }
        return ResultInfo.success(mainService.delData(id));
    }


    /**
     * 批量删除
     */
    @RequestMapping("/batchDeletion")
    public ResultInfo batchDeletion(String ids) {
        if(StringUtils.isEmpty(ids)){
            return ResultInfo.error("id不能未空，请选择后删除！");
        }
        return mainService.batchDeletion(ids);
    }

}
