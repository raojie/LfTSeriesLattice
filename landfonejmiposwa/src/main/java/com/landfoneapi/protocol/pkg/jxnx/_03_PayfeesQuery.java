package com.landfoneapi.protocol.pkg.jxnx;

import com.landfoneapi.protocol.pkg.TradeType;
import com.landfoneapi.protocol.pkg._03_Common;
import com.landfone.common.utils.LfException;
import com.landfone.common.utils.LfUtils;

/**
 * 缴费查询
 */
public class _03_PayfeesQuery extends _03_Common {

	private String AreaCode = "";//4,地区号
	private String IndustryCode = "";//3，行业号
	private String ContentCode = "";//3，缴费内容码
	private String UserNo = "";//20,用户号
	//构造函数
	public _03_PayfeesQuery(){
		this.setTradeType(TradeType.PAYFEES_QUERY);
	}

	public void reset(){
		super.reset();
		this.setTradeType(TradeType.PAYFEES_QUERY);

	}

	public byte[] getBytes(){
		byte[] retbb = new byte[128+2];
		byte[] retbb2 = null;
		int i=0;

		//拷贝指令
		LfUtils.memcpy(retbb, i, this.getTradeType().getCode().getBytes(), 0, this.getTradeType().getCode().getBytes().length);
		i+=2;
		//分隔符
		LfUtils.memcpy(retbb, i,FS,0, FS.length);
		i+=FS.length;

		//拷贝数据
		LfUtils.memcpy(retbb, i, this.getAreaCode().getBytes(), 0, this.getAreaCode().getBytes().length);
		i+=this.getAreaCode().getBytes().length;
		//分隔符
		LfUtils.memcpy(retbb, i,FS,0, FS.length);
		i+=FS.length;

		//拷贝数据
		LfUtils.memcpy(retbb, i, this.getIndustryCode().getBytes(), 0, this.getIndustryCode().getBytes().length);
		i+=this.getIndustryCode().getBytes().length;
		//分隔符
		LfUtils.memcpy(retbb, i,FS,0, FS.length);
		i+=FS.length;

		//拷贝数据
		LfUtils.memcpy(retbb, i, this.getContentCode().getBytes(), 0, this.getContentCode().getBytes().length);
		i+=this.getContentCode().getBytes().length;
		//分隔符
		LfUtils.memcpy(retbb, i,FS,0, FS.length);
		i+=FS.length;

		//拷贝数据
		LfUtils.memcpy(retbb, i, this.getUserNo().getBytes(), 0, this.getUserNo().getBytes().length);
		i+=this.getUserNo().getBytes().length;
		//分隔符
		LfUtils.memcpy(retbb, i,FS,0, FS.length);
		i+=FS.length;

		if(i>0 && i<128) {
			retbb2 = new byte[i];
			LfUtils.memcpy(retbb2, 0,retbb,0, i);
		}
		return retbb2;
	}

	public String getAreaCode() {
		return AreaCode;
	}

	public void setAreaCode(String areaCode) throws LfException {
		if(areaCode==null || areaCode.getBytes().length>4){
			throw new LfException(-8,"areaCode length error, need <=4");
		}
		AreaCode = areaCode;
	}

	public String getIndustryCode() {
		return IndustryCode;
	}

	public void setIndustryCode(String industryCode) throws LfException {
		if(industryCode==null || industryCode.getBytes().length>3){
			throw new LfException(-8,"industryCode length error, need <=3");
		}
		IndustryCode = industryCode;
	}

	public String getContentCode() {
		return ContentCode;
	}

	public void setContentCode(String contentCode) throws LfException {
		if(contentCode==null || contentCode.getBytes().length>3){
			throw new LfException(-8,"contentCode length error, need <=3");
		}
		ContentCode = contentCode;
	}

	public String getUserNo() {
		return UserNo;
	}

	public void setUserNo(String userNo)  throws LfException {
		if(userNo==null || userNo.getBytes().length>20){
			throw new LfException(-8,"userNo length error, need <=20");
		}
		UserNo = userNo;
	}
}
