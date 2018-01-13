package com.landfone.enums;

/**
 * Created by yelz on 2015/9/21.
 */
public enum Errs {
	NORMAL(0,"OK","成功"),
	UNKNOW(1,"UNKNOW","未知错误"),
	SERIAL_OPEN_ERR(-1,"Serial port err!","串口打开失败"),
	SERIAL_IFACE_NULL(-2,"Serial port IFace not set!","串口接口未指定"),
	SERIAL_TIME_OUT(-3,"COM ERR","串口收(发)超时"),
	SERIAL_IO_ERR(-4,"Serial port IO ERR!","串口IO错误"),
	PROTOCOL_ID_ERR(-5,"Protocol ID err!","P ID错误"),
	PROTOCOL_LEN_ERR(-6,"Protocol LEN err!","P LEN错误"),
	POS_REPLY_INVALID(-7,"Reply Ivalid!","P 回复错误"),
	NET_REPLY_ERR(-8,"NET RECV TIMEOUT!","网络接收超时"),
	OTHER_ERR(-99,"Other err","其它错误");

	private int value = 1;
	private String desc = "UNKNOW";
	private String desc_cn = "未知错误";
	Errs(int v, String desc, String desc_cn){
		this.value = v;
		this.desc = desc;
		this.desc_cn = desc_cn;
	}
	public String getMsg(){
		return ""+this.value+","+this.desc+","+this.desc_cn;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getDesc_cn() {
		return desc_cn;
	}
	public void setDesc_cn(String desc_cn) {
		this.desc_cn = desc_cn;
	}
}