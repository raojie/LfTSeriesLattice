package com.landfoneapi.protocol.pkg.jxnx;

import com.landfoneapi.protocol.pkg.TradeType;
import com.landfoneapi.protocol.pkg._03_Common;
import com.landfone.common.utils.LfUtils;

/**
 * Created by ASUS on 2016/3/9.
 */
public class _03_GetKeyboard extends _03_Common {

    //获取键盘信息
    public _03_GetKeyboard() {
        this.setTradeType(TradeType.GET_KEYBOARD);
    }

    public void reset() {
        super.reset();
        this.setTradeType(TradeType.GET_KEYBOARD);
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
