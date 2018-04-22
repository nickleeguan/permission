package com.icode.service.interfaces;

import com.icode.model.SysAcl;

import java.util.List;

public interface SysCoreService {

    public List<SysAcl> getCurrentUserAclList();

    List<SysAcl> getRoleAclList(int roleId);

    List<SysAcl> getUserAclList(int userId);

    boolean hasUrlAcl(String url);
}
