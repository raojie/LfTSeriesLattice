package com.landfoneapi.protocol.pkg.jxnx;

import com.landfoneapi.protocol.pkg.TradeType;
import com.landfoneapi.protocol.pkg._03_Common;
import com.landfone.common.utils.LfException;
import com.landfone.common.utils.LfUtils;

import java.io.UnsupportedEncodingException;

/**
 * 缴费查询
 */
public class _03_PassbookToCard extends _03_Common {

	private String Amount = "";//12
	private String Track2 = "";//40
	private String Track3 = "";//110


	private String clientNo = "";
	private String operator = "";
	private String serialNo = "";

	private String RecvCardNo = "";//30
	private String RecvName = "";//20
	private String PassbookAccount = "";//20
	private String Keyboard = "";//1
	private String RecvCardType = "";//3字节收款卡类型
	//构造函数
	public _03_PassbookToCard(){
		this.setTradeType(TradeType.PASSBOOK_TO_CARD);
	}

	public void reset(){
		super.reset();
		this.setTradeType(TradeType.PASSBOOK_TO_CARD);

	}

	public byte[] getBytes() throws UnsupportedEncodingException {
		byte[] retbb = new byte[128*3];
		byte[] retbb2 = null;
		int i=0;
		byte[] tmpbb_1 = new byte[24];
		byte[] commonbb = null;
/*
		//拷贝指令
		LfUtils.memcpy(retbb, i, this.getTradeType().getCode().getBytes(), 0, this.getTradeType().getCode().getBytes().length);
		i+=2;
		//分隔符
		i+=LfUtils.memcpy(retbb, i,FS,0, FS.length);
		*/
		commonbb = super.getBytes();
		if(commonbb!=null) {
			i += LfUtils.memcpy(retbb, i, commonbb, 0, commonbb.length);
		}
		//--------金额
		String.format("%120s", this.Amount);
		tmpbb_1 = this.Amount.getBytes();
		if(tmpbb_1!=null && tmpbb_1.length>=12) {
			i += LfUtils.memcpy(retbb, i, tmpbb_1, 0, 12);
		}
		i+=LfUtils.memcpy(retbb, i, FS, 0, FS.length);

		//Track2
		i+=LfUtils.memcpy(retbb, i, Track2.getBytes(), 0, Track2.getBytes().length);
		//分隔符
		i+=LfUtils.memcpy(retbb, i,FS,0, FS.length);
		//Track3
		i+=LfUtils.memcpy(retbb, i, Track3.getBytes(), 0, Track3.getBytes().length);
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
		i+=LfUtils.memcpy(retbb, i, this.getRecvCardNo().getBytes(), 0, this.getRecvCardNo().getBytes().length);
		//分隔符
		i+=LfUtils.memcpy(retbb, i,FS,0, FS.length);

		//拷贝数据
		i+=LfUtils.memcpy(retbb, i, this.getRecvName().getBytes("GBK"), 0, this.getRecvName().getBytes("GBK").length);
		//分隔符
		i+=LfUtils.memcpy(retbb, i,FS,0, FS.length);

		//拷贝数据
		i+=LfUtils.memcpy(retbb, i, this.getPassbookAccount().getBytes(), 0, this.getPassbookAccount().getBytes().length);
		//分隔符
		i+=LfUtils.memcpy(retbb, i,FS,0, FS.length);
		//FS.length;

		//拷贝数据
		i+=LfUtils.memcpy(retbb, i, this.getKeyboard().getBytes(), 0, this.getKeyboard().getBytes().length);
		//分隔符
		i+=LfUtils.memcpy(retbb, i,FS,0, FS.length);

		//拷贝数据
		i+=LfUtils.memcpy(retbb, i, this.getRecvCardNo().getBytes(), 0, this.getRecvCardNo().getBytes().length);
		//分隔符
		i+=LfUtils.memcpy(retbb, i,FS,0, FS.length);

		if(i>0 && i<(128*3)) {
			retbb2 = new byte[i];
			LfUtils.memcpy(retbb2, 0,retbb,0, i);
		}
		return retbb2;
	}

	public String getRecvCardNo() {
		return RecvCardNo;
	}

	public void setRecvCardNo(String recvCardNo) throws LfException {
		if(recvCardNo ==null || recvCardNo.getBytes().length>30){
			throw new LfException(-8,"recvCardNo length error, need <=30");
		}
		RecvCardNo = recvCardNo;
	}

	public String getRecvName() {
		return RecvName;
	}

	public void setRecvName(String recvName) throws LfException {
		try {
			if(recvName ==null || recvName.getBytes("GBK").length>20){
                throw new LfException(-8,"recvName length error, need <=20");
            }
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new LfException(-8,"recvName getBytes(\"GBK\") error");
		}
		RecvName = recvName;
	}

	public String getPassbookAccount() {
		return PassbookAccount;
	}

	public void setPassbookAccount(String passbookAccount) throws LfException {
		if(passbookAccount ==null || passbookAccount.getBytes().length>20){
			throw new LfException(-8,"passbookAccount length error, need <=20");
		}
		PassbookAccount = passbookAccount;
	}

	public String getKeyboard() {
		return Keyboard;
	}

	public void setKeyboard(String keyboard)  throws LfException {
		if(keyboard ==null || keyboard.getBytes().length!=1){
			throw new LfException(-8,"keyboard length error, need ==1");
		}
		Keyboard = keyboard;
	}

	public String getAmount() {
		return Amount;
	}

	public void setAmount(String amount)   throws LfException {
		if(amount==null || amount.getBytes().length>12){
			throw new LfException(-8,"amount error, need <=12");
		}
		Amount = amount;
	}

	public String getTrack2() {
		return Track2;
	}

	public void setTrack2(String track2)   throws LfException {
		if(track2 ==null || track2.getBytes().length>40){
			throw new LfException(-8,"track2 length error, need <=40");
		}
		Track2 = track2;
	}

	public String getTrack3() {
		return Track3;
	}

	public void setTrack3(String track3)   throws LfException {
		if(track3 ==null || track3.getBytes().length>110){
			throw new LfException(-8,"track3 length error, need <=110");
		}
		Track3 = track3;
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
		if(operator.getBytes().length!=15){
			throw new LfException(-8,"operator length error, need 15");
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



	public String getRecvCardType() {
		return RecvCardType;
	}

	public void setRecvCardType(String recvCardType)    throws LfException {
		if(recvCardType ==null || recvCardType.getBytes().length>3){
			throw new LfException(-8,"recvCardType length error, need <=3");
		}
		RecvCardType = recvCardType;
	}
}
