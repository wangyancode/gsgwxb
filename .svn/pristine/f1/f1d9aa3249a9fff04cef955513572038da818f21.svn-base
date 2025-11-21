package com.jsdc.gsgwxb.controller.sys;

import com.jsdc.gsgwxb.model.sys.SysFile;
import com.jsdc.gsgwxb.service.sys.SysFileService;
import com.jsdc.gsgwxb.utils.ResultInfo;
import com.jsdc.gsgwxb.vo.sys.SysFileVo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Api(tags = "文件管理")
@RestController
@RequestMapping("sysFile")
public class SysFileController {

    @Autowired
    private SysFileService sysFileService;


    //分页查询
    @PostMapping("selectPageList")
    public ResultInfo page(@RequestBody SysFileVo vo) {
        try {
            return ResultInfo.success(sysFileService.selectPageList(vo));
        } catch (Exception e) {
            e.printStackTrace();
            return ResultInfo.error("操作失败");
        }
    }


    //新增或编辑
    @PostMapping("saveOrUp")
    public ResultInfo saveOrUp(@RequestBody SysFile bean) {
        try {
            sysFileService.saveOrUp(bean);
            return ResultInfo.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultInfo.error("操作失败");
        }
    }


    //根据ID查询
    @GetMapping("detail")
    public ResultInfo getById(String id) {
        try {
            return ResultInfo.success(sysFileService.getById(id));
        } catch (Exception e) {
            e.printStackTrace();
            return ResultInfo.error("操作失败");
        }
    }


    //根据ID删除
    @GetMapping("delById")
    public ResultInfo delById(String id) {
        try {
            sysFileService.delById(id);
            return ResultInfo.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultInfo.error("操作失败");
        }
    }


}