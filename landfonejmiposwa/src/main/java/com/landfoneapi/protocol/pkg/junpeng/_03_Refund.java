package com.landfoneapi.protocol.pkg.junpeng;

import com.landfoneapi.protocol.pkg.TradeType;
import com.landfoneapi.protocol.pkg._03_Common;
import com.landfone.common.utils.LfException;
import com.landfone.common.utils.LfUtils;

import java.io.UnsupportedEncodingException;

public class _03_Refund extends _03_Common {

    private String cardNo = "";
    private String amount = "";
    private String specTmnSerial = "";

    //构造函数
    public _03_Refund() {
        this.setTradeType(TradeType.REFUND);
    }

    public void reset() {
        super.reset();
        this.setTradeType(TradeType.REFUND);
        try {
            this.setAmount("");
            this.setSpecTmnSerial("");
        } catch (LfException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public byte[] getBytes() {
        byte[] retbb = new byte[128 * 4];
        byte[] retbb2 = null;
        int i = 0;
        byte[] tmpbb_1 = new byte[24];
        byte[] commonbb = null;


        //指令代码
        i += LfUtils.memcpy(retbb, i, TradeType.REFUND.getCode().getBytes(), 0, TradeType.REFUND.getCode().getBytes().length);
        //分隔符
        i += LfUtils.memcpy(retbb, i, FS, 0, FS.length);
        //卡号
        i += LfUtils.memcpy(retbb, i, cardNo.getBytes(), 0, cardNo.getBytes().length);
        //分隔符
        i += LfUtils.memcpy(retbb, i, FS, 0, FS.length);
        //金额
        String.format("%120s", this.amount);
        tmpbb_1 = this.amount.getBytes();
        if (tmpbb_1 != null && tmpbb_1.length >= 12) {
            i += LfUtils.memcpy(retbb, i, tmpbb_1, 0, 12);
        }
        i += LfUtils.memcpy(retbb, i, FS, 0, FS.length);
        //Track3
        i += LfUtils.memcpy(retbb, i, specTmnSerial.getBytes(), 0, specTmnSerial.getBytes().length);
        //分隔符
        i += LfUtils.memcpy(retbb, i, FS, 0, FS.length);


        if (i > 0 && i < retbb.length) {
            retbb2 = new byte[i];
            LfUtils.memcpy(retbb2, 0, retbb, 0, i);
        }
        return retbb2;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) throws LfException {
        if (amount.length() != 12) {
            throw new LfException(-8, "amount length error, need 12");
        }
        this.amount = amount;
    }

    public String getSpecTmnSerial() {
        return specTmnSerial;
    }

    public void setSpecTmnSerial(String specTmnSerial) throws LfException {
        try {
            if (specTmnSerial == null || (specTmnSerial.getBytes("GBK")).length < 26) {
                throw new LfException(-8, "specTmnSerial length error, need < 26");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new LfException(-8, "specTmnSerial TO bytes error");
        }
        this.specTmnSerial = specTmnSerial;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) throws LfException {
        try {
            if (cardNo == null || (cardNo.getBytes("GBK")).length > 19) {
                throw new LfException(-8, "cardNo length error, need <= 19");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new LfException(-8, "cardNo TO bytes error");
        }
        this.cardNo = cardNo;
    }
}
