package com.icode.service.impl;

import com.icode.dao.SysAclMapper;
import com.icode.exception.ParamException;
import com.icode.model.SysAcl;
import com.icode.param.AclParam;
import com.icode.service.interfaces.SysAclService;
import com.icode.util.BeanValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysAclServiceImpl implements SysAclService {

    private static final Logger logger = LoggerFactory.getLogger(SysAclServiceImpl.class);

    @Autowired
    private SysAclMapper sysAclMapper;

    public void saveAcl(AclParam param) {
        BeanValidator.check(param);
        if (checkExist(param.getAclModuleId(), param.getName(), param.getId())){
            throw new ParamException("当前权限模块下存在相同名称的权限点");
        }
        SysAcl sysAcl = new SysAcl(param.getName(), param.getAclModuleId(), param.getUrl(),
                param.getType(), param.getStatus(), param.getSeq(), param.getRemark());
    }

    private boolean checkExist(int aclModuleId, String name, Integer id){
        return false;
    }

    public void updateAcl(AclParam param) {

    }
}
