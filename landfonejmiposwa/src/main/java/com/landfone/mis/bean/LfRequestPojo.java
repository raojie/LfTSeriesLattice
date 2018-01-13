package com.landfone.mis.bean;

/**
 * Created by asus on 2017/3/16.
 */

public class LfRequestPojo extends RequestPojo {
    private String goodsTotalNum;//商品总数
    private String goodsInfo;//商品信息

    public String getGoodsTotalNum() {
        return goodsTotalNum;
    }

    public void setGoodsTotalNum(String goodsTotalNum) {
        this.goodsTotalNum = goodsTotalNum;
    }

    public String getGoodsInfo() {
        return goodsInfo;
    }

    public void setGoodsInfo(String goodsInfo) {
        this.goodsInfo = goodsInfo;
    }

    @Override
    public String toString() {
        return "LfRequestPojo{" +
                "goodsTotalNum='" + goodsTotalNum + '\'' +
                ", goodsInfo='" + goodsInfo + '\'' +
                '}';
    }
}
