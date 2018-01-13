package com.landfoneapi.mispos;

/**
 * Created by thinkpad on 2015/12/18.
 */
public enum E_ERR_CODE {
    ERR_UNKNOW(0,"未知"),//ERR_UNKNOW
    ERR_SERIAL_ERROR(1,"串口错误"),//ERR_SERIAL_ERROR
    ERR_POS_NOT_INIT(2,"未初始化"),//ERR_POS_NOT_INIT
    ERR_POS_NOT_SIGNIN(3,"未签到"),//ERR_POS_NOT_SIGNIN
    ERR_OTHRER(4,"其它错误"),//ERR_OTHRER
    ERR_KEYBOARD_INPUT_FINISH(5,"按键输入结束"),//ERR_KEYBOARD_INPUT_FINISH
    ERR_READ_CARD_CANCEL(6,"读卡取消"),//ERR_READ_CARD_CANCEL
    ERR_TIME_OUT(7,"超时"),//ERR_READ_CARD_CANCEL
    ERR_CANCEL(8,"交易取消"),
    ERR_PARAMS_INVALID(9,"参数错误");
    private int err = 0;
    private String desc = "";
    E_ERR_CODE(int err, String desc){
        this.err = err;
        this.desc = desc;
    }
    public String getDesc(){
        return this.desc;
    }
}
