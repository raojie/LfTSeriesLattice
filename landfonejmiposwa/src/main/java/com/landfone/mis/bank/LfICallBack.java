package com.landfone.mis.bank;

/**
 * Created by asus on 2017/3/16.
 */

public interface LfICallBack extends ICallBack {

    public abstract void getCallBack(String stateCode, String stateTips);

}
