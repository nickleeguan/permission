package com.icode.service.impl;

import com.google.common.base.Preconditions;
import com.icode.beans.PageQuery;
import com.icode.beans.PageResult;
import com.icode.common.RequestHolder;
import com.icode.dao.SysAclMapper;
import com.icode.exception.ParamException;
import com.icode.model.SysAcl;
import com.icode.param.AclParam;
import com.icode.service.interfaces.SysAclService;
import com.icode.service.interfaces.SysLogService;
import com.icode.util.BeanValidator;
import com.icode.util.IpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class SysAclServiceImpl implements SysAclService {

    private static final Logger logger = LoggerFactory.getLogger(SysAclServiceImpl.class);

    @Autowired
    private SysAclMapper sysAclMapper;

    @Resource
    private SysLogService sysLogService;

    public void saveAcl(AclParam param) {
        BeanValidator.check(param);
        if (checkExist(param.getAclModuleId(), param.getName(), param.getId())){
            throw new ParamException("当前权限模块下存在相同名称的权限点");
        }
        SysAcl acl = new SysAcl(param.getName(), param.getAclModuleId(), param.getUrl(),
                param.getType(), param.getStatus(), param.getSeq(), param.getRemark());

        acl.setCode(generateCode());

        acl.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        acl.setOperateTime(new Date());
        acl.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysAclMapper.insert(acl);
        sysLogService.saveAclLog(null, acl);
    }

    private boolean checkExist(int aclModuleId, String name, Integer id){
        return sysAclMapper.countByNameAndAclModuleId(aclModuleId, name, id) > 0;
    }

    public void updateAcl(AclParam param) {
        BeanValidator.check(param);
        if (checkExist(param.getAclModuleId(), param.getName(), param.getId())){
            throw new ParamException("当前权限模块下存在相同名称的权限点");
        }

        SysAcl before = sysAclMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(before, "待更新的权限点为空");

        SysAcl after = new SysAcl(param.getId(), param.getName(), param.getAclModuleId(), param.getUrl(),
                param.getType(), param.getStatus(), param.getSeq(), param.getRemark());

        after.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        after.setOperateTime(new Date());
        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysAclMapper.updateByPrimaryKeySelective(after);
        sysLogService.saveAclLog(before, after);
    }

    public String generateCode(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return dateFormat.format(new Date()) + "_" + (int)(Math.random() * 100);
    }

    public PageResult<SysAcl> getPageByAclModuleId(int aclModuleId, PageQuery pageQuery) {
        BeanValidator.check(pageQuery);
        int count = sysAclMapper.countByAclModuleId(aclModuleId);
        if (count > 0){
            List<SysAcl> aclList = sysAclMapper.getPageByAclModuleId(aclModuleId, pageQuery);
            return new PageResult<SysAcl>(aclList, count);
        }
        return new PageResult<SysAcl>();
    }
}
