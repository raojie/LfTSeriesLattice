package com.landfone.mis.bean;

import android.content.Context;

/**
 * Created by asus on 2017/3/16.
 */

public class TransCfx {
    /****/
    private Context context;
    /**
     * 0专网，1公网
     **/
    private int ssl_on = 1;
    /**
     * 0硬加密，1不使用硬加密
     **/
    private int hard_on = 1;

    /**
     * 商户号
     */
    private String merId = "";
    /**
     * 终端号
     */
    private String tmnId = "";
    /**
     * TPDU
     */
    private String TPDU = "";
    /**
     * 串口号
     */
    private String com_port = "";
    /**
     * 波特率
     */
    private String baudRate = "";
    /**
     * 终端信息
     */
    private String term_info = "";
    /**
     * 终端序列号
     */
    private String ssl_sn = "";
    /**
     * 数字证书路径
     */
    private String ssl_cert = "";
    /**
     * SN密文
     */
    private String authSN = "";
    /**
     * 传统银联服务器IP
     **/
    private String pos_ip = "";
    /**
     * 传统银联服务器端口号
     */
    private int pos_port = 0;
    /**
     * 硬加密服务器IP
     **/
    private String hard_ip = "";
    /**
     * 硬加密服务器端口
     **/
    private int hard_port = 0;
    /**
     * 硬加密服务器IP2
     **/
    private String hard_ip2 = "";
    /**
     * 硬加密服务器端口2
     **/
    private int hard_port2 = 0;
    /**
     * 会员卡服务器
     */
    private String card_ip = "";
    /**
     * 会员卡端口
     */
    private int card_port = 0;
    /**
     * SSL加密服务器
     */
    private String ssl_ip = "";
    /**
     * SSL加密端口
     */
    private int ssl_port = 0;
    /**
     * 杉德会员卡服务器IP
     */
    private String sand_card_ip = "";
    /**
     * 杉德会员卡服务器端口
     */
    private int sand_card_port = 0;
    /**
     * 杉德O2O平台服务器IP
     */
    private String sand_o2o_ip = "";
    /**
     * 杉德O2O平台服务器端口
     */
    private int sand_o2o_port = 0;
    /**
     * TMS服务器IP
     **/
    private String tms_ip = "";
    /**
     * TMS服务器端口
     **/
    private int tms_port = 0;

    /**
     * ping测试的ip
     */
    private String ping_ip = "";
    /**
     * ping测试的服务器
     */
    private int ping_port = 0;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int getSsl_on() {
        return ssl_on;
    }

    public void setSsl_on(int ssl_on) {
        this.ssl_on = ssl_on;
    }

    public int getHard_on() {
        return hard_on;
    }

    public void setHard_on(int hard_on) {
        this.hard_on = hard_on;
    }

    public String getMerId() {
        return merId;
    }

    public void setMerId(String merId) {
        this.merId = merId;
    }

    public String getTmnId() {
        return tmnId;
    }

    public void setTmnId(String tmnId) {
        this.tmnId = tmnId;
    }

    public String getTPDU() {
        return TPDU;
    }

    public void setTPDU(String TPDU) {
        this.TPDU = TPDU;
    }

    public String getCom_port() {
        return com_port;
    }

    public void setCom_port(String com_port) {
        this.com_port = com_port;
    }

    public String getBaudRate() {
        return baudRate;
    }

    public void setBaudRate(String baudRate) {
        this.baudRate = baudRate;
    }

    public String getTerm_info() {
        return term_info;
    }

    public void setTerm_info(String term_info) {
        this.term_info = term_info;
    }

    public String getSsl_sn() {
        return ssl_sn;
    }

    public void setSsl_sn(String ssl_sn) {
        this.ssl_sn = ssl_sn;
    }

    public String getSsl_cert() {
        return ssl_cert;
    }

    public void setSsl_cert(String ssl_cert) {
        this.ssl_cert = ssl_cert;
    }

    public String getAuthSN() {
        return authSN;
    }

    public void setAuthSN(String authSN) {
        this.authSN = authSN;
    }

    /**
     * 银联IP
     **/
    public String getPos_ip() {
        return pos_ip;
    }

    public void setPos_ip(String pos_ip) {
        this.pos_ip = pos_ip;
    }

    /**
     * 银联端口
     **/
    public int getPos_port() {
        return pos_port;
    }

    public void setPos_port(int pos_port) {
        this.pos_port = pos_port;
    }

    /**
     * 硬加密IP
     **/
    public String getHard_ip() {
        return hard_ip;
    }

    public void setHard_ip(String hard_ip) {
        this.hard_ip = hard_ip;
    }

    /**
     * 硬加密端口
     **/
    public int getHard_port() {
        return hard_port;
    }

    public void setHard_port(int hard_port) {
        this.hard_port = hard_port;
    }


    /**
     * 硬加密IP2
     **/
    public String getHard_ip2() {
        return hard_ip2;
    }

    public void setHard_ip2(String hard_ip) {
        this.hard_ip2 = hard_ip;
    }

    /**
     * 硬加密端口2
     **/
    public int getHard_port2() {
        return hard_port2;
    }

    public void setHard_port2(int hard_port) {
        this.hard_port2 = hard_port;
    }


    /**
     * 会员卡IP
     **/
    public String getCard_ip() {
        return card_ip;
    }

    public void setCard_ip(String card_ip) {
        this.card_ip = card_ip;
    }

    /**
     * 会员卡端口
     **/
    public int getCard_port() {
        return card_port;
    }

    public void setCard_port(int card_port) {
        this.card_port = card_port;
    }

    /**
     * SSL加密IP
     */
    public String getSsl_ip() {
        return ssl_ip;
    }

    public void setSsl_ip(String ssl_ip) {
        this.ssl_ip = ssl_ip;
    }

    /**
     * SSL加密端口
     */
    public int getSsl_port() {
        return ssl_port;
    }

    public void setSsl_port(int ssl_port) {
        this.ssl_port = ssl_port;
    }

    /**
     * 杉德会员卡IP
     **/
    public String getSand_card_ip() {
        return sand_card_ip;
    }

    public void setSand_card_ip(String sand_card_ip) {
        this.sand_card_ip = sand_card_ip;
    }

    /**
     * 杉德会员卡端口
     **/
    public int getSand_card_port() {
        return sand_card_port;
    }

    public void setSand_card_port(int sand_card_port) {
        this.sand_card_port = sand_card_port;
    }

    /**
     * 杉德O2O平台IP
     **/
    public String getSand_o2o_ip() {
        return sand_o2o_ip;
    }

    public void setSand_o2o_ip(String sand_o2o_ip) {
        this.sand_o2o_ip = sand_o2o_ip;
    }

    /**
     * 杉德O2O平台端口
     **/
    public int getSand_o2o_port() {
        return sand_o2o_port;
    }

    public void setSand_o2o_port(int sand_o2o_port) {
        this.sand_o2o_port = sand_o2o_port;
    }


    /**
     * TMS服务器IP
     **/
    public String getTms_ip() {
        return tms_ip;
    }

    public void setTms_ip(String tms_ip) {
        this.tms_ip = tms_ip;
    }

    /**
     * TMS服务器端口
     **/
    public int getTms_port() {
        return tms_port;
    }

    public void setTms_port(int tms_port) {
        this.tms_port = tms_port;
    }

    public String getPing_ip() {
        return ping_ip;
    }

    public void setPing_ip(String ping_ip) {
        this.ping_ip = ping_ip;
    }

    public int getPing_port() {
        return ping_port;
    }

    public void setPing_port(int ping_port) {
        this.ping_port = ping_port;
    }

}
