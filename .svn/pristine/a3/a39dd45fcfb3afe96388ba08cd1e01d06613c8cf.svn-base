package com.jsdc.gsgwxb.service.sys;


import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jsdc.gsgwxb.enums.G;
import com.jsdc.gsgwxb.model.sys.SysUserRole;
import com.jsdc.gsgwxb.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class SysUserRoleService extends BaseService<SysUserRole> {


    //保存功能
    public void saveUserRole(List<String> roleIds, String user_id) {
        if (CollUtil.isEmpty(roleIds)) {
            return;
        }
        update(Wrappers.<SysUserRole>lambdaUpdate().set(SysUserRole::getIsDel, 1).eq(SysUserRole::getUserId, user_id));

        roleIds.forEach(a -> {
            SysUserRole role = new SysUserRole();
            role.setRoleId(a);
            role.setId(G.getUUID());
            role.setIsDel(0);
            role.setUserId(user_id);
            save(role);
        });
    }


    //根据用户id获取勾选的角色ID
    public List<String> selectUserByRoleIds(String userId) {
        List<String> roles = new ArrayList<>();
        List<SysUserRole> userRoles = list(new LambdaQueryWrapper<SysUserRole>().
                eq(SysUserRole::getIsDel, 0).eq(SysUserRole::getUserId, userId));
        userRoles.forEach(a -> {
            roles.add(a.getRoleId());
        });
        return roles;
    }

}
