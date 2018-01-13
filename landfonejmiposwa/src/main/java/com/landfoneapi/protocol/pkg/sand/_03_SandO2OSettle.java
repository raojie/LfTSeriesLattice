package com.landfoneapi.protocol.pkg.sand;

import com.landfoneapi.protocol.pkg.TradeType;
import com.landfoneapi.protocol.pkg._03_Common;
import com.landfone.common.utils.LfException;
import com.landfone.common.utils.LfUtils;

import java.io.UnsupportedEncodingException;

/**
 * Created by asus on 2017/3/31.
 */

public class _03_SandO2OSettle extends _03_Common {
    private String clientNo = "";
    private String operator = "";
    private String serialNo = "";

    public void _03_SandO2OSettle() {
        this.setTradeType(TradeType.SAND_O2O_SETTLE);
    }

    public void reset() {
        super.reset();
        this.setTradeType(TradeType.SAND_O2O_SETTLE);
        try {
            this.setClientNo("");
            this.setOperator("");
            this.setSerialNo("");
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
        //终端号
        i += LfUtils.memcpy(tmpbb, i, clientNo.getBytes(), 0, clientNo.getBytes().length);
        //分隔符
        i += LfUtils.memcpy(tmpbb, i, FS, 0, FS.length);
        //操作员号
        i += LfUtils.memcpy(tmpbb, i, operator.getBytes(), 0, operator.getBytes().length);
        //分隔符
        i += LfUtils.memcpy(tmpbb, i, FS, 0, FS.length);
        //收银流水号
        i += LfUtils.memcpy(tmpbb, i, serialNo.getBytes(), 0, serialNo.getBytes().length);
        //分隔符
        i += LfUtils.memcpy(tmpbb, i, FS, 0, FS.length);

        if (i > 0 && i < tmpbb.length) {
            retbb = new byte[i];
            LfUtils.memcpy(retbb, 0, tmpbb, 0, i);
        }
        return retbb;
    }

    public String getClientNo() {
        return clientNo;
    }

    public void setClientNo(String clientNo) throws LfException {
        if (clientNo.getBytes().length > 15) {
            throw new LfException(-8, "clientNo length error, need <=15");
        }
        this.clientNo = clientNo;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) throws LfException {
        if (operator.getBytes().length > 15) {
            throw new LfException(-8, "operator length error, need <=15");
        }
        this.operator = operator;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) throws LfException {
        if (serialNo.getBytes().length > 20) {
            throw new LfException(-8, "serialNo length error, need <=20");
        }
        this.serialNo = serialNo;
    }
}
