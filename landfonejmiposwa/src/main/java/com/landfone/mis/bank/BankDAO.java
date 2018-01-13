package com.landfone.mis.bank;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.landfone.common.utils.LfUtils;
import com.landfone.common.utils.Logz;
import com.landfone.mis.bean.RequestPojo;
import com.landfone.mis.bean.ResponsePojo;
import com.landfone.mis.bean.TransCfx;
import com.landfoneapi.mispos.CallbackMsg;
import com.landfoneapi.mispos.E_REQ_RETURN;
import com.landfoneapi.mispos.ILfListener;
import com.landfoneapi.misposwa.MyApi;
import com.landfoneapi.protocol.pkg._04_GetRecordReply;
import com.landfoneapi.protocol.pkg._04_QueryReply;
import com.landfoneapi.protocol.pkg.sand._04_SandO2OResultReply;
import com.landfoneapi.protocol.pkg.sand._04_SandO2OSigninReply;

import java.io.UnsupportedEncodingException;

/**
 * Created by asus on 2017/3/16.
 */

public class BankDAO {
    private String TAG = this.getClass().getSimpleName();

    private MyApi myApi = new MyApi();

    private int iRet;
    private ICallBack mCallback;
    private ResponsePojo response = new ResponsePojo();
    private String responseStr;

    private boolean isInited = false;
    private boolean isResponse = false;

    public ResponsePojo bankall(TransCfx poscfx, RequestPojo request) {
        if (poscfx.getSsl_on() == 0) {
            Log.e(TAG, "poscfx.getSsl_on() == 0");
            myApi.pos_setKeyCert(poscfx.getContext(), true, poscfx.getSsl_cert());
        } else {
            myApi.pos_setKeyCert(poscfx.getContext(), false, poscfx.getSsl_cert());
        }
        if (poscfx.getHard_on() == 0) {
            Log.e(TAG, "poscfx.getHard_on() == 0");
            myApi.pos_setHardEncrypt(poscfx.getContext(), true, poscfx.getSsl_cert());
        } else {
            myApi.pos_setHardEncrypt(poscfx.getContext(), false, poscfx.getSsl_cert());
        }
        isResponse = false;
        myApi.setILfListener(mILfMsgHandler);
        myApi.setmICallBack(mCallback);
        myApi.setTransCfx(poscfx);
        //if (!isInited) {
        myApi.pos_release();
        myApi.pos_init(poscfx.getPos_ip(), poscfx.getPos_port(), poscfx.getCom_port(), poscfx.getBaudRate());
        //}
        response = new ResponsePojo();
        int ret = CheckRequestIsRule(request);
        isResponse = true;
        switch (ret) {
            case ERR_OK:
                isResponse = false;
            case ERR_BUSY:
                Log.e(TAG, "pos返回接口忙");
                response.setRspCode("06");
                response.setRspChin("接口忙");
                break;
            case ERR_DENY:
                response.setRspCode("06");
                response.setRspChin("请求拒绝");
                break;
            case ERR_PARAMS:
                response.setRspCode("06");
                response.setRspChin("参数错误");
                break;
            case ERR_TYPE:
                response.setRspCode("06");
                response.setRspChin("交易类型错误");
                break;
            case ERR_AMT:
                response.setRspCode("06");
                response.setRspChin("金额错误:" + request.getAmount());
                break;
        }

        while (!isResponse) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return response;
    }

    public int BankCancel() {
        E_REQ_RETURN ret = myApi.pos_cancel();
        return ret.getRst();
    }

    public int getCallBack(ICallBack mCallback) {
        if (mCallback != null) {
            this.mCallback = mCallback;
            return 0;
        } else {
            Logz.w(TAG, "CallBack null");
            return 1;
        }
    }

    //////////////////////////////////////////业务返回///////////////////////////////////////////////
    private ILfListener mILfMsgHandler = new ILfListener() {
        @Override
        public void onCallback(Message msg) {
            mmHandler.sendMessage(msg);
        }
    };

    private Handler mmHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.arg1 == -9999) {
            } else {
                CallbackMsg cbmsg = (CallbackMsg) msg.obj;
                String[] rsp = cbmsg.info.split("#");
                if (rsp.length > 1) {
                    response.setRspCode(rsp[0]);
                    response.setRspChin(rsp[1]);
                } else {
                    response.setRspCode(rsp[0]);
                    if (rsp[0].equals("00")) {
                        response.setRspChin("交易成功");
                    } else {
                        response.setRspChin(cbmsg.nak_err_code.toString());
                    }
                }
                switch (cbmsg.op_type) {
                    case OP_POS_CANCEL:
                        if (cbmsg.reply == 0) {//成功
//                            response.setRspCode("00");
//                            response.setRspChin(cbmsg.info);
                        } else if (cbmsg.reply == 1) {//失败
//                            response.setRspCode(cbmsg.nak_err_code.getDesc());
//                            response.setRspChin(cbmsg.info);
                        }
                        break;
                    case OP_POS_SIGNIN://签到
                    case OP_POS_REFUND://退款
                    case OP_POS_SETTLE://结算
                    case OP_MEMBER_RECHARGE://会员卡充值
                        ///////////////////////////杉德//////////////
                    case OP_SAND_BSC_REFUND:
                    case OP_SAND_BSC_RETURN:
                    case OP_SAND_KEY_REPLACE:
                    case OP_SAND_SETTLE:
                    case OP_SAND_O2O_SETTLE:
                    case OP_SAND_O2O_QRCODE_QUERY:
                    case OP_SAND_O2O_CANCEL:
                    case OP_SAND_O2O_REFUND:
                        if (cbmsg.reply == 0) {//成功
//                            response.setRspCode("00");
//                            response.setRspChin(cbmsg.info);
                        } else if (cbmsg.reply == 1) {//失败
//                            response.setRspCode(cbmsg.nak_err_code.toString());
//                            response.setRspChin(cbmsg.info);
                        }
                        isResponse = true;
                        break;
                    case OP_SAND_O2O_SIGNIN:
                        if (cbmsg.reply == 0) {//成功
                            byte[] tmp = new byte[22];
                            byte[] channelInfo = new byte[0];
                            try {
                                channelInfo = ((_04_SandO2OSigninReply) (cbmsg.mREPLY)).getChannelInfo().getBytes("GBK");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            int channelCount = 0;
                            Logz.d(TAG, "channelInfo" + ((_04_SandO2OSigninReply) (cbmsg.mREPLY)).getChannelInfo());
                            dbg_printfWHex(channelInfo, channelInfo.length, "channelInfo");
                            channelCount += (channelInfo[1] - 48);
                            channelCount += (channelInfo[0] - 48) * 100;
                            String[] channelString = new String[channelCount];
                            for (int i = 0; i < channelCount; i++) {
                                LfUtils.memcpy(tmp, 0, channelInfo, i * 22 + 2, 22);
                                try {
                                    channelString[i] = new String(tmp, "GBK");
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                            }
                            String channel = "";
                            for (int i = 0; i < channelCount; i++) {
                                channel += channelString[i] + "&";
                            }
//                            response.setRspCode("00");
//                            response.setRspChin(cbmsg.info);
                            response.setTransMemo("渠道信息:" + channel + "&折扣信息:" + ((_04_SandO2OSigninReply) (cbmsg.mREPLY)).getDiscountInfo());
                        } else if (cbmsg.reply == 1) {//失败
//                            response.setRspCode(cbmsg.nak_err_code.toString());
//                            response.setRspChin(cbmsg.info);
                        }
                        isResponse = true;
                        break;
                    /////////////////////////支付////////////////
                    case OP_POS_PURCHASE://银行卡会员卡支付
                    case OP_SAND_SMALL://银联小额双免
                    case OP_SAND_BSC_PAY://银联B扫C
                    case OP_SAND_CARD_TRADE://杉德会员卡支付
                    case OP_SAND_O2O_QRCODE_TRADE://杉德二维码下单
                    case OP_SAND_O2O_LOOP://杉德O2O平台反复下单
                        if (cbmsg.reply == 0) {//成功
                            Log.e(TAG, " 00000000000");
                            myApi.pos_getrecord("000000");
                        } else if (cbmsg.reply == 1) {//失败
//                            response.setRspCode(cbmsg.nak_err_code.toString());
//                            response.setRspChin(cbmsg.info);
                            isResponse = true;
                            Log.e(TAG, " 11111111111");
                        }
                        break;
                    case OP_POS_GETRECORD:
                        if (cbmsg.reply == 0) {//成功
//                            response.setRspCode("00");
//                            response.setRspChin(((_04_GetRecordReply) (cbmsg.mREPLY)).code_info);
                            response.setCardNo(((_04_GetRecordReply) (cbmsg.mREPLY)).getCardNo());
                            response.setAmount(((_04_GetRecordReply) (cbmsg.mREPLY)).getTransacionAmount());
                            response.setBankCode(((_04_GetRecordReply) (cbmsg.mREPLY)).getBankCode());
                            response.setBankName(((_04_GetRecordReply) (cbmsg.mREPLY)).getCardCode());
                            response.setTraceNo(((_04_GetRecordReply) (cbmsg.mREPLY)).getTransacionVoucherNo());
                            response.setBatchNo(((_04_GetRecordReply) (cbmsg.mREPLY)).getTransacionBatchNo());
                            response.setRefNo(((_04_GetRecordReply) (cbmsg.mREPLY)).getTransacionReferNo());
                            response.setAuthNo(((_04_GetRecordReply) (cbmsg.mREPLY)).getTransacionAuthCode());
                            response.setSettleDate(((_04_GetRecordReply) (cbmsg.mREPLY)).getSettleDate());
                            response.setMerchId(((_04_GetRecordReply) (cbmsg.mREPLY)).getMer());
                            response.setTermId(((_04_GetRecordReply) (cbmsg.mREPLY)).getTmn());
                            response.setTransDate(((_04_GetRecordReply) (cbmsg.mREPLY)).getTransacionDatetime());
                            response.setTransTime(((_04_GetRecordReply) (cbmsg.mREPLY)).getTransacionDatetime());
                            if (myApi.pos_getType() == 0) {
                                response.setTransMemo("交易凭证号:" + ((_04_GetRecordReply) (cbmsg.mREPLY)).getTransacionVoucherNo() + "&" +
                                        "交易类型:" + ((_04_GetRecordReply) (cbmsg.mREPLY)).getTransType() + "&" +
                                        ((_04_GetRecordReply) (cbmsg.mREPLY)).getSpecInfoField());
                            } else if (myApi.pos_getType() == 1) {
                                response.setTransMemo("" + ((_04_GetRecordReply) (cbmsg.mREPLY)).getPaycode() + "&" +
                                        "交易类型:" + ((_04_GetRecordReply) (cbmsg.mREPLY)).getTransType() + "&");
                            } else if (myApi.pos_getType() == 92) {
                                response.setCardNo(((_04_GetRecordReply) (cbmsg.mREPLY)).getCard_no());
                                response.setAmount(((_04_GetRecordReply) (cbmsg.mREPLY)).getConsumptionAmount());
                                response.setTransDate(((_04_GetRecordReply) (cbmsg.mREPLY)).getTransactionDate());
                                response.setTransTime(((_04_GetRecordReply) (cbmsg.mREPLY)).getTransactionTime());
                                response.setTransMemo("交易前余额:" + ((_04_GetRecordReply) (cbmsg.mREPLY)).getTransBalanceBefore() + "&" +
                                        "交易流水号:" + ((_04_GetRecordReply) (cbmsg.mREPLY)).getTransSerialNo() + "&" +
                                        "分散数据:" + ((_04_GetRecordReply) (cbmsg.mREPLY)).getScatteredData() + "&" +
                                        "扣款金额:" + ((_04_GetRecordReply) (cbmsg.mREPLY)).getDebitAmount() + "&" +
                                        "类型标识:" + ((_04_GetRecordReply) (cbmsg.mREPLY)).getTypeMark() + "&" +
                                        "终端编号:" + ((_04_GetRecordReply) (cbmsg.mREPLY)).getTerminalNo() + "&" +
                                        "交易序号:" + ((_04_GetRecordReply) (cbmsg.mREPLY)).getTransactionNo() + "&" +
                                        "TC:" + ((_04_GetRecordReply) (cbmsg.mREPLY)).getTC() + "&" +
                                        "卡充值流水号:" + ((_04_GetRecordReply) (cbmsg.mREPLY)).getCardRechargeSerialNo() + "&" +
                                        "发卡方:" + ((_04_GetRecordReply) (cbmsg.mREPLY)).getIssuer() + "&" +
                                        "消费金额:" + ((_04_GetRecordReply) (cbmsg.mREPLY)).getConsumptionAmount() + "&" +
                                        "卡计数次数:" + ((_04_GetRecordReply) (cbmsg.mREPLY)).getCardCount() + "&" +
                                        "卡类型标识:" + ((_04_GetRecordReply) (cbmsg.mREPLY)).getCardTypeMark() + "&" +
                                        "交易后余额:" + ((_04_GetRecordReply) (cbmsg.mREPLY)).getTransBalanceAfter() + "&" +
                                        "柜员号:" + ((_04_GetRecordReply) (cbmsg.mREPLY)).getClerkNo() + "&" +
                                        "交易类型:" + ((_04_GetRecordReply) (cbmsg.mREPLY)).getTransactionType() + "&"
                                );
                            } else if (myApi.pos_getType() == 93) {
                                response.setCardNo(((_04_GetRecordReply) (cbmsg.mREPLY)).getCard_no());
                                response.setTransDate(((_04_GetRecordReply) (cbmsg.mREPLY)).getTransactionDate());
                                response.setTransTime(((_04_GetRecordReply) (cbmsg.mREPLY)).getTransactionTime());
                                response.setTransMemo("二维码URL:" + ((_04_GetRecordReply) (cbmsg.mREPLY)).getQrCodeUrl() + "&" +
                                        "商户账单号:" + ((_04_GetRecordReply) (cbmsg.mREPLY)).getBillCode() + "&" +
                                        "交易类型:" + ((_04_GetRecordReply) (cbmsg.mREPLY)).getTransType() + "&" +
                                        "POS订单号:" + ((_04_GetRecordReply) (cbmsg.mREPLY)).getPosOrderNo() + "&" +
                                        "用户支付账户:" + ((_04_GetRecordReply) (cbmsg.mREPLY)).getUserPaymentAcocunt() + "&"
                                );
                                Logz.d(TAG, response.getTransMemo());
                            }
                        } else if (cbmsg.reply == 1) {//失败
//                            response.setRspCode(cbmsg.nak_err_code.toString());
//                            response.setRspChin(cbmsg.info);
                        }
                        isResponse = true;
                        break;
                    case OP_SAND_O2O_RESULT:
                        if (cbmsg.reply == 0) {
//                            response.setRspCode("00");
//                            response.setRspChin(((_04_SandO2OResultReply) (cbmsg.mREPLY)).code_info);
                            response.setTransMemo(
                                    "结果:" + ((_04_SandO2OResultReply) (cbmsg.mREPLY)).code + "&" +
                                            "订单号:" + ((_04_SandO2OResultReply) (cbmsg.mREPLY)).getOrderNo() + "&" +
                                            "查询结果:" + ((_04_SandO2OResultReply) (cbmsg.mREPLY)).getQueryResult() + "&" +
                                            "后台交易号:" + ((_04_SandO2OResultReply) (cbmsg.mREPLY)).getBackstageTransNo() + "&" +
                                            "用户支付账户:" + ((_04_SandO2OResultReply) (cbmsg.mREPLY)).getUserPaymentAcocunt() + "&"
                            );
                        } else if (cbmsg.reply == 1) {//失败
//                            response.setRspCode(cbmsg.nak_err_code.toString());
//                            response.setRspChin(cbmsg.info);
                        }
                        isResponse = true;
                        break;
                    case OP_POS_QUERY:
                        if (cbmsg.reply == 0) {
//                            response.setRspCode("00");
//                            response.setRspChin(((_04_QueryReply) (cbmsg.mREPLY)).getCode_info());
                            response.setCardNo(((_04_QueryReply) (cbmsg.mREPLY)).getCardNo());//卡号
                            response.setAmount(((_04_QueryReply) (cbmsg.mREPLY)).getCashBalance());//现金余额
                            response.setTransMemo(((_04_QueryReply) (cbmsg.mREPLY)).getPointBalance());//积分余额
                        } else if (cbmsg.reply == 1) {
//                            response.setRspCode(cbmsg.nak_err_code.getDesc());
//                            response.setRspChin(cbmsg.info);
                        }
                        isResponse = true;
                        break;
                    ////////////////////////////////////////////////////////////////////////////////
                    case TMS_ONLINE_REPORT:
                        if (cbmsg.reply == 0) {//成功
                            Logz.w(TAG, "TMS_ONLINE_REPORT");
                            myApi.tms_remote_download();
                        } else if (cbmsg.reply == 1) {//失败
//                            response.setRspCode(cbmsg.nak_err_code.toString());
//                            response.setRspChin(cbmsg.info);
                            isResponse = true;
                        }
                        break;
                    case TMS_REMOTE_DOWNLOAD:
                        if (cbmsg.reply == 0) {//成功
                            Logz.w(TAG, "TMS_REMOTE_DOWNLOAD");
                            myApi.tms_tmn_info_send();
                        } else if (cbmsg.reply == 1) {//失败
//                            response.setRspCode(cbmsg.nak_err_code.toString());
//                            response.setRspChin(cbmsg.info);
                            Logz.e(TAG, cbmsg.nak_err_code.toString() + " , " + cbmsg.info);
                            if (cbmsg.info.contains("98")) {
                                myApi.tms_tmn_info_send();
                            } else {
                                isResponse = true;
                            }
                        }
                        break;
                    case TMS_TMN_INFO_SEND:
                        if (cbmsg.reply == 0) {//成功
                            Logz.w(TAG, "TMS_TMN_INFO_SEND");
                            myApi.tms_download_confirm_notice();
                        } else if (cbmsg.reply == 1) {//失败
//                            response.setRspCode(cbmsg.nak_err_code.toString());
//                            response.setRspChin(cbmsg.info);
                            isResponse = true;
                        }
                        break;
                    case TMS_DOWNLOAD_CONFIRM_NOTICE:
                        if (cbmsg.reply == 0) {//成功
                            Logz.w(TAG, "TMS_DOWNLOAD_CONFIRM_NOTICE");
                            myApi.tms_download_key();
                        } else if (cbmsg.reply == 1) {//失败
//                            response.setRspCode(cbmsg.nak_err_code.toString());
//                            response.setRspChin(cbmsg.info);
                            isResponse = true;
                        }
                        break;
                    case TMS_DOWNLOAD_KEY:
                        if (cbmsg.reply == 0) {//成功
                            Logz.d(TAG, "TMS_DOWNLOAD_KEY");
//                            response.setRspCode("00");
//                            response.setRspChin(cbmsg.info);
                        } else if (cbmsg.reply == 1) {//失败
//                            response.setRspCode(cbmsg.nak_err_code.toString());
//                            response.setRspChin(cbmsg.info);
                        }
                        isResponse = true;
                        break;
                    case OP_POS_DISPLAY://POS提示信息
                        break;
                }
            }
        }
    };

    private void dbg_printfWHex(byte[] data, int len, String format, Object... args) {
        int minlen = 0;
        String msg = "";
        msg = String.format(format, args);
        minlen = len < data.length ? len : data.length;
        System.out.print(msg + ":");
        for (int i = 0; i < minlen; i++) {
            System.out.printf("%02X ", data[i]);
        }
        System.out.println("");
    }

    /////////////////////////////////////////////指令解析////////////////////////////////////////////
    private String[] transM;
    private int amount = 0;//1分
    private String appType;
    private String mer;
    private String tmn;
    private String cardNo;
    private String serialNo;
    private String channelCode;
    private String date;
    private String paycode;

    private final static int ERR_OK = 0;//
    private final static int ERR_BUSY = 1;//
    private final static int ERR_DENY = 2;//
    private final static int ERR_PARAMS = 3;////参数错误
    private final static int ERR_TYPE = 4;//交易请求类型错误
    private final static int ERR_AMT = 5;

    public int CheckRequestIsRule(RequestPojo req) {
        E_REQ_RETURN ret = E_REQ_RETURN.REQ_OK;
        String transType = req.getTransType();
        if (transType == null) {
            return ERR_TYPE;
        }
        if (req.getAmount() != 0) {
            amount = req.getAmount();
        }
        if (req.getTransMemo() != null) {
            Logz.d(TAG, "transMemo:" + req.getTransMemo());
            transM = req.getTransMemo().split("&");
            appType = transM[0];
            Logz.d(TAG, "transM.length:" + transM.length);
            Logz.w(TAG, "appType:" + appType);
            if (appType.equals("F0")) {
                myApi.pos_setType(0);
            } else if (appType.equals("01")) {
                myApi.pos_setType(1);
            } else if (appType.equals("92")) {
                myApi.pos_setType(92);
            } else if (appType.equals("93")) {
                myApi.pos_setType(93);
            } else {
                return ERR_TYPE;
            }
        } else {
            return ERR_PARAMS;
        }

        if (transType.equals("00")) { //消费
            if (amount == 0) return ERR_AMT;
//            myApi.setUseSynch(true);
            ret = myApi.pos_purchase(amount);
        } else if (transType.equals("01")) {
            //退款
            Logz.d(TAG, "getTransMemo:" + req.getTransMemo());
            transM = req.getTransMemo().split("&");
            mer = transM[1];
            tmn = transM[2];
            cardNo = transM[3];
            serialNo = transM[4];
            if (amount == 0) return ERR_AMT;
            ret = myApi.pos_refund(mer, tmn, cardNo, amount, serialNo);
        } else if (transType.equals("02")) { //查余
            ret = myApi.pos_query();
        } else if (transType.equals("03")) { //退货，暂无
        } else if (transType.equals("04")) { //结算
            ret = myApi.pos_settle();
        } else if (transType.equals("05")) { //签到
            ret = myApi.pos_signin();
        } else if (transType.equals("69")) { //查询交易信息
            if (transM.length > 1) {
                serialNo = transM[1];
                ret = myApi.pos_getrecord(serialNo);
            } else {
                return ERR_PARAMS;
            }
        } else if (transType.equals("M4")) { //会员卡充值
            myApi.pos_setType(0);
            if (amount == 0) return ERR_AMT;
            ret = myApi.pos_recharge(amount);
        } else if (transType.equals("S1")) {
            if (amount == 0) return ERR_AMT;
            ret = myApi.sand_small(amount);
        } else if (transType.equals("S2")) {
            if (amount == 0) return ERR_AMT;
            ret = myApi.sand_bsc_pay(amount);
        } else if (transType.equals("S3")) {
            if (amount == 0) return ERR_AMT;
            if (transM.length > 1) {
                serialNo = transM[1];
                ret = myApi.sand_bsc_refund(amount, serialNo);
            } else {
                return ERR_PARAMS;
            }
        } else if (transType.equals("S4")) {
            if (amount == 0) return ERR_AMT;
            if (transM.length > 3) {
                serialNo = transM[1];
                date = transM[2];
                paycode = transM[3];
                ret = myApi.sand_bsc_return(amount, serialNo, date, paycode);
            } else {
                return ERR_PARAMS;
            }
        } else if (transType.equals("S5")) {
            if (amount == 0) return ERR_AMT;
            ret = myApi.sand_card_trade(amount);
        } else if (transType.equals("S6")) {
            ret = myApi.sand_key_replace();
        } else if (transType.equals("S7")) {
            ret = myApi.sand_settle();
        } else if (transType.equals("S8")) {
            ret = myApi.sand_O2O_settle();
        } else if (transType.equals("S9")) {
            if (amount == 0) return ERR_AMT;
            if (transM.length > 1) {
                channelCode = transM[1];
                ret = myApi.sand_O2O_qrcode_trade(amount, channelCode);
            } else {
                return ERR_PARAMS;
            }
        } else if (transType.equals("SA")) {
            if (transM.length > 1) {
                serialNo = transM[1];
                ret = myApi.sand_O2O_qrcode_query(serialNo);
            } else {
                return ERR_PARAMS;
            }
        } else if (transType.equals("SB")) {
            if (amount == 0) return ERR_AMT;
            if (transM.length > 1) {
                channelCode = transM[1];
                ret = myApi.sand_O2O_loop(amount, channelCode);
            } else {
                return ERR_PARAMS;
            }
        } else if (transType.equals("SC")) {
            if (transM.length > 1) {
                serialNo = transM[1];
                ret = myApi.sand_O2O_result(serialNo);
            } else {
                return ERR_PARAMS;
            }
        } else if (transType.equals("SD")) {
            if (transM.length > 1) {
                serialNo = transM[1];
                ret = myApi.sand_O2O_cancel(serialNo);
            } else {
                return ERR_PARAMS;
            }
        } else if (transType.equals("SE")) {
            if (amount == 0) return ERR_AMT;
            if (transM.length > 1) {
                serialNo = transM[1];
                ret = myApi.sand_O2O_refund(serialNo, amount);
            } else {
                return ERR_PARAMS;
            }
        } else if (transType.equals("SF")) {
            ret = myApi.sand_O2O_signin();
        } else if (transType.equals("SG")) {
            ret = myApi.sand_delivery_key();
        } else if (transType.equals("SH")) {
            ret = myApi.sand_sync_message();
        } else if (transType.equals("B1")) {
            ret = myApi.tms_online_report();
        } else if (transType.equals("B7")) {
            ret = myApi.tms_remote_download();
        } else if (transType.equals("B8")) {
            ret = myApi.tms_tmn_info_send();
        } else if (transType.equals("BB")) {
            ret = myApi.tms_download_confirm_notice();
        } else if (transType.equals("BD")) {
            ret = myApi.tms_download_key();

        } else {
            return ERR_TYPE;
        }
        if (ret == E_REQ_RETURN.REQ_OK) {
            return ERR_OK;
        } else if (ret == E_REQ_RETURN.REQ_BUSY) {
            return ERR_BUSY;
        } else if (ret == E_REQ_RETURN.REQ_DENY) {
            return ERR_DENY;
        }
        return ERR_OK;
    }

}