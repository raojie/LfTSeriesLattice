package com.landfoneapi.protocol.pkg;

public enum TradeType {
    NONE("", "空"),
    PURCHASE("01", "01消费指令"),
    REFUNDPURCHASE("02", "02消费撤销"),
    QUERY("04", "04查余额指令"),
    CANCEL("07", "07取消指令"),
    CONFIRM("09", "09确认指令"),
    SIGNIN("51", "51签到指令"),
    SETTLE("52", "52结算指令"),
    GET_PRINT_INFO("61", "61获取打印信息"),
    GET_SETTLE_INFO("62", "62获取结算信息"),
    GET_PRINT_INFO2("66", "66获取打印信息"),
    GET_RECORD("69", "69消费查询"),
    DISPLAY("97", "97信息通知指令"),
    //////////////////////////////////福建骏鹏////////////////////////////////
    REFUND("M2", "M2退款指令"),
    RECHARGE("M4", "M4充值指令"),
    //////////////////////////////////神思饭卡////////////////////////////////
    RECHARGE_SHENSI_FANKA("X3", "X3神思饭卡充值指令"),
    QUERY_SHENSI_FANKA("X4", "X4神思饭卡查余额指令"),
    STATEQUERY("SQ", "SQ状态查询(当前支不支持银联交易)"),
    PING("PI", "PING服务器"),
    SETTINGS("CC", "CC属性配置请求"),
    //////////////////////////////////江西农信////////////////////////////////
    WITHDRAW("37", "37助农取款"),
    CASHREM("38", "38现金汇款"),
    TRANFERREM("39", "39转账汇款"),
    PASSBOOKRENEW("43", "43存折补登"),
    DOWNLOADPAYCODE("44", "44缴费应用代码下载"),
    GETZNRECORDINFO("45", "45取助农类交易信息"),
    GETKEY_REQ("46", "46获取按键透传值"),
    GETKEY_KEYREPORT("47", "47POS透传键值"),
    GET_CARDNO("48", "48获取卡号"),
    GET_ACCOUNTNAME("49", "49获取户名"),
    UPLOADOFFLINE("56", "56脱机交易强制上送指令"),
    PAYFEES_QUERY("93", "93缴费查询"),
    PAYFEES("94", "94缴费"),
    BOUNDACCOUNT_QUERY("95", "95绑定账户信息查询"),
    PASSBOOK_TO_CARD("96", "96存折转卡"),
    GET_MERTMN("B1", "B1获取商户终端信息"),
    GET_KEYBOARD("B2", "B2获取键盘信息"),
    TEST_MK210("B3", "B3测试MK210"),
    TEST_ZNWITHDRAW("B4", "B4单选测试助农取款"),
    TEST_ZNRECORDINFO("B5", "B5测试助农类交易信息"),
    TEST_ZNTRANFERREM("B6", "B6测试助农转账汇款"),
    CHECK_BALANCE("B7", "B7余额查询"),
    TRANS_QUERY("B8", "B8交易查询"),
    SEND_TMK("B9", "B9终端下发TMK"),
    SET_MERTMN("BA", "BA设置商户终端参数"),
    GET_CARDREADER("C1", "C1获取读卡器透传"),
    /////////////////////////////////杭州仁盈 预授权///////////////////////////////
    PREAUTHORIZATION("21", "21预授权"),
    PREAUTHORDONE("23", "23预授权完成"),
    PREAUTHORCANCEL("25", "25预授权撤销"),
    PREAUTHORDONECANCEL("26", "26预授权完成撤销"),
    ///////////////////////////////POS通 厦门停车场////////////////////////////////
    CTB_PURCHASE("10", "10POS通C扫B消费指令"),
    CTB_QUERY("11", "11POS通C扫B结果查询指令"),
    BTC_CANCEL("12", "12厦门定制B扫C撤销指令"),
    TWO_IN_ONE("80", "80厦门停车场定制二合一消费指令//暂不使用"),
    THREE_IN_ONE("81", "81厦门停车场定制三合一消费指令//暂不使用"),
    //////////////////////////////////杉德指令/////////////////////////////////////
    SAND_SMALL_NONSECRET_PAY("S1", "S1银联小额双免支付"),
    SAND_BSC_PAY("S2", "S2银联二维码B扫C支付"),
    SAND_BSC_REFUND("S3", "S3银联二维码B扫C支付撤销"),
    SAND_BSC_RETURN("S4", "S4银联二维码B扫C支付退货"),
    SAND_CARD_TRADE("S5", "S5杉德会员卡支付"),
    SAND_KEY_REPLACE("S6", "S6杉德秘钥置换"),
    SAND_SETTLE("S7", "S7杉德结算"),
    SAND_O2O_SETTLE("S8", "S8杉德O2O平台结算"),
    SAND_O2O_QRCODE_TRADE("S9", "S9杉德O2O平台二维码下单"),
    SAND_O2O_QRCODE_QUERY("SA", "SA杉德O2O平台二维码下单查询"),
    SAND_O2O_LOOP("SB", "SB杉德O2O平台反复下单并支付"),
    SAND_O2O_QUERY_RESULT("SC", "SC杉德O2O平台查询结果"),
    SAND_O2O_CANCEL("SD", "SD杉德O2O平台撤销"),
    SAND_O2O_REFUND("SE", "SE杉德O2O平台退款"),
    SAND_O2O_SIGNIN("SF", "SF杉德O2O平台签到"),
    SAND_DELIVERY_KEY("SG", "SG杉德母pos分发秘钥"),
    SAND_SYNC_MESSAGE("SH", "SH杉德会员卡同步报文"),
    ////////////////////////////////////////TMS///////////////////////////////////
    TMS_ONLINE_REPORT("B1", "B1联机报道"),
    TMS_REMOTE_DOWNLOAD("B7", "B7远程下载"),
    TMS_TMN_INFO_SEND("B8", "B8终端信息上送"),
    TMS_DOWNLOAD_CONFIRM_NOTICE("BB", "BB下载确认通知"),
    TMS_DOWNLOAD_KEY("BD", "BD一键下秘钥"),
    ///////////////////////////////////////////////////////////
    REBOOT("RE", "RE重启POS"),
    ///////////////////////////////////////////////////////////////////////////////////
    ;

    private String code = "01";
    private String desc = "01";

    TradeType(String v, String desc) {//, Object t
        this.code = v;
        this.desc = desc;
    }

    public String getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }
}
