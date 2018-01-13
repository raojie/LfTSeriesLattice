package com.landfoneapi.protocol.pkg.jxnx;

import com.landfoneapi.protocol.pkg.TradeType;
import com.landfoneapi.protocol.pkg._03_Common;
import com.landfone.common.utils.LfException;
import com.landfone.common.utils.LfUtils;

import java.io.UnsupportedEncodingException;

/**
 * 存折补登
 */
public class _03_Payfees extends _03_Common {

    private String amount = "";
    private String track2 = "";
    private String track3 = "";
    private String clientNo = "";
    private String operator = "";
    private String serialNo = "";

    private String AreaCode = "";//4,地区号
    private String IndustryCode = "";//3，行业号
    private String ContentCode = "";//3，缴费内容码
    private String UserNo = "";//20,用户号
    //    private String PreQueryReply = "";//512,原查询应答部分
    private byte[] PreQueryReply = null;

    //private static final int buffsize = 512+256;
    //构造函数
    public _03_Payfees() {
        this.setTradeType(TradeType.PAYFEES);
    }

    public void reset() {
        super.reset();
        this.setTradeType(TradeType.PAYFEES);
        try {
            this.setAmount("");
            this.setClientNo("");
            this.setOperator("");
            this.setMer_cn_name("");
            this.setMer_en_name("");
            this.setSerialNo("");
            this.setTrack2("");
            this.setTrack3("");
        } catch (LfException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public byte[] getBytes() throws UnsupportedEncodingException {
        byte[] retbb = new byte[128 * 4 + 128 * 2];
        byte[] retbb2 = null;
        int i = 0;
        byte[] tmpbb_1 = new byte[24];
        byte[] commonbb = null;


        commonbb = super.getBytes();
        if (commonbb != null) {
            i += LfUtils.memcpy(retbb, i, commonbb, 0, commonbb.length);
        }
        //--------金额
        String.format("%120s", this.amount);
        tmpbb_1 = this.amount.getBytes();
        if (tmpbb_1 != null && tmpbb_1.length >= 12) {
            i += LfUtils.memcpy(retbb, i, tmpbb_1, 0, 12);
        }
        i += LfUtils.memcpy(retbb, i, FS, 0, FS.length);

        //Track2
        i += LfUtils.memcpy(retbb, i, track2.getBytes(), 0, track2.getBytes().length);
        //分隔符
        i += LfUtils.memcpy(retbb, i, FS, 0, FS.length);
        //Track3
        i += LfUtils.memcpy(retbb, i, track3.getBytes(), 0, track3.getBytes().length);
        //分隔符
        i += LfUtils.memcpy(retbb, i, FS, 0, FS.length);

        //Track3
        i += LfUtils.memcpy(retbb, i, clientNo.getBytes(), 0, clientNo.getBytes().length);
        //分隔符
        i += LfUtils.memcpy(retbb, i, FS, 0, FS.length);
        //Track3
        i += LfUtils.memcpy(retbb, i, operator.getBytes(), 0, operator.getBytes().length);
        //分隔符
        i += LfUtils.memcpy(retbb, i, FS, 0, FS.length);
        //Track3
        i += LfUtils.memcpy(retbb, i, serialNo.getBytes(), 0, serialNo.getBytes().length);
        //分隔符
        i += LfUtils.memcpy(retbb, i, FS, 0, FS.length);

        //拷贝数据
        i += LfUtils.memcpy(retbb, i, AreaCode.getBytes(), 0, AreaCode.getBytes().length);
        //分隔符
        i += LfUtils.memcpy(retbb, i, FS, 0, FS.length);

        //拷贝数据
        i += LfUtils.memcpy(retbb, i, IndustryCode.getBytes(), 0, IndustryCode.getBytes().length);
        //分隔符
        i += LfUtils.memcpy(retbb, i, FS, 0, FS.length);

        //拷贝数据
        i += LfUtils.memcpy(retbb, i, ContentCode.getBytes(), 0, ContentCode.getBytes().length);
        //分隔符
        i += LfUtils.memcpy(retbb, i, FS, 0, FS.length);

        //拷贝数据
        i += LfUtils.memcpy(retbb, i, UserNo.getBytes(), 0, UserNo.getBytes().length);
        //分隔符
        i += LfUtils.memcpy(retbb, i, FS, 0, FS.length);

        //拷贝数据
        i += LfUtils.memcpy(retbb, i, PreQueryReply, 0, PreQueryReply.length);
        //分隔符
        i += LfUtils.memcpy(retbb, i, FS, 0, FS.length);


        if (i > 0 && i < retbb.length) {
            retbb2 = new byte[i];
            LfUtils.memcpy(retbb2, 0, retbb, 0, i);
        }
        return retbb2;
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
        if (operator.getBytes().length > 15) {
            throw new LfException(-8, "operator length error, need <=15");
        }
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


    public String getAreaCode() {
        return AreaCode;
    }

    public void setAreaCode(String areaCode) throws LfException {
        if (areaCode == null || areaCode.getBytes().length > 4) {
            throw new LfException(-8, "areaCode length error, need <=4");
        }
        AreaCode = areaCode;
    }

    public String getIndustryCode() {
        return IndustryCode;
    }

    public void setIndustryCode(String industryCode) throws LfException {
        if (industryCode == null || industryCode.getBytes().length > 3) {
            throw new LfException(-8, "industryCode length error, need <=3");
        }
        IndustryCode = industryCode;
    }

    public String getContentCode() {
        return ContentCode;
    }

    public void setContentCode(String contentCode) throws LfException {
        if (contentCode == null || contentCode.getBytes().length > 3) {
            throw new LfException(-8, "contentCode length error, need <=3");
        }
        ContentCode = contentCode;
    }

    public String getUserNo() {
        return UserNo;
    }

    public void setUserNo(String userNo) throws LfException {
        if (userNo == null || userNo.getBytes().length > 20) {
            throw new LfException(-8, "userNo length error, need <=20");
        }
        UserNo = userNo;
    }

    public byte[] getPreQueryReply() {
        return PreQueryReply;
    }

    public void setPreQueryReply(byte[] preQueryReply) throws LfException {
        if (preQueryReply == null || preQueryReply.length > 512) {
            throw new LfException(-8, "preQueryReply length error, need <=512");
        }
        PreQueryReply = preQueryReply;
    }
//    public void setPreQueryReply(String preQueryReply) throws LfException {
//        try {
//            if (preQueryReply == null || preQueryReply.getBytes("GBK").length > 512) {
//                throw new LfException(-8, "preQueryReply length error, need <=512");
//            }
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//            throw new LfException(-8, "preQueryReply getBytes(\"GBK\") error");
//        }
//        PreQueryReply = preQueryReply;
//    }
}
