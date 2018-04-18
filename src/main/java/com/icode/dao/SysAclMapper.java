package com.icode.dao;

import com.icode.model.SysAcl;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public interface SysAclMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysAcl record);

    int insertSelective(SysAcl record);

    SysAcl selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysAcl record);

    int updateByPrimaryKey(SysAcl record);
}