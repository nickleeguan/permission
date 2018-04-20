package com.icode.controller;

import com.google.common.collect.Maps;
import com.icode.beans.PageQuery;
import com.icode.beans.PageResult;
import com.icode.common.JsonData;
import com.icode.model.SysRole;
import com.icode.param.UserParam;
import com.icode.service.interfaces.SysRoleService;
import com.icode.service.interfaces.SysTreeService;
import com.icode.service.interfaces.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.print.attribute.standard.JobSheets;
import java.util.Map;

@Controller
@RequestMapping("sys/user")
public class SysUserController {
    private static final Logger logger = LoggerFactory.getLogger(SysUserController.class);

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysTreeService sysTreeService;

    @Autowired
    private SysRoleService sysRoleService;

    @RequestMapping("save.json")
    @ResponseBody
    public JsonData saveUser(@ModelAttribute UserParam param){
        sysUserService.save(param);
        return JsonData.success();
    }

    @RequestMapping("update.json")
    @ResponseBody
    public JsonData updateUser(@ModelAttribute UserParam param){
        sysUserService.update(param);
        return JsonData.success();
    }

    @RequestMapping("page.json")
    @ResponseBody
    public JsonData page(@RequestParam("deptId") int deptId,
                         @ModelAttribute PageQuery pageQuery){
        PageResult result = sysUserService.getPageByDeptId(deptId, pageQuery);
        return JsonData.success(result);
    }

    @RequestMapping("acls.json")
    @ResponseBody
    public JsonData acls(@RequestParam("userId") int userId){
        Map<String, Object> map = Maps.newHashMap();
        map.put("acls", sysTreeService.userAclTree(userId));
        map.put("roles", sysRoleService.getRoleListByUserId(userId));
        return JsonData.success(map);
    }
}
