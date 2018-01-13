package com.landfone.common.utils;

import java.io.IOException;

/**
 * Created by Administrator on 2017/7/27.
 */

public interface IUsbPort {
    public void open(String path, String para) throws IOException, LfException;
    public int read(byte[] buf, int size, int timeout) throws IOException;
    public int write(byte[] buf, int size, int timeout) throws IOException;
    public int readAvailable() throws IOException;
    public void close();
    public void flush();
}
