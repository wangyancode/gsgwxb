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
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@TableName(value = "sys_role")
@Table(name = "sys_role")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@org.hibernate.annotations.Table(
        comment = "角色",
        appliesTo = "sys_role"
)
public class SysRole extends Model<SysRole> implements Serializable {
    @Id
    @TableId(value = "id", type = IdType.INPUT)
    @Comment("id")
    private String id;
    @Comment("角色名")
    private String roleName;
    @Comment("角色标识")
    private String roleCode;
    @Comment("状态 1、正常 2、禁用")
    private Integer isFlag;
    @Comment("备注")
    private String remarks;
    @Comment("排序")
    private Integer sort;
    @Comment("删除标记, 0、否 1、是")
    private Integer isDel;
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
    //菜单权限ids
    @Transient
    @TableField(exist = false)
    private List<String> menuIds;
    @Transient
    @TableField(exist = false)
    private Integer pageIndex;
    @Transient
    @TableField(exist = false)
    private Integer pageSize;
    @Transient
    @TableField(exist = false)
    private String createTimeStr;
    @Transient
    @TableField(exist = false)
    private String status;
    @Transient
    @TableField(exist = false)
    private String startTime;
    @Transient
    @TableField(exist = false)
    private String endTime;
}