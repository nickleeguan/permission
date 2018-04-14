package com.icode.controller;

import com.icode.beans.PageQuery;
import com.icode.beans.PageResult;
import com.icode.common.JsonData;
import com.icode.param.UserParam;
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

@Controller
@RequestMapping("sys/user")
public class SysUserController {
    private static final Logger logger = LoggerFactory.getLogger(SysUserController.class);

    @Autowired
    private SysUserService sysUserService;

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
}
