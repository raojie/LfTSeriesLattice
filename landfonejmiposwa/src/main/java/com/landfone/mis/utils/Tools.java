package com.landfone.mis.utils;

import com.landfone.mis.bean.RequestPojo;
import com.landfone.mis.bean.ResponsePojo;
import com.landfoneapi.misposwa.MyApi;

/**
 * Created by asus on 2017/3/16.
 */

public class Tools {
    private static final String TAG = "Tools";

    private MyApi myApi = new MyApi();

    private String[] transM;
    private int amount = 1;//1分
    private int amount_fen = 1;
    private String appType;
    private String mer;
    private String tmn;
    private String card_no;
    private String serial_no;

    public int CheckRequestIsRule(RequestPojo req) {
        String transType = req.getTransType();
        if (transType == "00") {
            //消费
            transM = req.getTransMemo().split("&");

            amount = req.getAmount();
            myApi.pos_purchase(amount);

        } else if (transType == "01") {
            //退款
            if (req.getTransMemo() != null) {
                transM = req.getTransMemo().split("&");
                appType = transM[0];
                mer = transM[1];
                tmn = transM[2];
                card_no = transM[3];
                serial_no = transM[4];
                myApi.pos_refund(mer, tmn, card_no, amount, serial_no);
            }
        } else if (transType == "02") {
            //查余
            myApi.pos_query();
        } else if (transType == "03") {
            //退货，暂无
        } else if (transType == "04") {
            //结算
            myApi.pos_settle();
        } else if (transType == "05") {
            //签到
            myApi.pos_signin();
        } else if (transType == "69") {
            //查询交易信息
            if (req.getTransMemo() != null) {
                transM = req.getTransMemo().split("&");
            }
            String serial_no = null;
            myApi.pos_getrecord(serial_no);
        }

        return 0;
    }

    public String initTransCfx() {

        return "";
    }

    public ResponsePojo parseResponseStr() {

        return null;
    }

    public Tools() {
    }

}
