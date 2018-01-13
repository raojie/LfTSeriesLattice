package com.landfone.common.utils;

import com.landfone.common.utils.LfException;

import java.io.UnsupportedEncodingException;
import java.util.Observable;

public class LfObservable extends Observable {
	public static final int SUCCESS = 0;
	public static final int INIT_ALL_ERR = -6000;          //初始化不成功
	public static final int STATE_PARA_ERR = INIT_ALL_ERR+1;				   //当前参数错误
	public static final int STATE_IDLE_ERR = STATE_PARA_ERR+1;				   //当前状态错误,不在IDLE状态
	public static final int STATE_QUERY_ERR = STATE_IDLE_ERR+1;	           //当前状态错误,不在QUERY状态
	public static final int STATE_PURCHASE_ERR = STATE_QUERY_ERR+1;            //当前状态错误,不在PURCHASE状态
	public static final int STATE_LOAD_ERR = STATE_PURCHASE_ERR+1;				   //当前状态错误,不在LOAD状态
	public static final int STATE_SIGNIN_ERR = STATE_LOAD_ERR+1;			   //当前状态错误,不在SIGNIN状态
	public static final int STATE_SETTLE_ERR = STATE_SIGNIN_ERR+1;			   //当前状态错误,不在SETTLE状态
	public static final int STATE_REVERT_ERR = STATE_SETTLE_ERR+1;			   //当前状态错误,不在REVERT状态
	public static final int STATE_BATCHUP_ERR = STATE_REVERT_ERR+1;			   //当前状态错误,不在BATCHUP状态
	public static final int LOCK_ERR = STATE_BATCHUP_ERR+1;                      //当前正在请求消费、查询
	public static final int CANCEL_ERR = LOCK_ERR+1;                    //已经刷卡不能取消
	public static final int RECV_BUSY_ERR = CANCEL_ERR+1;				   //已经接收了数据
	public static final int SEND_BUSY_ERR = RECV_BUSY_ERR+1;				   //已经发送了数据
	public static final int CONNECT_ERR = SEND_BUSY_ERR+1;                   //连接错误
	public static final int CONNECT_ALREADY_ERR = CONNECT_ERR+1;           //正在请求连接
	public static final int COMMU_TYPE_ERR = CONNECT_ALREADY_ERR+1;                //透传类型错误
	public static final int COMMU_ERR = COMMU_TYPE_ERR+1;					   //通信异常



	//////////////////////////////DEBUG///////////////////////////
	public boolean dbgFlag = true;
	public void dbg_printfWHex(byte[] data, int len ,String format, Object... args)
	{
		int minlen = 0;
		String msg = "";
		msg = String.format(format, args);
		minlen = len<data.length?len:data.length;
		System.out.print(msg+":");
		for (int i = 0; i < minlen; i ++) {
			System.out.printf("%02X ", data[i]);
		}
		System.out.println("");
	}

	public void dbg_mPrintfWHex(byte[] data, int len ,String format, Object... args)
	{
		if(this.dbgFlag){
			dbg_printfWHex(data,len,format,args);
		}
	}
	public void dbg_printf(String format, Object... args)
	{
		int minlen = 0;
		String msg = "";
		msg = String.format(format, args);
		System.out.println(msg);

	}
	public void dbg_mPrintf(String format, Object... args)
	{
		if(this.dbgFlag){
			dbg_printf(format,args);
		}
	}
	//获取时间
	public long lf_Getsystime(){
		return System.currentTimeMillis();
	}
	//sleep
	public void Sleep(int ms) throws LfException{
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
			throw new LfException(e,-1,"sleep error");
		}
	}
	// 内部API
	public long GetSysCount()
	{
		long count;
		count = lf_Getsystime();
		return count;
	}
	public String newString(byte[] inbuff){
		if(inbuff==null){
			return null;
		}else{
			int validlen = 0;
			for(int i=0;i<inbuff.length;i++){
				if(inbuff[i]==0x00){
					validlen = i;//不需要\0
					break;
				}
			}
			if(validlen!=0){
				byte[] tmp = new byte[validlen];
				System.arraycopy(inbuff, 0, tmp, 0, validlen);
				String ret = null;
				try {
					ret = new String(tmp,"GBK");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				return ret;
			}
		}
		return null;
	}

}
