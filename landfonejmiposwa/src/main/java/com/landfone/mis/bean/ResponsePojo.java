package com.landfone.mis.bean;

import android.util.Log;


/**
 * Created by asus on 2017/3/16.
 */

public class ResponsePojo {
    private String TAG = this.getClass().getSimpleName();

    private String rspCode;
    private String rspChin;
    private String cardNo;
    private String amount;
    private String bankCode;
    private String bankName;
    private String traceNo;
    private String batchNo;
    private String refNo;
    private String authNo;
    private String settleDate;
    private String merchId;
    private String termId;
    private String transDate;
    private String transTime;
    private String transMemo;

    public ResponsePojo() {
        this.rspCode = "";
        this.rspChin = "";
        this.cardNo = "";
        this.amount = "";
        this.bankCode = "";
        this.bankName = "";
        this.traceNo = "";
        this.batchNo = "";
        this.refNo = "";
        this.authNo = "";
        this.settleDate = "";
        this.merchId = "";
        this.termId = "";
        this.transDate = "";
        this.transTime = "";
        this.transMemo = "";
    }


    public String getRspCode() {
        return rspCode;
    }

    public void setRspCode(String rspCode) {
        this.rspCode = rspCode;
    }

    public String getRspChin() {
        return rspChin;
    }

    public void setRspChin(String rspChin) {
        this.rspChin = rspChin;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getTraceNo() {
        return traceNo;
    }

    public void setTraceNo(String traceNo) {
        this.traceNo = traceNo;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getRefNo() {
        return refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    public String getAuthNo() {
        return authNo;
    }

    public void setAuthNo(String authNo) {
        this.authNo = authNo;
    }

    public String getSettleDate() {
        return settleDate;
    }

    public void setSettleDate(String settleDate) {
        this.settleDate = settleDate;
    }

    public String getMerchId() {
        return merchId;
    }

    public void setMerchId(String merchId) {
        this.merchId = merchId;
    }

    public String getTermId() {
        return termId;
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }

    public String getTransDate() {
        return transDate;
    }

    public void setTransDate(String transDate) {
        this.transDate = transDate;
    }

    public String getTransTime() {
        return transTime;
    }

    public void setTransTime(String transTime) {
        this.transTime = transTime;
    }

    public String getTransMemo() {
        return transMemo;
    }

    public void setTransMemo(String transMemo) {
        this.transMemo = transMemo;
    }

    @Override
    public String toString() {
        String ret = getRspCode() + " " + getRspChin()
                + (getCardNo().equals("") ? "" : " 卡号:" + getCardNo()) + (getAmount().equals("") ? "" : " 交易金额:" + getAmount())
                + (getBankCode().equals("") ? "" : " 发卡行代码:" + getBankCode()) + (getBankName().equals("") ? "" : " 发卡行:" + getBankName())
                + (getTraceNo().equals("") ? "" : " 交易凭证号:" + getTraceNo()) + (getBatchNo().equals("") ? "" : " 交易批次号:" + getBatchNo())
                + (getRefNo().equals("") ? "" : " 系统参考号:" + getRefNo()) + (getAuthNo().equals("") ? "" : " 授权号:" + getAuthNo())
                + (getSettleDate().equals("") ? "" : " 清算日期:" + getSettleDate()) + (getMerchId().equals("") ? "" : " 商户号:" + getMerchId())
                + (getTermId().equals("") ? "" : " 终端号:" + getTermId()) + (getTransDate().equals("") ? "" : " 交易日期时间:" + getTransDate())
                + (getTransMemo().equals("") ? "" : "" + getTransMemo());
//        Logz.d(TAG, "getTransMemo" + getTransMemo());
        return ret;
    }
}
