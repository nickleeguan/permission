package com.icode.dao;

import com.icode.model.SysRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysRoleMapper {
    int deleteByPrimaryKey(@Param("id") Integer id);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(@Param("id") Integer id);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);

    List<SysRole> getAll();

    int countByName(@Param("name") String name, @Param("id") Integer id);

    List<SysRole> getByIdList(@Param("idList") List<Integer> idList);
}