package com.landfoneapi.protocol.pkg.jxnx;

import com.landfoneapi.protocol.pkg.TradeType;
import com.landfoneapi.protocol.pkg._03_Common;
import com.landfone.common.utils.LfException;
import com.landfone.common.utils.LfUtils;

import java.io.UnsupportedEncodingException;

/**
 * Created by ASUS on 2016/3/10.
 */
public class _03_CheckBalance extends _03_Common {
    private String track2 = "";
    private String track3 = "";

    public _03_CheckBalance() {
        this.setTradeType(TradeType.CHECK_BALANCE);
    }

    public void reset() {
        super.reset();
        this.setTradeType(TradeType.CHECK_BALANCE);
        try {
            this.setTrack2("");
            this.setTrack3("");
        } catch (LfException e) {
            e.printStackTrace();
        }
    }

    public byte[] getBytes() throws UnsupportedEncodingException {
        byte[] retbb = new byte[128 + 2];
        byte[] retbb2 = null;
        int i = 0;
        byte[] commonbb = null;


        commonbb = super.getBytes();
        if (commonbb != null) {
            i += LfUtils.memcpy(retbb, i, commonbb, 0, commonbb.length);
        }

        LfUtils.memcpy(retbb, i, this.getTrack2().getBytes(), 0, this.getTrack2().getBytes().length);
        i += this.getTrack2().getBytes().length;

        LfUtils.memcpy(retbb, i, FS, 0, FS.length);
        i += FS.length;

        LfUtils.memcpy(retbb, i, this.getTrack3().getBytes(), 0, this.getTrack3().getBytes().length);
        i += this.getTrack3().getBytes().length;

        LfUtils.memcpy(retbb, i, FS, 0, FS.length);
        i += FS.length;

        if (i > 0 && i < retbb.length) {
            retbb2 = new byte[i];
            LfUtils.memcpy(retbb2, 0, retbb, 0, i);
        }
        return retbb2;
    }

    public String getTrack2() {
        return track2;
    }

    public void setTrack2(String track2) throws LfException {
        if (track2.getBytes().length > 40) {
            throw new LfException(-8, "track2 length error, need <=40");
        }
        this.track2 = track2;
    }

    public String getTrack3() {
        return track3;
    }

    public void setTrack3(String track3) throws LfException {
        if (track3.getBytes().length > 110) {
            throw new LfException(-8, "track3 length error, need 《=110");
        }
        this.track3 = track3;
    }

}
