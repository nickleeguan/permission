package com.icode.service.impl;

import com.google.common.collect.Lists;
import com.icode.common.RequestHolder;
import com.icode.dao.SysAclMapper;
import com.icode.dao.SysRoleAclMapper;
import com.icode.dao.SysRoleUserMapper;
import com.icode.model.SysAcl;
import com.icode.service.interfaces.SysCoreService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysCoreServiceImpl implements SysCoreService {

    @Resource
    private SysAclMapper sysAclMapper;

    @Resource
    private SysRoleUserMapper sysRoleUserMapper;

    @Resource
    private SysRoleAclMapper sysRoleAclMapper;

    public List<SysAcl> getCurrentUserAclList() {
        int useId = RequestHolder.getCurrentUser().getId();

        return getUserAclList(useId);
    }

    public List<SysAcl> getRoleAclList(int roleId) {
        List<Integer> aclIdList = sysRoleAclMapper.getAclIdListByRoleIdList(Lists.<Integer>newArrayList(roleId));

        if (CollectionUtils.isEmpty(aclIdList)){
            return Lists.newArrayList();
        }

        return sysAclMapper.getByIdList(aclIdList);
    }

    public List<SysAcl> getUserAclList(int userId) {
        if (isSuperAdmin()){
            return sysAclMapper.getAll();
        }
        List<Integer> userRoleIdList = sysRoleUserMapper.getRoleIdListByUserId(userId);
        if (CollectionUtils.isEmpty(userRoleIdList)){
            return Lists.newArrayList();
        }

        List<Integer> userAclIdList = sysRoleAclMapper.getAclIdListByRoleIdList(userRoleIdList);
        if (CollectionUtils.isEmpty(userAclIdList)){
            return Lists.newArrayList();
        }


        return sysAclMapper.getByIdList(userAclIdList);
    }

    private boolean isSuperAdmin(){
        return true;
    }
}
