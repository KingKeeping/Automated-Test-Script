package com.get.automation.constants;

public class KloginConstant {
    private KloginConstant(){
        throw new UnsupportedOperationException("Constants class cannot be instantiated");
    }

    public static final String User="#normal_login_appId";
    public static final String Password="//*[@id=\"normal_login_password\"]";
    public static final String LoginButton="//*[@id=\"components-form-demo-normal-login\"]/div[5]/div/div/span/button";

}
//*[@id="normal_login_appId"]