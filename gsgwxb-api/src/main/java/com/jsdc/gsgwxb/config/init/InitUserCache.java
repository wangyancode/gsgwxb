//package com.jsdc.gsgwxb.config.init;
//
//import com.baomidou.mybatisplus.core.toolkit.Wrappers;
//import com.jsdc.gsgwxb.config.GlobalVariableUtils;
//import com.jsdc.gsgwxb.mapper.sys.SysUserMapper;
//import com.jsdc.gsgwxb.model.sys.SysUser;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import java.util.List;
//
//@Slf4j
//@Component
//public class InitUserCache {
//
//    private static SysUserMapper sysUserMapper; // 静态变量
//    @Autowired
//    public void setSysUserMapper(SysUserMapper mapper) {
//        sysUserMapper = mapper; // 通过Setter注入
//    }
//
//    // Spring容器初始化完成后自动执行
//    @PostConstruct
//    public void initCache() {
//        List<SysUser> users = sysUserMapper.selectList(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getIsDel, 0));
//        users.forEach(user  -> GlobalVariableUtils.USER_CACHE.put(user.getId(),  user));
//        log.info(" 用户缓存预热完成，加载数量：{}", GlobalVariableUtils.USER_CACHE.size());
//    }
//
//    /** 全量刷新缓存（适合批量操作后调用） */
//    public static void refreshAllUsers() {
//        List<SysUser> users = sysUserMapper.selectList(
//            Wrappers.<SysUser>lambdaQuery().eq(SysUser::getIsDel, 0)
//        );
//        GlobalVariableUtils.USER_CACHE.clear();
//        users.forEach(user  -> GlobalVariableUtils.USER_CACHE.put(user.getId(),  user));
//        log.info(" 用户缓存全量刷新完成，数量：{}", GlobalVariableUtils.USER_CACHE.size());
//    }
//
//    /** 增量刷新单个用户（适合单条数据更新） */
//    public static void refreshUser(String userId) {
//        SysUser user = sysUserMapper.selectById(userId);
//        if (user != null) {
//            GlobalVariableUtils.USER_CACHE.put(user.getId(),  user);
//            log.debug(" 用户[{}]缓存已刷新", userId);
//        }
//    }
//    /** 带状态验证的缓存移除（更严谨版） */
//    public static void removeUserFromCache(String userId) {
//        SysUser user = sysUserMapper.selectById(userId);
//        if (user != null && user.getIsDel()  == 1) {
//            GlobalVariableUtils.USER_CACHE.remove(userId);
//            log.info("  用户[{}]完成软删除缓存同步", userId);
//        }
//    }
//}
