package com.landfone.command;

import com.landfone.utils.Converter;

import java.util.Arrays;

/**
 * Method: FourthShowTextWindowDownlink
 * Decription:
 * Author: raoj
 * Date: 2017/9/7
 **/
public class FourthShowTextWindowDownlink extends DownlinkBaseDeviceProtocolInitiative {

    public FourthShowTextWindowDownlink() {
        setCommandNumber(CommandType.Display_TextWindow);
        setCommandType(new byte[]{0x01});
        setWindowNumber(new byte[]{0x02});
        setXLayout(new byte[]{0x00,0x00});
        setYLayout(new byte[]{0x30,0x00});
        setXPoint(new byte[]{0x40,0x00});
        setYPoint(new byte[]{0x10,0x00});
        setAlternateAttribute(new byte[]{0x00,0x00});
        setStuntType(new byte[]{0x00});
        setStuntSpeed(new byte[]{0x00});
        setResidenceTime(new byte[]{0x23});
        setWhetherCleared(new byte[]{0x01});
        setContentLayout(new byte[]{0x00});
        setHorizontalLayoutStyle(new byte[]{0x00});
        setVerticalLayoutStyle(new byte[]{0x00});
        setAlternateStyle(new byte[]{0x00});
        setFont(new byte[]{0x01});
        setFontSize(new byte[]{0x10});
        setFontColor(new byte[]{0x01});
    }

    @Override
    public byte[] serialize(byte[] bytes) {
        contentLength = Converter.intToTwoBytes(bytes.length);
        byte[] sendByte = new byte[bytes.length + 26];
        Arrays.fill(sendByte, (byte) 0x00);
        int srcPos = 0;
        System.arraycopy(getCommandNumber(), 0, sendByte, srcPos, getCommandNumber().length);
        srcPos +=getCommandNumber().length;
        System.arraycopy(getCommandType(), 0, sendByte, srcPos, getCommandType().length);
        srcPos +=getCommandType().length;
        System.arraycopy(getWindowNumber(), 0, sendByte, srcPos, getWindowNumber().length);
        srcPos +=getWindowNumber().length;
        System.arraycopy(getXLayout(), 0, sendByte, srcPos, getXLayout().length);
        srcPos +=getXLayout().length;
        System.arraycopy(getYLayout(), 0, sendByte, srcPos, getYLayout().length);
        srcPos +=getYLayout().length;
        System.arraycopy(getXPoint(), 0, sendByte, srcPos, getXPoint().length);
        srcPos +=getXPoint().length;
        System.arraycopy(getYPoint(), 0, sendByte, srcPos, getYPoint().length);
        srcPos +=getYPoint().length;
        System.arraycopy(getAlternateAttribute(), 0, sendByte, srcPos, getAlternateAttribute().length);
        srcPos +=getAlternateAttribute().length;
        System.arraycopy(getStuntType(), 0, sendByte, srcPos, getStuntType().length);
        srcPos +=getStuntType().length;
        System.arraycopy(getStuntSpeed(), 0, sendByte, srcPos, getStuntSpeed().length);
        srcPos +=getStuntSpeed().length;
        System.arraycopy(getResidenceTime(), 0, sendByte, srcPos, getResidenceTime().length);
        srcPos +=getResidenceTime().length;
        System.arraycopy(getWhetherCleared(), 0, sendByte, srcPos, getWhetherCleared().length);
        srcPos +=getWhetherCleared().length;
        System.arraycopy(getContentLayout(), 0, sendByte, srcPos, getContentLayout().length);
        srcPos +=getContentLayout().length;
        System.arraycopy(getHorizontalLayoutStyle(), 0, sendByte, srcPos, getHorizontalLayoutStyle().length);
        srcPos +=getHorizontalLayoutStyle().length;
        System.arraycopy(getVerticalLayoutStyle(), 0, sendByte, srcPos, getVerticalLayoutStyle().length);
        srcPos +=getVerticalLayoutStyle().length;
        System.arraycopy(getAlternateStyle(), 0, sendByte, srcPos, getAlternateStyle().length);
        srcPos +=getAlternateStyle().length;
        System.arraycopy(contentLength, 1, sendByte, srcPos, 1);
        srcPos +=1;
        System.arraycopy(contentLength, 0, sendByte, srcPos, 1);
        srcPos +=1;
        System.arraycopy(getFont(), 0, sendByte, srcPos, getFont().length);
        srcPos +=getFont().length;
        System.arraycopy(getFontSize(), 0, sendByte, srcPos, getFontSize().length);
        srcPos +=getFontSize().length;
        System.arraycopy(getFontColor(), 0, sendByte, srcPos, getFontColor().length);
        srcPos +=getFontColor().length;
        System.arraycopy(bytes, 0, sendByte, srcPos, bytes.length);
        return super.serialize(sendByte);
    }
}
