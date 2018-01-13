package com.landfoneapi.protocol.pkg.jxnx;

import com.landfoneapi.protocol.pkg.TradeType;
import com.landfoneapi.protocol.pkg._03_Common;
import com.landfone.common.utils.LfException;
import com.landfone.common.utils.LfUtils;

/**
 * Created by ASUS on 2016/3/15.
 */
public class _03_SetMerTmn extends _03_Common {

    private String mer = "";
    private String tmn = "";

    public _03_SetMerTmn() {
        this.setTradeType(TradeType.SET_MERTMN);
    }

    public void reset() {
        super.reset();
        this.setTradeType(TradeType.SET_MERTMN);
    }

    public byte[] getBytes() {
        byte[] retbb = null;
        int retlen = 0, i = 0;

        retlen += this.getTradeType().getCode().getBytes().length;
        retlen += 1 + 15 + 1 + 8 + 1;

        retbb = new byte[retlen];

        i += LfUtils.memcpy(retbb, i, this.getTradeType().getCode().getBytes(), 0, this.getTradeType().getCode().getBytes().length);
        i += LfUtils.memcpy(retbb, i, FS, 0, FS.length);
        i += LfUtils.memcpy(retbb, i, mer.getBytes(), 0, mer.getBytes().length);
        i += LfUtils.memcpy(retbb, i, FS, 0, FS.length);
        i += LfUtils.memcpy(retbb, i, tmn.getBytes(), 0, tmn.getBytes().length);
        i += LfUtils.memcpy(retbb, i, FS, 0, FS.length);
        return retbb;
    }

    public String getMer() {
        return mer;
    }

    public void setMer(String mer) throws LfException {
        if (mer.getBytes().length != 0 && mer.getBytes().length != 15) {
            throw new LfException(-8, "mer length error, need 0 or 15");
        }
        this.mer = mer;
    }

    public String getTmn() {
        return tmn;
    }

    public void setTmn(String tmn) throws LfException {
        if (tmn.getBytes().length != 0 && tmn.getBytes().length != 8) {
            throw new LfException(-8, "tmn length error, need 0 or 8");
        }
        this.tmn = tmn;
    }

}
