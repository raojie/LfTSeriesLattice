package com.landfoneapi.protocol.pkg.jxnx;

import com.landfoneapi.protocol.pkg.TradeType;
import com.landfoneapi.protocol.pkg._03_Common;
import com.landfone.common.utils.LfException;
import com.landfone.common.utils.LfUtils;

import java.io.UnsupportedEncodingException;

/**
 * Created by ASUS on 2016/3/13.
 */
public class _03_TransQuery extends _03_Common {

    private String startDate = "";
    private String endDate = "";

    public _03_TransQuery() {
        this.setTradeType(TradeType.TRANS_QUERY);
    }

    public void reset() {
        super.reset();
        this.setTradeType(TradeType.TRANS_QUERY);
        try {
            this.setStartDate("");
            this.setEndDate("");
        } catch (LfException e) {
            e.printStackTrace();
        }
    }

    public byte[] getBytes() throws UnsupportedEncodingException {
        byte[] retbb = new byte[128 * 4];
        int i = 0;
        int retlen = 0;
        retlen = 2 + 1 + 8 + 1 + 8 + 1;
        retbb = new byte[retlen];

        i += LfUtils.memcpy(retbb, i, this.getTradeType().getCode().getBytes(), 0, this.getTradeType().getCode().getBytes().length);
        i += LfUtils.memcpy(retbb, i, FS, 0, FS.length);
        i += LfUtils.memcpy(retbb, i, this.getStartDate().getBytes(), 0, this.getStartDate().getBytes().length);
        i += LfUtils.memcpy(retbb, i, FS, 0, FS.length);
        i += LfUtils.memcpy(retbb, i, this.getEndDate().getBytes(), 0, this.getEndDate().getBytes().length);
        i += LfUtils.memcpy(retbb, i, FS, 0, FS.length);

        return retbb;
    }


    public String getStartDate() {
        return startDate;

    }

    public void setStartDate(String startDate) throws LfException {
        if (startDate.getBytes().length > 8) {
            throw new LfException(-8, "startDate(" + startDate + ") length error, need 8");
        }
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) throws LfException {
        if (endDate.getBytes().length > 8) {
            throw new LfException(-8, "endDate(" + endDate + ") length error, need 8");
        }
        this.endDate = endDate;
    }
}
