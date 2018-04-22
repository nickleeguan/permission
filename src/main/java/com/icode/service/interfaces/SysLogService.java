package com.icode.service.interfaces;

import com.icode.model.*;

import java.util.List;

public interface SysLogService {
    void saveDeptLog(SysDept before, SysDept after);

    void saveUserLog(SysUser before, SysUser after);

    void saveAclModuleLog(SysAclModule before, SysAclModule after);

    void saveAclLog(SysAcl before, SysAcl after);

    void saveRoleLog(SysRole before, SysRole after);

    void saveRoleAclLog(int roleId, List<Integer> before, List<Integer> after);

    void saveRoleUserLog(int roleId, List<Integer> before, List<Integer> after);
}
