package com.icode.controller;

import com.icode.common.JsonData;
import com.icode.param.AclModuleParam;
import com.icode.service.interfaces.SysAclModuleService;
import com.icode.service.interfaces.SysTreeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("sys/aclModule")
public class SysAclModuleController {

    @Autowired
    private SysAclModuleService sysAclModuleService;

    @Autowired
    private SysTreeService sysTreeService;

    private static final Logger logger = LoggerFactory.getLogger(SysAclModuleController.class);

    @RequestMapping("acl.page")
    public ModelAndView page(){
        return new ModelAndView("acl");
    }

    @RequestMapping("save.json")
    @ResponseBody
    public JsonData saveAclModule(@ModelAttribute AclModuleParam param){
        sysAclModuleService.save(param);
        return JsonData.success();
    }

    @RequestMapping("update.json")
    @ResponseBody
    public JsonData updateAclModule(@ModelAttribute AclModuleParam param){
        sysAclModuleService.update(param);
        return JsonData.success();
    }

    @RequestMapping("tree.json")
    @ResponseBody
    public JsonData tree(){
        return JsonData.success(sysTreeService.aclModuleTree());
    }

    @RequestMapping("delete.json")
    @ResponseBody
    public JsonData delete(@RequestParam("id") int id){
        sysAclModuleService.delete(id);
        return JsonData.success();
    }
}
