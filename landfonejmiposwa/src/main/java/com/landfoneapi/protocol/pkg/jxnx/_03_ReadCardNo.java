package com.landfoneapi.protocol.pkg.jxnx;

import com.landfoneapi.protocol.pkg.TradeType;
import com.landfoneapi.protocol.pkg._03_Common;
import com.landfone.common.utils.LfUtils;

/**
 * 存折补登
 */
public class _03_ReadCardNo extends _03_Common {

	//构造函数
	public _03_ReadCardNo(){
		this.setTradeType(TradeType.GET_CARDNO);
	}

	public void reset(){
		super.reset();
		this.setTradeType(TradeType.GET_CARDNO);

	}

	public byte[] getBytes(){
		byte[] retbb = null;
		int retlen = 0,i=0;

		//长度
		retlen +=this.getTradeType().getCode().getBytes().length;//指令代码
		retlen +=1;//分隔符
		//申请内存
		retbb = new byte[retlen];
		//拷贝数据
		LfUtils.memcpy(retbb, i, this.getTradeType().getCode().getBytes(), 0, this.getTradeType().getCode().getBytes().length);
		i+=2;
		//分隔符
		LfUtils.memcpy(retbb, i,FS,0, FS.length);
		i+=FS.length;

		return retbb;
	}
}
