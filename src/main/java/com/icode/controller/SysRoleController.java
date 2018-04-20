package com.icode.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.icode.common.JsonData;
import com.icode.model.SysUser;
import com.icode.param.RoleParam;
import com.icode.service.interfaces.*;
import com.icode.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("sys/role")
public class SysRoleController {

    private static final Logger logger = LoggerFactory.getLogger(SysRoleController.class);

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysTreeService sysTreeService;

    @Autowired
    private SysRoleAclService sysRoleAclService;

    @Resource
    private SysRoleUserService sysRoleUserService;

    @Autowired
    private SysUserService sysUserService;


    @RequestMapping("role.page")
    public ModelAndView page(){
        return new ModelAndView("role");
    }

    @RequestMapping("save.json")
    @ResponseBody
    public JsonData saveRole(@ModelAttribute RoleParam param){
        sysRoleService.saveRole(param);
        return JsonData.success();
    }

    @RequestMapping("update.json")
    @ResponseBody
    public JsonData updateRole(@ModelAttribute RoleParam param){
        sysRoleService.updateRole(param);
        return JsonData.success();
    }

    @RequestMapping("list.json")
    @ResponseBody
    public JsonData list(){
        return JsonData.success(sysRoleService.getAll());
    }

    @RequestMapping("roleTree.json")
    @ResponseBody
    public JsonData roleTree(@RequestParam("roleId") int roleId){

        return JsonData.success(sysTreeService.roleTree(roleId));
    }

    @RequestMapping("changeAcls.json")
    @ResponseBody
    public JsonData changeAcls(@RequestParam("roleId") int roleId,
                               @RequestParam(value = "aclIds", required = false, defaultValue = "") String aclIds){
        List<Integer> aclIdList = StringUtil.splitToListInt(aclIds);

        sysRoleAclService.changeRoleAcls(roleId, aclIdList);
        return JsonData.success();
    }

    @RequestMapping("changeUsers.json")
    @ResponseBody
    public JsonData changeUsers(@RequestParam("roleId") int roleId,
                               @RequestParam(value = "userIds", required = false, defaultValue = "") String userIds){
        List<Integer> userIdList = StringUtil.splitToListInt(userIds);

        sysRoleUserService.changeRoleUsers(roleId, userIdList);
        return JsonData.success();
    }

    @RequestMapping("users.json")
    @ResponseBody
    public JsonData users(@RequestParam("roleId") int roleId){
        List<SysUser> selectedUserList = sysRoleUserService.getListByRoleId(roleId);
        List<SysUser> allUserList = sysUserService.getAll();
        List<SysUser> unSelectedUserList = Lists.newArrayList();

        Set<Integer> selectedUserIdSet =
                selectedUserList.stream().map(sysUser -> sysUser.getId()).collect(Collectors.toSet());

        allUserList.forEach(sysUser -> {
            if (sysUser.getStatus() == 1 && !selectedUserIdSet.contains(sysUser.getId())){
                unSelectedUserList.add(sysUser);
            }
        });
//        selectedUserList  =
//                selectedUserList.stream().filter(sysUser -> sysUser.getStatus() != 1).collect(Collectors.toList());

        Map<String, List<SysUser>> map = Maps.newHashMap();
        map.put("selected", selectedUserList);
        map.put("unselected", unSelectedUserList);
        return JsonData.success(map);
    }
}
