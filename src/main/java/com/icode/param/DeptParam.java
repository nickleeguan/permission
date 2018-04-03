package com.icode.param;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ToString
public class DeptParam {
    private Integer id;

    @NotBlank(message = "部门名称不可为空")
    @Length(max = 15, min = 2, message = "部门名称不合规范")
    private String name;

    private Integer parentId;

    @NotNull(message = "展示顺序不可为空")
    private Integer seq;

    @Length(max = 150, message = "备注长度不可超限")
    private String remark;

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

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
