package com.icode.service.interfaces;

import com.icode.model.SysRole;
import com.icode.param.RoleParam;

import javax.management.relation.Role;
import java.util.List;

public interface SysRoleService {

    void saveRole(RoleParam param);

    void updateRole(RoleParam param);
    List<SysRole> getAll();

}
