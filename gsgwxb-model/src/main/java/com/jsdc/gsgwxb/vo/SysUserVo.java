package com.jsdc.gsgwxb.vo;

import cn.hutool.core.lang.tree.Tree;
import com.jsdc.gsgwxb.model.sys.SysMenu;
import com.jsdc.gsgwxb.model.sys.SysUser;
import lombok.Data;

import java.util.List;

@Data
public class SysUserVo extends SysUser {

    //新密码
    private String newPass;
    private String startTime;
    private String endTime;
    //角色名称
    private String roleNames;
    //角色id
    private String roleId;
    //勾选角色Ids
    private List<String> roleIds;
    //菜单权限
    private List<SysMenu> menus;
    //token数据
    private String token;

    // 告警按钮处置权限
    private Integer disposePermission;

    //用户菜单勾选tree
    private List<Tree<String>> menuTrees;

}
