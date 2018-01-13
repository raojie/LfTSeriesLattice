package com.landfoneapi.mispos;

import com.landfoneapi.mispos.Display;
import com.landfoneapi.mispos.E_API_STATE;
import com.landfoneapi.mispos.E_ERR_CODE;
import com.landfoneapi.mispos.E_OP_TYPE;

/**
 * Created by thinkpad on 2015/12/18.
 */
public class CallbackMsg {
    /**
     * 返回结果的操作类型
     */
    public E_OP_TYPE op_type = E_OP_TYPE.OP_NONE;
    /**
     * 返回状态，0成功，1失败
     */
    public int reply = 0;//
    /**
     * 返回失败（reply==1）时的错误码（如果失败reply==1）
     */
    public E_ERR_CODE nak_err_code = E_ERR_CODE.ERR_UNKNOW;
    /**
     * api状态
     */
    public E_API_STATE api_state = E_API_STATE.NOT_INIT;
    /**
     * 返回的信息(交易/查询/...等等信息)
     */
    public String info = "";
    /**
     * 网络状态（如果有，在查询网络状态时返回）
     */
    public int net_state = 0;
    /**
     * 微信支付宝订单状态（如果有）,0-成功，1-处理中
     */
    public int wa_order_state = 0;
    /**
     * 微信支付宝的二维码信息（如果有，预下单成功时）
     */
    public String qrcode = "";
    /**
     * POS显示数据（如果有，如提示刷卡/插卡等等）
     */
    public Display dsp = null;

    //返回的解析对象
    public Object mREPLY = null;

    public Object obj = null;
    /**
     * 倒计时
     */
    public int countDown = 0;
    ////////////////////////////////////////主控通讯流程回调//////////////////////////////////////////////////////////////////
    public int dataServerState = 1;//默认1-connect_fail
    public int paymentState = 0;
    public CallbackMsg(E_ERR_CODE nak_err_code,E_OP_TYPE op_type,int reply, int dataServerState, int paymentState,Display dsp){
        this.nak_err_code = nak_err_code;
        this.op_type = op_type;
        this.reply = reply;
        this.dataServerState = dataServerState;
        this.paymentState = paymentState;
        this.dsp = dsp;
    }

    public CallbackMsg(E_ERR_CODE nak_err_code,E_OP_TYPE op_type,int countDown){
        this.nak_err_code = nak_err_code;
        this.op_type = op_type;
        this.countDown = countDown;
    }

    ////////////////////////////////////////////支付功能回调////////////////////////////////////////////////////////////////
    public CallbackMsg(E_ERR_CODE nak_err_code,E_OP_TYPE op_type,int reply,E_API_STATE api_state,String info){

        this.nak_err_code = nak_err_code;
        this.op_type = op_type;
        this.reply = reply;
        this.api_state = api_state;
        this.info = info;
    }
    public CallbackMsg(E_ERR_CODE nak_err_code,E_OP_TYPE op_type,int reply,E_API_STATE api_state,String info,Object mREPLY,Display dsp){

        this.nak_err_code = nak_err_code;
        this.op_type = op_type;
        this.reply = reply;
        this.api_state = api_state;
        this.info = info;
        this.mREPLY = mREPLY;
        this.obj = mREPLY;
        this.dsp = dsp;
    }

    public CallbackMsg(E_ERR_CODE nak_err_code,E_OP_TYPE op_type,int reply,E_API_STATE api_state,String info
            ,String qrcode){
        this.nak_err_code = nak_err_code;
        this.op_type = op_type;
        this.reply = reply;
        this.api_state = api_state;
        this.info = info;
        this.qrcode = qrcode;
    }
    public CallbackMsg(E_ERR_CODE nak_err_code,E_OP_TYPE op_type,int reply,E_API_STATE api_state,String info
            ,Display dsp){
        this.nak_err_code = nak_err_code;
        this.op_type = op_type;
        this.reply = reply;
        this.api_state = api_state;
        this.info = info;
        this.dsp = dsp;
    }
    public CallbackMsg(E_ERR_CODE nak_err_code,E_OP_TYPE op_type,int reply,E_API_STATE api_state,String info
            ,int pint){
        this.nak_err_code = nak_err_code;
        this.op_type = op_type;
        this.reply = reply;
        this.api_state = api_state;
        this.info = info;
        if(op_type == E_OP_TYPE.OP_3G_STATE){
            this.net_state = pint;
        }else{
            this.wa_order_state = pint;
        }

    }
}
