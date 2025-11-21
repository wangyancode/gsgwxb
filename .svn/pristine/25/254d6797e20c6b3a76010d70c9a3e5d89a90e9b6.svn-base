package com.jsdc.gsgwxb.service.sys;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.gsgwxb.enums.G;
import com.jsdc.gsgwxb.mapper.sys.SysRoleMapper;
import com.jsdc.gsgwxb.model.sys.SysRole;
import com.jsdc.gsgwxb.model.sys.SysRoleMenu;
import com.jsdc.gsgwxb.model.sys.SysUserRole;
import com.jsdc.gsgwxb.service.BaseService;
import com.jsdc.gsgwxb.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class SysRoleService extends BaseService<SysRole> {

    @Autowired
    private SysRoleMenuService roleMenuService;
    @Autowired
    private SysUserRoleService userRoleService;
    @Autowired
    private SysRoleMapper roleMapper;

    /**
     * 分页查询
     *
     * @param bean
     * @return
     */
    public Page<SysRole> selectListPage(SysRole bean) {
        Page page = new Page(bean.getPageIndex(), bean.getPageSize());
        Page<SysRole> pageList = roleMapper.selectPageList(page, bean);
        return pageList;
    }


    /**
     * 根据id获取数据
     *
     * @param id
     * @return
     */
    public SysRole selectRoleById(String id) {
        SysRole sysRole = getOne(new LambdaQueryWrapper<SysRole>().eq(SysRole::getIsDel, 0).eq(SysRole::getId, id));
        //根据角色id查询角色菜单关联表数据列表
        List<SysRoleMenu> roleMenus = roleMenuService.list(new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getIsDel, 0).eq(SysRoleMenu::getRoleId, id));
        List<String> menuIds = roleMenus.stream().map(SysRoleMenu::getMenuId).collect(Collectors.toList());
        if (null != menuIds && !menuIds.isEmpty() && menuIds.size() > 0) {
            sysRole.setMenuIds(menuIds);
        }
        return sysRole;
    }


    /**
     * 新增修改功能
     * 根据勾选的菜单，保存到角色菜单表中
     *
     * @param bean
     */
    public void saveOrUpd(SysRole bean) {
        if (null != bean.getId()) {
            updateById(bean);
        } else {
            bean.setCreateTime(new Date());
            bean.setIsDel(0);
            bean.setId(G.getUUID());
            save(bean);
        }

        //根据user_id删除掉关联菜单的数据
        roleMenuService.update(Wrappers.<SysRoleMenu>lambdaUpdate().set(SysRoleMenu::getIsDel, 1).
                eq(SysRoleMenu::getRoleId, bean.getId()));
        for (String temp : bean.getMenuIds()) {
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setId(G.getUUID());
            sysRoleMenu.setRoleId(bean.getId());
            sysRoleMenu.setMenuId(temp);
            sysRoleMenu.setIsDel(0);
            roleMenuService.save(sysRoleMenu);
        }

    }

    /**
     * 导出
     */
    public void export(SysRole bean, HttpServletResponse response) {
        // 查询数据
        List<SysRole> records = roleMapper.selectList(Wrappers.<SysRole>lambdaQuery()
                .eq(SysRole::getIsDel, 0)
                .like(StrUtil.isNotEmpty(bean.getRoleName()), SysRole::getRoleName, bean.getRoleName())
                .like(StrUtil.isNotEmpty(bean.getRoleCode()), SysRole::getRoleCode, bean.getRoleCode())
                .eq(null != bean.getIsFlag(), SysRole::getIsFlag, bean.getIsFlag())
                .between(StrUtil.isNotEmpty(bean.getStartTime()), SysRole::getCreateTime, bean.getStartTime(), bean.getEndTime())
                .orderByDesc(SysRole::getCreateTime));
        if (CollUtil.isNotEmpty(records)) {
            for (SysRole record : records) {
                record.setCreateTimeStr(DateUtils.dateToStr(record.getCreateTime()));
                if (record.getIsFlag() == 1) {
                    record.setStatus("正常");
                } else {
                    record.setStatus("禁用");
                }
            }
        }
        ExcelWriter writer = ExcelUtil.getWriter();
        writer.addHeaderAlias("roleName", "用户名称");
        writer.addHeaderAlias("roleCode", "登录地址");
        writer.addHeaderAlias("sort", "排序");
        writer.addHeaderAlias("status", "状态");
        writer.addHeaderAlias("createTimeStr", "创建时间");
        writer.setOnlyAlias(true);
        writer.setColumnWidth(4, 20); // 设置第5列("创建时间")的宽度
        writer.write(records, true);
        OutputStream outputStream = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/vnd.ms-excel");
        try {
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("角色.xls", "UTF-8"));
            outputStream = response.getOutputStream();
            writer.flush(outputStream, true);
//            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //删除功能
    public Integer deleteByRoleId(String id) {
        int count = userRoleService.count(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getIsDel, 0).eq(SysUserRole::getRoleId, id));
        if (count > 0) {
            return -1;
        }
        SysRole bean = getById(id);
        bean.setIsDel(1);
        updateById(bean);
        return 0;
    }

    //判断名称是否存在
    public Integer isExistJudgeCount(SysRole bean, Integer type) {
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<SysRole>().eq(SysRole::getIsDel, 0);
        if (1 == type) {
            queryWrapper.eq(SysRole::getRoleName, bean.getRoleName());
        } else {
            queryWrapper.eq(SysRole::getRoleCode, bean.getRoleCode());
        }
        if (null != bean.getId()) {
            queryWrapper.ne(SysRole::getId, bean.getId());
        }
        return count(queryWrapper);
    }
}
