package com.landfone.command;

/**
 * Method: CommandType
 * Decription:
 * Author: raoj
 * Date: 2017/9/5
 **/
public class CommandType {

    public static byte[] Adjust_Rightness = new byte[]{0x03};//调节亮度
    public static byte[] Timing_Rightness = new byte[]{0x04};//定时亮度
    public static byte[] Play_Control = new byte[]{0x05};//播放控制
    public static byte[] IO_Output_Control = new byte[]{0x06};//IO 输出控制
    public static byte[] IO_Status_Read = new byte[]{0x07};//IO 状态读取
    public static byte[] Voice_Operation = new byte[]{0x09};//语音操作
    public static byte[] Draw_Rectangle = new byte[]{0x13};//绘制矩形
    public static byte[] Draw_Polygons = new byte[]{0x14};//绘制多边形
    public static byte[] Draw_Circle = new byte[]{0x15};//绘制圆
    public static byte[] Draw_Arc = new byte[]{0x16};//绘制圆弧
    public static byte[] Display_Text = new byte[]{0x17};//显示文字
    public static byte[] Display_Special_Characters = new byte[]{0x18};//显示特殊字符
    public static byte[] Region_Reversal_Display = new byte[]{0x19};//区域反转显示
    public static byte[] Display_TextWindow = new byte[]{0x1a};//显示文本窗
    public static byte[] Display_TimeWindow = new byte[]{0x1b};//显示时间窗
    public static byte[] Delete_Window = new byte[]{0x1c};//删除窗口
    public static byte[] Reset = new byte[]{(byte) 0xff};//恢复出厂默认值
    public static final byte[] openSrc = new byte[]{0x53, 0x00, (byte) 0xff, 0x09, 0x00, 0x00, 0x32, (byte) 0xd5, 0x40};
    public static final byte[] closeSrc = new byte[]{0x53, 0x00, (byte) 0xff, 0x09, 0x00, 0x0f, (byte) 0xdd, 0x24, 0x40};
    public static final byte[] clearSrc = new byte[]{0x53, 0x00, (byte) 0xff, 0x08, 0x10, (byte) 0xfb, 0x54, 0x40};

    public static final byte[] star = new byte[]{0x53, 0x00, (byte) 0xff, 0x29, 0x1a, 0x01, 0x01, 0x00, 0x00, 0x00, 0x00, 0x40, 0x00, 0x20, 0x00, 0x00, 0x00, 0x07, 0x05, 0x01, 0x00, 0x02, 0x00, 0x00, 0x00, 0x08, 0x00, 0x01, 0x10, 0x02, (byte) 0xce, (byte) 0xe5, (byte) 0xd0, (byte) 0xc7, (byte) 0xba, (byte) 0xec, (byte) 0xc6, (byte) 0xec, 0x3d, 0x6e, 0x40};
}
