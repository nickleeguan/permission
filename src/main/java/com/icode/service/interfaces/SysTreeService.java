package com.icode.service.interfaces;

import com.icode.dto.DeptLevelDto;

import java.util.List;

public interface SysTreeService {
    public List<DeptLevelDto> deptTree();

    public List<DeptLevelDto> deptListToTree(List<DeptLevelDto> deptLevelList);
}
