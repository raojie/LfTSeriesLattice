package com.landfoneapi.mispos;

import com.landfone.common.utils.Debug;
import com.landfone.common.utils.LfException;
import com.landfone.common.utils.LfUtils;
import com.landfone.common.utils.Logz;

import java.util.Arrays;

public class MISPOS extends Debug {

    /*
    **********************************************************************
    *                            宏定义
    **********************************************************************
    */
    public static final int MISPOS_TRUE = 0x01;
    public static final int MISPOS_FALSE = 0x00;

    public static final int UART_DATALINK_MAX_LEN = 4096;
    public static final int UART_DATALINK_MIN_LEN = 5;

    public static final int UART_DATALINK_ERROR_STX = -1;	/*协议头错误*/
    public static final int UART_DATALINK_ERROR_LEN = -2;	/*协议长度错误*/
    public static final int UART_DATALINK_ERROR_ETX = -3;	/*协议尾错误*/
    public static final int UART_DATALINK_ERROR_LRC = -4;	/*协议校验错误*/
    public static final int UART_DATALINK_ERROR_PARA = -5;	/*传入参数错误*/


    public static final byte PACK_STX = 0x02;            //起始
    public static final byte PACK_ETX = 0x03;            //终止
    public static final byte PACK_FS = 0x1C;            //分隔符
    public static final byte PACK_ACK = 0x06;            //确认
    public static final byte PACK_NAK = 0x15;            //拒绝
    public static final byte PACK_ECP = 0x55;            //异常捕捉
    /*define PATH*/
    public static final byte PROTOCOL_PATH_TEST_SEND = 0x01;            //测试请求
    public static final byte PROTOCOL_PATH_TEST_RECV = 0x02;            //测试应答
    public static final byte PROTOCOL_PATH_ASK_SEND = 0x03;            //收银请求
    public static final byte PROTOCOL_PATH_ASK_RECV = 0x04;            //收银应答
    public static final byte PROTOCOL_PATH_SEND = 0x05;            //向中心请求/
    public static final byte PROTOCOL_PATH_RECV = 0x06;            //中心应答

    public static final byte PROTOCOL_PATH_INFO_SEND = 0x07;            //pos发送通知信息包
    public static final byte PROTOCOL_PATH_INFO_RECV = 0x08;            //client收到通知信息包

    public static final byte SERVICE_MISPOS_TYPE = 0x01;        //银联金融模块类型


    public byte Mispos_ReadCard_Type = 0;
    public byte[] Protocol_Serial_ID = new byte[7];        //流水号


    public MISPOS() {
        try {
            strcpy(Protocol_Serial_ID, "000001");
        } catch (LfException e) {
            e.printStackTrace();
        }
    }

    private byte type = SERVICE_MISPOS_TYPE;

    public void setType(byte t) {
        type = t;
    }

    public byte getType() {
        return type;
    }

    /*
    **********************************************************************
    * 函数名称 : Mispos_Protocol_Pack
    * 函数功能 : 串口协议解包
    * 函数说明 : 无
    * 入口参数 : ptPacketPara			---- 待打包数据参数
    *
    * 出口参数 :  pcSendBuf			---- 打包完成数据
    * 返回值   :
                 >0:解包后数据长度
    * 使用范例 :
    * 修改记录 :
    *           YYYY/MM/DD BY ***:
    **********************************************************************
    */
    public byte Mispos_Protocol_Unpack(byte[] pcRecvData, short nRecvlen, UART_PROTOCOL ptOutPara) throws LfException {
        //pcRecvData结构：STX(1),LEN(2),PATH(1),TYPE(1),ID(6),CONT(n),ETX(1),LRC(1)
        short nTemp = 0;
        byte lrc = 0;
        short nLen = 0;
        byte[] cSerial = new byte[6 + 1];
        int i = 0;

        if (nRecvlen == 0) {
            throw new LfException(MISPOS_FALSE, "len err");
        }

        if (nRecvlen < 2 || pcRecvData[0] != PACK_STX || pcRecvData[nRecvlen - 2] != PACK_ETX) {
            throw new LfException(MISPOS_FALSE, "hed err");
        }

        lrc = LfUtils.CalcLRC(pcRecvData, 1, nRecvlen - 2, (byte) 0);

        if (lrc != pcRecvData[nRecvlen - 1]) {
            throw new LfException(MISPOS_FALSE, "lrc err");
        }
        nLen = (short) ((((pcRecvData[1] & 0x0ff) << 8) & 0x0FF00) | (pcRecvData[2] & 0x000FF));//LEN(2)
        if (nLen + 5 != nRecvlen) {
            throw new LfException(MISPOS_FALSE, "len err");
        }

        nTemp += 3;
        ptOutPara.path = pcRecvData[nTemp];//PATH(1)
        nTemp++;

        ptOutPara.type = pcRecvData[nTemp];//TYPE(1)
        nTemp++;
        nTemp += General_memcpy(cSerial, 0, pcRecvData, (int) nTemp, (short) 6);
        //同步测试报文的流水
        General_memcpy(ptOutPara.id, 0, cSerial, (int) 0, (short) 6);//ID(6)

        nLen = (short) General_memcpy(ptOutPara.data, 0, pcRecvData, (int) nTemp, (short) (nRecvlen - 13));//去除包头，包尾的内容，
        ptOutPara.datalen = nLen;
        dbg_printf("len %d", nLen);
        return MISPOS_TRUE;
    }

    /*
    **********************************************************************
    * 函数名称 : Mispos_Protocol_Pack
    * 函数功能 : 串口协议打包
    * 函数说明 : 无
    * 入口参数 : ptPacketPara			---- 待打包数据参数
    *
    * 出口参数 :  pcSendBuf			---- 打包完成数据
    * 返回值   :
                 >0:解包后数据长度
    * 使用范例 :
    * 修改记录 :
    *           YYYY/MM/DD BY ***:
    **********************************************************************
    */
    public short Mispos_Protocol_Pack(UART_PROTOCOL ptPacketPara, byte[] pcSendBuf) throws LfException {
        Logz.i(this.getClass().getName(), "raoj------Mispos_Protocol_Pack");
        short nTemp = 0;
        byte lrc = 0;
        //包头
        pcSendBuf[nTemp] = PACK_STX;
//        dbg_TprintfWHex(this.getClass().getName(), pcSendBuf, pcSendBuf.length, "---pcSendBuf1---:");
        //留出长度空间
        nTemp += 3;

        //PATH
        pcSendBuf[nTemp] = ptPacketPara.path;
//        dbg_TprintfWHex(this.getClass().getName(), pcSendBuf, pcSendBuf.length, "---pcSendBuf2---:");
        nTemp++;

        pcSendBuf[nTemp] = type;
//        dbg_TprintfWHex(this.getClass().getName(), pcSendBuf, pcSendBuf.length, "---pcSendBuf3---:");
        nTemp++;
        //ID
        General_memcpy(pcSendBuf, nTemp, ptPacketPara.id, 0, (short) 6);
//        dbg_TprintfWHex(this.getClass().getName(), pcSendBuf, pcSendBuf.length, "---pcSendBuf4---:");
        nTemp += 6;

        //数据
        nTemp += General_memcpy(pcSendBuf, nTemp, ptPacketPara.data, 0, ptPacketPara.datalen);
//        dbg_TprintfWHex(this.getClass().getName(), pcSendBuf, pcSendBuf.length, "---pcSendBuf5---:");
        pcSendBuf[1] = (byte) ((byte) ((nTemp - 3) / 256) & 0x0ff);
        pcSendBuf[2] = (byte) ((byte) ((nTemp - 3) % 256) & 0x0ff);

        //结束符
        pcSendBuf[nTemp] = PACK_ETX;
//        dbg_TprintfWHex(this.getClass().getName(), pcSendBuf, pcSendBuf.length, "---pcSendBuf6---:");
        nTemp++;
        //lrc count
        lrc = LfUtils.CalcLRC(pcSendBuf, 1, nTemp - 1, (byte) 0);
        //LRC
        pcSendBuf[nTemp] = lrc;
        nTemp++;

        if (nTemp < 7) {
            throw new LfException(-1, "Mispos_Protocol_Pack total:" + nTemp);
        }
        return nTemp;

    }

    private byte Mispos_Err_Code = 0x00;

    public void Mispos_Set_ErrCode(byte cErrCode) {
        Mispos_Err_Code = cErrCode;
    }


    public byte Mispos_Get_ErrCode() {
        return Mispos_Err_Code;
    }

    /***********************************************************
     * 函数名称 : Common_api_serial_num_handle
     * 函数功能 : 交易流水号处理（自增或自减）
     * 入口参数 : handle, serial_num
     * 入口参数说明：handle:操作参数
     * -1:自减1
     * 1:自加1
     * serial_num:当前需要操作流水号
     * 出口参数 :
     * 出口参数说明：
     * 返回值：
     * 返回值说明：
     *
     * @throws LfException
     **************************************************************/
    public void Mispos_serial_num_handle(byte handle, byte[] serial_num) throws LfException {
        byte len = 0;
        int trade_num = 0;
        byte[] buf = new byte[10];
        byte[] buf2 = new byte[10];
        trade_num = atoi(serial_num);

        if (handle == 1) {
            trade_num++;
        } else {
            trade_num--;
        }
        if (trade_num == 0) {
            trade_num = 999999;
        }
        if (trade_num == 1000000) {
            trade_num = 1;
        }
        LfUtils.sprintf(buf2, "%d", trade_num);
        len = (byte) strlen(buf2);
        Arrays.fill(buf, 0, 10, (byte) '0');
        //memset((char *)buf,'0',10);
        General_memcpy(buf, 6 - len, buf2, 0, (short) len);
        General_memcpy(serial_num, 0, buf, 0, (short) 6);
        serial_num[6] = '\0';
    }

}
