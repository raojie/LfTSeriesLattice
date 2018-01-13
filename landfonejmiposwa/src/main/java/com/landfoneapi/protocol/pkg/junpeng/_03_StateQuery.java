package com.landfoneapi.protocol.pkg.junpeng;

import com.landfone.common.utils.LfUtils;
import com.landfoneapi.protocol.pkg.TradeType;
import com.landfoneapi.protocol.pkg._03_Common;

/**
 * Created by ASUS on 2016/11/9.
 */
public class _03_StateQuery extends _03_Common {

    public _03_StateQuery() {
        this.setTradeType(TradeType.STATEQUERY);
    }

    public void reset() {
        super.reset();
        this.setTradeType(TradeType.STATEQUERY);
    }

    public byte[] getBytes() {
        byte[] retbb = null;
        int retlen = 0, i = 0;

        retlen += this.getTradeType().getCode().getBytes().length;
        retlen += 1;

        retbb = new byte[retlen];

        i += LfUtils.memcpy(retbb, i, this.getTradeType().getCode().getBytes(), 0, this.getTradeType().getCode().getBytes().length);
        i += LfUtils.memcpy(retbb, i, FS, 0, FS.length);
        return retbb;
    }

}
