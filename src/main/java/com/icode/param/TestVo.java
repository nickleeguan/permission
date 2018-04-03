package com.icode.param;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class TestVo {

    @NotBlank
    private String msg;

    @NotNull(message = "ID can not be null")
    @Max(value = 10, message = "id can not bigger than 10")
    @Min(0)
    private Integer id;


    private List<String> strs;
}
