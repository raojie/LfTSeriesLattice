package com.landfoneapi.protocol.pkg.sand;

import com.landfoneapi.protocol.pkg.TradeType;
import com.landfoneapi.protocol.pkg._03_Common;
import com.landfone.common.utils.LfException;
import com.landfone.common.utils.LfUtils;

import java.io.UnsupportedEncodingException;

/**
 * Created by asus on 2017/3/31.
 */

public class _03_SandO2OCancel extends _03_Common {

    private String amount = "";//原交易金额
    private String voucher = "";//原交易凭证号
    private String track2 = "";//二磁道信息
    private String track3 = "";//三磁道信息
    private String clientNo = "";//款台号
    private String operator = "";//操作员号
    private String serialNo = "";//收银流水号
    private String orderNo = "";//64订单号

    public _03_SandO2OCancel() {
        this.setTradeType(TradeType.SAND_O2O_CANCEL);
    }

    public void reset() {
        super.reset();
        this.setTradeType(TradeType.SAND_O2O_CANCEL);
        try {
            this.setAmount("");
            this.setVoucher("");
            this.setTrack2("");
            this.setTrack3("");
            this.setClientNo("");
            this.setOperator("");
            this.setSerialNo("");
            this.setOrderNo("");
        } catch (LfException e) {
            e.printStackTrace();
        }
    }

    @Override
    public byte[] getBytes() throws UnsupportedEncodingException {
        int i = 0;
        byte[] retbb = null;
        byte[] tmpbb = new byte[128 * 4];
        byte[] commonbb = null;
        commonbb = super.getBytes();
        if (commonbb != null) {
            i += LfUtils.memcpy(tmpbb, i, commonbb, 0, commonbb.length);
        }
        //原交易金额
        i += LfUtils.memcpy(tmpbb, i, amount.getBytes(), 0, amount.getBytes().length);
        //分隔符
        i += LfUtils.memcpy(tmpbb, i, FS, 0, FS.length);
        //原交易凭证号
        i += LfUtils.memcpy(tmpbb, i, voucher.getBytes(), 0, voucher.getBytes().length);
        //分隔符
        i += LfUtils.memcpy(tmpbb, i, FS, 0, FS.length);
        //Track2
        i += LfUtils.memcpy(tmpbb, i, track2.getBytes(), 0, track2.getBytes().length);
        //分隔符
        i += LfUtils.memcpy(tmpbb, i, FS, 0, FS.length);
        //Track3
        i += LfUtils.memcpy(tmpbb, i, track3.getBytes(), 0, track3.getBytes().length);
        //分隔符
        i += LfUtils.memcpy(tmpbb, i, FS, 0, FS.length);
        //款台号
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
        //订单号
        i += LfUtils.memcpy(tmpbb, i, orderNo.getBytes(), 0, orderNo.getBytes().length);
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

    public String getVoucher() {
        return voucher;
    }

    public void setVoucher(String voucher) throws LfException {
        if (voucher.getBytes().length > 6) {
            throw new LfException(-8, "voucher length error,need<6");
        }
        this.voucher = voucher;
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
            throw new LfException(-8, "serialNo length error, need <= 20");
        }
        this.serialNo = serialNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) throws LfException {
        if (orderNo.getBytes().length > 64) {
            throw new LfException(-8, "orderNo length error, need <=64");
        }
        this.orderNo = orderNo;
    }

}
