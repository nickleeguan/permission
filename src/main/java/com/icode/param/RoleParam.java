package com.icode.param;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class RoleParam {

    private Integer id;

    @NotBlank(message = "角色名称不可为空")
    @Length(max = 15, min = 2, message = "角色名称不合规范")
    private String name;


    @Min(value = 1, message = "角色类型不合法")
    @Max(value = 2, message = "角色类型不合法")
    private Integer type = 1;

    @NotNull(message = "角色状态不可为空")
    @Min(value = 0, message = "角色状态不合法")
    @Max(value = 1, message = "角色状态不合法")
    private Integer status;

    @Length(max = 150, message = "角色备注长度不可超限")
    private String remark;



    public RoleParam() {
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }



    public RoleParam(Integer id, String name, Integer type, String remark) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.remark = remark;
    }

    public RoleParam(String name, Integer type, String remark) {
        this.name = name;
        this.type = type;
        this.remark = remark;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
