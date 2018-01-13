package com.landfone.mis.bean;

/**
 * Created by asus on 2017/3/16.
 */

public class RequestPojo {
    private String operId;
    private String posId;
    private String transType;
    private int amount;
    private String transMemo;

    public RequestPojo() {
    }

    public String getOperId() {
        return operId;
    }

    public void setOperId(String operId) {
        this.operId = operId;
    }

    public String getPosId() {
        return posId;
    }

    public void setPosId(String posId) {
        this.posId = posId;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getTransMemo() {
        return transMemo;
    }

    public void setTransMemo(String transMemo) {
        this.transMemo = transMemo;
    }

    @Override
    public String toString() {
        return "operId:" + getOperId() + " , " + "posId:" + getPosId() + " , "
                + "amount:" + getAmount() + " , " + "transType:" + getTransType() + " , "
                + "transMemo:" + getTransMemo();
    }
}
