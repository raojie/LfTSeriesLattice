package com.landfoneapi.protocol.pkg;

import com.landfone.common.utils.Debug;
import com.landfone.common.utils.LfException;
import com.landfone.common.utils.LfUtils;

import java.io.UnsupportedEncodingException;

public class _03_Common extends Debug {
    private TradeType tradeType = TradeType.PURCHASE;//指令代码
    private String mer = "";//商户代码
    private String tmn = "";//终端号
    private String mer_cn_name = "";//商户中文名
    private String mer_en_name = "";//商户英文名//
    private String signin_keyboard = "";//“0”- MK-210 “1”-金属键盘

    public static final byte[] FS = {0x1C};

    public void reset() {
        try {
            this.setMer("000000000000000");
            this.setTmn("00000000");
            this.setMer_cn_name("0");
            this.setMer_en_name("0");
        } catch (LfException e) {
            e.printStackTrace();
        }

    }

    public byte[] getBytes() throws UnsupportedEncodingException {
        int i = 0;
        byte[] retbb = null;
        byte[] tmpbb = new byte[256];

        // 指令代码
        i += LfUtils.memcpy(tmpbb, i, tradeType.getCode().getBytes(), 0, tradeType.getCode().getBytes().length);
        i += LfUtils.memcpy(tmpbb, i, FS, 0, FS.length);
        // 商户号
        if (mer.getBytes().length > 0) {
            i += LfUtils.memcpy(tmpbb, i, mer.getBytes(), 0, mer.getBytes().length);
        }
        i += LfUtils.memcpy(tmpbb, i, FS, 0, FS.length);
        // 终端号
        if (tmn.getBytes().length > 0) {
            i += LfUtils.memcpy(tmpbb, i, tmn.getBytes(), 0, tmn.getBytes().length);
        }
        i += LfUtils.memcpy(tmpbb, i, FS, 0, FS.length);
        // 商户中文名
        if (mer_cn_name.getBytes("GBK").length > 0) {
            i += LfUtils.memcpy(tmpbb, i, mer_cn_name.getBytes("GBK"), 0, mer_cn_name.getBytes("GBK").length);
        }
        i += LfUtils.memcpy(tmpbb, i, FS, 0, FS.length);
        // 商户英文名
        if (mer_en_name.getBytes("GBK").length > 0) {
            i += LfUtils.memcpy(tmpbb, i, mer_en_name.getBytes("GBK"), 0, mer_en_name.getBytes("GBK").length);
        }
        i += LfUtils.memcpy(tmpbb, i, FS, 0, FS.length);
        // 键盘
        if (signin_keyboard.getBytes().length > 0) {
            i += LfUtils.memcpy(tmpbb, i, signin_keyboard.getBytes(), 0, signin_keyboard.getBytes().length);
            i += LfUtils.memcpy(tmpbb, i, FS, 0, FS.length);
        }

        if (i > 0 && i < tmpbb.length) {
            System.out.println(">>>>>>>>>>>>>>>>>>signin_keyboard:" + signin_keyboard);
            retbb = new byte[i];
            LfUtils.memcpy(retbb, 0, tmpbb, 0, i);
        }
        return retbb;
    }

    public TradeType getTradeType() {
        return tradeType;
    }

    public void setTradeType(TradeType tradeType) {
        this.tradeType = tradeType;
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

    public String getMer_cn_name() {
        return mer_cn_name;
    }

    public void setMer_cn_name(String mer_cn_name) throws LfException {
        try {
            if (mer_cn_name.getBytes("GBK").length > 60) {
                throw new LfException(-8, "mer_cn_name length error, need <60");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new LfException(-8, "mer_cn_name getBytes(\"GBK\") error");
        }
        this.mer_cn_name = mer_cn_name;
    }

    public String getMer_en_name() {
        return mer_en_name;
    }

    public void setMer_en_name(String mer_en_name) throws LfException {
        try {
            if (mer_en_name.getBytes("GBK").length > 60) {
                throw new LfException(-8, "mer_en_name length error, need <60");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new LfException(-8, "mer_en_name getBytes(\"GBK\") error");
        }
        this.mer_en_name = mer_en_name;
    }

    public String getSignin_keyboard() {
        return signin_keyboard;
    }

    public void setSignin_keyboard(String signin_keyboard) throws LfException {
        if (signin_keyboard.getBytes().length > 1 || (signin_keyboard.getBytes().length == 1 & (!signin_keyboard.equals("0") && !signin_keyboard.equals("1")))) {
            throw new LfException(-8, "signin_keyboard(" + signin_keyboard + ") length error, need 1, value:'0'/'1'");
        }
        this.signin_keyboard = signin_keyboard;
        System.out.println(">>>>>>>signin_keyboard:" + signin_keyboard);
    }

    private long timeout = 5 * 60;

    public void setTimeout(byte timeout) {
        this.timeout = (long) timeout;
    }

    public byte[] getTimeout() {
        byte[] tmp = {0x00};
        tmp[0] = (byte) timeout;
        return tmp;
    }

    public long getTimeoutMs() {
        return timeout * 1000;
    }
}
