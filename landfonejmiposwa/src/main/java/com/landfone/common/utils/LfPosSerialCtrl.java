package com.landfone.common.utils;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.hardware.usb.UsbRequest;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Iterator;


import android_serialport_api.SerialPort;


public class LfPosSerialCtrl extends Debug {
    protected Context context;

    /**************************
     * 串口接口
     ******************************/
    public void IOpen(String path, String para) throws LfException {
        if (this.pmISerialPort != null) {
            try {
                this.pmISerialPort.open(path, para);
            } catch (IOException e) {
                //e.printStackTrace();
                throw new LfException(Errs.SERIAL_OPEN_ERR);
            }
        } else {
            throw new LfException(Errs.SERIAL_IFACE_NULL);
        }
    }

    public int IReadAvailable() throws LfException {
        int ret = 0;
        if (this.pmISerialPort != null) {
            //dbg_mPrintf("this.pmISerialPort!=null");
            try {
                ret = this.pmISerialPort.readAvailable();
            } catch (IOException e) {
                //e.printStackTrace();
                throw new LfException(Errs.SERIAL_IO_ERR);
            }
        } else {
            throw new LfException(-1, "this.pmISerialPort==null, 串口接口null");
        }
        return ret;
    }

    public int IRead(byte[] buf, int size, int timeout) throws LfException {
        int ret = -1;
        //dbg_mPrintf("IRead");
        if (this.pmISerialPort != null) {
            //dbg_mPrintf("this.pmISerialPort!=null");
            try {
                ret = this.pmISerialPort.read(buf, size, timeout);
            } catch (IOException e) {
                //e.printStackTrace();
                throw new LfException(e, Errs.OTHER_ERR.getValue(), "serial read error");
            }
        } else {
            throw new LfException(Errs.SERIAL_IFACE_NULL);
        }
        return ret;
    }

    public int IWrite(byte[] buf, int size, int timeout) throws LfException {
        int ret = -1;
        if (this.pmISerialPort != null) {
            //dbg_mPrintf("this.pmISerialPort!=null");
            try {
                ret = this.pmISerialPort.write(buf, size, timeout);
            } catch (IOException e) {
                //e.printStackTrace();
                throw new LfException(e, Errs.OTHER_ERR.getValue(), "serial write error");
            }
        } else {
            throw new LfException(Errs.SERIAL_IFACE_NULL);
        }
        return ret;
    }

    public void IClose() {
        if (this.pmISerialPort != null) {
            this.pmISerialPort.close();
        }
        if (this.pmIUsbPort != null) {
            this.pmIUsbPort.close();
        }
    }

    public void IFlush() {
        if (this.pmISerialPort != null) {
            this.pmISerialPort.flush();
        }
        if (this.pmIUsbPort != null) {
            this.pmIUsbPort.flush();
        }
    }

    private ISerialPort pmISerialPort = null;

    public ISerialPort getISerialPort() {
        return pmISerialPort;
    }

    public void setISerialPort(ISerialPort pmISerialPort) {
        if (pmISerialPort == null) {//如果是null则使用默认的串口api
            this.pmISerialPort = mAndroidSerialPortApi;
            System.out.println("使用默认串口jni,mAndroidSerialPortApi");
        } else {
            this.pmISerialPort = pmISerialPort;
            System.out.println("使用其它串口jni");
        }
    }

    ////////////////////////////////android通用api////////////////////////////////////
    private SerialPort mSerialPort = null;// jni
    private ISerialPort mAndroidSerialPortApi = new ISerialPort() {
        @Override
        public void open(String path, String para) throws LfException {
            dbg_mPrintf("open:" + path + ", para:" + para);
            if (mSerialPort == null) {
                try {
                    mSerialPort = new SerialPort(new File(path), Integer.parseInt(para), 'N', 0);
                } catch (Exception e) {
                    throw new LfException(Errs.SERIAL_OPEN_ERR);
                }
            }
        }

        @Override
        public int read(byte[] buf, int size, int timeout) throws IOException {
            int tmo = timeout;
            int tmpi = 0, ret = 0, ret2 = 0, ret3 = 0;

            if (timeout < 1) {
                if (mSerialPort.getInputStream().available() >= size) {
                    ret = mSerialPort.getInputStream().read(buf, 0, size);
                }
                return ret;
            }

            tmo = tmo < 50 ? 100 : tmo;
            if (mSerialPort != null && buf != null && size > 0) {
                while (size > mSerialPort.getInputStream().available()
                        && tmo > 0) {
                    try {
                        Thread.sleep(50);
                        tmo -= 50;
                    } catch (InterruptedException e) {
                        break;
                    }
                }

                tmpi = size > mSerialPort.getInputStream().available() ? mSerialPort.getInputStream().available() : size;

                dbg_mPrintf("mAndroidSerialPortApi available:" + mSerialPort.getInputStream().available() + ", size:" + size);
                ret = mSerialPort.getInputStream().read(buf, 0, tmpi);
                ret3 = ret;
                dbg_TprintfWHex("POS", buf, ret, "read " + ret);
                while (ret >= 0 && ret < tmpi) {
                    ret2 = ret;
                    tmpi -= ret2;
                    ret = mSerialPort.getInputStream().read(buf, ret2, tmpi);
                    ret3 += ret;
                    dbg_TprintfWHex("POS", buf, ret3, "read " + ret3);
                }
                dbg_mPrintfWHex(buf, tmpi, "read");
            }
            return ret3;
        }

        @Override
        public int write(byte[] buf, int size, int timeout) throws IOException {
            int tmo = timeout;
            int ret = 0;
            tmo = tmo < 50 ? 100 : tmo;
            if (mSerialPort != null && buf != null && size > 0) {
                mSerialPort.getOutputStream().write(buf, 0, size);
                ret = size;
            }
            return ret;
        }

        @Override
        public void close() {
            if (mSerialPort != null) {
                dbg_mPrintf("mAndroidSerialPortApi close!!!!");
                mSerialPort.close();
                mSerialPort = null;
            }
        }

        @Override
        public void flush() {
            if (mSerialPort != null) {
                try {
                    mSerialPort.getOutputStream().flush();

                } catch (IOException e) {
                    //e.printStackTrace();
                }
            }

        }

        @Override
        public int readAvailable() throws IOException {
            int ret = 0;
            if (mSerialPort != null) {
                ret = mSerialPort.getInputStream().available();
            }
            return ret;
        }
    };

    ////////////////////////////////////////////////////////////////////////////////////////////////
    private IUsbPort pmIUsbPort = null;

    public IUsbPort getIUsbPort() {
        return pmIUsbPort;
    }

    public void setIUsbPort(IUsbPort pmIUsbPort) {
        if (pmIUsbPort == null) {
            this.pmIUsbPort = mAndroidUsbPortApi;
            System.out.println("使用默认USBmAndroidUsbPortApi");
        } else {
            this.pmIUsbPort = pmIUsbPort;
            System.out.println("使用其他USB");
        }
    }

    private UsbEndpoint usbEpOut;
    private UsbEndpoint usbEpIn;
    private UsbDeviceConnection mDeviceConnection;
    private IUsbPort mAndroidUsbPortApi = new IUsbPort() {
        @Override
        public void open(String path, String para) throws IOException, LfException {
            UsbManager manager = (UsbManager) context.getSystemService(Context.USB_SERVICE);
            HashMap<String, UsbDevice> deviceList = manager.getDeviceList();

            UsbInterface mInterface = null;
            UsbDevice mUsbDevice = null;

            Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();
            while (deviceIterator.hasNext()) {
                mUsbDevice = deviceIterator.next();
            }
            for (int i = 0; i < mUsbDevice.getInterfaceCount(); ) {
                UsbInterface usbInterface = mUsbDevice.getInterface(i);
                mInterface = usbInterface;
                break;
            }
            if (mInterface.getEndpoint(1) != null) {
                usbEpOut = mInterface.getEndpoint(1);
            }
            if (mInterface.getEndpoint(0) != null) {
                usbEpIn = mInterface.getEndpoint(0);
            }
            if (mInterface != null) {
                if (manager.hasPermission(mUsbDevice)) {
                    mDeviceConnection = manager.openDevice(mUsbDevice);
                    if (mDeviceConnection == null) {
                        return;
                    }
                    if (mDeviceConnection.claimInterface(mInterface, true)) {
                        Logz.d("找到设备接口");
                    } else {
                        mDeviceConnection.close();
                    }
                } else {
                    Logz.d("没有权限");
                }
            } else {
                Logz.d("没有找到设备接口！");
            }

        }

        @Override
        public int read(byte[] buf, int size, int timeout) throws IOException {
            int tmo = timeout;
            int tmpi = 0, ret = 0, ret2 = 0, ret3 = 0;
            if (timeout < 1) {
                ret = mDeviceConnection.bulkTransfer(usbEpIn, buf, size, timeout);
                return ret;
            }

            tmo = tmo < 50 ? 100 : tmo;
            if (mDeviceConnection != null && buf != null && size > 0) {
                while (tmo > 0) {
                    try {
                        Thread.sleep(50);
                        tmo -= 50;
                    } catch (InterruptedException e) {
                        break;
                    }
                }
                tmpi = size;

                dbg_mPrintf("mDeciceConnection available:");
                ret = mDeviceConnection.bulkTransfer(usbEpIn, buf, 0, tmpi);
                ret3 = ret;
                dbg_printfWHex(buf, ret, "read(" + ret + ")");
                while (ret >= 0 && ret < tmpi) {
                    ret2 = ret;
                    tmpi -= ret2;
                    ret = mDeviceConnection.bulkTransfer(usbEpIn, buf, ret2, tmpi);
                    ret3 += ret;
                    dbg_printfWHex(buf, ret3, "read(" + ret + ")");
                }
                dbg_printfWHex(buf, tmpi, "read(" + ret + ")");
            }
            return ret3;
        }

        @Override
        public int write(byte[] buf, int size, int timeout) throws IOException {
            int tmo = timeout;
            int ret = 0;
            tmo = tmo < 50 ? 100 : tmo;

            if (mDeviceConnection != null && buf != null && size > 0) {
                ret = mDeviceConnection.bulkTransfer(usbEpOut, buf, size, tmo);
                if (ret > 0) {
                    ret = size;
                } else {
                    ret = 0;
                }
            }
            return ret;
        }

        @Override
        public int readAvailable() throws IOException {

            return 0;
        }

        @Override
        public void close() {
            mDeviceConnection.close();
            usbEpIn = null;
            usbEpOut = null;

        }

        @Override
        public void flush() {
        }
    };

}
