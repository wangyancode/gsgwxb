package com.jsdc.gsgwxb.controller.sys;

import com.jsdc.gsgwxb.model.sys.SysUser;
import com.jsdc.gsgwxb.service.sys.SysUserService;
import com.jsdc.gsgwxb.utils.MD5Utils;
import com.jsdc.gsgwxb.utils.PassUtils;
import com.jsdc.gsgwxb.utils.ResultInfo;
import com.jsdc.gsgwxb.vo.SysUserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api(tags = "用户管理")
@RestController
@RequestMapping("/app/sysUser")
public class SysUserController {

    @Autowired
    private SysUserService userService;

    @ApiOperation(value = "用户列表")
    @PostMapping(value = "selectUserList")
    public ResultInfo selectUserList(@RequestBody SysUserVo bean) {
        return ResultInfo.success(userService.selectUserList(bean));
    }

    @ApiOperation(value = "根据用户id查询")
    @RequestMapping(value = "selectUserById")
    public ResultInfo selectUserById(String id) {
        return ResultInfo.success(userService.selectUserById(id));
    }


    @ApiOperation(value = "个人中心", notes = "传递参数：bean(根据页面数值填充)")
    @RequestMapping(value = "personalMethod", method = RequestMethod.POST)
    public ResultInfo personalMethod(@RequestBody SysUserVo bean) {
        Integer judgeCount = userService.isExistJudgeCount(bean);
        if (judgeCount > 0) {
            return ResultInfo.error("用户名已存在");
        }
        boolean b = userService.personalMethod(bean);
        if (b) {
            return ResultInfo.success("操作成功");
        }
        return ResultInfo.error("操作失败");
    }

    @ApiOperation(value = "修改密码", notes = "传递参数：id(必填),旧密码：pass_word(必填),新密码：new_pass(必填)")
    @RequestMapping(value = "updatePass", method = RequestMethod.POST)
    public ResultInfo updatePass(@RequestBody SysUserVo bean) {
        if (null != bean.getId() && null != bean.getPassword() && null != bean.getNewPass()) {
            SysUser user = userService.getById(bean.getId());
            if (!user.getPassword().equals(MD5Utils.getMD5(bean.getPassword()))) {
                return ResultInfo.error("旧密码错误");
            } else if (bean.getPassword().equals(bean.getNewPass())) {
                return ResultInfo.error("新密码不能与旧密码一致");
            } else {
                if (bean.getNewPass() == null) {
                    return ResultInfo.error("新密码不能为空");
                }
                boolean b = PassUtils.validateStrongPassword(bean.getNewPass());
                if (!b) {
                    return ResultInfo.error("密码规则说明：小写字母+大写字母+数字+特殊符号+长度为8-16位！");
                }
                user.setPassword(MD5Utils.getMD5(bean.getNewPass()));
                boolean f = userService.updateById(user);
                if (f) {
                    return ResultInfo.success("操作成功");
                }
            }
        }
        return ResultInfo.error("参数不能为空");
    }


    @ApiOperation(value = "启用|禁用", notes = "传递参数：id(必填)，states(必填)")
    @RequestMapping(value = "updStates", method = RequestMethod.POST)
    public ResultInfo updStates(@RequestBody SysUser bean) {
        SysUser user = userService.getById(bean.getId());
        user.setStates(bean.getStates());
        boolean b = userService.updateById(user);
        if (b) {
            return ResultInfo.success("操作成功");
        }
        return ResultInfo.error("操作失败");
    }


    @ApiOperation(value = "重置密码", notes = "传递参数：id(必填)")
    @RequestMapping(value = "resetPass", method = RequestMethod.POST)
    public ResultInfo resetPass(@RequestBody SysUser bean) {
        boolean b = userService.resetPass(bean.getId());
        if (b) {
            return ResultInfo.success("操作成功");
        }
        return ResultInfo.error("操作失败");
    }

}
