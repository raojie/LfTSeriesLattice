package com.landfoneapi.misposwa;

import android.os.Message;

import com.landfone.mis.bank.ICallBack;
import com.landfoneapi.mispos.E_API_STATE;
import com.landfoneapi.mispos.E_OP_TYPE;
import com.landfoneapi.mispos.E_REQ_RETURN;
import com.landfoneapi.mispos.ILfListener;

//import com.landfone.idemo.LfActivity.MyHandler;

public class LfApi1 {
    protected static E_API_STATE state_api = E_API_STATE.NOT_INIT;
    protected static E_REQ_RETURN state_op = E_REQ_RETURN.REQ_OK;

    protected E_OP_TYPE lastOP = E_OP_TYPE.OP_NONE;

    //private MyHandler myHandler = null;

    protected void sendMessage(Message msg) {
        if (this.mILfListener != null) {
            this.mILfListener.onCallback(msg);
        }
    }

    protected void getCallBack(String code, String tips) {
        if (this.mICallBack != null) {
            this.mICallBack.getCallBack(code, tips);
        }
    }

    private ICallBack mICallBack = null;

    public ICallBack getmICallBack() {
        return mICallBack;
    }

    public void setmICallBack(ICallBack mICallBack) {
        this.mICallBack = mICallBack;
    }

    private ILfListener mILfListener = null;

    public ILfListener getILfListener() {
        return mILfListener;
    }


    public void setILfListener(ILfListener mILfListener) {
        this.mILfListener = mILfListener;
    }

    /**
     * 获取接口当前进行的操作
     *
     * @return
     */
    public E_REQ_RETURN getStateOp() {
        return state_op;
    }


}
