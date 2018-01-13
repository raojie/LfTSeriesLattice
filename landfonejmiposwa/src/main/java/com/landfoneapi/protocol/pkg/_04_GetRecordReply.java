package com.landfoneapi.protocol.pkg;

import com.landfoneapi.mispos.MISPOS;
import com.landfoneapi.mispos.UART_PROTOCOL;
import com.landfone.common.utils.LfUtils;

/**
 * Created by thinkpad on 2015/9/30.
 */
public class _04_GetRecordReply extends REPLY {

    private TradeType reply_type = TradeType.NONE;//1指令代码
    private String mer = "";//2商户代码
    private String tmn = "";//3终端号
    private String mer_cn_name = "";//4商户中文名
    private String mer_en_name = "";//5商户英文名

    private String bankCode = "";//6收单行标识码
    private String cardCode = "";//7发卡行标识码
    private String posCenterCode = "";//8POS中心标识码
    private String cardNo = "";//9卡号
    private String operatorNo = "";//10操作员号
    private String preTradeType = "";//11原交易类型
    private String cardExp = "";//12卡有效期
    private String transacionBatchNo = "";//13交易批次号
    private String transacionVoucherNo = "";//14交易凭证号
    private String transacionDatetime = "";//15交易日期和时间
    private String transacionAuthCode = "";//16授权码
    private String transacionReferNo = "";//17参考号
    private String transacionAmount = "";//18交易金额
    private String TipAmount = "";//19小费金额
    private String CumAmount = "";//20累计金额
    private String TotalAmount = "";//21总金额
    private String InterCreditCode = "";//22国际信用卡公司代码
    private String ICTransCert = "";//23IC卡交易证书
    private String TVR = "";//24TVR
    private String TSI = "";//25TSI
    private String AID = "";//26AID
    private String ATC = "";//2ATC
    private String appLabels = "";//28应用标签
    private String appPreferredName = "";//29应用首选名称
    private String TAC = "";//30TAC
    private String DeducAmount = "";//31扣持卡人金额
    private String IsOffline = "";//32是否脱机交易
    private String InputMode = "";//33输入模式

    private String Remark = "";//34备注信息
    private String SpecInfoField = "";//35特定信息域
    private String PayEntryNo = "";//36款台号
    //    private String operatorNo="";//37操作员号
    private String PosWaterNo = "";//38收银流水号

    private String settleDate = "";//39清算日期
    private String unpredictableNum = "";//40不可预知数
    private String appComuFeatures;//41交互特征
    private String terminalPerformance;//42终端性能
    private String cardBankAppData;//43发卡行应用数据
    private String cardSerial;  //44卡片序列号
    private String cardOwnerCertRst;//45持卡人验证结果
    private String telehone;//46手机号
    private String QrCodeUrl;//47二维码URL
    private String billCode;//48商户账单号
    private String billTime; //49账单时间
    private String tradeState;//50交易状态
    private String lastChkTime;//51上次查询时间
    private String transType;// 52交易类型

    private String card_no;//53卡号(16)
    private String transBalanceBefore;//54交易前余额(12)
    private String transSerialNo;//55交易流水号(6)
    private String scatteredData;//56分散数据(16)
    private String debitAmount;//57扣款金额(12)
    private String typeMark;//58类型标志(2)
    private String terminalNo;//59终端编号(6)
    private String transactionNo;//60交易序号(8)
    private String transactionDate;//61交易日期(8)
    private String transactionTime;//62交易时间(6)
    private String TC;//63TC(8)
    private String cardRechargeSerialNo;//64卡充值流水号(16)
    private String Issuer;//65发卡方(6)
    private String consumptionAmount;//66消费金额(12)
    private String cardCount;//67卡计数次数(8)
    private String cardTypeMark;//68卡类型标识(4)
    private String transBalanceAfter;//69交易后余额(12)
    private String clerkNo;//70柜员号(6)
    private String transactionType; //71交易类型(2)
    private String recoveryCardMark;//72回收卡标识(1)
    private String testCardMark;//73测试卡标识(1)
    private String cardType;//74卡类型(2)
    private String totalTransCount;//75交易总笔数(4)
    private String totalTransAmount;//76交易总金额(12)
    private String sandCardPreField1;//77杉德会员卡预留1(12)
    private String sandCardPreField2;//78杉德会员卡预留2(12)
    private String sandCardPreField3;//79杉德会员卡预留3(12)
    private String posOrderNo;//80POS订单号(20)
    private String thirdPartyAccessOrderNo;//81第三方接入订单号(64)
    private String transVerificationCode;//82交易验证码(64)
    private String backstageTransNo;//83后台交易号(64)
    private String userPaymentAcocunt;//84用户支付账户(64)
    private String amountDetail;//85金额明细(90)
    private String orderResult;//86订单结果(2)
    private String APP_ID;//87APP_ID(15)
    private String paycode;//88付款码(20)

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
        if (ptOutPara.datalen > 1 && ptOutPara.data[ptOutPara.datalen - 1] == MISPOS.PACK_FS) {//尾部的分隔符
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
            i = 0;
            if (tmpstrbb.length > i) {
                if (tmpstrbb[i] != null && tmpstrbb[i].equals("69")) {
                    this.reply_type = TradeType.GET_RECORD;//指令代码
                }
            }
            i++;
            if (tmpstrbb.length > i) {
                this.setMer(tmpstrbb[i]);//2商户代码
            }
            i++;
            if (tmpstrbb.length > i) {
                this.setTmn(tmpstrbb[i]);//3终端号
            }
            i++;
            if (tmpstrbb.length > i) {
                this.mer_cn_name = tmpstrbb[i];//4商户中文名
            }
            i++;
            if (tmpstrbb.length > i) {
                this.mer_en_name = tmpstrbb[i];//5商户英文名
            }
            i++;
            if (tmpstrbb.length > i) {
                this.bankCode = tmpstrbb[i];//6收单行标识码
            }
            i++;
            if (tmpstrbb.length > i) {
                this.cardCode = tmpstrbb[i];//7发卡行标识码
            }
            i++;
            if (tmpstrbb.length > i) {
                this.posCenterCode = tmpstrbb[i];//8POS中心标识码
            }
            i++;
            if (tmpstrbb.length > i) {
                this.setCardNo(tmpstrbb[i]);//9卡号
            }
            i++;
            if (tmpstrbb.length > i) {
                operatorNo = tmpstrbb[i];//10操作员号
            }
            i++;
            if (tmpstrbb.length > i) {
                preTradeType = tmpstrbb[i];//11原交易类型
            }
            i++;
            if (tmpstrbb.length > i) {
                cardExp = tmpstrbb[i];//12卡有效期
            }
            i++;
            if (tmpstrbb.length > i) {
                setTransacionBatchNo(tmpstrbb[i]);//13交易批次号
            }
            i++;
            if (tmpstrbb.length > i) {
                setTransacionVoucherNo(tmpstrbb[i]);//14原交易类型
            }
            i++;
            if (tmpstrbb.length > i) {
                setTransacionDatetime(tmpstrbb[i]);//15交易日期和时间
            }
            i++;
            if (tmpstrbb.length > i) {
                transacionAuthCode = tmpstrbb[i];//16授权码
            }
            i++;
            if (tmpstrbb.length > i) {
                transacionReferNo = tmpstrbb[i];//17参考号
            }
            i++;
            if (tmpstrbb.length > i) {
                setTransacionAmount(tmpstrbb[i]);//18交易金额
            }
            i++;
            if (tmpstrbb.length > i) {
                TipAmount = tmpstrbb[i];//19小费金额
            }
            i++;
            if (tmpstrbb.length > i) {
                CumAmount = tmpstrbb[i];//20累计金额
            }
            i++;
            if (tmpstrbb.length > i) {
                TotalAmount = tmpstrbb[i];//21总金额
            }
            i++;
            if (tmpstrbb.length > i) {
                InterCreditCode = tmpstrbb[i];//22国际信用卡公司代码
            }
            i++;
            if (tmpstrbb.length > i) {
                ICTransCert = tmpstrbb[i];//23IC卡交易证书
            }
            i++;
            if (tmpstrbb.length > i) {
                TVR = tmpstrbb[i];//24TVR
            }
            i++;
            if (tmpstrbb.length > i) {
                TSI = tmpstrbb[i];//25TSI
            }
            i++;
            if (tmpstrbb.length > i) {
                AID = tmpstrbb[i];//26AID
            }
            i++;
            if (tmpstrbb.length > i) {
                ATC = tmpstrbb[i];//27ATC
            }
            i++;
            if (tmpstrbb.length > i) {
                appLabels = tmpstrbb[i];//28应用标签
            }
            i++;
            if (tmpstrbb.length > i) {
                appPreferredName = tmpstrbb[i];//29应用首选名称
            }
            i++;
            if (tmpstrbb.length > i) {
                TAC = tmpstrbb[i];//30TAC
            }
            i++;
            if (tmpstrbb.length > i) {
                DeducAmount = tmpstrbb[i];//31扣持卡人金额
            }
            i++;
            if (tmpstrbb.length > i) {
                IsOffline = tmpstrbb[i];//32是否脱机交易
            }
            i++;
            if (tmpstrbb.length > i) {
                InputMode = tmpstrbb[i];//33输入模式
            }
            i++;

            if (tmpstrbb.length > i) {
                Remark = tmpstrbb[i];//34备注信息
            }
            i++;
            if (tmpstrbb.length > i) {
                SpecInfoField = tmpstrbb[i];//35特定信息域
            }
            i++;
            if (tmpstrbb.length > i) {
                PayEntryNo = tmpstrbb[i];//36款台号
            }
            i++;
            if (tmpstrbb.length > i) {
                operatorNo = tmpstrbb[i];//37操作员号
            }
            i++;
            if (tmpstrbb.length > i) {
                setPosWaterNo(tmpstrbb[i]);//38收银流水号
            }
            i++;
            if (tmpstrbb.length > i) {
                settleDate = tmpstrbb[i];//39清算日期
            }
            i++;
            if (tmpstrbb.length > i) {
                unpredictableNum = tmpstrbb[i];//40不可预知数
            }
            i++;
            if (tmpstrbb.length > i) {
                appComuFeatures = tmpstrbb[i];//41应用交互特征
            }
            i++;
            if (tmpstrbb.length > i) {
                terminalPerformance = tmpstrbb[i];//42终端性能
            }
            i++;
            if (tmpstrbb.length > i) {
                cardBankAppData = tmpstrbb[i];//43发卡行应用数据
            }
            i++;
            if (tmpstrbb.length > i) {
                cardSerial = tmpstrbb[i];//44卡片序列号
            }
            i++;
            if (tmpstrbb.length > i) {
                cardOwnerCertRst = tmpstrbb[i];//45持卡人验证结果
            }
            i++;
            if (tmpstrbb.length > i) {
                telehone = tmpstrbb[i];//46手机号
            }
            i++;
            if (tmpstrbb.length > i) {
                QrCodeUrl = tmpstrbb[i];//47二维码URL
            }
            i++;
            if (tmpstrbb.length > i) {
                billCode = tmpstrbb[i];//48商户账单号
            }
            i++;
            if (tmpstrbb.length > i) {
                billTime = tmpstrbb[i];//49账单时间
            }
            i++;
            if (tmpstrbb.length > i) {
                tradeState = tmpstrbb[i];//50交易状态
            }
            i++;
            if (tmpstrbb.length > i) {
                lastChkTime = tmpstrbb[i];//51上次查询时间
            }
            i++;
            if (tmpstrbb.length > i) {
                transType = tmpstrbb[i];//52交易类型
            }
            i++;
            if (tmpstrbb.length > i) {
                card_no = tmpstrbb[i];//53卡号
            }
            i++;
            if (tmpstrbb.length > i) {
                transBalanceBefore = tmpstrbb[i];//54交易前余额
            }
            i++;
            if (tmpstrbb.length > i) {
                transSerialNo = tmpstrbb[i];//55交易流水号
            }
            i++;
            if (tmpstrbb.length > i) {
                scatteredData = tmpstrbb[i];//56分散数据
            }
            i++;
            if (tmpstrbb.length > i) {
                debitAmount = tmpstrbb[i];//57扣款金额
            }
            i++;
            if (tmpstrbb.length > i) {
                typeMark = tmpstrbb[i];//58类型表识
            }
            i++;
            if (tmpstrbb.length > i) {
                terminalNo = tmpstrbb[i];//59终端编号
            }
            i++;
            if (tmpstrbb.length > i) {
                transactionNo = tmpstrbb[i];//60交易序号
            }
            i++;
            if (tmpstrbb.length > i) {
                transactionDate = tmpstrbb[i];//61交易日期
            }
            i++;
            if (tmpstrbb.length > i) {
                transactionTime = tmpstrbb[i];//62交易时间
            }
            i++;
            if (tmpstrbb.length > i) {
                TC = tmpstrbb[i];//63TC
            }
            i++;
            if (tmpstrbb.length > i) {
                cardRechargeSerialNo = tmpstrbb[i];//64卡充值流水号
            }
            i++;
            if (tmpstrbb.length > i) {
                Issuer = tmpstrbb[i];//65发卡方
            }
            i++;
            if (tmpstrbb.length > i) {
                consumptionAmount = tmpstrbb[i];//66消费金额
            }
            i++;
            if (tmpstrbb.length > i) {
                cardCount = tmpstrbb[i];//67卡计数次数
            }
            i++;
            if (tmpstrbb.length > i) {
                cardTypeMark = tmpstrbb[i];//68卡类型标识
            }
            i++;
            if (tmpstrbb.length > i) {
                transBalanceAfter = tmpstrbb[i];//69交易后余额
            }
            i++;
            if (tmpstrbb.length > i) {
                clerkNo = tmpstrbb[i];//70柜员号
            }
            i++;
            if (tmpstrbb.length > i) {
                transactionType = tmpstrbb[i];//71交易类型
            }
            i++;
            if (tmpstrbb.length > i) {
                recoveryCardMark = tmpstrbb[i];//72回收卡标识
            }
            i++;
            if (tmpstrbb.length > i) {
                testCardMark = tmpstrbb[i];//73测试卡标识
            }
            i++;
            if (tmpstrbb.length > i) {
                cardType = tmpstrbb[i];//74卡类型
            }
            i++;
            if (tmpstrbb.length > i) {
                totalTransCount = tmpstrbb[i];//75交易总笔数
            }
            i++;
            if (tmpstrbb.length > i) {
                totalTransAmount = tmpstrbb[i];//76交易总金额
            }
            i++;
            if (tmpstrbb.length > i) {
                sandCardPreField1 = tmpstrbb[i];//77杉德会员卡预留1
            }
            i++;
            if (tmpstrbb.length > i) {
                sandCardPreField2 = tmpstrbb[i];//78杉德会员卡预留2
            }
            i++;
            if (tmpstrbb.length > i) {
                sandCardPreField3 = tmpstrbb[i];//79杉德会员卡预留3
            }
            i++;
            if (tmpstrbb.length > i) {
                posOrderNo = tmpstrbb[i];//80POS订单号
            }
            i++;
            if (tmpstrbb.length > i) {
                thirdPartyAccessOrderNo = tmpstrbb[i];//80第三方接入订单号
            }
            i++;
            if (tmpstrbb.length > i) {
                transVerificationCode = tmpstrbb[i];//82交易验证码
            }
            i++;
            if (tmpstrbb.length > i) {
                backstageTransNo = tmpstrbb[i];//83后台交易号
            }
            i++;
            if (tmpstrbb.length > i) {
                userPaymentAcocunt = tmpstrbb[i];//84用户支付账户
            }
            i++;
            if (tmpstrbb.length > i) {
                amountDetail = tmpstrbb[i];//85金额明细
            }
            i++;
            if (tmpstrbb.length > i) {
                orderResult = tmpstrbb[i];//86订单结果
            }
            i++;
            if (tmpstrbb.length > i) {
                APP_ID = tmpstrbb[i];//87APP_ID
            }
            i++;
            if (tmpstrbb.length > i) {
                paycode = tmpstrbb[i];//88paycode
            }
            i++;
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

    public void setPosWaterNo(String posWaterNo) {
        PosWaterNo = posWaterNo;
    }

    //////////////////////////////////////////
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

    public String getRemark() {
        return Remark;
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

    public String getSpecInfoField() {
        return SpecInfoField;
    }

    public String getPayEntryNo() {
        return PayEntryNo;
    }

    public String getPosWaterNo() {
        return PosWaterNo;
    }


    public String getSettleDate() {
        return settleDate;
    }

    public String getUnpredictableNum() {
        return unpredictableNum;
    }

    public String getAppComuFeatures() {
        return appComuFeatures;
    }

    public String getTerminalPerformance() {
        return terminalPerformance;
    }

    public String getCardBankAppData() {
        return cardBankAppData;
    }

    public String getCardSerial() {
        return cardSerial;
    }

    public String getCardOwnerCertRst() {
        return cardOwnerCertRst;
    }

    public String getTelphone() {
        return telehone;
    }

    public String getQrCodeUrl() {
        return QrCodeUrl;
    }

    public String getBillCode() {
        return billCode;
    }


    public String getBillTime() {
        return billTime;
    }

    public String getTradeState() {
        return tradeState;
    }

    public String getLastChkTime() {
        return lastChkTime;
    }

    public String getTransType() {
        return transType;
    }

    public String getCard_no() {
        return card_no;
    }

    public String getTransBalanceBefore() {
        return transBalanceBefore;
    }

    public String getTransSerialNo() {
        return transSerialNo;
    }

    public String getScatteredData() {
        return scatteredData;
    }

    public String getDebitAmount() {
        return debitAmount;
    }

    public String getTypeMark() {
        return typeMark;
    }

    public String getTerminalNo() {
        return terminalNo;
    }

    public String getTransactionNo() {
        return transactionNo;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public String getTransactionTime() {
        return transactionTime;
    }

    public String getTC() {
        return TC;
    }

    public String getCardRechargeSerialNo() {
        return cardRechargeSerialNo;
    }

    public String getIssuer() {
        return Issuer;
    }

    public String getConsumptionAmount() {
        return consumptionAmount;
    }

    public String getCardCount() {
        return cardCount;
    }

    public String getCardTypeMark() {
        return cardTypeMark;
    }

    public String getTransBalanceAfter() {
        return transBalanceAfter;
    }

    public String getClerkNo() {
        return clerkNo;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public String getRecoveryCardMark() {
        return recoveryCardMark;
    }

    public String getTestCardMark() {
        return testCardMark;
    }

    public String getCardType() {
        return cardType;
    }

    public String getTotalTransCount() {
        return totalTransCount;
    }

    public String getTotalTransAmount() {
        return totalTransAmount;
    }

    public String getSandCardPreField1() {
        return sandCardPreField1;
    }

    public String getSandCardPreField2() {
        return sandCardPreField2;
    }

    public String getSandCardPreField3() {
        return sandCardPreField3;
    }

    public String getPosOrderNo() {
        return posOrderNo;
    }

    public String getThirdPartyAccessOrderNo() {
        return thirdPartyAccessOrderNo;
    }

    public String getTransVerificationCode() {
        return transVerificationCode;
    }

    public String getBackstageTransNo() {
        return backstageTransNo;
    }

    public String getUserPaymentAcocunt() {
        return userPaymentAcocunt;
    }

    public String getAmountDetail() {
        return amountDetail;
    }

    public String getOrderResult() {
        return orderResult;
    }

    public String getAPP_ID() {
        return APP_ID;
    }

    public String getPaycode() {
        return paycode;
    }
}
