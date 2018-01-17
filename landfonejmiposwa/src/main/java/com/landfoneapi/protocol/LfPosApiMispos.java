package com.landfoneapi.protocol;

import android.content.Context;
import android.util.Log;

import com.landfone.common.utils.ConfigUtil;
import com.landfone.common.utils.Logz;
import com.landfone.mis.bean.TransCfx;
import com.landfoneapi.mispos.ErrCode;
import com.landfoneapi.mispos.MISPOS;
import com.landfoneapi.mispos.UART_PROTOCOL;
import com.landfoneapi.protocol.iface.IDisplay;
import com.landfone.common.utils.ISerialPort;
import com.landfoneapi.mispos.Display;
import com.landfoneapi.mispos.DisplayType;
import com.landfoneapi.protocol.pkg.REPLY;
import com.landfoneapi.protocol.pkg.TradeType;
import com.landfoneapi.protocol.pkg._04_GetSettleInfoReply;
import com.landfoneapi.protocol.pkg._04_QueryReply;
import com.landfoneapi.protocol.pkg.junpeng._03_Recharge;
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
import com.landfoneapi.protocol.pkg.sand._04_SandO2OResultReply;
import com.landfoneapi.protocol.pkg.sand._04_SandO2OSigninReply;
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
import com.landfoneapi.protocol.pkg._03_Purchase;
import com.landfoneapi.protocol.pkg.jxnx._03_ReadAccountName;
import com.landfoneapi.protocol.pkg.jxnx._03_ReadCardNo;
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
import com.landfoneapi.protocol.pkg.jxnx._04_CheckBalanceReply;
import com.landfoneapi.protocol.pkg.jxnx._04_DownloadAppcodeReply;
import com.landfoneapi.protocol.pkg.jxnx._04_GetCardReaderReply;
import com.landfoneapi.protocol.pkg.jxnx._04_GetKeyboardReply;
import com.landfoneapi.protocol.pkg._04_GetPrintInfoReply;
import com.landfoneapi.protocol.pkg._04_GetRecordReply;
import com.landfoneapi.protocol.pkg.jxnx._04_GetZNRecordInfoReply;
import com.landfoneapi.protocol.pkg.jxnx._04_PassbookRenewReply;
import com.landfoneapi.protocol.pkg.jxnx._04_PayfeesQueryReply;
import com.landfoneapi.protocol.pkg._04_PurchaseReply;
import com.landfoneapi.protocol.pkg.jxnx._04_ReadAccountNameReply;
import com.landfoneapi.protocol.pkg.jxnx._04_ReadCardNoReply;
import com.landfoneapi.protocol.pkg.jxnx._04_ReadMerTmnReply;
import com.landfoneapi.protocol.pkg.jxnx._04_TestGetZNRecordInfoReply;
import com.landfoneapi.protocol.pkg.jxnx._04_TransQueryReply;
import com.landfoneapi.protocol.pkg._07_Common;
import com.landfoneapi.protocol.pkg._XXK_Reply;
import com.landfone.common.utils.Errs;
import com.landfone.common.utils.LfException;
import com.landfone.common.utils.LfPosSerialCtrl;
import com.landfone.common.utils.LfUtils;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMReader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManagerFactory;

public class LfPosApiMispos extends LfPosSerialCtrl {
    private String TAG = this.getClass().getSimpleName();
    private MISPOS mMISPOS = new MISPOS();
    private ConfigUtil configUtil = new ConfigUtil();
    Socket socket = null;

    private byte[] lockApi = new byte[0];
    private byte[] lockPosp = new byte[0];

    private static final long TRADE_TIME_OUT = 5 * 60 * 1000;

    private boolean isPospExchanging = false;// 是否正在进行posp交互
    private boolean isPospExchangStop = false;// 是否正在进行posp交互
    private boolean isUserInterrupted = false;// 是否用户中断
    private boolean isNetDisConnect = false;// 是否网络中断
    private boolean isSendCancel = false;//是否发送取消
    private byte[] pospExchangeSend = new byte[2018];
    private int pospExchangeSend_len = 0;
    private int pospExchangeSend_tmo = (60 * 1000);
    private byte[] pospExchangeRecv = null;
    private byte[] posOfflineBuffer = null;

    private byte[] serial_ID = new byte[7];

    private String mer = "";
    private String tmn = "";
    private String operId = "";

    private boolean isApiRunning = false;
    private boolean isBusy = false;
    private TradeType userTradeType = TradeType.NONE;
    private boolean isdoPosAutoTaskRunning = false;
    private int FLAG_TMS = 0;
    private int FLAG_55 = -1;

    public void disConnect(boolean isConnect) {
        this.isNetDisConnect = !isConnect;
    }

    private void setUserTradeType(TradeType v) {
        this.userTradeType = v;
    }

    private TradeType getUserTradeType() {
        return this.userTradeType;
    }

    private long lastApiTime = 0;

    // 最近操作时间
    private void updateOpTime() {
        this.lastApiTime = System.currentTimeMillis();
    }

    private void updateOpTime(long v) {
        this.lastApiTime = v;
    }

    private long getOpTimeGap() {
        if (this.lastApiTime == 0) {
            updateOpTime();
        }
        return (System.currentTimeMillis() - this.lastApiTime);
    }

    private boolean skipReadClean = false;

    /**
     * 收到的交易类型
     **/
    private byte recvType = (byte) 0xFF;

    public boolean _IsBusy() {
        return (this.getUserTradeType() != TradeType.NONE);
    }

    //////////////////////////////////////////自动 结算、TMS/////////////////////////////////////////

    private Date settleDate = null;
    private Date tmsDate = null;
    // 结算时间
    private String settleTime = "02:04:00";// "02:00:00";

    private long getIdleTime() {
        return getOpTimeGap();
    }

    public String getSettleTime() {
        return settleTime;
    }

    public void setSettleTime(String settleTime) {
        this.settleTime = settleTime;
    }

    //TMS升级时间
    private String tmsTime = "02:07:00";

    public String getTmsTime() {
        return tmsTime;
    }

    public void setTmsTime(String tmsTime) {
        this.tmsTime = tmsTime;
    }

    // 无操作情况下脱机上送检查时间
    private long offlineCheckTime = 3 * 60 * 1000;

    public void setOfflineCheckTime(long msecs) {
        offlineCheckTime = msecs;
    }

    public long getOfflineCheckTime() {
        return offlineCheckTime;
    }

    /**
     * 是否自动结算，默认不进行自动结算
     */
    private boolean autoSettle = false;

    public boolean isAutoSettle() {
        return autoSettle;
    }

    public void setAutoSettle(boolean autoSettle) {
        this.autoSettle = autoSettle;
    }

    /***
     * 是否自动TMS升级，默认不进行自动TMS升级
     **/
    private boolean autoTms = false;

    public boolean isAutoTms() {
        return autoTms;
    }

    public void setAutoTms(boolean autoTms) {
        this.autoTms = autoTms;
    }

    private Date RandomTime(String beginTime, String endTime) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
            Date start = format.parse(beginTime);//构造开始日期
            Date end = format.parse(endTime);//构造结束日期
            if (start.getTime() >= end.getTime()) {
                return null;
            }
            long date = random(start.getTime(), end.getTime());
            return new Date(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static long random(long begin, long end) {
        long rtn = begin + (long) (Math.random() * (end - begin));
        if (rtn == begin || rtn == end) {
            return random(begin, end);
        }
        return rtn;
    }

    /**
     * 判断是否到了结算时间
     *
     * @return true表示可以开始结算
     */
    private boolean checkSettleTime() {
        if (!isAutoSettle()) {
            return false;// 不进行自动结算
        }
        SimpleDateFormat sdf_dd = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date currentTime = null;
        String currDate = null;
        String settleDateTime = null;
        Date settleTime_d = null;
        // long diff = -1;
        try {
            currDate = sdf_dd.format(new Date());
            if (settleDate != null) {// 如果不为空，则使用上次结算后的日期
                currDate = sdf_dd.format(settleDate);
            } else {// 如果为空，则使用当日日期
                currDate = sdf_dd.format(new Date());
            }
            settleDateTime = currDate + " " + settleTime;
            currentTime = sdf.parse(sdf.format(new Date()));
            settleTime_d = sdf.parse(settleDateTime);
            if (currentTime.getTime() > settleTime_d.getTime()) {
                if (currentTime.getTime() > (settleTime_d.getTime() + (3 * 60 * 60 * 1000))) {// 大于结算时间3个小时，不进行结算
                    updateSettleTime();// 放到第二天结算
                    return false;
                } else {
                    return true;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 判断是否到了TMS更新时间
     *
     * @return true表示可以开始TMS更新
     */
    private boolean checkTmsTime() {
        if (!isAutoTms()) {
            return false;// 不进行自动结算
        }
        SimpleDateFormat sdf_dd = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date currentTime = null;
        String currDate = null;
        String tmsDateTime = null;
        Date tmsTime_d = null;
        // long diff = -1;
        try {
            currDate = sdf_dd.format(new Date());
            if (tmsDate != null) {// 如果不为空，则使用上次结算后的日期
                currDate = sdf_dd.format(tmsDate);
            } else {// 如果为空，则使用当日日期
                currDate = sdf_dd.format(new Date());
            }
            tmsDateTime = currDate + " " + tmsTime;
            currentTime = sdf.parse(sdf.format(new Date()));
            tmsTime_d = sdf.parse(tmsDateTime);
            if (currentTime.getTime() > tmsTime_d.getTime()) {
                if (currentTime.getTime() > (tmsTime_d.getTime() + (3 * 60 * 60 * 1000))) {// 大于结算时间3个小时，不进行结算
                    updateTmsTime();// 放到第二天结算
                    return false;
                } else {
                    return true;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 设置下次结算时间
     */
    private void updateSettleTime() {
        settleDate = new Date();
        settleDate = updateNextAutoTime(settleDate);// 这个时间就是日期往后推一天的结果
    }

    /**
     * 设置下次TMS升级时间
     **/
    private void updateTmsTime() {
        tmsDate = new Date();
        tmsDate = updateNextAutoTime(tmsDate);// 这个时间就是日期往后推一天的结果
    }

    /**
     * 设置下次自动操作时间
     */
    private Date updateNextAutoTime(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, 1);// 把日期往后增加一天.整数往后推,负数往前移动
        return calendar.getTime();
    }

    private boolean settleNeedSignin = true;

    private void posAutoTms() {
        boolean needTmsRemoteDownload = false;
        boolean needTmsInfoSend = false;
        boolean needTmsDownloadConfirmNotice = false;
        boolean needTmsDownloadKey = false;
        updateTmsTime();//立即更新
        Logz.i(TAG, "-----》开始TMS更新...");
        _03_Common t = new _03_Common();
        try {//TMS更新
            long oldoptime = getOpTimeGap();

            REPLY mREPLY = new REPLY();
            t.setTradeType(TradeType.TMS_ONLINE_REPORT);// TMS联机报道
            mREPLY = _03_TmsOnlineReport(t);
            updateOpTime(oldoptime);
            oldoptime = getOpTimeGap();
            if (mREPLY.reply == MISPOS.PACK_ACK) {//联机报道 返回成功
                needTmsRemoteDownload = true;
            }
            if (needTmsRemoteDownload) {
                t.setTradeType(TradeType.TMS_REMOTE_DOWNLOAD);//TMS远程下载
                mREPLY = _03_TmsRemoteDownload(t);//设置正常返回结果
                updateOpTime(oldoptime);
                oldoptime = getOpTimeGap();
                if (mREPLY.reply == MISPOS.PACK_ACK) {//远程下载，更新成功
                    needTmsInfoSend = true;
                } else if (mREPLY.code.equals("98")) {//远程下载 无需更新
                    needTmsInfoSend = true;
                }
            }
            if (needTmsInfoSend) {
                t.setTradeType(TradeType.TMS_TMN_INFO_SEND);//TMS 终端信息上送
                mREPLY = _03_TmsTmnInfoSend(t);
                updateOpTime(oldoptime);
                oldoptime = getOpTimeGap();
                if (mREPLY.reply == MISPOS.PACK_ACK) {
                    needTmsDownloadConfirmNotice = true;
                }
            }
            if (needTmsDownloadConfirmNotice) {
                t.setTradeType(TradeType.TMS_DOWNLOAD_CONFIRM_NOTICE);
                mREPLY = _03_TmsDownloadConfirmNotice(t);
                updateOpTime(oldoptime);
                oldoptime = getOpTimeGap();
                if (mREPLY.reply == MISPOS.PACK_ACK) {
                    needTmsDownloadKey = true;
                }
                if (needTmsDownloadKey) {
                    t.setTradeType(TradeType.TMS_DOWNLOAD_KEY);
                    mREPLY = _03_TmsDownloadKey(t);
                }
            }
            updateOpTime(oldoptime);// _03_UploadOffline会更新操作时间，所以要设置回去，以便立即处理posp透传
        } catch (LfException e1) {
            updateSettleTime();
            e1.printStackTrace();
        } finally {
            // 可以认为暂时么有脱机数据了
            mightBeOfflineData = false;
            posOfflineBuffer = null;
        }
    }

    private void posAutoSettle() {
        updateSettleTime();// 立即更新
        Logz.i(TAG, "-----》开始结算...");
        // 获取POSP脱机数据
        _03_Common t = new _03_Common();
        try {// 结算
            long oldoptime = getOpTimeGap();
            t.setMer(mer);
            t.setTmn(tmn);
            t.setTradeType(TradeType.SETTLE);
            _03_Settle(t);
            updateOpTime(oldoptime);// _03_UploadOffline会更新操作时间，所以要设置回去，以便立即处理posp透传
            oldoptime = getOpTimeGap();
            // 结算后签到,不成功最多三次
            t.setMer(mer);
            t.setTmn(tmn);
            REPLY mREPLY = new REPLY();

            t.setTradeType(TradeType.SIGNIN);
            //this.setType((byte) 0xF0);//会员卡签到
            //for (int i = 0; i < 3; i++) {
            //    if (settleNeedSignin) {
            //        mREPLY = _03_Signin(t); //设置正常返回结果
            //        settleNeedSignin = (mREPLY.reply != MISPOS.PACK_ACK);
            //    }
            //}
            settleNeedSignin = true;
            this.setType((byte) 0x01);//银行卡签到
            for (int i = 0; i < 3; i++) {
                if (settleNeedSignin) {
                    mREPLY = _03_Signin(t); //设置正常返回结果
                    settleNeedSignin = (mREPLY.reply != MISPOS.PACK_ACK);
                }
            }
            updateOpTime(oldoptime);// _03_UploadOffline会更新操作时间，所以要设置回去，以便立即处理posp透传
        } catch (LfException e1) {
            updateSettleTime();
            e1.printStackTrace();
        } finally {
            // 可以认为暂时么有脱机数据了
            mightBeOfflineData = false;
            posOfflineBuffer = null;
        }
    }

    // TODO:另开线程，判断是否有内存数据可以上送&&判断无练级交易进行--->脱机上送(60秒)
    // ---->有联机交易时中断--->保存结果至内存
    // ---->判断无POS操作（锁+标志？）-->下发POS（不需要回复）
    // 如果无内存数据，则根据最近交易标志（每次PURCHASE结束后设置）-->下发POS获取脱机数据-->至内存
    // ---->如果没有脱机数据--->重置最近交易标志（需要用函数同步处理）
    // 到一定时间时，执行结算
    private void posAutoTask() {
        byte[] tmp = null;
        long idle_time = -1;

        SimpleDateFormat sdf_mm = new SimpleDateFormat("HH:mm:ss");
        setSettleTime(sdf_mm.format(RandomTime("00:00:00", "03:00:00")));
        setTmsTime(sdf_mm.format(RandomTime("00:00:00", "03:00:00")));

        while (!stop) {
            if (isApiRunning) {
                // 先判断是否到了结算时间，如果到了，则发起结算
                // api的操作要能够返回"正在结算，请稍后"
                idle_time = getIdleTime();
                if (idle_time > getOfflineCheckTime()) {
                    if (checkSettleTime()) {
                        //校验自动结算时间
                        posAutoSettle();
                    } else if (checkTmsTime()) {
                        //校验自动TMS时间
                        posAutoTms();
                    } else if (isPospExchangStop && posOfflineBuffer != null) {// 脱机数据不为空，且没有POSP交互
                        Logz.i(TAG, "-----》没有posp交互，且脱机交易不为空，进行posp交互....");
                        try {
                            setUserTradeType(TradeType.UPLOADOFFLINE);// 重要
                            // 获取posp回复
                            tmp = pospExchange(posOfflineBuffer, posOfflineBuffer.length, (60 * 1000), false);
                            // 下发给POS
                            if (tmp != null) {
                                Logz.i(TAG, "-----》没有posp交互，且脱机交易不为空，posp返回数据，下发pos...");
                                write(tmp, tmp.length, 2000);
                                tmp = null;
                            } else {  // TODO:如果为空的处理
                            }
                        } catch (LfException e) {
                            e.printStackTrace();
                        } finally {
                            setUserTradeType(TradeType.NONE);// !!!!重要
                            long tmpl = System.currentTimeMillis() - (60 * 1000);
                            updateOpTime(tmpl);
                        }
                        posOfflineBuffer = null;// 重置脱机数据缓存
                    } else if (!isBusy && mightBeOfflineData// 可能有脱机交易数据
                            && mer != null && tmn != null && mer.length() > 0
                            && tmn.length() > 0 && posOfflineBuffer == null) {
                        Logz.i(TAG, "-----》可能有脱机交易数据，且缓存为null，下发获取脱机数据...");
                        // 获取POSP脱机数据
                        _03_Common t = new _03_Common();
                        try {
                            long oldoptime = getOpTimeGap();
                            t.setMer(mer);
                            t.setTmn(tmn);
                            t.setTradeType(TradeType.UPLOADOFFLINE);
                            setUserTradeType(TradeType.UPLOADOFFLINE);
                            _03_UploadOffline(t);
                            if (posOfflineBuffer != null) {
                                updateOpTime(oldoptime);// _03_UploadOffline会更新操作时间，所以要设置回去，以便立即处理posp透传
                            }
                        } catch (LfException e1) {
                            e1.printStackTrace();
                        } finally {
                            setUserTradeType(TradeType.NONE);
                        }
                    }
                }
            } else {
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;//被中断就退出
            }
        }
    }

    private Runnable PosAutoTaskTread = new Runnable() {
        @Override
        public void run() {
            isdoPosAutoTaskRunning = true;
            posAutoTask();
            isdoPosAutoTaskRunning = false;
        }
    };

    private void doPosAutoTask() {
        (new Thread(PosAutoTaskTread)).start();
    }

    ///////////////////////////////////////服务器通讯处理部分////////////////////////////////////////
    private void setUseServer(byte recvType) {
        if (recvType == (byte) 0xF0) {//骏鹏会员卡
            this.ip = this.card_ip;
            this.port = this.card_port;
        } else if (recvType == (byte) 0x92) {//杉德会员卡
            this.ip = this.sand_card_ip;
            this.port = this.sand_card_port;
        } else if (recvType == (byte) 0x93) {//杉德O2O平台
            this.ip = this.sand_o2o_ip;
            this.port = this.sand_o2o_port;
        } else if (recvType == (byte) 0x01) {
            if (getUserTradeType() == TradeType.TMS_ONLINE_REPORT ||
                    getUserTradeType() == TradeType.TMS_REMOTE_DOWNLOAD ||
                    getUserTradeType() == TradeType.TMS_TMN_INFO_SEND ||
                    getUserTradeType() == TradeType.TMS_DOWNLOAD_CONFIRM_NOTICE ||
                    getUserTradeType() == TradeType.TMS_DOWNLOAD_KEY) {
                this.ip = this.tms_ip;
                this.port = this.tms_port;
            } else if (isSSL) {
                this.ip = this.ssl_ip;
                this.port = this.ssl_port;
            } else if (isHardEncryption) {
                this.ip = this.hard_encrypt_ip;
                this.port = this.hard_encrypt_port;
            } else {
                this.ip = this.posp_ip;
                this.port = this.posp_port;
            }
        } else if (recvType == (byte) 0x88) {
            this.ip = this.hard_encrypt_ip2;
            this.port = this.hard_encrypt_port2;
        } else if (recvType == (byte) 0x89) {
            this.ip = this.hard_encrypt_ip2;
            this.port = this.hard_encrypt_port2;
        } else {
            this.ip = this.posp_ip;
            this.port = this.posp_port;
        }
        if (this.ip.equals("") && this.port == 0) {
            this.ip = this.posp_ip;
            this.port = this.posp_port;
        }
    }

    private byte[] pospExchange(byte[] data, int len, int timeout, boolean needReply) throws LfException {
        Logz.i(TAG, "raoj--->pospExchange");
        synchronized (lockPosp) {
            byte[] retbb = null;
            int tmo = timeout < (60 * 1000) ? (60 * 1000) : timeout;
            this.setPospExchangStop(false);

            /////// 连接网络////////////
            pospConnect(tmo);
//            Log.i("pospExchange1", "needReply:" + needReply);
            isConnectStop(needReply, ErrCode._Z0, ErrCode._X6);

            ////// 发送数据///////////
            pospSenddata(data, len);
//            Log.i("pospExchange2", "needReply:" + needReply);
            isConnectStop(needReply, ErrCode._Z0, ErrCode._X7);

            ////// 接收返回///////////
            retbb = pospRecvdata(tmo);

            displayMsg("交互结束");
            return retbb;
        }
    }

    private void pospConnect(int tmo) throws LfException {
        Logz.i(TAG, "raoj--->pospConnect");
        //TODO:多次重试连接
        int trycnt = 5, retrysleep = 500;
        while (!isPospExchangStop && trycnt > 0) {
            if (isUserInterrupted || isNetDisConnect || !isPospExchanging || isSendCancel) {
//                Logz.i(TAG, "isUserInterrupted:" + isUserInterrupted + " , isNetDisConnect:" + isNetDisConnect
//                +" , !isPospExchanging:" + !isPospExchanging + " , isSendCancel:" + isSendCancel);
                try {
                    _ReplyXXK(MISPOS.PROTOCOL_PATH_RECV, MISPOS.PACK_NAK, ErrCode._Z1);
                } catch (LfException e) {
                    e.printStackTrace();
                }
                throw new LfException(Errs.OTHER_ERR, ErrCode._Z1.getDesc());
            }

            if (socket != null) {
                trycnt = 0;
            } else {
                try {
                    setUseServer(recvType);
                    Logz.w(TAG, "IP:" + ip + " , port:" + port);
                    displayMsg("正在连接银联中心...");
                    long begin = System.currentTimeMillis();
                    if (!isSSL && !isHard || getType() == (byte) 0xF0) {
                        SocketAddress address;
                        if (recvType == (byte) 0xF1) {
                            address = new InetSocketAddress(this.update_ip, this.update_port);
                        } else {
                            address = new InetSocketAddress(ip, port);
                        }
                        socket = new Socket();
                        socket.connect(address, (trycnt > 1 ? (4 * 1000) : (8 * 1000)));
                        Logz.d(TAG, "common socket");
                    } else {
                        socket = getSSLSocket(this.ip, this.port);
                        if (socket == null) {
                            try {
                                _ReplyXXK(MISPOS.PROTOCOL_PATH_RECV, MISPOS.PACK_NAK, ErrCode._Z0);
                                displayMsg("找不到证书文件！无法进行SSL加密");
                            } catch (LfException e) {
                                e.printStackTrace();
                            }
                        }
                        Logz.d(TAG, "SSL socket");
                    }
                    System.out.println("socket connect time:" + (System.currentTimeMillis() - begin) + "ms");
                    trycnt = 0;
                } catch (Exception e) {
                    socket = null;
                    e.printStackTrace();
                    if (trycnt-- < 1) {
                        try {
                            _ReplyXXK(MISPOS.PROTOCOL_PATH_RECV, MISPOS.PACK_NAK, ErrCode._Z0);
                        } catch (LfException e1) {
                            e1.printStackTrace();
                        }
                        displayMsg("服务器连接失败1");
                        throw new LfException(Errs.OTHER_ERR, ErrCode._Z0.getDesc() + "host err");
                    } else {
                        try {
                            Thread.sleep(retrysleep);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                        tmo -= retrysleep;
                    }
                }
            }
        }
    }

    private void pospSenddata(byte[] data, int len) throws LfException {
        Logz.i(TAG, "raoj--->pospSenddata");
        try {
            displayMsg("已连接银联中心\n处理中...\n发送数据包...");
            if (getUserTradeType() == TradeType.TMS_REMOTE_DOWNLOAD && FLAG_TMS < 3) {
                FLAG_TMS++;
            }
            long send_s_time = System.currentTimeMillis();
            if (!isSSL) {
                socket.getOutputStream().write(data, 0, len);
                dbg_TprintfWHex(TAG, data, len, "connect ok, write");
            } else {
                data = PubPackHttpHead(data, len);
                socket.getOutputStream().write(data, 0, data.length);
                dbg_TprintfWHex(TAG, data, data.length, "SSL connect ok, write");
            }
            System.out.println("write use time:" + (System.currentTimeMillis() - send_s_time));
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (!isHardEncryption) {
                    Logz.i(TAG, "关闭socket");
                    socket.close();
                }
                socket = null;
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            try {
                _ReplyXXK(MISPOS.PROTOCOL_PATH_RECV, MISPOS.PACK_NAK, ErrCode._X6);
            } catch (LfException e1) {
                e1.printStackTrace();
            }
            throw new LfException(Errs.OTHER_ERR, ErrCode._X6.getDesc());
        }
    }

    private byte[] pospRecvdata(int tmo) throws LfException {
        Logz.i(TAG, "raoj--->pospRecvdata,tmo:" + tmo);
        byte[] retbb = null;
        if (FLAG_TMS == 2) {
            return retbb;
        }
        try {
            displayMsg("已连接银联中心\n处理中...\n接收返回...");
            byte[] tmpData = null;
            byte[] tmpSTX = new byte[1];
            byte[] tmpL = new byte[2];
            int tmpiL = 0;

            if (getType() == (byte) 0xF0) {//会员卡接收数据解析
                IOException te = null;
                try {
                    retbb = readHttpResponse(socket);
                } catch (IOException e) {
                    e.printStackTrace();
                    te = e;
                } finally {
                    if (retbb != null) {
                        System.out.println("http req OK");
                    } else {
                        System.out.println("retbb == null");
                        if (te != null) {
                            throw te;
                        } else {
                            throw new LfException(Errs.OTHER_ERR, ErrCode._X8.getDesc());
                        }
                    }
                }
            } else {//银行卡接收数据解析
                while (!isPospExchangStop && tmo > 0) {
                    if (isUserInterrupted || !isPospExchanging || isSendCancel) {
                        isPospExchangStop = true;
                        if (socket != null) {
                            socket.close();
                        }
                        socket = null;
                        throw new LfException(Errs.OTHER_ERR, ErrCode._X8.getDesc());
                    }
                    if (getUserTradeType() == TradeType.TMS_ONLINE_REPORT ||
                            getUserTradeType() == TradeType.TMS_REMOTE_DOWNLOAD ||
                            getUserTradeType() == TradeType.TMS_TMN_INFO_SEND ||
                            getUserTradeType() == TradeType.TMS_DOWNLOAD_CONFIRM_NOTICE ||
                            getUserTradeType() == TradeType.TMS_DOWNLOAD_KEY) {
                        if (socket.getInputStream().available() >= 1) {
                            socket.getInputStream().read(tmpSTX, 0, 1);
                        }
                        if (socket.getInputStream().available() >= 2) {
                            socket.getInputStream().read(tmpL, 0, 2);
                            break;
                        }
                    } else if (!isSSL && !isHard || getType() == (byte) 0xF0) {
                        if (socket.getInputStream().available() >= 2) {
                            socket.getInputStream().read(tmpL, 0, 2);
                            break;
                        }
                    } else {
                        InputStream in = socket.getInputStream();
                        byte[] buffer = new byte[1024];
                        in.read(buffer);
                        if (isSSL) {
                            tmpData = PubCheckHttpHead(buffer);
                        } else {
                            tmpData = buffer;
                        }
                        if (tmpData != null && tmpData.length > 0) {
                            LfUtils.memcpy(tmpL, 0, tmpData, 0, 2);
                            break;
                        }
                    }
                    try {
                        Thread.sleep(30);
                        tmo -= 30;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Logz.d(TAG, "recv tmo(" + tmo + ") sock datalen:" + String.format("%02X %02X", tmpL[0], tmpL[1]));
                if (getType() == (byte) 0x92 ||
                        getUserTradeType() == TradeType.TMS_ONLINE_REPORT ||
                        getUserTradeType() == TradeType.TMS_REMOTE_DOWNLOAD ||
                        getUserTradeType() == TradeType.TMS_TMN_INFO_SEND ||
                        getUserTradeType() == TradeType.TMS_DOWNLOAD_CONFIRM_NOTICE ||
                        getUserTradeType() == TradeType.TMS_DOWNLOAD_KEY) {
                    tmpiL += Integer.parseInt(String.format("%02X", tmpL[1]));
                    tmpiL += Integer.parseInt(String.format("%02X", tmpL[0])) * 100;
                    if (getType() != (byte) 0x92) {
                        tmpiL += 2;
                    }
                } else {
                    tmpiL += (tmpL[1] & 0x00ff);
                    tmpiL += (((tmpL[0] & 0x00ff) << 8) & 0x00ff00);
                }
                dbg_TprintfWHex(TAG, tmpL, 2, "head length read(" + tmpiL + ")");
                int recv_len = 0;
                if (tmpiL > 0 && tmpiL < 1024 * 15) {
                    byte[] tmpbb = new byte[tmpiL];
                    while (!isPospExchangStop && tmo > 0) {
                        if (isUserInterrupted || !isPospExchanging) {
                            isPospExchangStop = true;
                            if (socket != null) {
                                socket.close();
                            }
                            socket = null;
                            throw new LfException(Errs.OTHER_ERR, ErrCode._X8.getDesc());
                        }
                        if (tmpiL > 0) {
                            if (!isSSL && !isHard || getType() == (byte) 0xF0) {
                                for (int i = 0; i < tmpiL; i++) {
                                    socket.getInputStream().read(tmpbb, i, 1);
                                }
                                recv_len = tmpbb.length;
                                // recv_len = socket.getInputStream().read(tmpbb, 0, tmpiL);
                            } else {
                                recv_len = LfUtils.memcpy(tmpbb, 0, tmpData, 2, tmpiL);
                                dbg_TprintfWHex(TAG, tmpbb, tmpbb.length, "tmpbb:length" + tmpbb.length);
                            }
                            break;
                        } else {
                            try {
                                Thread.sleep(30);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            tmo -= 30;
                        }
                    }
                    if (recv_len > 0) {
                        if (getType() == (byte) 0x92 || getType() == (byte) 0x93) {
                            retbb = new byte[tmpiL + 2];
                            LfUtils.memcpy(retbb, 0, tmpL, 0, 2);
                            LfUtils.memcpy(retbb, 2, tmpbb, 0, tmpbb.length);
                        } else if (getUserTradeType() == TradeType.TMS_ONLINE_REPORT ||
                                getUserTradeType() == TradeType.TMS_REMOTE_DOWNLOAD ||
                                getUserTradeType() == TradeType.TMS_TMN_INFO_SEND ||
                                getUserTradeType() == TradeType.TMS_DOWNLOAD_CONFIRM_NOTICE ||
                                getUserTradeType() == TradeType.TMS_DOWNLOAD_KEY) {
                            retbb = new byte[tmpiL + 3];
                            LfUtils.memcpy(retbb, 0, tmpSTX, 0, 1);
                            LfUtils.memcpy(retbb, 1, tmpL, 0, 2);
                            LfUtils.memcpy(retbb, 3, tmpbb, 0, tmpbb.length);
                        } else {
                            retbb = new byte[tmpiL];
                            LfUtils.memcpy(retbb, 0, tmpbb, 0, tmpbb.length);
                        }
                        if (retbb.length < 512) {
                            dbg_TprintfWHex(TAG, retbb, retbb.length, "connect ok，read len(" + retbb.length + ")");
                        } else {
                            Logz.d(TAG, "connect ok，read len(" + retbb.length + ")");
                        }
                    } else {
                        if (retbb != null) {
                            dbg_TprintfWHex(TAG, retbb, retbb.length, "#err,length:" + tmpiL + ",but read:" + recv_len);
                        }
                    }
                } else {
                    try {
                        Logz.d(TAG, "expected sock datalen(" + tmpiL + ") to large??");
                        _ReplyXXK(MISPOS.PROTOCOL_PATH_RECV, MISPOS.PACK_NAK, ErrCode._X8);
                    } catch (LfException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            try {
                _ReplyXXK(MISPOS.PROTOCOL_PATH_RECV, MISPOS.PACK_NAK, ErrCode._X7);
            } catch (LfException e1) {
                e1.printStackTrace();
            }
            displayMsg("数据接收错误2");
            throw new LfException(Errs.OTHER_ERR, "exchange err");
        } finally {
            try {
                if (socket != null && !isHardEncryption && FLAG_TMS < 1 && getUserTradeType() != TradeType.TMS_DOWNLOAD_KEY) {
                    Logz.i(TAG, "关闭socket");
                    socket.close();
                    socket = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return retbb;
    }

    private void isConnectStop(boolean needReply, ErrCode Err1, ErrCode Err2) throws LfException {
        if (socket == null || !socket.isConnected()) {
            displayMsg("服务器连接失败");
            if (needReply) {
                _ReplyXXK(MISPOS.PROTOCOL_PATH_RECV, MISPOS.PACK_NAK, Err1);
            }
            try {
                if (!isHardEncryption) {
                    Logz.i(TAG, "关闭socket");
                    if (socket != null) {
                        socket.close();
                        socket = null;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            throw new LfException(Errs.OTHER_ERR, Err1.getDesc());
        } else if (isUserInterrupted || isNetDisConnect || !isPospExchanging || isSendCancel) {
            try {
                _ReplyXXK(MISPOS.PROTOCOL_PATH_RECV, MISPOS.PACK_NAK, Err2);
            } catch (LfException e) {
                e.printStackTrace();
            }
            throw new LfException(Errs.OTHER_ERR, Err2.getDesc());
        }
    }


    private Thread pospExchangeThead = null;

    public boolean isPospExchangStop() {
        return isPospExchangStop;
    }

    public void setPospExchangStop(boolean isPospExchangStop) {
        synchronized (lockPosp) {
            this.isPospExchangStop = isPospExchangStop;
        }
    }

    private byte[] getPospExchangeRecv() {
        return pospExchangeRecv;
    }

    private void setPospExchangeRecv(byte[] pospExchangeRecv) {
        this.pospExchangeRecv = pospExchangeRecv;
    }


    private void doPospExchange(byte[] data, int len, int timeout) {
        Logz.i(TAG, "raoj--->doPospExchange");
        int i = 0;
        if (!isPospExchanging) {
            if (getType() == (byte) 0xF0 || //会员卡透传不需要长度
                    getType() == (byte) 0x92 || getType() == (byte) 0x93 || //硬加密透传不需要长度
                    (getUserTradeType() == TradeType.TMS_ONLINE_REPORT) ||
                    (getUserTradeType() == TradeType.TMS_REMOTE_DOWNLOAD) ||
                    (getUserTradeType() == TradeType.TMS_TMN_INFO_SEND) ||
                    (getUserTradeType() == TradeType.TMS_DOWNLOAD_CONFIRM_NOTICE) ||
                    (getUserTradeType() == TradeType.TMS_DOWNLOAD_KEY)) {//TMS透传不需要长度
                i = 0;
            } else { // 传统MIS协议 数据要加上长度
                this.pospExchangeSend[0] = (byte) (len / 256);// (byte) (len&0x00ff);
                this.pospExchangeSend[1] = (byte) (len % 256);// (byte)// ((len&0x00ff00)>>8);
                i = 2;
            }
            LfUtils.memcpy(this.pospExchangeSend, i, data, 0, len);
            this.pospExchangeSend_len = i + len;
            this.pospExchangeSend_tmo = timeout;
            pospExchangeThead = new Thread(pospExchangeRunnable);
            if (pospExchangeThead != null) {
                pospExchangeThead.start();
            }
        }
    }

    private Runnable pospExchangeRunnable = new Runnable() {
        @Override
        public void run() {
            Logz.i(TAG, "raoj--->pospExchangeRunnable");
            if (!isPospExchanging) {
                isPospExchanging = true;
                setPospExchangeRecv(null);
                try {
                    setPospExchangeRecv(pospExchange(pospExchangeSend, pospExchangeSend_len, pospExchangeSend_tmo, true));
                } catch (LfException e) {
                    e.printStackTrace();
                } finally {
                    isPospExchanging = false;
                }
            }
        }
    };

    public static final byte[] input2byte(InputStream inStream) throws IOException {
        byte[] in2b = null;
        ByteArrayOutputStream swapStream = null;
        try {
            Logz.i("LfPosApiMispos", "raoj---->input2byte1");
            swapStream = new ByteArrayOutputStream();
            byte[] buff = new byte[100];
            int rc = -1;
            rc = inStream.available();
            while ((rc = inStream.read(buff)) != -1) {
                swapStream.write(buff, 0, rc);
                dbg_TprintfWHex("LfPosApiMispos", buff, buff.length, "---buff---");
            }
            in2b = swapStream.toByteArray();
            Logz.i("LfPosApiMispos", "raoj---->input2byte2");
        } catch (Exception e) {
            e.fillInStackTrace();
        } finally {
            swapStream.close();
            inStream.close();
        }
        return in2b;
    }

    //////////////////////////////////////////会员卡报文处理/////////////////////////////////////////
    private byte[] readHttpResponse(Socket s) throws IOException {
        byte[] result = null;
        InputStream inputStream = s.getInputStream();
        if (getType() == (byte) 0xF0) {//会员卡饭卡接收数据,原样返回-----针对神思饭卡
            if (inputStream != null) {
                ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
                byte[] head = new byte[6];
                byte[] len = new byte[4];
                byte[] cmda = new byte[2];
                byte[] state = new byte[1];
                byte[] crc = new byte[2];
                int offset = 0;
                inputStream.read(head, 0, head.length);
                offset += head.length;
//                dbg_TprintfWHex(TAG, head, head.length, "---head---");
                inputStream.read(len, 0, len.length);
                offset += len.length;
//                dbg_TprintfWHex(TAG, len, len.length, "---len---");

//                Logz.i(TAG, "raoj---->datalen:" + LfUtils.byteArrayToInt(len, 0));
                byte[] data = new byte[LfUtils.byteArrayToInt(len, 0)];

                inputStream.read(data, 0, data.length);
                offset += data.length;
//                dbg_TprintfWHex(TAG, data, data.length, "---data---");

                result = new byte[offset];
                int i = 0;
                LfUtils.memcpy(result, i, head, 0, head.length);
                i += head.length;
                LfUtils.memcpy(result, i, len, 0, len.length);
                i += len.length;
                LfUtils.memcpy(result, i, data, 0, data.length);
                i += data.length;
//                dbg_TprintfWHex(TAG, result, result.length, "---data---");
            } else {

            }
            return result;
        } else {
            //--输出服务器传回的消息的头信息
            inputStream = s.getInputStream();
            String line = null;
            byte[] line_arr = null, ret = null;
            String header = "";
            int contentLength = -1, ret_len = 0;
            boolean isContentLength = false;
            do {
                line_arr = readLine(inputStream, 0);
                if (line_arr == null) return null;
                line = new String(line_arr, "GBK");

                //如果有Content-Length消息头时取出
                if (line.startsWith("Content-Length")) {
                    contentLength = Integer.parseInt(line.split(":")[1].trim());
                    isContentLength = true;
                }
                header += line;
                //如果遇到了一个单独的回车换行，则表示请求头结束
            } while (line != null && line.length() > 0 && !line.equals("\r\n"));

            System.out.println("header:");
            System.out.print("\n" + header);
            System.out.println("isContentLength:" + isContentLength);
            System.out.println("contentLength:" + contentLength);

            if (isContentLength) {// 有长度
                if (contentLength > 0) {
                    line_arr = readLine(inputStream, contentLength);

                }
                if (line_arr != null) {
                    dbg_printfWHex(line_arr, line_arr.length, "content:");
                    ret_len = header.getBytes().length + line_arr.length;
                    System.out.println("read content len:" + ret_len);
                    ret = new byte[ret_len];
                    LfUtils.memcpy(ret, 0, header.getBytes(), 0, header.getBytes().length);
                    LfUtils.memcpy(ret, header.getBytes().length, line_arr, 0, line_arr.length);
                } else {
                    System.out.println("read content len:" + 0);
                    ret_len = header.getBytes().length;
                    ret = new byte[ret_len];
                    LfUtils.memcpy(ret, 0, header.getBytes(), 0, header.getBytes().length);
                }

            } else {
                do {
                    line_arr = readLine(inputStream, 0);
                    if (line_arr == null) return null;
                    line = new String(line_arr, "GBK");
                    header += line;
                    //如果遇到了一个单独的回车换行，则表示请求头结束
                } while (line != null && line.length() > 0 && !header.endsWith("\r\n0\r\n\r\n"));

                ret = header.getBytes("GBK");
                System.out.println("all:");
                System.out.print(header);
            }
            return ret;
        }
    }

    private byte[] readLine(InputStream is, int contentLe) throws IOException {
        ArrayList lineByteList = new ArrayList();
        byte[] tmp = {0};
        int total = 0, ret = -1;
        byte[] tmpByteArr = null;
        if (contentLe != 0) {
            do {
                ret = (byte) is.read(tmp);
                if (ret == -1) {
                    break;
                }
                lineByteList.add(Byte.valueOf(tmp[0]));
                total++;
            } while (total < contentLe);//消息体读还未读完
        } else {
            do {
                ret = (byte) is.read(tmp);
                if (ret == -1) {
                    break;
                }
                lineByteList.add(Byte.valueOf(tmp[0]));
            } while (tmp[0] != 10);
        }
        if (lineByteList.size() > 0) {
            tmpByteArr = new byte[lineByteList.size()];
            for (int i = 0; i < lineByteList.size(); i++) {
                tmpByteArr[i] = ((Byte) lineByteList.get(i)).byteValue();
            }
            lineByteList.clear();
        }
        //return tmpByteArr==null?"":(new String(tmpByteArr, "GBK"));
        //return tmpByteArr;
        return tmpByteArr;
    }

    private byte[] readUpdateResponse(Socket s) throws IOException {
        int tmo = 30 * 1000;
        byte[] retbb = null;
        byte[] buffer = new byte[1024 * 10];
        byte[] tmpL = new byte[2];
        int tmpiL = 0;

        while (tmo > 0) {
            if (s.getInputStream().available() >= 2) {//
                s.getInputStream().read(tmpL, 0, 2);
                break;
            }
            try {
                Thread.sleep(30);
                tmo -= 30;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        tmpiL += (tmpL[1] & 0x00ff);// 0
        tmpiL += ((tmpL[0] & 0x00ff) << 8) & 0x00ff00;
        retbb = new byte[tmpiL + 2];
        LfUtils.memcpy(retbb, 0, tmpL, 0, 2);

        if (tmpiL > 0 && tmpiL < (1024 * 10)) {
            byte[] tmpbb = new byte[tmpiL];
            while (tmo > 0) {
                if (s.getInputStream().available() >= tmpiL) {//
                    s.getInputStream().read(tmpbb, 0, tmpiL);
                    break;
                } else {
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    tmo -= 30;
                }
            }
            LfUtils.memcpy(retbb, 2, tmpbb, 0, tmpbb.length);
        } else {
            Logz.d(TAG, "expected sock datalen(" + tmpiL + ") to large??");
            if (true) {
                // 回复NAK
                try {
                    _ReplyXXK(MISPOS.PROTOCOL_PATH_RECV,
                            MISPOS.PACK_NAK, ErrCode._X8);// 数据接收有误
                } catch (LfException e) {
                    e.printStackTrace();
                }
            }
        }
        return retbb;
    }

    /////////////////////////////////////////////硬加密//////////////////////////////////////////////
    private boolean isHardEncryption = false;

    public void setHardEncryption(boolean hardEncryption) {
        isHardEncryption = hardEncryption;
    }

    ///////////////////////////////////////////SSL加密部分///////////////////////////////////////////
    private static final String CLIENT_AGREEMENT = "TLS";//使用协议
    private static final String CLIENT_KEY_MANAGER = "X509";//密钥管理器
    private static final String CLIENT_TRUST_MANAGER = "X509";//
    private static final String CLIENT_KEY_KEYSTORE = "BKS";//密库，这里用的是BouncyCastle密库
    private static final String CLIENT_TRUST_KEYSTORE = "JKS";//
    private String KEY_STORE_KEY_PATH = "client.p12";//客户端要给服务器端认证的证书
    private String KEY_STORE_TRUST_PATH = "CUP_cacert.pem";//客户端验证服务器端的证书库
    private String CLIENT_KEY_PASSWORD = "123456";//私钥密码
    private String CLIENT_TRUST_PASSWORD = "123456";//信任证书密码
    private boolean isSSL = false;
    private boolean isHard = false;

    /**
     * 设置是否SSL加密，以及秘钥路径
     */
    public void setKeyCert(Context context, boolean isSSL, String trust_path) {
        this.context = context;
        this.isSSL = isSSL;
        KEY_STORE_TRUST_PATH = trust_path;
    }

    public void setHardEncrypt(Context context, boolean isHard, String trust_path) {
        this.context = context;
        this.isHard = isHard;
        KEY_STORE_TRUST_PATH = trust_path;
    }

    /**
     * SSL单向认证，创建SSLSocket连接
     **/
    private SSLSocket getSSLSocket(String ip, int port) throws CertificateException, IOException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        SSLContext sslContext = getSSLContext();
        if (sslContext != null) {
            return (SSLSocket) sslContext.getSocketFactory().createSocket(ip, port);
        } else {
            return null;
        }
    }

    private SSLContext getSSLContext() {
        SSLContext sslContext = null;
        try {
            Security.addProvider(new BouncyCastleProvider());
            KeyStore ksKeys = KeyStore.getInstance("BKS");
            ksKeys.load(null, null);
            PEMReader cacertfile = new PEMReader(new InputStreamReader(context.getAssets().open(KEY_STORE_TRUST_PATH)));
            if (cacertfile != null) {
                X509Certificate cacert = (X509Certificate) cacertfile.readObject();
                cacertfile.close();
                KeyStore.TrustedCertificateEntry trustedEntry = new KeyStore.TrustedCertificateEntry(cacert);
                ksKeys.setEntry("ca_root", trustedEntry, null);
                TrustManagerFactory tmf = TrustManagerFactory.getInstance("X509");
                tmf.init(ksKeys);
                sslContext = SSLContext.getInstance("TLS");
                sslContext.init(null, tmf.getTrustManagers(), null);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sslContext;
    }

    private byte[] PubPackHttpHead(byte[] pData, int uiDataLen) {
        String chHttpServerHead =
                "POST /mjc/webtrans/VPB_lb HTTP/1.1\r\n" +
                        "HOST: 145.4.206.244:5000\r\n" +
                        "User-Agent: Donjin Http 0.1\r\n" +
                        "Cache-Control: no-cache\r\n" +
                        "Content-Type:x-ISO-TPDU/x-auth\r\n" +
                        "Accept: */*\r\n" +
                        "Content-Length: " + uiDataLen + "\r\n" +
                        "\r\n";
        byte[] vBuffer = chHttpServerHead.getBytes();
        int vLen = chHttpServerHead.getBytes().length;

        int len = (vBuffer.length) + (uiDataLen);
        byte[] retBuf = new byte[len];
        System.arraycopy(vBuffer, 0, retBuf, 0, vLen);
        System.arraycopy(pData, 0, retBuf, vLen, uiDataLen);
        return retBuf;
    }

    private byte[] PubCheckHttpHead(byte[] pBuffer) {
        byte[] pTmp;
        int len;
        //找到"\r\n\r\n",并删除前面的所有内容
        for (int i = 0; i < pBuffer.length; i++) {
            if (pBuffer[i] == '\r' && pBuffer[i + 1] == '\n' && pBuffer[i + 2] == '\r' && pBuffer[i + 3] == '\n') {
                i += 4;
                pTmp = new byte[pBuffer.length - i];
                LfUtils.memcpy(pTmp, 0, pBuffer, i, pBuffer.length - i);
                return pTmp;
            }
        }
        return null;
    }

    ///////////////////////////////////////回调显示//////////////////////////////////////////
    private IDisplay mIDisplay = null;

    /**
     * 回调显示提示信息
     **/
    private void displayMsg(String msg) {
        Display dis = new Display();
        dis.setType(DisplayType._4.getType());
        dis.setMsg(msg);
        doDisplayCallback(dis);
        dbg_mPrintf(TAG + " display:" + DisplayType.Display(dis));
    }

    /**
     * 设置提示回调接口，传入回调
     */
    public void setIDisplay(IDisplay p) {
        mIDisplay = p;
    }

    private Display mDisplay = null;
    private boolean mightBeOfflineData = false;

    private void doDisplayCallback(Display p) {
        mDisplay = p;
        (new Thread(new Runnable() {
            @Override
            public void run() {
                if (mDisplay != null && mIDisplay != null) {
                    mIDisplay.Display(mDisplay.getType(), null,
                            mDisplay.getMsg());// DisplayType.getDesc(mDisplay)
                }
            }
        })).start();
    }

    private synchronized void _ReplyXXK(byte path, byte xxk, ErrCode xxkcode)
            throws LfException {
        byte[] pcSendBuf = new byte[1024];
        int send_len = 0;
        int nRecvlen = 0, i = 0;
        UART_PROTOCOL ptPacketPara = new UART_PROTOCOL();
        ptPacketPara.path = path;// MISPOS.PROTOCOL_PATH_INFO_RECV;
        ptPacketPara.type = MISPOS.SERVICE_MISPOS_TYPE;
        LfUtils.memcpy(ptPacketPara.id, this.serial_ID, 6);

        // DATA数据
        _XXK_Reply tmpxx = new _XXK_Reply();
        tmpxx.setXxk(xxk);
        tmpxx.setXxkCode(xxkcode);
        byte[] tmpbbrp = tmpxx.getBytes();
        if (tmpbbrp == null) {
            this.setPospExchangStop(true);
            throw new LfException(-1, "cp _XXK_Reply bytes err");
        }
        ptPacketPara.datalen = (short) tmpbbrp.length;
        LfUtils.memcpy(ptPacketPara.data, tmpbbrp, tmpbbrp.length);
        // 重置成null
        send_len = mMISPOS.Mispos_Protocol_Pack(ptPacketPara, pcSendBuf);
        // 发送报文
        skipReadClean = true;
        this.send(pcSendBuf, send_len, 2000);
        skipReadClean = false;
    }

    /////////////////////////////////////传统银联参数及会员卡参数/////////////////////////////////////

    public void setTransCfx(TransCfx transCfx) {
        Logz.i(TAG,"raoj----->setTransCfx");
        if (transCfx != null) {
            Properties prop = new Properties();
            //也可以添加基本类型数据 get时就需要强制转换成封装类型
            if (!transCfx.getPos_ip().equals("") && transCfx.getPos_port() != 0) {
                setPosServer(transCfx.getPos_ip(), transCfx.getPos_port());
                prop.put("posp_ip", transCfx.getPos_ip());
                prop.put("posp_port", "" + transCfx.getPos_port());
                Logz.i(TAG, "posp:" + posp_ip + ":" + posp_port);
            }
            if (!transCfx.getHard_ip().equals("") && transCfx.getHard_port() != 0) {
                setHardServer(transCfx.getHard_ip(), transCfx.getHard_port());
                prop.put("hard_encrypt_ip", transCfx.getHard_ip());
                prop.put("hard_encrypt_port", "" + transCfx.getHard_port());
                Logz.i(TAG, "hard:" + hard_encrypt_ip + ":" + hard_encrypt_port);
            }
            if (!transCfx.getHard_ip2().equals("") && transCfx.getHard_port2() != 0) {
                Logz.e(TAG,"raoj----->transCfx.getHard_ip2():" + transCfx.getHard_ip2() + ",transCfx.getHard_port2():" + transCfx.getHard_port2());
                setHardServer2(transCfx.getHard_ip2(), transCfx.getHard_port2());
                prop.put("hard_encrypt_ip2", transCfx.getHard_ip2());
                prop.put("hard_encrypt_port2", "" + transCfx.getHard_port2());
                Logz.i(TAG, "hard2:" + hard_encrypt_ip2 + ":" + hard_encrypt_port2);
            }
            if (!transCfx.getCard_ip().equals("") && transCfx.getCard_port() != 0) {
                setCardServer(transCfx.getCard_ip(), transCfx.getCard_port());
                prop.put("card_ip", transCfx.getCard_ip());
                prop.put("card_port", "" + transCfx.getCard_port());
                Logz.i(TAG, "card:" + card_ip + ":" + card_port);
            }
            if (!transCfx.getSsl_ip().equals("") && transCfx.getSsl_port() != 0) {
                setSSLServer(transCfx.getSsl_ip(), transCfx.getSsl_port());
                prop.put("ssl_ip", transCfx.getSsl_ip());
                prop.put("ssl_port", "" + transCfx.getSsl_port());
                Logz.i(TAG, "ssl:" + ssl_ip + ":" + ssl_port);
            }
            if (!transCfx.getSand_card_ip().equals("") && transCfx.getSand_card_port() != 0) {
                setSandServer(transCfx.getSand_card_ip(), transCfx.getSand_card_port());
                prop.put("sand_card_ip", transCfx.getSand_card_ip());
                prop.put("sand_card_port", "" + transCfx.getSand_card_port());
                Logz.i(TAG, "sand_card:" + sand_card_ip + ":" + sand_card_port);
            }
            if (!transCfx.getSand_o2o_ip().equals("") && transCfx.getSand_o2o_port() != 0) {
                setSandO2OServer(transCfx.getSand_o2o_ip(), transCfx.getSand_o2o_port());
                prop.put("sand_o2o_ip", transCfx.getSand_o2o_ip());
                prop.put("sand_o2o_port", "" + transCfx.getSand_o2o_port());
                Logz.i(TAG, "sand_o2o:" + sand_o2o_ip + ":" + sand_o2o_port);
            }
            if (!transCfx.getTms_ip().equals("") && transCfx.getTms_port() != 0) {
                setTmsServer(transCfx.getTms_ip(), transCfx.getTms_port());
                prop.put("tms_ip", transCfx.getTms_ip());
                prop.put("tms_port", transCfx.getTms_port());
                Logz.i(TAG, "tms:" + tms_ip + ":" + tms_port);
            }
//            configUtil.saveConfig("/mnt/sdcard/", "LfCfg.txt", prop);
        }
    }

    public Properties getProperties() {
        Properties prop = prop = configUtil.loadConfig("/mnt/sdcard/", "LfCfg.txt");///sdcard/ev/LFPos/LfCfg.txt
        if (prop == null || prop.isEmpty()) {
            Logz.w(TAG, "prop:isEmpty");

        } else {
            //get出来的都是Object对象 如果是基本类型 需要用到封装类
            String isSSL_tmp = ((String) prop.get("isSSL"));
            if (isSSL_tmp != null) {
                isSSL = (isSSL_tmp.equals("true"));
                Logz.w(TAG, "isSSL:" + isSSL_tmp);
            }
            String isHard_tmp = ((String) prop.get("isHard"));
            if (isHard_tmp != null) {
                isHard = (isHard_tmp.equals("true"));
                Logz.w(TAG, "isHard:" + isHard_tmp);
            }
            String posp_ip_tmp = (String) prop.get("posp_ip");
            if (posp_ip_tmp != null) {
                posp_ip = posp_ip_tmp;
                Logz.w(TAG, "posp_ip:" + posp_ip_tmp);
            }
            String posp_port_tmp = (String) prop.get("posp_port");
            if (posp_port_tmp != null) {
                posp_port = Integer.parseInt(posp_port_tmp);
                Logz.w(TAG, "posp_port:" + posp_port_tmp);
            }
            String card_ip_tmp = (String) prop.get("card_ip");
            if (card_ip_tmp != null) {
                card_ip = card_ip_tmp;
                Logz.w(TAG, "card_ip:" + card_ip_tmp);
            }
            String card_port_tmp = (String) prop.get("card_port");
            if (card_port_tmp != null) {
                card_port = Integer.parseInt(card_port_tmp);
                Logz.w(TAG, "card_port:" + card_port_tmp);
            }
            String ssl_ip_tmp = (String) prop.get("ssl_ip");
            if (ssl_ip_tmp != null) {
                ssl_ip = ssl_ip_tmp;
                Logz.w(TAG, "ssl_ip:" + ssl_ip_tmp);
            }
            String ssl_port_tmp = (String) prop.get("ssl_port");
            if (ssl_port_tmp != null) {
                ssl_port = Integer.parseInt(ssl_port_tmp);
                Logz.w(TAG, "ssl_port:" + ssl_port_tmp);
            }
            String hard_encrypt_ip_tmp = (String) prop.get("hard_encrypt_ip");
            if (hard_encrypt_ip_tmp != null) {
                hard_encrypt_ip = hard_encrypt_ip_tmp;
                Logz.w(TAG, "hard_encrypt_ip:" + hard_encrypt_ip_tmp);
            }
            String hard_encrypt_port_tmp = (String) prop.get("hard_encrypt_port");
            if (hard_encrypt_port_tmp != null) {
                hard_encrypt_port = Integer.parseInt(hard_encrypt_port_tmp);
                Logz.w(TAG, "hard_encrypt_port:" + hard_encrypt_port_tmp);
            }
            String hard_encrypt_ip2_tmp = (String) prop.get("hard_encrypt_ip2");
            if (hard_encrypt_ip2_tmp != null) {
                hard_encrypt_ip2 = hard_encrypt_ip2_tmp;
                Logz.w(TAG, "hard_encrypt_ip2:" + hard_encrypt_ip2_tmp);
            }
            String hard_encrypt_port2_tmp = (String) prop.get("hard_encrypt_port2");
            if (hard_encrypt_port2_tmp != null) {
                hard_encrypt_port2 = Integer.parseInt(hard_encrypt_port2_tmp);
                Logz.w(TAG, "hard_encrypt_port2:" + hard_encrypt_port2_tmp);
            }
            String sand_card_ip_tmp = (String) prop.get("sand_card_ip");
            if (sand_card_ip_tmp != null) {
                sand_card_ip = sand_card_ip_tmp;
                Logz.w(TAG, "sand_card_ip:" + sand_card_ip_tmp);
            }
            String sand_card_port_tmp = (String) prop.get("sand_card_port");
            if (sand_card_port_tmp != null) {
                sand_card_port = Integer.parseInt(sand_card_port_tmp);
                Logz.w(TAG, "sand_card_port:" + sand_card_port_tmp);
            }
            String sand_o2o_ip_tmp = (String) prop.get("sand_o2o_ip");
            if (sand_o2o_ip_tmp != null) {
                sand_o2o_ip = sand_o2o_ip_tmp;
                Logz.w(TAG, "sand_o2o_ip:" + sand_o2o_ip_tmp);
            }
            String sand_o2o_port_tmp = (String) prop.get("sand_o2o_port");
            if (sand_o2o_port_tmp != null) {
                sand_o2o_port = Integer.parseInt(sand_o2o_port_tmp);
                Logz.w(TAG, "sand_o2o_port:" + sand_o2o_port_tmp);
            }
            String tms_ip_tmp = (String) prop.get("tms_ip");
            if (tms_ip_tmp != null) {
                tms_ip = tms_ip_tmp;
                Logz.w(TAG, "tms_ip:" + tms_ip_tmp);
            }
            String tms_port_tmp = (String) prop.get("tms_port");
            if (tms_port_tmp != null) {
                tms_port = Integer.parseInt(tms_port_tmp);
                Logz.w(TAG, "tms_port:" + tms_port_tmp);
            }
            Logz.w(TAG, "prop:" + prop.toString());
        }
        return prop;
    }

    /**
     * 初始化串口
     **/
    public void init() throws LfException {
        try {
            this.IOpen(this.path, this.para);
            strcpy(serial_ID, "000001");
        } catch (LfException e) {
            throw new LfException(e.getErrcode(), e.getMessage());
        }
        this.isApiRunning = true;
        this.stop = false;
        // 判断是否已经运行了检查线程
        if (!isdoPosAutoTaskRunning && (isAutoSettle() || isAutoTms())) {
            this.doPosAutoTask();// 自动结算的才进行自动任务
        }
    }

    private boolean stop = true;

    /**
     * 释放串口
     **/
    public void release() {
        this.IClose();
        this.stop = true;
        isPospExchangStop = true;// 停止posp交互
        this.isApiRunning = false;
        this.isUserInterrupted = true;
    }

    // 检查用户调用/操作是否允许中断
    private void _IdleTaskInterrupt() throws LfException {
        int tmo = 5 * 1000;
        if (pospExchangeThead != null) {
            pospExchangeThead.interrupt();
        }
        isUserInterrupted = true;
        while (this.userTradeType != TradeType.NONE) {
            try {
                Thread.sleep(50);
                tmo -= 50;
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
        isUserInterrupted = false;
    }

    private String path = "";
    private String para = "";

    /**
     * 设置串口参数
     *
     * @param path 串口号
     * @param para 波特率
     **/
    public void setPara(String path, String para) {
        this.path = path;
        this.para = para;
    }

    /**
     * 当前设定的交易或渠道类型：银联金融模块、会员卡、杉德等等
     * 0x01--银行卡
     * 0xF0--骏鹏会员卡
     * 0x92--杉德会员卡
     * 0x93--杉德O2O交易平台
     **/
    public void setType(byte t) {
        this.mMISPOS.setType(t);
    }

    /**
     * 当前设定的交易或渠道类型：银联金融模块、会员卡、杉德等等
     * 0x01--银行卡
     * 0xF0--骏鹏会员卡
     * 0x92--杉德会员卡
     * 0x93--杉德O2O交易平台
     */
    public byte getType() {
        return this.mMISPOS.getType();
    }

    private String ip = "";
    private int port = 0;

    /**
     * 设置通讯服务器
     **/
    public void setServer(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    private String posp_ip = "";
    private int posp_port = 0;

    /**
     * 设置银联服务器
     **/
    public void setPosServer(String ip, int port) {
        this.posp_ip = ip;
        this.posp_port = port;
    }

    private String update_ip = "";
    private int update_port = 8003;

    /**
     * 升级服务器
     */
    public void setUpdateServer(String ip, int port) {
        this.update_ip = ip;
        this.update_port = port;
    }

    /**
     * 会员卡服务器
     **/
//    private String card_ip = "121.40.30.62";
//    private int card_port = 18080;
    private String card_ip = "124.128.89.187";
    private int card_port = 6542;

    public void setCardServer(String ip, int port) {
        this.card_ip = ip;
        this.card_port = port;
    }

    /**
     * SSL地铁参数
     **/
    private String ssl_ip = "101.231.141.158";
    private int ssl_port = 30000;

    public void setSSLServer(String ip, int port) {
        this.ssl_ip = ip;
        this.ssl_port = port;
    }

    ///////////////////////////////////////////硬加密////////////////////////////////////////////////
    private String hard_encrypt_ip = "180.166.112.24";
    private int hard_encrypt_port = 10003;

    public void setHardServer(String ip, int port) {
        this.hard_encrypt_ip = ip;
        this.hard_encrypt_port = port;
    }

    private String hard_encrypt_ip2 = "180.166.112.24";
    private int hard_encrypt_port2 = 10004;

    public void setHardServer2(String ip, int port) {
        this.hard_encrypt_ip2 = ip;
        this.hard_encrypt_port2 = port;
    }

    ////////////////////////////////////////////杉德参数/////////////////////////////////////////////
    private String sand_card_ip = "192.168.1.1";
    private int sand_card_port = 80;

    /**
     * 设置杉德会员卡服务器
     **/
    public void setSandServer(String ip, int port) {
        this.sand_card_ip = ip;
        this.sand_card_port = port;
    }

    private String sand_o2o_ip = "192.168.1.2";
    private int sand_o2o_port = 0;

    /**
     * 设置杉德O2O服务器
     **/
    public void setSandO2OServer(String ip, int port) {
        this.sand_o2o_ip = ip;
        this.sand_o2o_port = port;
    }

    private String tms_ip = "";
    private int tms_port = 0;

    public void setTmsServer(String ip, int port) {
        this.tms_ip = ip;
        this.tms_port = port;
    }

    /////////////////////////////////////////////串口操作////////////////////////////////////////////

    /**
     * 设置串口通讯接口
     *
     * @param pISerialPort null时使用android的串口jni，SerialPort
     */
    public void setPOSISerialPort(ISerialPort pISerialPort) {
        this.setISerialPort(pISerialPort);
    }

    //发送包
    private int send(byte[] data, int len, int timeout) throws LfException {
        // 因为是一发一收的通讯形式，所以在发送之前要先进行接收缓存的清除
        byte[] tmpr = new byte[128];
        int ra = 0, ret = 0;
//        while (!skipReadClean && (ra = this.IReadAvailable()) > 0) {// 在发送之前，清理接收缓存
//            ra = ra > 128 ? 128 : ra;
//            this.IRead(tmpr, ra, 300);
//            dbg_TprintfWHex(TAG, tmpr, ra, "clear recv");
//        }
        if (len < 512) {
            dbg_TprintfWHex(TAG, data, len, "send to POS len(" + len + ")");
        } else {
            Logz.d(TAG, "send to POS len(" + len + ")");
        }
        ret = this.IWrite(data, len, timeout);
        this.IFlush();
        return ret;
    }

    // 解包读
    private int read(byte[] data, int len, int timeout) throws LfException {
        int ret = 0;
        int tmo = timeout;
        byte[] tmpS = new byte[2];
        byte[] tmpL = new byte[3];
        byte[] tmpbb = new byte[1024];
        short tmpshort = 0;
        tmo = tmo < 2000 ? 2000 : tmo;
        tmo += 500;// 加0.5秒
        if (len < 13) {
            return 0;
        }
        while (tmo > 0) {
            Arrays.fill(tmpS, (byte) 0x00);
            Arrays.fill(tmpL, (byte) 0x00);
            Arrays.fill(tmpbb, (byte) 0x00);
            tmpshort = 0;
            ret = 0;

            while (tmo > 0) {
                if (isUserInterrupted) {// 被中断
                    return 0;
                }
                if (1 <= this.IReadAvailable()) {
                    ret = this.IRead(tmpS, 1, -1);
                    // tmo -=100;
                    if (ret == 1 && tmpS[0] == MISPOS.PACK_STX) {
                        break;
                    } else {
                        if (1 <= this.IReadAvailable()) {
                            continue;
                        }
                        dbg_TprintfWHex(TAG, tmpS, 1, "STX err");
                        continue;
                    }
                } else {
                    try {
                        Thread.sleep(30);
                        tmo -= 30;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return 0;
                    }
                }
            }

            while (tmo > 0) {
                if (isUserInterrupted) {// 被中断
                    return 0;
                }
                if (2 <= this.IReadAvailable()) {
                    ret = this.IRead(tmpL, 2, -1);
                    break;
                }
                try {
                    Thread.sleep(30);
                    tmo -= 30;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (ret != 2) {
                if (ret > 0 && ret < 2) {
                    dbg_TprintfWHex(TAG, tmpL, ret, "tmpL");
                }
                dbg_TprintfWHex(TAG, tmpL, 2, "excep, ret(" + ret + "),LEN");
                continue;
            }
            tmpshort += ((tmpL[0] & 0x00ff) * 256) & 0x00ffff;
            tmpshort += tmpL[1] & 0x00ff;
            tmpshort += 2;// 加上两字节的ETX、LRC
            if (len < (1 + 2 + tmpshort)) {// 是否可以放下报文长度
                Logz.i(TAG, "len{" + len + "}<(1+2+tmpshort){" + (1 + 2 + tmpshort) + "},continue...");
                continue;
            }
            while (tmo > 0) {
                if (isUserInterrupted) {// 被中断
                    return 0;
                }
                if (tmpshort <= this.IReadAvailable()) {
                    ret = this.IRead(tmpbb, tmpshort, -1);
                    break;
                }
                try {
                    Thread.sleep(30);
                    tmo -= 30;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (ret != tmpshort) {
                if (ret > 0 && ret < tmpbb.length) {
                    dbg_TprintfWHex(TAG, tmpbb, ret, "tmpbb");
                }
                Logz.i(TAG, "rest frame err, need:" + tmpshort + ", recved:" + ret + ",continue...");
                continue;
            }
            System.arraycopy(tmpS, 0, data, 0, 1);
            ret = 1;
            System.arraycopy(tmpL, 0, data, 1, 2);
            ret += 2;
            System.arraycopy(tmpbb, 0, data, 1 + 2, tmpshort);
            ret += tmpshort;

            if (ret > 4) {
                recvType = data[4];
                setType(data[4]);
            } else {
                recvType = (byte) 0xFF;
            }
            break;
        }
        if (ret < 4096) {
            dbg_TprintfWHex(TAG, data, ret, "recv from POS len(" + ret + ")");
        } else {
            Logz.d(TAG, "recv from POS len(" + ret + ")");
        }
        return ret;
    }

    // 打包写
    private int write(byte[] data, int len, int timeout) throws LfException {
        byte[] pcSendBuf = new byte[1024];
        int send_len = 0, ret = 0;
        UART_PROTOCOL ptPacketPara = new UART_PROTOCOL();
        synchronized (lockApi) {
            // 打包下发
            // 发送设置
            ptPacketPara.path = MISPOS.PROTOCOL_PATH_RECV;
            ptPacketPara.type = MISPOS.SERVICE_MISPOS_TYPE;
            LfUtils.memcpy(ptPacketPara.id, this.serial_ID, 6);
            // DATA数据
            LfUtils.memcpy(ptPacketPara.data, data, len);
            ptPacketPara.datalen = (short) len;
            // 重置成null
            this.setPospExchangeRecv(null);
            send_len = mMISPOS.Mispos_Protocol_Pack(ptPacketPara, pcSendBuf);
            // 发送报文
            ret = this.send(pcSendBuf, send_len, timeout);
        }
        return ret;
    }


    ////////////////////////////////////////////指令打包和解包///////////////////////////////////////

    public static byte[] Trade2Buffer(_03_Common t) {
        byte[] retbb = null;
        try {
            switch (t.getTradeType()) {
                // 带参数的的指令------------------------
                case PURCHASE:// 消费
                    retbb = ((_03_Purchase) t).getBytes();
                    break;
                case GET_PRINT_INFO:// 获取打印信息
                    retbb = ((_03_GetPrintInfo) t).getBytes();
                    break;
                case RECHARGE:// 充值
                    retbb = ((_03_Recharge) t).getBytes();
                    break;
                case RECHARGE_SHENSI_FANKA:// 神思饭卡充值
                    retbb = ((_03_Shensi_Recharge) t).getBytes();
                    break;
                case QUERY_SHENSI_FANKA:// 神思饭卡余额查询
                    retbb = ((_03_Shensi_Query) t).getBytes();
                    break;
                case GET_RECORD:
                    retbb = ((_03_GetRecord) t).getBytes();
                    break;
                case WITHDRAW:// 助农取款
                    retbb = ((_03_Withdraw) t).getBytes();
                    break;
                case CASHREM:// 现金汇款
                    retbb = ((_03_Cashrem) t).getBytes();
                    break;
                case TRANFERREM:// 转账汇款
                    retbb = ((_03_Transferrem) t).getBytes();
                    break;
                case PASSBOOKRENEW:// ("43","43存折补登"),
                    retbb = ((_03_PassbookRenew) t).getBytes();
                    break;
                case DOWNLOADPAYCODE:// ("44","44缴费应用代码下载"),
                    retbb = ((_03_DownloadAppcode) t).getBytes();
                    break;
                case GETZNRECORDINFO:// ("45","45取助农类交易信息"),
                    retbb = ((_03_GetZNRecordInfo) t).getBytes();
                    break;
                case GET_CARDNO:// 获取卡号
                    retbb = ((_03_ReadCardNo) t).getBytes();
                    break;
                case GET_ACCOUNTNAME:// 获取户名
                    retbb = ((_03_ReadAccountName) t).getBytes();
                    break;
                case PAYFEES_QUERY:// 缴费查询
                    retbb = ((_03_PayfeesQuery) t).getBytes();
                    break;
                case BOUNDACCOUNT_QUERY:// 绑定账户信息查询
                    retbb = ((_03_BoundAccountQuery) t).getBytes();
                    break;
                case PAYFEES:// 缴费
                    retbb = ((_03_Payfees) t).getBytes();
                    break;
                case PASSBOOK_TO_CARD:// 存折转卡
                    retbb = ((_03_PassbookToCard) t).getBytes();
                    break;
                case GETKEY_REQ:// 46获取按键透传值
                    retbb = ((_03_GetKeyRequest) t).getBytes();
                    break;
                case TEST_ZNWITHDRAW:
                    retbb = ((_03_TestZNWithdraw) t).getBytes();
                    break;
                case TEST_ZNRECORDINFO:
                    retbb = ((_03_TestGetZNRecordInfo) t).getBytes();
                    break;
                case TEST_ZNTRANFERREM:
                    retbb = ((_03_TestZNTransferrem) t).getBytes();
                    break;
                case CHECK_BALANCE:
                    retbb = ((_03_CheckBalance) t).getBytes();
                    break;
                case TRANS_QUERY:
                    retbb = ((_03_TransQuery) t).getBytes();
                    break;
                case CTB_PURCHASE:
                    retbb = ((_03_PosCSBTrade) t).getBytes();
                    break;
                case CTB_QUERY:
                    retbb = ((_03_PosCSBQuery) t).getBytes();
                    break;
                case TWO_IN_ONE:
                    retbb = ((_03_TwoInOne) t).getBytes();
                    break;
                case THREE_IN_ONE:
                    retbb = ((_03_ThreeInOne) t).getBytes();
                    break;
                case BTC_CANCEL:
                    retbb = ((_03_BSCCancel) t).getBytes();
                    break;
                // 不带参数的指令----------------------
                case SIGNIN:// 签到
                case SETTLE:// 结算
                case UPLOADOFFLINE:// 脱机交易上送
                case QUERY:// 查余额
                case GET_MERTMN:
                case GET_KEYBOARD:
                case TEST_MK210:
                case SEND_TMK://终端下发TMK
                case SET_MERTMN:

                default:
                    retbb = t.getBytes();
                    break;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            retbb = new byte[1];
        }
        return retbb;
    }

    private int sockTimeout = 60 * 1000;

    private boolean _RecvHandler(byte expected_path, TradeType currentTradeType, byte[] pcRecvData, int[] recvlen,
                                 UART_PROTOCOL ptOutPara, long timeout) throws LfException {
        Logz.i(TAG, "raoj--->_RecvHandler");
        byte[] pcSendBuf = new byte[1024 * 15];
        byte[] tmpOutParaData = null;
        byte[] tmpPospReply = null;
        boolean returnRightNow = true;
        int send_len = 0;
        int nRecvlen = 0, i = 0;
        UART_PROTOCOL ptPacketPara = new UART_PROTOCOL();
        // 接收报文
        long tmo = (timeout > 0 && timeout <= 2000) ? 2000 : timeout;
        if (timeout == 280) {
            tmo = 280;
        }
        this.setPospExchangeRecv(null);
        this.setPospExchangStop(true);
        isSendCancel = false;

        boolean isWaitingForEver = tmo == 0 ? true : false;//是否无限等

        // 用于内部处理的解包
        REPLY mREPLY = new REPLY();
        //连接升级服务器socket
        SocketAddress address;
        Socket updateSocket = new Socket();

        try {
            while ((isWaitingForEver) || (!isWaitingForEver && tmo > 0)) {//无限等或者超时等
                if (isUserInterrupted) {// 被中断
                    Logz.i("userInterrupted return");
                    return false;
                }
                if (this.IReadAvailable() > 0) {
                    nRecvlen = this.read(pcRecvData, pcRecvData.length, 8000);
                    if (nRecvlen == 0) {
                        return false;
                    }
                    if (pcRecvData[19] == (byte) 0x55 && pcRecvData[20] == (byte) 0x55) {
                        FLAG_55 = 2;
                    } else if (FLAG_TMS == 3) {
                        socket.close();
                        socket = null;
                        FLAG_TMS = 0;
                    }

                    //收到的报文头两位为{0x55,0x55}时进入升级流程
                    if (pcRecvData[0] == (byte) 0x55 && pcRecvData[1] == (byte) 0x55) {
                        long send_read = -1;
                        byte[] retbb = null;
                        if (updateSocket != null) {
                            try {
                                displayMsg(">>>发送数据...");
                                send_read = System.currentTimeMillis();
                                updateSocket.getOutputStream().write(pcRecvData, 0, nRecvlen);
                                dbg_TprintfWHex(TAG, pcRecvData, nRecvlen, "connect ok, write:");
                                System.out.println("write use time:" + (System.currentTimeMillis() - send_read));
                            } catch (IOException e) {
                                e.printStackTrace();
                                try {
                                    System.out.println("posp连接失败2：" + this.update_ip + ", " + this.update_port);
                                    _ReplyXXK(MISPOS.PROTOCOL_PATH_RECV
                                            , MISPOS.PACK_NAK, ErrCode._X6);//数据发送失败
                                } catch (LfException e1) {
                                    e1.printStackTrace();
                                }
                                throw new LfException(Errs.OTHER_ERR, ErrCode._X6.getDesc());
                            }

                            try {
                                displayMsg("<<<接收数据...");
                                byte[] tmpL = new byte[2];
                                int tmpiL = 0;
                                boolean recved = false;
                                IOException te = null;
                                try {
                                    retbb = readUpdateResponse(updateSocket);
                                } catch (IOException e) {//可能是读取结束
                                    te = e;
                                } finally {
                                    if (retbb != null) {
                                        System.out.println("http rep OK");
                                    } else {
                                        if (te != null) {
                                            throw te;
                                        } else {
                                            throw new LfException(Errs.OTHER_ERR, ErrCode._X8.getDesc());
                                        }
                                    }
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                                if (false) {
                                    //回复NAK
                                    try {
                                        System.out.println("数据接收错误2posp：" + this.update_ip + ", " + this.update_port);
                                        _ReplyXXK(MISPOS.PROTOCOL_PATH_RECV
                                                , MISPOS.PACK_NAK, ErrCode._X7);//数据接收失败
                                    } catch (LfException e1) {
                                        e1.printStackTrace();
                                    }
                                }
                                displayMsg("数据接收错误2");
                                throw new LfException(Errs.OTHER_ERR, "exchane err, len:" + nRecvlen);
                            }
                            if (retbb != null) {
                                dbg_mPrintf("-----》update服务器返回数据，下发pos...");
                                this.send(retbb, retbb.length, 2000);
                                retbb = null;
                                tmo = (60 + 8) * 1000;
                            } else {
                                //TODO:如果为空的处理
                            }
                        }

                    } else {
                        mMISPOS.Mispos_Protocol_Unpack(pcRecvData, (short) nRecvlen, ptOutPara);
                        Logz.i(TAG, ">>>>>>>>>>Unpack TYPE:" + String.format("%02X", ptOutPara.type) + " , >>>>>>>>>>Unpack PATH:" + String.format("%02X", ptOutPara.path));
                        isHardEncryption = false;
                        {//硬加密处理部分
                            byte[] tpdu = {0x20, 0x20, 0x20, 0x20, 0x20};
                            if (0 == LfUtils.memcmp(ptOutPara.data, tpdu, 5)) {
                                isHardEncryption = true;
                                tmpOutParaData = new byte[ptOutPara.datalen - 5];
                                LfUtils.memcpy(tmpOutParaData, 0, ptOutPara.data, 5, (ptOutPara.datalen - 5));
                                Logz.w(TAG, "去掉了0x20");
                            } else {
                                isHardEncryption = false;
                            }
                        }

                        // 检查报文type
                        switch (ptOutPara.path) {
                            case MISPOS.PROTOCOL_PATH_TEST_RECV:
                                FLAG_TMS = 0;
                                return true;
                            case MISPOS.PROTOCOL_PATH_ASK_RECV:// //04收银应答<直接返回>
                                Logz.i(TAG, ">>>>>>>>>>04 response unpacking");
                                returnRightNow = true;
                                mREPLY.DisableDBG();
                                mREPLY.Unpack(ptOutPara);
                                mREPLY.EnableDBG();
                                Logz.i(TAG, ">>>>>>>>>>04 response unpack done");
                                Logz.i(TAG, ">>>>>>>>>>04 response unpack done,mREPLY.code:" + mREPLY.code + ", mREPLY.code_info:" + mREPLY.code_info);
                                if (mREPLY.code.equals("07")) {// 如果是取消指令则继续接收
                                    returnRightNow = true;
                                } else if (mREPLY.code.equals("48")) {// 读卡改成了异步返回
                                    _04_ReadCardNoReply tmpRPL = new _04_ReadCardNoReply();
                                    tmpRPL.Unpack(ptOutPara);
                                    // ===================把卡信息异步返回，然后继续读取按键透传====================
                                    Display dis = new Display();
                                    dis.setType(DisplayType._card.getType());
                                    dis.setMsg(((_04_ReadCardNoReply) tmpRPL).getCardNo() + ","
                                            + ((_04_ReadCardNoReply) tmpRPL).getTrack2() + ","
                                            + ((_04_ReadCardNoReply) tmpRPL).getTrack3() + ","
                                            + ((_04_ReadCardNoReply) tmpRPL).getExtra());
                                    doDisplayCallback(dis);// 异步返回
                                    returnRightNow = false;// 接续接收
                                } else if (mREPLY.code.equals("B8")) {
                                    _04_TransQueryReply tmpRPL = new _04_TransQueryReply();
                                    tmpRPL.Unpack(ptOutPara);
                                    Display dis = new Display();
                                    dis.setType(DisplayType._record.getType());
                                    String flag = ((_04_TransQueryReply) tmpRPL).getIsEnd();
                                    String info = ((_04_TransQueryReply) tmpRPL).getInfo();
                                    dis.setMsg(info + "\n");

                                    doDisplayCallback(dis);// 异步返回
                                    if (flag.equals("1")) {
                                        returnRightNow = false;// 接续接收
                                    } else {
                                        returnRightNow = true;
                                    }
                                } else if (mREPLY.code.equals("Z1") || mREPLY.code.equals("XY")
                                        || mREPLY.code.equals("X8") || mREPLY.code.equals("X7")
                                        || mREPLY.code.equals("A0") || mREPLY.code.equals("X6")
                                        || mREPLY.code.equals("XX") || mREPLY.code.equals("Z0")) {
                                    Display dis = new Display();
                                    dis.setType(DisplayType._6.getType());
                                    dis.setMsg(mREPLY.code_info + "\n");

                                    doDisplayCallback(dis);// 异步返回
                                    returnRightNow = true;
                                }
                                if (returnRightNow) {
                                    // 停止线程
                                    this.setPospExchangStop(true);
                                    Logz.i(TAG, ">>>>>>>>>>04 response and return");
                                    if (socket != null && getUserTradeType() == TradeType.TMS_DOWNLOAD_KEY) {
                                        socket.close();
                                        socket = null;
                                    }
                                    return true;
                                }
                                Logz.i(TAG, ">>>>>>>>>>04 break and continue reading");
                                break;
                            case MISPOS.PROTOCOL_PATH_SEND: //05透传发送<内部流程处理>
                                if (currentTradeType == TradeType.UPLOADOFFLINE) {// 离线上送报文，另外处理
                                    dbg_mPrintf("-----》透传 >>离线上送报文，另外处理...");
                                    if (ptOutPara.datalen > 0 && ptOutPara.datalen < 2048) {
                                        mREPLY.Unpack(ptOutPara);
                                        if (mREPLY.reply == MISPOS.PACK_NAK) {
                                            mightBeOfflineData = false;
                                            Logz.i(TAG, ">>>>没有脱机上送数据");
                                        } else {
                                            Logz.i(TAG, ">>>>得到脱机上送数据");
                                            posOfflineBuffer = new byte[ptOutPara.datalen + 2];
                                            // 数据要加上长度
                                            posOfflineBuffer[0] = (byte) (ptOutPara.datalen % 256);// (byte)
                                            // (len&0x00ff);
                                            posOfflineBuffer[1] = (byte) (ptOutPara.datalen / 256);// (byte)
                                            // ((len&0x00ff00)>>8);
                                            LfUtils.memcpy(posOfflineBuffer, 2, ptOutPara.data, 0, ptOutPara.datalen);
                                        }
                                    }
                                    Logz.i(TAG, "应答脱机上送包->POS");
                                    // 回复ACK
                                    _ReplyXXK(MISPOS.PROTOCOL_PATH_INFO_RECV, MISPOS.PACK_ACK, ErrCode._00);
                                    return true;
                                } else {// 其它的直接透传
                                    // 透传，新开线程处理，通过标志判断
                                    this.setPospExchangStop(true);
                                    Logz.e("新开线程处理");
                                    try {
                                        Thread.sleep(50);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    if (isHardEncryption) {
                                        Logz.i(TAG, "isHardEncryption");
                                        doPospExchange(tmpOutParaData, tmpOutParaData.length, sockTimeout);
                                    } else {
                                        Logz.i(TAG, "!isHardEncryption");
                                        doPospExchange(ptOutPara.data, ptOutPara.datalen, sockTimeout);
                                    }
                                    tmo = sockTimeout + 5 * 1000;//超时时间重设
                                    break;
                                }
                            case MISPOS.PROTOCOL_PATH_INFO_SEND:// 07POS通知信息包<内部流程处理>
                                // TODO:显示信息处理，新开线程，回调？
                                _07_Common disc = new _07_Common();
                                i = disc.Unpack(ptOutPara);
                                Display dis = disc.getDis();
                                if (dis.getMsg().equals("欢迎使用银联云闪付")) {
                                    Logz.d(TAG, "dis.getType:" + dis.getType() + ", dis.getMsg:" + dis.getMsg());
                                    _ReplyXXK(MISPOS.PROTOCOL_PATH_INFO_RECV, MISPOS.PACK_ACK, ErrCode._00);
                                    setUserTradeType(TradeType.NONE);
                                    doDisplayCallback(dis);
                                    Sleep(2000);
                                    throw new LfException(Errs.OTHER_ERR, ErrCode._Z1.getDesc());
                                }
                                doDisplayCallback(dis);
                                // TODO:遇到输入密码或者确认卡号时，超时时间需要重新设置！！！！！！！！！！！！显示内容回调
                                if (disc.getTradeType() == TradeType.GETKEY_KEYREPORT) {
                                    Logz.d("GETKEY_KEYREPORT");
                                } else if (dis.getType().equals("6")) {
                                    //TODO:创建并连接新的socket
                                    String[] conn_param = dis.getMsg().split(":");
                                    if (conn_param.length == 3) {
                                        this.setUpdateServer(conn_param[1], Integer.parseInt(conn_param[2]));
                                        int trycnt = 2, retrysleep = 200;
                                        boolean needReply = false;
                                        try {
                                            Logz.d(TAG, "update_ip:" + this.update_ip + ",update_port:" + this.update_port);
                                            address = new InetSocketAddress(this.update_ip, this.update_port);
                                            updateSocket.connect(address, (trycnt > 1 ? (4 * 1000) : (8 * 1000)));//4\8秒超时时间
                                        } catch (UnknownHostException e) {
                                            e.printStackTrace();
                                            if (trycnt-- < 1) {
                                                if (needReply) { //回复NAK
                                                    try {
                                                        System.out.println("update连接失败：" + this.update_ip + ", " + this.update_ip);
                                                        _ReplyXXK(MISPOS.PROTOCOL_PATH_RECV, MISPOS.PACK_NAK, ErrCode._X6);//数据发送失败
                                                    } catch (LfException e1) {
                                                        e1.printStackTrace();
                                                    }
                                                }
                                                displayMsg("服务器连接失败1");
                                                throw new LfException(Errs.OTHER_ERR, ErrCode._Z0.getDesc());
                                            } else {
                                                try {
                                                    Thread.sleep(retrysleep);
                                                } catch (InterruptedException e1) {
                                                    e1.printStackTrace();
                                                }
                                            }
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                            if (trycnt-- < 1) {
                                                if (true) {//回复NAK
                                                    try {
                                                        System.out.println("update连接失败2：" + this.update_ip + ", " + this.update_ip);
                                                        _ReplyXXK(MISPOS.PROTOCOL_PATH_RECV, MISPOS.PACK_NAK, ErrCode._X6);//数据发送失败
                                                    } catch (LfException e1) {
                                                        e1.printStackTrace();
                                                    }
                                                }
                                                displayMsg("服务器连接失败2");
                                                throw new LfException(Errs.OTHER_ERR, ErrCode._Z0.getDesc());
                                            } else {
                                                try {
                                                    Thread.sleep(retrysleep);
                                                } catch (InterruptedException e1) {
                                                    e1.printStackTrace();
                                                }
                                            }
                                        }
                                        _ReplyXXK(MISPOS.PROTOCOL_PATH_INFO_RECV, MISPOS.PACK_ACK, ErrCode._00);
                                    } else {
                                        _ReplyXXK(MISPOS.PROTOCOL_PATH_INFO_RECV, MISPOS.PACK_ACK, ErrCode._00);
                                    }
                                } else {
                                    skipReadClean = true;
                                    _ReplyXXK(MISPOS.PROTOCOL_PATH_INFO_RECV, MISPOS.PACK_ACK, ErrCode._00);
                                    skipReadClean = false;
                                }
                                break;
                        }
                    }
                } else {
//                    Logz.i(TAG, "raoj-----透传线程处理");
                    // 透传线程处理结果判断
                    tmpPospReply = this.getPospExchangeRecv();
                    if (tmpPospReply != null && tmpPospReply.length < 1024 * 15) {// 可以判断为收到Posp回复<内部流程处理>
                        // 打包下发
                        ptPacketPara.path = MISPOS.PROTOCOL_PATH_RECV;
                        // 发送设置
                        LfUtils.memcpy(ptPacketPara.id, this.serial_ID, 6);
                        if (isSendCancel) {//骏鹏需求，修改流程需要在通讯过程也能取消交易。
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            _03_Common t = new _03_Common();
                            t.setTradeType(TradeType.CANCEL);//交易类型
                            this._07_Cancel(t);
                        }
                        // DATA数据
                        LfUtils.memcpy(ptPacketPara.data, tmpPospReply, tmpPospReply.length);
                        ptPacketPara.datalen = (short) tmpPospReply.length;
                        // 重置成null
                        this.setPospExchangeRecv(null);
                        ptPacketPara.type = mMISPOS.getType();
                        ptPacketPara.type = recvType == (byte) 0xFF ? ptPacketPara.type : recvType;
                        send_len = mMISPOS.Mispos_Protocol_Pack(ptPacketPara, pcSendBuf);
                        // 发送报文
                        skipReadClean = true;
                        Logz.i(TAG, "转发->POS");
                        if (FLAG_TMS == 3) {
                            this.send(tmpPospReply, tmpPospReply.length, 2000);
                        } else {
                            this.send(pcSendBuf, send_len, 2000);
                        }
                        skipReadClean = false;
                    }
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        if (!isWaitingForEver) {//有超时的情况下-时间
                            tmo -= 20;
                        }
                    }
                }
            }
        } catch (LfException e) {
            e.printStackTrace();
            this.setPospExchangStop(true);
            throw e;
        } catch (IOException e) {
            e.printStackTrace();
        }
        Logz.i("end return");
        return false;
    }

    ///////////////////////////////////////////指令发送处理//////////////////////////////////////////

    /**
     * 测试请求和应答
     */

    public REPLY _01_Test() throws LfException {
        synchronized (lockApi) {
            this.updateOpTime();
            byte[] pcSendBuf = new byte[1024];
            int send_len = 0;
            byte[] pcRecvData = new byte[1024];
            int nRecvlen = 0, i = 0;
            int[] nRecvlenbb = {0};
            boolean recved = false, tmpSkip = false;
            int cnt = 5;
            UART_PROTOCOL ptPacketPara = new UART_PROTOCOL();
            UART_PROTOCOL ptOutPara = new UART_PROTOCOL();
            REPLY mREPLY = new REPLY();
            if (isBusy) {
                mREPLY.reply = MISPOS.PACK_NAK;
                mREPLY.code = ErrCode._FF.getCode();
                mREPLY.code_info = ErrCode._FF.getDesc();
                return mREPLY;
            }
            // 发送设置
            ptPacketPara.path = MISPOS.PROTOCOL_PATH_TEST_SEND;
            ptPacketPara.type = mMISPOS.getType();
            ptPacketPara.type = recvType == (byte) 0xFF ? ptPacketPara.type : recvType;
            // Serial ID
            if (this.serial_ID[0] == 0x00) {
                strcpy(serial_ID, "000001");
            }
            mMISPOS.Mispos_serial_num_handle((byte) 1, this.serial_ID);
            LfUtils.memcpy(ptPacketPara.id, this.serial_ID, 6);
            // 指令代码
            ptPacketPara.datalen = 2;
            ptPacketPara.data[0] = '9';
            ptPacketPara.data[1] = '9';
            ptPacketPara.data[2] = 0;
            send_len = mMISPOS.Mispos_Protocol_Pack(ptPacketPara, pcSendBuf);
            // 发送报文
            tmpSkip = skipReadClean;
            skipReadClean = false;
            this.send(pcSendBuf, send_len, 1000);
            skipReadClean = tmpSkip;
            // ------------------------------------------------------------------------
            while (cnt-- > 0) {
                // 接收报文
                try {
                    recved = _RecvHandler(MISPOS.PROTOCOL_PATH_TEST_SEND,
                            TradeType.NONE, pcRecvData, nRecvlenbb, ptOutPara,
                            2000);
                } catch (LfException e) {
                    if (cnt > 0) {
                        continue;
                    }
                    throw new LfException(Errs.OTHER_ERR, e.getMessage());
                }
                nRecvlen = nRecvlenbb[0];

                if (!recved) {
                    throw new LfException(Errs.SERIAL_TIME_OUT);
                }
                // 检查serial ID
                if (0 != LfUtils.memcmp(ptPacketPara.id, ptOutPara.id, 6)) {
                    if (this.IReadAvailable() > 0) {// 可能当前接收的报文是以前的报文
                        continue;
                    }
                    dbg_TprintfWHex(TAG, ptPacketPara.id, 6, "ptPacketPara.id");
                    dbg_TprintfWHex(TAG, ptOutPara.id, 6, "ptOutPara.id");
                    throw new LfException(Errs.PROTOCOL_ID_ERR);
                }
                break;
            }
            // 长度判断
            if (ptOutPara.datalen < 1) {
                throw new LfException(Errs.PROTOCOL_LEN_ERR);
            }
            if (MISPOS.PACK_ACK == ptOutPara.data[0] || MISPOS.PACK_NAK == ptOutPara.data[0]) {// 回复ACK 、NAK
                mREPLY.Unpack(ptOutPara);
            } else {
                dbg_TprintfWHex(TAG, ptOutPara.data, ptOutPara.datalen, "data");
                throw new LfException(Errs.POS_REPLY_INVALID);
            }
            return mREPLY;
        }
    }

    private REPLY _03_base(_03_Common t) throws LfException {
        Logz.i(TAG,"raoj------_03_base");
        _IdleTaskInterrupt();
        if ((t.getTradeType() != TradeType.TMS_ONLINE_REPORT)
                && (t.getTradeType() != TradeType.TMS_REMOTE_DOWNLOAD)
                && (t.getTradeType() != TradeType.TMS_TMN_INFO_SEND)
                && (t.getTradeType() != TradeType.TMS_DOWNLOAD_CONFIRM_NOTICE)
                && (t.getTradeType() != TradeType.TMS_DOWNLOAD_KEY)
                && (t.getTradeType() != TradeType.REBOOT)) {
            // _01_Test();
        }
        synchronized (lockApi) {
            this.setUserTradeType(t.getTradeType());
            this.updateOpTime();
            byte[] tmpbb = null;
            byte[] pcSendBuf = new byte[1024];
            int send_len = 0;
            byte[] pcRecvData = new byte[1024];
            int nRecvlen = 0, i = 0;
            int[] nRecvlenbb = {0};
            UART_PROTOCOL ptPacketPara = new UART_PROTOCOL();
            UART_PROTOCOL ptOutPara = new UART_PROTOCOL();
            REPLY mREPLY = null;
            if (t.getTradeType() == TradeType.SIGNIN) {
                Logz.i(TAG,"raoj------_03_base---sign");
                mREPLY = new _04_PurchaseReply();
            } else if (t.getTradeType() == TradeType.PURCHASE) {
                Logz.i(TAG,"raoj------_03_base---purchase");
                mREPLY = new _04_PurchaseReply();
            } else if (t.getTradeType() == TradeType.QUERY) {
                mREPLY = new _04_QueryReply();
            } else if (t.getTradeType() == TradeType.RECHARGE) {
                mREPLY = new _04_PurchaseReply();
            } else if (t.getTradeType() == TradeType.RECHARGE_SHENSI_FANKA) {
                mREPLY = new _04_PurchaseReply();
            } else if (t.getTradeType() == TradeType.QUERY_SHENSI_FANKA) {
                mREPLY = new _04_QueryReply();
            } else if (t.getTradeType() == TradeType.REFUNDPURCHASE) {
                mREPLY = new _04_GetPrintInfoReply();
            } else if (t.getTradeType() == TradeType.REFUND) {
                mREPLY = new _04_GetPrintInfoReply();
            } else if (t.getTradeType() == TradeType.GET_PRINT_INFO) {
                mREPLY = new _04_GetPrintInfoReply();
            } else if (t.getTradeType() == TradeType.GET_RECORD) {
                mREPLY = new _04_GetRecordReply();
            } else if (t.getTradeType() == TradeType.GET_SETTLE_INFO) {
                mREPLY = new _04_GetSettleInfoReply();
            } else if (t.getTradeType() == TradeType.PASSBOOKRENEW) {
                mREPLY = new _04_PassbookRenewReply();
            } else if (t.getTradeType() == TradeType.DOWNLOADPAYCODE) {
                mREPLY = new _04_DownloadAppcodeReply();
            } else if (t.getTradeType() == TradeType.GETZNRECORDINFO) {
                mREPLY = new _04_GetZNRecordInfoReply();
            } else if (t.getTradeType() == TradeType.GET_CARDNO) {
                mREPLY = new _04_ReadCardNoReply();
            } else if (t.getTradeType() == TradeType.GET_ACCOUNTNAME) {
                mREPLY = new _04_ReadAccountNameReply();
            } else if (t.getTradeType() == TradeType.PAYFEES_QUERY) {
                mREPLY = new _04_PayfeesQueryReply();
            } else if (t.getTradeType() == TradeType.BOUNDACCOUNT_QUERY) {
                mREPLY = new _04_BoundAccountQueryReply();
            } else if (t.getTradeType() == TradeType.GET_MERTMN) {
                mREPLY = new _04_ReadMerTmnReply();
            } else if (t.getTradeType() == TradeType.GET_KEYBOARD) {
                mREPLY = new _04_GetKeyboardReply();
            } else if (t.getTradeType() == TradeType.TEST_ZNRECORDINFO) {
                mREPLY = new _04_TestGetZNRecordInfoReply();
            } else if (t.getTradeType() == TradeType.CHECK_BALANCE) {
                mREPLY = new _04_CheckBalanceReply();
            } else if (t.getTradeType() == TradeType.TRANS_QUERY) {
                mREPLY = new _04_TransQueryReply();
            } else if (t.getTradeType() == TradeType.GET_CARDREADER) {
                mREPLY = new _04_GetCardReaderReply();
            } else if (t.getTradeType() == TradeType.SAND_O2O_QUERY_RESULT) {
                mREPLY = new _04_SandO2OResultReply();
            } else if (t.getTradeType() == TradeType.SAND_O2O_SIGNIN) {
                mREPLY = new _04_SandO2OSigninReply();
                /////////////////////////////////////////////////////////////////////////
            } else if (t.getTradeType() == TradeType.TMS_ONLINE_REPORT) {
                mREPLY = new _04_PurchaseReply();
            } else if (t.getTradeType() == TradeType.TMS_REMOTE_DOWNLOAD) {
                mREPLY = new _04_PurchaseReply();
            } else if (t.getTradeType() == TradeType.TMS_TMN_INFO_SEND) {
                mREPLY = new _04_PurchaseReply();
            } else if (t.getTradeType() == TradeType.TMS_DOWNLOAD_CONFIRM_NOTICE) {
                mREPLY = new _04_PurchaseReply();
            } else if (t.getTradeType() == TradeType.TMS_DOWNLOAD_KEY) {
                mREPLY = new _04_PurchaseReply();
            } else if (t.getTradeType() == TradeType.REBOOT) {
                mREPLY = new _04_PurchaseReply();
            } else {
                mREPLY = new REPLY();
            }
            if (isBusy) {
                mREPLY.reply = MISPOS.PACK_NAK;
                mREPLY.code = ErrCode._FF.getCode();
                mREPLY.code_info = ErrCode._FF.getDesc();
                this.setUserTradeType(TradeType.NONE);
                return mREPLY;
            }
            try {
                isBusy = true;
                mer = t.getMer();
                tmn = t.getTmn();
//                operId = t.g
                Logz.i(TAG, "mer:" + mer + ", tmn:" + tmn);
                // 发送设置
                ptPacketPara.path = MISPOS.PROTOCOL_PATH_ASK_SEND;
                ptPacketPara.type = MISPOS.SERVICE_MISPOS_TYPE;
                ptPacketPara.type = recvType == (byte) 0xFF ? ptPacketPara.type : recvType;
                // Serial ID
                LfUtils.memcpy(ptPacketPara.id, this.serial_ID, 6);

                // DATA数据打包
                tmpbb = LfPosApiMispos.Trade2Buffer(t);
                if (tmpbb != null) {
                    Logz.i(TAG,"raoj------_03_base---");
                    LfUtils.memcpy(ptPacketPara.data, tmpbb, tmpbb.length);
                    ptPacketPara.datalen = (short) tmpbb.length;
                    dbg_TprintfWHex(TAG, tmpbb, tmpbb.length, "---tmpbb---:");
                }
//                dbg_TprintfWHex(TAG, ptPacketPara.data, ptPacketPara.data.length, "---ptPacketPara.data---:");
                send_len = mMISPOS.Mispos_Protocol_Pack(ptPacketPara, pcSendBuf);
                // 发送报文
                Logz.i(TAG, ">>>>>>>>send(" + send_len + ")...");
                this.send(pcSendBuf, send_len, 2000);
                Logz.i(TAG, ">>>>>>>>send size(" + send_len + ") done.");

                // 接收报文，当指令类型为取消或确认时则不接收
                if (t.getTradeType() != TradeType.CANCEL && t.getTradeType() != TradeType.CONFIRM) {
                    long tmo = t.getTimeoutMs();
                    boolean recved = false;
                    Logz.i(TAG, ">>>>>>>_03_base >>>>>" + t.getTradeType().toString() + " -> _RecvHandler(" + t.getTimeoutMs() + ").....");
                    long tmp_t = System.currentTimeMillis();
                    if (t.getTimeoutMs() == 0) {
                        recved = _RecvHandler(MISPOS.PROTOCOL_PATH_ASK_SEND, t.getTradeType(), pcRecvData, nRecvlenbb, ptOutPara, t.getTimeoutMs());
                    } else {
                        recved = _RecvHandler(MISPOS.PROTOCOL_PATH_ASK_SEND, t.getTradeType(), pcRecvData, nRecvlenbb, ptOutPara, tmo);
                    }
                    tmo -= ((System.currentTimeMillis() - tmp_t));
                    nRecvlen = nRecvlenbb[0];
                    Logz.i(TAG, ">>>>>>>_03_base >>>>>" + t.getTradeType().toString() + " -> _RecvHandler(" + t.getTimeoutMs() + ") done.(ms:" + ((System.currentTimeMillis() - tmp_t)) + ")");

                    this.updateOpTime();
                    if (!recved) {
                        throw new LfException(Errs.SERIAL_TIME_OUT);
                    }
                    // 检查serial ID
                    if (0 != LfUtils.memcmp(ptPacketPara.id, ptOutPara.id, 6)) {
                        throw new LfException(Errs.PROTOCOL_ID_ERR);
                    }
                    int cnt = 0;
                    // 长度判断
                    if (ptOutPara.datalen < 1) {
                        throw new LfException(Errs.PROTOCOL_LEN_ERR);
                    }
                    // 解析接收到的返回值
                    if (recved && mREPLY != null) {
                        if (t.getTradeType() == TradeType.SIGNIN) {
                            ((_04_PurchaseReply) mREPLY).Unpack(ptOutPara);
                        } else if (t.getTradeType() == TradeType.PURCHASE) {
                            ((_04_PurchaseReply) mREPLY).Unpack(ptOutPara);
                        } else if (t.getTradeType() == TradeType.QUERY) {
                            ((_04_QueryReply) mREPLY).Unpack(ptOutPara);
                        } else if (t.getTradeType() == TradeType.RECHARGE) {
                            ((_04_PurchaseReply) mREPLY).Unpack(ptOutPara);
                        } else if (t.getTradeType() == TradeType.REFUNDPURCHASE) {
                            ((_04_GetPrintInfoReply) mREPLY).Unpack(ptOutPara);
                        } else if (t.getTradeType() == TradeType.REFUND) {
                            ((_04_GetPrintInfoReply) mREPLY).Unpack(ptOutPara);
                        } else if (t.getTradeType() == TradeType.GET_PRINT_INFO) {
                            ((_04_GetPrintInfoReply) mREPLY).Unpack(ptOutPara);
                        } else if (t.getTradeType() == TradeType.GET_RECORD) {
                            ((_04_GetRecordReply) mREPLY).Unpack(ptOutPara);
                        } else if (t.getTradeType() == TradeType.GET_SETTLE_INFO) {
                            ((_04_GetSettleInfoReply) mREPLY).Unpack(ptOutPara);
                        } else if (t.getTradeType() == TradeType.PASSBOOKRENEW) {
                            ((_04_PassbookRenewReply) mREPLY).Unpack(ptOutPara);
                        } else if (t.getTradeType() == TradeType.GETZNRECORDINFO) {
                            ((_04_GetZNRecordInfoReply) mREPLY).Unpack(ptOutPara);
                        } else if (t.getTradeType() == TradeType.GET_CARDNO) {
                            ((_04_ReadCardNoReply) mREPLY).Unpack(ptOutPara);
                        } else if (t.getTradeType() == TradeType.GET_ACCOUNTNAME) {
                            ((_04_ReadAccountNameReply) mREPLY).Unpack(ptOutPara);
                        } else if (t.getTradeType() == TradeType.PAYFEES_QUERY) {
                            ((_04_PayfeesQueryReply) mREPLY).Unpack(ptOutPara);
                        } else if (t.getTradeType() == TradeType.BOUNDACCOUNT_QUERY) {
                            ((_04_BoundAccountQueryReply) mREPLY).Unpack(ptOutPara);
                        } else if (t.getTradeType() == TradeType.GET_MERTMN) {
                            ((_04_ReadMerTmnReply) mREPLY).Unpack(ptOutPara);
                        } else if (t.getTradeType() == TradeType.TEST_ZNRECORDINFO) {
                            ((_04_TestGetZNRecordInfoReply) mREPLY).Unpack(ptOutPara);
                        } else if (t.getTradeType() == TradeType.CHECK_BALANCE) {
                            ((_04_CheckBalanceReply) mREPLY).Unpack(ptOutPara);
                        } else if (t.getTradeType() == TradeType.TRANS_QUERY) {
                            ((_04_TransQueryReply) mREPLY).Unpack(ptOutPara);
                        } else if (t.getTradeType() == TradeType.SAND_O2O_QUERY_RESULT) {
                            ((_04_SandO2OResultReply) mREPLY).Unpack(ptOutPara);
                        } else if (t.getTradeType() == TradeType.SAND_O2O_SIGNIN) {
                            ((_04_SandO2OSigninReply) mREPLY).Unpack(ptOutPara);
                        } else if (t.getTradeType() == TradeType.DOWNLOADPAYCODE) {
                            ((_04_DownloadAppcodeReply) mREPLY).Unpack(ptOutPara);
                            Logz.d(TAG, ">>>>cnt:" + (++cnt) + ", infolen:" + ((_04_DownloadAppcodeReply) mREPLY).getReturnData().length());
                            while ((t.getTimeoutMs() == 0 || (t.getTimeoutMs() != 0 && tmo > 0)) && ((_04_DownloadAppcodeReply) mREPLY).getFollowUpPkg().equals("1")) {// 当有后续包时，继续等待读取数据
                                recved = false;
                                Logz.d(TAG, ">>>>>continue...");
                                ptOutPara.reset();
                                if (t.getTimeoutMs() == 0) {
                                    recved = _RecvHandler(MISPOS.PROTOCOL_PATH_ASK_SEND, t.getTradeType(), pcRecvData, nRecvlenbb, ptOutPara, t.getTimeoutMs());
                                } else {
                                    tmp_t = System.currentTimeMillis();
                                    recved = _RecvHandler(MISPOS.PROTOCOL_PATH_ASK_SEND, t.getTradeType(), pcRecvData, nRecvlenbb, ptOutPara, tmo);
                                    tmo -= ((System.currentTimeMillis() - tmp_t));
                                }
                                this.updateOpTime();
                                if (recved) {
                                    ((_04_DownloadAppcodeReply) mREPLY).Unpack(ptOutPara);
                                    Logz.d(TAG, ">>>>cnt:" + (++cnt) + ", infolen:" + ((_04_DownloadAppcodeReply) mREPLY).getReturnData().length());
                                }
                            }
                            Logz.i(TAG, "_04_DownloadAppcodeReply(" + ((_04_DownloadAppcodeReply) mREPLY).getFollowUpPkg() + "):" + ((_04_DownloadAppcodeReply) mREPLY).getReturnData());
                        } else {
                            mREPLY.Unpack(ptOutPara);
                        }
                    } else {
                        dbg_TprintfWHex(TAG, ptOutPara.data, ptOutPara.datalen, "data");
                        throw new LfException(Errs.POS_REPLY_INVALID);
                    }
                } else {
                    mREPLY.reply = MISPOS.PACK_ACK;
                    mREPLY.code = "";
                    mREPLY.code_info = "";
                }
            } catch (LfException e) {
                throw e;
            } finally {
                isBusy = false;
                this.setUserTradeType(TradeType.NONE);
            }
            if (mREPLY.code != null && mREPLY.code.equals("69")) {
                mREPLY.code = "00";
            }
            return mREPLY;
        }
    }

    /**
     * 取消、确认指令调用
     **/

    private REPLY _07_base(_03_Common t, TradeType tt, long timeout) throws LfException {
        if (tt != TradeType.CANCEL && tt != TradeType.CONFIRM) {
            this.setUserTradeType(tt);
            this.updateOpTime();
        }
        byte[] tmpbb = null;
        byte[] pcSendBuf = new byte[1024];
        int send_len = 0;
        byte[] pcRecvData = new byte[1024];
        int nRecvlen = 0, i = 0;
        int[] nRecvlenbb = {0};
        UART_PROTOCOL ptPacketPara = new UART_PROTOCOL();
        UART_PROTOCOL ptOutPara = new UART_PROTOCOL();
        REPLY mREPLY = new REPLY();

        // 发送设置
        ptPacketPara.path = MISPOS.PROTOCOL_PATH_ASK_SEND;
        ptPacketPara.type = mMISPOS.getType();
        ptPacketPara.type = recvType == (byte) 0xFF ? ptPacketPara.type : recvType;
        LfUtils.memcpy(ptPacketPara.id, this.serial_ID, 6);

        // DATA数据
        tmpbb = LfPosApiMispos.Trade2Buffer(t);
        if (tmpbb != null) {
            LfUtils.memcpy(ptPacketPara.data, tmpbb, tmpbb.length);
            ptPacketPara.datalen = (short) tmpbb.length;
        } else {
            ptPacketPara.datalen = 0;
        }

        send_len = mMISPOS.Mispos_Protocol_Pack(ptPacketPara, pcSendBuf);
        // 发送报文
        this.send(pcSendBuf, send_len, 2000);

        // ------------------------------------------------------------------------
        if (tt != TradeType.CANCEL && tt != TradeType.CONFIRM) {// 不是取消/确认操作才进行接收回复处理
            Logz.i(TAG, ">>>>>>>" + tt.toString() + " >>> _RecvHandler....");
            // 接收报文
            boolean recved = false;
            recved = _RecvHandler(MISPOS.PROTOCOL_PATH_ASK_SEND, tt,
                    pcRecvData, nRecvlenbb, ptOutPara, timeout);
            nRecvlen = nRecvlenbb[0];
            Logz.i(TAG, ">>>>>>>" + tt.toString() + " >>> _RecvHandler  done.");
            this.updateOpTime();
            if (!recved) {
                throw new LfException(Errs.SERIAL_TIME_OUT);
            }
            // 检查serial ID
            if (0 != LfUtils.memcmp(ptPacketPara.id, ptOutPara.id, 6)) {
                throw new LfException(Errs.PROTOCOL_ID_ERR);
            }
            // 长度判断
            if (ptOutPara.datalen < 1) {
                throw new LfException(Errs.PROTOCOL_LEN_ERR);
            }
            if (recved) {
                mREPLY.Unpack(ptOutPara);
            }
        } else {
            mREPLY.reply = MISPOS.PACK_ACK;
            mREPLY.code = "";
            mREPLY.code_info = "";
        }
        return mREPLY;
    }

    /**
     * 07，取消操作指令(刷卡前调用可中止读卡)
     **/
    public REPLY _07_Cancel(_03_Common t) throws LfException {
        REPLY reply = null;
//        if (isPospExchanging) {
//            displayMsg("网络交互中，无法取消");
//            reply = new REPLY();
//            reply.reply = MISPOS.PACK_NAK;
//            reply.code = ErrCode._FF.getCode();
//            return reply;
//        }
        try {
            reply = this._07_base(t, TradeType.CANCEL, 20 * 1000);
        } catch (LfException e) {
            throw e;
        } finally {
            isSendCancel = true;
            Logz.i(TAG, "isSendCancel:" + isSendCancel);
        }
        return reply;
    }

    /**
     * 09，确认操作指令(带键盘的设备才会调用到)
     **/
    public REPLY _07_Confirm(_03_Common t) throws LfException {
        REPLY reply = null;
        try {
            reply = this._07_base(t, TradeType.CONFIRM, 20 * 1000);
        } catch (LfException e) {
            throw e;
        }
        return reply;
    }

    //////////////////////////////////////////////基础指令///////////////////////////////////////////

    /**
     * 01，消费指令
     **/
    public _04_PurchaseReply _03_Trade(_03_Common t) throws LfException {
        return (_04_PurchaseReply) _03_base(t);
    }


    /**
     * 02，消费撤销指令，退款
     **/
    public _04_GetPrintInfoReply _03_RefundPurchase(_03_Common t) throws LfException {
        return (_04_GetPrintInfoReply) _03_base(t);
    }

    /**
     * 04，查余额指令
     **/
    public _04_QueryReply _03_Query(_03_Common t) throws LfException {
        return (_04_QueryReply) _03_base(t);
    }

    /**
     * 51，签到指令
     **/
    public _04_PurchaseReply _03_Signin(_03_Common t) throws LfException {
        Logz.i(TAG,"raoj------签到7---_03_Signin");
        return (_04_PurchaseReply) _03_base(t);
    }

    /**
     * 52，结算指令
     **/
    public REPLY _03_Settle(_03_Common t) throws LfException {
        return _03_base(t);
    }

    /**
     * 56，脱机交易强制上送
     **/
    public REPLY _03_UploadOffline(_03_Common t) throws LfException {
        return _03_base(t);
    }

    /**
     * 61，取打印信息
     **/
    public _04_GetPrintInfoReply _03_GetPrintInfo(_03_Common t) throws LfException {
        return (_04_GetPrintInfoReply) _03_base(t);
    }

    /**
     * 62获取结算信息
     **/
    public _04_GetSettleInfoReply _03_GetSettleInfo(_03_Common t) throws LfException {
        return (_04_GetSettleInfoReply) _03_base(t);
    }

    /**
     * 69，取交易信息
     **/
    public _04_GetRecordReply _03_GetRecordReply(_03_Common t) throws LfException {
        return (_04_GetRecordReply) _03_base(t);
    }


    //////////////////////////////////////////江西农信///////////////////////////////////////////////

    /**
     * 37,助农取款
     **/
    public REPLY _03_ZN_Withdraw(_03_Withdraw t) throws LfException {
        return _03_base(t);
    }

    /**
     * 38，现金汇款
     **/
    public REPLY _03_ZN_Cashrem(_03_Cashrem t) throws LfException {
        return _03_base(t);
    }

    /**
     * 39，转账汇款
     **/
    public REPLY _03_ZN_Transferrem(_03_Transferrem t) throws LfException {
        return _03_base(t);
    }

    /**
     * 43,存折补登
     **/
    public _04_PassbookRenewReply _03_ZN_PassbookRenew(_03_PassbookRenew t)
            throws LfException {
        return ((_04_PassbookRenewReply) _03_base(t));
    }

    /**
     * 44,缴费应用代码下载
     **/
    public _04_DownloadAppcodeReply _03_ZN_DownloadAppcode(_03_DownloadAppcode t)
            throws LfException {
        return ((_04_DownloadAppcodeReply) _03_base(t));
    }

    /**
     * 45,取助农类交易信息
     **/
    public _04_GetZNRecordInfoReply _03_ZN_GetZNRecordInfo(_03_GetZNRecordInfo t)
            throws LfException {
        return ((_04_GetZNRecordInfoReply) _03_base(t));
    }

    /**
     * 46,获取按键透传值
     **/
    public REPLY _03_ZN_GetKeyRequest(_03_GetKeyRequest t) throws LfException {
        return _03_base(t);
    }

    /**
     * 48,获取卡号
     **/
    public _04_ReadCardNoReply _03_ZN_ReadCardNo(_03_ReadCardNo t)
            throws LfException {
        return ((_04_ReadCardNoReply) _03_base(t));
    }

    /**
     * 49,获取户名
     **/
    public _04_ReadAccountNameReply _03_ZN_ReadAccountName(_03_ReadAccountName t)
            throws LfException {
        return ((_04_ReadAccountNameReply) _03_base(t));
    }

    /**
     * 93缴费查询
     **/
    public _04_PayfeesQueryReply _03_ZN_PayfeesQuery(_03_PayfeesQuery t)
            throws LfException {
        return ((_04_PayfeesQueryReply) _03_base(t));
    }

    /**
     * 94,缴费
     **/
    public REPLY _03_ZN_Payfees(_03_Payfees t) throws LfException {
        return _03_base(t);
    }

    /**
     * 95，绑定账户信息查询
     **/
    public _04_BoundAccountQueryReply _03_ZN_BoundAccountQuery(
            _03_BoundAccountQuery t) throws LfException {
        return ((_04_BoundAccountQueryReply) _03_base(t));
    }

    /**
     * 96,存折转卡
     **/
    public REPLY _03_ZN_PassbookToCard(_03_PassbookToCard t) throws LfException {
        return (_03_base(t));
    }

    /**
     * B1，获取商户终端信息
     **/
    public _04_ReadMerTmnReply _04_GetMerTmn(_03_Common t) throws LfException {
        return ((_04_ReadMerTmnReply) _03_base(t));
    }

    /**
     * B2，获取键盘信息
     **/
    public _04_GetKeyboardReply _03_ZN_GetKeyboard(_03_GetKeyboard t)
            throws LfException {
        return ((_04_GetKeyboardReply) _03_base(t));
    }

    /**
     * B3,测试MK210
     **/
    public REPLY _03_ZN_TestMK210(_03_TestMK210 t) throws LfException {
        return _03_base(t);
    }

    /**
     * B4，单选测试助农取款
     **/
    public REPLY _03_ZN_TestWithdraw(_03_TestZNWithdraw t) throws LfException {
        return _03_base(t);
    }

    /**
     * B5,测试助农类交易信息
     **/
    public _04_TestGetZNRecordInfoReply _03_ZN_TestGetZNRecordInfo(
            _03_TestGetZNRecordInfo t) throws LfException {
        return ((_04_TestGetZNRecordInfoReply) _03_base(t));
    }

    /**
     * B6,测试助农转账汇款
     **/
    public REPLY _03_ZN_TestTransferrem(_03_TestZNTransferrem t) throws LfException {
        return _03_base(t);
    }

    /**
     * B7，余额查询
     **/
    public _04_CheckBalanceReply _03_ZN_CheckBalance(_03_CheckBalance t)
            throws LfException {
        return ((_04_CheckBalanceReply) _03_base(t));
    }

    /**
     * B8,交易查询
     **/
    public _04_TransQueryReply _03_GetTransQuery(_03_TransQuery t) throws LfException {
        return ((_04_TransQueryReply) _03_base(t));
    }

    /**
     * B9,终端下发TMK
     **/
    public REPLY _03_Test_SendTMK(_03_SendTMK t) throws LfException {
        return _03_base(t);
    }

    /**
     * BA,设置商户终端参数
     **/
    public REPLY _03_ZN_SetPara(_03_SetMerTmn t) throws LfException {
        return _03_base(t);
    }

    /**
     * C1,获取读卡器透传
     **/
    public _04_GetCardReaderReply _03_Get_CardReader(_03_GetCardReader t)
            throws LfException {
        return ((_04_GetCardReaderReply) _03_base(t));
    }

    //////////////////////////////////////////////杭州仁盈///////////////////////////////////////////

    /**
     * 21,预授权
     **/
    public REPLY _03_Preauthor(_03_Common t) throws LfException {
        return _03_base(t);
    }


    /**
     * 23,预授权完成
     **/
    public REPLY _03_PreauthorDone(_03_Common t) throws LfException {
        return _03_base(t);
    }

    /**
     * 25,预授权撤销
     **/
    public REPLY _03_PreauthorCancel(_03_Common t) throws LfException {
        return _03_base(t);
    }

    /**
     * 26,预授权完成撤销
     **/
    public REPLY _03_PreauthorDoneCancel(_03_Common t) throws LfException {
        return _03_base(t);
    }

    /////////////////////////////////////////////厦门停车场//////////////////////////////////////////

    /**
     * 10,POS通C扫B消费
     **/
    public REPLY _03_PosCSBTrade(_03_PosCSBTrade t) throws LfException {
        return _03_base(t);
    }

    /***
     * 11,POS通C扫B查询
     **/
    public REPLY _03_PosCSBQuery(_03_PosCSBQuery t) throws LfException {
        return _03_base(t);
    }

    /**
     * 80,厦门停车场二合一消费指令（暂不使用）
     **/
    public REPLY _03_TwoInOne(_03_TwoInOne t) throws LfException {
        return _03_base(t);
    }

    /**
     * 81,厦门停车场三合一消费指令（暂不使用）
     **/
    public REPLY _03_ThreeInOne(_03_ThreeInOne t) throws LfException {
        return _03_base(t);
    }

    /**
     * 12,厦门定制B扫C撤销
     **/
    public REPLY _03_BSCCancel(_03_BSCCancel t) throws LfException {
        return _03_base(t);
    }

    ///////////////////////////////////////////////杉德/////////////////////////////////////////////

    /**
     * S1,杉德小额双免支付
     **/
    public REPLY _03_SandSmall(_03_SandSmall t) throws LfException {
        return _03_base(t);
    }

    /**
     * S2,杉德二维码B扫C支付
     **/
    public REPLY _03_SandBscPay(_03_SandBscPay t) throws LfException {
        return _03_base(t);
    }

    /**
     * S3,杉德二维码B扫C支付撤销
     **/
    public REPLY _03_SandBscRefund(_03_SandBscRefund t) throws LfException {
        return _03_base(t);
    }

    /**
     * S4,杉德二维码B扫C支付退货
     **/
    public REPLY _03_SandBscReturn(_03_SandBscReturn t) throws LfException {
        return _03_base(t);
    }

    /**
     * S5杉德会员卡支付
     **/
    public REPLY _03_SandCardTrade(_03_SandCardTrade t) throws LfException {
        return _03_base(t);
    }

    /**
     * S6,杉德秘钥置换
     **/
    public REPLY _03_SandKeyReplace(_03_SandKeyReplace t) throws LfException {
        return _03_base(t);
    }

    /**
     * S7,杉德结算
     **/
    public REPLY _03_SandSettle(_03_SandSettle t) throws LfException {
        return _03_base(t);
    }

    /**
     * S8,杉德O2O平台结算
     **/
    public REPLY _03_SandO2OSettle(_03_SandO2OSettle t) throws LfException {
        return _03_base(t);
    }

    /**
     * S9,杉德O2O平台二维码下单
     **/
    public REPLY _03_SandO2OQrCodeTrade(_03_SandO2OQrCodeTrade t) throws LfException {
        return _03_base(t);
    }

    /**
     * SA,杉德O2O平台二维码下单查询
     **/
    public REPLY _03_SandO2OQrCodeQuery(_03_SandO2OQrCodeQuery t) throws LfException {
        return _03_base(t);
    }

    /**
     * SB,杉德平台反复下单并支付
     **/
    public REPLY _03_SandO2OLoop(_03_SandO2OLoop t) throws LfException {
        return _03_base(t);
    }

    /**
     * SC,杉德平台查询结果
     **/
    public REPLY _03_SandO2OResult(_03_SandO2OResult t) throws LfException {
        return _03_base(t);
    }

    /**
     * SD,杉德平台撤销
     **/
    public REPLY _03_SandO2OCandel(_03_SandO2OCancel t) throws LfException {
        return _03_base(t);
    }

    /**
     * SE,杉德平台O2O退款
     **/
    public REPLY _03_SandO2ORefund(_03_SandO2ORefund t) throws LfException {
        return _03_base(t);
    }

    /**
     * SF,杉德O2O平台签到
     **/
    public _04_SandO2OSigninReply _03_SandO2OSignin(_03_SandO2OSignin t) throws LfException {
        return (_04_SandO2OSigninReply) _03_base(t);
    }

    /**
     * 杉德母pos分发秘钥
     **/
    public REPLY _03_SandPosDeliveryKey(_03_SandPosDeliveryKey t) throws LfException {
        return _03_base(t);
    }

    /**
     * 杉德会员卡同步报文
     **/
    public REPLY _03_SandSyncMessage(_03_SandSyncMessage t) throws LfException {
        return _03_base(t);
    }
    //////////////////////////////////////////////TMS///////////////////////////////////////////////

    /**
     * B1,联机报道
     **/
    public _04_PurchaseReply _03_TmsOnlineReport(_03_Common t) throws LfException {
        return (_04_PurchaseReply) _03_base(t);
    }

    /**
     * B7,远程下载
     **/
    public _04_PurchaseReply _03_TmsRemoteDownload(_03_Common t) throws LfException {
        return (_04_PurchaseReply) _03_base(t);
    }

    /**
     * B8,终端信息上送
     **/
    public _04_PurchaseReply _03_TmsTmnInfoSend(_03_Common t) throws LfException {
        return (_04_PurchaseReply) _03_base(t);
    }

    /**
     * BB,下载确认通知
     **/
    public _04_PurchaseReply _03_TmsDownloadConfirmNotice(_03_Common t) throws LfException {
        return (_04_PurchaseReply) _03_base(t);
    }

    /**
     * BD,一键下秘钥
     **/
    public _04_PurchaseReply _03_TmsDownloadKey(_03_Common t) throws LfException {
        return (_04_PurchaseReply) _03_base(t);
    }

    //////////////////////////////////////////重启POS///////////////////////////////////////////////
    public _04_PurchaseReply _03_Reboot(_03_Common t) throws LfException {
        return (_04_PurchaseReply) _03_base(t);
    }
    /////////////////////////////////////////////骏鹏///////////////////////////////////////////////

    /**
     * M2,会员卡退款指令
     **/
    public REPLY _03_Refund(_03_Common t) throws LfException {
        return _03_base(t);
    }

    /**
     * M4,会员卡充值指令
     **/
    public _04_PurchaseReply _03_Recharge(_03_Common t) throws LfException {
        return (_04_PurchaseReply) _03_base(t);
    }

    /**
     * SQ,获取pos状态
     **/
    public REPLY _03_StateQuery(_03_Common t) throws LfException {
        return _03_base(t);
    }

    /**
     * PI,pos进行PING测试
     **/
    public REPLY _03_Ping(_03_Common t) throws LfException {
        return _03_base(t);
    }

    ///////////////////////////////////////////////其他指令//////////////////////////////////////////

}
