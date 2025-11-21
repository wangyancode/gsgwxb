package com.jsdc.gsgwxb.service.sys;


import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.gsgwxb.enums.G;
import com.jsdc.gsgwxb.exception.UserException;
import com.jsdc.gsgwxb.mapper.sys.SysRoleMapper;
import com.jsdc.gsgwxb.mapper.sys.SysRoleMenuMapper;
import com.jsdc.gsgwxb.mapper.sys.SysUserMapper;
import com.jsdc.gsgwxb.mapper.sys.SysUserRoleMapper;
import com.jsdc.gsgwxb.model.sys.SysRole;
import com.jsdc.gsgwxb.model.sys.SysUser;
import com.jsdc.gsgwxb.model.sys.SysUserRole;
import com.jsdc.gsgwxb.service.BaseService;
import com.jsdc.gsgwxb.utils.*;
import com.jsdc.gsgwxb.vo.SysUserVo;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@Service
@Transactional
public class SysUserService extends BaseService<SysUser> {
    @Autowired
    private SysUserRoleService userRoleService;
    @Autowired
    private SysRoleService roleService;
    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private SysMenuService menuService;


    //分页查询
    public Page<SysUserVo> selectPageList(SysUserVo bean) {
        Page page = new Page(bean.getPageNo(), bean.getPageSize());
        Page<SysUserVo> pageList = userMapper.selectPageList(page, bean);
        List<SysUserVo> records = pageList.getRecords();
        if (CollUtil.isNotEmpty(records)) {
            for (SysUserVo record : records) {
                List<String> roleIds = userRoleService.selectUserByRoleIds(record.getId());
                if (null != roleIds && !roleIds.isEmpty()) {
                    StringBuilder roleNames = new StringBuilder();
                    for (int i = 0; i < roleIds.size(); i++) {
                        SysRole role = roleService.getById(roleIds.get(i));
                        if (i != roleIds.size() - 1) {
                            roleNames.append(role.getRoleName() + ",");
                        } else {
                            roleNames.append(role.getRoleName());
                        }
                    }
                    record.setRoleNames(roleNames.toString());
                    record.setRoleIds(roleIds);
                }
                conventUser(record);
            }
        }
        return pageList;
    }

    /**
     * 保存|修改
     * 勾选的角色，存入用户角色表
     * 勾选的监舍，存入用户监舍表
     *
     * @param bean
     */
    public void saveOrUpd(SysUserVo bean) {
        if (null != bean.getId()) {
            SysUser sysUser = getById(bean.getId());
            bean.setPassword(sysUser.getPassword());
            updateById(bean);
        } else {
            //初始化密码
            bean.setId(G.getUUID());
            bean.setCreateTime(new Date());
            bean.setCreateUser(getUser().getId());
            bean.setPassword(MD5Utils.getMD5(CommonEnum.DEFAULT_PASS_WORD.getCode()));
            bean.setIsDel(0);
            bean.setStates(1);
            bean.setRealName(StrUtil.isEmpty(bean.getRealName()) ? bean.getUserName() : bean.getRealName());
            save(bean);
        }
        //保存用户角色
        userRoleService.saveUserRole(bean.getRoleIds(), bean.getId());
    }

    //登录功能
    public SaTokenInfo login(String userName, String passWord) {
        List<SysUser> users = userMapper.getUserInfo(userName, MD5Utils.getMD5(passWord));
        if (CollUtil.isEmpty(users)) {
            return null;
        }
        SysUser user = users.get(0);
        StpUtil.login(user.getId());
        SaTokenInfo token = StpUtil.getTokenInfo();
        StpUtil.getSession().set(user.getId(), user);
        return token;
    }

    //获取当前用户信息
    public SysUser getUser(String userId) {
        SysUser user = null;
        try {
            user = getBaseMapper().selectById(userId);
            conventUser(user);
        } catch (Exception e) {
            SysUserService.log.error("获取当前用户信息失败", e);
        }
        return user;
    }

    /**
     * 微信获取openid
     *
     * @param code
     * @return
     */
    @SneakyThrows
    public ResultInfo wxLoginMethond(String code) {
        String url = "https://api.weixin.qq.com/sns/jscode2session";
        url += "?appid=wxf971f086e0e5f390";//自己的appid
        url += "&secret=f16cbe59f3236223846367b252160b71";//自己的appSecret
        url += "&js_code=" + code;
        url += "&grant_type=authorization_code";
        url += "&connect_redirect=1";
        String res = null;
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // DefaultHttpClient();
        HttpGet httpget = new HttpGet(url);    //GET方式
        CloseableHttpResponse response = null;
        // 配置信息
        RequestConfig requestConfig = RequestConfig.custom()          // 设置连接超时时间(单位毫秒)
                .setConnectTimeout(5000)                    // 设置请求超时时间(单位毫秒)
                .setConnectionRequestTimeout(5000)             // socket读写超时时间(单位毫秒)
                .setSocketTimeout(5000)                    // 设置是否允许重定向(默认为true)
                .setRedirectsEnabled(false).build();           // 将上面的配置信息 运用到这个Get请求里
        httpget.setConfig(requestConfig);                         // 由客户端执行(发送)Get请求
        response = httpClient.execute(httpget);                   // 从响应模型中获取响应实体
        HttpEntity responseEntity = response.getEntity();
        System.out.println("响应状态为:" + response.getStatusLine());
        if (responseEntity != null) {
            res = EntityUtils.toString(responseEntity);
            System.out.println("响应内容长度为:" + responseEntity.getContentLength());
            System.out.println("响应内容为:" + res);
        }
        // 释放资源
        if (httpClient != null) {
            httpClient.close();
        }
        if (response != null) {
            response.close();
        }
        JSONObject jo = JSON.parseObject(res);
        String errcode = jo.getString("errcode");
        if (null != errcode) {
            return ResultInfo.error("服务器无法获取授权码,请使用账号重新登录");
        } else {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("openId", jo.getString("openid"));
            return ResultInfo.success(jsonObject);
        }
    }

    //获取当前用户信息
    public SysUser getUser() {
        SysUser user = null;
        try {
            user = Optional.ofNullable(StpUtil.getTokenValue())
                    .map(String::toString)
                    .map(StpUtil::getLoginIdByToken)
                    .map(StrUtil::toString)
                    .map(StpUtil.getSession()::get)
                    .map(SysUser.class::cast)
                    .orElseThrow(() -> new UserException("未获取有效token", 10401));
        } catch (Exception e) {
            throw new UserException("未获取有效token", 10401);
        } finally {
            if (null == user) {
                throw new UserException("未获取有效token", 10401);
            }
        }

        conventUser(user);
        if (ObjUtil.isEmpty(user)) {
            throw new UserException("未获取有效token", 10401);
        }
        return user;
    }

    // 回填用户信息
    public void conventUser(SysUser user) {
        if (null == user) {
            SysUserService.log.error("用户信息为空");
            return;
        }
        // 得到菜单信息
        user.setMenus(SpringUtil.getBean(SysRoleMenuMapper.class).selectUserMenuList(user.getId()));
        // 得用角色信息
        List<SysUserRole> userRoles = SpringUtils.getBean(SysUserRoleMapper.class).selectList(Wrappers.<SysUserRole>lambdaQuery()
                .eq(SysUserRole::getUserId, user.getId())
                .eq(SysUserRole::getIsDel, 0)
        );
        if (!CollectionUtils.isEmpty(userRoles)) {
            user.setRoles(SpringUtils.getBean(SysRoleMapper.class).selectList(Wrappers.<SysRole>lambdaQuery()
                    .in(SysRole::getId, userRoles.stream().map(SysUserRole::getRoleId).collect(Collectors.toList()))
                    .eq(SysRole::getIsDel, 0)
            ));
        }
    }

    //重置密码
    public boolean resetPass(String id) {
        SysUser sysUser = getById(id);
        sysUser.setPassword(MD5Utils.getMD5(CommonEnum.DEFAULT_PASS_WORD.getCode()));
        return updateById(sysUser);
    }

    //退出功能
    public ResultInfo logout() {
        StpUtil.logout(getUser().getId());
        return ResultInfo.success();
    }

    //个人中心
    public boolean personalMethod(SysUserVo bean) {
        SysUser user = getById(bean.getId());
        bean.setPassword(user.getPassword());
        return updateById(bean);
    }


    //根据id查看信息
    public SysUserVo selectUserById(String id) {
        SysUser sysUser = getById(id);
        if (null == sysUser) {
            return null;
        }
        SysUserVo vo = new SysUserVo();
        sysUser.setPassword("");
        BeanUtils.copyProperties(sysUser, vo);
        List<SysRole> roles = new ArrayList<>();
        List<String> roleIds = userRoleService.selectUserByRoleIds(id);
        if (null != roleIds && !roleIds.isEmpty()) {
            String roleNames = "";
            for (String roleId : roleIds) {
                SysRole role = roleService.getById(roleId);
                if (null != role) {
                    roleNames += role.getRoleName() + ",";
                    roles.add(role);
                }
            }
            vo.setRoleNames(roleNames);
            vo.setRoleIds(roleIds);
        }
        vo.setRoles(roles);
        List<Tree<String>> trees = menuService.selectRoleMenuList(id);
        vo.setMenuTrees(trees);
        return vo;
    }

    public Integer isExistJudgeCountAudit(SysUser bean) {
        SysUser user = getById(bean.getId());
        LambdaQueryWrapper<SysUser> wrapper1 = new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getIsDel, 0)
                .eq(SysUser::getUserName, user.getUserName());

        if (count(wrapper1) > 0) {
            return 1;
        }
        LambdaQueryWrapper<SysUser> wrapper2 = new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getIsDel, 0)
                .eq(SysUser::getAccount, user.getAccount());

        if (count(wrapper2) > 0) {
            return 2;
        }
        return -1;
    }

    //判断名称是否存在
    public Integer isExistJudgeCount(SysUser bean) {
        LambdaQueryWrapper<SysUser> wrapper1 = new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getIsDel, 0)
                .eq(SysUser::getUserName, bean.getUserName())
                .ne(null != bean.getId(), SysUser::getId, bean.getId());
        if (count(wrapper1) > 0) {
            return 1;
        }
        LambdaQueryWrapper<SysUser> wrapper2 = new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getIsDel, 0)
                .eq(SysUser::getAccount, bean.getAccount())
                .ne(null != bean.getId(), SysUser::getId, bean.getId());

        if (count(wrapper2) > 0) {
            return 2;
        }
        LambdaQueryWrapper<SysUser> wrapper3 = new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getIsDel, 0)
                .eq(SysUser::getPhone, bean.getPhone())
                .ne(null != bean.getId(), SysUser::getId, bean.getId());

        if (count(wrapper3) > 0) {
            return 3;
        }
        return -1;
    }

    //删除功能
    public boolean deleteByUserId(String id) {
        SysUser bean = getById(id);
        bean.setIsDel(1);
        return updateById(bean);
    }


    //批量删除功能
    public void deleteUserByIds(List<String> ids) {
        if (CollUtil.isNotEmpty(ids)) {
            ids.forEach(a -> {
                SysUser bean = getById(a);
                bean.setIsDel(1);
                updateById(bean);
            });
        }
    }

    public void export(SysUserVo bean, HttpServletResponse response) {
        // 查询数据
        List<SysUserVo> records = userMapper.selectListExport(bean);
        if (CollUtil.isNotEmpty(records)) {
            for (SysUserVo record : records) {
                exportHandle(record);
            }
        }
        ExcelWriter writer = ExcelUtil.getWriter();
        writer.addHeaderAlias("account", "登录账号");
        writer.addHeaderAlias("realName", "用户姓名");
        writer.addHeaderAlias("email", "邮箱");
        writer.addHeaderAlias("phone", "联系方式");
        writer.addHeaderAlias("stateName", "状态");
        writer.addHeaderAlias("createTimeStr", "创建时间");
        writer.setOnlyAlias(true);
        // 设置列宽（将厘米转换为字符宽度）
        writer.write(records, true);
        writer.setColumnWidth(0, 20);
        writer.setColumnWidth(1, 20);
        writer.setColumnWidth(2, 20);
        writer.setColumnWidth(3, 20);
        writer.setColumnWidth(4, 20);
        OutputStream outputStream = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/vnd.ms-excel");
        try {
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("用户信息.xls", "UTF-8"));
            outputStream = response.getOutputStream();
            writer.flush(outputStream, true);
//            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void exportHandle(SysUser record) {
        if (record.getStates() == 1) {
            record.setStateName("正常");
        }
        if (record.getStates() == 0) {
            record.setStateName("禁用");
        }
        record.setCreateTimeStr(DateUtils.dateToStr(record.getCreateTime()));
    }

    public List<SysUser> selectUserList(SysUserVo bean) {
        return userMapper.selectList(Wrappers.<SysUser>lambdaQuery().
                eq(SysUser::getIsDel, 0).
                eq(SysUser::getStates, 1));
    }


}
