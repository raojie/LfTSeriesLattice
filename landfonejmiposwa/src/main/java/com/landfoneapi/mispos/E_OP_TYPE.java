package com.landfoneapi.mispos;

/**
 * Created by thinkpad on 2015/12/18.
 */
public enum E_OP_TYPE {
    OP_POS_RELEASE(-1, "POS_RELEASE,POS接口释放操作"),
    OP_NONE(0, "NONE,空"),
    OP_POS_INIT(1, "POS_INIT,POS接口初始化操作"),
    OP_POS_SIGNIN(2, "POS_SIGNIN,签到操作"),
    OP_POS_PURCHASE(3, "POS_PURCHASE,POS支付操作"),
    OP_POS_QUERY(4, "POS_QUERY,POS查余额操作"),
    OP_POS_DISPLAY(5, "POS_DISPLAY,POS提示信息"),
    OP_WA_INIT(6, "WA_INIT,WA初始化操作"),
    OP_WA_PURCHASE_WX(7, "WA_PURCHASE_WX,WA微信(预下单)操作"),
    OP_WA_PURCHASE_ALP(8, "WA_PURCHASE_ALP,WA支付宝(预下单操作"),
    OP_WA_ORDER_QUERY(9, "WA_ORDER_QUERY,WA订单查询操作"),
    OP_WA_ORDER_CANCEL(10, "WA_ORDER_CANCEL,WA订单撤销操作"),
    OP_WA_ERROR(11, "WA_ERROR,WA错误返回"),
    OP_WA_QRCODE(12, "WA_QRCODE,二维码返回"),
    OP_PRO_POS_REFUND(14, "POS退款"),
    OP_WA_COUNTDOWN(81, "WA_COUNTDOWN,WA倒计时"),
    OP_SEND_NETSTATE(89, "SEND_NETSTATE下发网络状态"),
    OP_3G_STATE(90, "OP_3G_STATE,网络(3g)状态"),

    OP_POS_GETRECORD(13, "OP_POS_GETRECORD,POS交易查询"),
    OP_POS_CANCEL(14, "OP_POS_CANCEL,POS取消操作"),

    OP_POS_CONFIRM(28, "OP_POS_CONFIRM确认操作"),
    OP_POS_SETTLE(14, "POS_SETTLE,结算操作"),//结算与签到用相同的类型
    OP_POS_GET_SETTLE_INFO(20, "获取结算信息"),
    OP_POS_STATEQUERY(40, "状态查询"),
    OP_POS_PING(41, "pos PING"),

    /////////////////////////////////福建骏鹏///////////////////////////////
    OP_MEMBER_RECHARGE(114, "OP_MEMBER_RECHARGE,会员卡充值"),
    /////////////////////////////////福建骏鹏///////////////////////////////
    OP_MEMBER_RECHARGE_SHENSI_FANKA(115, "OP_MEMBER_RECHARGE_SHENSI_FANKA,神思饭卡充值"),
    OP_POS_QUERY_SHENSI_FANKA(4, "POS_QUERY,神思饭卡查余额操作"),
    /////////////////////////////////江西农信///////////////////////////////
    OP_POS_WITHDRAW(15, "OP_POS_WITHDRAW,POS助农取款"),
    OP_POS_CASHREM(16, "OP_POS_CASHREM,POS现金汇款"),
    OP_POS_TRANSFERREM(17, "OP_POS_TRANSFERREM,POS转账汇款"),

    OP_POS_PASSBOOKRENEW(18, "OP_POS_PASSBOOKRENEW存折补登"),
    OP_POS_DOWNLOADPAYCODE(19, "OP_POS_DOWNLOADPAYCODE缴费应用代码下载"),
    OP_POS_GETZNRECORDINFO(20, "OP_POS_GETZNRECORDINFO取助农类交易信息"),

    OP_POS_GETKEY_REQ(21, "OP_POS_GETKEY_REQ获取按键透传值"),

    OP_POS_GET_CARDNO(22, "OP_POS_GET_CARDNO获取卡号"),
    OP_POS_GET_ACCOUNTNAME(23, "OP_POS_GET_ACCOUNTNAME获取户名"),

    OP_POS_PAYFEES_QUERY(24, "OP_POS_PAYFEES_QUERY缴费查询"),
    OP_POS_PAYFEES(25, "OP_POS_PAYFEES缴费"),
    OP_POS_BOUNDACCOUNTQUERY(26, "OP_POS_BOUNDACCOUNTQUERY绑定账户信息查询"),
    OP_POS_PASSBOOKTOCARD(27, "OP_POS_PASSBOOKTOCARD存折转卡"),


    OP_POS_GET_MER_TMN(29, "OP_POS_GET_MER_TMN获取商户终端信息"),
    OP_POS_GET_KEYBOARD(30, "OP_POS_GET_KEYBOARD获取键盘信息"),
    OP_POS_TEST_MK210(31, "OP_POS_TEST_MK210测试MK210"),
    OP_POS_TEST_WITHDRAW(32, "OP_POS_TEST_WITHDRAW测试助农取款"),
    OP_POS_TEST_GETZNRECORDINFO(33, "OP_POS_TEST_GETRECORDINFO测试取助农类交易信息"),
    OP_POS_TEST_TRANSFERREM(34, "OP_POS_TEST_TRANSFERREM测试POS转账汇款"),
    OP_POS_CHECKBALANCE(35, "OP_POS_TEST_CHECKBALANCE查询余额"),
    OP_POS_TRANSQUERY(36, "OP_POS_TRANSQUERY查询交易"),
    OP_POS_SEND_TMK(37, "OP_POS_SEND_TMK终端下发TMK"),
    OP_POS_SET_MER_TMN(38, "OP_POS_SET_PARA设置商户终端参数"),
    /////////////////////////////////杭州仁盈预授权//////////////////////////////////
    OP_POS_PREAUTHOR(40, "OP_POS_PREAUTHOR预授权"),
    OP_POS_PREAUTHORCANCEL(41, "OP_POS_PREAUTHORCANCEL预授权撤销"),
    OP_POS_PREAUTHORDONE(42, "OP_POS_PREAUTHORDONE预授权完成"),
    OP_POS_PREAUTHORDONECANCEL(43, "OP_POS_PREAUTHORDONECANCEL预授权完成撤销"),
    OP_POS_GETPRINTINFO(44, "OP_POS_GETPRINTINFO取打印信息"),
    OP_POS_GET_CARDREADER(45, "OP_POS_GET_CARDREADER获取读卡器透传"),
    ///////////////////////////////////厦门停车场////////////////////////////////////
    OP_POS_CTB_TRADE(10, "OP_POS_CTB_TRADE POS通C扫B消费指令"),
    OP_POS_CTB_QUERY(11, "OP_POS_CTB_QUERY POS通C扫B结果查询指令"),
    OP_POS_TWOINONE(80, "OP_POS_TWO_IN_ONE厦门停车场定制二合一消费指令"),
    OP_POS_THREEINONE(81, "OP_POS_THREE_IN_ONE厦门停车场定制三合一消费指令"),
    OP_POS_BTC_CANCEL(82, "OP_POS_BTC_CANCEL厦门定制B扫C撤销指令"),

    OP_POS_REFUND(50, "POS撤销交易退款"),
    //////////////////////////主控交互/////////////////////
    OP_TOP_PRO_DATASERVER_STATE(91, "OP_TOP_PRO_DATASERVER_STATE,数据服务器交互状态"),
    OP_TOP_PRO_PAYDEVICE_STATE(92, "OP_TOP_PRO_PAYDEVICE_STATE,支付状态"),
    OP_TOP_PRO_SYS_PARA(98, "OP_TOP_PRO_SYS_PARA,设置系统参数"),
    OP_TOP_PRO_DEBUG(99, "OP_TOP_PRO_DEBUG,调试信息"),
    OP_POS_TEST(999, "OP_POS_TEST,串口测试"),

    /////////////////////////////////////////杉德//////////////////////////////////////////
    OP_SAND_SMALL(401, "OP_SAND_SMALL,银联小额双免支付"),
    OP_SAND_BSC_PAY(402, "OP_SAND_BSC_PAY,银联二维码B扫C支付"),
    OP_SAND_BSC_REFUND(403, "OP_SAND_BSC_REFUND,银联二维码B扫C支付撤销"),
    OP_SAND_BSC_RETURN(404, "OP_SAND_BSC_RETURN,银联二维码B扫C支付退货"),
    OP_SAND_CARD_TRADE(405, "OP_SAND_CARD_TRADE,杉德会员卡支付"),
    OP_SAND_KEY_REPLACE(406, "OP_SAND_KEY_REPLACE,杉德秘钥置换"),
    OP_SAND_SETTLE(407, "OP_SAND_SETTLE,杉德结算"),
    OP_SAND_O2O_SETTLE(408, "OP_SAND_O2O_SETTLE,杉德O2O平台结算"),
    OP_SAND_O2O_QRCODE_TRADE(409, "OP_SAND_O2O_QRCODE_TRADE,杉德O2O平台二维码下单"),
    OP_SAND_O2O_QRCODE_QUERY(410, "OP_SAND_O2O_QRCODE_QUERY,杉德O2O平台二维码下单查询"),
    OP_SAND_O2O_LOOP(411, "OP_SAND_O2O_LOOP,杉德O2O平台反复下单并支付"),
    OP_SAND_O2O_RESULT(412, "OP_SAND_O2O_RESULT,杉德O2O平台查询结果"),
    OP_SAND_O2O_CANCEL(413, "OP_SAND_O2O_CANCEL,杉德O2O平台撤销"),
    OP_SAND_O2O_REFUND(414, "OP_SAND_O2O_REFUND,杉德O2O平台退款"),
    OP_SAND_O2O_SIGNIN(415, "OP_SAND_O2O_SIGNIN,杉德O2O平台签到"),
    OP_SAND_DELIVERY_KEY(416, "OP_SAND_DELIVERY_KEY,杉德母pos分发秘钥"),
    OP_SAND_SYNC_MESSAGE(417, "OP_SAND_SYNC_MESSAGE,杉德会员卡同步报文"),
    ////////////////////////////////////////TMS/////////////////////////////////
    TMS_ONLINE_REPORT(501, "B1联机报道"),
    TMS_REMOTE_DOWNLOAD(502, "B7远程下载"),
    TMS_TMN_INFO_SEND(503, "B8终端信息上送"),
    TMS_DOWNLOAD_CONFIRM_NOTICE(504, "BB下载确认通知"),
    TMS_DOWNLOAD_KEY(505, "BD一键下秘钥"),
    //////////////////////////////////////////////////////////////////////
    REBOOT(9999, "RE重启POS"),
    /////////////////////////////////////////////////////////////////////////////////////
    ;
    private int op = 0;
    private String desc = "";

    E_OP_TYPE(int op, String desc) {
        this.op = op;
        this.desc = desc;
    }

    public String getDesc() {
        return this.desc;
    }

    public int getValue() {
        return this.op;
    }

    ;
}
