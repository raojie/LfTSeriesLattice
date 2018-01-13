package com.landfoneapi.protocol.pkg.junpeng;

import com.landfone.common.utils.LfUtils;
import com.landfoneapi.protocol.pkg.TradeType;
import com.landfoneapi.protocol.pkg._03_Common;

public class _03_Settings extends _03_Common {

	private boolean useOfflineTrade = true;//是否使用电子现金

	//构造函数
	public _03_Settings(){
		this.setTradeType(TradeType.SETTINGS);
	}

	public void reset(){
		super.reset();
		this.setTradeType(TradeType.SETTINGS);
	}

	public byte[] getBytes(){
		byte[] retbb = new byte[128*4];
		byte[] retbb2 = null;
		int i=0;
		byte[] settings = new byte[16];

		settings[0] = isUseOfflineTrade() ==true?(byte)0x00:(byte)0x01;


		LfUtils.memcpy(retbb, i, getTradeType().getCode().getBytes(), 0, getTradeType().getCode().length());
		i+=2;
		//LfUtils.memcpy(retbb, i,FS,0, FS.length);
		//i+=FS.length;

		//Track3
		i+= LfUtils.memcpy(retbb, i, settings, 0, settings.length);
		//分隔符
		i+= LfUtils.memcpy(retbb, i,FS,0, FS.length);




		if(i>0 && i<retbb.length) {
			retbb2 = new byte[i];
			LfUtils.memcpy(retbb2, 0,retbb,0, i);
		}
		return retbb2;

	}


	public boolean isUseOfflineTrade() {
		return useOfflineTrade;
	}

	public void setUseOfflineTrade(boolean useOfflineTrade) {
		this.useOfflineTrade = useOfflineTrade;
	}
}
