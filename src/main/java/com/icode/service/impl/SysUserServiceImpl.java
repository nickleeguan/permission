package com.icode.service.impl;

import com.google.common.base.Preconditions;
import com.icode.dao.SysUserMapper;
import com.icode.exception.ParamException;
import com.icode.model.SysUser;
import com.icode.param.UserParam;
import com.icode.service.interfaces.SysUserService;
import com.icode.util.BeanValidator;
import com.icode.util.MD5Util;
import com.icode.util.PasswordUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class SysUserServiceImpl implements SysUserService {
    private static final Logger logger = LoggerFactory.getLogger(SysUserServiceImpl.class);

    @Resource
    private SysUserMapper sysUserMapper;

    public void save(UserParam param) {
        BeanValidator.check(param);
        if (checkTelephoneExist(param.getTelephone(), param.getId())){
            throw new ParamException("电话号码已被占用");
        }
        if (checkEmailExist(param.getMail(), param.getId())){
            throw new ParamException("邮箱已被占用");
        }

        String password = PasswordUtil.randomPassword();
        //todo
        password="123456";
        String encryptedPassword = MD5Util.encrypt(password);

        SysUser user = new SysUser(param.getUsername(), param.getTelephone(),
                param.getMail(), encryptedPassword, param.getDeptId(), param.getStatus(),
                param.getRemark());
        user.setOperateIp("127.0.0.1");
        user.setOperateTime(new Date());
        user.setOperator("system");

        // TODO:send Email

        sysUserMapper.insertSelective(user);
    }

    public void update(UserParam param){
        BeanValidator.check(param);
        if (checkTelephoneExist(param.getTelephone(), param.getId())){
            throw new ParamException("电话号码已被占用");
        }
        if (checkEmailExist(param.getMail(), param.getId())){
            throw new ParamException("邮箱已被占用");
        }

        SysUser before = sysUserMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(before, "待更新的用户不存在");
        SysUser after = new SysUser(param.getId(), param.getUsername(), param.getTelephone(),
                param.getMail(), before.getPassword(), param.getDeptId(), param.getStatus(),
                param.getRemark());

        sysUserMapper.updateByPrimaryKeySelective(after);
    }

    public boolean checkEmailExist(String mail, Integer userId){

        return false;
    }

    public boolean checkTelephoneExist(String telephone, Integer userId){
        return false;
    }

    public SysUser findByKeyword(String keyword) {
        return null;
    }
}
