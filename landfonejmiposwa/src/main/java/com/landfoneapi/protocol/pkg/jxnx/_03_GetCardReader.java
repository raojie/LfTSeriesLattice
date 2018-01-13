package com.landfoneapi.protocol.pkg.jxnx;

import com.landfoneapi.protocol.pkg.TradeType;
import com.landfoneapi.protocol.pkg._03_Common;
import com.landfone.common.utils.LfException;
import com.landfone.common.utils.LfUtils;

/**
 * Created by ASUS on 2016/6/23.
 */
public class _03_GetCardReader extends _03_Common {
    private String cmd = "";
    private String p1 = "";
    private String inBuf = "";
    private String timeout_s = "";

    public _03_GetCardReader() {
        this.setTradeType(TradeType.GET_CARDREADER);
    }

    public void reset() {
        super.reset();
        this.setTradeType(TradeType.GET_CARDREADER);
        try {
            this.setCmd("");
            this.setP1("");
            this.setInBuf("");
            this.setTimeout_s("");
        } catch (LfException e) {
            e.printStackTrace();
        }
    }

    public byte[] getBytes() {
        byte[] retbb = new byte[128 * 3];
        byte[] retbb2 = null;
        int i = 0;
        i += LfUtils.memcpy(retbb, i, this.getTradeType().getCode().getBytes(), 0, this.getTradeType().getCode().getBytes().length);
        i += LfUtils.memcpy(retbb, i, FS, 0, FS.length);
        i += LfUtils.memcpy(retbb, i, this.getCmd().getBytes(), 0, this.getCmd().getBytes().length);
        i += LfUtils.memcpy(retbb, i, FS, 0, FS.length);
        i += LfUtils.memcpy(retbb, i, this.getP1().getBytes(), 0, this.getP1().getBytes().length);
        i += LfUtils.memcpy(retbb, i, FS, 0, FS.length);
        i += LfUtils.memcpy(retbb, i, this.getInBuf().getBytes(), 0, this.getInBuf().getBytes().length);
        i += LfUtils.memcpy(retbb, i, FS, 0, FS.length);
        i += LfUtils.memcpy(retbb, i, this.getTimeout_s().getBytes(), 0, this.getTimeout_s().getBytes().length);
        i += LfUtils.memcpy(retbb, i, FS, 0, FS.length);

        if (1 > 0 && i < retbb.length) {
            retbb2 = new byte[i];
            LfUtils.memcpy(retbb2, 0, retbb, 0, i);
        }
        return retbb2;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) throws LfException {
        if (cmd.getBytes().length > 1) {
            throw new LfException(-8, "cmd length error,need = 1");
        }
        this.cmd = cmd;
    }

    public String getP1() {
        return p1;
    }

    public void setP1(String p1) throws LfException {
        if (p1.getBytes().length > 1) {
            throw new LfException(-8, "p1 length error,need = 1");
        }
        this.p1 = p1;
    }

    public String getInBuf() {
        return inBuf;
    }

    public void setInBuf(String inBuf) throws LfException {
        if (inBuf.getBytes().length > 256) {
            throw new LfException(-8, "inBuf length error,need <=256");
        }
        this.inBuf = inBuf;
    }

    public String getTimeout_s() {
        return timeout_s;
    }

    public void setTimeout_s(String timeout_s) throws LfException {
        if (timeout_s.getBytes().length > 2) {
            throw new LfException(-8, "timeout_s error,need<=2");
        }
        this.timeout_s = timeout_s;
    }
}