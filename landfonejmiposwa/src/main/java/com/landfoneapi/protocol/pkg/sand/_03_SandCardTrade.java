package com.landfoneapi.protocol.pkg.sand;

import com.landfoneapi.protocol.pkg.TradeType;
import com.landfoneapi.protocol.pkg._03_Common;
import com.landfone.common.utils.LfException;
import com.landfone.common.utils.LfUtils;

import java.io.UnsupportedEncodingException;

/**
 * Created by asus on 2017/3/31.
 */

public class _03_SandCardTrade extends _03_Common {
    private String amount = "";
    private String track2 = "";
    private String track3 = "";
    private String clientNo = "";
    private String operator = "";
    private String serialNo = "";
    private String reservedField0 = "";
    private String reservedField1 = "";
    private String reservedField2 = "";
    private String reservedField3 = "";

    public _03_SandCardTrade() {
        this.setTradeType(TradeType.SAND_CARD_TRADE);
    }

    public void reset() {
        super.reset();
        this.setTradeType(TradeType.SAND_CARD_TRADE);
        try {
            this.setMer_cn_name("");
            this.setMer_en_name("");
            this.setAmount("");
            this.setTrack2("");
            this.setTrack3("");
            this.setClientNo("");
            this.setOperator("");
            this.setSerialNo("");
            this.setReservedField0("");
            this.setReservedField1("");
            this.setReservedField2("");
            this.setReservedField3("");
        } catch (LfException e) {
            e.printStackTrace();
        }
    }

    @Override
    public byte[] getBytes() throws UnsupportedEncodingException {
        int i = 0;
        byte[] retbb = null;
        byte[] tmpbb = new byte[128 * 4];
        byte[] amountbb = new byte[24];
        byte[] commonbb = null;
        commonbb = super.getBytes();
        if (commonbb != null) {
            i += LfUtils.memcpy(tmpbb, i, commonbb, 0, commonbb.length);
        }
        String.format("%120s", this.amount);
        amountbb = this.amount.getBytes();
        if (amountbb != null && amountbb.length >= 12) {
            i += LfUtils.memcpy(tmpbb, i, amountbb, 0, 12);
        }
        i += LfUtils.memcpy(tmpbb, i, FS, 0, FS.length);
        //Track2
        i += LfUtils.memcpy(tmpbb, i, track2.getBytes(), 0, track2.getBytes().length);
        //分隔符
        i += LfUtils.memcpy(tmpbb, i, FS, 0, FS.length);
        //Track3
        i += LfUtils.memcpy(tmpbb, i, track3.getBytes(), 0, track3.getBytes().length);
        //分隔符
        i += LfUtils.memcpy(tmpbb, i, FS, 0, FS.length);
        //终端号
        i += LfUtils.memcpy(tmpbb, i, clientNo.getBytes(), 0, clientNo.getBytes().length);
        //分隔符
        i += LfUtils.memcpy(tmpbb, i, FS, 0, FS.length);
        //操作员号
        i += LfUtils.memcpy(tmpbb, i, operator.getBytes(), 0, operator.getBytes().length);
        //分隔符
        i += LfUtils.memcpy(tmpbb, i, FS, 0, FS.length);
        //收银流水号
        i += LfUtils.memcpy(tmpbb, i, serialNo.getBytes(), 0, serialNo.getBytes().length);
        //分隔符
        i += LfUtils.memcpy(tmpbb, i, FS, 0, FS.length);
        //预留字段0
        i += LfUtils.memcpy(tmpbb, i, reservedField0.getBytes(), 0, reservedField0.getBytes().length);
        //分隔符
        i += LfUtils.memcpy(tmpbb, i, FS, 0, FS.length);
        //预留字段1
        i += LfUtils.memcpy(tmpbb, i, reservedField1.getBytes(), 0, reservedField1.getBytes().length);
        //分隔符
        i += LfUtils.memcpy(tmpbb, i, FS, 0, FS.length);
        //预留字段2
        i += LfUtils.memcpy(tmpbb, i, reservedField2.getBytes(), 0, reservedField2.getBytes().length);
        //分隔符
        i += LfUtils.memcpy(tmpbb, i, FS, 0, FS.length);
        //预留字段3
        i += LfUtils.memcpy(tmpbb, i, reservedField3.getBytes(), 0, reservedField3.getBytes().length);
        //分隔符
        i += LfUtils.memcpy(tmpbb, i, FS, 0, FS.length);

        if (i > 0 && i < tmpbb.length) {
            retbb = new byte[i];
            LfUtils.memcpy(retbb, 0, tmpbb, 0, i);
        }
        return retbb;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) throws LfException {
        if (amount.getBytes().length != 12) {
            throw new LfException(-8, "amount length error, need 12");
        }
        this.amount = amount;
    }

    public String getTrack2() {
        return track2;
    }

    public void setTrack2(String track2) throws LfException {
        if (track2.getBytes().length > 40) {
            throw new LfException(-8, "track2 length error, need <=37");
        }
        this.track2 = track2;
    }

    public String getTrack3() {
        return track3;
    }

    public void setTrack3(String track3) throws LfException {
        if (track3.getBytes().length > 110) {
            throw new LfException(-8, "track3 length error, need <=104");
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

    public String getReservedField0() {
        return reservedField0;
    }

    public void setReservedField0(String reservedField0) throws LfException {
        if (reservedField0.getBytes().length > 50) {
            throw new LfException(-8, "reservedField0 length error, need <=50");
        }
        this.reservedField0 = reservedField0;
    }

    public String getReservedField1() {
        return reservedField1;
    }

    public void setReservedField1(String reservedField1) throws LfException {
        if (reservedField1.getBytes().length > 50) {
            throw new LfException(-8, "reservedField1 length error, need <=50");
        }
        this.reservedField1 = reservedField1;
    }

    public String getReservedField2() {
        return reservedField2;
    }

    public void setReservedField2(String reservedField2) throws LfException {
        if (reservedField2.getBytes().length > 40) {
            throw new LfException(-8, "reservedField2 length error, need <=40");
        }
        this.reservedField2 = reservedField2;
    }

    public String getReservedField3() {
        return reservedField3;
    }

    public void setReservedField3(String reservedField3) throws LfException {
        if (reservedField3.getBytes().length > 30) {
            throw new LfException(-8, "reservedField3 length error, need <=30");
        }
        this.reservedField3 = reservedField3;
    }
}
