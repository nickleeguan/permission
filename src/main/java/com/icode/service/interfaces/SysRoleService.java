package com.icode.service.interfaces;

import com.icode.model.SysRole;
import com.icode.model.SysUser;
import com.icode.param.RoleParam;

import javax.management.relation.Role;
import java.util.List;

public interface SysRoleService {

    void saveRole(RoleParam param);

    void updateRole(RoleParam param);

    List<SysRole> getAll();

    List<SysRole> getRoleListByUserId(int userId);

    List<SysRole> getRoleListByAclId(int aclId);

    List<SysUser> getUserListByRoleList(List<SysRole> roleList);
}
