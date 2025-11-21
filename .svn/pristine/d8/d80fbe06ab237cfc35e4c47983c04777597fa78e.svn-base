package com.jsdc.gsgwxb.model.cooperation;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jsdc.gsgwxb.model.sys.SysMenu;
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

@Entity
@TableName(value = "cooperation_case")
@Table(name = "cooperation_case")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@org.hibernate.annotations.Table(
        comment = "合作案例",
        appliesTo = "cooperation_case"
)
public class CooperationCase extends Model<CooperationCase> implements Serializable {
    @Id
    @TableId(value = "id", type = IdType.INPUT)
    @Comment("id")
    private String id;
    @Comment("产品名称")
    private String productName;
    @Comment("所属概述")
    private String overview;
    @Comment("详细描述")
    private String description;
    @Comment("案例标识")
    private String caseIcon;
    @Comment("是否审核 1是0否")
    private String isReview;
    @Comment("是否推荐 1是0否")
    private String isRecommended;
    @Comment("排序")
    private Integer sort;
    @Comment("删除标记, 0：否 1：是")
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
    @Transient
    @TableField(exist = false)
    private Integer pageIndex;
    @Transient
    @TableField(exist = false)
    private Integer pageSize;

}
