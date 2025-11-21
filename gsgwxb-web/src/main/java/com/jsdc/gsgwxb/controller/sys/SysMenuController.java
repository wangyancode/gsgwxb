package com.jsdc.gsgwxb.controller.sys;

import com.jsdc.gsgwxb.mapper.sys.SysRoleMenuMapper;
import com.jsdc.gsgwxb.model.sys.SysMenu;
import com.jsdc.gsgwxb.service.sys.SysMenuService;
import com.jsdc.gsgwxb.utils.ResultInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/sysMenu")
@Api(tags = "菜单管理")
public class SysMenuController {
    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private SysRoleMenuMapper menuMapper;


    @ApiOperation(value = "分页查询", notes = "参数传递：pageIndex(页码)、pageSize(页大小)、bean(根据页面数值填充)")
    @RequestMapping(value = "selectAreaPage")
    @ResponseBody
    public ResultInfo selectAreaPage(@RequestBody SysMenu bean) {
        try {
            return ResultInfo.success(sysMenuService.selectMenuPage(bean));
        } catch (Exception e) {
            e.printStackTrace();
            return ResultInfo.error("操作失败");
        }
    }


    @ApiOperation(value = "保存|修改", notes = "参数传递：新增：bean(根据页面数值填充),修改：id(必填)、bean(根据页面数值填充)")
    @RequestMapping(value = "saveOrUpd", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo saveOrUpd(@RequestBody SysMenu bean) {
        try {
            if (null == bean.getMenuName()) {
                return ResultInfo.error("参数值不能为空");
            }
            Integer judgeCount = sysMenuService.isExistJudgeCount(bean);
            if (judgeCount > 0) {
                return ResultInfo.error("菜单名称已存在");
            }
            return sysMenuService.saveOrUpd(bean);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultInfo.error("操作失败");
        }
    }


    @ApiOperation(value = "删除功能", notes = "参数传递：id(必填)")
    @RequestMapping(value = "deleteMenuById", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo deleteMenuById(@RequestBody SysMenu bean) {
        if (null == bean.getId()) {
            return ResultInfo.error("参数值不能为空");
        }
        Integer count = menuMapper.countRoleMenu(bean.getId());
        if (count > 0) {
            return ResultInfo.error("角色绑定菜单,请先解绑");
        }
        boolean b = sysMenuService.deleteMenuById(bean.getId());
        if (b) {
            return ResultInfo.success("操作成功");
        } else {
            return ResultInfo.error("存在子菜单,不可删除");
        }
    }


    @ApiOperation(value = "树形菜单列表", notes = "传递参数：查询条件：bean(条件不是必填项)")
    @RequestMapping(value = "selectMenuTrees")
    @ResponseBody
    public ResultInfo selectMenuTreeList(@RequestBody SysMenu bean) {
        try {
            return ResultInfo.success(sysMenuService.selectMenuTreeList(bean));
        } catch (Exception e) {
            e.printStackTrace();
            return ResultInfo.error("操作失败");
        }
    }

    @ApiOperation(value = "启用|禁用", notes = "传递参数：id(必填)，is_open(必填)")
    @RequestMapping(value = "updStates", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo updStates(@RequestBody SysMenu bean) {
        SysMenu sysMenu = sysMenuService.getById(bean.getId());
        sysMenu.setIsFlag(bean.getIsFlag());
        boolean b = sysMenuService.updateById(sysMenu);
        if (b) {
            return ResultInfo.success("操作成功");
        }
        return ResultInfo.error("操作失败");
    }

}
