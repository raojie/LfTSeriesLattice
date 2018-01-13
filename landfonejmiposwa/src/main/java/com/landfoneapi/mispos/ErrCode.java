package com.landfoneapi.mispos;

public enum ErrCode {
    _FF("FF", "FF正在处理,请稍后..."),
    _00("00", "00交易成功"),
    _58("58", "58发卡方不允许进行此交易"),
    _51("51", "51余额不足"),
    _Y4("Y4", "Y4找不到原交易"),
    _A3("A3", "A3校验密钥错"),
    _A4("A4", "A4未签到,需要重新签到"),
    _A5("A5", "A5脱机交易失败"),
    _A6("A6", "A6卡片拒绝"),
    _XX("XX", "XX交易异常"),
    _XY("XY", "XY人为取消"),
    _X4("X4", "X4无交易流水"),
    _XB("XB", "XB交易流水满，请结算"),
    _X5("X5", "X5签到校验错"),
    _X6("X6", "X6数据发送失败"),
    _X7("X7", "X7数据接收失败"),
    _X8("X8", "X8数据接收有误"),
    _X9("X9", "X9结算前脱机交易批上送失败，须打故障单"),
    _Y1("Y1", "Y1按键输入结束"),
    _Y2("Y2", "Y2读卡结束"),
    _Y3("Y3", "Y3超时"),

    ///////////////////////////////自定义错误/////////////////////////////
    _Z0("Z0", "Z0服务器连接失败"),
    _Z1("Z1", "Z1交互中断"),

    _Z2("Z2", "Z2请求拒绝"),
    _Z3("Z3", "Z3接口忙"),

    _Z4("Z4", "UNKNOW,未知"),//ERR_UNKNOW
    _Z5("Z5", "SUCCESS,成功"),//ERR_UNKNOW
    _Z6("Z6", "ERR_SERIAL_ERROR,串口错误"),//ERR_SERIAL_ERROR
    _Z7("Z7", "ERR_POS_NOT_INIT,未初始化"),//ERR_POS_NOT_INIT
    _Z8("Z8", "ERR_POS_NOT_SIGNIN,未签到"),//ERR_POS_NOT_SIGNIN
    _Z9("Z9", "ERR_OTHRER,其它错误"),//ERR_OTHRER
    _LF("LF", "REQUEST_ERR,请求失败"),
    _ER("ER", "ERR_OTHRER2,其它错误2"),
    _PR("PR", "ERR_PARAMS,参数错误"),
    _CO("CO", "ERR_COMPORT,串口错误，初始化失败"),;


    private String code = "00";
    private String desc = "交易成功";

    ErrCode(String v, String desc) {//, Object t
        this.code = v;
        this.desc = desc;
    }

    public String getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }


    public static String tryGetDesc(String v) {
        if (v == null) return "";
        if (v.startsWith("FF"))/*("FF",*/ return "FF正在处理,请稍后...";/*),*/
        if (v.startsWith("00"))/*("00",*/ return "00交易成功";/*),*/
        if (v.startsWith("51"))/*("51",*/ return "51余额不足";/*),*/
        if (v.startsWith("58"))/*("58",*/ return "58发卡方不允许进行此交易";/*),*/
        if (v.startsWith("Y4"))/*("Y4",*/ return "Y4找不到原交易";/*),*/
        if (v.startsWith("A3"))/*("A3",*/ return "A3校验密钥错";/*),*/
        if (v.startsWith("A4"))/*("A4",*/ return "A4未签到,请重新签到";/*),*/
        if (v.startsWith("A5"))/*("A5",*/ return "A5脱机交易失败";/*),*/
        if (v.startsWith("A6"))/*("A6",*/ return "A6卡片拒绝";/*),*/
        if (v.startsWith("XX"))/*("XX",*/ return "XX交易异常";/*),*/
        if (v.startsWith("XY"))/*("XY",*/ return "XY交易人为取消";/*),*/
        if (v.startsWith("X4"))/*("X4",*/ return "X4无交易流水";/*),*/
        if (v.startsWith("XB"))/*("XB",*/ return "XB交易流水满，请结算";/*),*/
        if (v.startsWith("X5"))/*("X5",*/ return "X5签到校验错";/*),*/
        if (v.startsWith("X6"))/*("X6",*/ return "X6数据发送失败";/*),*/
        if (v.startsWith("X7"))/*("X7",*/ return "X7数据接收失败";/*),*/
        if (v.startsWith("X8"))/*("X8",*/ return "X8数据接收有误";/*),*/
        if (v.startsWith("X9"))/*("X9",*/ return "X9结算前脱机交易批上送失败，须打故障单";
        if (v.startsWith("Y1"))/*("Y1",*/ return "Y1按键输入结束";
        if (v.startsWith("Y2"))/*("Y1",*/ return "Y2读卡结束";
        if (v.startsWith("Y3"))/*("Y1",*/ return "Y3超时";
        return "";
    }
}
