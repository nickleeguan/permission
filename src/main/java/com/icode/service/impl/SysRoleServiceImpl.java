package com.icode.service.impl;

import com.google.common.base.Preconditions;
import com.icode.common.RequestHolder;
import com.icode.dao.SysRoleMapper;
import com.icode.exception.ParamException;
import com.icode.model.SysRole;
import com.icode.param.RoleParam;
import com.icode.service.interfaces.SysRoleService;
import com.icode.util.BeanValidator;
import com.icode.util.IpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class SysRoleServiceImpl implements SysRoleService {

    private static final Logger logger = LoggerFactory.getLogger(SysRoleServiceImpl.class);

    @Resource
    private SysRoleMapper sysRoleMapper;

    public void saveRole(RoleParam param) {
        BeanValidator.check(param);
        if (checkExist(param.getName(), param.getId())){
            throw new ParamException("角色名称已经存在");
        }

        SysRole role = new SysRole(param.getName(), param.getType(), param.getStatus(), param.getRemark());

        role.setOperator(RequestHolder.getCurrentUser().getUsername());
        role.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        role.setOperateTime(new Date());

        sysRoleMapper.insertSelective(role);
    }

    public void updateRole(RoleParam param) {
        BeanValidator.check(param);
        if (checkExist(param.getName(), param.getId())){
            throw new ParamException("角色名称已经存在");
        }
        SysRole before = sysRoleMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(before, "待修改的角色为空");

        SysRole after = new SysRole(param.getId(), param.getName(), param.getType(), param.getStatus(), param.getRemark());

        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        after.setOperateTime(new Date());

        sysRoleMapper.updateByPrimaryKeySelective(after);
    }

    public List<SysRole> getAll(){
        return sysRoleMapper.getAll();
    }

    private boolean checkExist(String name, Integer id){
        return sysRoleMapper.countByName(name, id) > 0;
    }
}
