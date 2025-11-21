package com.jsdc.gsgwxb.model.company;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jsdc.gsgwxb.model.sys.SysFile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 用户管理
 */
@Entity
@Table(name = "company_detail")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@org.hibernate.annotations.Table(
        appliesTo = "company_detail",
        comment = "公司详情"
)
public class CompanyDetail extends Model<CompanyDetail> implements Serializable {
    @Id
    @TableId(value = "id", type = IdType.INPUT)
    @Comment("id")
    private String id;
    @Comment("类型 1 公司简介 2 服务体系 3 安全保障")
    private String type;
    @Comment("所属类型 数据字典 company_detail_belongingType")
    private String belongingType;
    @Comment("标题")
    private String title;
    @Comment("简语")
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
