package com.icode.service.interfaces;

import com.icode.model.SysUser;

import java.util.List;

public interface SysRoleUserService {

    List<SysUser> getListByRoleId(int roleId);

    void changeRoleUsers(int roleId, List<Integer> userIdList);
}
