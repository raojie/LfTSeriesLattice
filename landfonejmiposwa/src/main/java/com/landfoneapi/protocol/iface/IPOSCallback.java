package com.landfoneapi.protocol.iface;

import com.landfoneapi.protocol.pkg.REPLY;


public interface IPOSCallback {


	//传入回调类对象（父类对象），和对象的class
	public boolean EventCallback(REPLY mREPLY, Object hdrclass);
}
