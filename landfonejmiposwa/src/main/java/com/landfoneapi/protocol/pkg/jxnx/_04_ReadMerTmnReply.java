package com.landfoneapi.protocol.pkg.jxnx;

import com.landfoneapi.mispos.MISPOS;
import com.landfoneapi.mispos.UART_PROTOCOL;
import com.landfoneapi.protocol.pkg.REPLY;
import com.landfoneapi.protocol.pkg.TradeType;
import com.landfone.common.utils.LfUtils;

/**
 * 存折补登返回
 */
public class _04_ReadMerTmnReply extends REPLY {

	private TradeType reply_type = TradeType.NONE;//指令代码
	private String mer = "";//商户代码
	private String tmn = "";//终端号
	private String mer_cn_name = "";//商户中文名
	private String mer_en_name = "";//商户英文名//

	public void print(){
		super.print();
		dbg_mPrintf("reply_type(指令码):"+reply_type.getCode());
	}
	public int Unpack(UART_PROTOCOL ptOutPara){
		int i=0,j=0;
		int tmpsize = 0;
		byte[] tail = null;
		byte[] head = null;
		byte[] tmp = null;
		i = super.Unpack(ptOutPara);
		if(ptOutPara.data[i]==MISPOS.PACK_FS){
			tmpsize +=1;
			head = new byte[1];
			head[0] = (byte)'0';
		}
		if(ptOutPara.datalen>1
				&& ptOutPara.data[ptOutPara.datalen-1]==MISPOS.PACK_FS){//尾部的分隔符
			tmpsize +=2;
			tail = new byte[2];
			tail[0] = '0';
			tail[1] = 0;
		}
		tmp = new byte[ptOutPara.datalen+tmpsize];
		if(head!=null){
			tmp[0] = head[0];
			j++;
		}
		dbg_mPrintf("tmpsize:"+tmpsize);
		if(tail!=null){
			tmp[j+ptOutPara.datalen+0] = tail[0];
			tmp[j+ptOutPara.datalen+1] = tail[1];
		}
		LfUtils.memcpy(tmp, j, ptOutPara.data, i, ptOutPara.datalen);
		for(i=0;i<tmp.length;i++){
			if(tmp[i]==MISPOS.PACK_FS){
				tmp[i] = '#';
			}
		}

		String tmpstr = newString(tmp);
		//dbg_mPrintf("tmpstr:"+tmpstr);
		String[] tmpstrbb = null;
		if(tmpstr!=null){
			tmpstrbb = tmpstr.split("#");
		}
		if(tmpstrbb!=null){
			for(j=0;j<tmpstrbb.length;j++){
				dbg_mPrintf("%d, %s",j,tmpstrbb[j]==null?"":tmpstrbb[j]);
			}
			i = 0;
			if(tmpstrbb.length>i){
				if(tmpstrbb[i]!=null && tmpstrbb[i].equals(TradeType.GET_MERTMN.getCode())){
					this.reply_type = TradeType.GET_MERTMN;//指令代码
				}
			}i++;

			if(tmpstrbb.length>i){	this.mer = tmpstrbb[i];}i++;//商户代码
			if(tmpstrbb.length>i){	this.tmn = tmpstrbb[i];}i++;//终端号
			if(tmpstrbb.length>i){	this.mer_cn_name = tmpstrbb[i];}i++;//商户中文名
			if(tmpstrbb.length>i){	this.mer_en_name = tmpstrbb[i];}i++;//商户英文名

		}
		return i;
	}

	public String getMer() {
		return mer;
	}

	public String getTmn() {
		return tmn;
	}

	public String getMer_cn_name() {
		return mer_cn_name;
	}

	public String getMer_en_name() {
		return mer_en_name;
	}
}
