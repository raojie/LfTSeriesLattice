package com.landfoneapi.protocol.pkg;

import com.landfoneapi.mispos.ErrCode;
import com.landfoneapi.mispos.MISPOS;
import com.landfoneapi.mispos.UART_PROTOCOL;
import com.landfone.common.utils.Debug;

public class REPLY extends Debug {

    public byte reply = MISPOS.PACK_NAK;//指令代码
    public String op = "";
    public String code = "";//代码
    public String code_info = "";//代码信息

    public void print() {
        dbg_mPrintf("reply:%s", reply == MISPOS.PACK_NAK ? "NAK" : (reply == MISPOS.PACK_ACK ? "ACK" : String.format("%02x", reply)));
        dbg_mPrintf("code:%s", code);
        dbg_mPrintf("code_info:%s", code_info);
    }

    public int Unpack(UART_PROTOCOL ptOutPara) {
        int i = 0;
        if (ptOutPara.datalen < 1) {
            return i;
        }
        if (ptOutPara.data[i] != 0x06 && ptOutPara.data[i] != 0x15) {//如果不是06/15，则为数据返回，设置成ACK
            this.reply = MISPOS.PACK_ACK;
            if (ptOutPara.data[i] != MISPOS.PACK_FS) {
                byte[] tmpbb_1 = new byte[3];
                tmpbb_1[0] = ptOutPara.data[i++];
                tmpbb_1[1] = ptOutPara.data[i++];
                tmpbb_1[2] = 0;
                this.code = newString(tmpbb_1);
            } else {
                i++;
            }
        } else {
            this.reply = ptOutPara.data[i++];
            if (ptOutPara.data[i++] == MISPOS.PACK_FS) {
                if (ptOutPara.data[i] != MISPOS.PACK_FS) {
                    byte[] tmpbb_1 = new byte[3];
                    tmpbb_1[0] = ptOutPara.data[i++];
                    tmpbb_1[1] = ptOutPara.data[i++];
                    tmpbb_1[2] = 0;
                    this.code = newString(tmpbb_1);
                } else {
                    i++;
                }
                if (ptOutPara.data[i++] == MISPOS.PACK_FS) {//ASCII字符串
                    if (ptOutPara.data[i] != MISPOS.PACK_FS) {
                        byte[] tmpbb_2 = new byte[128];
                        int j = 0;
                        while (ptOutPara.data[i] != MISPOS.PACK_FS
                                && ptOutPara.data[i] != MISPOS.PACK_ETX && j < tmpbb_2.length) {
                            tmpbb_2[j++] = ptOutPara.data[i++];
                        }
                        dbg_mPrintfWHex(tmpbb_2,tmpbb_2.length,"raoj----->tmpbb_2");
                        this.code_info = newString(tmpbb_2);
//                        dbg_mPrintf("raoj----->code_info:"+this.code_info);
                    } else {
                        //没有提示信息的ascii字符串
                        this.code_info = ErrCode.tryGetDesc(this.code);
//                        dbg_mPrintf("raoj----->code_info:"+this.code_info);
                        i++;
                    }
                }
                if (this.code != null && this.code.length() > 1 && (this.code_info == null || this.code_info.length() < 1)) {
                    this.code_info = ErrCode.tryGetDesc(this.code);
                }
            } else {
            }
        }
        return i;
    }
}
