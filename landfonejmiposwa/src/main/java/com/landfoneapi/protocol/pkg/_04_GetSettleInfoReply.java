package com.landfoneapi.protocol.pkg;

import com.landfoneapi.mispos.MISPOS;
import com.landfoneapi.mispos.UART_PROTOCOL;
import com.landfone.common.utils.LfUtils;

/**
 * Created by yelz on 2015/9/30.
 */
public class _04_GetSettleInfoReply extends REPLY {

    private TradeType reply_type = TradeType.NONE;//指令代码
    public String mer = "";//商户代码
    public String tmn = "";//终端号
    public String mer_cn_name = "";//商户中文名
    private String mer_en_name = "";//商户英文名

    private String transacionBatchNo = "";//交易批次号
    public String transacionDatetime = "";//交易日期和时间
    private String civilCardSettleEqualFlag = "";//参数8内卡结帐平标志
    public String civilCardPayCnt = "";//参数9内卡消费笔数
    public String civilCardPaySum = "";//参数10内卡消费金额
    private String civilCardRefundCnt = "";//参数11内卡退货笔数
    private String civilCardRefundSum = "";//参数12内卡退货金额
    private String civilCardAuthCompleteOnlineCnt = "";//参数13内卡预授完成(联机)笔数
    private String civilCardAuthCompleteOnlineSum = "";//参数14内卡预授完成(联机)金额
    private String civilCardAuthCompleteOffOnlineCnt = "";//参数15内卡预授完成(离线)笔数
    private String civilCardAuthCompleteOffOnlineSum = "";//参数16内卡预授完成(离线)金额
    private String civilCardPayOffOnlineCnt = "";//参数17内卡离线交易笔数
    private String civilCardPayOffOnlineSum = "";//参数18内卡离线交易金额
    private String foreiCardSettleEqualFlag = "";//参数19外卡结帐平标志
    public String foreiCardPayCnt = "";//参数20外卡消费笔数
    public String foreiCardPaySum = "";//参数21外卡消费金额
    private String foreiCardRefundCnt = "";//参数22外卡退货笔数
    private String foreiCardRefundSum = "";//参数23外卡退货金额
    private String foreiCardAuthCompleteOnlineCnt = "";//参数24外卡预授完成(联机)笔数
    private String foreiCardAuthCompleteOnlineSum = "";//参数25外卡预授完成(联机)金额
    private String foreiCardAuthCompleteOffOnlineCnt = "";//参数26外卡预授完成(离线)笔数
    private String foreiCardAuthCompleteOffOnlineSum = "";//参数27外卡预授完成(离线)金额
    private String foreiCardPayOffOnlineCnt = "";//参数28外卡离线交易笔数
    private String foreiCardPayOffOnlineSum = "";//参数29外卡离线交易金额

    private String RemarkInfo = "";//备注信息
    private String SpecInfoField = "";//特定信息域
    private String PayEntryNo = "";//款台号
    private String PosWaterNo = "";//收银流水号

    public int Unpack(UART_PROTOCOL ptOutPara) {
        int i = 0, j = 0;
        int tmpsize = 0;
        byte[] tail = null;
        byte[] head = null;
        byte[] tmp = null;
        i = super.Unpack(ptOutPara);
        if (ptOutPara.data[0] == MISPOS.PACK_FS) {
            tmpsize += 1;
            head = new byte[1];
            head[0] = (byte) '0';
        }
        if (ptOutPara.datalen > 1
                && ptOutPara.data[ptOutPara.datalen - 1] == MISPOS.PACK_FS) {//尾部的分隔符
            tmpsize += 2;
            tail = new byte[2];
            tail[0] = '0';
            tail[1] = 0;
        }
        tmp = new byte[ptOutPara.datalen + tmpsize];
        if (head != null) {
            tmp[0] = head[0];
            j++;
        }
        dbg_mPrintf("tmpsize:" + tmpsize);
        if (tail != null) {
            tmp[j + ptOutPara.datalen + 0] = tail[0];
            tmp[j + ptOutPara.datalen + 1] = tail[1];
        }
        LfUtils.memcpy(tmp, j, ptOutPara.data, 0, ptOutPara.datalen);
        for (i = 0; i < tmp.length; i++) {
            if (tmp[i] == MISPOS.PACK_FS) {
                tmp[i] = '#';
            }
        }

        String tmpstr = newString(tmp);
        dbg_mPrintf("tmpstr:" + tmpstr);
        String[] tmpstrbb = null;
        if (tmpstr != null) {
            tmpstrbb = tmpstr.split("#");
        }
        if (tmpstrbb != null) {
            /*for(j=0;j<tmpstrbb.length;j++){
                dbg_mPrintf("%d, %s",j,tmpstrbb[j]==null?"":tmpstrbb[j]);
            }*/
            i = 0;
            if (tmpstrbb.length > i) {
                if (tmpstrbb[i] != null && tmpstrbb[i].equals("62")) {
                    this.reply_type = TradeType.GET_SETTLE_INFO;//指令代码
                }
            }
            i++;
            if (tmpstrbb.length > i) {
                this.setMer(tmpstrbb[i]);//商户代码
            }
            i++;
            if (tmpstrbb.length > i) {
                this.setTmn(tmpstrbb[i]);//终端号
            }
            i++;
            if (tmpstrbb.length > i) {
                this.mer_cn_name = tmpstrbb[i];//商户中文名
            }
            i++;
            if (tmpstrbb.length > i) {
                this.mer_en_name = tmpstrbb[i];//商户英文名
            }
            i++;

            if (tmpstrbb.length > i) {
                setTransacionBatchNo(tmpstrbb[i]);
            }
            i++;//交易批次号
            if (tmpstrbb.length > i) {
                setTransacionDatetime(tmpstrbb[i]);
            }
            i++;//交易日期和时间
            if (tmpstrbb.length > i) {
                civilCardSettleEqualFlag = tmpstrbb[i];
            }
            i++;//参数8内卡结帐平标志
            if (tmpstrbb.length > i) {
                civilCardPayCnt = tmpstrbb[i];
            }
            i++;//参数9内卡消费笔数
            if (tmpstrbb.length > i) {
                civilCardPaySum = tmpstrbb[i];
            }
            i++;//参数10内卡消费金额
            if (tmpstrbb.length > i) {
                civilCardRefundCnt = tmpstrbb[i];
            }
            i++;//参数11内卡退货笔数
            if (tmpstrbb.length > i) {
                civilCardRefundSum = tmpstrbb[i];
            }
            i++;//参数12内卡退货金额
            if (tmpstrbb.length > i) {
                civilCardAuthCompleteOnlineCnt = tmpstrbb[i];
            }
            i++;//参数13内卡预授完成(联机)笔数
            if (tmpstrbb.length > i) {
                civilCardAuthCompleteOnlineSum = tmpstrbb[i];
            }
            i++;//参数14内卡预授完成(联机)金额
            if (tmpstrbb.length > i) {
                civilCardAuthCompleteOffOnlineCnt = tmpstrbb[i];
            }
            i++;//参数15内卡预授完成(离线)笔数
            if (tmpstrbb.length > i) {
                civilCardAuthCompleteOffOnlineSum = tmpstrbb[i];
            }
            i++;//参数16内卡预授完成(离线)金额
            if (tmpstrbb.length > i) {
                civilCardPayOffOnlineCnt = tmpstrbb[i];
            }
            i++;//参数17内卡离线交易笔数
            if (tmpstrbb.length > i) {
                civilCardPayOffOnlineSum = tmpstrbb[i];
            }
            i++;//参数18内卡离线交易金额
            if (tmpstrbb.length > i) {
                foreiCardSettleEqualFlag = tmpstrbb[i];
            }
            i++;//参数19外卡结帐平标志
            if (tmpstrbb.length > i) {
                foreiCardPayCnt = tmpstrbb[i];
            }
            i++;//参数20外卡消费笔数
            if (tmpstrbb.length > i) {
                foreiCardPaySum = tmpstrbb[i];
            }
            i++;//参数21外卡消费金额
            if (tmpstrbb.length > i) {
                foreiCardRefundCnt = tmpstrbb[i];
            }
            i++;//参数22外卡退货笔数
            if (tmpstrbb.length > i) {
                foreiCardRefundSum = tmpstrbb[i];
            }
            i++;//参数23外卡退货金额
            if (tmpstrbb.length > i) {
                foreiCardAuthCompleteOnlineCnt = tmpstrbb[i];
            }
            i++;//参数24外卡预授完成(联机)笔数
            if (tmpstrbb.length > i) {
                foreiCardAuthCompleteOnlineSum = tmpstrbb[i];
            }
            i++;//参数25外卡预授完成(联机)金额
            if (tmpstrbb.length > i) {
                foreiCardAuthCompleteOffOnlineCnt = tmpstrbb[i];
            }
            i++;//参数26外卡预授完成(离线)笔数
            if (tmpstrbb.length > i) {
                foreiCardAuthCompleteOffOnlineSum = tmpstrbb[i];
            }
            i++;//参数27外卡预授完成(离线)金额
            if (tmpstrbb.length > i) {
                foreiCardPayOffOnlineCnt = tmpstrbb[i];
            }
            i++;//参数28外卡离线交易笔数
            if (tmpstrbb.length > i) {
                foreiCardPayOffOnlineSum = tmpstrbb[i];
            }
            i++;//参数29外卡离线交易金额


            if (tmpstrbb.length > i) {
                RemarkInfo = tmpstrbb[i];
            }
            i++;//备注信息
            if (tmpstrbb.length > i) {
                SpecInfoField = tmpstrbb[i];
            }
            i++;//特定信息域
            if (tmpstrbb.length > i) {
                PayEntryNo = tmpstrbb[i];
            }
            i++;//款台号
            if (tmpstrbb.length > i) {
                setPosWaterNo(tmpstrbb[i]);
            }
            i++;//收银流水号

        }
        return i;
    }

    public TradeType getReply_type() {
        return reply_type;
    }

    public void setReply_type(TradeType reply_type) {
        this.reply_type = reply_type;
    }

    public String getMer() {
        return mer;
    }

    public void setMer(String mer) {
        this.mer = mer;
    }

    public String getTmn() {
        return tmn;
    }

    public void setTmn(String tmn) {
        this.tmn = tmn;
    }


    public String getTransacionBatchNo() {
        return transacionBatchNo;
    }

    public void setTransacionBatchNo(String transacionBatchNo) {
        this.transacionBatchNo = transacionBatchNo;
    }


    public String getTransacionDatetime() {
        return transacionDatetime;
    }

    public void setTransacionDatetime(String transacionDatetime) {
        this.transacionDatetime = transacionDatetime;
    }


    public String getPosWaterNo() {
        return PosWaterNo;
    }

    public void setPosWaterNo(String posWaterNo) {
        PosWaterNo = posWaterNo;
    }
}
