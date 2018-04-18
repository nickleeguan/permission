package com.icode.controller;

import com.icode.common.JsonData;
import com.icode.param.AclParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@RequestMapping("sys/acl")
@Controller
public class SysAclController {
    private static final Logger logger = LoggerFactory.getLogger(SysAclController.class);

    @RequestMapping("save.json")
    @ResponseBody
    public JsonData saveAcl(@ModelAttribute AclParam param){
        return JsonData.success();
    }

    @RequestMapping("update.json")
    @ResponseBody
    public JsonData updateAcl(@ModelAttribute AclParam param){

        return JsonData.success();
    }
}
