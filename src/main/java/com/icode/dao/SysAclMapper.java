package com.icode.dao;

import com.icode.beans.PageQuery;
import com.icode.model.SysAcl;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysAclMapper {
    int deleteByPrimaryKey(@Param("id") Integer id);

    int insert(SysAcl record);

    int insertSelective(SysAcl record);

    SysAcl selectByPrimaryKey(@Param("id") Integer id);

    int updateByPrimaryKeySelective(SysAcl record);

    int updateByPrimaryKey(SysAcl record);

    int countByAclModuleId(@Param("aclModuleId") int aclModuleId);

    List<SysAcl> getPageByAclModuleId(@Param("aclModuleId") int aclModuleId, @Param("page")PageQuery page);

    int countByNameAndAclModuleId(@Param("aclModuleId") int aclModuleId, @Param("name") String name, @Param("id") Integer id);

    List<SysAcl> getAll();

    List<SysAcl> getByIdList(@Param("idList") List<Integer> idList);

    List<SysAcl> getByUrl(@Param("url") String url);
}