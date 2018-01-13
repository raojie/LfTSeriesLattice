package com.landfone.serialport;

import android.util.Log;

import com.landfone.enums.Errs;
import com.landfone.exceptions.LfException;
import com.landfone.listener.ISerialPort;

import java.io.File;
import java.io.IOException;

import android_serialport_api.SerialPort;


public class LfPosSerialCtrl {
    private static final String TAG = "LfPosSerialCtrl";
    //是否开启调试
    public boolean dbgFlag = true;

    /**************************
     * 串口接口
     ******************************/
    public void IOpen(String path, String para) throws LfException {
        if (this.pmISerialPort != null) {
            try {
                this.pmISerialPort.open(path, para);
            } catch (IOException e) {
                throw new LfException(Errs.SERIAL_OPEN_ERR);
            }
        } else {
            throw new LfException(Errs.SERIAL_IFACE_NULL);
        }
    }

    public int IReadAvailable() throws LfException {
        int ret = 0;
        if (this.pmISerialPort != null) {
            try {
                ret = this.pmISerialPort.readAvailable();
            } catch (IOException e) {
                throw new LfException(Errs.SERIAL_IO_ERR);
            }
        } else {
            throw new LfException(-1, "this.pmISerialPort==null, 串口接口null");
        }
        return ret;
    }

    public int IRead(byte[] buf, int size, int timeout) throws LfException {
        int ret = -1;
        if (this.pmISerialPort != null) {
            try {
                ret = this.pmISerialPort.read(buf, size, timeout);
            } catch (IOException e) {
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
            try {
                ret = this.pmISerialPort.write(buf, size, timeout);
            } catch (IOException e) {
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
    }

    public void IFlush() {
        if (this.pmISerialPort != null) {
            this.pmISerialPort.flush();
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
        public void open(String path, String para) throws IOException {

            Log.d(TAG, "open:" + path + ", para:" + para);
            if (mSerialPort == null) {
                mSerialPort = new SerialPort(new File(path),
                        Integer.parseInt(para), 'N', 0);
            }
        }

        @Override
        public int read(byte[] buf, int size, int timeout) throws IOException {
            int tmo = timeout;
            int tmpi = 0, ret = 0, ret2 = 0, ret3 = 0;

            if (timeout < 1) {
                if (mSerialPort != null && buf != null && size > 0) {
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
                if (size > mSerialPort.getInputStream().available()
                        && tmo > 0) {
                    return 0;
                }

                tmpi = size > mSerialPort.getInputStream().available() ? mSerialPort
                        .getInputStream().available() : size;

                ret = mSerialPort.getInputStream().read(buf, 0, tmpi);
                ret3 = ret;
            } else {

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
            } else {
            }
            return ret;
        }

        @Override
        public void close() {
            if (mSerialPort != null) {
                Log.d(TAG, "mAndroidSerialPortApi close!!!!");
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
}
