package com.icode.controller;

import com.icode.common.JsonData;
import com.icode.dto.DeptLevelDto;
import com.icode.param.DeptParam;
import com.icode.service.interfaces.SysDeptService;
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

import java.util.List;


@Controller
@RequestMapping("sys/dept")
public class SysDeptController {
    private static final Logger logger = LoggerFactory.getLogger(SysDeptController.class);

    @Autowired
    private SysDeptService sysDeptService;

    @Autowired
    private SysTreeService sysTreeService;

    @RequestMapping("dept.page")
    public ModelAndView page(){
        return new ModelAndView("dept");
    }

    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData saveDept(@ModelAttribute DeptParam param){
        sysDeptService.save(param);
        return JsonData.success();
    }

    @RequestMapping("/tree.json")
    @ResponseBody
    public JsonData tree(){
        List<DeptLevelDto> dtoList = sysTreeService.deptTree();
        return JsonData.success(dtoList);
    }

    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData updateDept(@ModelAttribute DeptParam param){
        sysDeptService.update(param);
        return JsonData.success();
    }

    @RequestMapping("delete.json")
    @ResponseBody
    public JsonData delete(@RequestParam("id") int id){
        sysDeptService.delete(id);
        return JsonData.success();
    }
}
