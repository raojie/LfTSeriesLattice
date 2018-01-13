package com.landfoneapi.mispos;

import java.util.Arrays;

public class UART_PROTOCOL {
    public byte path = 0;                //包类型
    public byte type = 0;                //类型
    public byte[] id = new byte[6];            //指令
    public byte[] data = new byte[1024 * 15];        //数据
    public short datalen = 0;            //数据长度
    public byte lrc = 0;                //校验位

    public byte[] getBytes() {
        int off = 0;
        this.datalen = this.datalen < 0 || this.datalen > 1024 ? 0 : this.datalen;
        byte[] tmpbb = new byte[3 + this.datalen + 3];
        tmpbb[off++] = this.path;
        tmpbb[off++] = this.type;
        System.arraycopy(this.id, 0, tmpbb, off++, id.length);
        off += id.length;
        System.arraycopy(this.data, 0, tmpbb, off++, this.datalen);
        off += this.datalen;

        tmpbb[off++] = (byte) (this.datalen & 0x000ff);
        tmpbb[off++] = (byte) ((this.datalen & 0x0ff00) >> 8);
        tmpbb[off] = this.lrc;

        return tmpbb;
    }

    public void reset() {
        Arrays.fill(data, (byte) 0x00);
        datalen = 0;
    }
}
