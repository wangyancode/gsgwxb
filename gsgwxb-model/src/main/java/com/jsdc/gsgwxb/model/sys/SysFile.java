package com.jsdc.gsgwxb.model.sys;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@TableName(value = "sys_file")
@Table(name = "sys_file")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@org.hibernate.annotations.Table(
        comment = "文件表",
        appliesTo = "sys_file"
)
public class SysFile extends Model<SysFile> implements Serializable {
    @Id
    @TableId(value = "id", type = IdType.INPUT)
    @Comment("id")
    private String id;
    @Comment("业务类型 1.新闻中心")
    private Integer bizType;
    @Comment("业务id")
    private String bizId;
    @Comment("文件原名")
    private String originalName;
    @Comment("文件后缀 如.word .pdf;知识库使用")
    private String fileSuffix;
    @Comment("文件类型")

    private String fileType;
    @Comment("文件大小")
    private String fileSize;
    @Comment("文件名")
    private String fileName;
    @Comment("存储桶")
    private String bucket;
    @Comment("对象名称")
    private String objectName;

    @Comment("创建者")
    @TableField(fill = FieldFill.INSERT)
    private String createUser;
    @Comment("修改者")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateUser;
    @Comment("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @Comment("修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    //是否删除
    @Comment("是否删除, 0、否 1、是")
    private Integer isDel;
}
