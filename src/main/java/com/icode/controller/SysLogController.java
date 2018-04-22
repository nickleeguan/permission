package com.icode.controller;

import com.icode.model.SysDept;
import com.icode.service.interfaces.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("sys/log")
public class SysLogController {

    @Autowired
    private SysLogService sysLogService;


}
