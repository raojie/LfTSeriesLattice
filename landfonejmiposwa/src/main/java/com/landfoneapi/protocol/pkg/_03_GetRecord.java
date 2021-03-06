package com.landfoneapi.protocol.pkg;

import com.landfone.common.utils.LfException;
import com.landfone.common.utils.LfUtils;
import com.landfoneapi.protocol.pkg.TradeType;
import com.landfoneapi.protocol.pkg._03_Common;

import java.io.UnsupportedEncodingException;

public class _03_GetRecord extends _03_Common {

    private String serialNo = "      ";//如果为“六个空格”则打印上一笔流水，如为空或空格则在密码键盘上提示输入流水号

    //构造函数
    public _03_GetRecord() {
        this.setTradeType(TradeType.GET_RECORD);
    }

    public void reset() {
        super.reset();
        this.setTradeType(TradeType.GET_RECORD);
        try {
            this.setSerialNo("      ");
        } catch (LfException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public byte[] getBytes() throws UnsupportedEncodingException {
        byte[] commonbb = null;
        byte[] retbb = null;
        int retlen = 0, i = 0;

        commonbb = super.getBytes();

        //retlen +=1;//分隔符
        retlen += 6;
        retlen += 1;//分隔符

        if (commonbb != null && retlen > 0 && retlen <= (8)) {
            retbb = new byte[commonbb.length + retlen];
            if (retbb != null) {
                //common数据
                LfUtils.memcpy(retbb, i, commonbb, 0, commonbb.length);
                i += commonbb.length;
                //LfUtils.memcpy(retbb, i,FS,0, FS.length);
                //i+=FS.length;
                LfUtils.memcpy(retbb, i, this.serialNo.getBytes(), 0, this.serialNo.getBytes().length);
                i += 6;
                LfUtils.memcpy(retbb, i, FS, 0, FS.length);
                i += FS.length;
            }
        }
        return retbb;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) throws LfException {
        if (serialNo != null && !serialNo.equals("")) {
            this.serialNo = serialNo;
        }
    }
}
