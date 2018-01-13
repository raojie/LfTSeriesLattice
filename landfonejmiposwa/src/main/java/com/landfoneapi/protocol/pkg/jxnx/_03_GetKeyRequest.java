package com.landfoneapi.protocol.pkg.jxnx;

import com.landfoneapi.protocol.pkg.TradeType;
import com.landfoneapi.protocol.pkg._03_Common;
import com.landfone.common.utils.LfUtils;

/**
 * 获取按键
 */
public class _03_GetKeyRequest extends _03_Common {


	//构造函数
	public _03_GetKeyRequest(){
		this.setTradeType(TradeType.GETKEY_REQ);
	}

	public void reset(){
		super.reset();
		this.setTradeType(TradeType.GETKEY_REQ);

	}

	public byte[] getBytes(){
		byte[] retbb = new byte[128];
		byte[] retbb2 = null;
		int i=0;

		//拷贝指令
		LfUtils.memcpy(retbb, i, this.getTradeType().getCode().getBytes(), 0, this.getTradeType().getCode().getBytes().length);
		i+=2;
		//分隔符
		LfUtils.memcpy(retbb, i,FS,0, FS.length);
		i+=FS.length;

		//拷贝数据
		LfUtils.memcpy(retbb, i, this.getTimeout(), 0, this.getTimeout().length);
		i+= this.getTimeout().length;
		//分隔符
		LfUtils.memcpy(retbb, i,FS,0, FS.length);
		i+=FS.length;



		if(i>0 && i<128) {
			retbb2 = new byte[i];
			LfUtils.memcpy(retbb2, 0,retbb,0, i);
		}
		return retbb2;
	}



}
