package com.icode.service.interfaces;

import com.icode.model.SysUser;
import com.icode.param.UserParam;

public interface SysUserService {
    public void save(UserParam param);

    void update(UserParam param);

    SysUser findByKeyword(String keyword);
}
