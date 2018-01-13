package com.landfoneapi.protocol.pkg.sand;

import com.landfoneapi.protocol.pkg.TradeType;
import com.landfoneapi.protocol.pkg._03_Common;
import com.landfone.common.utils.LfException;
import com.landfone.common.utils.LfUtils;

import java.io.UnsupportedEncodingException;

/**
 * Created by asus on 2017/3/31.
 */

public class _03_SandO2OQrCodeQuery extends _03_Common {
    private String orderNo = "";//64订单号

    public _03_SandO2OQrCodeQuery() {
        this.setTradeType(TradeType.SAND_O2O_QRCODE_QUERY);
    }

    public void reset() {
        super.reset();
        this.setTradeType(TradeType.SAND_O2O_QRCODE_QUERY);
        try {
            this.setOrderNo("");
        } catch (LfException e) {
            e.printStackTrace();
        }
    }

    @Override
    public byte[] getBytes() throws UnsupportedEncodingException {
        int i = 0;
        byte[] retbb = null;
        byte[] tmpbb = new byte[128 * 4];
        byte[] commonbb = null;
        commonbb = super.getBytes();
        if (commonbb != null) {
            i += LfUtils.memcpy(tmpbb, i, commonbb, 0, commonbb.length);
        }
        //订单号
        i += LfUtils.memcpy(tmpbb, i, orderNo.getBytes(), 0, orderNo.getBytes().length);
        //分隔符
        i += LfUtils.memcpy(tmpbb, i, FS, 0, FS.length);

        if (i > 0 && i < tmpbb.length) {
            retbb = new byte[i];
            LfUtils.memcpy(retbb, 0, tmpbb, 0, i);
        }
        return retbb;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) throws LfException {
        if (orderNo.getBytes().length > 64) {
            throw new LfException(-8, "orderNo length error, need <=64");
        }
        this.orderNo = orderNo;
    }
}
