package com.landfoneapi.protocol.pkg.sand;

import com.landfoneapi.protocol.pkg.TradeType;
import com.landfoneapi.protocol.pkg._03_Common;

import java.io.UnsupportedEncodingException;

/**
 * Created by asus on 2017/3/31.
 */

public class _03_SandO2OSignin extends _03_Common {
    public _03_SandO2OSignin() {
        this.setTradeType(TradeType.SAND_O2O_SIGNIN);
    }

    public void reset() {
        super.reset();
        this.setTradeType(TradeType.SAND_O2O_SIGNIN);
    }

    public byte[] getBytes() throws UnsupportedEncodingException {
        byte[] commonbb = super.getBytes();
        return commonbb;
    }

}
