package com.landfoneapi.protocol.pkg;

import com.landfone.common.utils.LfException;
import com.landfone.common.utils.LfUtils;

import java.io.UnsupportedEncodingException;

public class _03_Purchase extends _03_Common {

    private String amount = "";
    private String track2 = "";
    private String track3 = "";
    private String clientNo = "";
    private String operator = "";
    private String serialNo = "";
    private String goodsTotalNum = "";//商品总数
    private String goodsInfo = "";//商品信息

    //构造函数
    public _03_Purchase() {
        this.setTradeType(TradeType.PURCHASE);
    }

    @Override
    public void reset() {
        super.reset();
        this.setTradeType(TradeType.PURCHASE);
        try {
            this.setAmount("");
            this.setClientNo("");
            this.setOperator("");
            this.setMer_cn_name("");
            this.setMer_en_name("");
            this.setSerialNo("");
            this.setTrack2("");
            this.setTrack3("");
            this.setGoodsInfo("");
            this.setGoodsTotalNum("");
        } catch (LfException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    public byte[] getBytes() throws UnsupportedEncodingException {
        byte[] amount = new byte[24];
        byte[] goodstotalnum = null;
        byte[] goodsinfo = null;
        byte[] commonbb = null;
        byte[] retbb = null;
        int retlen = 0, i = 0;

        commonbb = super.getBytes();

        //retlen +=1;//分隔符
        //LfUtils.setbcd(tmpbb_1, "%s", this.amount);
        String.format("%120s", this.amount);
        amount = this.amount.getBytes();
        goodstotalnum = goodsTotalNum.getBytes();
//        dbg_TprintfWHex(this.getClass().getName(), goodstotalnum, goodstotalnum.length, "---goodstotalnum---");
        goodsinfo = goodsInfo.getBytes();
//        dbg_TprintfWHex(this.getClass().getName(), goodsinfo, goodsinfo.length, "---goodsinfo---");
//        dbg_TprintfWHex(this.getClass().getName(), this.operator.getBytes(), getOperator().getBytes().length, "---Operator---");
        retlen += 12 + 1;

        retlen += goodsTotalNum.getBytes().length > 0 ? (1 + goodsTotalNum.getBytes().length) : 1;
        retlen += goodsInfo.getBytes().length > 0 ? (1 + goodsInfo.getBytes().length) : 1;
        retlen += track2.getBytes().length > 0 ? (1 + track2.getBytes().length) : 1;
        retlen += track3.getBytes().length > 0 ? (1 + track3.getBytes().length) : 1;
        retlen += clientNo.getBytes().length > 0 ? (1 + clientNo.getBytes().length) : 1;
        retlen += operator.getBytes().length > 0 ? (1 + operator.getBytes().length) : 1;
        retlen += serialNo.getBytes().length > 0 ? (1 + serialNo.getBytes().length) : 1;
        //retlen +=1;//结束的FS
        if (commonbb != null && retlen > 0 && retlen <= (1 +goodsTotalNum.length() + goodsInfo.length() + 12 + 1 + 37 + 1 + 104 + 1 + 15 + 1 + 15 + 1 + 20)) {
            retbb = new byte[commonbb.length + retlen];
            if (retbb != null) {
                //common数据
                LfUtils.memcpy(retbb, i, commonbb, 0, commonbb.length);
                i += commonbb.length;
                //LfUtils.memcpy(retbb, i,FS,0, FS.length);
                //i+=FS.length;
                dbg_mPrintf("commonbb:%d, i:%d, retbb:%d, tmpbb_1:%d", commonbb.length, i, retbb.length, amount.length);
                //当前交易的数据
                LfUtils.memcpy(retbb, i, amount, 0, 12);
                i += 12;
                LfUtils.memcpy(retbb, i, FS, 0, FS.length);
                i += FS.length;
                //交易商品总数
                LfUtils.memcpy(retbb, i, goodstotalnum, 0, goodstotalnum.length);
                i += goodstotalnum.length;
                LfUtils.memcpy(retbb, i, FS, 0, FS.length);
                i += FS.length;
                //交易商品信息
                LfUtils.memcpy(retbb, i, goodsinfo, 0, goodsinfo.length);
                i += goodsinfo.length;
                LfUtils.memcpy(retbb, i, FS, 0, FS.length);
                i += FS.length;

                if (track2.getBytes().length > 0) {
                    LfUtils.memcpy(retbb, i, track2.getBytes(), 0, track2.getBytes().length);
                    i += track2.getBytes().length;
                    LfUtils.memcpy(retbb, i, FS, 0, FS.length);
                    i += FS.length;
                }
                if (track3.getBytes().length > 0) {
                    LfUtils.memcpy(retbb, i, track3.getBytes(), 0, track3.getBytes().length);
                    i += track3.getBytes().length;
                    LfUtils.memcpy(retbb, i, FS, 0, FS.length);
                    i += FS.length;
                }
                if (clientNo.getBytes().length > 0) {
                    LfUtils.memcpy(retbb, i, clientNo.getBytes(), 0, clientNo.getBytes().length);
                    i += clientNo.getBytes().length;
                    LfUtils.memcpy(retbb, i, FS, 0, FS.length);
                    i += FS.length;
                }
                if (operator.getBytes().length > 0) {
                    LfUtils.memcpy(retbb, i, operator.getBytes(), 0, operator.getBytes().length);
                    i += operator.getBytes().length;
                }
                LfUtils.memcpy(retbb, i, FS, 0, FS.length);
                i += FS.length;
                if (serialNo.getBytes().length > 0) {
                    LfUtils.memcpy(retbb, i, serialNo.getBytes(), 0, serialNo.getBytes().length);
                    i += serialNo.getBytes().length;
                }
                LfUtils.memcpy(retbb, i, FS, 0, FS.length);
                i += FS.length;
            }
        }
        return retbb;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) throws LfException {
        if (amount.getBytes().length != 12) {
            throw new LfException(-8, "amount length error, need 12");
        }
        this.amount = amount;
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
            throw new LfException(-8, "track3 length error, need <=110");
        }
        this.track3 = track3;
    }

    public String getClientNo() {
        return clientNo;
    }

    public void setClientNo(String clientNo) throws LfException {
        if (clientNo.getBytes().length > 15) {
            throw new LfException(-8, "clientNo length error, need <=15");
        }
        this.clientNo = clientNo;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) throws LfException {
//        if (operator.getBytes().length > 15) {
//            throw new LfException(-8, "operator length error, need <=15");
//        }
        this.operator = operator;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) throws LfException {
        if (serialNo.getBytes().length > 20) {
            throw new LfException(-8, "serialNo length error, need <=20");
        }
        this.serialNo = serialNo;
    }

    public String getGoodsTotalNum() {
        return goodsTotalNum;
    }

    public void setGoodsTotalNum(String goodsTotalNum) {
        this.goodsTotalNum = goodsTotalNum;
    }

    public String getGoodsInfo() {
        return goodsInfo;
    }

    public void setGoodsInfo(String goodsInfo) {
        this.goodsInfo = goodsInfo;
    }
}
