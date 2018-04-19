package com.icode.dto;

import com.icode.model.SysAcl;
import org.springframework.beans.BeanUtils;

public class AclDto extends SysAcl {

    //是否默认选中
    private boolean checked = false;

    //是否有权限操作
    private boolean hasAcl = false;

    public static AclDto adapt(SysAcl acl){
        AclDto dto = new AclDto();

        BeanUtils.copyProperties(acl, dto);
        return dto;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isHasAcl() {
        return hasAcl;
    }

    public void setHasAcl(boolean hasAcl) {
        this.hasAcl = hasAcl;
    }
}
