package com.landfone.lattice;

import com.landfone.command.ClearSrcDownlink;
import com.landfone.command.CloseSrcDownlink;
import com.landfone.command.FirstShowTextWindowDownlink;
import com.landfone.command.FourthShowTextWindowDownlink;
import com.landfone.command.OpenSrcDownlink;
import com.landfone.command.PreviousTextWindowDownlink;
import com.landfone.command.SecondShowTextWindowDownlink;
import com.landfone.command.ThirdShowTextWindowDownlink;
import com.landfone.exceptions.LfException;
import com.landfone.serialport.LfPosSerialCtrl;
import com.landfone.utils.Converter;

import java.util.Arrays;

/**
 * Method: LfLattice
 * Decription:
 * Author: raoj
 * Date: 2017/9/5
 **/
public class LfLattice {
    int tmo;
    private String path = null;
    private String baudRate = null;
    private LfPosSerialCtrl mLfPosSerialCtrl;

    public void InitLfLattice(String path) {
        this.path = path;
        if (this.mLfPosSerialCtrl == null) {
            this.mLfPosSerialCtrl = new LfPosSerialCtrl();
        }
        this.mLfPosSerialCtrl.setISerialPort(null);//设置null则使用默认的串口api
        try {
            this.mLfPosSerialCtrl.IOpen(this.path, "9600");//安卓板固定为这个串口参数
        } catch (LfException e) {
            e.printStackTrace();
        }
    }

    public void InitLfLattice(String path, String baudRate) {
        this.path = path;
        this.baudRate = baudRate;
        if (this.mLfPosSerialCtrl == null) {
            this.mLfPosSerialCtrl = new LfPosSerialCtrl();
        }
        this.mLfPosSerialCtrl.setISerialPort(null);//设置null则使用默认的串口api
        try {
            this.mLfPosSerialCtrl.IOpen(this.path, this.baudRate);//安卓板固定为这个串口参数
        } catch (LfException e) {
            e.printStackTrace();
        }
    }

    public synchronized void openSrc() throws Exception {
        if (this.mLfPosSerialCtrl == null) {
            throw new Exception("尚未初始化，请先初始化");
        }
        OpenSrcDownlink openSrcDownlink = new OpenSrcDownlink();
        byte[] openSrc = openSrcDownlink.serialize();
        int ret_write = -1;
        ret_write = this.mLfPosSerialCtrl.IWrite(openSrc, openSrc.length, 2000);
        if (ret_write > 0) {
            System.out.println("ret_write1:" + ret_write);
        } else {
            System.out.println("ret_write2:" + ret_write);
        }
    }

    public synchronized void closeSrc() throws Exception {
        if (this.mLfPosSerialCtrl == null) {
            throw new Exception("尚未初始化，请先初始化");
        }
        CloseSrcDownlink closeSrcDownlink = new CloseSrcDownlink();
        byte[] closeSrc = closeSrcDownlink.serialize();
        int ret_write = -1;
        ret_write = this.mLfPosSerialCtrl.IWrite(closeSrc, closeSrc.length, 2000);
        if (ret_write > 0) {
            System.out.println("ret_write1:" + ret_write);
        } else {
            System.out.println("ret_write2:" + ret_write);
        }
    }

    /**
     * 前两行行显示
     * @param content
     * @param length
     * @throws Exception
     */
    public synchronized boolean showPreviousContent(byte[] content,int length) throws Exception {
//        tmo = timeout_mils;
        tmo = 1000;
        boolean result = false;
        try {
//        System.out.println("content.length1:" + length);
            if (this.mLfPosSerialCtrl == null) {
                throw new Exception("尚未初始化，请先初始化");
            }
            PreviousTextWindowDownlink downlink = new PreviousTextWindowDownlink();
            downlink.setCharcterCode(content);
//        System.out.println("content.length2:" + Converter.bytesToHexString(Converter.intToTwoBytes(length)));
            byte[] show = downlink.serialize(content);
            int ret_write = -1;
            int ret_read = -1;
            ret_write = this.mLfPosSerialCtrl.IWrite(show, show.length, 2000);
//            System.out.println("show:" + Converter.bytesToHexString(show) + "show.length:" + show.length);
            if (ret_write > 0) {
//                System.out.println("ret_write1:" + ret_write);
            } else {
//                System.out.println("ret_write2:" + ret_write);
            }
            while (tmo > 0) {
                ret_read = this.mLfPosSerialCtrl.IReadAvailable();
//                System.out.println("ret_read:" + ret_read);
                byte[] recv_buff = new byte[8];
                Arrays.fill(recv_buff, (byte) 0x00);
                if (ret_read > 0) {
                    this.mLfPosSerialCtrl.IRead(recv_buff, 8, tmo);
//                    System.out.println("recv_buff:" + Converter.bytesToHexString(recv_buff));
                    byte[] resultType = new byte[1];
                    System.arraycopy(recv_buff, 4, resultType, 0, 1);
//                    System.out.println("resultType:" + Converter.bytesToHexString(resultType));
                    if (Converter.bytesToHexString(resultType).toString().trim().equals("00")) {
                        result = true;
                    }
                    break;
                } else {
                    countDown();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 第一行显示
     * @param content
     * @param length
     * @throws Exception
     */
    public synchronized void showFirstContent(byte[] content,int length) throws Exception {
//        System.out.println("content.length1:" + length);
        if (this.mLfPosSerialCtrl == null) {
            throw new Exception("尚未初始化，请先初始化");
        }
        FirstShowTextWindowDownlink downlink = new FirstShowTextWindowDownlink();
        downlink.setCharcterCode(content);
//        System.out.println("content.length2:" + Converter.bytesToHexString(Converter.intToTwoBytes(length)));
        byte[] show = downlink.serialize(content);
        int ret_write = -1;
        int ret_read = -1;
        ret_write = this.mLfPosSerialCtrl.IWrite(show, show.length, 2000);
//        System.out.println("show:" + Converter.bytesToHexString(show) + "show.length:" + show.length);
        if (ret_write > 0) {
//            System.out.println("ret_write1:" + ret_write);
        } else {
//            System.out.println("ret_write2:" + ret_write);
        }
        ret_read = this.mLfPosSerialCtrl.IReadAvailable();
        byte[] rec_buff = new byte[2048];
        if(ret_read > 0) {
            this.mLfPosSerialCtrl.IRead(rec_buff, length, 2000);
        }
    }

    /**
     * 第二行显示
     * @param content
     * @param length
     * @throws Exception
     */
    public synchronized void showSecondContent(byte[] content,int length) throws Exception {
//        System.out.println("content.length1:" + length);
        if (this.mLfPosSerialCtrl == null) {
            throw new Exception("尚未初始化，请先初始化");
        }
        SecondShowTextWindowDownlink downlink = new SecondShowTextWindowDownlink();
        downlink.setCharcterCode(content);
//        System.out.println("content.length2:" + Converter.bytesToHexString(Converter.intToTwoBytes(length)));
        byte[] show = downlink.serialize(content);
        int ret_write = -1;
        int ret_read = -1;
        ret_write = this.mLfPosSerialCtrl.IWrite(show, show.length, 2000);
//        System.out.println("show:" + Converter.bytesToHexString(show) + "show.length:" + show.length);
        if (ret_write > 0) {
//            System.out.println("ret_write1:" + ret_write);
        } else {
//            System.out.println("ret_write2:" + ret_write);
        }
        ret_read = this.mLfPosSerialCtrl.IReadAvailable();
        byte[] rec_buff = new byte[2048];
        if(ret_read > 0) {
            this.mLfPosSerialCtrl.IRead(rec_buff, length, 2000);
        }
    }

    /**
     * 第三行显示
     * @param content
     * @param length
     * @throws Exception
     */
    public synchronized void showThirdContent(byte[] content,int length) throws Exception {
//        System.out.println("content.length1:" + length);
        if (this.mLfPosSerialCtrl == null) {
            throw new Exception("尚未初始化，请先初始化");
        }
        ThirdShowTextWindowDownlink downlink = new ThirdShowTextWindowDownlink();
        downlink.setCharcterCode(content);
//        System.out.println("content.length2:" + Converter.bytesToHexString(Converter.intToTwoBytes(length)));
        byte[] show = downlink.serialize(content);
        int ret_write = -1;
        int ret_read = -1;
        ret_write = this.mLfPosSerialCtrl.IWrite(show, show.length, 2000);
//        System.out.println("show:" + Converter.bytesToHexString(show) + "show.length:" + show.length);
        if (ret_write > 0) {
//            System.out.println("ret_write1:" + ret_write);
        } else {
//            System.out.println("ret_write2:" + ret_write);
        }
        ret_read = this.mLfPosSerialCtrl.IReadAvailable();
        byte[] rec_buff = new byte[2048];
        if(ret_read > 0) {
            this.mLfPosSerialCtrl.IRead(rec_buff, length, 2000);
        }
    }

    /**
     * 第四行显示
     * @param content
     * @param length
     * @throws Exception
     */
    public synchronized void showFourthContent(byte[] content,int length) throws Exception {
//        System.out.println("content.length1:" + length);
        if (this.mLfPosSerialCtrl == null) {
            throw new Exception("尚未初始化，请先初始化");
        }
        FourthShowTextWindowDownlink downlink = new FourthShowTextWindowDownlink();
        downlink.setCharcterCode(content);
//        System.out.println("content.length2:" + Converter.bytesToHexString(Converter.intToTwoBytes(length)));
        byte[] show = downlink.serialize(content);
        int ret_write = -1;
        int ret_read = -1;
        ret_write = this.mLfPosSerialCtrl.IWrite(show, show.length, 2000);
//        System.out.println("show:" + Converter.bytesToHexString(show) + "show.length:" + show.length);
        if (ret_write > 0) {
//            System.out.println("ret_write1:" + ret_write);
        } else {
//            System.out.println("ret_write2:" + ret_write);
        }
        ret_read = this.mLfPosSerialCtrl.IReadAvailable();
        byte[] rec_buff = new byte[2048];
        if(ret_read > 0) {
            this.mLfPosSerialCtrl.IRead(rec_buff, ret_read, 2000);
        }
    }

    /**
     * 清屏
     * @throws Exception
     */
    public synchronized void clearSrc() throws Exception {
        if (this.mLfPosSerialCtrl == null) {
            throw new Exception("尚未初始化，请先初始化");
        }
        ClearSrcDownlink downlink = new ClearSrcDownlink();
        byte[] clearSrc = downlink.serialize();
        int ret_write = -1;
        int ret_read = -1;
        ret_write = this.mLfPosSerialCtrl.IWrite(clearSrc, clearSrc.length, 2000);
        if (ret_write > 0) {
            System.out.println("ret_write1:" + ret_write);
        } else {
            System.out.println("ret_write2:" + ret_write);
        }
        ret_read = this.mLfPosSerialCtrl.IReadAvailable();
        byte[] rec_buff = new byte[2048];
        if(ret_read > 0) {
            this.mLfPosSerialCtrl.IRead(rec_buff, ret_read, 2000);
        }
    }

    private void countDown() {
        try {
            Thread.sleep(500);
            tmo -= 500;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return;
        }
    }
}
