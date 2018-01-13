package com.landfoneapi.protocol.pkg.junpeng;

import com.landfoneapi.protocol.pkg.TradeType;
import com.landfoneapi.protocol.pkg._03_Common;
import com.landfone.common.utils.LfException;
import com.landfone.common.utils.LfUtils;

import java.io.UnsupportedEncodingException;

/**
 * Created by asus on 2017/4/7.
 */

public class _03_Recharge extends _03_Common {

    private String amount = "";
    private String track2 = "";
    private String track3 = "";
    private String clientNo = "";
    private String operator = "";
    private String serialNo = "";

    private String extraInfo = "";

    //构造函数
    public _03_Recharge() {
        this.setTradeType(TradeType.RECHARGE);
    }

    public void reset() {
        super.reset();
        this.setTradeType(TradeType.PURCHASE);
        try {
            this.setAmount("");
            this.setClientNo("");
            this.setOperator("");
            this.setMer_cn_name("");
            this.setMer_en_name("");
            this.setSerialNo("");
            this.setTrack2("");
            this.setTrack3("");
        } catch (LfException e) {
            // TODO Auto-generated catch block
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

        if (extraInfo.length() > 0) {
            try {
                LfUtils.memcpy(retbb, i, extraInfo.getBytes("GBK"), 0, extraInfo.getBytes("GBK").length);
                i += extraInfo.getBytes("GBK").length;
                LfUtils.memcpy(retbb, i, FS, 0, FS.length);
                i += FS.length;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

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

    public void setExtraInfo(String extraInfo) throws LfException {
        if (extraInfo.length() > (EXTRA_INFO_SIZE - 5)) {
            throw new LfException(-8, "extraInfo length error, need <=" + (EXTRA_INFO_SIZE - 5));
        }
        this.extraInfo = extraInfo;
    }

    public void setExtraInfoWithLF111(String extraInfo) throws LfException {
        if (extraInfo.length() > (EXTRA_INFO_SIZE)) {
            throw new LfException(-8, "extraInfo length error, need <=" + (EXTRA_INFO_SIZE));
        }
        this.extraInfo = extraInfo;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public static final int EXTRA_INFO_SIZE = 80;
}
