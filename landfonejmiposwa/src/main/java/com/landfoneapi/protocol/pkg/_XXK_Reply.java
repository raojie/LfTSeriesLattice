package com.landfoneapi.protocol.pkg;

import com.landfoneapi.mispos.ErrCode;
import com.landfoneapi.mispos.MISPOS;
import com.landfone.common.utils.Debug;
import com.landfone.common.utils.LfException;
import com.landfone.common.utils.LfUtils;

public class _XXK_Reply extends Debug {

    private byte xxk = MISPOS.PACK_ACK;//指令代码
    private ErrCode xxkCode = ErrCode._00;//指令代码
    private String info = "";//提示信息

    public static final byte[] FS = {0x1C};

    public void reset() {
        this.setXxk(MISPOS.PACK_ACK);
        this.setXxkCode(ErrCode._00);
        try {
            this.setInfo("");
        } catch (LfException e) {
            e.printStackTrace();
        }
    }

    public byte[] getBytes() {
        byte[] tmpbb_1 = new byte[24];
        byte[] retbb = null;
        int retlen = 0, i = 0;
        int total = 0;

        retlen += 1;//指令代码
        retlen += 1;//分隔符
        retlen += 2;//代码
        retlen += 1;//分隔符
        retlen += info.getBytes().length;//提示信息
        retlen += 1;//分隔符

        if (retlen > 0) {
            retbb = new byte[retlen];
            if (retbb != null) {//TODO:
                retbb[i++] = this.xxk;
                LfUtils.memcpy(retbb, i, FS, 0, FS.length);
                i += FS.length;

                LfUtils.memcpy(retbb, i, this.xxkCode.getCode().getBytes(), 0, this.xxkCode.getCode().getBytes().length);
                i += this.xxkCode.getCode().getBytes().length;
                LfUtils.memcpy(retbb, i, FS, 0, FS.length);
                i += FS.length;

                if (info.getBytes().length > 0) {
                    LfUtils.memcpy(retbb, i, info.getBytes(), 0, info.getBytes().length);
                    i += info.getBytes().length;
                }
                LfUtils.memcpy(retbb, i, FS, 0, FS.length);
                i += FS.length;

            }
        }
        return retbb;
    }

    public byte getXxk() {
        return xxk;
    }

    public void setXxk(byte xxk) {
        this.xxk = xxk;
    }

    public ErrCode getXxkCode() {
        return xxkCode;
    }

    public void setXxkCode(ErrCode xxkCode) {
        this.xxkCode = xxkCode;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) throws LfException {
        if (info.getBytes().length > 30) {
            throw new LfException(-8, "to large, max 30");
        }
        this.info = info;
    }

}
