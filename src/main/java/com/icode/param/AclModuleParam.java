package com.icode.param;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class AclModuleParam {

    private Integer id;

    @NotBlank(message = "权限名称不可为空")
    @Length(min = 2, max = 20, message = "权限名称长度不合法")
    private String name;
    private Integer parentId = 0;
    private String level;

    @NotNull(message = "权限模块展示顺序不可为空")
    private Integer seq;

    @NotNull(message = "状态值不可为空")
    @Min(value = 0, message = "状态值不合法")
    @Max(value = 1, message = "状态值不合法")
    private Integer status;

    @Length(max = 200, message = "权限模块备注长度不合法")
    private String remark;

    public AclModuleParam() {
    }

    public AclModuleParam(String name, Integer parentId, String level, Integer seq, Integer status, String remark) {
        this.name = name;
        this.parentId = parentId;
        this.level = level;
        this.seq = seq;
        this.status = status;
        this.remark = remark;
    }

    public AclModuleParam(String name, Integer parentId, Integer seq, Integer status, String remark) {
        this.name = name;
        this.parentId = parentId;
        this.seq = seq;
        this.status = status;
        this.remark = remark;
    }



    public AclModuleParam(Integer id, String name, Integer parentId, String level, Integer seq, Integer status, String remark) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.level = level;
        this.seq = seq;
        this.status = status;
        this.remark = remark;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
