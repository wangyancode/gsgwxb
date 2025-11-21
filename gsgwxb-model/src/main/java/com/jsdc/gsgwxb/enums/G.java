package com.jsdc.gsgwxb.enums;

import cn.hutool.core.util.IdUtil;
import lombok.experimental.UtilityClass;

/**
 * @author: jxl
 * @description: 静态类
 */
@UtilityClass
public class G {
    /**
     * 逻辑删除 是
     */
    public Integer ISDEL_YES = 1;

    /**
     * 逻辑删除 否
     */
    public Integer ISDEL_NO = 0;
    /**
     * MINIO 文件存储桶 通用
     */
    public String MINIO_BUCKET = "szywpt";
    /**
     * minio 的存储空间地址
     *
     * 命名规则:
     * 必须全小写（Lowercase letters only）
     * 仅允许小写字母、数字、连字符(-)、点号(.)
     * 必须以字母或数字开头和结束
     * 3-63个字符
     */
//    public String MINIO_BUCKET_NAME = "num-yun-wei";

    /**
     * 生成uuid主键
     *
     * @return
     */
    public String getUUID() {
        return IdUtil.fastSimpleUUID();
    }
}
