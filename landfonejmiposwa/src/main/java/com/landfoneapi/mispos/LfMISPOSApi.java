package com.landfoneapi.mispos;

import android.content.Context;
import android.os.Message;

import com.landfone.common.utils.Logz;
import com.landfone.mis.bean.TransCfx;
import com.landfoneapi.protocol.LfPosApiMispos;
import com.landfoneapi.protocol.iface.IDisplay;
import com.landfone.common.utils.IUserCallback;
import com.landfoneapi.protocol.pkg.REPLY;
import com.landfoneapi.protocol.pkg.TradeType;
import com.landfoneapi.protocol.pkg._03_Common;
import com.landfoneapi.protocol.pkg._03_GetRecord;
import com.landfoneapi.protocol.pkg._03_Purchase;
import com.landfoneapi.protocol.pkg.junpeng._03_Recharge;
import com.landfoneapi.protocol.pkg.junpeng._03_Refund;
import com.landfoneapi.protocol.pkg._03_RefundPurchase;
import com.landfoneapi.protocol.pkg._04_GetSettleInfoReply;
import com.landfoneapi.protocol.pkg._04_QueryReply;
import com.landfone.common.utils.Errs;
import com.landfone.common.utils.LfException;

/**
 * pos操作的接口调用实现，Service中调用
 */
public class LfMISPOSApi {
    private String TAG = this.getClass().getSimpleName();

    public static final String OP_INIT = "INIT";
    public static final String OP_RELEASE = "RELE";
    public static final String OP_PURCHASE = "PURC";
    public static final String OP_SIGNIN = "SIGN";
    public static final String OP_SETTLE = "SETT";
    public static final String OP_QUERY = "QUER";
    public static final String OP_UPLOAD = "UPLO";
    public static final String OP_CONFG = "CONF";
    public static final String OP_GETRECORD = "GETR";
    public static final String OP_GETSETTLEINFO = "GETS";
    public static final String OP_RECHARGE = "RECH";

    public static final String OP_REFUND = "REFD";
    public static final String OP_STATEQUERY = "STQR";
    public static final String OP_PING = "PING";

    private static byte[] lockApi = new byte[0];
    //基本参数
    private String pospIP = "218.20.222.1";
    private int pospPort = 7001;

    //POS接口API对象
    private LfPosApiMispos pmLfPosApiMispos = new LfPosApiMispos();

    protected static E_API_STATE state_api = E_API_STATE.NOT_INIT;
    protected static E_REQ_RETURN state_op = E_REQ_RETURN.REQ_OK;

    /**
     * 期望进行op的操作，先进行api状态的判断；ignor用于屏蔽某一个判断
     *
     * @param op
     * @param ignor
     * @return
     */
    private boolean CheckApiState(E_OP_TYPE op, E_API_STATE ignor) {
        boolean ret = true;
        denyErrCode = ErrCode._Z2;
        if (state_api == E_API_STATE.NOT_INIT && ignor != state_api) {
            denyErrCode = ErrCode._Z7;
        } else if (state_api == E_API_STATE.NOT_SIGNIN && ignor != state_api) {
            denyErrCode = ErrCode._Z8;
            System.out.println(">>>CheckApiState ERR_POS_NOT_SIGNIN,1");
        } else {
            ret = false;
        }
        return ret;
    }

    private ErrCode CheckMisPosLfException(LfException e) {
        ErrCode ret;
        if (e.getErrcode() == Errs.SERIAL_OPEN_ERR.getValue()
                || e.getErrcode() == Errs.SERIAL_IFACE_NULL.getValue()
                || e.getErrcode() == Errs.SERIAL_TIME_OUT.getValue()
                || e.getErrcode() == Errs.SERIAL_IO_ERR.getValue()) {
            ret = ErrCode._Z6;
        } else if (e.getErrcode() == Errs.PARAMS_INVALID.getValue()) {
            ret = ErrCode._PR;
        } else {
            ret = ErrCode._Z9;
        }
        return ret;
    }

    //TODO:通过重新定义的回调返回给用户，更简洁一点
    private IUserCallback mIUserCallback = null;
    private Display dpl = null;

    private void onProccess(Display pdpl) {
        if (mIUserCallback != null && pdpl != null) {
            dpl = pdpl;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mIUserCallback.onProcess(dpl);
                }
            }).start();
        }
    }

    private REPLY rst = null;

    private void onResult(REPLY prst) {
        if (mIUserCallback != null && prst != null) {
            rst = prst;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (rst.op == null || rst.op.length() < 1) {
                        rst.op = opflag;
                    }
                    mIUserCallback.onResult(rst);
                }
            }).start();
        }
    }

    private void onResult(String op, ErrCode code) {
        REPLY tmp = new REPLY();
        tmp.op = op;
        tmp.code = code.getCode();
        tmp.code_info = code.getDesc();
        onResult(tmp);
    }

    private void onResult(ErrCode code) {
        onResult(opflag, code);
    }

    private void onSuccess() {
        onResult(ErrCode._00);
    }

    private void onBusy(String op) {
        onResult(op, ErrCode._Z3);
    }

    private ErrCode denyErrCode = ErrCode._Z7;

    private void onDeny(String op) {
        onResult(op, denyErrCode);
    }

    private String opflag = "";

    /**
     * 设置交易通道类型
     * 0会员卡；
     * 1银行卡；
     * 92杉德会员卡；
     * 93杉德O2O;
     */
    public void pos_setType(int type) {
        if (type == 0) {
            pmLfPosApiMispos.setType((byte) 0xF0);//会员卡
        } else if (type == 1) {
            pmLfPosApiMispos.setType((byte) 0x01);//银联卡
        } else if (type == 2) {
            pmLfPosApiMispos.setType((byte) 0x02);//pos通
        } else {
            pmLfPosApiMispos.setType((byte) 0xF0);
        }
    }

    public int pos_getType() {
        byte type = pmLfPosApiMispos.getType();
        if (type == (byte) 0xF0) {
            return 0;
        } else if (type == (byte) 0x01) {
            return 1;
        } else if (type == (byte) 0x02) {
            return 2;
        } else return -1;
    }

    /**
     * 初始化函数
     *
     * @param pospIP POSP服务器IP，目前【测试】用"218.20.222.1",		需要可配置/保存/读取
     * @param port   POSP服务器端口，目前【测试】用7001,				需要可配置/保存/读取
     *               <p/>
     *               String updateIP,int updatePort
     */
    public void pos_init(String pospIP, int port, String comPath, String comBaud, IUserCallback lsn) {//String pospUrl,
        mIUserCallback = lsn;

        if (state_op == E_REQ_RETURN.REQ_BUSY) {
            onBusy(OP_INIT);
        } else {
            opflag = OP_INIT;
            synchronized (lockApi) {
                state_op = E_REQ_RETURN.REQ_BUSY;
                this.pospIP = pospIP;
                this.pospPort = port;
                //设置dbg输出
                pmLfPosApiMispos.EnableDBG();
                //pmLfPosApiSimple.DisableDBG();
                //设置串口接口,null为默认的串口jni
                pmLfPosApiMispos.setISerialPort(null);
                //设置信息提示回调接口
                pmLfPosApiMispos.setIDisplay(mIDisplay);
                //设置串口参数
                pmLfPosApiMispos.setPara(comPath, comBaud);//("/dev/ttyS1", "9600");//目前的安卓板固定为这个串口参数
                pmLfPosApiMispos.setServer(this.pospIP, this.pospPort);
                pmLfPosApiMispos.setAutoSettle(true);//自动结算
                pmLfPosApiMispos.setType((byte) 0x01);//会员卡

                try {
                    pmLfPosApiMispos.init();
                    state_api = E_API_STATE.INIT_OK;
                    onSuccess();
                } catch (LfException e) {
                    Logz.v(TAG, e.getMessage());
                    onResult(ErrCode._CO);//ERR_SERIAL_ERROR
                }
                state_op = E_REQ_RETURN.REQ_OK;
            }
        }
    }

    //退出,释放
    public void pos_release() {
        if (state_op == E_REQ_RETURN.REQ_BUSY) {
            onBusy(OP_RELEASE);
        } else {
            opflag = OP_RELEASE;
            synchronized (lockApi) {
                state_op = E_REQ_RETURN.REQ_BUSY;
                Message msg = new Message();
                pmLfPosApiMispos.release();
                onSuccess();
                state_op = E_REQ_RETURN.REQ_OK;
            }
        }
    }

    public void pos_setKeyCert(Context context, boolean isSSL, String trust_path) {
        synchronized (lockApi) {
            pmLfPosApiMispos.setKeyCert(context, isSSL, trust_path);
            state_op = E_REQ_RETURN.REQ_OK;
        }
    }

    public void pos_setHardEncrypt(Context context, boolean isHard, String trust_path) {
        synchronized (lockApi) {
            pmLfPosApiMispos.setHardEncrypt(context, isHard, trust_path);
            state_op = E_REQ_RETURN.REQ_OK;
        }
    }

    public void setTransCfx(TransCfx transCfx) {
        pmLfPosApiMispos.setTransCfx(transCfx);
        pmLfPosApiMispos.getProperties();
    }

    private Thread mMyApiTask = null;
    private boolean querying = false;
    private boolean useOfflineTrade = true;

    //////////////////////////////////////////////////////////
    protected E_OP_TYPE lastOP = E_OP_TYPE.OP_NONE;
    private E_OP_TYPE op_type = E_OP_TYPE.OP_NONE;
    private String gr_mer = "000000000000000";
    private String gr_tmn = "00000000";
    private String gr_poswater = "";
    private String specTmpSerial = "";
    private int amount_fen = 1;
    private String CardNo = "";

    private void pos_base(E_OP_TYPE opType) {
        if (state_op == E_REQ_RETURN.REQ_BUSY || pmLfPosApiMispos._IsBusy()) {
            onBusy(opType.getDesc());
        } else if (CheckApiState(E_OP_TYPE.OP_PRO_POS_REFUND, E_API_STATE.UNKNOW)) {
            System.out.println("return deny");
            onDeny(opType.getDesc());
        } else {
            opflag = opType.getDesc();
            synchronized (lockApi) {
                state_op = E_REQ_RETURN.REQ_BUSY;
                op_type = opType;

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        _03_Common t = new _03_Common();
                        REPLY mREPLY = new REPLY();
                        mREPLY.reply = 1;//默认失败
                        mREPLY.code = "LF";
                        mREPLY.code_info = "请求失败";

                        String amount_str = String.format("%012d", amount_fen);
                        lastOP = op_type;

                        if (amount_fen < 1) {
                            mREPLY.code_info = "金额不能小于1分";
                            mREPLY.code = ErrCode._Z9.getCode();
                            onResult(mREPLY);
                            state_op = E_REQ_RETURN.REQ_OK;
                        } else {
                            try {
                                if (op_type == E_OP_TYPE.OP_POS_SIGNIN) {
                                    t.setTradeType(TradeType.SIGNIN);
                                    mREPLY = pmLfPosApiMispos._03_Signin(t);
                                } else if (op_type == E_OP_TYPE.OP_POS_PURCHASE) {
                                    t = new _03_Purchase();
                                    t.setTradeType(TradeType.PURCHASE);
                                    ((_03_Purchase) t).setAmount(amount_str);
                                    mREPLY = pmLfPosApiMispos._03_Trade(t);
                                } else if (op_type == E_OP_TYPE.OP_POS_REFUND) {
                                    if (pos_getType() == 0) {
                                        t = new _03_Refund();//设置请求参数
                                        t.setTradeType(TradeType.REFUND);//交易类型
                                        ((_03_Refund) t).setCardNo(CardNo);
                                        ((_03_Refund) t).setAmount(amount_str);//金额，单位：分
                                        ((_03_Refund) t).setSpecTmnSerial(specTmpSerial);
                                        Logz.i(TAG, "会员卡退款");
                                        mREPLY = pmLfPosApiMispos._03_Refund(t);//发起请求
                                    } else if (pos_getType() == 1) {
                                        t = new _03_RefundPurchase();
                                        t.setTradeType(TradeType.REFUNDPURCHASE);
                                        ((_03_RefundPurchase) t).setMer(gr_mer);
                                        ((_03_RefundPurchase) t).setTmn(gr_tmn);
                                        ((_03_RefundPurchase) t).setAmount(amount_str);
                                        ((_03_RefundPurchase) t).setVoucher(specTmpSerial);
                                        Logz.i(TAG, "银行卡退款");
                                        mREPLY = pmLfPosApiMispos._03_RefundPurchase(t);
                                    }
                                } else if (op_type == E_OP_TYPE.OP_POS_GETRECORD) {
                                    t = new _03_GetRecord();
                                    t.setTradeType(TradeType.GET_RECORD);
                                    t.setMer(gr_mer);
                                    t.setTmn(gr_tmn);
                                    ((_03_GetRecord) t).setSerialNo(gr_poswater);
                                    mREPLY = pmLfPosApiMispos._03_GetRecordReply(t);//发起请求
                                } else if (op_type == E_OP_TYPE.OP_POS_SETTLE) {
                                    t.setTradeType(TradeType.SETTLE);
                                    mREPLY = pmLfPosApiMispos._03_Settle(t);
//                                    if (mREPLY.reply == MISPOS.PACK_ACK) {
//                                        state_api = E_API_STATE.NOT_SIGNIN;
//                                    }
//                                    if (isSigninAfterSettled) {
                                    //结算后不主动进行签到
                                    t.setTradeType(TradeType.SIGNIN);
                                    mREPLY = pmLfPosApiMispos._03_Signin(t);
//                                    }
                                } else if (op_type == E_OP_TYPE.OP_MEMBER_RECHARGE) {
                                    t = new _03_Recharge();
                                    t.setTradeType(TradeType.RECHARGE);
                                    ((_03_Recharge) t).setAmount(amount_str);
                                    mREPLY = pmLfPosApiMispos._03_Recharge(t);//发起请求
                                } else {
                                    mREPLY.code = "";
                                    mREPLY.code_info = "无接口";
                                    Logz.i(TAG, "call  NONE !!!!!");
                                }
                                Logz.v(TAG, " done:" + (int) mREPLY.reply + "," + mREPLY.code + ", " + mREPLY.code_info);
                                if (mREPLY.reply == MISPOS.PACK_ACK) {
                                    state_api = E_API_STATE.SIGNIN_OK;
                                }
                                onResult(mREPLY);
                            } catch (LfException e) {
                                mREPLY.code = "ER";
                                mREPLY.code_info = e.getMessage();
                                Logz.v("POS", e.getMessage());
                                onResult(CheckMisPosLfException(e));
                            } finally {
                                state_op = E_REQ_RETURN.REQ_OK;
                            }
                        }
                    }
                }).start();

            }
        }
    }

    /**
     * 签到，只有签到成功后才可以进行交易
     */
    public void pos_signin(IUserCallback lsn) {
        this.mIUserCallback = lsn;
        pos_base(E_OP_TYPE.OP_POS_SIGNIN);
    }
//    public void pos_signin(IUserCallback lsn) {
//        mIUserCallback = lsn;
//        if (state_op == E_REQ_RETURN.REQ_BUSY) {
//            onBusy(OP_SIGNIN);
//        } else if (CheckApiState(E_OP_TYPE.OP_POS_SIGNIN, E_API_STATE.NOT_SIGNIN)) {//无视未签到状态
//            onDeny(OP_SIGNIN);
//        } else {
//            opflag = OP_SIGNIN;
//            synchronized (lockApi) {
//                state_op = E_REQ_RETURN.REQ_BUSY;
//                //异步调用
//                new Thread(new Runnable() {
//                    public void run() {
//                        _03_Common t = new _03_Common();
//                        REPLY mREPLY = new REPLY();
//                        lastOP = E_OP_TYPE.OP_POS_SIGNIN;
//                        try {
//                            //设置请求参数
//                            t.setTradeType(TradeType.SIGNIN);//交易类型
//                            mREPLY = pmLfPosApiMispos._03_Signin(t);//发起请求
//                            //设置正常返回结果
//                            if (mREPLY.reply == MISPOS.PACK_ACK) {
//                                state_api = E_API_STATE.SIGNIN_OK;
//                            }
//                            onResult(mREPLY);
//                        } catch (LfException e) {
//                            mREPLY.code = "ER";
//                            mREPLY.code_info = e.getMessage();
//                            Log.v(TAG, e.getMessage());
//                            onResult(CheckMisPosLfException(e));
//                        } finally {
//                            state_op = E_REQ_RETURN.REQ_OK;
//                        }
//                    }
//                }).start();
//            }
//        }
//    }

    private boolean isSigninAfterSettled = false;

    /**
     * 结算，需要重新签到
     * 操作类型与签到共用
     */
    public void pos_settle(IUserCallback lsn) {
        this.mIUserCallback = lsn;
        pos_base(E_OP_TYPE.OP_POS_SETTLE);
    }
//    public void pos_settle(IUserCallback lsn) {
//        mIUserCallback = lsn;
//        if (state_op == E_REQ_RETURN.REQ_BUSY) {
//            onBusy(OP_SETTLE);
//        } else if (isSigninAfterSettled) {
//            if (CheckApiState(E_OP_TYPE.OP_POS_SIGNIN, E_API_STATE.NOT_SIGNIN)) {//无视未签到状态
//                onDeny(OP_SIGNIN);
//            }
//        } else if (CheckApiState(E_OP_TYPE.OP_POS_SETTLE, E_API_STATE.NOT_SIGNIN)) {//无视未签到状态
//            onDeny(OP_SETTLE);
//        } else {
//            opflag = OP_SETTLE;
//            synchronized (lockApi) {
//                state_op = E_REQ_RETURN.REQ_BUSY;
//                //异步调用
//                new Thread(new Runnable() {
//                    public void run() {
//                        _03_Common t = new _03_Common();
//                        REPLY mREPLY = new REPLY();
//                        lastOP = E_OP_TYPE.OP_POS_SETTLE;
//                        try {
//                            //设置请求参数
//                            t.setTradeType(TradeType.SETTLE);//交易类型
//                            mREPLY = pmLfPosApiMispos._03_Settle(t);//发起结算
//                            if (mREPLY.reply == MISPOS.PACK_ACK) {
//                                state_api = E_API_STATE.NOT_SIGNIN;
//                            }
//                            if (isSigninAfterSettled) {
//                                //结算后不主动进行签到
//                                //设置请求参数
//                                t.setTradeType(TradeType.SIGNIN);//交易类型
//                                mREPLY = pmLfPosApiMispos._03_Signin(t);//结算之后进行签到
//                                if (mREPLY.reply == MISPOS.PACK_ACK) {
//                                    state_api = E_API_STATE.SIGNIN_OK;
//                                }
//                            }
//                            onResult(mREPLY);
//                        } catch (LfException e) {
//                            mREPLY.code = "ER";
//                            mREPLY.code_info = e.getMessage();
//                            Log.v(TAG, e.getMessage());
//                            onResult(CheckMisPosLfException(e));
//                        } finally {
//                            state_op = E_REQ_RETURN.REQ_OK;
//                        }
//                    }
//                }).start();
//            }
//        }
//    }


    /**
     * 支付
     *
     * @param amount 以分为单位！！！
     */
    public void pos_purchase(int amount, int type, IUserCallback lsn) {
        pos_setType(type);
        this.amount_fen = amount;
        this.mIUserCallback = lsn;
        pos_base(E_OP_TYPE.OP_POS_PURCHASE);
    }
//    public void pos_purchase(int amount, int type, IUserCallback lsn) {
//        mIUserCallback = lsn;
//        pos_setType(type);//新增,设置会员卡退款类型
//        if ((state_op == E_REQ_RETURN.REQ_BUSY || pmLfPosApiMispos._IsBusy())) {
//            onBusy(OP_PURCHASE);
//        } else if (CheckApiState(E_OP_TYPE.OP_POS_PURCHASE, E_API_STATE.UNKNOW)) {
//            System.out.println("return deny");
//            onDeny(OP_PURCHASE);
//        } else {
//            opflag = OP_PURCHASE;
//            synchronized (lockApi) {
//                state_op = E_REQ_RETURN.REQ_BUSY;
//
//                this.amount_fen = amount;
//                //异步调用
//                new Thread(new Runnable() {
//                    public void run() {
//                        _04_PurchaseReply mREPLY = new _04_PurchaseReply();
//                        mREPLY.reply = 1;//默认失败
//                        mREPLY.code = "LF";
//                        mREPLY.code_info = "请求失败";
//
//                        _03_Purchase t = new _03_Purchase();
//
//                        String amount_str = String.format("%012d", amount_fen);
//                        lastOP = E_OP_TYPE.OP_POS_PURCHASE;
//                        if (amount_fen < 1) {
//                            mREPLY.code_info = "金额不能小于1分";
//                            mREPLY.code = ErrCode._Z9.getCode();
//                            onResult(mREPLY);
//                            state_op = E_REQ_RETURN.REQ_OK;
//                        } else {
//                            try {
//                                //设置请求参数
//                                t.setTradeType(TradeType.PURCHASE);//交易类型
//                                Log.v(TAG, "amount_str:" + amount_str);
//                                t.setAmount(amount_str);//金额，单位：分
//                                Log.v(TAG, "call trade...");
//                                mREPLY = pmLfPosApiMispos._03_Trade(t);//发起请求
//                                Log.v(TAG, "trade done:" + (int) mREPLY.reply + "," + mREPLY.code + ", " + mREPLY.code_info);
//                                if (mREPLY.reply == MISPOS.PACK_ACK) {
//                                    state_api = E_API_STATE.SIGNIN_OK;
//                                }
//                                onResult(mREPLY);
//                            } catch (LfException e) {
//                                mREPLY.code = "ER";
//                                mREPLY.code_info = e.getMessage();
//                                Log.v(TAG, e.getMessage());
//                                onResult(CheckMisPosLfException(e));
//                            } finally {
//                                state_op = E_REQ_RETURN.REQ_OK;
//                            }
//                        }
//                    }
//                }).start();
//            }
//        }
//    }

    /**
     * 退款
     */
    public void pos_refund(String mer, String tmn, String card_no, int amount, String p_specTmpSerial, int type, IUserCallback lsn) {
        Logz.w(TAG, "mer:" + mer + " ，tmn:" + tmn + " ，card_n:" + card_no + " ，amount_fen:" + amount + " ，p_specTmpSerial:" + p_specTmpSerial);
        pos_setType(type);
        this.gr_mer = mer;
        this.gr_tmn = tmn;
        this.CardNo = card_no;
        this.amount_fen = amount;
        this.specTmpSerial = p_specTmpSerial;
        pos_base(E_OP_TYPE.OP_POS_REFUND);
    }
//    public void pos_refund(String mer, String tmn, String card_no, int amount, String p_specTmpSerial, int type, IUserCallback lsn) {
//        mIUserCallback = lsn;
//        if (type == 0) {
//            pos_setType(0);//设置会员卡退款类型
//        } else if (type == 1) {
//            pos_setType(1);
//        }
//        if ((state_op == E_REQ_RETURN.REQ_BUSY || pmLfPosApiMispos._IsBusy())) {
//            onBusy(OP_REFUND);
//        } else if (CheckApiState(E_OP_TYPE.OP_PRO_POS_REFUND, E_API_STATE.UNKNOW)) {
//            System.out.println("return deny");
//            onDeny(OP_REFUND);
//        } else {
//            opflag = OP_REFUND;
//            synchronized (lockApi) {
//                state_op = E_REQ_RETURN.REQ_BUSY;
//                gr_mer = mer;
//                gr_tmn = tmn;
//                CardNo = card_no;
//                amount_fen = amount;
//                specTmpSerial = p_specTmpSerial;
//                //异步调用
//                new Thread(new Runnable() {
//                    public void run() {
//                        _03_Common t = new _03_Common();
//                        String amount_str = String.format("%012d", amount_fen);
//                        REPLY mREPLY = new REPLY();
//                        mREPLY.reply = 1;//默认失败
//                        mREPLY.code = "LF";
//                        mREPLY.code_info = "请求失败";
//
//                        lastOP = E_OP_TYPE.OP_PRO_POS_REFUND;
//                        if (amount_fen < 1) {
//                            mREPLY.code_info = "金额不能小于1分";
//                            mREPLY.code = ErrCode._Z9.getCode();
//                            onResult(mREPLY);
//                            state_op = E_REQ_RETURN.REQ_OK;
//                        } else if (CardNo.length() > 19) {
//                            mREPLY.code_info = "会员卡长度应为0~19位";
//                            mREPLY.code = ErrCode._Z9.getCode();
//                            onResult(mREPLY);
//                            state_op = E_REQ_RETURN.REQ_OK;
//                        } else {
//                            try {
//                                Log.v(TAG, "amount_str:" + amount_str);
//                                if (pos_getType() == 0) {
//                                    t = new _03_Refund();//设置请求参数
//                                    t.setTradeType(TradeType.REFUND);//交易类型
//                                    ((_03_Refund) t).setCardNo(CardNo);
//                                    ((_03_Refund) t).setAmount(amount_str);//金额，单位：分
//                                    ((_03_Refund) t).setSpecTmnSerial(specTmpSerial);
//                                    Log.v(TAG, "call refund...");
//                                    mREPLY = pmLfPosApiMispos._03_Refund(t);//发起请求
//                                } else if (pos_getType() == 1) {
//                                    t = new _03_RefundPurchase();
//                                    t.setTradeType(TradeType.REFUNDPURCHASE);
//                                    ((_03_RefundPurchase) t).setMer(gr_mer);
//                                    ((_03_RefundPurchase) t).setTmn(gr_tmn);
//                                    ((_03_RefundPurchase) t).setAmount(amount_str);
//                                    ((_03_RefundPurchase) t).setVoucher(specTmpSerial);
//                                    mREPLY = pmLfPosApiMispos._03_RefundPurchase(t);
//                                } else {
//                                    t = new _03_RefundPurchase();
//                                    t.setTradeType(TradeType.REFUNDPURCHASE);
//                                    ((_03_RefundPurchase) t).setMer(gr_mer);
//                                    ((_03_RefundPurchase) t).setTmn(gr_tmn);
//                                    ((_03_RefundPurchase) t).setAmount(amount_str);
//                                    ((_03_RefundPurchase) t).setVoucher(specTmpSerial);
//                                    mREPLY = pmLfPosApiMispos._03_RefundPurchase(t);
//                                }
//                                Log.v(TAG, "refund done:" + (int) mREPLY.reply + "," + mREPLY.code + ", " + mREPLY.code_info);
//                                if (mREPLY.reply == MISPOS.PACK_ACK) {
//                                    state_api = E_API_STATE.SIGNIN_OK;
//                                }
//                                onResult(mREPLY);
//                            } catch (LfException e) {
//                                mREPLY.code = "ER";
//                                mREPLY.code_info = e.getMessage();
//                                Log.v(TAG, e.getMessage());
//                                onResult(CheckMisPosLfException(e));
//                            } finally {
//                                state_op = E_REQ_RETURN.REQ_OK;
//                            }
//                        }
//                    }
//                }).start();
//            }
//        }
//    }

    /**
     * 充值
     *
     * @param amount 以分为单位！！！
     */
    public void pos_recharge(int amount, IUserCallback lsn) {
        pos_setType(0);
        this.amount_fen = amount;
        this.mIUserCallback = lsn;
        pos_base(E_OP_TYPE.OP_MEMBER_RECHARGE);
    }
//    public void pos_recharge(int amount, IUserCallback lsn) {
//        mIUserCallback = lsn;
//        pos_setType(0);//新增,设置会员卡退款类型
//        if ((state_op == E_REQ_RETURN.REQ_BUSY || pmLfPosApiMispos._IsBusy())) {
//            onBusy(OP_RECHARGE);
//        } else if (CheckApiState(E_OP_TYPE.OP_MEMBER_RECHARGE, E_API_STATE.UNKNOW)) {
//
//            System.out.println("return deny");
//            onDeny(OP_RECHARGE);
//        } else {
//            opflag = OP_RECHARGE;
//            synchronized (lockApi) {
//                state_op = E_REQ_RETURN.REQ_BUSY;
//                amount_fen = amount;
//                //异步调用
//                new Thread(new Runnable() {
//                    public void run() {
//                        _04_PurchaseReply mREPLY = new _04_PurchaseReply();
//                        mREPLY.reply = 1;//默认失败
//                        mREPLY.code = "LF";
//                        mREPLY.code_info = "请求失败";
//
//                        _03_Shensi_Recharge t = new _03_Shensi_Recharge();
//
//                        String amount_str = String.format("%012d", amount_fen);
//                        lastOP = E_OP_TYPE.OP_MEMBER_RECHARGE;
//
//                        if (amount_fen < 1) {
//                            mREPLY.code_info = "金额不能小于1分";
//                            mREPLY.code = ErrCode._Z9.getCode();
//                            onResult(mREPLY);
//                            state_op = E_REQ_RETURN.REQ_OK;
//                        } else {
//                            try {
//                                //设置请求参数
//                                t.setTradeType(TradeType.RECHARGE);//交易类型
//                                Log.v(TAG, "amount_str:" + amount_str);
//                                t.setAmount(amount_str);//金额，单位：分
//                                Log.v(TAG, "call trade...");
//                                mREPLY = pmLfPosApiMispos._03_Shensi_Recharge(t);//发起请求
//                                Log.v(TAG, "trade done:" + (int) mREPLY.reply + "," + mREPLY.code + ", " + mREPLY.code_info);
//                                if (mREPLY.reply == MISPOS.PACK_ACK) {
//                                    state_api = E_API_STATE.SIGNIN_OK;
//                                }
//                                onResult(mREPLY);
//                            } catch (LfException e) {
//                                mREPLY.code = "ER";
//                                mREPLY.code_info = e.getMessage();
//                                Log.v(TAG, e.getMessage());
//                                onResult(CheckMisPosLfException(e));
//                            } finally {
//                                state_op = E_REQ_RETURN.REQ_OK;
//                            }
//                        }
//                    }
//                }).start();
//            }
//        }
//    }

    /**
     * 交易查询
     *
     * @param mer      商户号
     * @param tmn      终端号
     * @param poswater 流水号
     */
    public void pos_getrecord(String mer, String tmn, String poswater, IUserCallback lsn) {
        this.gr_mer = mer;
        this.gr_tmn = tmn;
        this.gr_poswater = poswater;
        this.mIUserCallback = lsn;
        pos_base(E_OP_TYPE.OP_POS_GETRECORD);
    }
//    public void pos_getrecord(String mer, String tmn, String poswater, IUserCallback lsn) {
//        mIUserCallback = lsn;
//        if (state_op == E_REQ_RETURN.REQ_BUSY) {
//            Log.v(TAG, "return busy");
//            onBusy(OP_GETRECORD);
//        } else if (CheckApiState(E_OP_TYPE.OP_POS_GETRECORD, E_API_STATE.NOT_SIGNIN)) {//忽略未签到状态
//            Log.v(TAG, "return deny");
//            onDeny(OP_GETRECORD);
//        } else {
//            opflag = OP_GETRECORD;
//            synchronized (lockApi) {
//                state_op = E_REQ_RETURN.REQ_BUSY;
//                gr_mer = mer;
//                gr_tmn = tmn;
//                gr_poswater = poswater;
//                //异步调用
//                new Thread(new Runnable() {
//                    public void run() {
//                        _03_GetRecord t = new _03_GetRecord();
//                        _04_GetRecordReply mREPLY = new _04_GetRecordReply();
//                        lastOP = E_OP_TYPE.OP_POS_GETRECORD;
//
//                        if (true) {
//                            try {
//                                //设置请求参数
//                                t.setTradeType(TradeType.GET_RECORD);//交易类型
//                                Log.v(TAG, "gr_poswater:" + gr_poswater);
//                                t.setMer(gr_mer);
//                                t.setTmn(gr_tmn);
//                                t.setSerialNo(gr_poswater);
//                                mREPLY = pmLfPosApiMispos._03_GetRecordReply(t);//发起请求
//                                Log.v(TAG, "get_record done:" + (int) mREPLY.reply + "," + mREPLY.code + ", " + mREPLY.code_info);
//                                onResult(mREPLY);
//                            } catch (LfException e) {
//                                mREPLY.code = "ER";
//                                mREPLY.code_info = e.getMessage();
//                                Log.v(TAG, e.getMessage());
//                                onResult(CheckMisPosLfException(e));
//                            } finally {
//                                state_op = E_REQ_RETURN.REQ_OK;
//                            }
//                        }
//                    }
//                }).start();
//            }
//        }
//    }

    public void pos_getSettleInfo(String mer, String tmn, IUserCallback lsn) {
        mIUserCallback = lsn;
        if (state_op == E_REQ_RETURN.REQ_BUSY) {
            Logz.v(TAG, "return busy");
            onBusy(OP_GETSETTLEINFO);
        } else if (CheckApiState(E_OP_TYPE.OP_POS_GET_SETTLE_INFO, E_API_STATE.NOT_SIGNIN)) {//忽略未签到状态
            Logz.v(TAG, "return deny");
            onDeny(OP_GETSETTLEINFO);
        } else {
            opflag = OP_GETSETTLEINFO;
            synchronized (lockApi) {
                state_op = E_REQ_RETURN.REQ_BUSY;
                gr_mer = mer;
                gr_tmn = tmn;
                //异步调用
                new Thread(new Runnable() {
                    public void run() {
                        _03_Common t = new _03_Common();
                        _04_GetSettleInfoReply mREPLY = new _04_GetSettleInfoReply();
                        lastOP = E_OP_TYPE.OP_POS_GET_SETTLE_INFO;

                        if (amount_fen < 1) {
                            state_op = E_REQ_RETURN.REQ_OK;
                            //异常返回
                            mREPLY.code_info = "金额不能小于1分";
                            mREPLY.code = ErrCode._Z9.getCode();
                            onResult(mREPLY);
                        } else {
                            try {
                                //设置请求参数
                                t.setTradeType(TradeType.GET_SETTLE_INFO);//交易类型
                                Logz.v(TAG, "gr_poswater:" + gr_poswater);
                                t.setMer(gr_mer);
                                t.setTmn(gr_tmn);
                                mREPLY = pmLfPosApiMispos._03_GetSettleInfo(t);//发起请求
                                Logz.v(TAG, "get_SettleInfo done:" + (int) mREPLY.reply + "," + mREPLY.code + ", " + mREPLY.code_info);
                                onResult(mREPLY);
                            } catch (LfException e) {
                                mREPLY.code = "ER";
                                mREPLY.code_info = e.getMessage();
                                Logz.v(TAG, e.getMessage());
                                onResult(CheckMisPosLfException(e));
                            } finally {
                                state_op = E_REQ_RETURN.REQ_OK;
                            }
                        }

                    }
                }).start();
            }
        }
    }


    public boolean pos_isQuerying() {
        return (querying || state_op == E_REQ_RETURN.REQ_BUSY || pmLfPosApiMispos._IsBusy());
    }

    /**
     * 查询闪付余额，只有签到成功后才可以进行交易
     */
    public void pos_query(IUserCallback lsn) {
        mIUserCallback = lsn;
        pos_setType(0);
        if (state_op == E_REQ_RETURN.REQ_BUSY || pmLfPosApiMispos._IsBusy()) {
            onBusy(OP_QUERY);
        } else if (CheckApiState(E_OP_TYPE.OP_POS_QUERY, E_API_STATE.NOT_SIGNIN)) {
            onDeny(OP_QUERY);
        } else {
            opflag = OP_QUERY;
            synchronized (lockApi) {
                querying = true;
                state_op = E_REQ_RETURN.REQ_BUSY;
                //异步调用
                new Thread(new Runnable() {
                    public void run() {
                        _03_Common t = new _03_Common();
                        _04_QueryReply mREPLY = new _04_QueryReply();
                        lastOP = E_OP_TYPE.OP_POS_QUERY;//msg.arg1;
                        try {
                            //设置请求参数
                            t.setTradeType(TradeType.QUERY);//交易类型
                            mREPLY = pmLfPosApiMispos._03_Query(t);//发起请求
                            if (mREPLY.reply == MISPOS.PACK_ACK) {
                                state_api = E_API_STATE.SIGNIN_OK;
                            }
                            onResult(mREPLY);
                        } catch (LfException e) {
                            mREPLY.code = "ER";
                            mREPLY.code_info = e.getMessage();
                            Logz.v(TAG, e.getMessage());
                            onResult(CheckMisPosLfException(e));
                        } finally {
                            state_op = E_REQ_RETURN.REQ_OK;
                            querying = false;
                        }
                    }
                }).start();
            }
        }
    }

    public void pos_stateQuery(IUserCallback lsn) {
        mIUserCallback = lsn;
        if (state_op == E_REQ_RETURN.REQ_BUSY || pmLfPosApiMispos._IsBusy()) {
            onBusy(OP_STATEQUERY);
        } else if (CheckApiState(E_OP_TYPE.OP_POS_STATEQUERY, E_API_STATE.UNKNOW)) {
            onDeny(OP_STATEQUERY);
        } else {
            opflag = OP_STATEQUERY;
            synchronized (lockApi) {
                querying = true;
                state_op = E_REQ_RETURN.REQ_BUSY;
                //异步调用
                new Thread(new Runnable() {
                    public void run() {
                        _03_Common t = new _03_Common();
                        REPLY mREPLY = new REPLY();
                        lastOP = E_OP_TYPE.OP_POS_STATEQUERY;
                        try {
                            //设置请求参数
                            t.setTradeType(TradeType.STATEQUERY);//交易类型
                            mREPLY = pmLfPosApiMispos._03_StateQuery(t);//发起请求
                            if (mREPLY.reply == MISPOS.PACK_ACK) {
                                state_api = E_API_STATE.SIGNIN_OK;
                            }
                            onResult(mREPLY);
                        } catch (LfException e) {
                            mREPLY.code = "ER";
                            mREPLY.code_info = e.getMessage();
                            Logz.v(TAG, e.getMessage());
                            onResult(CheckMisPosLfException(e));
                        } finally {
                            state_op = E_REQ_RETURN.REQ_OK;
                        }
                    }
                }).start();
            }
        }
    }

    public void pos_ping(IUserCallback lsn) {
        mIUserCallback = lsn;
        if (state_op == E_REQ_RETURN.REQ_BUSY) {
            onBusy(OP_PING);
        } else if (CheckApiState(E_OP_TYPE.OP_POS_PING, E_API_STATE.UNKNOW)) {//无视未签到状态
            onDeny(OP_PING);
        } else {
            opflag = OP_PING;
            synchronized (lockApi) {
                state_op = E_REQ_RETURN.REQ_BUSY;
                //异步调用
                new Thread(new Runnable() {
                    public void run() {
                        _03_Common t = new _03_Common();
                        REPLY mREPLY = new REPLY();
                        lastOP = E_OP_TYPE.OP_POS_PING;
                        try {
                            //设置请求参数
                            t.setTradeType(TradeType.PING);//交易类型
                            mREPLY = pmLfPosApiMispos._03_Ping(t);//发起请求
                            //设置正常返回结果
                            if (mREPLY.reply == MISPOS.PACK_ACK) {
                                state_api = E_API_STATE.SIGNIN_OK;
                            }
                            onResult(mREPLY);
                        } catch (LfException e) {
                            mREPLY.code = "ER";
                            mREPLY.code_info = e.getMessage();
                            Logz.v(TAG, e.getMessage());
                            onResult(CheckMisPosLfException(e));
                        } finally {
                            state_op = E_REQ_RETURN.REQ_OK;
                        }
                    }
                }).start();
            }
        }
    }

    /**
     * 查询闪付余额，只有签到成功后才可以进行交易
     */
//    public void pos_upload(IUserCallback lsn) {
//        mIUserCallback = lsn;
//        if (state_op == E_REQ_RETURN.REQ_BUSY || pmLfPosApiMispos._IsBusy()) {
//            onBusy(OP_UPLOAD);
//        } else if (CheckApiState(E_OP_TYPE.OP_POS_UPLOAD, E_API_STATE.UNKNOW)) {
//            onDeny(OP_UPLOAD);
//        } else {
//            opflag = OP_UPLOAD;
//            synchronized (lockApi) {
//                querying = true;
//                state_op = E_REQ_RETURN.REQ_BUSY;
//                //异步调用
//                new Thread(new Runnable() {
//                    public void run() {
//                        _03_Common t = new _03_Common();
//                        REPLY mREPLY = new REPLY();
//                        lastOP = E_OP_TYPE.OP_POS_UPLOAD;
//                        try {
//                            //设置请求参数
//                            t.setTradeType(TradeType.UPLOADOFFLINE);//交易类型
//                            t.setHasExtraInfo(true);//有附加信息
//                            mREPLY = pmLfPosApiMispos._03_UploadOffline(t);//发起请求
//                            if (mREPLY.reply == MISPOS.PACK_ACK) {
//                                state_api = E_API_STATE.SIGNIN_OK;
//                            }
//                            onResult(mREPLY);
//                        } catch (LfException e) {
//                            mREPLY.code = "ER";
//                            mREPLY.code_info = e.getMessage();
//                            Log.v(TAG, e.getMessage());
//                            onResult(CheckMisPosLfException(e));
//                        } finally {
//                            state_op = E_REQ_RETURN.REQ_OK;
//                            querying = false;
//                        }
//                    }
//                }).start();
//            }
//        }
//    }

    /**
     * 配置pos
     */

//    public void pos_config(boolean pUseOfflineTrade, IUserCallback lsn) {
//        mIUserCallback = lsn;
//        if (state_op == E_REQ_RETURN.REQ_BUSY || pmLfPosApiMispos._IsBusy()) {
//            onBusy(OP_CONFG);
//        } else if (CheckApiState(E_OP_TYPE.OP_POS_SETTINGS, E_API_STATE.NOT_SIGNIN)) {
//            onDeny(OP_CONFG);
//        } else {
//            opflag = OP_CONFG;
//            synchronized (lockApi) {
//                querying = true;
//                state_op = E_REQ_RETURN.REQ_BUSY;
//
//                useOfflineTrade = pUseOfflineTrade;
//                //异步调用
//                new Thread(new Runnable() {
//                    public void run() {
//                        _03_Settings t = new _03_Settings();
//                        REPLY mREPLY = new REPLY();
//                        lastOP = E_OP_TYPE.OP_POS_SETTINGS;
//                        try {
//                            //设置请求参数
//                            t.setTradeType(TradeType.SETTINGS);//交易类型
//                            t.setUseOfflineTrade(useOfflineTrade);
//
//                            mREPLY = pmLfPosApiMispos._03_Settings(t);//发起请求
//                            if (mREPLY.reply == MISPOS.PACK_ACK) {
//                                state_api = E_API_STATE.SIGNIN_OK;
//                            }
//                            onResult(mREPLY);
//                        } catch (LfException e) {
//                            Log.v(TAG, e.getMessage());
//                            onResult(CheckMisPosLfException(e));
//                        } finally {
//                            state_op = E_REQ_RETURN.REQ_OK;
//                            querying = false;
//                        }
//                    }
//                }).start();
//            }
//        }
//    }

    /**
     * 操作取消
     */
    public void pos_cancel() {//TODO:需要测试
        if (CheckApiState(E_OP_TYPE.OP_POS_CANCEL, E_API_STATE.UNKNOW)) {
            //onDeny();
            return;
        }
        if (mMyApiTask != null) {
            mMyApiTask.interrupt();
        }
        //异步调用
        new Thread(new Runnable() {
            public void run() {
                _03_Common t = new _03_Common();
                REPLY mREPLY = new REPLY();
                try {
                    System.out.println("call _07_Cancel...");
                    t.setTradeType(TradeType.CANCEL);//交易类型
                    mREPLY = pmLfPosApiMispos._07_Cancel(t);//发起请求
                } catch (LfException e) {
                    Logz.v(TAG, e.getMessage());
                } finally {
                }
            }
        }).start();
    }

    /**
     * 操作取消
     */
    public int BankCancel() {//TODO:需要测试
        if (CheckApiState(E_OP_TYPE.OP_POS_CANCEL, E_API_STATE.UNKNOW)) {
            //onDeny();
            return -1;
        }
        if (mMyApiTask != null) {
            mMyApiTask.interrupt();
        }
        //异步调用
        new Thread(new Runnable() {
            public void run() {
                _03_Common t = new _03_Common();
                REPLY mREPLY = new REPLY();
                try {
                    System.out.println("call _07_Cancel...");
                    t.setTradeType(TradeType.CANCEL);//交易类型
                    mREPLY = pmLfPosApiMispos._07_Cancel(t);//发起请求
                } catch (LfException e) {
                    Logz.v(TAG, e.getMessage());
                } finally {
                }
            }
        }).start();
        return 0;
    }

    private void pmsg(String msg) {
        Display dis = new Display();
        //提示信息
        dis.setType(DisplayType._2.getType());//用读卡类型
        dis.setMsg(msg);
        onProccess(dis);
    }

    //默认的提示信息回调，用于在液晶上显示
    private IDisplay mIDisplay = new IDisplay() {
        @Override
        public void Display(String code, String desc, String msg) {
            Display dis = new Display();
            dis.setType(code);
            if (desc != null) {
                dis.setMsg(desc + "," + msg);
            } else {
                dis.setMsg(msg);
            }
            onProccess(dis);
        }
    };


    private boolean useSynch = false;

    public boolean isUseSynch() {
        return useSynch;
    }

    public void setUseSynch(boolean useSynch) {
        this.useSynch = useSynch;
    }
}

