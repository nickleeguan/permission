package com.icode.service.impl;

import com.google.common.base.Preconditions;
import com.icode.common.RequestHolder;
import com.icode.dao.SysAclMapper;
import com.icode.dao.SysAclModuleMapper;
import com.icode.exception.ParamException;
import com.icode.model.SysAclModule;
import com.icode.model.SysDept;
import com.icode.param.AclModuleParam;
import com.icode.service.interfaces.SysAclModuleService;
import com.icode.service.interfaces.SysLogService;
import com.icode.util.BeanValidator;
import com.icode.util.IpUtil;
import com.icode.util.LevelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class SysAclModuleServiceImpl implements SysAclModuleService {

    @Resource
    private SysAclModuleMapper sysAclModuleMapper;

    @Resource
    private SysAclMapper sysAclMapper;

    @Resource
    private SysLogService sysLogService;

    public void save(AclModuleParam param) {
        BeanValidator.check(param);
        if (checkExist(param.getParentId(), param.getName(), param.getId())){
            throw new ParamException("同一层级下存在相同名称的权限模块");
        }

        SysAclModule aclModule = new SysAclModule(param.getName(), param.getParentId(), param.getSeq(),
                param.getStatus(), param.getRemark());

        aclModule.setLevel(LevelUtil.calculateLevel(getLevel(param.getParentId()), param.getParentId()));
        aclModule.setOperator(RequestHolder.getCurrentUser().getUsername());
        aclModule.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        aclModule.setOperateTime(new Date());

        sysAclModuleMapper.insert(aclModule);
        sysLogService.saveAclModuleLog(null, aclModule);
    }

    public void update(AclModuleParam param) {
        BeanValidator.check(param);
        if (checkExist(param.getParentId(), param.getName(), param.getId())){
            throw new ParamException("同一层级下存在相同名称的权限模块");
        }

        SysAclModule before = sysAclModuleMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(before, "待更新的权限模块不存在");
        SysAclModule after = new SysAclModule(param.getId(), param.getName(), param.getParentId(), param.getSeq(),
                param.getStatus(), param.getRemark());

        after.setLevel(LevelUtil.calculateLevel(getLevel(param.getParentId()), param.getParentId()));
        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        after.setOperateTime(new Date());

        updateWithChild(before, after);
        sysLogService.saveAclModuleLog(before, after);
    }

    @Transactional
    public void updateWithChild(SysAclModule before, SysAclModule after){
        String newLevelPrefix = after.getLevel();
        String oldLevelPrefix = before.getLevel();
        if (!after.getLevel().equals(before.getLevel())){
            List<SysAclModule> aclModuleList = sysAclModuleMapper.getChildDeptListByLevel(before.getLevel());
            if (CollectionUtils.isNotEmpty(aclModuleList)){
                for (SysAclModule aclModule : aclModuleList) {
                    String level = aclModule.getLevel();
                    if (level.indexOf(oldLevelPrefix + "." + before.getId()) == 0){
                        level = newLevelPrefix + level.substring(oldLevelPrefix.length());
                        aclModule.setLevel(level);
                    }
                }
                sysAclModuleMapper.batchUpdateLevel(aclModuleList);
            }
        }

        sysAclModuleMapper.updateByPrimaryKey(after);
    }

    private boolean checkExist(Integer parentId, String aclModuleName, Integer aclModuleId){
        return sysAclModuleMapper.countByNameAndParentId(parentId, aclModuleName, aclModuleId) > 0;
    }

    private String getLevel(Integer aclModuleId){
        SysAclModule aclModule = sysAclModuleMapper.selectByPrimaryKey(aclModuleId);
        if (aclModule == null){
            return null;
        }
        return aclModule.getLevel();
    }

    @Override
    public void delete(int aclModuleId) {
        SysAclModule aclModule = sysAclModuleMapper.selectByPrimaryKey(aclModuleId);
        Preconditions.checkNotNull(aclModule, "待删除的权限模块不存在");

        if (sysAclModuleMapper.countByParentId(aclModuleId) > 0){
            throw new ParamException("待删除的权限模块下存在子模块不可删除");
        }

        if (sysAclMapper.countByAclModuleId(aclModuleId) > 0){
            throw new ParamException("待删除的权限模块下存在权限点不可删除");
        }

        sysAclModuleMapper.deleteByPrimaryKey(aclModuleId);
    }
}
