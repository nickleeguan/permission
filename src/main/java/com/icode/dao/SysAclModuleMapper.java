package com.icode.dao;

import com.icode.model.SysAclModule;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysAclModuleMapper {
    int deleteByPrimaryKey(@Param("id") Integer id);

    int insert(SysAclModule record);

    int insertSelective(SysAclModule record);

    SysAclModule selectByPrimaryKey(@Param("id") Integer id);

    int updateByPrimaryKeySelective(SysAclModule record);

    int updateByPrimaryKey(SysAclModule record);

    int countByNameAndParentId(@Param("parentId") Integer parentId,@Param("name") String aclModuleName,@Param("id") Integer aclModuleId);

    List<SysAclModule> getChildDeptListByLevel(@Param("level") String level);

    void batchUpdateLevel(@Param("aclModuleList") List<SysAclModule> aclModuleList);

    List<SysAclModule> getAllAclModule();
}