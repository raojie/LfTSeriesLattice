package com.landfoneapi.protocol.iface;

/**
 * 实现接口时要注意线程同步，synchronized 
 * @author thinkpad
 *
 */
public interface IDisplay {
	public void Display(String code, String desc, String msg);
}
