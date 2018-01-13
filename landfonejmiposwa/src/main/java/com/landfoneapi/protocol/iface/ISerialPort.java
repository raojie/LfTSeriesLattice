package com.landfoneapi.protocol.iface;

import java.io.IOException;

/**
 * 实现接口时要注意线程同步，synchronized 
 * @author thinkpad
 *
 */
public interface ISerialPort {
	public void open(String path, String para) throws IOException;
	public int read(byte[] buf, int size, int timeout) throws IOException;
	public int write(byte[] buf, int size, int timeout) throws IOException;
	public int readAvailable() throws IOException;
	public void close();
	public void flush();
}
