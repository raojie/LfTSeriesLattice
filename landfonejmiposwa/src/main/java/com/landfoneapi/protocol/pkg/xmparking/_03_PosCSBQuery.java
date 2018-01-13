package com.landfoneapi.protocol.pkg.xmparking;


import com.landfoneapi.protocol.pkg.TradeType;
import com.landfoneapi.protocol.pkg._03_Common;
import com.landfone.common.utils.LfException;
import com.landfone.common.utils.LfUtils;

/**
 * Created by asus on 2017/2/7.
 */

public class _03_PosCSBQuery extends _03_Common {
    private String BillCode = "";
    private String BillTime = "";

    public _03_PosCSBQuery() {
        this.setTradeType(TradeType.CTB_QUERY);
    }

    public void reset() {
        super.reset();
        this.setTradeType(TradeType.CTB_QUERY);
    }

    public byte[] getBytes() {
        byte[] retbb = new byte[128 + 2];
        byte[] retbb2 = null;
        int i = 0;

        LfUtils.memcpy(retbb, i, this.getTradeType().getCode().getBytes(), 0, this.getTradeType().getCode().getBytes().length);
        i += 2;
        //分隔符
        LfUtils.memcpy(retbb, i, FS, 0, FS.length);
        i += FS.length;

        LfUtils.memcpy(retbb, i, this.getBillCode().getBytes(), 0, this.getBillCode().getBytes().length);
        i += this.getBillCode().getBytes().length;
        //分隔符
        LfUtils.memcpy(retbb, i, FS, 0, FS.length);
        i += FS.length;

        LfUtils.memcpy(retbb, i, this.getBillTime().getBytes(), 0, this.getBillTime().getBytes().length);
        i += this.getBillTime().getBytes().length;
        //分隔符
        LfUtils.memcpy(retbb, i, FS, 0, FS.length);
        i += FS.length;

        if (i > 0 && i < 128) {
            retbb2 = new byte[i];
            LfUtils.memcpy(retbb2, 0, retbb, 0, i);
        }
        return retbb2;
    }

    public String getBillCode() {
        return BillCode;
    }

    public void setBillCode(String billCode) throws LfException {
        if (billCode == null || billCode.getBytes().length > 64) {
            throw new LfException(-8, "billCode length error, need = 64");
        }
        BillCode = billCode;
    }

    public String getBillTime() {
        return BillTime;
    }

    public void setBillTime(String billTime) throws LfException {
        if (billTime == null || billTime.getBytes().length > 10) {
            throw new LfException(-8, "billTime  error, should like YYYY-MM-DD");
        }
        billTime = billTime;
    }
}
