package com.landfoneapi.protocol.pkg;

import com.landfoneapi.mispos.Display;
import com.landfoneapi.mispos.MISPOS;
import com.landfoneapi.mispos.UART_PROTOCOL;
import com.landfone.common.utils.Debug;

import java.util.Arrays;

/**
 * POS上发通知信息包
 * @author yelz
 *
 */
public class _07_Common extends Debug {

	private TradeType tradeType = TradeType.DISPLAY;//指令代码
	private Display dis = new Display();//提示信息

	public static final byte[] FS = {0x1C};

	public void reset(){
		dis.reset();

	}
	public int Unpack(UART_PROTOCOL ptOutPara){
		int i=0;
		if(ptOutPara.datalen<1){
			return i;
		}
		byte[] tmpcode = {'0','0'};
		if(ptOutPara.data[i]=='9' && ptOutPara.data[i+1]=='7'){//液晶显示透传
			setTradeType(TradeType.DISPLAY);
		}else if(ptOutPara.data[i]=='4' && ptOutPara.data[i+1]=='7'){//按键透传
			setTradeType(TradeType.GETKEY_KEYREPORT);
		}else{
			setTradeType(TradeType.NONE);
			return 0;
		}
		i +=2;
		String tmpstr2 = "";
		if(ptOutPara.data[i++]==MISPOS.PACK_FS){
			if(ptOutPara.data[i]!=MISPOS.PACK_FS){
				tmpstr2 = String.format("%c", ptOutPara.data[i++]);
				if(tmpstr2!=null){
					if(tmpstr2.equals("#")|| this.getTradeType() == TradeType.GETKEY_KEYREPORT){//按键
						setTradeType(TradeType.GETKEY_KEYREPORT);
						this.dis.setType("#");//表示按键透传
					}else{
						this.dis.setType(tmpstr2);//液晶显示类型
					}

				}
			}else{
				i++;
			}

			if(ptOutPara.data[i++]==MISPOS.PACK_FS){//ASCII字符串
				byte[] tmpbb_2 = new byte[1000];
				Arrays.fill(tmpbb_2, (byte)0);
				int j = 0;
				while(ptOutPara.data[i]!=MISPOS.PACK_FS
						&& ptOutPara.data[i]!=MISPOS.PACK_ETX && j<1000){
					tmpbb_2[j++] = ptOutPara.data[i++];
				}
				tmpstr2 = newString(tmpbb_2);
				if(tmpstr2!=null){
					this.dis.setMsg(tmpstr2);
				}
			}
		}else{
		}
		return i;
	}
	public Display getDis() {
		return dis;
	}
	public void setDis(Display dis) {
		this.dis = dis;
	}

	public TradeType getTradeType() {
		return tradeType;
	}

	public void setTradeType(TradeType tradeType) {
		this.tradeType = tradeType;
	}
}
