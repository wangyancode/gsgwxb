package com.jsdc.gsgwxb.model.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 角色菜单管理
 */
@Entity
@TableName("sys_role_menu")
@Table(name = "sys_role_menu")
@Data
@org.hibernate.annotations.Table(
        appliesTo = "sys_role_menu",
        comment = "角色菜单关联表"
)
public class SysRoleMenu extends Model<SysRoleMenu> implements Serializable {
    @Id
    @TableId(value = "id", type = IdType.INPUT)
    private String id;
    //菜单id
    @Comment("菜单id")
    private String menuId;
    //角色id
    @Comment("角色id")
    private String roleId;
    @Comment("是否删除, 0、否 1、是")
    private Integer isDel;

}
