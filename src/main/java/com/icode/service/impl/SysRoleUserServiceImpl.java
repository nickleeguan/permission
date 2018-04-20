package com.icode.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.icode.common.RequestHolder;
import com.icode.dao.SysRoleUserMapper;
import com.icode.dao.SysUserMapper;
import com.icode.model.SysRoleUser;
import com.icode.model.SysUser;
import com.icode.service.interfaces.SysRoleUserService;
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
public class SysRoleUserServiceImpl implements SysRoleUserService {

    private static final Logger logger = LoggerFactory.getLogger(SysRoleUserServiceImpl.class);

    @Resource
    private SysRoleUserMapper sysRoleUserMapper;

    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public List<SysUser> getListByRoleId(int roleId) {
        logger.debug("获取拥有当前角色的用户");
        List<Integer> userIdList = sysRoleUserMapper.getUserIdListByRoleId(roleId);
        if (CollectionUtils.isEmpty(userIdList)){
            return Lists.newArrayList();
        }
        return sysUserMapper.getByIdList(userIdList);
    }

    @Override
    public void changeRoleUsers(int roleId, List<Integer> userIdList) {
        List<Integer> originUserIdList = sysRoleUserMapper.getUserIdListByRoleId(roleId);
        if (originUserIdList.size() == userIdList.size()){
            Set<Integer> originUserIdSet = Sets.newHashSet(originUserIdList);
            Set<Integer> userIdSet = Sets.newHashSet(userIdList);
            originUserIdSet.removeAll(userIdSet);
            if (CollectionUtils.isEmpty(originUserIdSet)){
                return;
            }
        }

        updateRoleUsers(roleId, userIdList);
    }

    @Transactional
    public void updateRoleUsers(int roleId, List<Integer> userIdList) {
        sysRoleUserMapper.deleteByRoleId(roleId);

        if (CollectionUtils.isEmpty(userIdList)){
            return;
        }

        List<SysRoleUser> roleUserList = Lists.newArrayList();
        userIdList.forEach(userId -> {
            SysRoleUser roleUser = new SysRoleUser(roleId, userId);
            roleUser.setOperator(RequestHolder.getCurrentUser().getUsername());
            roleUser.setOperateTime(new Date());
            roleUser.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));

            roleUserList.add(roleUser);
        });

        sysRoleUserMapper.batchInsert(roleUserList);
    }
}
