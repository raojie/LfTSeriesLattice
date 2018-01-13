package com.landfoneapi.protocol.pkg.xmparking;

import com.landfoneapi.protocol.pkg.TradeType;
import com.landfoneapi.protocol.pkg._03_Common;
import com.landfone.common.utils.LfException;
import com.landfone.common.utils.LfUtils;

import java.io.UnsupportedEncodingException;

/**
 * Created by asus on 2017/2/20.
 */

public class _03_BSCCancel extends _03_Common {

    private String amount = "";
    private String voucher = "";
    private String track2 = "";
    private String track3 = "";
    private String clientNo = "";
    private String operator = "";
    private String serialNo = "";
    private String prefield0 = "";
    private String prefield1 = "";
    private String prefield2 = "";
    private String prefield3 = "";

    public _03_BSCCancel() {
        this.setTradeType(TradeType.BTC_CANCEL);
    }

    public void reset() {
        super.reset();
        this.setTradeType(TradeType.BTC_CANCEL);
        try {
            this.setAmount("");
            this.setVoucher("");
            this.setTrack2("");
            this.setTrack3("");
            this.setClientNo("");
            this.setOperator("");
            this.setSerialNo("");
            this.setPrefield0("");
            this.setPrefield1("");
            this.setPrefield2("");
            this.setPrefield3("");
        } catch (LfException e) {
            e.printStackTrace();
        }
    }

    @Override
    public byte[] getBytes() throws UnsupportedEncodingException {
        byte[] retbb = new byte[128 * 4];
        byte[] retbb2 = null;
        int i = 0;
        byte[] tmpbb_1 = new byte[24];
        byte[] commonbb = null;

        commonbb = super.getBytes();
        if (commonbb != null) {
            i += LfUtils.memcpy(retbb, i, commonbb, 0, commonbb.length);
        }
        //--------金额
        String.format("%120s", this.amount);
        tmpbb_1 = this.amount.getBytes();
        if (tmpbb_1 != null && tmpbb_1.length >= 12) {
            i += LfUtils.memcpy(retbb, i, tmpbb_1, 0, 12);
        }
        i += LfUtils.memcpy(retbb, i, FS, 0, FS.length);
        //原交易凭证号
        i += LfUtils.memcpy(retbb, i, voucher.getBytes(), 0, voucher.getBytes().length);
        //分隔符
        i += LfUtils.memcpy(retbb, i, FS, 0, FS.length);
        //Track2
        i += LfUtils.memcpy(retbb, i, track2.getBytes(), 0, track2.getBytes().length);
        //分隔符
        i += LfUtils.memcpy(retbb, i, FS, 0, FS.length);
        //Track3
        i += LfUtils.memcpy(retbb, i, track3.getBytes(), 0, track3.getBytes().length);
        //分隔符
        i += LfUtils.memcpy(retbb, i, FS, 0, FS.length);
        //Track3
        i += LfUtils.memcpy(retbb, i, clientNo.getBytes(), 0, clientNo.getBytes().length);
        //分隔符
        i += LfUtils.memcpy(retbb, i, FS, 0, FS.length);
        //Track3
        i += LfUtils.memcpy(retbb, i, operator.getBytes(), 0, operator.getBytes().length);
        //分隔符
        i += LfUtils.memcpy(retbb, i, FS, 0, FS.length);
        //Track3
        i += LfUtils.memcpy(retbb, i, serialNo.getBytes(), 0, serialNo.getBytes().length);
        //分隔符
        i += LfUtils.memcpy(retbb, i, FS, 0, FS.length);
        //预留字段0
        i += LfUtils.memcpy(retbb, i, prefield0.getBytes(), 0, prefield0.getBytes().length);
        //分隔符
        i += LfUtils.memcpy(retbb, i, FS, 0, FS.length);
        //预留字段1
        i += LfUtils.memcpy(retbb, i, prefield1.getBytes(), 0, prefield1.getBytes().length);
        //分隔符
        i += LfUtils.memcpy(retbb, i, FS, 0, FS.length);
        //预留字段2
        i += LfUtils.memcpy(retbb, i, prefield2.getBytes(), 0, prefield2.getBytes().length);
        //分隔符
        i += LfUtils.memcpy(retbb, i, FS, 0, FS.length);
        //预留字段3
        i += LfUtils.memcpy(retbb, i, prefield3.getBytes(), 0, prefield3.getBytes().length);
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

    public String getVoucher() {
        return voucher;
    }

    public void setVoucher(String voucher) throws LfException {
        if (voucher.length() != 6) {
            throw new LfException(-8, "voucher length error, need 6");
        }
        this.voucher = voucher;
    }


    public String getTrack2() {
        return track2;
    }

    public void setTrack2(String track2) throws LfException {
        if (track2.length() != 37) {
            throw new LfException(-8, "track2 length error, need 37");
        }
        this.track2 = track2;
    }

    public String getTrack3() {
        return track3;
    }

    public void setTrack3(String track3) throws LfException {
        if (track3.length() != 104) {
            throw new LfException(-8, "track3 length error, need 104");
        }
        this.track3 = track3;
    }

    public String getClientNo() {
        return clientNo;
    }

    public void setClientNo(String clientNo) throws LfException {
        if (clientNo.length() != 15) {
            throw new LfException(-8, "clientNo length error, need 15");
        }
        this.clientNo = clientNo;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) throws LfException {
        if (operator.length() != 15) {
            throw new LfException(-8, "operator length error, need 15");
        }
        this.operator = operator;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) throws LfException {
        if (serialNo.length() != 20) {
            throw new LfException(-8, "serialNo length error, need 20");
        }
        this.serialNo = serialNo;
    }

    public String getPrefield0() {
        return prefield0;
    }

    public void setPrefield0(String prefield0) {
        this.prefield0 = prefield0;
    }

    public String getPrefield1() {
        return prefield1;
    }

    public void setPrefield1(String prefield1) {
        this.prefield1 = prefield1;
    }

    public String getPrefield2() {
        return prefield2;
    }

    public void setPrefield2(String prefield2) {
        this.prefield2 = prefield2;
    }

    public String getPrefield3() {
        return prefield3;
    }

    public void setPrefield3(String prefield3) {
        this.prefield3 = prefield3;
    }
}
