package com.landfoneapi.protocol.pkg;

import com.landfone.common.utils.LfUtils;

/**
 * Created by ASUS on 2016/11/9.
 */
public class _03_Ping extends _03_Common {

    public _03_Ping() {
        this.setTradeType(TradeType.PING);
    }

    public void reset() {
        super.reset();
        this.setTradeType(TradeType.PING);
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
