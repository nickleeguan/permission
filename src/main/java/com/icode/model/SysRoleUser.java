package com.icode.model;

import java.util.Date;

public class SysRoleUser {
    private Integer id;

    private Integer roleId;

    private Integer userId;

    private String operator;

    private Date operateTime;

    private String operateIp;

    public SysRoleUser() {
    }

    public SysRoleUser(Integer id, Integer roleId, Integer userId) {
        this.id = id;
        this.roleId = roleId;
        this.userId = userId;
    }

    public SysRoleUser(Integer roleId, Integer userId) {
        this.roleId = roleId;
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public String getOperateIp() {
        return operateIp;
    }

    public void setOperateIp(String operateIp) {
        this.operateIp = operateIp == null ? null : operateIp.trim();
    }
}