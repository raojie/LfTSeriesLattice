package com.landfoneapi.protocol.pkg.jxnx;

import com.landfoneapi.mispos.MISPOS;
import com.landfoneapi.mispos.UART_PROTOCOL;
import com.landfoneapi.protocol.pkg.REPLY;
import com.landfoneapi.protocol.pkg.TradeType;
import com.landfone.common.utils.LfUtils;

/**
 * 缴费应用代码下载返回
 */
public class _04_DownloadAppcodeReply extends REPLY {

	private TradeType reply_type = TradeType.NONE;//指令代码
	private String mer = "";//商户代码
	private String tmn = "";//终端号
	private String mer_cn_name = "";//商户中文名
	private String mer_en_name = "";//商户英文名

	private String returnData = "";//缴费应用代码下载返回数据
	private String FollowUpPkg  = "0";//是否有后续包

	public void print(){
		super.print();
		dbg_mPrintf("reply_type(指令码):"+reply_type.getCode());
		dbg_mPrintf("mer:"+mer);
		dbg_mPrintf("tmn:"+tmn);
		dbg_mPrintf("mer_cn_name:"+mer_cn_name);
		dbg_mPrintf("mer_en_name:"+mer_en_name);
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
			/*for(j=0;j<tmpstrbb.length;j++){
				dbg_mPrintf("%d, %s",j,tmpstrbb[j]==null?"":tmpstrbb[j]);
			}*/
			i = 0;
			if(tmpstrbb.length>i){
				if(tmpstrbb[i]!=null && tmpstrbb[i].equals(TradeType.DOWNLOADPAYCODE.getCode())){
					this.reply_type = TradeType.DOWNLOADPAYCODE;//指令代码
				}
			}
			i++;
			if(tmpstrbb.length>i){
				this.mer = tmpstrbb[i];//商户代码
			}i++;
			if(tmpstrbb.length>i){
				this.tmn = tmpstrbb[i];//终端号
			}i++;
			if(tmpstrbb.length>i){
				this.mer_cn_name = tmpstrbb[i];//商户中文名
			}i++;
			if(tmpstrbb.length>i){
				this.mer_en_name = tmpstrbb[i];//商户英文名
			}i++;
			if(tmpstrbb.length>i){
				this.returnData += tmpstrbb[i];//返回数据,可能有分包处理，所以需要累加
			}i++;
			if(tmpstrbb.length>i){
				this.FollowUpPkg = tmpstrbb[i];//是否有后续包
			}i++;
		}
		return i;
	}

	public String getReturnData() {
		return returnData;
	}

	public String getFollowUpPkg() {
		return FollowUpPkg;
	}
/*
	private byte[][] __upack(byte[] data, int offset, int len){
		int data_cnt = 0;
		byte[][] retdata = null;
		int[] offsetFS = new int[32];
		int validFS = 0,f=0;
		if(data!=null && (data.length-offset)>=len){
			for(int i=offset;i<(offset+len);i++){
				if(data[i]==MISPOS.PACK_FS){
					data_cnt++;

					validFS++;
					if(f<32){offsetFS[f++]=i;};
				}
			}
			if(data_cnt>0&&data_cnt<10){
				retdata = new byte[data_cnt][256];
			}
			for(int i=offset,j=0;i<(offset+len);i++){
				if(data[i]==MISPOS.PACK_FS){
					data_cnt++;
					j++;
				}
			}


		}else{

		}
	}*/
}
