//package com.jsdc.gsgwxb.config;
//
//
//import com.jsdc.common.minio.service.MinioTemplate;
//import com.jsdc.gsgwxb.config.init.InitDictCache;
//import com.jsdc.gsgwxb.config.init.InitOperationCache;
//import com.jsdc.gsgwxb.config.init.InitUserCache;
//import com.jsdc.gsgwxb.enums.G;
//import com.jsdc.gsgwxb.model.resource.OperationPerson;
//import com.jsdc.gsgwxb.model.sys.SysDictData;
//import com.jsdc.gsgwxb.model.sys.SysUser;
//import com.jsdc.gsgwxb.utils.SpringUtils;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//@Slf4j
//@Component
//public class GlobalVariableUtils {
//
//    /**
//     * 用户信息, 缓存加载
//     */
//    public static final ConcurrentHashMap<String, SysUser> USER_CACHE = new ConcurrentHashMap<>(256);
//
//    /**
//     * 运维用户信息, 缓存加载
//     */
//    public static final ConcurrentHashMap<String, OperationPerson> OPERATOR_CACHE = new ConcurrentHashMap<>(256);
//
//    /**
//     * 字典信息, 缓存加载
//     * Map<String(SysDictType -> type), Map<String(SysdictData -> value, String(SysdictData -> label), SysDictType>>
//     */
//    public static final Map<String, Map<String, SysDictData>> DICT_VALUE_CACHE = new ConcurrentHashMap<>(128);
//    /**
//     * 字典信息, 缓存加载
//     * Map<String(SysDictType -> type), List<SysDictData> >
//     */
//    public static final Map<String, List<SysDictData>> DICT_LIST_CACHE = new ConcurrentHashMap<>(128);
//
//    // 每小时全量刷新一次
//    @Scheduled(fixedRate = 3600000)
//    public void autoRefresh() {
//        SpringUtils.getBean(InitUserCache.class).refreshAllUsers();
//        SpringUtils.getBean(InitDictCache.class).refreshAll();
//        SpringUtils.getBean(InitOperationCache.class).refreshAll();
//    }
//
//    /**
//     * 字典: 根据type, value获取SysdictData
//     */
//    public static SysDictData getDictData(String type, String value) {
//        Map<String, SysDictData> dictDataMap = GlobalVariableUtils.DICT_VALUE_CACHE.get(type);
//        if (null == dictDataMap) {
//            return null;
//        }
//        return dictDataMap.get(value);
//    }
//
//    /**
//     * 字典: 根据type,label获取SysdictData
//     */
//    public static SysDictData getDataByLabel(String type, String label) {
//        Map<String, SysDictData> dictDataMap = GlobalVariableUtils.DICT_VALUE_CACHE.get(type);
//        if (dictDataMap == null || dictDataMap.isEmpty()) {
//            return null;
//        }
//        for (SysDictData dictData : dictDataMap.values()) {
//            if (label.equals(dictData.getDictLabel())) {
//                return dictData;
//            }
//        }
//        return null;
//    }
//
//
//    /**
//     * 初始化minio的使用空间,如果没有则创建,有则不创建
//     */
//    @PostConstruct
//    public void initMinio() {
//        SpringUtils.getBean(MinioTemplate.class).createBucket(G.MINIO_BUCKET);
//    }
//
//
//}
