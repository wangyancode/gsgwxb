package com.jsdc.gsgwxb.model.sys;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户管理
 */
@Entity
@Table(name = "sys_user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@org.hibernate.annotations.Table(
        appliesTo = "sys_user",
        comment = "系统用户"
)
public class SysUser extends Model<SysUser> implements Serializable {
    @Id
    @TableId(value = "id", type = IdType.INPUT)
    @Comment("id")
    private String id;
    @Comment("用户账号")
    private String account;
    @Comment("用户名")
    private String userName;
    @Comment("姓名")
    private String realName;
    @Comment("手机号")
    private String phone;
    @Comment("性别 1男 2女 -1未知")
    private Integer sex;
    @Comment("身份证号")
    private String idCard;
    @Comment("邮箱")
    private String email;
    @Comment("备注")
    private String remarks;
    @Comment(value = "微信openId")
    @TableField(value = "wxOpenId")
    private String wxOpenId;
    @Comment("密码")
    private String password;
    @Comment("审核人")
    private String reviewUser;
    @Comment("审核意见")
    private String reviewReason;
    //状态
    @Comment("状态 1正常 0禁用")
    private Integer states;
    @Comment("同步版本")
    private String version;
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
    //是否是初始密码（0、否 1、是）
    @Transient
    @TableField(exist = false)
    private Integer is_init_pwd;
    @Transient
    @TableField(exist = false)
    private String createTimeStr;
    @Transient
    @TableField(exist = false)
    private String token;
    //状态名称
    @Transient
    @TableField(exist = false)
    private String stateName;
    // 关联的菜单
    @Transient
    @TableField(exist = false)
    private List<SysMenu> menus;
    // 关联的角色
    @Transient
    @TableField(exist = false)
    private List<SysRole> roles;
    @Transient
    @TableField(exist = false)
    private Integer pageNo;
    @Transient
    @TableField(exist = false)
    private Integer pageSize;
    //头像文件
    @Transient
    @TableField(exist = false)
    private SysFile sysFile;
    @Transient
    @TableField(exist = false)
    @ApiModelProperty(hidden = true)
    List<String> authority = new ArrayList<>();

}
