package com.landfoneapi.protocol.pkg;

import android.util.Log;

import com.landfoneapi.mispos.MISPOS;
import com.landfoneapi.mispos.UART_PROTOCOL;
import com.landfone.common.utils.LfUtils;

/**
 * Created by ASUS on 2016/10/19.
 */
public class _04_QueryReply extends REPLY {
    private String TAG = this.getClass().getSimpleName();

    private TradeType reply_type = TradeType.NONE;//指令代码\
    public String cardNo = "";//卡号
    public String cashBalance = "";//现金余额
    public String pointBalance = "";//积分余额

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
            }
        }

        String tmpstr = newString(tmp);
        //dbg_mPrintf("tmpstr:"+tmpstr);
        String[] tmpstrbb = null;
        if (tmpstr != null) {
            tmpstrbb = tmpstr.split("#");
        }
        if (tmpstrbb != null) {
            i = 0;
            if (tmpstrbb.length > i) {
                if (tmpstrbb[i] != null && tmpstrbb[i].equals("04")) {
                    this.reply_type = TradeType.QUERY;//指令代码
                }
            }
            i++;
            if (tmpstrbb.length > i) {
                this.code = (tmpstrbb[i]);
            }
            i++;
            if (tmpstrbb.length > i) {
                this.code_info = (tmpstrbb[i]);
            }
            i++;
            if (tmpstrbb.length > i) {
                this.setCardNo(tmpstrbb[i]);
            }
            i++;
            if (tmpstrbb.length > i) {
                this.cashBalance = tmpstrbb[i];
            }
            i++;
            if (tmpstrbb.length > i) {
                this.pointBalance = tmpstrbb[i];
            }
            i++;
        }
        return i;
    }

    public TradeType getReply_type() {
        return reply_type;
    }

    public void setReply_type(TradeType reply_type) {
        this.reply_type = reply_type;
    }

    public String getCode() {
        return code;
    }

    public String getCode_info() {
        return code_info;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCashBalance(String cashBalance) {
        this.cashBalance = cashBalance;
    }

    public String getCashBalance() {
        return cashBalance;
    }

    public void setPointBalance(String pointBalance) {
        this.pointBalance = pointBalance;
    }

    public String getPointBalance() {
        return pointBalance;
    }
}
