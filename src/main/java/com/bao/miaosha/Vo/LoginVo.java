package com.bao.miaosha.Vo;

import com.bao.miaosha.validator.IsMobile;

import javax.validation.constraints.NotBlank;

public class LoginVo {

    @NotBlank(message = "手机号码不能为空")
    @IsMobile(required = true)
    private String mobile;

    @NotBlank(message = "密码不能为空")
    private String password;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
