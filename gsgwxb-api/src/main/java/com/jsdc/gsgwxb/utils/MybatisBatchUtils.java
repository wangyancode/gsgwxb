package com.jsdc.gsgwxb.utils;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

@Component
public class MybatisBatchUtils {

    private static final Logger logger = LoggerFactory.getLogger(MybatisBatchUtils.class);
    private static final int BATCH_SIZE = 1000;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    /**
     * 批量处理修改或者插入
     *
     * @param data        需要被处理的数据
     * @param mapperClass Mybatis的Mapper类
     * @param function    自定义处理逻辑
     * @return boolean 处理是否成功
     */
    public <T, U, R> boolean batchUpdateOrInsert(List<T> data, Class<U> mapperClass, BiFunction<T, U, R> function) {
        SqlSession batchSqlSession = openBatchSqlSession();
        try {
            U mapper = getMapper(batchSqlSession, mapperClass);
            processData(data, mapper, function);
            commitSession(batchSqlSession);
            return true;
        } catch (Exception e) {
            handleException(batchSqlSession, e);
            return false;
        } finally {
            closeSession(batchSqlSession);
        }
    }

    /**
     * 批量修改方法
     *
     * @param data        需要被修改的数据
     * @param mapperClass Mybatis的Mapper类
     * @param function    自定义修改逻辑
     * @return boolean 修改是否成功
     */
    public <T, U, R> boolean batchUpdate(List<T> data, Class<U> mapperClass, BiFunction<T, U, R> function) {
        return batchUpdateOrInsert(data, mapperClass, function);
    }

    private SqlSession openBatchSqlSession() {
        return sqlSessionFactory.openSession(ExecutorType.BATCH);
    }

    private <U> U getMapper(SqlSession sqlSession, Class<U> mapperClass) {
        return sqlSession.getMapper(mapperClass);
    }

    private <T, U, R> void processData(List<T> data, U mapper, BiFunction<T, U, R> function) {
        int i = 1;
        int size = data.size();
        for (T element : data) {
            function.apply(element, mapper);
            if ((i % MybatisBatchUtils.BATCH_SIZE == 0) || i == size) {
                flushStatements();
            }
            i++;
        }
    }

    private void flushStatements() {
        // 这里可以添加日志记录等操作
    }

    private void commitSession(SqlSession sqlSession) {
        sqlSession.commit(!TransactionSynchronizationManager.isSynchronizationActive());
    }

    private void handleException(SqlSession sqlSession, Exception e) {
        MybatisBatchUtils.logger.error(" 批量处理数据时发生异常", e);
        sqlSession.rollback();
    }

    private void closeSession(SqlSession sqlSession) {
        sqlSession.close();
    }

    /**
     * 获取完整 SQL（带实际参数值）
     *
     * @param queryWrapper QueryWrapper 实例
     * @return 替换占位符后的 SQL 条件片段
     */
    public static String getFullSqlWithParams(QueryWrapper<?> queryWrapper) {
        try {
            // 1. 获取原始 SQL 片段（带 ? 占位符）
            String sqlSegment = queryWrapper.getSqlSegment();

            // 2. 反射获取参数集合（适配 3.0.7 内部字段名）
            Field paramNameValuePairsField = QueryWrapper.class.getDeclaredField("paramNameValuePairs");
            paramNameValuePairsField.setAccessible(true);
            Map<String, Object> paramMap = (Map<String, Object>) paramNameValuePairsField.get(queryWrapper);

            // 3. 提取有序参数值（按 SQL 中 ? 出现顺序）
            List<Object> orderedParams = paramMap.values().stream()
                    .map(obj -> (obj instanceof Map) ? ((Map<?, ?>) obj).get("value") : obj)
                    .collect(Collectors.toList());

            // 4. 替换占位符为实际值（处理字符串、数值等类型）
            int index = 0;
            StringBuilder finalSql = new StringBuilder();
            for (String segmentPart : sqlSegment.split("\\?")) {
                finalSql.append(segmentPart);
                if (index < orderedParams.size()) {
                    Object param = orderedParams.get(index);
                    finalSql.append(MybatisBatchUtils.formatParamValue(param));
                    index++;
                }
            }
            return finalSql.toString();
        } catch (Exception e) {
            return "[ERROR] Failed to generate SQL: " + e.getMessage();
        }
    }

    /**
     * 格式化参数值（字符串加引号，数值保留原格式）
     */
    private static String formatParamValue(Object param) {
        if (param == null) return "null";
        if (param instanceof String || param instanceof Character) {
            return "'" + param.toString() + "'";
        }
        if (param instanceof Number || param instanceof Boolean) {
            return param.toString();
        }
        return "'" + param.toString() + "'"; // 其他类型统一转字符串
    }
}
