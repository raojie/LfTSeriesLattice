package com.landfoneapi.protocol;

public interface ILfWACallback {
	//二维码返回
	public void onQrCode(String msg, String qrcode);
	//订单查询返回
	public void onOrderQuery(int state, String msg);
	//撤销订单返回
	public void onOrderCancel(int state, String msg);
	//错误码返回
	public void onErrorStr(String msg);
}
