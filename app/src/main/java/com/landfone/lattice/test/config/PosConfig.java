package com.landfone.lattice.test.config;

/**
 * Method: PosConfig
 * Decription:
 * Author: raoj
 * Date: 2017/12/28
 **/
public class PosConfig {

    private static final String TAG = "PosConfig";

    private PosConfig() {
    }

    public static final PosConfig getInstance() {
        return PosConfigHolder.INSTANCE;
    }

    /**
     * 静态内部类实现单例模式
     */
    private static class PosConfigHolder {
        private static final PosConfig INSTANCE = new PosConfig();
    }
    private boolean mightPos1Signing = false;//pos1是否正在签到
    private boolean mightPos2Signing = false;//pos2是否正在签到
    private boolean mightPos1Signed = false;//pos1是否签到成功
    private boolean mightPos2Signed = false;//pos2是否签到成功
    private boolean mightPos1SignSerialTimeOut = false;//pos1签到是否串口超时
    private boolean mightPos2SignSerialTimeOut = false;//pos2签到是否串口超时
    private boolean mightPos1SignedBusy = false;//pos1签到是否接口忙
    private boolean mightPos2SignedBusy = false;//pos2签到是否接口忙
    private boolean mightPos1Paying = false;//pos1是否正在消费
    private boolean mightPos2Paying = false;//pos2是否正在消费
    private boolean mightPos1PayBusy = false;//pos1消费接口忙
    private boolean mightPos2PayBusy = false;//pos2消费接口忙
    private boolean mightPos1ReadCard = false;//pos1读到卡
    private boolean mightPos2ReadCard = false;//pos2读到卡
    private boolean mightPos1PlaySound = false;//pos1播放语音
    private boolean mightPos2PlaySound = false;//pos2播放语音
    private boolean mightCheckPos1Signing = false;//是否正在检测pos1签到状态
    private boolean mightCheckPos2Signing = false;//是否正在检测pos2签到状态
    private boolean mightCheckPos1Paying = false;//是否正在检测pos1消费接口状态
    private boolean mightCheckPos2Paying = false;//是否正在检测pos2消费接口状态
    private boolean mightPos1ReturnPayResult = false;//pos1消费是否有结果返回
    private boolean mightPos2ReturnPayResult = false;//pos2消费是否有结果返回
    private boolean mightPos1HandOK = false;//pos1握手成功
    private boolean mightPos2HandOK = false;//pos2握手成功

    public boolean isMightPos1HandOK() {
        return mightPos1HandOK;
    }

    public void setMightPos1HandOK(boolean mightPos1HandOK) {
        this.mightPos1HandOK = mightPos1HandOK;
    }

    public boolean isMightPos2HandOK() {
        return mightPos2HandOK;
    }

    public void setMightPos2HandOK(boolean mightPos2HandOK) {
        this.mightPos2HandOK = mightPos2HandOK;
    }

    public boolean isMightPos1ReturnPayResult() {
        return mightPos1ReturnPayResult;
    }

    public void setMightPos1ReturnPayResult(boolean mightPos1ReturnPayResult) {
        this.mightPos1ReturnPayResult = mightPos1ReturnPayResult;
    }

    public boolean isMightPos2ReturnPayResult() {
        return mightPos2ReturnPayResult;
    }

    public void setMightPos2ReturnPayResult(boolean mightPos2ReturnPayResult) {
        this.mightPos2ReturnPayResult = mightPos2ReturnPayResult;
    }

    public boolean isMightCheckPos1Paying() {
        return mightCheckPos1Paying;
    }

    public void setMightCheckPos1Paying(boolean mightCheckPos1Paying) {
        this.mightCheckPos1Paying = mightCheckPos1Paying;
    }

    public boolean isMightCheckPos2Paying() {
        return mightCheckPos2Paying;
    }

    public void setMightCheckPos2Paying(boolean mightCheckPos2Paying) {
        this.mightCheckPos2Paying = mightCheckPos2Paying;
    }

    public boolean isMightCheckPos1Signing() {
        return mightCheckPos1Signing;
    }

    public void setMightCheckPos1Signing(boolean mightCheckPos1Signing) {
        this.mightCheckPos1Signing = mightCheckPos1Signing;
    }

    public boolean isMightCheckPos2Signing() {
        return mightCheckPos2Signing;
    }

    public void setMightCheckPos2Signing(boolean mightCheckPos2Signing) {
        this.mightCheckPos2Signing = mightCheckPos2Signing;
    }

    public boolean isMightPos1PlaySound() {
        return mightPos1PlaySound;
    }

    public void setMightPos1PlaySound(boolean mightPos1PlaySound) {
        this.mightPos1PlaySound = mightPos1PlaySound;
    }

    public boolean isMightPos2PlaySound() {
        return mightPos2PlaySound;
    }

    public void setMightPos2PlaySound(boolean mightPos2PlaySound) {
        this.mightPos2PlaySound = mightPos2PlaySound;
    }

    public boolean isMightPos1ReadCard() {
        return mightPos1ReadCard;
    }

    public void setMightPos1ReadCard(boolean mightPos1ReadCard) {
        this.mightPos1ReadCard = mightPos1ReadCard;
    }

    public boolean isMightPos2ReadCard() {
        return mightPos2ReadCard;
    }

    public void setMightPos2ReadCard(boolean mightPos2ReadCard) {
        this.mightPos2ReadCard = mightPos2ReadCard;
    }

    public boolean isMightPos1PayBusy() {
        return mightPos1PayBusy;
    }

    public void setMightPos1PayBusy(boolean mightPos1PayBusy) {
        this.mightPos1PayBusy = mightPos1PayBusy;
    }

    public boolean isMightPos2PayBusy() {
        return mightPos2PayBusy;
    }

    public void setMightPos2PayBusy(boolean mightPos2PayBusy) {
        this.mightPos2PayBusy = mightPos2PayBusy;
    }

    public boolean isMightPos1Paying() {
        return mightPos1Paying;
    }

    public void setMightPos1Paying(boolean mightPos1Paying) {
        this.mightPos1Paying = mightPos1Paying;
    }

    public boolean isMightPos2Paying() {
        return mightPos2Paying;
    }

    public void setMightPos2Paying(boolean mightPos2Paying) {
        this.mightPos2Paying = mightPos2Paying;
    }

    public boolean isMightPos1Signing() {
        return mightPos1Signing;
    }

    public void setMightPos1Signing(boolean mightPos1Signing) {
        this.mightPos1Signing = mightPos1Signing;
    }

    public boolean isMightPos2Signing() {
        return mightPos2Signing;
    }

    public void setMightPos2Signing(boolean mightPos2Signing) {
        this.mightPos2Signing = mightPos2Signing;
    }

    public boolean isMightPos1SignedBusy() {
        return mightPos1SignedBusy;
    }

    public void setMightPos1SignedBusy(boolean mightPos1SignedBusy) {
        this.mightPos1SignedBusy = mightPos1SignedBusy;
    }

    public boolean isMightPos2SignedBusy() {
        return mightPos2SignedBusy;
    }

    public void setMightPos2SignedBusy(boolean mightPos2SignedBusy) {
        this.mightPos2SignedBusy = mightPos2SignedBusy;
    }

    public boolean isMightPos1SignSerialTimeOut() {
        return mightPos1SignSerialTimeOut;
    }

    public void setMightPos1SignSerialTimeOut(boolean mightPos1SignSerialTimeOut) {
        this.mightPos1SignSerialTimeOut = mightPos1SignSerialTimeOut;
    }

    public boolean isMightPos2SignSerialTimeOut() {
        return mightPos2SignSerialTimeOut;
    }

    public void setMightPos2SignSerialTimeOut(boolean mightPos2SignSerialTimeOut) {
        this.mightPos2SignSerialTimeOut = mightPos2SignSerialTimeOut;
    }

    public boolean isMightPos1Signed() {
        return mightPos1Signed;
    }

    public void setMightPos1Signed(boolean mightPos1Signed) {
        this.mightPos1Signed = mightPos1Signed;
    }

    public boolean isMightPos2Signed() {
        return mightPos2Signed;
    }

    public void setMightPos2Signed(boolean mightPos2Signed) {
        this.mightPos2Signed = mightPos2Signed;
    }
}
