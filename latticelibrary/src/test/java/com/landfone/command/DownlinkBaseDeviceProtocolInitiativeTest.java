package com.landfone.command;

import com.landfone.utils.Converter;

import org.junit.Test;

import static org.junit.Assert.*;

public class DownlinkBaseDeviceProtocolInitiativeTest {

//    byte[] buff = new byte[]{(byte) 0xff, (byte) 0xff};
    byte[] buff = new byte[]{(byte) 0xd6, (byte) 0xd0, (byte) 0xb9, (byte) 0xfa, (byte) 0xc8, (byte) 0xcb};
    @Test
    public void serialize() throws Exception {
//        String str = "中国人";
//        System.out.println("str:" + Converter.bytesToHexString(str.getBytes("GBK")));
//        DownlinkBaseDeviceProtocolInitiative downlink = new DownlinkBaseDeviceProtocolInitiative();
//        downlink.serialize(buff);
        OpenSrcDownlink openSrcDownlink = new OpenSrcDownlink();
        openSrcDownlink.serialize();
    }

}