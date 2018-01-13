package com.landfone.lattice;

import com.landfone.utils.CRC16Utils;
import com.landfone.utils.Converter;

import org.junit.Test;

import static org.junit.Assert.*;

public class LfLatticeTest {

//    byte[] buff = new byte[]{0x00, (byte) 0xff, 0x38, 0x1a, 0x01, 0x01, 0x00, 0x00, 0x00, 0x00, 0x40, 0x00,
//            0x20, 0x00, 0x00, 0x00, 0x07, 0x05, 0x01, 0x00, 0x02, 0x00, 0x00, 0x00, 0x17, 0x00, 0x01,
//            0x10, 0x02, (byte) 0xb0, (byte) 0xa1, 0x7e, (byte) 0xce, (byte) 0xe5, (byte) 0xbb, (byte) 0xb7,
//            0x2c, (byte) 0xc4, (byte) 0xe3, (byte) 0xb1,
//            (byte) 0xc8, (byte) 0xcb, (byte) 0xc4, (byte) 0xbb,(byte) 0xb7,(byte) 0xb6,
//            (byte) 0xe0,(byte) 0xd2,(byte) 0xbb,(byte) 0xbb,(byte) 0xb7,0x21};

    byte[] buff = new byte[]{0x00, (byte) 0xff,0x08,0x10};
//    byte[] buff = new byte[]{0x53, (byte) 0xff,0x08,0x10};
//    byte[] buff = new byte[]{0x53, (byte) 0xff};
    @Test
    public void initLfLattice() throws Exception {

    }

    @Test
    public void initLfLattice1() throws Exception {

    }

    @Test
    public void preTwoLine() throws Exception {

    }

    @Test
    public void nextTwoLine() throws Exception {

    }

    @Test
    public void openSrc() throws Exception {

    }

    @Test
    public void get_crc16() throws Exception {
        int result = CRC16Utils.calc_Crc16(buff,buff.length,0x00);
//        int result = LfLattice.calcCrc16(0x00,buff);
        System.out.println("result1:" + result);
//        System.out.println("result2:" + Converter.bytesToHexString(result));
//        System.out.println("result2:" + Converter.byteToHexString(Converter.intToOneBytes(result)));
        System.out.println("result3:" + Converter.bytesToHexString(Converter.intToTwoBytes(result)));
        System.out.println("result4:" + Converter.bytesToHexString(new byte[]{(byte) 0xfb,0x54}));
    }

    @Test
    public void clearSrc() throws Exception {

    }

}