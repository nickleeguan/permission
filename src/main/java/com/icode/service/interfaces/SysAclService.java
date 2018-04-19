package com.icode.service.interfaces;

import com.icode.beans.PageQuery;
import com.icode.beans.PageResult;
import com.icode.model.SysAcl;
import com.icode.param.AclParam;



public interface SysAclService {
    void saveAcl(AclParam param);

    void updateAcl(AclParam param);

    PageResult<SysAcl> getPageByAclModuleId(int aclModuleId, PageQuery pageQuery);
}
