package com.jsdc.gsgwxb.model.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 用户角色管理
 */
@Entity
@TableName(value = "sys_user_role")
@Table(name = "sys_user_role")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@org.hibernate.annotations.Table(
        comment = "用户角色管理",
        appliesTo = "sys_user_role"
)
public class SysUserRole extends Model<SysUserRole> implements Serializable {

    @Id
    @TableId(value = "id", type = IdType.INPUT)
    private String id;
    //用户id
    @Comment("用户id")
    private String userId;
    //角色id
    @Comment("角色id")
    private String roleId;
    @Comment("是否删除, 0、否 1、是")
    private Integer isDel;
}
