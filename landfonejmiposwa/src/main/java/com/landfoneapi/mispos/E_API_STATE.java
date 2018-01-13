package com.landfoneapi.mispos;

/**
 * Created by thinkpad on 2015/12/18.
 */
public enum E_API_STATE {
    UNKNOW(0,"未知"),//UNKNOW,
    NOT_INIT(1,"未初始化"),//NOT_INIT,
    INIT_OK(2,"初始化成功"),//INIT_OK,
    NOT_SIGNIN(3,"未签到"),//NOT_SIGNIN,
    SIGNIN_OK(4,"签到成功");//SIGNIN_OK,
    private int st = 0;
    private String desc = "";
    E_API_STATE(int st, String desc){
        this.st = st;
        this.desc = desc;
    }
    public String getDesc(){
        return this.desc;
    }
}
