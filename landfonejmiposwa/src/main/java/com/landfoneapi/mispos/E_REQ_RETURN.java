package com.landfoneapi.mispos;

/**
 * Created by thinkpad on 2015/12/18.
 */
public enum E_REQ_RETURN {
    REQ_OK(0, "请求发出"),//REQ_OK
    REQ_DENY(1, "请求拒绝"),//REQ_DENY
    REQ_BUSY(2, "接口忙");//REQ_BUSY
    private int rst = 0;
    private String desc = "";
    private boolean synch = false;
    private Object obj = null;

    E_REQ_RETURN(int rst, String desc) {
        this.rst = rst;
        this.desc = desc;
    }

    public String getDesc() {
        return this.desc;
    }

    public int getRst() {
        return this.rst;
    }

    public void setObj(Object pobj) {
        this.obj = pobj;
    }

    public Object getObj() {
        return this.obj;
    }

    ;
}
