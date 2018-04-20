package com.icode.service.interfaces;

import java.util.List;

public interface SysRoleAclService {

    void changeRoleAcls(Integer roleId, List<Integer> aclIdList);

    void updateRoleAcls(int roleId, List<Integer> aclIdList);
}
