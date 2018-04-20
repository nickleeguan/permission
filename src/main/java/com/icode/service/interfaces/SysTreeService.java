package com.icode.service.interfaces;

import com.icode.dto.AclModuleLevelDto;
import com.icode.dto.DeptLevelDto;

import java.util.List;

public interface SysTreeService {
    public List<DeptLevelDto> deptTree();

    public List<DeptLevelDto> deptListToTree(List<DeptLevelDto> deptLevelList);

    public List<AclModuleLevelDto> aclModuleTree();

    public List<AclModuleLevelDto> roleTree(int roleId);

    List<AclModuleLevelDto> userAclTree(int userId);
}
