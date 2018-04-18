package com.icode.dto;

import com.google.common.collect.Lists;
import com.icode.model.SysAclModule;
import org.springframework.beans.BeanUtils;

import java.util.List;

public class AclModuleLevelDto extends SysAclModule {

    private List<AclModuleLevelDto> aclModuleList = Lists.newArrayList();

    public static AclModuleLevelDto adapt(SysAclModule aclModule){
        AclModuleLevelDto dto = new AclModuleLevelDto();
        BeanUtils.copyProperties(aclModule, dto);
        return dto;
    }

    public List<AclModuleLevelDto> getAclModuleList() {
        return aclModuleList;
    }

    public void setAclModuleList(List<AclModuleLevelDto> aclModuleList) {
        this.aclModuleList = aclModuleList;
    }
}
