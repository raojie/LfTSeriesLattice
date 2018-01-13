package com.landfoneapi.protocol.pkg.preauthor;

import com.landfoneapi.protocol.pkg.TradeType;
import com.landfoneapi.protocol.pkg._03_Common;
import com.landfone.common.utils.LfException;
import com.landfone.common.utils.LfUtils;

import java.io.UnsupportedEncodingException;

/**
 * Created by ASUS on 2016/5/16.
 */
public class _03_PreAuthorization extends _03_Common {
    private String amount = "";
    private String track2 = "";
    private String track3 = "";
    private String clientNo = "";
    private String operator = "";
    private String serialNo = "";

    public _03_PreAuthorization() {
        this.setTradeType(TradeType.PREAUTHORIZATION);
    }

    public void reset() {
        super.reset();
        this.setTradeType(TradeType.PREAUTHORIZATION);
        try {
            this.setAmount("");
            this.setTrack2("");
            this.setTrack3("");
            this.setClientNo("");
            this.setOperator("");
            this.setSerialNo("");
        } catch (LfException e) {
            e.printStackTrace();
        }
    }

    public byte[] getBytes() throws UnsupportedEncodingException {
        byte[] tmpbb_1 = new byte[24];
        byte[] commonbb = null;
        byte[] retbb = null;
        int retlen = 0, i = 0;

        commonbb = super.getBytes();

        //retlen +=1;//分隔符
        //LfUtils.setbcd(tmpbb_1, "%s", this.amount);
        String.format("%120s", this.amount);
        tmpbb_1 = this.amount.getBytes();
        retlen += 12 + 1;

        retlen += track2.getBytes().length > 0 ? (1 + track2.getBytes().length) : 1;
        retlen += track3.getBytes().length > 0 ? (1 + track3.getBytes().length) : 1;
        retlen += clientNo.getBytes().length > 0 ? (1 + clientNo.getBytes().length) : 1;
        retlen += operator.getBytes().length > 0 ? (1 + operator.getBytes().length) : 1;
        retlen += serialNo.getBytes().length > 0 ? (1 + serialNo.getBytes().length) : 1;
        //retlen +=1;//结束的FS
        if (commonbb != null && retlen > 0 && retlen <= (1 + 12 + 1 + 37 + 1 + 104 + 1 + 15 + 1 + 15 + 1 + 20)) {
            retbb = new byte[commonbb.length + retlen];
            if (retbb != null) {
                //common数据
                LfUtils.memcpy(retbb, i, commonbb, 0, commonbb.length);
                i += commonbb.length;
                //LfUtils.memcpy(retbb, i,FS,0, FS.length);
                //i+=FS.length;
                dbg_mPrintf("commonbb:%d, i:%d, retbb:%d, tmpbb_1:%d", commonbb.length, i, retbb.length, tmpbb_1.length);
                //当前交易的数据
                LfUtils.memcpy(retbb, i, tmpbb_1, 0, 12);
                i += 12;
                LfUtils.memcpy(retbb, i, FS, 0, FS.length);
                i += FS.length;

                if (track2.getBytes().length > 0) {
                    LfUtils.memcpy(retbb, i, track2.getBytes(), 0, track2.getBytes().length);
                    i += track2.getBytes().length;
                }
                LfUtils.memcpy(retbb, i, FS, 0, FS.length);
                i += FS.length;

                if (track3.getBytes().length > 0) {
                    LfUtils.memcpy(retbb, i, track3.getBytes(), 0, track3.getBytes().length);
                    i += track3.getBytes().length;
                }
                LfUtils.memcpy(retbb, i, FS, 0, FS.length);
                i += FS.length;
                if (clientNo.getBytes().length > 0) {
                    LfUtils.memcpy(retbb, i, clientNo.getBytes(), 0, clientNo.getBytes().length);
                    i += clientNo.getBytes().length;
                }
                LfUtils.memcpy(retbb, i, FS, 0, FS.length);
                i += FS.length;
                if (operator.getBytes().length > 0) {
                    LfUtils.memcpy(retbb, i, operator.getBytes(), 0, operator.getBytes().length);
                    i += operator.getBytes().length;
                }
                LfUtils.memcpy(retbb, i, FS, 0, FS.length);
                i += FS.length;
                if (serialNo.getBytes().length > 0) {
                    LfUtils.memcpy(retbb, i, serialNo.getBytes(), 0, serialNo.getBytes().length);
                    i += serialNo.getBytes().length;
                }
                LfUtils.memcpy(retbb, i, FS, 0, FS.length);
                i += FS.length;
            }
        }
        return retbb;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) throws LfException {
        if (amount.getBytes().length != 12) {
            throw new LfException(-8, "amount length error,need 12");
        }
        this.amount = amount;
    }

    public String getTrack2() {
        return track2;
    }

    public void setTrack2(String track2) throws LfException {
        if (track2.getBytes().length > 40) {
            throw new LfException(-8, "track2 length error, need <=40");
        }
        this.track2 = track2;
    }

    public String getTrack3() {
        return track3;
    }

    public void setTrack3(String track3) throws LfException {
        if (track3.getBytes().length > 110) {
            throw new LfException(-8, "track3 length error, need <=110");
        }
        this.track3 = track3;
    }

    public String getClientNo() {
        return clientNo;
    }

    public void setClientNo(String clientNo) throws LfException {
        if (clientNo.getBytes().length > 15) {
            throw new LfException(-8, "clientNo length error, need <=15");
        }
        this.clientNo = clientNo;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) throws LfException {
        if (operator.getBytes().length > 15) {
            throw new LfException(-8, "operator length error, need <=15");
        }
        this.operator = operator;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) throws LfException {
        if (serialNo.getBytes().length > 20) {
            throw new LfException(-8, "serialNo length error, need <=20");
        }
        this.serialNo = serialNo;
    }

}
