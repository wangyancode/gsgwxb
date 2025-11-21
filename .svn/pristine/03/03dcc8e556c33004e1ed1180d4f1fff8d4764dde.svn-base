//package com.jsdc.gsgwxb.config.init;
//
//import cn.hutool.core.collection.CollUtil;
//import com.baomidou.mybatisplus.core.toolkit.Wrappers;
//import com.jsdc.gsgwxb.config.GlobalVariableUtils;
//import com.jsdc.gsgwxb.enums.G;
//import com.jsdc.gsgwxb.mapper.sys.SysDictDataMapper;
//import com.jsdc.gsgwxb.mapper.sys.SysDictTypeMapper;
//import com.jsdc.gsgwxb.model.sys.SysDictData;
//import com.jsdc.gsgwxb.model.sys.SysDictType;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import java.util.List;
//import java.util.Map;
//import java.util.function.Function;
//import java.util.stream.Collectors;
//
//@Slf4j
//@Component
//public class InitDictCache {
//
//
//    private static SysDictTypeMapper dictTypeMapper; // 静态变量
//    private static SysDictDataMapper dictDataMapper; // 静态变量
//
//    @Autowired
//    public void setSysDictTypeMapper(SysDictTypeMapper mapper) {
//        InitDictCache.dictTypeMapper = mapper; // 通过Setter注入
//    }
//
//    @Autowired
//    public void setSysDictDataMapper(SysDictDataMapper mapper) {
//        InitDictCache.dictDataMapper = mapper; // 通过Setter注入
//    }
//
//    @PostConstruct
//    public void initDictCache() {
//        InitDictCache.refreshAll();
//        InitDictCache.log.info(" 字典缓存预热完成，加载数量：{}, {}", GlobalVariableUtils.DICT_LIST_CACHE.size(), GlobalVariableUtils.DICT_VALUE_CACHE.size());
//
//    }
//
//    // 全量刷新（含双缓存同步）⌬
//    // 如果type下面没有data,则不加入字典缓存
//    public static void refreshAll() {
//        List<SysDictType> types = InitDictCache.dictTypeMapper.selectList(Wrappers.<SysDictType>lambdaQuery().eq(SysDictType::getIsDel, G.ISDEL_NO));
//        if (CollUtil.isEmpty(types)) {
//            return;
//        }
//        GlobalVariableUtils.DICT_LIST_CACHE.clear();
//        GlobalVariableUtils.DICT_VALUE_CACHE.clear();
//        for (SysDictType dictType : types) {
//            List<SysDictData> dictDatas = InitDictCache.dictDataMapper.selectList(Wrappers.<SysDictData>lambdaQuery()
//                    .eq(SysDictData::getDictTypeId, dictType.getId())
//                    .eq(SysDictData::getIsDel, 0)
//                    .orderByDesc(SysDictData::getSort)
//            );
//            if (CollUtil.isEmpty(dictDatas)) {
//                continue;
//            }
//            GlobalVariableUtils.DICT_LIST_CACHE.put(dictType.getDictType(), dictDatas);
//            Map<String, SysDictData> valueMap = dictDatas.stream()
//                    .collect(Collectors.toMap(
//                            SysDictData::getDictValue,
//                            Function.identity(),
//                            (v1, v2) -> v2 // 或者根据业务需求选择保留哪一个值
//                    ));
//            GlobalVariableUtils.DICT_VALUE_CACHE.put(dictType.getDictType(), valueMap);
//        }
//    }
//
//    /**
//     * 增量刷新单个（适合单条数据更新）
//     */
//    public static void refresh(String sysDictDataId) {
//        SysDictData dictData = InitDictCache.dictDataMapper.selectById(sysDictDataId);
//        if (dictData != null) {
//            SysDictType dictType = InitDictCache.dictTypeMapper.selectById(dictData.getDictTypeId());
//            if (null == dictType) {
//                return;
//            }
//            List<SysDictData> dictDatas = InitDictCache.dictDataMapper.selectList(Wrappers.<SysDictData>lambdaQuery()
//                    .eq(SysDictData::getDictTypeId, dictType.getId())
//                    .eq(SysDictData::getIsDel, 0)
//                    .orderByDesc(SysDictData::getSort)
//            );
//            if (CollUtil.isEmpty(dictDatas)) {
//                return;
//            }
//            GlobalVariableUtils.DICT_LIST_CACHE.put(dictType.getDictType(), dictDatas);
//            Map<String, SysDictData> valueMap = dictDatas.stream()
//                    .collect(Collectors.toMap(
//                            SysDictData::getDictValue,
//                            Function.identity(),
//                            (v1, v2) -> v2 // 或者根据业务需求选择保留哪一个值
//                    ));
//            GlobalVariableUtils.DICT_VALUE_CACHE.put(dictType.getDictType(), valueMap);
//        }
//    }
//
//
//}
