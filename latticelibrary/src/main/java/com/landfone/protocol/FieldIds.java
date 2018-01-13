package com.landfone.protocol;

/**
 * Method: FieldIds
 * Decription: 点阵屏指令编码
 * Author: raoj
 * Date: 2017/9/5
 **/
public class FieldIds {

    public static byte[] STX = new byte[]{0x53};//起始符'S'
    public static byte[] FrameNumber = new byte[]{0x00};//帧编号
    public static byte[] TargetNumber = new byte[]{(byte) 0xff};//目标编号
    public static byte[] ETX = new byte[]{0x40};//结束符'@'

}
