package com.landfoneapi.misposwa;

import android.content.Context;
import android.os.Message;
import android.util.Log;

import com.landfone.common.utils.Logz;
import com.landfone.mis.bean.TransCfx;
import com.landfoneapi.mispos.CallbackMsg;
import com.landfoneapi.mispos.DisplayType;
import com.landfoneapi.mispos.E_API_STATE;
import com.landfoneapi.mispos.E_ERR_CODE;
import com.landfoneapi.mispos.E_OP_TYPE;
import com.landfoneapi.mispos.E_REQ_RETURN;
import com.landfoneapi.mispos.MISPOS;
import com.landfoneapi.protocol.LfPosApiMispos1;
import com.landfoneapi.protocol.iface.IDisplay;
import com.landfone.common.utils.ISerialPort;
import com.landfoneapi.mispos.Display;
import com.landfoneapi.protocol.pkg.REPLY;
import com.landfoneapi.protocol.pkg.TradeType;
import com.landfoneapi.protocol.pkg._04_QueryReply;
import com.landfoneapi.protocol.pkg.junpeng._03_Recharge;
import com.landfoneapi.protocol.pkg.junpeng._03_Refund;
import com.landfoneapi.protocol.pkg.sand._03_SandBscPay;
import com.landfoneapi.protocol.pkg.sand._03_SandBscRefund;
import com.landfoneapi.protocol.pkg.sand._03_SandBscReturn;
import com.landfoneapi.protocol.pkg.sand._03_SandCardTrade;
import com.landfoneapi.protocol.pkg.sand._03_SandKeyReplace;
import com.landfoneapi.protocol.pkg.sand._03_SandO2OCancel;
import com.landfoneapi.protocol.pkg.sand._03_SandO2OLoop;
import com.landfoneapi.protocol.pkg.sand._03_SandO2OQrCodeQuery;
import com.landfoneapi.protocol.pkg.sand._03_SandO2OQrCodeTrade;
import com.landfoneapi.protocol.pkg.sand._03_SandO2ORefund;
import com.landfoneapi.protocol.pkg.sand._03_SandO2OResult;
import com.landfoneapi.protocol.pkg.sand._03_SandO2OSettle;
import com.landfoneapi.protocol.pkg.sand._03_SandO2OSignin;
import com.landfoneapi.protocol.pkg.sand._03_SandPosDeliveryKey;
import com.landfoneapi.protocol.pkg.sand._03_SandSettle;
import com.landfoneapi.protocol.pkg.sand._03_SandSmall;
import com.landfoneapi.protocol.pkg.sand._03_SandSyncMessage;
import com.landfoneapi.protocol.pkg.shensi._03_Shensi_Query;
import com.landfoneapi.protocol.pkg.shensi._03_Shensi_Recharge;
import com.landfoneapi.protocol.pkg.xmparking._03_BSCCancel;
import com.landfoneapi.protocol.pkg.jxnx._03_BoundAccountQuery;
import com.landfoneapi.protocol.pkg.jxnx._03_Cashrem;
import com.landfoneapi.protocol.pkg.jxnx._03_CheckBalance;
import com.landfoneapi.protocol.pkg._03_Common;
import com.landfoneapi.protocol.pkg.jxnx._03_DownloadAppcode;
import com.landfoneapi.protocol.pkg.jxnx._03_GetCardReader;
import com.landfoneapi.protocol.pkg.jxnx._03_GetKeyRequest;
import com.landfoneapi.protocol.pkg.jxnx._03_GetKeyboard;
import com.landfoneapi.protocol.pkg._03_GetPrintInfo;
import com.landfoneapi.protocol.pkg._03_GetRecord;
import com.landfoneapi.protocol.pkg.jxnx._03_GetZNRecordInfo;
import com.landfoneapi.protocol.pkg.jxnx._03_PassbookRenew;
import com.landfoneapi.protocol.pkg.jxnx._03_PassbookToCard;
import com.landfoneapi.protocol.pkg.jxnx._03_Payfees;
import com.landfoneapi.protocol.pkg.jxnx._03_PayfeesQuery;
import com.landfoneapi.protocol.pkg.xmparking._03_PosCSBQuery;
import com.landfoneapi.protocol.pkg.xmparking._03_PosCSBTrade;
import com.landfoneapi.protocol.pkg.preauthor._03_PreAuthorCancel;
import com.landfoneapi.protocol.pkg.preauthor._03_PreAuthorDone;
import com.landfoneapi.protocol.pkg.preauthor._03_PreAuthorDoneCancel;
import com.landfoneapi.protocol.pkg.preauthor._03_PreAuthorization;
import com.landfoneapi.protocol.pkg._03_Purchase;
import com.landfoneapi.protocol.pkg.jxnx._03_ReadAccountName;
import com.landfoneapi.protocol.pkg.jxnx._03_ReadCardNo;
import com.landfoneapi.protocol.pkg._03_RefundPurchase;
import com.landfoneapi.protocol.pkg.jxnx._03_SendTMK;
import com.landfoneapi.protocol.pkg.jxnx._03_SetMerTmn;
import com.landfoneapi.protocol.pkg.jxnx._03_TestGetZNRecordInfo;
import com.landfoneapi.protocol.pkg.jxnx._03_TestMK210;
import com.landfoneapi.protocol.pkg.jxnx._03_TestZNTransferrem;
import com.landfoneapi.protocol.pkg.jxnx._03_TestZNWithdraw;
import com.landfoneapi.protocol.pkg.xmparking._03_ThreeInOne;
import com.landfoneapi.protocol.pkg.jxnx._03_TransQuery;
import com.landfoneapi.protocol.pkg.jxnx._03_Transferrem;
import com.landfoneapi.protocol.pkg.xmparking._03_TwoInOne;
import com.landfoneapi.protocol.pkg.jxnx._03_Withdraw;
import com.landfoneapi.protocol.pkg.jxnx._04_BoundAccountQueryReply;
import com.landfoneapi.protocol.pkg.jxnx._04_DownloadAppcodeReply;
import com.landfoneapi.protocol.pkg.jxnx._04_GetKeyboardReply;
import com.landfoneapi.protocol.pkg._04_GetPrintInfoReply;
import com.landfoneapi.protocol.pkg._04_GetRecordReply;
import com.landfoneapi.protocol.pkg.jxnx._04_GetZNRecordInfoReply;
import com.landfoneapi.protocol.pkg.jxnx._04_PassbookRenewReply;
import com.landfoneapi.protocol.pkg.jxnx._04_PayfeesQueryReply;
import com.landfoneapi.protocol.pkg._04_PurchaseReply;
import com.landfoneapi.protocol.pkg.jxnx._04_ReadAccountNameReply;
import com.landfoneapi.protocol.pkg.jxnx._04_ReadCardNoReply;
import com.landfoneapi.protocol.pkg.jxnx._04_TestGetZNRecordInfoReply;
import com.landfoneapi.protocol.pkg.jxnx._04_TransQueryReply;
import com.landfone.common.utils.Errs;
import com.landfone.common.utils.LfException;

//import com.landfoneapi.protocol.LfWxAlp;

//import LfApi.*;

/**
 * pos操作的接口调用实现，Service中调用
 *
 * @author yelz
 */
public class MyApi1 extends LfApi1 {
    private String TAG = this.getClass().getSimpleName();

    private byte[] lockApi = new byte[0];
    //基本参数
    private String pospIP = "218.20.222.1";
    private int pospPort = 7001;


    private boolean useSynch = false;//是否使用同步方法
    //POS接口API对象
    private LfPosApiMispos1 pmLfPosApiMispos1 = new LfPosApiMispos1();

    public boolean isUseSynch() {
        return useSynch;
    }

    public void setUseSynch(boolean useSynch) {
        this.useSynch = useSynch;
    }

    /**
     * 设置是否需要签到检查
     */
    public void setCheckPOSSignin(boolean checkPOSSignin) {
        this.checkPOSSignin = checkPOSSignin;
    }

    public String getCurrOpInfo() {
        return lastOP.getDesc();
    }

    private boolean checkPOSSignin = false;

    /**
     * 期望进行op的操作，先进行api状态的判断；ignor用于屏蔽某一个判断（UNKOWN表示需要进行现有的判断——初始化和签到）
     */
    private boolean CheckApiState(E_OP_TYPE op, E_API_STATE ignor) {
        boolean ret = true;
        dpl.setType(DisplayType._6.getDesc());
        dpl.setMsg("检测Api状态");
        if (state_api == E_API_STATE.NOT_INIT && ignor != state_api) {
            Message msg = new Message();
            msg.obj = (new CallbackMsg(E_ERR_CODE.ERR_POS_NOT_INIT, op, 1, state_api, E_ERR_CODE.ERR_POS_NOT_INIT.getDesc(), dpl));
            Logz.i(TAG, "CheckApiState,op:" + op + ", state_api:" + state_api);
            sendMessage(msg);
        } else if (checkPOSSignin && (state_api == E_API_STATE.NOT_SIGNIN && ignor != state_api)) {
            Message msg = new Message();
            msg.obj = (new CallbackMsg(E_ERR_CODE.ERR_POS_NOT_SIGNIN, op, 1, state_api, E_ERR_CODE.ERR_POS_NOT_SIGNIN.getDesc(), dpl));
            Logz.i(TAG, ">>>CheckApiState ERR POS_NOT_SIGNIN,1");
            sendMessage(msg);
        } else {
            ret = false;
        }
        return ret;
    }

    /**
     * 根据ErrCode返回码，进行再一次处理
     */
    private E_ERR_CODE CheckNAK(REPLY p) {
        E_ERR_CODE ret = E_ERR_CODE.ERR_UNKNOW;
        if (p.reply == MISPOS.PACK_NAK && p.code != null) {
            if (p.code.length() > 1) {
                Logz.i(TAG, "CheckNAK " + p.code);
                if (p.code.startsWith("A4")) {//未签到
                    state_api = E_API_STATE.NOT_SIGNIN;
                    ret = E_ERR_CODE.ERR_POS_NOT_SIGNIN;
                } else if (p.code.startsWith("X5")) {//未签到
                    state_api = E_API_STATE.NOT_SIGNIN;
                    ret = E_ERR_CODE.ERR_POS_NOT_SIGNIN;
                } else if (p.code.startsWith("00")) {//未签到
                    ret = E_ERR_CODE.ERR_OTHRER;
                } else if (p.code.startsWith("Y1")) {//按键输入结束
                    ret = E_ERR_CODE.ERR_KEYBOARD_INPUT_FINISH;
                } else if (p.code.startsWith("Y2")) {//读卡取消
                    ret = E_ERR_CODE.ERR_READ_CARD_CANCEL;
                } else if (p.code.startsWith("Y3")) {//超时
                    ret = E_ERR_CODE.ERR_TIME_OUT;
                } else if (p.code.startsWith("XY")) {//交易取消
                    ret = E_ERR_CODE.ERR_CANCEL;
                } else if (p.code.startsWith("Z1")) {//超时
                    ret = E_ERR_CODE.ERR_TIME_OUT;
                }  else {
                    ret = E_ERR_CODE.ERR_OTHRER;
                }
            }
        }
        return ret;
    }

    private E_ERR_CODE CheckMisPosLfException(LfException e) {
        E_ERR_CODE ret = E_ERR_CODE.ERR_UNKNOW;
        Logz.i(TAG, "errorcode:" + e.getErrcode());
        if (e.getErrcode() == Errs.SERIAL_OPEN_ERR.getValue()
                || e.getErrcode() == Errs.SERIAL_IFACE_NULL.getValue()
                || e.getErrcode() == Errs.SERIAL_TIME_OUT.getValue()
                || e.getErrcode() == Errs.SERIAL_IO_ERR.getValue()) {
            ret = E_ERR_CODE.ERR_SERIAL_ERROR;
        } else if (e.getErrcode() == Errs.PARAMS_INVALID.getValue()) {
            ret = E_ERR_CODE.ERR_PARAMS_INVALID;
        } else {
            ret = E_ERR_CODE.ERR_OTHRER;
        }
        return ret;
    }

    /**
     * 获取状态
     */
    private E_API_STATE get_state() {
        return state_api;
    }

    /**
     * 设置状态，为了处理无需重复签到的情况
     */
    private void set_state(E_API_STATE st) {
        state_api = st;
    }

    /**
     * 设置交易类型：
     * 0——0xF0——会员卡;
     * 1——0x01——银行卡;
     * 92——0x92——杉德会员卡;
     * 93——0x93——杉德O2O;
     */
    public void pos_setType(int type) {
        if (type == 0) {
            pmLfPosApiMispos1.setType((byte) 0xF0);//会员卡
        } else if (type == 1) {
            pmLfPosApiMispos1.setType((byte) 0x01);//银联卡
        } else if (type == 92) {
            pmLfPosApiMispos1.setType((byte) 0x92);//杉德会员卡
        } else if (type == 93) {
            pmLfPosApiMispos1.setType((byte) 0x93);//杉德O2O平台
        } else {
            pmLfPosApiMispos1.setType((byte) 0x01);
        }
    }

    public int pos_getType() {
        byte type = pmLfPosApiMispos1.getType();
        if (type == (byte) 0xF0) {
            return 0;
        } else if (type == (byte) 0x01) {
            return 1;
        } else if (type == (byte) 0x02) {
            return 2;
        } else if (type == (byte) 0x92) {
            return 92;
        } else if (type == (byte) 0x93) {
            return 93;
        } else return 1;
    }

    public void setTransCfx(TransCfx transCfx) {
        Logz.i(TAG,"raoj----->setTransCfx");
        pmLfPosApiMispos1.setTransCfx(transCfx);
        pmLfPosApiMispos1.getProperties();
    }

    public void setTmsServer(String ip, int port) {
        pmLfPosApiMispos1.setTmsServer(ip, port);
    }

    public void setAutoSettle(boolean isAuto) {
        pmLfPosApiMispos1.setAutoSettle(isAuto);//自动结算
    }

    public void setAutoTms(boolean isAuto) {
        pmLfPosApiMispos1.setAutoTms(isAuto);//自动TMS升级
    }

    public void pos_netdisconnect(boolean isConnect) {
        pmLfPosApiMispos1.disConnect(isConnect);
    }

    public E_REQ_RETURN pos_setKeyCert(Context context, boolean isSSL, String trust_path) {
        state_op = E_REQ_RETURN.REQ_BUSY;
        pmLfPosApiMispos1.setKeyCert(context, isSSL, trust_path);
        state_op = E_REQ_RETURN.REQ_OK;
        return E_REQ_RETURN.REQ_OK;
    }

    public E_REQ_RETURN pos_setHardEncrypt(Context context, boolean isHard, String trust_path) {
        state_op = E_REQ_RETURN.REQ_BUSY;
        pmLfPosApiMispos1.setHardEncrypt(context, isHard, trust_path);
        state_op = E_REQ_RETURN.REQ_OK;
        return E_REQ_RETURN.REQ_OK;
    }

    /**
     * 设置串口通讯接口
     *
     * @param pISerialPort null时使用android的串口jni，SerialPort
     */

    public void setPOSISerialPort(ISerialPort pISerialPort) {
        pmLfPosApiMispos1.setPOSISerialPort(pISerialPort);
    }

    /**
     * 初始化函数
     *
     * @param pospIP POSP服务器IP，目前【测试】用"218.20.222.1",		需要可配置/保存/读取
     * @param port   POSP服务器端口，目前【测试】用7001,				需要可配置/保存/读取
     * @param path   串口路径，如："/dev/ttyS1"
     * @param baud   波特率，如"9600"
     */
    public E_REQ_RETURN pos_init(String pospIP, int port, String path, String baud) {//
        if (state_op == E_REQ_RETURN.REQ_BUSY) {
            Logz.i(TAG, "return busy");
            return E_REQ_RETURN.REQ_BUSY;
        }

        synchronized (lockApi) {
            state_op = E_REQ_RETURN.REQ_BUSY;
            String info = "";
            int reply = 0;
            E_ERR_CODE err_code = E_ERR_CODE.ERR_UNKNOW;

            Message msg = new Message();
            this.pospIP = pospIP;
            this.pospPort = port;
            pmLfPosApiMispos1.EnableDBG();//设置dbg输出
            pmLfPosApiMispos1.setPOSISerialPort(null);//设置串口接口,null为默认的串口jni
            pmLfPosApiMispos1.setIDisplay(mIDisplay);  //设置信息提示回调接口
            pmLfPosApiMispos1.setPara(path, baud); //设置串口参数
            pmLfPosApiMispos1.setPosServer(this.pospIP, this.pospPort);//设置服务器参数
            pmLfPosApiMispos1.setAutoSettle(true);//自动结算
            pmLfPosApiMispos1.setAutoTms(true);//自动TMS升级

            try {
                pmLfPosApiMispos1.init();
                info = "初始化成功";
                reply = 0;
                msg.arg1 = 0;
                msg.arg2 = 0;
                state_api = E_API_STATE.INIT_OK;
            } catch (LfException e) {
                Logz.i(TAG, e.getMessage());
                msg.arg1 = 0;
                msg.arg2 = 0;
                reply = 1;
                info = e.getMessage();
                err_code = E_ERR_CODE.ERR_SERIAL_ERROR;
                state_api = E_API_STATE.NOT_INIT;
            } finally {
                state_op = E_REQ_RETURN.REQ_OK;
                msg.obj = (new CallbackMsg(err_code, E_OP_TYPE.OP_POS_INIT, reply, state_api, info));
                sendMessage(msg);
            }
            return E_REQ_RETURN.REQ_OK;
        }
    }


    /**
     * 释放接口，不要在业务处理进行时调用
     *
     * @return
     */
    public E_REQ_RETURN pos_release() {
        //允许执行中释放资源
        state_op = E_REQ_RETURN.REQ_BUSY;
        Message msg = new Message();
        pmLfPosApiMispos1.release();
        E_ERR_CODE err_code = E_ERR_CODE.ERR_UNKNOW;
        msg.arg1 = 0;
        msg.arg2 = 0;
        msg.obj = (new CallbackMsg(err_code, E_OP_TYPE.OP_POS_RELEASE, 0, state_api, "释放成功"));//"释放成功";
        sendMessage(msg);
        state_op = E_REQ_RETURN.REQ_OK;
        state_api = E_API_STATE.NOT_INIT;
        return E_REQ_RETURN.REQ_OK;
    }

    private String gr_mer = "";
    private String gr_tmn = "";
    private String gr_poswater = "";

    /**
     * 操作取消
     */
    public E_REQ_RETURN pos_cancel() {
        if (CheckApiState(E_OP_TYPE.OP_POS_CANCEL, E_API_STATE.UNKNOW)) {
            return E_REQ_RETURN.REQ_DENY;
        }
//        synchronized (lockApi) {
        //异步调用
        new Thread(new Runnable() {
            public void run() {
                _03_Common t = new _03_Common();
                REPLY mREPLY = null;
                String info = "";
                int reply = 0;
                E_ERR_CODE err_code = E_ERR_CODE.ERR_OTHRER;
                Message msg = new Message();
                msg.arg1 = 0;//OP_QUERY;
                msg.obj = null;
                lastOP = E_OP_TYPE.OP_POS_CANCEL;//msg.arg1;
                try {
                    Thread.sleep(200);
                    Logz.i(TAG, "call _07_Cancel...");
                    t.setTradeType(TradeType.CANCEL);//交易类型
                    mREPLY = pmLfPosApiMispos1._07_Cancel(t);//发起请求

                    err_code = CheckNAK(mREPLY);
                    Logz.i(TAG, "call _07_Cancel done.");
                    //设置正常返回结果
                    msg.arg2 = 0;
                    reply = mREPLY.reply == MISPOS.PACK_ACK ? 0 : 1;
                    info = mREPLY.code + ", " + mREPLY.code_info;
                } catch (LfException e) {
                    Logz.i(TAG, e.getMessage());
                    //异常返回
                    msg.arg2 = 0;
                    reply = 1;
                    info = e.getMessage();
                    err_code = CheckMisPosLfException(e);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    msg.obj = (new CallbackMsg(err_code, E_OP_TYPE.OP_POS_CANCEL, reply, state_api, info));
                    sendMessage(msg);
                }
            }
        }).start();
        return E_REQ_RETURN.REQ_OK;
//        }
    }

    public E_REQ_RETURN pos_confirm() {
        if (CheckApiState(E_OP_TYPE.OP_POS_CONFIRM, E_API_STATE.UNKNOW)) {
            return E_REQ_RETURN.REQ_DENY;
        }
        //异步调用
        new Thread(new Runnable() {
            public void run() {
                _03_Common t = new _03_Common();
                REPLY mREPLY = null;
                String info = "";
                int reply = 0;
                E_ERR_CODE err_code = E_ERR_CODE.ERR_OTHRER;
                Message msg = new Message();
                msg.arg1 = 0;//OP_QUERY;
                msg.obj = null;
                lastOP = E_OP_TYPE.OP_POS_CONFIRM;//msg.arg1;
                try {
                    t.setTradeType(TradeType.CONFIRM);//交易类型
                    mREPLY = pmLfPosApiMispos1._07_Confirm(t);//发起请求
                    err_code = CheckNAK(mREPLY);
                    //设置正常返回结果
                    msg.arg2 = 0;
                    reply = mREPLY.reply == MISPOS.PACK_ACK ? 0 : 1;
                    info = mREPLY.code + ", " + mREPLY.code_info;//info = mREPLY.reply==MISPOS.PACK_ACK?"查询成功":"查询失败";
                } catch (LfException e) {
                    Logz.i(TAG, e.getMessage());
                    //异常返回
                    msg.arg2 = 0;//MISPOS.PACK_NAK;
                    reply = 1;
                    info = e.getMessage();
                    err_code = CheckMisPosLfException(e);
                } finally {
                    msg.obj = (new CallbackMsg(err_code, E_OP_TYPE.OP_POS_CONFIRM, reply, state_api, info));
                    sendMessage(msg);
                }
            }
        }).start();
        return E_REQ_RETURN.REQ_OK;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    private E_OP_TYPE zn_op_type = E_OP_TYPE.OP_NONE;
    private int amount_fen = 1;
    private String authCode = "";
    private String date = "";
    private String voucherNo = "";
    private String cardNo = "";
    private String effectiveDate = "";
    private String refNo = "";
    private String paycode = "";
    private String orderNo = "";
    private String zn_merNo = "000000000000000";
    private String zn_tmnNo = "00000000";
    private String zn_recvCardNo = "";
    private String zn_recvName = "";
    private String zn_payName = "";
    private String zn_keyboard = "";
    private String zn_reqNum = "";
    private String zn_voucherNo = "";
    private String zn_cardNo = "";
    private String zn_znTradeType = "";
    private String zn_startDate = "";
    private String zn_endDate = "";
    private String zn_tradeSerial = "";
    private String zn_serialNo = "";

    private byte zn_timeout = 0x3c;
    private String areaCode = "";
    private String industryCode = "";
    private String contentCode = "";
    private String userNo = "";
    private byte[] prePayFeesQueryReply = null;
    private String zn_track2 = "";
    private String zn_track3 = "";
    private String zn_passbookAccount = "";
    private String zn_recvCardType = "";

    private String zn_billCode = "";
    private String zn_billTime = "";

    private String cmd = "";
    private String p1 = "";
    private String inbuf = "";
    private String timeout = "";
    private String channelCode = "";

    private String goodsTotalNum = "";//商品总数
    private String goodsInfo = "";//商品信息
    private String operId = "";//操作员
    /**
     * base
     *
     * @param optype
     * @return
     */
    private E_REQ_RETURN pos_base(E_OP_TYPE optype) {
        Logz.i(TAG, "return busy,state_op:"+ state_op+",_IsBusy:" + pmLfPosApiMispos1._IsBusy());
        if (state_op == E_REQ_RETURN.REQ_BUSY || pmLfPosApiMispos1._IsBusy()) {
            Logz.i(TAG, "return busy");
            return E_REQ_RETURN.REQ_BUSY;
        }

        if (CheckApiState(optype, E_API_STATE.UNKNOW)) {
            Logz.i(TAG, "return deny");
            return E_REQ_RETURN.REQ_DENY;
        }
        synchronized (lockApi) {
            state_op = E_REQ_RETURN.REQ_BUSY;
            zn_op_type = optype;

            //异步调用
            Thread task = new Thread(new Runnable() {
                public void run() {
                    _03_Common t = new _03_Common();
                    REPLY mREPLY = new REPLY();
                    Object obj = null;
                    String info = "";
                    int reply = 0;
                    E_ERR_CODE err_code = E_ERR_CODE.ERR_UNKNOW;
                    String amount_str = String.format("%012d", amount_fen);
                    Message msg = new Message();
                    msg.arg1 = 0;
                    msg.obj = null;
                    mREPLY.reply = MISPOS.PACK_NAK;
                    dpl.setType("");
                    dpl.setMsg("");
                    mREPLY.code = "";
                    mREPLY.code_info = "";
                    lastOP = zn_op_type;//E_OP_TYPE.OP_POS_WITHDRAW;
                    if (amount_fen < 1) {
                        state_op = E_REQ_RETURN.REQ_OK;
                        //异常返回
                        msg.arg2 = 0;//MISPOS.PACK_NAK;//NAK
                        reply = 1;
                        info = "金额不能小于1分";
                        err_code = E_ERR_CODE.ERR_OTHRER;
                        obj = mREPLY;
                        msg.obj = (new CallbackMsg(err_code, zn_op_type, reply, state_api, info, obj, dpl));
                        if (isUseSynch()) {//同步返回
                            state_op.setObj(msg.obj);
                        } else {//异步返回
                            sendMessage(msg);
                        }
                    } else {
                        try {
                            Logz.i(TAG, "call " + zn_op_type.getDesc() + "...");
                            if (zn_keyboard.length() > 0) {
                                Logz.i(TAG, ">>>>>>>zn_keyboard:" + zn_keyboard);
                                t.setSignin_keyboard(zn_keyboard);
                            }
                            if (zn_op_type == E_OP_TYPE.OP_POS_PURCHASE) {
                                Logz.i(TAG,"raoj------消费6---pos_base---");
                                t = new _03_Purchase();
                                t.setTradeType(TradeType.PURCHASE);
                                ((_03_Purchase) t).setAmount(amount_str);
                                ((_03_Purchase) t).setOperator(operId);
                                ((_03_Purchase) t).setGoodsInfo(goodsInfo);
                                ((_03_Purchase) t).setGoodsTotalNum(goodsTotalNum);
                                mREPLY = pmLfPosApiMispos1._03_Trade(t);
                                obj = (_04_PurchaseReply) mREPLY;
                            } else if (zn_op_type == E_OP_TYPE.OP_POS_SIGNIN) {
                                Logz.i(TAG,"raoj------签到6---pos_base");
//                                t.setTimeout((byte) 120);
                                t.setTimeout((byte) 60);
                                t.setTradeType(TradeType.SIGNIN);
                                mREPLY = pmLfPosApiMispos1._03_Signin(t);
                                obj = mREPLY;
                            } else if (zn_op_type == E_OP_TYPE.OP_POS_SETTLE) {
                                t.setTradeType(TradeType.SETTLE);
                                mREPLY = pmLfPosApiMispos1._03_Settle(t);
                                t.setTradeType(TradeType.SIGNIN);
                                mREPLY = pmLfPosApiMispos1._03_Signin(t);
                                obj = mREPLY;
                            } else if (zn_op_type == E_OP_TYPE.OP_POS_REFUND) {
                                if (pos_getType() == 0) {
                                    t = new _03_Refund();//设置请求参数
                                    t.setTradeType(TradeType.REFUND);//交易类型
                                    ((_03_Refund) t).setCardNo(cardNo);
                                    ((_03_Refund) t).setAmount(amount_str);//金额，单位：分
                                    ((_03_Refund) t).setSpecTmnSerial(zn_tradeSerial);
                                    mREPLY = pmLfPosApiMispos1._03_Refund(t);//发起请求
                                } else {//银行卡及其他
                                    t = new _03_RefundPurchase();
                                    t.setTradeType(TradeType.REFUNDPURCHASE);
                                    t.setMer(gr_mer);
                                    t.setTmn(gr_tmn);
                                    ((_03_RefundPurchase) t).setAmount(amount_str);
                                    ((_03_RefundPurchase) t).setVoucher(zn_tradeSerial);
                                    mREPLY = pmLfPosApiMispos1._03_RefundPurchase(t);
                                }
                                obj = (_04_GetPrintInfoReply) mREPLY;
                            } else if (zn_op_type == E_OP_TYPE.OP_POS_GETRECORD) {
                                t = new _03_GetRecord();
                                t.setTradeType(TradeType.GET_RECORD);
                                t.setMer(zn_merNo);
                                t.setTmn(zn_tmnNo);
                                ((_03_GetRecord) t).setSerialNo(gr_poswater);
                                mREPLY = pmLfPosApiMispos1._03_GetRecordReply(t);
                                obj = (_04_GetRecordReply) mREPLY;
                            } else if (zn_op_type == E_OP_TYPE.OP_POS_GETPRINTINFO) {
                                t = new _03_GetPrintInfo();
                                t.setTradeType(TradeType.GET_PRINT_INFO);
                                ((_03_GetPrintInfo) t).setSerialNo(zn_serialNo);
                                mREPLY = pmLfPosApiMispos1._03_GetPrintInfo(t);
                                obj = (_04_GetPrintInfoReply) mREPLY;
                                ////////////////////////////////////////////////////////////////////
                            } else if (zn_op_type == E_OP_TYPE.OP_POS_QUERY) {
                                pos_setType(0);
                                t.setTradeType(TradeType.QUERY);
                                mREPLY = pmLfPosApiMispos1._03_Query(t);
                                obj = (_04_QueryReply) mREPLY;
                            } else if (zn_op_type == E_OP_TYPE.OP_MEMBER_RECHARGE) {
                                Logz.i(TAG,"raoj------俊鹏充值6---pos_base");
                                pos_setType(0);
                                t = new _03_Recharge();
                                t.setTradeType(TradeType.RECHARGE);
                                ((_03_Recharge) t).setAmount(amount_str);
                                ((_03_Recharge) t).setOperator(operId);
                                mREPLY = pmLfPosApiMispos1._03_Recharge(t);
                                obj = (_04_PurchaseReply) mREPLY;
                                ////////////////////////////////////神思饭卡////////////////////////////////
                            } else if (zn_op_type == E_OP_TYPE.OP_POS_QUERY_SHENSI_FANKA) {
                                Logz.i(TAG,"raoj------神思饭卡余额查询6---pos_base");
                                pos_setType(0);
                                t = new _03_Shensi_Query();
                                t.setTradeType(TradeType.QUERY_SHENSI_FANKA);
                                ((_03_Shensi_Query) t).setOperator(operId);
                                mREPLY = pmLfPosApiMispos1._03_Query(t);
                                obj = (_04_QueryReply) mREPLY;
                            } else if (zn_op_type == E_OP_TYPE.OP_MEMBER_RECHARGE_SHENSI_FANKA) {
                                Logz.i(TAG,"raoj------神思饭卡充值6---pos_base");
                                pos_setType(0);
                                t = new _03_Shensi_Recharge();
                                t.setTradeType(TradeType.RECHARGE_SHENSI_FANKA);
                                ((_03_Shensi_Recharge) t).setAmount(amount_str);
                                ((_03_Shensi_Recharge) t).setOperator(operId);
                                mREPLY = pmLfPosApiMispos1._03_Recharge(t);
                                obj = (_04_PurchaseReply) mREPLY;
                                ////////////////////////////////////江西农信////////////////////////////////
                            } else if (zn_op_type == E_OP_TYPE.OP_POS_WITHDRAW) {
                                t = new _03_Withdraw();
                                t.setTradeType(TradeType.WITHDRAW);//交易类型
                                ((_03_Withdraw) t).setAmount(amount_str);//金额，单位：分
                                ((_03_Withdraw) t).setKeyboard(zn_keyboard);
                                mREPLY = pmLfPosApiMispos1._03_ZN_Withdraw((_03_Withdraw) t);
                                obj = mREPLY;
                            } else if (zn_op_type == E_OP_TYPE.OP_POS_GET_CARDREADER) {
                                t = new _03_GetCardReader();
                                t.setTradeType(TradeType.GET_CARDREADER);
                                ((_03_GetCardReader) t).setCmd(cmd);
                                ((_03_GetCardReader) t).setP1(p1);
                                ((_03_GetCardReader) t).setInBuf(inbuf);
                                ((_03_GetCardReader) t).setTimeout_s(timeout);
                                mREPLY = pmLfPosApiMispos1._03_Get_CardReader((_03_GetCardReader) t);
                                obj = mREPLY;
                            } else if (zn_op_type == E_OP_TYPE.OP_POS_CASHREM) {
                                t = new _03_Cashrem();
                                t.setTradeType(TradeType.CASHREM);//交易类型
                                ((_03_Cashrem) t).setAmount(amount_str);//金额，单位：分
                                ((_03_Cashrem) t).setRecieverCardNo(zn_recvCardNo);//收款卡号
                                ((_03_Cashrem) t).setRecieverName(zn_recvName);//收款人姓名
                                ((_03_Cashrem) t).setKeyboard(zn_keyboard);
                                mREPLY = pmLfPosApiMispos1._03_ZN_Cashrem((_03_Cashrem) t);
                                obj = mREPLY;
                            } else if (zn_op_type == E_OP_TYPE.OP_POS_TRANSFERREM) {
                                t = new _03_Transferrem();
                                t.setTradeType(TradeType.TRANFERREM);//交易类型
                                ((_03_Transferrem) t).setAmount(amount_str);//金额，单位：分
                                ((_03_Transferrem) t).setRecieverCardNo(zn_recvCardNo);//收款卡号
                                ((_03_Transferrem) t).setRecieverName(zn_recvName);//收款人姓名
                                ((_03_Transferrem) t).setPayerName(zn_payName);//付款人姓名
                                ((_03_Transferrem) t).setKeyboard(zn_keyboard);
                                mREPLY = pmLfPosApiMispos1._03_ZN_Transferrem((_03_Transferrem) t);
                                obj = mREPLY;
                            } else if (zn_op_type == E_OP_TYPE.OP_POS_PASSBOOKRENEW) {
                                t = new _03_PassbookRenew();
                                t.setTradeType(TradeType.PASSBOOKRENEW);//交易类型
                                ((_03_PassbookRenew) t).setReqNum(zn_reqNum);
                                ((_03_PassbookRenew) t).setVoucherNo(zn_voucherNo);
                                ((_03_PassbookRenew) t).setAcountNo(zn_cardNo);
                                mREPLY = pmLfPosApiMispos1._03_ZN_PassbookRenew((_03_PassbookRenew) t);
                                obj = (_04_PassbookRenewReply) mREPLY;
                            } else if (zn_op_type == E_OP_TYPE.OP_POS_DOWNLOADPAYCODE) {
                                t = new _03_DownloadAppcode();
                                t.setTradeType(TradeType.DOWNLOADPAYCODE);//交易类型
                                ((_03_DownloadAppcode) t).setZnTradeType(zn_znTradeType);
                                ((_03_DownloadAppcode) t).setStartDate(zn_startDate);
                                ((_03_DownloadAppcode) t).setEndDate(zn_endDate);
                                mREPLY = pmLfPosApiMispos1._03_ZN_DownloadAppcode((_03_DownloadAppcode) t);//发起请求
                                obj = (_04_DownloadAppcodeReply) mREPLY;
                            } else if (zn_op_type == E_OP_TYPE.OP_POS_GETZNRECORDINFO) {
                                t = new _03_GetZNRecordInfo();
                                t.setTradeType(TradeType.GETZNRECORDINFO);//交易类型
                                ((_03_GetZNRecordInfo) t).setVoucherNo(zn_tradeSerial);
                                mREPLY = pmLfPosApiMispos1._03_ZN_GetZNRecordInfo((_03_GetZNRecordInfo) t);//发起请求
                                obj = (_04_GetZNRecordInfoReply) mREPLY;
                            } else if (zn_op_type == E_OP_TYPE.OP_POS_GET_CARDNO) {//
                                t = new _03_ReadCardNo();
                                t.setTradeType(TradeType.GET_CARDNO);//交易类型
                                mREPLY = pmLfPosApiMispos1._03_ZN_ReadCardNo((_03_ReadCardNo) t);//发起请求
                                obj = (_04_ReadCardNoReply) mREPLY;
                            } else if (zn_op_type == E_OP_TYPE.OP_POS_GET_ACCOUNTNAME) {
                                t = new _03_ReadAccountName();
                                t.setTradeType(TradeType.GET_ACCOUNTNAME);//交易类型
                                ((_03_ReadAccountName) t).setCardNo(zn_cardNo);
                                mREPLY = pmLfPosApiMispos1._03_ZN_ReadAccountName((_03_ReadAccountName) t);//发起请求
                                obj = (_04_ReadAccountNameReply) mREPLY;
                            } else if (zn_op_type == E_OP_TYPE.OP_POS_PAYFEES_QUERY) {//
                                t = new _03_PayfeesQuery();
                                t.setTradeType(TradeType.PAYFEES_QUERY);//交易类型
                                ((_03_PayfeesQuery) t).setAreaCode(areaCode);
                                ((_03_PayfeesQuery) t).setIndustryCode(industryCode);
                                ((_03_PayfeesQuery) t).setContentCode(contentCode);
                                ((_03_PayfeesQuery) t).setUserNo(userNo);
                                mREPLY = pmLfPosApiMispos1._03_ZN_PayfeesQuery((_03_PayfeesQuery) t);//发起请求
                                obj = (_04_PayfeesQueryReply) mREPLY;
                            } else if (zn_op_type == E_OP_TYPE.OP_POS_PAYFEES) {
                                t = new _03_Payfees();
                                t.setTradeType(TradeType.PAYFEES);//交易类型
                                ((_03_Payfees) t).setAmount(amount_str);//金额，单位：分
                                ((_03_Payfees) t).setAreaCode(areaCode);
                                ((_03_Payfees) t).setIndustryCode(industryCode);
                                ((_03_Payfees) t).setContentCode(contentCode);
                                ((_03_Payfees) t).setUserNo(userNo);
                                ((_03_Payfees) t).setPreQueryReply(prePayFeesQueryReply);
                                mREPLY = pmLfPosApiMispos1._03_ZN_Payfees((_03_Payfees) t);//发起请求
                                obj = mREPLY;
                            } else if (zn_op_type == E_OP_TYPE.OP_POS_BOUNDACCOUNTQUERY) {//
                                t = new _03_BoundAccountQuery();
                                t.setTradeType(TradeType.BOUNDACCOUNT_QUERY);//交易类型
                                mREPLY = pmLfPosApiMispos1._03_ZN_BoundAccountQuery((_03_BoundAccountQuery) t);//发起请求
                                obj = (_04_BoundAccountQueryReply) mREPLY;
                            } else if (zn_op_type == E_OP_TYPE.OP_POS_PASSBOOKTOCARD) {
                                t = new _03_PassbookToCard();
                                t.setTradeType(TradeType.PASSBOOK_TO_CARD);//交易类型
                                ((_03_PassbookToCard) t).setAmount(amount_str);//金额，单位：分
                                ((_03_PassbookToCard) t).setTrack2(zn_track2);
                                ((_03_PassbookToCard) t).setTrack3(zn_track3);
                                ((_03_PassbookToCard) t).setRecvCardNo(zn_recvCardNo);
                                ((_03_PassbookToCard) t).setRecvName(zn_recvName);
                                ((_03_PassbookToCard) t).setPassbookAccount(zn_passbookAccount);
                                ((_03_PassbookToCard) t).setKeyboard(zn_keyboard);
                                ((_03_PassbookToCard) t).setRecvCardType(zn_recvCardType);
                                mREPLY = pmLfPosApiMispos1._03_ZN_PassbookToCard((_03_PassbookToCard) t);
                                obj = mREPLY;
                            } else if (zn_op_type == E_OP_TYPE.OP_POS_GETKEY_REQ) {
                                t = new _03_GetKeyRequest();
                                t.setTradeType(TradeType.GETKEY_REQ);//交易类型
                                Logz.i(TAG, ">>>>>>>>>>pos_getkey zn_timeout:" + ((int) (zn_timeout)));
                                ((_03_GetKeyRequest) t).setTimeout(zn_timeout);
                                mREPLY = pmLfPosApiMispos1._03_ZN_GetKeyRequest(((_03_GetKeyRequest) t));//发起请求
                                obj = mREPLY;
                            } else if (zn_op_type == E_OP_TYPE.OP_POS_GET_MER_TMN) {
                                t = new _03_Common();
                                t.setTradeType(TradeType.GET_MERTMN);//交易类型
                                mREPLY = pmLfPosApiMispos1._04_GetMerTmn(t);
                                obj = mREPLY;
                            } else if (zn_op_type == E_OP_TYPE.OP_POS_GET_KEYBOARD) {
                                t = new _03_GetKeyboard();
                                t.setTradeType(TradeType.GET_KEYBOARD);
                                mREPLY = pmLfPosApiMispos1._03_ZN_GetKeyboard((_03_GetKeyboard) t);
                                obj = (_04_GetKeyboardReply) mREPLY;
                            } else if (zn_op_type == E_OP_TYPE.OP_POS_TEST_MK210) {
                                t = new _03_TestMK210();
                                t.setTradeType(TradeType.TEST_MK210);
                                mREPLY = pmLfPosApiMispos1._03_ZN_TestMK210((_03_TestMK210) t);
                                obj = mREPLY;
                            } else if (zn_op_type == E_OP_TYPE.OP_POS_TEST_WITHDRAW) {
                                t = new _03_TestZNWithdraw();
                                t.setTradeType(TradeType.TEST_ZNWITHDRAW);
                                ((_03_TestZNWithdraw) t).setAmount(amount_str);
                                ((_03_TestZNWithdraw) t).setKeyboard(zn_keyboard);
                                mREPLY = pmLfPosApiMispos1._03_ZN_TestWithdraw((_03_TestZNWithdraw) t);
                                obj = mREPLY;
                            } else if (zn_op_type == E_OP_TYPE.OP_POS_TEST_GETZNRECORDINFO) {
                                t = new _03_TestGetZNRecordInfo();
                                t.setTradeType(TradeType.TEST_ZNRECORDINFO);
                                ((_03_TestGetZNRecordInfo) t).setVoucherNo(zn_tradeSerial);
                                mREPLY = pmLfPosApiMispos1._03_ZN_TestGetZNRecordInfo((_03_TestGetZNRecordInfo) t);
                                obj = (_04_TestGetZNRecordInfoReply) mREPLY;
                            } else if (zn_op_type == E_OP_TYPE.OP_POS_TEST_TRANSFERREM) {
                                t = new _03_TestZNTransferrem();
                                t.setTradeType(TradeType.TEST_ZNTRANFERREM);
                                ((_03_TestZNTransferrem) t).setAmount(amount_str);
                                ((_03_TestZNTransferrem) t).setRecieverCardNo(zn_recvCardNo);
                                ((_03_TestZNTransferrem) t).setRecieverName(zn_recvName);
                                ((_03_TestZNTransferrem) t).setPayerName(zn_payName);
                                ((_03_TestZNTransferrem) t).setKeyboard(zn_keyboard);
                                mREPLY = pmLfPosApiMispos1._03_ZN_TestTransferrem((_03_TestZNTransferrem) t);
                                obj = mREPLY;
                            } else if (zn_op_type == E_OP_TYPE.OP_POS_CHECKBALANCE) {
                                t = new _03_CheckBalance();
                                t.setTradeType(TradeType.CHECK_BALANCE);
                                ((_03_CheckBalance) t).setTrack2(zn_track2);
                                ((_03_CheckBalance) t).setTrack3(zn_track3);
                                mREPLY = pmLfPosApiMispos1._03_ZN_CheckBalance((_03_CheckBalance) t);
                                obj = mREPLY;
                            } else if (zn_op_type == E_OP_TYPE.OP_POS_TRANSQUERY) {
                                t = new _03_TransQuery();
                                t.setTradeType(TradeType.TRANS_QUERY);//交易类型
                                ((_03_TransQuery) t).setStartDate(zn_startDate);
                                ((_03_TransQuery) t).setEndDate(zn_endDate);
                                mREPLY = pmLfPosApiMispos1._03_GetTransQuery((_03_TransQuery) t);
                                obj = (_04_TransQueryReply) mREPLY;
                            } else if (zn_op_type == E_OP_TYPE.OP_POS_SEND_TMK) {
                                t = new _03_SendTMK();
                                t.setTradeType(TradeType.SEND_TMK);
                                mREPLY = pmLfPosApiMispos1._03_Test_SendTMK((_03_SendTMK) t);
                                obj = mREPLY;
                            } else if (zn_op_type == E_OP_TYPE.OP_POS_SET_MER_TMN) {
                                t = new _03_SetMerTmn();
                                t.setTradeType(TradeType.SET_MERTMN);
                                ((_03_SetMerTmn) t).setMer(zn_merNo);
                                ((_03_SetMerTmn) t).setTmn(zn_tmnNo);
                                mREPLY = pmLfPosApiMispos1._03_ZN_SetPara((_03_SetMerTmn) t);
                                obj = mREPLY;
                                ////////////////////////////////////厦门停车场////////////////////////////////
                            } else if (zn_op_type == E_OP_TYPE.OP_POS_CTB_TRADE) {
                                t = new _03_PosCSBTrade();
                                t.setTradeType(TradeType.CTB_PURCHASE);
                                ((_03_PosCSBTrade) t).setAmount(amount_str);
                                mREPLY = pmLfPosApiMispos1._03_PosCSBTrade((_03_PosCSBTrade) t);
                                obj = mREPLY;
                            } else if (zn_op_type == E_OP_TYPE.OP_POS_CTB_QUERY) {
                                t = new _03_PosCSBQuery();
                                t.setTradeType(TradeType.CTB_QUERY);
                                ((_03_PosCSBQuery) t).setBillCode(zn_billCode);
                                ((_03_PosCSBQuery) t).setBillTime(zn_billTime);
                                mREPLY = pmLfPosApiMispos1._03_PosCSBQuery((_03_PosCSBQuery) t);
                                obj = mREPLY;
                            } else if (zn_op_type == E_OP_TYPE.OP_POS_TWOINONE) {
                                t = new _03_TwoInOne();
                                t.setTradeType(TradeType.TWO_IN_ONE);
                                ((_03_TwoInOne) t).setAmount(amount_str);
                                mREPLY = pmLfPosApiMispos1._03_TwoInOne((_03_TwoInOne) t);
                                obj = mREPLY;
                            } else if (zn_op_type == E_OP_TYPE.OP_POS_THREEINONE) {
                                t = new _03_ThreeInOne();
                                t.setTradeType(TradeType.THREE_IN_ONE);
                                ((_03_ThreeInOne) t).setAmount(amount_str);
                                mREPLY = pmLfPosApiMispos1._03_ThreeInOne((_03_ThreeInOne) t);
                                obj = mREPLY;
                            } else if (zn_op_type == E_OP_TYPE.OP_POS_BTC_CANCEL) {
                                t = new _03_BSCCancel();
                                t.setTradeType(TradeType.BTC_CANCEL);
                                ((_03_BSCCancel) t).setAmount(amount_str);
                                mREPLY = pmLfPosApiMispos1._03_BSCCancel((_03_BSCCancel) t);
                                obj = mREPLY;
                                /////////////////////////////////预授权///////////////////////////////////
                            } else if (zn_op_type == E_OP_TYPE.OP_POS_PREAUTHOR) {
                                t = new _03_PreAuthorization();
                                t.setTradeType(TradeType.PREAUTHORIZATION);
                                ((_03_PreAuthorization) t).setAmount(amount_str);
                                mREPLY = pmLfPosApiMispos1._03_Preauthor((_03_PreAuthorization) t);
                                obj = mREPLY;
                            } else if (zn_op_type == E_OP_TYPE.OP_POS_PREAUTHORCANCEL) {
                                t = new _03_PreAuthorCancel();
                                t.setTradeType(TradeType.PREAUTHORCANCEL);
                                ((_03_PreAuthorCancel) t).setAmount(amount_str);
                                ((_03_PreAuthorCancel) t).setAuthCode(authCode);
                                ((_03_PreAuthorCancel) t).setDate(date);
                                ((_03_PreAuthorCancel) t).setCardNo(cardNo);
                                ((_03_PreAuthorCancel) t).setEffectiveDate(effectiveDate);
                                mREPLY = pmLfPosApiMispos1._03_PreauthorCancel((_03_PreAuthorCancel) t);
                                obj = mREPLY;
                            } else if (zn_op_type == E_OP_TYPE.OP_POS_PREAUTHORDONE) {
                                t = new _03_PreAuthorDone();
                                t.setTradeType(TradeType.PREAUTHORDONE);
                                ((_03_PreAuthorDone) t).setAmount(amount_str);
                                ((_03_PreAuthorDone) t).setAuthCode(authCode);
                                ((_03_PreAuthorDone) t).setDate(date);
                                ((_03_PreAuthorDone) t).setCardNo(cardNo);
                                ((_03_PreAuthorDone) t).setEffectiveDate(effectiveDate);
                                mREPLY = pmLfPosApiMispos1._03_PreauthorCancel((_03_PreAuthorDone) t);
                                obj = mREPLY;
                            } else if (zn_op_type == E_OP_TYPE.OP_POS_PREAUTHORDONECANCEL) {
                                t = new _03_PreAuthorDoneCancel();
                                t.setTradeType(TradeType.PREAUTHORDONECANCEL);
                                ((_03_PreAuthorDoneCancel) t).setAmount(amount_str);
                                ((_03_PreAuthorDoneCancel) t).setVoucherNo(voucherNo);
                                ((_03_PreAuthorDoneCancel) t).setCardNo(cardNo);
                                ((_03_PreAuthorDoneCancel) t).setEffectiveDate(effectiveDate);
                                mREPLY = pmLfPosApiMispos1._03_PreauthorDoneCancel((_03_PreAuthorDoneCancel) t);
                                obj = mREPLY;
                                ////////////////////////////////////杉德////////////////////////////////
                            } else if (zn_op_type == E_OP_TYPE.OP_SAND_SMALL) {
                                t = new _03_SandSmall();
                                t.setTradeType(TradeType.SAND_SMALL_NONSECRET_PAY);
                                ((_03_SandSmall) t).setAmount(amount_str);
                                mREPLY = pmLfPosApiMispos1._03_SandSmall((_03_SandSmall) t);
                                obj = mREPLY;
                            } else if (zn_op_type == E_OP_TYPE.OP_SAND_BSC_PAY) {
                                t = new _03_SandBscPay();
                                t.setTradeType(TradeType.SAND_BSC_PAY);
                                ((_03_SandBscPay) t).setAmount(amount_str);
                                mREPLY = pmLfPosApiMispos1._03_SandBscPay((_03_SandBscPay) t);
                                obj = mREPLY;
                            } else if (zn_op_type == E_OP_TYPE.OP_SAND_BSC_REFUND) {
                                t = new _03_SandBscRefund();
                                t.setTradeType(TradeType.SAND_BSC_REFUND);
                                ((_03_SandBscRefund) t).setAmount(amount_str);
                                mREPLY = pmLfPosApiMispos1._03_SandBscRefund((_03_SandBscRefund) t);
                                obj = mREPLY;
                            } else if (zn_op_type == E_OP_TYPE.OP_SAND_BSC_RETURN) {
                                t = new _03_SandBscReturn();
                                t.setTradeType(TradeType.SAND_BSC_RETURN);
                                ((_03_SandBscReturn) t).setAmount(amount_str);
                                ((_03_SandBscReturn) t).setReferNo(refNo);
                                ((_03_SandBscReturn) t).setDate(date);
                                ((_03_SandBscReturn) t).setPaycode(paycode);
                                mREPLY = pmLfPosApiMispos1._03_SandBscReturn((_03_SandBscReturn) t);
                                obj = mREPLY;
                            } else if (zn_op_type == E_OP_TYPE.OP_SAND_CARD_TRADE) {
                                t = new _03_SandCardTrade();
                                t.setTradeType(TradeType.SAND_CARD_TRADE);
                                ((_03_SandCardTrade) t).setAmount(amount_str);
                                mREPLY = pmLfPosApiMispos1._03_SandCardTrade((_03_SandCardTrade) t);
                                obj = mREPLY;
                            } else if (zn_op_type == E_OP_TYPE.OP_SAND_KEY_REPLACE) {
                                t = new _03_SandKeyReplace();
                                t.setTradeType(TradeType.SAND_KEY_REPLACE);
                                mREPLY = pmLfPosApiMispos1._03_SandKeyReplace((_03_SandKeyReplace) t);
                                obj = mREPLY;
                            } else if (zn_op_type == E_OP_TYPE.OP_SAND_SETTLE) {
                                t = new _03_SandSettle();
                                t.setTradeType(TradeType.SAND_SETTLE);
                                mREPLY = pmLfPosApiMispos1._03_SandSettle((_03_SandSettle) t);
                                obj = mREPLY;
                            } else if (zn_op_type == E_OP_TYPE.OP_SAND_O2O_SETTLE) {
                                t = new _03_SandO2OSettle();
                                t.setTradeType(TradeType.SAND_O2O_SETTLE);
                                mREPLY = pmLfPosApiMispos1._03_SandO2OSettle((_03_SandO2OSettle) t);
                                obj = mREPLY;
                            } else if (zn_op_type == E_OP_TYPE.OP_SAND_O2O_QRCODE_TRADE) {
                                t = new _03_SandO2OQrCodeTrade();
                                t.setTradeType(TradeType.SAND_O2O_QRCODE_TRADE);
                                ((_03_SandO2OQrCodeTrade) t).setAmount(amount_str);
                                ((_03_SandO2OQrCodeTrade) t).setChannelCode(channelCode);
                                mREPLY = pmLfPosApiMispos1._03_SandO2OQrCodeTrade((_03_SandO2OQrCodeTrade) t);
                                obj = mREPLY;
                            } else if (zn_op_type == E_OP_TYPE.OP_SAND_O2O_QRCODE_QUERY) {
                                t = new _03_SandO2OQrCodeQuery();
                                t.setTradeType(TradeType.SAND_O2O_QRCODE_QUERY);
                                mREPLY = pmLfPosApiMispos1._03_SandO2OQrCodeQuery((_03_SandO2OQrCodeQuery) t);
                                obj = mREPLY;
                            } else if (zn_op_type == E_OP_TYPE.OP_SAND_O2O_LOOP) {
                                t = new _03_SandO2OLoop();
                                t.setTradeType(TradeType.SAND_O2O_LOOP);
                                ((_03_SandO2OLoop) t).setAmount(amount_str);
                                ((_03_SandO2OLoop) t).setChannelCode(channelCode);
                                mREPLY = pmLfPosApiMispos1._03_SandO2OLoop((_03_SandO2OLoop) t);
                                obj = mREPLY;
                            } else if (zn_op_type == E_OP_TYPE.OP_SAND_O2O_RESULT) {
                                t = new _03_SandO2OResult();
                                t.setTradeType(TradeType.SAND_O2O_QUERY_RESULT);
                                ((_03_SandO2OResult) t).setOrderNo(orderNo);
                                mREPLY = pmLfPosApiMispos1._03_SandO2OResult((_03_SandO2OResult) t);
                                obj = mREPLY;
                            } else if (zn_op_type == E_OP_TYPE.OP_SAND_O2O_CANCEL) {
                                t = new _03_SandO2OCancel();
                                t.setTradeType(TradeType.SAND_O2O_CANCEL);
                                ((_03_SandO2OCancel) t).setOrderNo(orderNo);
                                mREPLY = pmLfPosApiMispos1._03_SandO2OCandel((_03_SandO2OCancel) t);
                                obj = mREPLY;
                            } else if (zn_op_type == E_OP_TYPE.OP_SAND_O2O_REFUND) {
                                t = new _03_SandO2ORefund();
                                t.setTradeType(TradeType.SAND_O2O_REFUND);
                                ((_03_SandO2ORefund) t).setOrderNo(orderNo);
                                ((_03_SandO2ORefund) t).setAmount(amount_str);
                                mREPLY = pmLfPosApiMispos1._03_SandO2ORefund((_03_SandO2ORefund) t);
                                obj = mREPLY;
                            } else if (zn_op_type == E_OP_TYPE.OP_SAND_O2O_SIGNIN) {
                                t = new _03_SandO2OSignin();
                                t.setTradeType(TradeType.SAND_O2O_SIGNIN);
                                mREPLY = pmLfPosApiMispos1._03_SandO2OSignin((_03_SandO2OSignin) t);
                                obj = mREPLY;
                            } else if (zn_op_type == E_OP_TYPE.OP_SAND_DELIVERY_KEY) {
                                t = new _03_SandPosDeliveryKey();
                                t.setTradeType(TradeType.SAND_DELIVERY_KEY);
                                mREPLY = pmLfPosApiMispos1._03_SandPosDeliveryKey((_03_SandPosDeliveryKey) t);
                                obj = mREPLY;
                            } else if (zn_op_type == E_OP_TYPE.OP_SAND_SYNC_MESSAGE) {
                                t = new _03_SandSyncMessage();
                                t.setTradeType(TradeType.SAND_SYNC_MESSAGE);
                                mREPLY = pmLfPosApiMispos1._03_SandSyncMessage((_03_SandSyncMessage) t);
                                obj = mREPLY;
                                ////////////////////////////////////TMS/////////////////////////////
                            } else if (zn_op_type == E_OP_TYPE.TMS_ONLINE_REPORT) {
                                t.setTradeType(TradeType.TMS_ONLINE_REPORT);
                                mREPLY = pmLfPosApiMispos1._03_TmsOnlineReport(t);
                                obj = mREPLY;
                            } else if (zn_op_type == E_OP_TYPE.TMS_REMOTE_DOWNLOAD) {
                                t.setTradeType(TradeType.TMS_REMOTE_DOWNLOAD);
                                mREPLY = pmLfPosApiMispos1._03_TmsRemoteDownload(t);
                                obj = mREPLY;
                            } else if (zn_op_type == E_OP_TYPE.TMS_TMN_INFO_SEND) {
                                t.setTradeType(TradeType.TMS_TMN_INFO_SEND);
                                mREPLY = pmLfPosApiMispos1._03_TmsTmnInfoSend(t);
                                obj = mREPLY;
                            } else if (zn_op_type == E_OP_TYPE.TMS_DOWNLOAD_CONFIRM_NOTICE) {
                                t.setTradeType(TradeType.TMS_DOWNLOAD_CONFIRM_NOTICE);
                                mREPLY = pmLfPosApiMispos1._03_TmsDownloadConfirmNotice(t);
                                obj = mREPLY;
                            } else if (zn_op_type == E_OP_TYPE.TMS_DOWNLOAD_KEY) {
                                t.setTradeType(TradeType.TMS_DOWNLOAD_KEY);
                                mREPLY = pmLfPosApiMispos1._03_TmsDownloadKey(t);
                                obj = mREPLY;
                                ////////////////////////////////////////////////////////////////////
                            } else if (zn_op_type == E_OP_TYPE.REBOOT) {
                                t.setTradeType(TradeType.REBOOT);
                                mREPLY = pmLfPosApiMispos1._03_Reboot(t);
                                obj = mREPLY;
                            } else if (zn_op_type == E_OP_TYPE.OP_POS_TEST) {
                                mREPLY = pmLfPosApiMispos1._01_Test();
                                obj = mREPLY;
                            } else {
                                mREPLY.code = "";
                                mREPLY.code_info = "无接口";
                                Logz.i(TAG, "call  NONE !!!!!");
                            }

                            err_code = CheckNAK(mREPLY);
                            Logz.i(TAG, "" + zn_op_type.getDesc() + " done:" + (int) mREPLY.reply + "," + mREPLY.code + ", " + mREPLY.code_info + ",errcode:" + err_code);
                            if (mREPLY.reply == MISPOS.PACK_ACK) {
                                state_api = E_API_STATE.SIGNIN_OK;
                            }
                            //设置正常返回结果
                            msg.arg2 = 0;//mREPLY.reply;//返回标志(MISPOS.PACK_NAK/MISPOS.PACK_ACK)
                            msg.obj = 0;//mREPLY;
                            reply = mREPLY.reply == MISPOS.PACK_ACK ? 0 : 1;
                            //TODO:如果成功，交易结果组合
                            info = mREPLY.code + "#" + mREPLY.code_info;//mREPLY.reply==MISPOS.PACK_ACK?"交易成功":"交易失败";
                            if (zn_op_type.getDesc().equals(E_OP_TYPE.OP_POS_SIGNIN.getDesc()) && mREPLY.code_info.equals("交易成功")) {
                                zn_op_type = E_OP_TYPE.OP_POS_SIGNIN;
                                mREPLY.code_info = "签到操作,交易成功";
                                dpl.setType(mREPLY.code);
                                dpl.setMsg("签到操作,交易成功");
                            } else if (zn_op_type.getDesc().equals(E_OP_TYPE.OP_POS_PURCHASE.getDesc()) && mREPLY.code_info.equals("交易成功")) {
                                zn_op_type = E_OP_TYPE.OP_POS_PURCHASE;
                                mREPLY.code_info = "POS支付操作,交易成功";
                                dpl.setType(mREPLY.code);
                                dpl.setMsg("POS支付操作,交易成功");
                            } else {
                                dpl.setType(mREPLY.code);
                                dpl.setMsg(mREPLY.code_info);
                            }
                        } catch (LfException e) {
                            Logz.i(TAG, e.getMessage());
                            if (e.getMessage().contains("Serial port timeout!")) {
                                zn_op_type = E_OP_TYPE.OP_POS_DISPLAY;
//                                Logz.i(TAG, "串口收发超时111,zn_op_type:" + zn_op_type);
                                dpl.setType("-3");
                                dpl.setMsg(e.getMessage());
                                mREPLY.code = "-3";
                                mREPLY.code_info = e.getMessage();
                                err_code = CheckMisPosLfException(e);
                                msg.obj = (new CallbackMsg(err_code, zn_op_type, reply, state_api, info, mREPLY,dpl));
                            } else if (e.getMessage().contains("Serial port IO ERR!")) {
                                zn_op_type = E_OP_TYPE.OP_POS_DISPLAY;
//                                Logz.i(TAG, "-4,Serial port IO ERR!,串口IO错误111,zn_op_type:" + zn_op_type);
                                dpl.setType("-4");
                                dpl.setMsg(e.getMessage());
                                mREPLY.code = "-4";
                                mREPLY.code_info = e.getMessage();
                                err_code = CheckMisPosLfException(e);
                                msg.obj = (new CallbackMsg(err_code, zn_op_type, reply, state_api, info, mREPLY,dpl));
                            } else {
                                mREPLY.code = "ER";
                                mREPLY.code_info = e.getMessage();
                                //异常返回
                                msg.arg2 = 0;//MISPOS.PACK_NAK;//
                                reply = 1;
                                info = e.getMessage();//LfException
                                err_code = CheckMisPosLfException(e);
                                msg.obj = (new CallbackMsg(err_code, zn_op_type, reply, state_api, info, mREPLY,dpl));
                            }
                        } finally {
                            state_op = E_REQ_RETURN.REQ_OK;
                            msg.obj = (new CallbackMsg(err_code, zn_op_type, reply, state_api, info, mREPLY,dpl));
//                            Logz.i(TAG, "send " + zn_op_type.getDesc() + " ,dpl:" + dpl.getMsg()
//                                    + " ,zn_op_type:" + zn_op_type);
                            if (isUseSynch()) {//同步返回
//                                Logz.i(TAG, "同步返回");
                                state_op.setObj(msg.obj);
                            } else {//异步返回
//                                Logz.i(TAG, "异步返回");
                                sendMessage(msg);
                            }
                        }
                    }
                }
            });
            if (isUseSynch()) {
                task.run();
//                Logz.i(TAG, "同步返回1");
                return state_op;
            }
//            else {
            task.start();
//            }
            return E_REQ_RETURN.REQ_OK;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public E_REQ_RETURN pos_conntest() {
        return pos_base(E_OP_TYPE.OP_POS_TEST);
    }

    /**
     * 签到
     *
     * @param keyboard 键盘类型
     * @return
     */
    private E_REQ_RETURN pos_signin(String keyboard) {
        Logz.i(TAG,"raoj------签到5---pos_signin");
        this.zn_keyboard = keyboard;
        return pos_base(E_OP_TYPE.OP_POS_SIGNIN);
    }

    /**
     * 签到，只有签到成功后才可以进行交易
     */
//    public E_REQ_RETURN pos_signin() {
//        if (state_op == E_REQ_RETURN.REQ_BUSY) {
//            if (state_op == E_REQ_RETURN.REQ_BUSY || pmLfPosApiMispos1._IsBusy()) {
//                return E_REQ_RETURN.REQ_BUSY;
//            }
//            if (CheckApiState(E_OP_TYPE.OP_POS_SIGNIN, E_API_STATE.NOT_SIGNIN)) {//无视未签到状态
//                Logz.i(TAG, "return deny");
//                return E_REQ_RETURN.REQ_DENY;
//            }
//            synchronized (lockApi) {
//                state_op = E_REQ_RETURN.REQ_BUSY;
//                //异步调用
//                Thread mMyApiTask = new Thread(new Runnable() {
//                    public void run() {
//                        _03_Common t = new _03_Common();
//                        REPLY mREPLY = new REPLY();
//                        String info = "";
//                        int reply = 0;
//                        E_ERR_CODE err_code = E_ERR_CODE.ERR_UNKNOW;
//                        Message msg = new Message();
//                        msg.arg1 = 0;//OP_SIGNIN;
//                        msg.obj = null;
//                        lastOP = E_OP_TYPE.OP_POS_SIGNIN;
//                        try {
//                            //设置请求参数
//                            t.setTradeType(TradeType.SIGNIN);//交易类型
////                        t.setSignOperator(operator);
//                            mREPLY = pmLfPosApiMispos1._03_Signin(t);//发起请求
//                            //mREPLY.code_info = "签到成功";
//                            //设置正常返回结果
//                            msg.arg2 = 0;//mREPLY.reply;//返回标志(MISPOS.PACK_NAK/MISPOS.PACK_ACK)
//                            err_code = CheckNAK(mREPLY);
//
//                            reply = mREPLY.reply == MISPOS.PACK_ACK ? 0 : 1;
//                            info = mREPLY.code + ", " + mREPLY.code_info;
//                            dpl.setType(mREPLY.code);
//                            dpl.setMsg(mREPLY.code_info);
//                            if (mREPLY.reply == MISPOS.PACK_ACK) {
//                                state_api = E_API_STATE.SIGNIN_OK;
//                            }
//                        } catch (LfException e) {
//                            mREPLY.code = "ER";
//                            mREPLY.code_info = e.getMessage();
//                            dpl.setType(mREPLY.code);
//                            dpl.setMsg(mREPLY.code_info);
//                            Log.v("POS", e.getMessage());
//                            //异常返回
//                            msg.arg2 = 0;//MISPOS.PACK_NAK;//
//                            msg.obj = 0;//e.getMessage();//LfException
//                            info = e.getMessage();
//                            reply = 1;
//                            err_code = CheckMisPosLfException(e);
//                        } finally {
//                            state_op = E_REQ_RETURN.REQ_OK;
//                            msg.obj = (new CallbackMsg(err_code, E_OP_TYPE.OP_POS_SIGNIN, reply, state_api, info, mREPLY, dpl));
//                            if (isUseSynch()) {//同步返回
//                                state_op.setObj(msg.obj);
//                            } else {//异步返回
//                                sendMessage(msg);
//                            }
//                        }
//                    }
//                });
//                if (isUseSynch()) {
//                    mMyApiTask.run();
//                    return state_op;
//                }
//                mMyApiTask.start();
//            }
//        }
//        return E_REQ_RETURN.REQ_OK;
//    }
    public E_REQ_RETURN pos_signin() {
        return pos_signin("");
    }

    /**
     * 结算，需要重新签到
     * 操作类型与签到共用
     */
    public E_REQ_RETURN pos_settle() {
        return pos_base(E_OP_TYPE.OP_POS_SETTLE);
    }

    public E_REQ_RETURN pos_getPrintInfo(String serialNo) {
        this.zn_serialNo = serialNo;
        return pos_base(E_OP_TYPE.OP_POS_GETPRINTINFO);
    }


    /**
     * 支付
     */
    public E_REQ_RETURN pos_purchase(int amount_fen) {
        this.amount_fen = amount_fen;
        return pos_base(E_OP_TYPE.OP_POS_PURCHASE);
    }

    /**
     * 支付
     */
    public E_REQ_RETURN pos_purchase(int amount_fen,String goodsTotalNum, String goodsInfo, String operId) {
        this.amount_fen = amount_fen;
        this.goodsTotalNum = goodsTotalNum;
        this.goodsInfo = goodsInfo;
        this.operId = operId;
        return pos_base(E_OP_TYPE.OP_POS_PURCHASE);
    }


    public E_REQ_RETURN pos_refund(String mer, String tmn, String cardNo, int amount_fen, String p_specTmpSerial) {
        this.gr_mer = mer;
        this.gr_tmn = tmn;
        this.amount_fen = amount_fen;
        this.cardNo = cardNo;
        this.zn_tradeSerial = p_specTmpSerial;
        return pos_base(E_OP_TYPE.OP_POS_REFUND);
    }

    /**
     * 交易查询
     */
    public E_REQ_RETURN pos_getrecord(String poswater) {
        this.gr_poswater = poswater;
        return pos_base(E_OP_TYPE.OP_POS_GETRECORD);
    }

    /**
     * 查询闪付余额，只有签到成功后才可以进行交易
     */
    public E_REQ_RETURN pos_query() {
        return pos_base(E_OP_TYPE.OP_POS_QUERY);
    }

    /**
     * 查询闪付余额，只有签到成功后才可以进行交易
     */
    public E_REQ_RETURN pos_query(String operId) {
        this.operId = operId;
        return pos_base(E_OP_TYPE.OP_POS_QUERY_SHENSI_FANKA);
    }

    public E_REQ_RETURN pos_recharge(int amount_fen) {
        this.amount_fen = amount_fen;
        return pos_base(E_OP_TYPE.OP_MEMBER_RECHARGE);
    }

    public E_REQ_RETURN pos_recharge(int amount_fen, String operId) {
        this.amount_fen = amount_fen;
        this.operId = operId;
        return pos_base(E_OP_TYPE.OP_MEMBER_RECHARGE_SHENSI_FANKA);
    }

    public E_REQ_RETURN pos_reboot() {
        return pos_base(E_OP_TYPE.REBOOT);
    }

    //////////////////////////////////////////////杉德//////////////////////////////////////////////
    public E_REQ_RETURN sand_small(int amount_fen) {
        this.amount_fen = amount_fen;
        return pos_base(E_OP_TYPE.OP_SAND_SMALL);
    }

    public E_REQ_RETURN sand_bsc_pay(int amount_fen) {
        this.amount_fen = amount_fen;
        return pos_base(E_OP_TYPE.OP_SAND_BSC_PAY);
    }

    public E_REQ_RETURN sand_bsc_refund(int amount_fen, String voucherNo) {
        this.amount_fen = amount_fen;
        this.voucherNo = voucherNo;
        return pos_base(E_OP_TYPE.OP_SAND_BSC_REFUND);
    }

    public E_REQ_RETURN sand_bsc_return(int amount_fen, String refNo, String date, String paycode) {
        this.amount_fen = amount_fen;
        this.refNo = refNo;
        this.date = date;
        this.paycode = paycode;
        return pos_base(E_OP_TYPE.OP_SAND_BSC_RETURN);
    }

    public E_REQ_RETURN sand_card_trade(int amount_fen) {
        this.amount_fen = amount_fen;
        return pos_base(E_OP_TYPE.OP_SAND_CARD_TRADE);
    }

    public E_REQ_RETURN sand_key_replace() {
        return pos_base(E_OP_TYPE.OP_SAND_KEY_REPLACE);
    }

    public E_REQ_RETURN sand_settle() {
        return pos_base(E_OP_TYPE.OP_SAND_SETTLE);
    }

    public E_REQ_RETURN sand_O2O_settle() {
        return pos_base(E_OP_TYPE.OP_SAND_O2O_SETTLE);
    }

    public E_REQ_RETURN sand_O2O_qrcode_trade(int amount_fen, String channelCode) {
        this.amount_fen = amount_fen;
        this.channelCode = channelCode;
        return pos_base(E_OP_TYPE.OP_SAND_O2O_QRCODE_TRADE);
    }

    /**
     * 杉德O2O平台二维码下单查询（SA）（暂不用）
     **/
    public E_REQ_RETURN sand_O2O_qrcode_query(String orderNo) {
        this.orderNo = orderNo;
        return pos_base(E_OP_TYPE.OP_SAND_O2O_QRCODE_QUERY);
    }

    public E_REQ_RETURN sand_O2O_loop(int amount_fen, String channelCode) {
        this.amount_fen = amount_fen;
        this.channelCode = channelCode;
        return pos_base(E_OP_TYPE.OP_SAND_O2O_LOOP);
    }

    public E_REQ_RETURN sand_O2O_result(String orderNo) {
        this.orderNo = orderNo;
        return pos_base(E_OP_TYPE.OP_SAND_O2O_RESULT);
    }

    public E_REQ_RETURN sand_O2O_cancel(String orderNo) {
        this.orderNo = orderNo;
        return pos_base(E_OP_TYPE.OP_SAND_O2O_CANCEL);
    }

    public E_REQ_RETURN sand_O2O_refund(String orderNo, int amount_fen) {
        this.orderNo = orderNo;
        this.amount_fen = amount_fen;
        return pos_base(E_OP_TYPE.OP_SAND_O2O_REFUND);
    }

    public E_REQ_RETURN sand_O2O_signin() {
        return pos_base(E_OP_TYPE.OP_SAND_O2O_SIGNIN);
    }

    public E_REQ_RETURN sand_delivery_key() {
        return pos_base(E_OP_TYPE.OP_SAND_DELIVERY_KEY);
    }

    public E_REQ_RETURN sand_sync_message() {
        return pos_base(E_OP_TYPE.OP_SAND_SYNC_MESSAGE);
    }

    /////////////////////////////////////////////TMS////////////////////////////////////////////////
    public E_REQ_RETURN tms_online_report() {
        return pos_base(E_OP_TYPE.TMS_ONLINE_REPORT);
    }

    public E_REQ_RETURN tms_remote_download() {
        return pos_base(E_OP_TYPE.TMS_REMOTE_DOWNLOAD);
    }

    public E_REQ_RETURN tms_tmn_info_send() {
        return pos_base(E_OP_TYPE.TMS_TMN_INFO_SEND);
    }

    public E_REQ_RETURN tms_download_confirm_notice() {
        return pos_base(E_OP_TYPE.TMS_DOWNLOAD_CONFIRM_NOTICE);
    }

    public E_REQ_RETURN tms_download_key() {
        return pos_base(E_OP_TYPE.TMS_DOWNLOAD_KEY);
    }

    /////////////////////////////////////////厦门停车场//////////////////////////////////////////////
    public E_REQ_RETURN pos_CTB_Trade(int amount_fen) {
        this.amount_fen = amount_fen;
        return pos_base(E_OP_TYPE.OP_POS_CTB_TRADE);
    }

    public E_REQ_RETURN pos_CTB_Query(String billCode, String billTime) {
        this.zn_billCode = billCode;
        this.zn_billTime = billTime;
        return pos_base(E_OP_TYPE.OP_POS_CTB_QUERY);
    }

    public E_REQ_RETURN pos_two_in_one(int amount_fen) {
        this.amount_fen = amount_fen;
        return pos_base(E_OP_TYPE.OP_POS_TWOINONE);
    }

    public E_REQ_RETURN pos_three_in_one(int amount_fen) {
        this.amount_fen = amount_fen;
        return pos_base(E_OP_TYPE.OP_POS_THREEINONE);
    }

    public E_REQ_RETURN pos_BTC_Cancel(int amount_fen) {
        this.amount_fen = amount_fen;
        return pos_base(E_OP_TYPE.OP_POS_BTC_CANCEL);
    }

    ////////////////////////////////////////////杭州仁盈预授权///////////////////////////////////////
    /**/
    public E_REQ_RETURN pos_preauthorization(int amount_fen) {
        this.amount_fen = amount_fen;
        return pos_base(E_OP_TYPE.OP_POS_PREAUTHOR);
    }

    public E_REQ_RETURN pos_preauthorCancel(int amount_fen, String auth_code, String date_in, String cardNo, String effectiveDate) {
        this.amount_fen = amount_fen;
        this.authCode = auth_code;
        this.date = date_in;
        this.cardNo = cardNo;
        this.effectiveDate = effectiveDate;
        return pos_base(E_OP_TYPE.OP_POS_PREAUTHORCANCEL);
    }

    public E_REQ_RETURN pos_preauthorDone(int amount_fen, String auth_code, String date_in, String cardNo, String effectiveDate) {
        this.amount_fen = amount_fen;
        this.authCode = auth_code;
        this.date = date_in;
        this.cardNo = cardNo;
        this.effectiveDate = effectiveDate;
        return pos_base(E_OP_TYPE.OP_POS_PREAUTHORDONE);
    }

    public E_REQ_RETURN pos_preauthorDoneCancel(int amount_fen, final String voucher_no, String cardNo, String effectiveDate) {
        this.amount_fen = amount_fen;
        this.voucherNo = voucher_no;
        this.cardNo = cardNo;
        this.effectiveDate = effectiveDate;
        return pos_base(E_OP_TYPE.OP_POS_PREAUTHORDONECANCEL);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 助农取款
     *
     * @param amount_fen 金额（分）
     * @return
     */
    public E_REQ_RETURN pos_zn_withdraw(int amount_fen, String keyboard) {
        this.amount_fen = amount_fen;
        this.zn_keyboard = keyboard;
        return pos_base(E_OP_TYPE.OP_POS_WITHDRAW);
    }

    public E_REQ_RETURN pos_getCardReader(String cmd, String p1, String inbuf, String timeout) {
        this.cmd = cmd;
        this.p1 = p1;
        this.inbuf = inbuf;
        this.timeout = timeout;
        return pos_base(E_OP_TYPE.OP_POS_GET_CARDREADER);
    }

    /**
     * 助农现金汇款
     *
     * @param amount_fen 金额（分）
     * @param recvCardNo 接收方卡号
     * @param recvName   接收方姓名
     * @param keyboard   keyboard “0”- MK-210 “1”-金属键盘
     * @return
     */
    public E_REQ_RETURN pos_zn_cashrem(int amount_fen, String recvCardNo, String recvName, String keyboard) {
        this.amount_fen = amount_fen;
        this.zn_recvCardNo = recvCardNo;
        this.zn_recvName = recvName;
        this.zn_keyboard = keyboard;
        return pos_base(E_OP_TYPE.OP_POS_CASHREM);
    }

    /**
     * 助农转账汇款
     *
     * @param amount_fen 金额（分）
     * @param recvCardNo 接收方卡号
     * @param recvName   接收方姓名
     * @param payerName  支付方姓名
     * @param keyboard   keyboard “0”- MK-210 “1”-金属键盘
     * @return
     */
    public E_REQ_RETURN pos_zn_transferrem(int amount_fen, String recvCardNo, String recvName, String payerName, String keyboard) {
        this.amount_fen = amount_fen;
        this.zn_recvCardNo = recvCardNo;
        this.zn_recvName = recvName;
        this.zn_payName = payerName;
        this.zn_keyboard = keyboard;
        return pos_base(E_OP_TYPE.OP_POS_TRANSFERREM);
    }

    /**
     * 存折补登
     *
     * @param reqNum    请求数
     * @param voucherNo 凭证号
     * @param cardNo    账号
     * @return
     */
    public E_REQ_RETURN pos_passbookrenew(String reqNum, String voucherNo, String cardNo) {
        this.amount_fen = 1;
        this.zn_reqNum = reqNum;
        this.zn_voucherNo = voucherNo;
        this.zn_cardNo = cardNo;
        return pos_base(E_OP_TYPE.OP_POS_PASSBOOKRENEW);
    }

    /**
     * @param znTradeType 交易类型码 (“10”-应用代码下载 “20”-交易明细查询)
     * @param startDate   查询起始日期 YYYYMMDD
     * @param endDate     查询结束日期 YYYYMMDD
     * @return
     */
    public E_REQ_RETURN pos_downloadPayAppcode(String znTradeType, String startDate, String endDate) {
        this.amount_fen = 1;
        this.zn_znTradeType = znTradeType;
        this.zn_startDate = startDate;
        this.zn_endDate = endDate;
        return pos_base(E_OP_TYPE.OP_POS_DOWNLOADPAYCODE);
    }

    /**
     * 取助农类交易信息
     *
     * @param tradeSerial 凭证号
     * @return
     */
    public E_REQ_RETURN pos_getTradeInfo(String tradeSerial) {
        this.amount_fen = 1;
        this.zn_tradeSerial = tradeSerial;
        return pos_base(E_OP_TYPE.OP_POS_GETZNRECORDINFO);
    }

    /**
     * 获取透传键值
     *
     * @return
     */
    public E_REQ_RETURN pos_getkey(byte timeout) {
        this.amount_fen = 1;
        this.zn_timeout = timeout;
        Logz.i(TAG, ">>>>>>>>>>pos_getkey timeout:" + ((int) (timeout)));
        return pos_base(E_OP_TYPE.OP_POS_GETKEY_REQ);
    }

    /**
     * 读卡号
     *
     * @return
     */
    public E_REQ_RETURN pos_readCardNo() {
        this.amount_fen = 1;
        return pos_base(E_OP_TYPE.OP_POS_GET_CARDNO);
    }

    /**
     * 读户名
     *
     * @return
     */
    public E_REQ_RETURN pos_readAccountName(String cardNo) {
        this.amount_fen = 1;
        this.zn_cardNo = cardNo;
        return pos_base(E_OP_TYPE.OP_POS_GET_ACCOUNTNAME);
    }


    /**
     * 缴费查询
     *
     * @param areaCode     4字节地区号
     * @param industryCode 3字节行业号
     * @param contentCode  3字节缴费内容码
     * @param userNo       20字节用户号
     * @return
     */
    public E_REQ_RETURN pos_payFeesQuery(String areaCode, String industryCode, String contentCode, String userNo) {
        this.amount_fen = 1;
        this.areaCode = areaCode;
        this.industryCode = industryCode;
        this.contentCode = contentCode;
        this.userNo = userNo;
        return pos_base(E_OP_TYPE.OP_POS_PAYFEES_QUERY);
    }


    /**
     * 缴费
     *
     * @param amount_fen           金额（分）
     * @param areaCode             4字节地区号
     * @param industryCode         3字节行业号
     * @param contentCode          3字节缴费内容码
     * @param userNo               20字节用户号
     * @param prePayFeesQueryReply 最多512字节，原缴费查询应答部分
     * @return
     */
    public E_REQ_RETURN pos_payFees(int amount_fen
            , String areaCode, String industryCode, String contentCode, String userNo
            , byte[] prePayFeesQueryReply) {
        this.amount_fen = amount_fen;
        this.areaCode = areaCode;
        this.industryCode = industryCode;
        this.contentCode = contentCode;
        this.userNo = userNo;
        this.prePayFeesQueryReply = prePayFeesQueryReply;
        return pos_base(E_OP_TYPE.OP_POS_PAYFEES);
    }

    public E_REQ_RETURN pos_boundAccountQuery() {
        this.amount_fen = 1;
        return pos_base(E_OP_TYPE.OP_POS_BOUNDACCOUNTQUERY);
    }

    /**
     * 存折转卡
     *
     * @param amount_fen      金额
     * @param recvCardNo      接收卡号
     * @param recvName        接收姓名
     * @param passbookAccount 存折账号
     * @param track2          磁道2
     * @param track3          磁道3
     * @param keyboard        keyboard “0”- MK-210 “1”-金属键盘
     * @return
     */
    public E_REQ_RETURN pos_passbookToCard(int amount_fen, String recvCardNo, String recvName
            , String passbookAccount, String track2, String track3, String keyboard, String recvCardType) {
        this.amount_fen = amount_fen;
        this.zn_recvCardNo = recvCardNo;
        this.zn_recvName = recvName;
        this.zn_passbookAccount = passbookAccount;
        this.zn_track2 = track2;
        this.zn_track3 = track3;
        this.zn_keyboard = keyboard;
        this.zn_recvCardType = recvCardType;
        return pos_base(E_OP_TYPE.OP_POS_PASSBOOKTOCARD);
    }


    public E_REQ_RETURN pos_getMerTmn() {
        this.amount_fen = 1;
        return pos_base(E_OP_TYPE.OP_POS_GET_MER_TMN);
    }

    public E_REQ_RETURN pos_getKeyboard() {
        return pos_base(E_OP_TYPE.OP_POS_GET_KEYBOARD);
    }

    public E_REQ_RETURN pos_testMK210() {
        return pos_base(E_OP_TYPE.OP_POS_TEST_MK210);
    }

    public E_REQ_RETURN pos_zn_testwithdraw(int amount_fen, String keyboard) {
        this.amount_fen = amount_fen;
        this.zn_keyboard = keyboard;
        return pos_base(E_OP_TYPE.OP_POS_TEST_WITHDRAW);
    }

    public E_REQ_RETURN pos_getTestTradeInfo(String tradeSerial) {
        this.amount_fen = 1;
        this.zn_tradeSerial = tradeSerial;
        return pos_base(E_OP_TYPE.OP_POS_TEST_GETZNRECORDINFO);
    }

    public E_REQ_RETURN pos_zn_testtransferrem(int amount_fen, String recvCardNo, String recvName, String payerName, String keyboard) {
        this.amount_fen = amount_fen;
        this.zn_recvCardNo = recvCardNo;
        this.zn_recvName = recvName;
        this.zn_payName = payerName;
        this.zn_keyboard = keyboard;
        return pos_base(E_OP_TYPE.OP_POS_TEST_TRANSFERREM);
    }

    public E_REQ_RETURN pos_checkBalance(String trace2, String trace3) {
        this.zn_track2 = trace2;
        this.zn_track3 = trace3;
        return pos_base(E_OP_TYPE.OP_POS_CHECKBALANCE);
    }

    public E_REQ_RETURN pos_transQuery(String startDate, String endDate) {
        this.zn_startDate = startDate;
        this.zn_endDate = endDate;
        return pos_base(E_OP_TYPE.OP_POS_TRANSQUERY);
    }


    public E_REQ_RETURN pos_send_tmk() {
        return pos_base(E_OP_TYPE.OP_POS_SEND_TMK);
    }

    public E_REQ_RETURN pos_setMertTmn() {
        return pos_base(E_OP_TYPE.OP_POS_SET_MER_TMN);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    //提示信息的回调，用于在液晶上显示
    private Display dpl = new Display();
    private String stateCode = "";
    private String stateTips = "";
    private IDisplay mIDisplay = new IDisplay() {
        @Override
        public void Display(String arg0, String arg1, String arg2) {
            dpl.setType(arg0);
            if (arg1 != null) {
                dpl.setMsg(arg1 + "," + arg2);
            } else {
                dpl.setMsg(arg2);
            }
            stateCode = arg0;
            stateTips = arg2;

            new Thread(new Runnable() {
                @Override
                public void run() {
                    getCallBack(stateCode, stateTips);
                }
            }).start();

            //异步发送
            new Thread(new Runnable() {
                public void run() {
                    E_ERR_CODE err_code = E_ERR_CODE.ERR_UNKNOW;
                    Message msg = new Message();
                    msg.arg1 = 0;//OP_POS_DISPLAY;
                    msg.arg2 = 0;//MISPOS.PACK_ACK;
                    msg.obj = (new CallbackMsg(err_code, E_OP_TYPE.OP_POS_DISPLAY, 0, state_api, "提示", dpl));
                    //msg.obj = dpl;//显示信息的对象
                    sendMessage(msg);
                }
            }).start();
        }
    };


}

