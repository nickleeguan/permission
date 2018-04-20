package com.icode.service.interfaces;

import com.icode.param.AclModuleParam;

public interface SysAclModuleService {

    void save(AclModuleParam param);

    void update(AclModuleParam param);

    void delete(int aclModuleId);
}
