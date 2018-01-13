package com.landfoneapi.protocol.pkg.tms;

import com.landfone.common.utils.LfUtils;
import com.landfoneapi.protocol.pkg.TradeType;
import com.landfoneapi.protocol.pkg._03_Common;

import java.io.UnsupportedEncodingException;

/**
 * Created by asus on 2017/5/18.
 */

public class _03_TmsOnlineReport extends _03_Common {
    public _03_TmsOnlineReport() {
        this.setTradeType(TradeType.TMS_ONLINE_REPORT);
    }

    public void reset() {
        super.reset();
        this.setTradeType(TradeType.TMS_ONLINE_REPORT);
    }

    public byte[] getBytes() throws UnsupportedEncodingException {
        byte[] commonbb = null;
        byte[] retbb = null;
        int retlen = 0, i = 0;

        commonbb = super.getBytes();
        if (commonbb != null) {
            retbb = new byte[commonbb.length];
            if (retbb != null) {
                //common数据
                LfUtils.memcpy(retbb, i, commonbb, 0, commonbb.length);
                i += commonbb.length;
                //LfUtils.memcpy(retbb, i,FS,0, FS.length);
                //i+=FS.length;
            }
        }
        return retbb;
    }

}
