package com.landfone.utils;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

/**
 * 字符串工具转换类.
 * Created by raoj on 2017/4/19.
 */

public class Converter {
    /**
     * Ints to bytes.
     *
     * @param iArray
     *            the i array
     * @return the byte[]
     */
    public static byte[] intsToBytes(int[] iArray) {
        byte[] bArray = new byte[iArray.length];
        for (int i = 0; i < iArray.length; i++) {
            bArray[i] = (byte) iArray[i];
        }
        return bArray;
    }

    /**
     * Int to hex.
     *
     * @param value
     *            the value
     * @param length
     *            the length
     * @param isLowFirst
     *            是否低位在前
     * @return the byte[]
     */
    public static byte[] intToHex(int value, int length, boolean isLowFirst) {
        byte[] buf = new byte[length];

        // 低位在前

        for (int i = 0; i < length; i++) {
            int moveBytes = length - 1 - i;
            if (isLowFirst) {
                moveBytes = i;
            }
            buf[i] = (byte) (value >> 8 * moveBytes & 0xFF);
        }

        return buf;
    }

    /**
     * 把字节数组转换成16进制字符串.
     *
     * @param bArray
     *            the b array
     * @return the string
     */
    public static final String bytesToHexString(byte[] bArray) {
        if (bArray == null || bArray.length == 0) {
            return "";
        }

        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
            sb.append(" ");
        }
        return sb.toString();
    }

    /**
     * 把字节转换成16进制字符串.
     *
     * @param b
     *            the b
     * @return the string
     */
    public static final String byteToHexString(byte b) {
        return Integer.toHexString(0xFF & b);
    }

    /**
     * 将int转化为1个字节
     *
     * @param data
     * @return
     */
    public static byte intToOneBytes(int data) {
        byte oneByte = (byte) (0xff & data);

        return oneByte;
    }

    /**
     * 将int转化为2个字节.
     * <p>
     * 高位在前，地位在后。
     *
     * @param length
     *            the length
     * @return the byte[]
     */
    public static byte[] intToTwoBytes(int length) {
        byte[] contentLengthArray = new byte[2];
        contentLengthArray[0] = (byte) ((0xff00 & length) >> 8);
        contentLengthArray[1] = (byte) (0xff & length);

        return contentLengthArray;
    }

    /**
     * 字符串转换 16进制转字符串
     * @param hexString 原hex字符串
     * @return 转换后字符串
     * <p>例:"3132333435363738"转换为"12345678"
     */
    public static String hexString2String (String hexString) {
        byte[] byteary = hexStringToByte(hexString);

        Charset cs = Charset.forName ("UTF-8");
        ByteBuffer bb = ByteBuffer.allocate (byteary.length);
        bb.put (byteary);
        bb.flip ();
        CharBuffer cb = cs.decode (bb);

        return cb.toString();
    }

    /**
     * 字符串转换 字符串转16进制
     * @param string 原字符串
     * @return 转换后字符串
     * <p>"12345678"转换为"3132333435363738"
     */
    public static String string2HexString(String string) {

        char[] chars = "0123456789ABCDEF".toCharArray();
        StringBuilder sb = new StringBuilder("");
        byte[] bs = string.getBytes();
        int bit;

        for (int i = 0; i < bs.length; i++) {
            bit = (bs[i] & 0x0f0) >> 4;
            sb.append(chars[bit]);
            bit = bs[i] & 0x0f;
            sb.append(chars[bit]);
        }
        return sb.toString().trim();
    }

    /**
     * 把16进制字符串转换成字节数组
     *
     * @param hex
     * @return
     */
    public static byte[] hexStringToByte(String hex) {
        hex = hex.toUpperCase();
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] achar = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
        }
        return result;
    }

    private static byte toByte(char c) {
        byte b = (byte) "0123456789ABCDEF".indexOf(c);
        return b;
    }

    /**
     * asciiToBcd
     *
     * @param asc
     * @return
     */
    public static byte asciiToBcd(byte asc) {
        byte bcd;

        if ((asc >= '0') && (asc <= '9'))
            bcd = (byte) (asc - '0');
        else if ((asc >= 'A') && (asc <= 'F'))
            bcd = (byte) (asc - 'A' + 10);
        else if ((asc >= 'a') && (asc <= 'f'))
            bcd = (byte) (asc - 'a' + 10);
        else
            bcd = (byte) (asc - 48);
        return bcd;
    }

    /**
     * asciiToBCD
     *
     * @param ascii
     * @return
     */
    public static byte[] asciiToBCD(byte[] ascii) {
        byte[] bcd = new byte[ascii.length / 2]; // / 2
        int j = 0;
        for (int i = 0; i < (ascii.length + 1) / 2; i++) { // + 1) / 2
            bcd[i] = asciiToBcd(ascii[j++]);
            bcd[i] = (byte) (((j >= ascii.length) ? 0x00
                    : asciiToBcd(ascii[j++])) + (bcd[i] << 4));
        }
        return bcd;
    }

}
