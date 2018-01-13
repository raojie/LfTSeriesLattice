package com.landfoneapi.protocol.pkg.sand;

import com.landfone.common.utils.LfUtils;
import com.landfoneapi.mispos.MISPOS;
import com.landfoneapi.mispos.UART_PROTOCOL;
import com.landfoneapi.protocol.pkg.REPLY;
import com.landfoneapi.protocol.pkg.TradeType;

/**
 * Created by asus on 2017/4/28.
 */

public class _04_SandO2OResultReply extends REPLY {
    private TradeType reply_type = TradeType.NONE;
    private String orderNo = "";
    private String queryResult = "";
    private String backstageTransNo = "";
    private String userPaymentAcocunt = "";

    public int Unpack(UART_PROTOCOL ptOutPara) {
        int i = 0, j = 0;
        int tmpsize = 0;
        byte[] tail = null;
        byte[] head = null;
        byte[] tmp = null;
        i = super.Unpack(ptOutPara);
        if (ptOutPara.data[0] == MISPOS.PACK_FS) {
            tmpsize += 1;
            head = new byte[1];
            head[0] = (byte) '0';
        }
        if (ptOutPara.datalen > 1
                && ptOutPara.data[ptOutPara.datalen - 1] == MISPOS.PACK_FS) {//尾部的分隔符
            tmpsize += 2;
            tail = new byte[2];
            tail[0] = '0';
            tail[1] = 0;
        }
        tmp = new byte[ptOutPara.datalen + tmpsize];
        if (head != null) {
            tmp[0] = head[0];
            j++;
        }
        dbg_mPrintf("tmpsize:" + tmpsize);
        if (tail != null) {
            tmp[j + ptOutPara.datalen + 0] = tail[0];
            tmp[j + ptOutPara.datalen + 1] = tail[1];
        }
        LfUtils.memcpy(tmp, j, ptOutPara.data, 0, ptOutPara.datalen);
        for (i = 0; i < tmp.length; i++) {
            if (tmp[i] == MISPOS.PACK_FS) {
                tmp[i] = '#';
            } else if (tmp[i] == 0x00) {//避免split不完整
                tmp[i] = '0';
            }
        }
        tmp[j + ptOutPara.datalen + 1] = tail[1];//再次把‘0’变成0
        String tmpstr = newString(tmp);
        dbg_mPrintf("tmpstr:" + tmpstr);
        String[] tmpstrbb = null;
        if (tmpstr != null) {
            tmpstrbb = tmpstr.split("#");
        }
        if (tmpstrbb != null) {
            i = 0;
            if (tmpstrbb.length > i) {
                if (tmpstrbb[i] != null && tmpstrbb[i].equals("SC")) {
                    this.reply_type = TradeType.SAND_O2O_QUERY_RESULT;//指令代码
                }
            }
            i++;
            if (tmpstrbb.length > i) {
                code = tmpstrbb[i];
            }
            i++;
            if (tmpstrbb.length > i) {
                orderNo = tmpstrbb[i];//订单号
            }
            i++;
            if (tmpstrbb.length > i) {
                queryResult = tmpstrbb[i];
            }
            i++;
            if (tmpstrbb.length > i) {
                backstageTransNo = tmpstrbb[i];
            }
            i++;
            if (tmpstrbb.length > i) {
                userPaymentAcocunt = tmpstrbb[i];
            }
            i++;
        }
        return i;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public String getQueryResult() {
        return queryResult;
    }

    public String getBackstageTransNo() {
        return backstageTransNo;
    }

    public String getUserPaymentAcocunt() {
        return userPaymentAcocunt;
    }
}
