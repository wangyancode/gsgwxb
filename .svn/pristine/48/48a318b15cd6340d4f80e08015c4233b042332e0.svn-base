package com.jsdc.gsgwxb.news;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jsdc.gsgwxb.model.sys.SysFile;
import com.jsdc.gsgwxb.model.sys.SysMenu;
import com.jsdc.gsgwxb.model.sys.SysRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 用户管理
 */
@Entity
@Table(name = "news_info")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@org.hibernate.annotations.Table(
        appliesTo = "news_info",
        comment = "新闻中心"
)
public class NewsInfo extends Model<NewsInfo> implements Serializable {
    @Id
    @TableId(value = "id", type = IdType.INPUT)
    @Comment("id")
    private String id;
    @Comment("类型 数据字典 news_type")
    private Integer type;
    @Comment("标题")
    private String title;
    @Comment("是否审核 1是 0否")
    private Integer isStates;
    @Comment("状态 1正常 0禁用")
    private Integer states;
    @Comment("是否推荐 1是 0否")
    private String isRecommended;
    @Comment("简要概述")
    private String remarks;
    @Comment("详细描述")
    @Column(
            name = "content",          // 数据库字段名
            length = 2000,               // 定义 VARCHAR(2000)
            columnDefinition = "VARCHAR(2000) COMMENT '描述'"  // 补充注释（部分数据库支持）
    )
    private String content;
    @Comment("排序")
    private String sort;

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
    @Comment("是否删除, 0、否 1、是")
    private Integer isDel;

    //文件
    @Transient
    @TableField(exist = false)
    private List<SysFile> files;
    //状态名称
    @Transient
    @TableField(exist = false)
    private String stateName;
    //查询条件 创建时间
    @Transient
    @TableField(exist = false)
    private String query_create_time_start;
    //查询条件 创建时间
    @Transient
    @TableField(exist = false)
    private String query_create_time_end;
    //创建人 姓名
    @Transient
    @TableField(exist = false)
    private String create_user_name;
    //页码
    @Transient
    @TableField(exist = false)
    private Integer pageNo;
    //页大小
    @Transient
    @TableField(exist = false)
    private Integer pageSize;
}
