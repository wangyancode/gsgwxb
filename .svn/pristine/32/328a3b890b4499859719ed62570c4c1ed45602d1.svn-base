package com.jsdc.gsgwxb.service.sys;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.gsgwxb.enums.G;
import com.jsdc.gsgwxb.mapper.sys.SysMenuMapper;
import com.jsdc.gsgwxb.model.sys.SysMenu;
import com.jsdc.gsgwxb.service.BaseService;
import com.jsdc.gsgwxb.utils.ResultInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class SysMenuService extends BaseService<SysMenu> {

    @Autowired
    private SysMenuMapper menuMapper;

    //分页查询
    public Page<SysMenu> selectMenuPage(SysMenu bean) {
        return baseMapper.selectPage(new Page<>(bean.getPageIndex(), bean.getPageSize()), new LambdaQueryWrapper<SysMenu>()
                .eq(null != bean.getIsFlag(), SysMenu::getIsFlag, bean.getIsFlag())
                .eq(null != bean.getStatus(), SysMenu::getStatus, bean.getStatus())
                .eq(SysMenu::getIsDel, 0)
                .like(StrUtil.isNotEmpty(bean.getMenuName()), SysMenu::getMenuName, bean.getMenuName())
                .orderByDesc(SysMenu::getCreateTime)
        );
    }


    //保存||修改功能
    public ResultInfo saveOrUpd(SysMenu bean) {
        if (null != bean.getId()) {
            if (bean.getId().equals(bean.getMenuSuperior())) {
                return ResultInfo.error("上级菜单不能为自己");
            }
            updateById(bean);
            return ResultInfo.success("操作成功");
        } else {
            bean.setId(G.getUUID());
            bean.setIsDel(0);
            save(bean);
            return ResultInfo.success("操作成功");
        }
    }

    //删除功能
    public boolean deleteMenuById(String id) {
        SysMenu bean = getById(id);
        List<SysMenu> sysMenus = baseMapper.selectList(new LambdaQueryWrapper<SysMenu>()
                .eq(SysMenu::getIsDel, 0)
                .eq(SysMenu::getMenuSuperior, bean.getId()));
        if (CollUtil.isNotEmpty(sysMenus)) {
            return false;
        }
        bean.setIsDel(1);
        return updateById(bean);
    }


    //菜单管理树形菜单显示
    public Object selectMenuTreeList(SysMenu bean) {
        List<SysMenu> list = list(new LambdaQueryWrapper<SysMenu>()
                .like(StrUtil.isNotEmpty(bean.getMenuName()), SysMenu::getMenuName, bean.getMenuName())
                .eq(SysMenu::getIsDel, 0)
                .eq(null != bean.getStatus(), SysMenu::getStatus, bean.getStatus())
                .eq(null != bean.getIsFlag(), SysMenu::getIsFlag, bean.getIsFlag())
                .orderByAsc(SysMenu::getSort)
        );

        return StrUtil.isNotEmpty(bean.getMenuName()) || null != bean.getStatus() || null != bean.getIsFlag() ? list : treeMenus(list);
    }

    /**
     * 根据类型得到菜单树
     *
     * @param
     * @return
     */
    public List<Tree<String>> treeMenus(List<SysMenu> list) {
        TreeNodeConfig config = new TreeNodeConfig();
        // 设置节点id和名称的属性名
        config.setNameKey("id");
        config.setNameKey("menuName");
        config.setChildrenKey("children");
        List<Tree<String>> treeNodes =
                TreeUtil.build(list, "-1", config, (treeNode, tree) -> {
                    tree.setId(treeNode.getId());
                    tree.setName(treeNode.getMenuName());
                    tree.setParentId(treeNode.getMenuSuperior());
                    tree.putExtra("menuSuperior", treeNode.getMenuSuperior());
                    tree.putExtra("menuIcon", treeNode.getMenuIcon());
                    tree.putExtra("isFlag", treeNode.getIsFlag());
                    tree.putExtra("menuHref", treeNode.getMenuHref());
                    tree.putExtra("menuCode", treeNode.getMenuCode());
                    tree.putExtra("menuType", treeNode.getMenuType());
                    tree.putExtra("menuVueHref", treeNode.getMenuVueHref());
                    tree.putExtra("menuRedirect", treeNode.getMenuRedirect());
                    tree.putExtra("pathName", treeNode.getPathName());
                    tree.putExtra("sort", treeNode.getSort());
                    tree.putExtra("status", treeNode.getStatus());
                    tree.putExtra("createTime", treeNode.getCreateTime());
                });
        return treeNodes;
    }

    //根据用户权限获取菜单列表
    public List<Tree<String>> selectRoleMenuList(String user_id) {
        List<SysMenu> list = menuMapper.selectRoleMenuList(user_id);
        if (!CollectionUtils.isEmpty(list)) {
            // 排除不是菜单的数据
            List<SysMenu> newMenus = new ArrayList<>();
            for (SysMenu sysMenu : list) {
                if (null != sysMenu.getMenuType() && sysMenu.getMenuType().equals("1")) {
                    newMenus.add(sysMenu);
                }
            }
            return treeMenus(newMenus);
        }
        return treeMenus(list);
    }

    /**
     * 封装条件
     *
     * @param bean
     * @return
     */
    private LambdaQueryWrapper<SysMenu> getWrapper(SysMenu bean) {
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
        if (null != bean) {
            wrapper.eq(null != bean.getId(), SysMenu::getId, bean.getId());
            wrapper.like(StringUtils.isNotEmpty(bean.getMenuName()), SysMenu::getMenuName, bean.getMenuName());
            wrapper.eq(null != bean.getMenuSuperior(), SysMenu::getMenuSuperior, bean.getMenuSuperior());
            wrapper.eq(null != bean.getStatus(), SysMenu::getStatus, bean.getStatus());
        }
        wrapper.eq(SysMenu::getIsDel, 0);
        wrapper.orderByAsc(SysMenu::getSort);
        return wrapper;
    }


    //判断名称是否存在
    public Integer isExistJudgeCount(SysMenu bean) {
        LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<SysMenu>().eq(SysMenu::getIsDel, 0)
                .eq(SysMenu::getMenuName, bean.getMenuName());
        if (null != bean.getId()) {
            queryWrapper.ne(SysMenu::getId, bean.getId());
        }
        return count(queryWrapper);
    }

}
