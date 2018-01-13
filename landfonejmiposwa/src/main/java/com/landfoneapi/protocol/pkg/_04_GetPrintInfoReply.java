package com.landfoneapi.protocol.pkg;

import com.landfoneapi.mispos.MISPOS;
import com.landfoneapi.mispos.UART_PROTOCOL;
import com.landfone.common.utils.LfUtils;

public class _04_GetPrintInfoReply extends REPLY {

    private TradeType reply_type = TradeType.NONE;//指令代码
    private String mer = "";//商户代码
    private String tmn = "";//终端号
    private String mer_cn_name = "";//商户中文名
    private String mer_en_name = "";//商户英文名

    private String bankCode = "";//收单行标识码
    private String cardCode = "";//发卡行标识码
    private String posCenterCode = "";//POS中心标识码
    private String cardNo = "";//卡号
    private String operatorNo = "";//操作员号
    private String preTradeType = "";//原交易类型
    private String cardExp = "";//卡有效期
    private String transacionBatchNo = "";//交易批次号
    private String transacionVoucherNo = "";//原交易类型
    private String transacionDatetime = "";//交易日期和时间
    private String transacionAuthCode = "";//授权码
    private String transacionReferNo = "";//参考号
    private String transacionAmount = "";//交易金额
    private String TipAmount = "";//小费金额
    private String CumAmount = "";//累计金额
    private String TotalAmount = "";//总金额
    private String InterCreditCode = "";//国际信用卡公司代码
    private String ICTransCert = "";//IC卡交易证书
    private String TVR = "";//TVR
    private String TSI = "";//TSI
    private String AID = "";//AID
    private String ATC = "";//ATC
    private String appLabels = "";//应用标签
    private String appPreferredName = "";//应用首选名称
    private String TAC = "";//TAC
    private String DeducAmount = "";//扣持卡人金额
    private String IsOffline = "";//是否脱机交易
    private String InputMode = "";//输入模式

    public int Unpack(UART_PROTOCOL ptOutPara) {
        int i = 0, j = 0;
        int tmpsize = 0;
        byte[] tail = null;
        byte[] head = null;
        byte[] tmp = null;
        i = super.Unpack(ptOutPara);
        if (ptOutPara.data[0] == MISPOS.PACK_FS) {
            tmpsize += 1;
            head = new byte[1];
            head[0] = (byte) '0';
        }
        if (ptOutPara.datalen > 1
                && ptOutPara.data[ptOutPara.datalen - 1] == MISPOS.PACK_FS) {//尾部的分隔符
            tmpsize += 2;
            tail = new byte[2];
            tail[0] = '0';
            tail[1] = 0;
        }
        tmp = new byte[ptOutPara.datalen + tmpsize];
        if (head != null) {
            tmp[0] = head[0];
            j++;
        }
        dbg_mPrintf("tmpsize:" + tmpsize);
        if (tail != null) {
            tmp[j + ptOutPara.datalen + 0] = tail[0];
            tmp[j + ptOutPara.datalen + 1] = tail[1];
        }
        LfUtils.memcpy(tmp, j, ptOutPara.data, 0, ptOutPara.datalen);
        for (i = 0; i < tmp.length; i++) {
            if (tmp[i] == MISPOS.PACK_FS) {
                tmp[i] = '#';
            } else if (tmp[i] == 0x00) {//避免split不完整
                tmp[i] = '0';
            }
        }
        tmp[j + ptOutPara.datalen + 1] = tail[1];//再次把‘0’变成0
        String tmpstr = newString(tmp);
        dbg_mPrintf("tmpstr:" + tmpstr);
        String[] tmpstrbb = null;
        if (tmpstr != null) {
            tmpstrbb = tmpstr.split("#");
        }
        if (tmpstrbb != null) {
            for (j = 0; j < tmpstrbb.length; j++) {
                dbg_mPrintf("%d, %s", j, tmpstrbb[j] == null ? "" : tmpstrbb[j]);
            }
            i = 0;
            if (tmpstrbb.length > i) {
                if (tmpstrbb[i] != null && tmpstrbb[i].equals("61")) {
                    this.reply_type = TradeType.GET_PRINT_INFO;//指令代码
                }
            }
            i++;
            if (tmpstrbb.length > i) {
                this.mer = tmpstrbb[i];//商户代码
            }
            i++;
            if (tmpstrbb.length > i) {
                this.tmn = tmpstrbb[i];//终端号
            }
            i++;
            if (tmpstrbb.length > i) {
                this.mer_cn_name = tmpstrbb[i];//商户中文名
            }
            i++;
            if (tmpstrbb.length > i) {
                this.mer_en_name = tmpstrbb[i];//商户英文名
            }
            i++;
            if (tmpstrbb.length > i) {
                this.bankCode = tmpstrbb[i];//收单行标识码
            }
            i++;
            if (tmpstrbb.length > i) {
                this.cardCode = tmpstrbb[i];//发卡行标识码
            }
            i++;
            if (tmpstrbb.length > i) {
                this.posCenterCode = tmpstrbb[i];//POS中心标识码
            }
            i++;
            if (tmpstrbb.length > i) {
                this.cardNo = tmpstrbb[i];//卡号
            }
            i++;

            if (tmpstrbb.length > i) {
                operatorNo = tmpstrbb[i];
            }
            i++;//操作员号
            if (tmpstrbb.length > i) {
                preTradeType = tmpstrbb[i];
            }
            i++;//原交易类型
            if (tmpstrbb.length > i) {
                cardExp = tmpstrbb[i];
            }
            i++;//卡有效期
            if (tmpstrbb.length > i) {
                transacionBatchNo = tmpstrbb[i];
            }
            i++;//交易批次号
            if (tmpstrbb.length > i) {
                transacionVoucherNo = tmpstrbb[i];
            }
            i++;//原交易类型
            if (tmpstrbb.length > i) {
                transacionDatetime = tmpstrbb[i];
            }
            i++;//交易日期和时间
            if (tmpstrbb.length > i) {
                transacionAuthCode = tmpstrbb[i];
            }
            i++;//授权码
            if (tmpstrbb.length > i) {
                transacionReferNo = tmpstrbb[i];
            }
            i++;//参考号
            if (tmpstrbb.length > i) {
                transacionAmount = tmpstrbb[i];
            }
            i++;//交易金额
            if (tmpstrbb.length > i) {
                TipAmount = tmpstrbb[i];
            }
            i++;//小费金额
            if (tmpstrbb.length > i) {
                CumAmount = tmpstrbb[i];
            }
            i++;//累计金额
            if (tmpstrbb.length > i) {
                TotalAmount = tmpstrbb[i];
            }
            i++;//总金额
            if (tmpstrbb.length > i) {
                InterCreditCode = tmpstrbb[i];
            }
            i++;//国际信用卡公司代码
            if (tmpstrbb.length > i) {
                ICTransCert = tmpstrbb[i];
            }
            i++;//IC卡交易证书
            if (tmpstrbb.length > i) {
                TVR = tmpstrbb[i];
            }
            i++;//TVR
            if (tmpstrbb.length > i) {
                TSI = tmpstrbb[i];
            }
            i++;//TSI
            if (tmpstrbb.length > i) {
                AID = tmpstrbb[i];
            }
            i++;//AID
            if (tmpstrbb.length > i) {
                ATC = tmpstrbb[i];
            }
            i++;//ATC
            if (tmpstrbb.length > i) {
                appLabels = tmpstrbb[i];
            }
            i++;//应用标签
            if (tmpstrbb.length > i) {
                appPreferredName = tmpstrbb[i];
            }
            i++;//应用首选名称
            if (tmpstrbb.length > i) {
                TAC = tmpstrbb[i];
            }
            i++;//TAC
            if (tmpstrbb.length > i) {
                DeducAmount = tmpstrbb[i];
            }
            i++;//扣持卡人金额
            if (tmpstrbb.length > i) {
                IsOffline = tmpstrbb[i];
            }
            i++;//是否脱机交易
            if (tmpstrbb.length > i) {
                InputMode = tmpstrbb[i];
            }
            i++;//输入模式
        }
        return i;
    }

    public void setMer(String mer) {
        this.mer = mer;
    }


    public void setTmn(String tmn) {
        this.tmn = tmn;
    }


    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public void setTransacionBatchNo(String transacionBatchNo) {
        this.transacionBatchNo = transacionBatchNo;
    }

    public void setTransacionVoucherNo(String transacionVoucherNo) {
        this.transacionVoucherNo = transacionVoucherNo;
    }

    public void setTransacionDatetime(String transacionDatetime) {
        this.transacionDatetime = transacionDatetime;
    }

    public void setTransacionAmount(String transacionAmount) {
        this.transacionAmount = transacionAmount;
    }


    public String getMer() {
        return mer;
    }

    public String getTmn() {
        return tmn;
    }

    public String getMer_cn_name() {
        return mer_cn_name;
    }

    public String getMer_en_name() {
        return mer_en_name;
    }

    public String getBankCode() {
        return bankCode;
    }

    public String getCardCode() {
        return cardCode;
    }

    public String getPosCenterCode() {
        return posCenterCode;
    }

    public String getCardNo() {
        return cardNo;
    }

    public String getOperatorNo() {
        return operatorNo;
    }

    public String getPreTradeType() {
        return preTradeType;
    }

    public String getCardExp() {
        return cardExp;
    }

    public String getTransacionBatchNo() {
        return transacionBatchNo;
    }

    public String getTransacionVoucherNo() {
        return transacionVoucherNo;
    }

    public String getTransacionDatetime() {
        return transacionDatetime;
    }

    public String getTransacionAuthCode() {
        return transacionAuthCode;
    }

    public String getTransacionReferNo() {
        return transacionReferNo;
    }

    public String getTransacionAmount() {
        return transacionAmount;
    }

    public String getTipAmount() {
        return TipAmount;
    }

    public String getCumAmount() {
        return CumAmount;
    }

    public String getTotalAmount() {
        return TotalAmount;
    }

    public String getInterCreditCode() {
        return InterCreditCode;
    }

    public String getICTransCert() {
        return ICTransCert;
    }

    public String getTVR() {
        return TVR;
    }

    public String getTSI() {
        return TSI;
    }

    public String getAID() {
        return AID;
    }

    public String getATC() {
        return ATC;
    }

    public String getAppLabels() {
        return appLabels;
    }

    public String getAppPreferredName() {
        return appPreferredName;
    }

    public String getTAC() {
        return TAC;
    }

    public String getDeducAmount() {
        return DeducAmount;
    }

    public String getIsOffline() {
        return IsOffline;
    }

    public String getInputMode() {
        return InputMode;
    }

    public TradeType getReply_type() {
        return reply_type;
    }

    public void setReply_type(TradeType reply_type) {
        this.reply_type = reply_type;
    }
}
