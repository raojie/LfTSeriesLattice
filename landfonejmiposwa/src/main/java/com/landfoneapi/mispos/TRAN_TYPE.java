package com.landfoneapi.mispos;
/**
 * //交易类型
 * @author yelz
 *
 */
public enum TRAN_TYPE {

	TRAN_TYPE_CONSUME(0x01),			//消费
	TRAN_TYPE_CONSUME_REVOKE(0x01),			//消费撤销
	TRAN_TYPE_BALANCE(0x01),					//余额查询
	TRAN_TYPE_PRE_AUTHORIZATION(0x01),		//预授权
	TRAN_TYPE_PRE_REVOKE(0x01),				//预授权撤销
	TRAN_TYPE_PRE_COMPLETE(0x01),				//预授权完成
	TRAN_TYPE_PRE_COMPLETE_REVOKE(0x01),		//预授权完成撤销
	TRAN_TYPE_RETURN_GOODS(0x01),				//退货
	TRAN_TYPE_PHONE(0x01),					//手机充值
	TRAN_TYPE_REFOUND(0x01),					//信用卡还款
	TRAN_TYPE_ICOFFLINE_FALSE(0x01),			//IC卡脱机失败
	TRAN_TYPE_ARQC_ERR(0x01),					//IC卡ARQC错
	TRAN_TYPE_TRANSFER_ACCOUNTS(0x01),		//卡卡转账
	TRAN_TYPE_SETTLE_ACCOUNTS_UNIONPAY(0x01),	//银联业务结算  
	TRAN_TYPE_GET_CONFIG(0x01),				//获取参数
	SIGN_IN_UNIONPAY(0x01),					//银联签到
	TRAN_TYPE_PRINT_NORMAL(0x01),				//普通交易打印
	TRADE_QUERY(0x01),			            //交易查询
	SETTLE_CONFIG_PRINT(0x01),				//取结算信息
	SETTLE_CONFIG_REPRINT(0x01),				//重打印
	TRAN_TYPE_HAND(0x01);

	private byte value = 0;
	private TRAN_TYPE(byte v){
		this.value = v;
	}
	private TRAN_TYPE(int v){
		this.value = (byte)v;
	}

}
