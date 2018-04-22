package com.icode.service.impl;

import com.google.common.collect.Lists;
import com.icode.beans.CacheKeyConstants;
import com.icode.common.RequestHolder;
import com.icode.dao.SysAclMapper;
import com.icode.dao.SysRoleAclMapper;
import com.icode.dao.SysRoleUserMapper;
import com.icode.model.SysAcl;
import com.icode.model.SysUser;
import com.icode.service.interfaces.SysCacheService;
import com.icode.service.interfaces.SysCoreService;
import com.icode.util.JsonMapper;
import com.icode.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;


import javax.annotation.Resource;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SysCoreServiceImpl implements SysCoreService {

    @Resource
    private SysAclMapper sysAclMapper;

    @Resource
    private SysRoleUserMapper sysRoleUserMapper;

    @Resource
    private SysRoleAclMapper sysRoleAclMapper;

    @Resource
    private SysCacheService sysCacheService;

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
        //自定义超级管理员规则
        SysUser user = RequestHolder.getCurrentUser();
        if (user.getMail().contains("admin")){
            return true;
        }
        return false;
    }

    public boolean hasUrlAcl(String url) {
        if (isSuperAdmin()){
            return true;
        }

        List<SysAcl> aclList = sysAclMapper.getByUrl(url);
        if (CollectionUtils.isEmpty(aclList)){
            return true;
        }

        List<SysAcl> userAclList = getCurrentUserAclListFromCache();
        Set<Integer> userAclIdSet = userAclList.stream().map(acl -> acl.getId()).collect(Collectors.toSet());

        //规则:只要有一个权限点有权限，就认为有权限
        // /sys/user/action.json
        boolean hasValidAcl = false;
        for (SysAcl acl : aclList) {
            if (acl == null || acl.getStatus() != 1){
                continue;
            }
            hasValidAcl = true;
            if (userAclIdSet.contains(acl.getId())){
                return true;
            }
        }
        if (!hasValidAcl){
            return true;
        }

        return false;
    }

    public List<SysAcl> getCurrentUserAclListFromCache(){
        int userId = RequestHolder.getCurrentUser().getId();
        String cacheValue = sysCacheService.getFromCache(CacheKeyConstants.USER_ACLS, String.valueOf(userId));
        if (StringUtils.isBlank(cacheValue)){
            List<SysAcl> aclList = getCurrentUserAclList();
            if (!CollectionUtils.isEmpty(aclList)){
                sysCacheService.saveCache(JsonMapper.obj2String(aclList), 600, CacheKeyConstants.USER_ACLS, String.valueOf(userId));
            }
            return aclList;
        }

        return JsonMapper.string2Obj(cacheValue, new TypeReference<List<SysAcl>>() {});
    }
}
