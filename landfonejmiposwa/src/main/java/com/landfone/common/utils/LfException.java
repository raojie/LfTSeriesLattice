package com.landfone.common.utils;

public class LfException extends Exception {

	private String message = ""; //定义String类型变量 
	private int errcode = 0;
	private Exception e = null;
	public LfException(String ErrorMessagr)
	{  //父类方法       
		errcode = 0;
		message = ErrorMessagr;
	}

	public LfException(int errcode ,String ErrorMessagr)
	{  //父类方法      
		//this.e = new Exception();
		message = ErrorMessagr;
		this.errcode = errcode;
	}
	public LfException(Exception pe, int errcode ,String ErrorMessagr)
	{  //父类方法       
		message = ErrorMessagr;
		this.errcode = errcode;
		this.e = pe;
	}
	public LfException(Errs err)
	{
		//this.e = new Exception();
		this.message = err.getMsg();
		this.errcode = err.getValue();
	}
	public LfException(Errs err ,String ErrorMessagr)
	{
		//this.e = new Exception();
		message = ErrorMessagr;
		this.errcode = err.getValue();
	}
	public String getMessage(){
		//覆盖getMessage()方法  
		return message;
	}
	public int getErrcode(){
		return errcode;
	}
	public void printStackTrace(){
		printTrace();
		super.printStackTrace();

	}
	public void printTrace(){
		String errcodehex = "";
		errcodehex = String.format("0x%x", this.errcode);
		System.err.println("LFEXCEPTION:lf-errcode:"+errcodehex+", msg:"+this.message);
		if(e!=null){
			this.e.printStackTrace();
		}
	}
}
