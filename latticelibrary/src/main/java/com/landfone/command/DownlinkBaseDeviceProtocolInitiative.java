package com.landfone.command;

import com.landfone.protocol.FieldIds;
import com.landfone.protocol.FieldImpl;
import com.landfone.utils.CRC16Utils;
import com.landfone.utils.Converter;

import java.security.cert.CRL;
import java.sql.Array;
import java.util.Arrays;
import java.util.zip.CRC32;

/**
 * Method: DownlinkBaseDeviceProtocolInitiative
 * Decription: 下发设备信息通信协议初始化
 * Author: raoj
 * Date: 2017/9/5
 **/
public class DownlinkBaseDeviceProtocolInitiative {

    /**
     * 指令编号
     */
    protected byte[] commandNumber;
    /**
     * 指令编号
     */
    protected byte[] commandType;

    //窗口号---1
    public byte[] windowNumber;
    //窗口X坐标---2
    public byte[] XLayout;
    //窗口Y坐标---2
    public byte[] YLayout;
    //窗口X像素---2
    public byte[] XPoint;
    //窗口Y像素---2
    public byte[] YPoint;
    //备用属性---2
    public byte[] alternateAttribute;
    //特技类型---1,0x00 固定,0x01 上覆盖,详见文档
    public byte[] stuntType;
    //特技速度---1
    public byte[] stuntSpeed;
    //停留时间---1
    public byte[] residenceTime;
    //是否清场---1
    public byte[] whetherCleared;
    //内容排版---1
    public byte[] contentLayout;
    //横向排版风格---1
    public byte[] horizontalLayoutStyle;
    //纵向排版风格---1
    public byte[] verticalLayoutStyle;
    //备用风格---1
    public byte[] alternateStyle;
    // 内容长度---2
    public byte[] contentLength;
    // 字体---1
    public byte[] font;
    // 字号---1
    public byte[] fontSize;
    // 字体颜色---1
    public byte[] fontColor;
    // 字符内码---n
    public byte[] charcterCode;

    public byte[] getCommandNumber() {
        return commandNumber;
    }

    public void setCommandNumber(byte[] commandNumber) {
        this.commandNumber = commandNumber;
    }

    public byte[] getCommandType() {
        return commandType;
    }

    public void setCommandType(byte[] commandType) {
        this.commandType = commandType;
    }

    public byte[] getWindowNumber() {
        return windowNumber;
    }

    public void setWindowNumber(byte[] windowNumber) {
        this.windowNumber = windowNumber;
    }

    public byte[] getXLayout() {
        return XLayout;
    }

    public void setXLayout(byte[] XLayout) {
        this.XLayout = XLayout;
    }

    public byte[] getYLayout() {
        return YLayout;
    }

    public void setYLayout(byte[] YLayout) {
        this.YLayout = YLayout;
    }

    public byte[] getXPoint() {
        return XPoint;
    }

    public void setXPoint(byte[] XPoint) {
        this.XPoint = XPoint;
    }

    public byte[] getYPoint() {
        return YPoint;
    }

    public void setYPoint(byte[] YPoint) {
        this.YPoint = YPoint;
    }

    public byte[] getAlternateAttribute() {
        return alternateAttribute;
    }

    public void setAlternateAttribute(byte[] alternateAttribute) {
        this.alternateAttribute = alternateAttribute;
    }

    public byte[] getStuntType() {
        return stuntType;
    }

    public void setStuntType(byte[] stuntType) {
        this.stuntType = stuntType;
    }

    public byte[] getStuntSpeed() {
        return stuntSpeed;
    }

    public void setStuntSpeed(byte[] stuntSpeed) {
        this.stuntSpeed = stuntSpeed;
    }

    public byte[] getResidenceTime() {
        return residenceTime;
    }

    public void setResidenceTime(byte[] residenceTime) {
        this.residenceTime = residenceTime;
    }

    public byte[] getWhetherCleared() {
        return whetherCleared;
    }

    public void setWhetherCleared(byte[] whetherCleared) {
        this.whetherCleared = whetherCleared;
    }

    public byte[] getContentLayout() {
        return contentLayout;
    }

    public void setContentLayout(byte[] contentLayout) {
        this.contentLayout = contentLayout;
    }

    public byte[] getHorizontalLayoutStyle() {
        return horizontalLayoutStyle;
    }

    public void setHorizontalLayoutStyle(byte[] horizontalLayoutStyle) {
        this.horizontalLayoutStyle = horizontalLayoutStyle;
    }

    public byte[] getVerticalLayoutStyle() {
        return verticalLayoutStyle;
    }

    public void setVerticalLayoutStyle(byte[] verticalLayoutStyle) {
        this.verticalLayoutStyle = verticalLayoutStyle;
    }

    public byte[] getAlternateStyle() {
        return alternateStyle;
    }

    public void setAlternateStyle(byte[] alternateStyle) {
        this.alternateStyle = alternateStyle;
    }

    public byte[] getContentLength() {
        return contentLength;
    }

    public void setContentLength(byte[] contentLength) {
        this.contentLength = contentLength;
    }

    public byte[] getFont() {
        return font;
    }

    public void setFont(byte[] font) {
        this.font = font;
    }

    public byte[] getFontSize() {
        return fontSize;
    }

    public void setFontSize(byte[] fontSize) {
        this.fontSize = fontSize;
    }

    public byte[] getFontColor() {
        return fontColor;
    }

    public void setFontColor(byte[] fontColor) {
        this.fontColor = fontColor;
    }

    public byte[] getCharcterCode() {
        return charcterCode;
    }

    public void setCharcterCode(byte[] charcterCode) {
        this.charcterCode = charcterCode;
    }

    public byte[] serialize() {
        byte[] serializeBytes = null;
        byte[] crc16Bytes = null;
        byte[] frameLenByte = null;
        int serializeByteLen = -1;
        int crc16Len = -1;
        int frameLen = -1;
        int srcPos = 0;
        try {
            serializeByteLen = 4 + getCommandNumber().length + 3;
//            System.out.println("serializeByteLen:" + serializeByteLen);//起始符
//            System.out.println("起始符:" + Converter.bytesToHexString(FieldIds.STX));//起始符
//            System.out.println("帧编号:" + Converter.bytesToHexString(FieldIds.FrameNumber));//帧编号
//            System.out.println("目标编号:" + Converter.bytesToHexString(FieldIds.TargetNumber));//目标编号
            frameLen = 4 + getCommandNumber().length + 3;//帧长度
//            System.out.println("帧长度:" + frameLen);//帧长度
            frameLenByte = new byte[1];
            Arrays.fill(frameLenByte, (byte) 0x00);
            frameLenByte[0] = (byte) frameLen;
            crc16Len = 3 + getCommandNumber().length;
            crc16Bytes = new byte[crc16Len];
            Arrays.fill(crc16Bytes, (byte) 0x00);
//            System.out.println("requestBuff:" + Converter.bytesToHexString(requestBuff));
            System.arraycopy(FieldIds.FrameNumber, 0, crc16Bytes, srcPos, FieldIds.FrameNumber.length);
            srcPos += FieldIds.FrameNumber.length;
//            System.out.println("crc16Bytes1:" + Converter.bytesToHexString(crc16Bytes) + " ,srcPos1:" + srcPos);
            System.arraycopy(FieldIds.TargetNumber, 0, crc16Bytes, srcPos, FieldIds.TargetNumber.length);
            srcPos += FieldIds.TargetNumber.length;
//            System.out.println("crc16Bytes2:" + Converter.bytesToHexString(crc16Bytes) + " ,srcPos2:" + srcPos);
            System.arraycopy(frameLenByte, 0, crc16Bytes, srcPos, 1);
            srcPos += 1;
//            System.out.println("crc16Bytes3:" + Converter.bytesToHexString(crc16Bytes) + " ,srcPos3:" + srcPos);
            System.arraycopy(getCommandNumber(), 0, crc16Bytes, srcPos, getCommandNumber().length);
            srcPos += getCommandNumber().length;
            int result = CRC16Utils.calc_Crc16(crc16Bytes, crc16Bytes.length, 0x00);
//            System.out.println("crc16Bytes4:" + Converter.bytesToHexString(crc16Bytes));
            byte[] checkCode = Converter.intToTwoBytes(result);
//            System.out.println("result:" + Converter.bytesToHexString(checkCode));

            serializeBytes = new byte[serializeByteLen];
            Arrays.fill(serializeBytes, (byte) 0x00);
            System.arraycopy(FieldIds.STX, 0, serializeBytes, 0, FieldIds.STX.length);
//            System.out.println("bytes1:" + Converter.bytesToHexString(serializeBytes));
            System.arraycopy(crc16Bytes, 0, serializeBytes, FieldIds.STX.length, crc16Bytes.length);
//            System.out.println("bytes2:" + Converter.bytesToHexString(serializeBytes));
            srcPos += FieldIds.STX.length;
            System.arraycopy(checkCode,0,serializeBytes,srcPos,checkCode.length);
            srcPos += checkCode.length;
            System.arraycopy(FieldIds.ETX,0,serializeBytes,srcPos,FieldIds.ETX.length);
//            System.out.println("bytes:" + Converter.bytesToHexString(serializeBytes));
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return serializeBytes;
    }

    public byte[] serialize(byte[] bytes) {
        byte[] requestBuff = new byte[]{0x00, (byte) 0xff,0x08,(byte) 0xff, (byte) 0xff};
        byte[] serializeBytes = null;
        byte[] crc16Bytes = null;
        byte[] frameLenByte = null;
        int serializeByteLen = -1;
        int crc16Len = -1;
        int frameLen = -1;
        int srcPos = 0;
        try {
            serializeByteLen = 4 + bytes.length + 3;
//            System.out.println("serializeByteLen:" + serializeByteLen);//起始符
//            System.out.println("起始符:" + Converter.bytesToHexString(FieldIds.STX));//起始符
//            System.out.println("帧编号:" + Converter.bytesToHexString(FieldIds.FrameNumber));//帧编号
//            System.out.println("目标编号:" + Converter.bytesToHexString(FieldIds.TargetNumber));//目标编号
            frameLen = 4 + bytes.length + 3;//帧长度
//            System.out.println("帧长度:" + frameLen);//帧长度
            frameLenByte = new byte[1];
            Arrays.fill(frameLenByte, (byte) 0x00);
            frameLenByte[0] = (byte) frameLen;
            crc16Len = 3 + bytes.length;
            crc16Bytes = new byte[crc16Len];
            Arrays.fill(crc16Bytes, (byte) 0x00);
//            System.out.println("requestBuff:" + Converter.bytesToHexString(requestBuff));
            System.arraycopy(FieldIds.FrameNumber, 0, crc16Bytes, srcPos, FieldIds.FrameNumber.length);
            srcPos += FieldIds.FrameNumber.length;
//            System.out.println("crc16Bytes1:" + Converter.bytesToHexString(crc16Bytes) + " ,srcPos1:" + srcPos);
            System.arraycopy(FieldIds.TargetNumber, 0, crc16Bytes, srcPos, FieldIds.TargetNumber.length);
            srcPos += FieldIds.TargetNumber.length;
//            System.out.println("crc16Bytes2:" + Converter.bytesToHexString(crc16Bytes) + " ,srcPos2:" + srcPos);
            System.arraycopy(frameLenByte, 0, crc16Bytes, srcPos, 1);
            srcPos += 1;
//            System.out.println("crc16Bytes3:" + Converter.bytesToHexString(crc16Bytes) + " ,srcPos3:" + srcPos);
            System.arraycopy(bytes, 0, crc16Bytes, srcPos, bytes.length);
            srcPos += bytes.length;
            int result = CRC16Utils.calc_Crc16(crc16Bytes, crc16Bytes.length, 0x00);
//            System.out.println("crc16Bytes4:" + Converter.bytesToHexString(crc16Bytes));
            byte[] checkCode = Converter.intToTwoBytes(result);
//            System.out.println("result:" + Converter.bytesToHexString(checkCode));

            serializeBytes = new byte[serializeByteLen];
            Arrays.fill(serializeBytes, (byte) 0x00);
            System.arraycopy(FieldIds.STX, 0, serializeBytes, 0, FieldIds.STX.length);
//            System.out.println("bytes1:" + Converter.bytesToHexString(serializeBytes));
            System.arraycopy(crc16Bytes, 0, serializeBytes, FieldIds.STX.length, crc16Bytes.length);
//            System.out.println("bytes2:" + Converter.bytesToHexString(serializeBytes));
            srcPos += FieldIds.STX.length;
            System.arraycopy(checkCode,0,serializeBytes,srcPos,checkCode.length);
            srcPos += checkCode.length;
            System.arraycopy(FieldIds.ETX,0,serializeBytes,srcPos,FieldIds.ETX.length);
//            System.out.println("bytes:" + Converter.bytesToHexString(serializeBytes));
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return serializeBytes;
    }
}
