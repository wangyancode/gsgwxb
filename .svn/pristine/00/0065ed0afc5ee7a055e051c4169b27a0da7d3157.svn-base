package com.jsdc.gsgwxb.controller.sys;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jsdc.gsgwxb.model.sys.SysRole;
import com.jsdc.gsgwxb.service.sys.SysRoleService;
import com.jsdc.gsgwxb.utils.ResultInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;


@Api(tags = "角色管理")
@RestController
@RequestMapping("sysRole")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @ApiOperation(value = "分页查询", notes = "传递参数：页码：pageIndex(必填)，页大小：pageSize(必填)，查询条件：bean(根据检索条件传参)")
    @RequestMapping(value = "selectListPage")
    @ResponseBody
    public ResultInfo selectListPage(@RequestBody SysRole bean) {
        try {
            return ResultInfo.success(sysRoleService.selectListPage(bean));
        } catch (Exception e) {
            e.printStackTrace();
            return ResultInfo.error("操作失败");
        }
    }


    @ApiOperation(value = "列表查询")
    @RequestMapping(value = "selectRoleList")
    public ResultInfo selectRoleList() {
        try {
            return ResultInfo.success(sysRoleService.list(new LambdaQueryWrapper<SysRole>().eq(SysRole::getIsDel, 0).eq(SysRole::getIsFlag, 1)));
        } catch (Exception e) {
            e.printStackTrace();
            return ResultInfo.error("操作失败");
        }
    }


    @ApiOperation(value = "根据id查询", notes = "传递参数：id(必填)")
    @RequestMapping(value = "selectRoleById")
    public ResultInfo selectRoleById(String id) {
        try {
            return ResultInfo.success(sysRoleService.selectRoleById(id));
        } catch (Exception e) {
            e.printStackTrace();
            return ResultInfo.error("操作失败");
        }
    }


    @ApiOperation(value = "保存|修改", notes = "传递参数：新增：bean(根据页面数值填充),修改：id(必填)、bean(根据页面数值填充)")
    @RequestMapping(value = "saveOrUpd", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo saveOrUpd(@RequestBody SysRole bean) {
        Integer judgeCount = sysRoleService.isExistJudgeCount(bean, 1);
        if (judgeCount > 0) {
            return ResultInfo.error("角色名称已存在");
        }
        Integer roleCount = sysRoleService.isExistJudgeCount(bean, 2);
        if (roleCount > 0) {
            return ResultInfo.error("角色标识已存在");
        }
        sysRoleService.saveOrUpd(bean);
        return ResultInfo.success("操作成功");
    }

    @ApiOperation(value = "删除功能", notes = "传递参数：id(必填)")
    @RequestMapping(value = "deleteByRoleId", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo deleteByRoleId(@RequestBody SysRole bean) {
        Integer count = sysRoleService.deleteByRoleId(bean.getId());
        if (count == -1) {
            return ResultInfo.error("该角色已被绑定用户,请先解绑");
        }
        return ResultInfo.success("操作成功");
    }

    @ApiOperation("导出")
    @PostMapping("export")
    public void export(@RequestBody SysRole bean, HttpServletResponse response) {
        try {
            sysRoleService.export(bean, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
