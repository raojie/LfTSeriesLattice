package com.landfoneapi.protocol.pkg.jxnx;

import com.landfoneapi.mispos.MISPOS;
import com.landfoneapi.mispos.UART_PROTOCOL;
import com.landfoneapi.protocol.pkg.REPLY;
import com.landfoneapi.protocol.pkg.TradeType;
import com.landfone.common.utils.LfUtils;

/**
 * Created by ASUS on 2016/3/13.
 */
public class _04_TransQueryReply extends REPLY {

    private TradeType reply_type = TradeType.NONE;

    private String isEnd = "";
    private String info = "";

    private String cardNo = "";
    private String payerName = "";
    private String flag = "";
    private String transAmount = "";
    private String transName = "";
    private String transDate = "";
    private String serialNo = "";
    private String recieverCardNo = "";
    private String recieverAccName = "";
    private String recieverName = "";

    public void print() {
        super.print();
        dbg_mPrintf("reply_type(指令码):" + reply_type.getCode());
    }

    public int Unpack(UART_PROTOCOL ptOutPara) {
        int i = 0, j = 0;
        int tmpsize = 0;
        byte[] tail = null;
        byte[] head = null;
        byte[] tmp = null;
        i = super.Unpack(ptOutPara);
        if (ptOutPara.data[i] == MISPOS.PACK_FS) {
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

        LfUtils.memcpy(tmp, j, ptOutPara.data, i, ptOutPara.datalen);
        for (i = 0; i < tmp.length; i++) {
            if (tmp[i] == MISPOS.PACK_FS) {
                tmp[i] = '#';
            }
        }

        String tmpstr = newString(tmp);
        String[] tmpstrbb = null;
        if (tmpstr != null) {
            tmpstrbb = tmpstr.split("#");
        }
        if (tmpstrbb != null) {
            for (j = 0; j < tmpstrbb.length; j++) {
                dbg_mPrintf("%d, %s", j, tmpstrbb[j] == null ? "" : tmpstrbb[j]);
            }
            i = 0;
            if (tmpstrbb.length > i) {
                if (tmpstrbb[i] != null && tmpstrbb[i].equals(TradeType.TRANS_QUERY.getCode())) {
                    this.reply_type = TradeType.TRANS_QUERY;//指令代码
                }
            }
            i++;
            if (tmpstrbb.length > i) {
                this.isEnd = tmpstrbb[i];
            }
            i++;
            if (tmpstrbb.length > i) {
                this.info = tmpstrbb[i];
            }
            i++;

//            if (tmpstrbb.length > i) {
//                this.cardNo = tmpstrbb[i];
//            }
//            i++;
//            if (tmpstrbb.length > i) {
//                this.cardNo = tmpstrbb[i];
//            }
//            i++;
//            if (tmpstrbb.length > i) {
//                this.payerName = tmpstrbb[i];
//            }
//            i++;
//            if (tmpstrbb.length > i) {
//                this.flag = tmpstrbb[i];
//            }
//            i++;
//            if (tmpstrbb.length > i) {
//                this.transAmount = tmpstrbb[i];
//            }
//            i++;
//            if (tmpstrbb.length > i) {
//                this.transName = tmpstrbb[i];
//            }
//            i++;
//            if (tmpstrbb.length > i) {
//                this.transDate = tmpstrbb[i];
//            }
//            i++;
//            if (tmpstrbb.length > i) {
//                this.serialNo = tmpstrbb[i];
//            }
//            i++;
//            if (tmpstrbb.length > i) {
//                this.recieverCardNo = tmpstrbb[i];
//            }
//            i++;
//            if (tmpstrbb.length > i) {
//                this.recieverAccName = tmpstrbb[i];
//            }
//            i++;
//            if (tmpstrbb.length > i) {
//                this.recieverName = tmpstrbb[i];
//            }
//            i++;        i++;
        }
        return i;
    }

    public String getIsEnd() {
        return isEnd;
    }

    public String getInfo() {
        return info;
    }

    public String getCardNo() {
        return cardNo;
    }

    public String getPayerName() {
        return payerName;
    }

    public String getflag() {
        return flag;
    }

    public String getTransAmount() {
        return transAmount;
    }

    public String getTransName() {
        return transName;
    }

    public String getTransDate() {
        return transDate;
    }

//    public String getSerialNo() {
//        return serialNo;
//    }

    public String getRecieverCardNo() {
        return recieverCardNo;
    }

    public String getRecieverAccName() {
        return recieverAccName;
    }

    public String getRecieverName() {
        return recieverName;
    }

}
