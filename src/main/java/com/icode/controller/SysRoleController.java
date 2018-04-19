package com.icode.controller;

import com.icode.common.JsonData;
import com.icode.param.RoleParam;
import com.icode.service.interfaces.SysRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("sys/role")
public class SysRoleController {

    private static final Logger logger = LoggerFactory.getLogger(SysRoleController.class);

    @Autowired
    private SysRoleService sysRoleService;

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
}
