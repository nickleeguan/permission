package com.icode.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.icode.common.RequestHolder;
import com.icode.dao.SysRoleAclMapper;
import com.icode.model.SysRoleAcl;
import com.icode.service.interfaces.SysLogService;
import com.icode.service.interfaces.SysRoleAclService;
import com.icode.util.IpUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class SysRoleAclServiceImpl implements SysRoleAclService {

    private static final Logger logger = LoggerFactory.getLogger(SysRoleAclServiceImpl.class);

    @Resource
    private SysRoleAclMapper sysRoleAclMapper;

    @Resource
    private SysLogService sysLogService;

    @Override
    public void changeRoleAcls(Integer roleId, List<Integer> aclIdList) {
        //取出已有权限
        List<Integer> originAclIdList = sysRoleAclMapper.getAclIdListByRoleIdList(Lists.newArrayList(roleId));

        //判断是否存在需要更新的权限点
        if (originAclIdList.size() == aclIdList.size()){
            Set<Integer> originAclIdSet = Sets.newHashSet(originAclIdList);
            Set<Integer> aclIdSet = Sets.newHashSet(aclIdList);
            originAclIdSet.removeAll(aclIdSet);
            if (CollectionUtils.isEmpty(originAclIdSet)){
                return;
            }
        }

        updateRoleAcls(roleId, aclIdList);

        sysLogService.saveRoleAclLog(roleId, originAclIdList, aclIdList);
    }

    @Transactional
    public void updateRoleAcls(int roleId, List<Integer> aclIdList){
        sysRoleAclMapper.deleteByRoleId(roleId);

        if (CollectionUtils.isEmpty(aclIdList)){
            return;
        }

        List<SysRoleAcl> roleAclList = Lists.newArrayList();
        aclIdList.forEach(aclId -> {
            SysRoleAcl roleAcl = new SysRoleAcl(roleId, aclId);
            roleAcl.setOperator(RequestHolder.getCurrentUser().getUsername());
            roleAcl.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
            roleAcl.setOperateTime(new Date());

            roleAclList.add(roleAcl);
        });

        sysRoleAclMapper.batchInsert(roleAclList);
    }
}
