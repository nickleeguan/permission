package com.icode.dto;

import com.google.common.collect.Lists;
import com.icode.model.SysDept;
import org.springframework.beans.BeanUtils;

import java.util.List;

public class DeptLevelDto extends SysDept{

    private List<DeptLevelDto> deptList = Lists.newArrayList();

    public static DeptLevelDto adapt(SysDept dept){
        DeptLevelDto dto = new DeptLevelDto();
        BeanUtils.copyProperties(dept, dto);

        return dto;
    }

    public List<DeptLevelDto> getDeptList() {
        return deptList;
    }

    public void setDeptList(List<DeptLevelDto> deptList) {
        this.deptList = deptList;
    }
}
