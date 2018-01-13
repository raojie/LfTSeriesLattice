package com.landfoneapi.protocol.pkg;

import com.landfoneapi.mispos.MISPOS;
import com.landfoneapi.mispos.UART_PROTOCOL;

import java.util.Arrays;

public class _04_PurchaseReply extends REPLY {

    private TradeType reply_type = TradeType.NONE;//指令代码
    //private String code = "";//指令代码
    private String mer = "";//商户代码
    private String tmn = "";//终端号
    private String mer_cn_name = "";//商户中文名
    private String mer_en_name = "";//商户英文名

    private int amount = 0;//金额,N12
    private String track2 = "";
    private String track3 = "";
    private String clientNo = "";
    private String operator = "";
    private String serialNo = "";

    private String goodsTotalNum = "";//商品总数
    private String goodsInfo = "";//商品信息

    //TODO:
    public void print() {
        super.print();
        dbg_mPrintf("reply_type(指令码):" + reply_type.getCode());
        dbg_mPrintf("mer:" + getMer());
        dbg_mPrintf("tmn:" + getTmn());
        dbg_mPrintf("mer_cn_name:" + mer_cn_name);
        dbg_mPrintf("mer_en_name:" + mer_en_name);
    }

    public int Unpack(UART_PROTOCOL ptOutPara) {
        String tmpStr = "";
        int i = 0, j = 0;
        int tmpsize = 0;
        byte[] tail = null;
        byte[] head = null;
        byte[] tmp = null;

        int fs_num = 0;
        int offset = 0;
        byte[] tmpArr = new byte[256];
        int attrID = 0;

        i = super.Unpack(ptOutPara);

        for (int k = 0; k < ptOutPara.datalen; k++) {
            if (ptOutPara.data[k] == MISPOS.PACK_FS) {//遇到一个分隔符
                if (j > 0) {
                    switch (attrID) {
                        case 1:
                            break;
                        case 2:
                            setMer(newString(tmpArr));
                            break;
                        case 3:
                            setTmn(newString(tmpArr));
                            break;
                        case 4:
                            mer_cn_name = newString(tmpArr);
                            break;
                        case 5:
                            mer_en_name = newString(tmpArr);
                            break;
                        case 6:
                            tmpStr = newString(tmpArr);
                            if (tmpStr == null) {
                                setAmount(0);
                            } else {
                                setAmount(Integer.parseInt(tmpStr));
                            }
                            break;
                        case 7:
                            track2 = newString(tmpArr);
                            break;
                        case 8:
                            track3 = newString(tmpArr);
                            break;
                        case 9:
                            setClientNo(newString(tmpArr));
                            break;
                        case 10:
                            setOperator(newString(tmpArr));
                            break;
                        case 11:
                            setSerialNo(newString(tmpArr));
                            break;
                    }
                }
                j = 0;
                Arrays.fill(tmpArr, (byte) 0x00);
                attrID++;
            }
            tmpArr[j++] = ptOutPara.data[k];
        }

        return i;
    }

    public String getMer() {
        return mer;
    }

    public void setMer(String mer) {
        this.mer = mer;
    }

    public String getTmn() {
        return tmn;
    }

    public void setTmn(String tmn) {
        this.tmn = tmn;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getClientNo() {
        return clientNo;
    }

    public void setClientNo(String clientNo) {
        this.clientNo = clientNo;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
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
