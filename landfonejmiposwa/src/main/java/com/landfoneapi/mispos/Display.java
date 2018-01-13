package com.landfoneapi.mispos;

public class Display {

	public String type = "";
	public String msg = "";

	public void reset(){
		this.type = "";
		this.msg  = "";
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
