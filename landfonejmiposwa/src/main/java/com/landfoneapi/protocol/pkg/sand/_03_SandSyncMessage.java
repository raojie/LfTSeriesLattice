package com.landfoneapi.protocol.pkg.sand;

import com.landfone.common.utils.LfUtils;
import com.landfoneapi.protocol.pkg.TradeType;
import com.landfoneapi.protocol.pkg._03_Common;

/**
 * Created by asus on 2017/5/2.
 */

public class _03_SandSyncMessage extends _03_Common {
    public _03_SandSyncMessage() {
        this.setTradeType(TradeType.SAND_SYNC_MESSAGE);
    }

    public void reset() {
        super.reset();
        this.setTradeType(TradeType.SAND_SYNC_MESSAGE);
    }

    public byte[] getBytes() {
        byte[] retbb = null;
        int retlen = 0, i = 0;

        retlen += this.getTradeType().getCode().getBytes().length;
        retlen += 1;

        retbb = new byte[retlen];

        LfUtils.memcpy(retbb, i, this.getTradeType().getCode().getBytes(), 0, this.getTradeType().getCode().getBytes().length);
        i += 2;
        LfUtils.memcpy(retbb, i, FS, 0, FS.length);
        i += FS.length;

        return retbb;
    }

}
