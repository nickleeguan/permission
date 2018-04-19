package com.icode.dao;

import com.icode.model.AppInfo;

public interface AppInfoMapper {
    int deleteByPrimaryKey(Long appId);

    int insert(AppInfo record);

    int insertSelective(AppInfo record);

    AppInfo selectByPrimaryKey(Long appId);

    int updateByPrimaryKeySelective(AppInfo record);

    int updateByPrimaryKey(AppInfo record);
}