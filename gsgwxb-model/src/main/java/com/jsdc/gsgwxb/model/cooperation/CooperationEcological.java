package com.jsdc.gsgwxb.model.cooperation;

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

@Entity
@TableName(value = "cooperation_ecological")
@Table(name = "cooperation_ecological")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@org.hibernate.annotations.Table(
        comment = "生态合作",
        appliesTo = "cooperation_ecological"
)
public class CooperationEcological extends Model<CooperationEcological> implements Serializable {
    @Id
    @TableId(value = "id", type = IdType.INPUT)
    @Comment("id")
    private String id;
    @Comment("主标题")
    private String mainTitle;
    @Comment("副标题")
    private String subtitle;
    @Comment("简语")
    private String simpleWords;


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
