package com.landfone.command;

import com.landfone.utils.Converter;

import java.util.Arrays;

/**
 * Method: FirstShowTextWindowDownlink
 * Decription:
 * Author: raoj
 * Date: 2017/9/7
 **/
public class ShowTextWindowDownlink extends DownlinkBaseDeviceProtocolInitiative {

    //窗口号---1
    public byte[] windowNumber = new byte[]{0x01};
    //窗口X坐标---2
    public byte[] XLayout = new byte[]{0x00,0x00};
    //窗口Y坐标---2
    public byte[] YLayout = new byte[]{0x00,0x00};
    //窗口X像素---2
    public byte[] XPoint = new byte[]{0x40,0x00};
    //窗口Y像素---2
    public byte[] YPoint = new byte[]{0x20,0x00};
    //备用属性---2
    public byte[] alternateAttribute = new byte[]{0x00,0x00};
    //特技类型---1,0x00 固定,0x01 上覆盖,详见文档
    public byte[] stuntType = new byte[]{0x00};
    //特技速度---1
    public byte[] stuntSpeed = new byte[]{0x00};
    //停留时间---1
    public byte[] residenceTime = new byte[]{0x01};
    //是否清场---1
    public byte[] whetherCleared = new byte[]{0x00};
    //内容排版---1
    public byte[] contentLayout = new byte[]{0x01};
    //横向排版风格---1
    public byte[] horizontalLayoutStyle = new byte[]{0x00};
    //纵向排版风格---1
    public byte[] verticalLayoutStyle = new byte[]{0x00};
    //备用风格---1
    public byte[] alternateStyle = new byte[]{0x00};
    // 内容长度---2
    public byte[] contentLength;
    // 字体---1
    public byte[] font = new byte[]{0x01};
    // 字号---1
    public byte[] fontSize = new byte[]{0x10};
    // 字体颜色蓝---1
    public byte[] fontBlueColor = new byte[]{0x02};
    // 字体颜色红---1
    public byte[] fontRedColor = new byte[]{0x01};
    // 字符内码---n
    public byte[] charcterCode;
    public ShowTextWindowDownlink() {
        setCommandNumber(CommandType.Display_TextWindow);
        setCommandType(new byte[]{0x01});
    }

    public byte[] getCharcterCode() {
        return charcterCode;
    }

    public void setCharcterCode(byte[] charcterCode) {
        this.charcterCode = charcterCode;
    }

    @Override
    public byte[] serialize(byte[] bytes) {
        contentLength = Converter.intToTwoBytes(bytes.length);
        byte[] sendByte = new byte[bytes.length + 26];
//        System.out.println("sendByte.length:"+sendByte.length);
        Arrays.fill(sendByte, (byte) 0x00);
        int srcPos = 0;
        System.arraycopy(getCommandNumber(), 0, sendByte, srcPos, getCommandNumber().length);
        srcPos +=getCommandNumber().length;
        System.arraycopy(getCommandType(), 0, sendByte, srcPos, getCommandType().length);
        srcPos +=getCommandType().length;
        System.arraycopy(windowNumber, 0, sendByte, srcPos, windowNumber.length);
        srcPos +=windowNumber.length;
        System.arraycopy(XLayout, 0, sendByte, srcPos, XLayout.length);
        srcPos +=XLayout.length;
        System.arraycopy(YLayout, 0, sendByte, srcPos, XLayout.length);
        srcPos +=YLayout.length;
        System.arraycopy(XPoint, 0, sendByte, srcPos, XPoint.length);
        srcPos +=XPoint.length;
        System.arraycopy(YPoint, 0, sendByte, srcPos, YPoint.length);
        srcPos +=YPoint.length;
        System.arraycopy(alternateAttribute, 0, sendByte, srcPos, alternateAttribute.length);
        srcPos +=alternateAttribute.length;
        System.arraycopy(stuntType, 0, sendByte, srcPos, stuntType.length);
        srcPos +=stuntType.length;
        System.arraycopy(stuntSpeed, 0, sendByte, srcPos, stuntSpeed.length);
        srcPos +=stuntSpeed.length;
        System.arraycopy(residenceTime, 0, sendByte, srcPos, residenceTime.length);
        srcPos +=residenceTime.length;
        System.arraycopy(whetherCleared, 0, sendByte, srcPos, whetherCleared.length);
        srcPos +=whetherCleared.length;
        System.arraycopy(contentLayout, 0, sendByte, srcPos, contentLayout.length);
        srcPos +=contentLayout.length;
        System.arraycopy(horizontalLayoutStyle, 0, sendByte, srcPos, horizontalLayoutStyle.length);
        srcPos +=horizontalLayoutStyle.length;
        System.arraycopy(verticalLayoutStyle, 0, sendByte, srcPos, verticalLayoutStyle.length);
        srcPos +=verticalLayoutStyle.length;
        System.arraycopy(alternateStyle, 0, sendByte, srcPos, alternateStyle.length);
        srcPos +=alternateStyle.length;
        System.arraycopy(contentLength, 1, sendByte, srcPos, 1);
        srcPos +=1;
        System.arraycopy(contentLength, 0, sendByte, srcPos, 1);
        srcPos +=1;
        System.arraycopy(font, 0, sendByte, srcPos, font.length);
        srcPos +=font.length;
        System.arraycopy(fontSize, 0, sendByte, srcPos, fontSize.length);
        srcPos +=fontSize.length;
        System.arraycopy(fontBlueColor, 0, sendByte, srcPos, fontBlueColor.length);
        srcPos +=fontBlueColor.length;
        System.arraycopy(bytes, 0, sendByte, srcPos, bytes.length);
        return super.serialize(sendByte);
    }
}
