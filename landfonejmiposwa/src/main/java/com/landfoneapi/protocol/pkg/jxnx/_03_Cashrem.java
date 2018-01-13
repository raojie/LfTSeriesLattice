package com.landfoneapi.protocol.pkg.jxnx;

import com.landfoneapi.protocol.pkg.TradeType;
import com.landfoneapi.protocol.pkg._03_Common;
import com.landfone.common.utils.LfException;
import com.landfone.common.utils.LfUtils;

import java.io.UnsupportedEncodingException;

/**
 * 现金汇款
 */
public class _03_Cashrem extends _03_Common {

	private String amount = "";
	private String track2 = "";
	private String track3 = "";
	private String clientNo = "";
	private String operator = "";
	private String serialNo = "";

	private String recieverCardNo = "";//30
	private String recieverName = "";//20

	private String keyboard = "";//1

	//构造函数
	public _03_Cashrem(){
		this.setTradeType(TradeType.CASHREM);
	}

	public void reset(){
		super.reset();
		this.setTradeType(TradeType.CASHREM);
		try {
			this.setAmount("");
			this.setClientNo("");
			this.setOperator("");
			this.setMer_cn_name("");
			this.setMer_en_name("");
			this.setSerialNo("");
			this.setTrack2("");
			this.setTrack3("");
		} catch (LfException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public byte[] getBytes() throws UnsupportedEncodingException {
		byte[] retbb = new byte[128*2];
		byte[] retbb2 = null;
		int i=0;
		byte[] tmpbb_1 = new byte[24];
		byte[] commonbb = null;


		commonbb = super.getBytes();
		if(commonbb!=null) {
			i += LfUtils.memcpy(retbb, i, commonbb, 0, commonbb.length);
		}
		//--------金额
		String.format("%120s", this.amount);
		tmpbb_1 = this.amount.getBytes();
		if(tmpbb_1!=null && tmpbb_1.length>=12) {
			i += LfUtils.memcpy(retbb, i, tmpbb_1, 0, 12);
		}
		i+=LfUtils.memcpy(retbb, i, FS, 0, FS.length);

		//Track2
		i+=LfUtils.memcpy(retbb, i, track2.getBytes(), 0, track2.getBytes().length);
		//分隔符
		i+=LfUtils.memcpy(retbb, i,FS,0, FS.length);
		//Track3
		i+=LfUtils.memcpy(retbb, i, track3.getBytes(), 0, track3.getBytes().length);
		//分隔符
		i+=LfUtils.memcpy(retbb, i,FS,0, FS.length);

		//Track3
		i+=LfUtils.memcpy(retbb, i, clientNo.getBytes(), 0, clientNo.getBytes().length);
		//分隔符
		i+=LfUtils.memcpy(retbb, i,FS,0, FS.length);
		//Track3
		i+=LfUtils.memcpy(retbb, i, operator.getBytes(), 0, operator.getBytes().length);
		//分隔符
		i+=LfUtils.memcpy(retbb, i,FS,0, FS.length);
		//Track3
		i+=LfUtils.memcpy(retbb, i, serialNo.getBytes(), 0, serialNo.getBytes().length);
		//分隔符
		i+=LfUtils.memcpy(retbb, i,FS,0, FS.length);


		//拷贝数据
		i+=LfUtils.memcpy(retbb, i, recieverCardNo.getBytes(), 0, recieverCardNo.getBytes().length);
		//分隔符
		i+=LfUtils.memcpy(retbb, i,FS,0, FS.length);

		//拷贝数据
		i+=LfUtils.memcpy(retbb, i, recieverName.getBytes("GBK"), 0, recieverName.getBytes("GBK").length);
		//分隔符
		i+=LfUtils.memcpy(retbb, i,FS,0, FS.length);

		//拷贝数据
		i+=LfUtils.memcpy(retbb, i, keyboard.getBytes(), 0, keyboard.getBytes().length);
		//分隔符
		i+=LfUtils.memcpy(retbb, i,FS,0, FS.length);


		if(i>0 && i<retbb.length) {
			retbb2 = new byte[i];
			LfUtils.memcpy(retbb2, 0,retbb,0, i);
		}
		return retbb2;
	}

	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) throws LfException {
		if(amount.getBytes().length!=12){
			throw new LfException(-8,"amount length error, need 12");
		}
		this.amount = amount;
	}
	public String getTrack2() {
		return track2;
	}
	public void setTrack2(String track2) throws LfException {
		if(track2.getBytes().length>40){
			throw new LfException(-8,"track2 length error, need <=40");
		}
		this.track2 = track2;
	}
	public String getTrack3() {
		return track3;
	}
	public void setTrack3(String track3) throws LfException {
		if(track3.getBytes().length>110){
			throw new LfException(-8,"track3 length error, need <=110");
		}
		this.track3 = track3;
	}
	public String getClientNo() {
		return clientNo;
	}
	public void setClientNo(String clientNo) throws LfException {
		if(clientNo.getBytes().length>15){
			throw new LfException(-8,"clientNo length error, need <=15");
		}
		this.clientNo = clientNo;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) throws LfException {
		if(operator.getBytes().length>15){
			throw new LfException(-8,"operator length error, need <=15");
		}
		this.operator = operator;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) throws LfException {
		if(serialNo.getBytes().length>20){
			throw new LfException(-8,"serialNo length error, need <=20");
		}
		this.serialNo = serialNo;
	}

	public String getRecieverCardNo() {
		return recieverCardNo;
	}

	public void setRecieverCardNo(String recieverCardNo) throws LfException {
		if(recieverCardNo.getBytes().length>30){
			throw new LfException(-8,"recieverCardNo length error, need <=30");
		}
		this.recieverCardNo = recieverCardNo;
	}

	public String getRecieverName() {
		return recieverName;
	}

	public void setRecieverName(String recieverName) throws LfException {
		try {
			if(recieverName.getBytes("GBK").length>20){
                throw new LfException(-8,"recieverName length error, need <=20");
            }
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new LfException(-8,"recieverName getBytes(\"GBK\") error");
		}
		/*System.out.println(">>>>>>>recieverName("+recieverName.length()+"):"+recieverName);
		System.out.println(">>>>>>>recieverName bytes size"+recieverName.getBytes().length);
		try {
			System.out.println(">>>>>>>recieverName bytes size(GBK)"+recieverName.getBytes("GBK").length);
			System.out.println(">>>>>>>recieverName bytes size(UTF-8)"+recieverName.getBytes("UTF-8").length);
			System.out.println(">>>>>>>小明 bytes size(GBK)"+("小明").getBytes("GBK").length);
			System.out.println(">>>>>>>小明 bytes size(UTF-8)"+("小明").getBytes("UTF-8").length);

			byte[] tmp = recieverName.getBytes("GBK");
			System.out.println(">>>>>>>new String(b,GBK):"+(new String(tmp,"GBK")));
			tmp = recieverName.getBytes("UTF-8");
			System.out.println(">>>>>>>new String(b,UTF-8):"+(new String(tmp,"UTF-8")));
			tmp = recieverName.getBytes();
			System.out.println(">>>>>>>new String(b):"+(new String(tmp)));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}*/
		this.recieverName = recieverName;
	}

	public String getKeyboard() {
		return keyboard;
	}

	public void setKeyboard(String keyboard) throws LfException {
		if(keyboard.getBytes().length>1 || (keyboard.getBytes().length==1 &( !keyboard.equals("0")&& !keyboard.equals("1")))){
			throw new LfException(-8,"keyboard("+keyboard+") length error, need 1, value:'0'/'1'");
		}
		this.keyboard = keyboard;
	}
}
