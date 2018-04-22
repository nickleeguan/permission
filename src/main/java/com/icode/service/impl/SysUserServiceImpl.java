package com.icode.service.impl;

import com.google.common.base.Preconditions;
import com.icode.beans.PageQuery;
import com.icode.beans.PageResult;
import com.icode.common.RequestHolder;
import com.icode.dao.SysUserMapper;
import com.icode.exception.ParamException;
import com.icode.model.SysUser;
import com.icode.param.UserParam;
import com.icode.service.interfaces.SysLogService;
import com.icode.service.interfaces.SysUserService;
import com.icode.util.BeanValidator;
import com.icode.util.IpUtil;
import com.icode.util.MD5Util;
import com.icode.util.PasswordUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class SysUserServiceImpl implements SysUserService {
    private static final Logger logger = LoggerFactory.getLogger(SysUserServiceImpl.class);

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private SysLogService sysLogService;

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
        user.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        user.setOperateTime(new Date());
        user.setOperator(RequestHolder.getCurrentUser().getUsername());

        // TODO:send Email

        sysUserMapper.insert(user);
        sysLogService.saveUserLog(null, user);
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

        after.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        after.setOperateTime(new Date());
        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysUserMapper.updateByPrimaryKeySelective(after);
        sysLogService.saveUserLog(before, after);
    }

    public boolean checkEmailExist(String mail, Integer userId){

        return sysUserMapper.countByMail(mail, userId) > 0;
    }

    public boolean checkTelephoneExist(String telephone, Integer userId){
        return sysUserMapper.countByTelephone(telephone, userId) > 0;
    }

    public SysUser findByKeyword(String keyword) {
        return sysUserMapper.findByKeyword(keyword);
    }

    public PageResult<SysUser> getPageByDeptId(int deptId, PageQuery page) {
        BeanValidator.check(page);
        int count = sysUserMapper.countByDeptId(deptId);
        if (count > 0){
            List<SysUser> list = sysUserMapper.getPageByDeptId(deptId, page);
            return new PageResult<SysUser>(list, count);
        }
        return new PageResult<SysUser>();
    }

    @Override
    public List<SysUser> getAll() {
        return sysUserMapper.getAll();
    }
}
