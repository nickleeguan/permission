package com.icode.service.interfaces;

import com.icode.param.AclParam;



public interface SysAclService {
    void saveAcl(AclParam param);
    void updateAcl(AclParam param);
}
