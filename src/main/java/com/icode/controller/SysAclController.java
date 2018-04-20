package com.icode.controller;

import com.google.common.collect.Maps;
import com.icode.beans.PageQuery;
import com.icode.beans.PageResult;
import com.icode.common.JsonData;
import com.icode.model.SysAcl;
import com.icode.model.SysRole;
import com.icode.model.SysUser;
import com.icode.param.AclParam;
import com.icode.service.interfaces.SysAclService;
import com.icode.service.interfaces.SysRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;


@RequestMapping("sys/acl")
@Controller
public class SysAclController {
    private static final Logger logger = LoggerFactory.getLogger(SysAclController.class);

    @Autowired
    private SysAclService sysAclService;

    @Autowired
    private SysRoleService sysRoleService;

    @RequestMapping("save.json")
    @ResponseBody
    public JsonData saveAcl(@ModelAttribute AclParam param){
        sysAclService.saveAcl(param);
        return JsonData.success();
    }

    @RequestMapping("update.json")
    @ResponseBody
    public JsonData updateAcl(@ModelAttribute AclParam param){
        sysAclService.updateAcl(param);
        return JsonData.success();
    }

    @RequestMapping("page.json")
    @ResponseBody
    public JsonData list(@RequestParam("aclModuleId") Integer aclModuleId,
                         PageQuery pageQuery){
        PageResult<SysAcl> result = sysAclService.getPageByAclModuleId(aclModuleId, pageQuery);
        return JsonData.success(result);
    }

    @RequestMapping("acls.json")
    @ResponseBody
    public JsonData acls(@RequestParam("aclId") int aclId){
        Map<String, Object> map = Maps.newHashMap();
        List<SysRole> roleList = sysRoleService.getRoleListByAclId(aclId);

        map.put("roles", roleList);
        map.put("users", sysRoleService.getUserListByRoleList(roleList));
        return JsonData.success(map);
    }
}
