package com.landfoneapi.mispos;

public enum DisplayType {
    _1("1", "IC应用选择提示"),
    _2("2", "读卡提示信息"),
    _3("3", "卡号信息确认提示"),
    _4("4", "通讯信息提示"),
    _5("5", "打印信息提示"),
    _6("6", "其他信息提示"),//通讯过程等
    _7("7", "查余额信息提示"),
    _8("8", "IC卡判断"),
    _9("9", "结算等待提示"),
    _a("a", "提示"),//密码输入*号
    _b("b", "退货类交易提示"),
    _c("c", "脱机交易上送失败"),
    _d("d", "二维码URL"),
    _e("e", "密码输入个数提示"),
    _f("f", "IC卡刷卡错误提示"),
    _g("g", "IC卡读卡错误提示"),
    _h("h", "读卡事件"),
    _i("i", "输入密码提示"),
    _j("j", "提示用户查询转入卡"),
    _k("k", "提示用户查询转出卡"),
    _l("l", "确认键"),//原为_d("d","确认键");
    _v("v", "b扫c二维码信息"),
    _key("#", "键值透传"),
    _card("@", "卡信息上报(卡号,2磁道,3磁道)"),
    _record("&", "交易记录(账号(22),户名(62),借贷标志(1),交易金额(15),交易名称(62),交易日期(10),流水号(5),对方账号(22),对方户名(62),对方行名(62),摘要信息(42)"), ;

    private String type = "";
    private String desc = "";

    DisplayType(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static String Display(Display dis) {
        String tmp = "";
        tmp = dis.getMsg();
        return tmp;
    }

    public static String getDesc(Display dis) {
        char c = dis.getType().charAt(0);
        switch (c) {
            case '1':
                return ("IC应用选择提示");
            case '2':
                return ("读卡提示信息");
            case '3':
                return ("卡号信息确认提示");
            case '4':
                return ("通讯信息提示");
            case '5':
                return ("打印信息提示");
            case '6':
                return ("其他信息提示");
            case '7':
                return ("查余额信息提示");
            case '8':
                return ("IC卡判断");
            case '9':
                return ("结算等待提示");
            case 'a':
                return ("提示");//密码输入
            case 'b':
                return ("退货类交易提示");
            case 'c':
                return ("脱机交易上送失败");
            case 'd':
                return ("确认键");
            case 'e':
                return ("密码输入个数提示");
            case 'f':
                return ("IC卡刷卡错误提示");
            case 'g':
                return ("IC卡读卡错误提示");
            case 'i':
                return ("输入密码提示");
            case '#':
                return ("键值透传");
            case '@':
                return ("卡信息上报(卡号,2磁道,3磁道)");
            case '&':
                return ("查询交易记录");
            default:
                return "";
//        if (c == '#') {
//            //            case '#':
//            return ("键值透传");
//        } else if (c == '@') {
//            //            case '@':
//            return ("卡信息上报(卡号,2磁道,3磁道)");
//        } else if (c == '&') {
//            return ("查询交易记录");
//        } else {
//            return "";
        }
    }
}
