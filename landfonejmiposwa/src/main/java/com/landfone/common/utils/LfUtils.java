package com.landfone.common.utils;

import java.io.UnsupportedEncodingException;

public class LfUtils {
	
	/*public static Object[] split(byte[] data, byte ser){
		int[] ser_offs = new int[512];//���512
		int last_ser_off = 0;//ser_offs���������һ����Ч��Ԫ��
		int objs = 0;
		Object[] subAry = null;
		int j = 0;
		for(int i=0;i<data.length;i++){
			if(data[i]==ser){
				ser_offs[objs++] = i;
				last_ser_off = objs;
			}
		}
		if(objs==0){
			return subAry;
		}
		objs+=1;//��һ��Ԫ�أ�����
		subAry = new Object[objs];  
		if(subAry!=null){
			for(int k=0;k<objs;k++){
				if(k==0 && ser_offs[k]==0){//����һ���ָ���֮ǰû�����
					subAry[k] = null;
				}else if(k==(objs-1) && ser_offs[k]==(data.length-1)){//ĩβ�ķָ����û�����
					subAry[k] = null;
				}
			}
		}
	}*/
/*	public static void setbcd(byte[] dst,String format, Object... args)
	{
		String msg = "";
		if(dst==null){
			return;
		}
		msg = String.format(format, args);
		if(msg!=null){
			
			byte[] tmpbb = LfUtils.String2Bcd(msg);
			if(tmpbb!=null && tmpbb.length<=dst.length){
				System.arraycopy(tmpbb, 0, dst, 0, tmpbb.length);
			}
		}
	}
	public static void sprintf(byte[] dst,String format, Object... args) throws LfException
	{
		String msg = "";
		int len = 0;
		if(dst==null){
			return;
		}
		msg = String.format(format, args);
		if(msg!=null){
			try {
				byte[] tmp = msg.getBytes("GBK");
				if(tmp!=null){
					len = dst.length>tmp.length?tmp.length:dst.length;
					System.arraycopy(tmp, 0, dst, 0, len);
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				throw new LfException(-1,"sprintf err");
			}
		}
		
	}
	public static void memcpy(byte[] dst, short src, int len){
    	if(dst!=null && dst.length>=2){
    		dst[0] = (byte) (src&0x000ff);
    		dst[1] = (byte) ((src&0x0ff00)>>8);
    	}
    }
	public static void memcpy(int dst,byte[] src, int len){
		if(src!=null && src.length>=2){
			dst =(short) (src[0]&0x0ff);
			dst += (short)((src[1]&0x0ff)<<8);
			dst += (short)((src[2]&0x0ff)<<16);
			dst += (short)((src[3]&0x0ff)<<24);
    	}
    }
	public static void memcpy(short dst,byte[] src, int len){
		if(src!=null && src.length>=2){
			dst =(short) (src[0]&0x0ff);
			dst += (short)((src[1]&0x0ff)<<8);
    	}
    }
	public static void memcpy(byte[] dst,int src, int len){
		//int min = 0;
		//min = len<4?len:4;
		if(dst!=null && dst.length>=4){
    		dst[0] = (byte)  (src&0x0000000ff);
    		dst[1] = (byte) ((src&0x00000ff00)>>8);
    		dst[2] = (byte) ((src&0x000ff0000)>>16);
    		dst[3] = (byte) ((src&0x0ff000000)>>24);
    	}
    }
	public static void memcpy(byte[] dst,byte[] src, int len){
    	System.arraycopy(src, 0, dst, 0, len);
    }
	public static void memcpy(byte[] dst,byte[] src, int src_off, int len){
    	System.arraycopy(src, src_off, dst, 0, len);
    }
	public static void memcpy(byte[] dst,int dst_off,byte[] src, int src_off, int len){
    	System.arraycopy(src, src_off, dst, dst_off, len);
    }
	public static int memcmp(byte[] a, byte[] b, int len){
		int tmp = -1;
		int i = 0;
		if(a.length<len || b.length<len){
			return tmp;
		}
		for(i=0;i<len;i++){
			if((tmp=a[i]-b[i])!=0){
				break;
			}
		}
		return tmp;
	}
	public static byte[] bytesAppend(byte[] a, byte[] b){
		byte[] tmp = {0};
		if(a.length>0 && b.length>0){
			tmp = new byte[a.length+b.length];
			System.arraycopy(a, 0, tmp, 0, a.length);
			System.arraycopy(b, 0, tmp, a.length, b.length);
		}
		return tmp;
	}
	public static byte[] intToByteArray(final int integer) {
		int byteNum = (40 - Integer.numberOfLeadingZeros(integer < 0 ? ~integer
				: integer)) / 8;
		byteNum = byteNum>4?4:byteNum;
		byte[] byteArray = new byte[4];

		for (int n = 0; n < byteNum; n++)
			byteArray[3 - n] = (byte) (integer >>> (n * 8));

		return (byteArray);
	}

	public static int byteArrayToInt(byte[] b, int offset) {
		int value = 0;
		for (int i = 0; i < 4; i++) {
			int shift = (4 - 1 - i) * 8;
			value += (b[i + offset] & 0x000000FF) << shift;
		}
		return value;
	}
	
	//��Ҫ����
	public static byte[] shortToByteArray(final short integer) {
		int byteNum = (40 - Integer.numberOfLeadingZeros(integer < 0 ? ~integer
				: integer)) / 8;
		byteNum = byteNum>2?2:byteNum;
		byte[] byteArray = new byte[2];

		for (int n = 0; n < byteNum; n++)
			byteArray[1 - n] = (byte) (integer >>> (n * 8));

		return (byteArray);
	}

	public static short byteArrayToShort(byte[] b, int offset) {
		short value = 0;
		for (int i = 0; i < 2; i++) {
			short shift = (short) ((2 - 1 - i) * 8);
			value += (b[i + offset] & 0x00FF) << shift;
		}
		return value;
	}
	
	public static byte[] String2Bcd(String value) {
        int charpos = 0; //char where we start
        int bufpos = 0;
        byte[] buf = null;
        int buflen = 0;
        
        if(value==null){
        	return buf;
        }
        //���鳤��
        buflen +=value.length() / 2   +   value.length() % 2;
        buf = new byte[buflen];
        if (value.length() % 2 == 1) {
            //for odd lengths we encode just the first digit in the first byte
            buf[0] = (byte)(value.charAt(0) - 48);
            charpos = 1;
            bufpos = 1;
        }
        
        //encode the rest of the string
        while (charpos < value.length()) {
            buf[bufpos] = (byte)(((value.charAt(charpos) - 48) << 4)
                | (value.charAt(charpos + 1) - 48));
            charpos += 2;
            bufpos++;
        }
        return buf;
    }
	public static String Bcd2String(byte[] bcd){
		if(bcd==null){
			return null;
		}
		String tmpstr = "";
		byte[] tmp2 = new byte[2];
		for(int k=0;k<bcd.length;k++){
			tmp2[0]= (byte) ((((byte)bcd[k]&0xff)>>4) +0x30);
			tmp2[1]= (byte) (((byte)bcd[k]&0x0F) +0x30);
			System.out.println("H:"+(char)tmp2[0]);
			System.out.println("L:"+(char)tmp2[1]);
			tmpstr +=new String(tmp2);
		}
		return tmpstr;
	}
	public static void Bcd2StringBytes(byte[] dst,int max, byte[] bcd){
		if(bcd==null || dst==null){
			return;
		}
		String tmpstr = "";
		byte[] tmp2 = new byte[2];
		for(int k=0;k<bcd.length;k++){
			tmp2[0]= (byte) ((((byte)bcd[k]&0xff)>>4) +0x30);
			tmp2[1]= (byte) (((byte)bcd[k]&0x0F) +0x30);
			System.out.println("H:"+(char)tmp2[0]);
			System.out.println("L:"+(char)tmp2[1]);
			tmpstr +=new String(tmp2);
		}
		int len = tmpstr.getBytes().length;
		len = len>max?max:len;
		System.arraycopy(tmpstr.getBytes(), 0, dst, 0, len);
	}
	public static void CpString2Bytes(byte[] dst,String str ){
		if(dst==null || str==null){
			return;
		}
		
		int len = dst.length;
		len = len>str.length()?str.length():len;
		System.arraycopy(str.getBytes(), 0, dst, 0, len);
	}
	public static int atoi(byte[] data){
		int ret = 0;
		if(data==null){
			return ret;
		}
		
		String tmp = new String(data);
		if(tmp!=null){
			ret = Integer.parseInt(tmp);
		}
		
		return ret;
	}
	public static byte CalcLRC(byte[] buf,int buf_off,  int DataLen, byte pre_value)
	{
		byte ucLrc = pre_value;//0x00;
		int i = 0;
		ucLrc = buf[buf_off];
	    for( i = 1 ; i < DataLen ; i ++)
		{
			ucLrc = (byte) (((byte)(buf[buf_off+i]&0x0ff)&0x0ff) ^ (byte)(ucLrc&0x0ff));
		}
		return ucLrc;
	}
*/

	public static String GetString(byte[] inbuff, int offset, int cnt, String charset){
		if(inbuff==null){
			return null;
		}else{
			int validlen = 0;
			for(int i=0;i<cnt;i++){
				if(inbuff[i+offset]==0x00){
					validlen = i;//不需要\0
					break;
				}
			}
			if(validlen!=0){
				byte[] tmp = new byte[validlen];
				System.arraycopy(inbuff, offset, tmp, 0, validlen);
				String ret = null;
				try {
					ret = new String(tmp,charset);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				return ret;
			}
		}
		return null;
	}

	public static int Bytes2Int_little(byte[] src, int offset){
		int dst = 0;
		if(src.length<(offset+4)){
			return 0;
		}
		dst =(int) (src[0+offset]&0x0ff);
		dst += (int)((src[1+offset]&0x0ff)<<8);
		dst += (int)((src[2+offset]&0x0ff)<<16);
		dst += (int)((src[3+offset]&0x0ff)<<24);
		return dst;
	}
	public static void setbcd(byte[] dst,String format, Object... args)
	{
		String msg = "";
		if(dst==null){
			return;
		}
		msg = String.format(format, args);
		if(msg!=null){

			byte[] tmpbb = LfUtils.String2Bcd(msg);
			if(tmpbb!=null && tmpbb.length<=dst.length){
				System.arraycopy(tmpbb, 0, dst, 0, tmpbb.length);
			}
		}
	}
	public static void sprintf(byte[] dst,String format, Object... args) throws LfException
	{
		String msg = "";
		int len = 0;
		if(dst==null){
			return;
		}
		msg = String.format(format, args);
		if(msg!=null){
			try {
				byte[] tmp = msg.getBytes("GBK");
				if(tmp!=null){
					len = dst.length>tmp.length?tmp.length:dst.length;
					System.arraycopy(tmp, 0, dst, 0, len);
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				throw new LfException(-1,"sprintf err");
			}
		}

	}
	public static void memcpy(int[] dst,byte[] src,int offset, int len){
		if(src!=null && src.length>=(4+offset)){
			dst[0] =(short) (src[offset+0]&0x0ff);
			dst[0] += (short)((src[offset+1]&0x0ff)<<8);
			dst[0] += (short)((src[offset+2]&0x0ff)<<16);
			dst[0] += (short)((src[offset+3]&0x0ff)<<24);
		}
	}
	public static void memcpy(byte[] dst, short src, int len){
		if(dst!=null && dst.length>=2){
			dst[0] = (byte) (src&0x000ff);
			dst[1] = (byte) ((src&0x0ff00)>>8);
		}
	}
	public static void memcpy(int dst,byte[] src, int len){
		if(src!=null && src.length>=2){
			dst =(short) (src[0]&0x0ff);
			dst += (short)((src[1]&0x0ff)<<8);
			dst += (short)((src[2]&0x0ff)<<16);
			dst += (short)((src[3]&0x0ff)<<24);
		}
	}
	public static void memcpy(short dst,byte[] src, int len){
		if(src!=null && src.length>=2){
			dst =(short) (src[0]&0x0ff);
			dst += (short)((src[1]&0x0ff)<<8);
		}
	}
	public static void Int2Bytes_little(byte[] dst, int offset,int src){
		if(dst!=null && dst.length>=(offset+4)){
			dst[offset+0] = (byte)  (src&0x0000000ff);
			dst[offset+1] = (byte) ((src&0x00000ff00)>>8);
			dst[offset+2] = (byte) ((src&0x000ff0000)>>16);
			dst[offset+3] = (byte) ((src&0x0ff000000)>>24);
		}
	}

	public static void memcpy(byte[] dst,int offset, int src){
		//int min = 0;
		//min = len<4?len:4;
		if(dst!=null && dst.length>=(4+offset)){
			dst[0+offset] = (byte)  (src&0x0000000ff);
			dst[1+offset] = (byte) ((src&0x00000ff00)>>8);
			dst[2+offset] = (byte) ((src&0x000ff0000)>>16);
			dst[3+offset] = (byte) ((src&0x0ff000000)>>24);
		}
	}
	public static void memcpy(byte[] dst,byte[] src, int len){
		System.arraycopy(src, 0, dst, 0, len);
	}
	public static void memcpy(byte[] dst,byte[] src, int src_off, int len){
		System.arraycopy(src, src_off, dst, 0, len);
	}

	/**
	 *
	 * @param dst
	 * @param dst_off
	 * @param src
	 * @param src_off
	 * @param len
	 * @return  返回拷贝的字节数
	 */
	public static int memcpy(byte[] dst,int dst_off,byte[] src, int src_off, int len){
		int ret = 0;
		if(len>0) {
			System.arraycopy(src, src_off, dst, dst_off, len);
			ret = len;
		}
		return ret;
	}
	public static int memcmp(byte[] a, byte[] b, int len){
		int tmp = -1;
		int i = 0;
		if(a.length<len || b.length<len){
			return tmp;
		}
		for(i=0;i<len;i++){
			if((tmp=a[i]-b[i])!=0){
				break;
			}
		}
		return tmp;
	}
	public static byte[] bytesAppend(byte[] a, byte[] b){
		byte[] tmp = {0};
		if(a.length>0 && b.length>0){
			tmp = new byte[a.length+b.length];
			System.arraycopy(a, 0, tmp, 0, a.length);
			System.arraycopy(b, 0, tmp, a.length, b.length);
		}
		return tmp;
	}
	public static byte[] intToByteArray(final int integer) {
		int byteNum = (40 - Integer.numberOfLeadingZeros(integer < 0 ? ~integer
				: integer)) / 8;
		byteNum = byteNum>4?4:byteNum;
		byte[] byteArray = new byte[4];

		for (int n = 0; n < byteNum; n++)
			byteArray[3 - n] = (byte) (integer >>> (n * 8));

		return (byteArray);
	}

	public static int byteArrayToInt(byte[] b, int offset) {
		int value = 0;
		for (int i = 0; i < 4; i++) {
			int shift = (4 - 1 - i) * 8;
			value += (b[i + offset] & 0x000000FF) << shift;
		}
		return value;
	}

	//需要测试
	public static byte[] shortToByteArray(final short integer) {
		int byteNum = (40 - Integer.numberOfLeadingZeros(integer < 0 ? ~integer
				: integer)) / 8;
		byteNum = byteNum>2?2:byteNum;
		byte[] byteArray = new byte[2];

		for (int n = 0; n < byteNum; n++)
			byteArray[1 - n] = (byte) (integer >>> (n * 8));

		return (byteArray);
	}

	public static short byteArrayToShort(byte[] b, int offset) {
		short value = 0;
		for (int i = 0; i < 2; i++) {
			short shift = (short) ((2 - 1 - i) * 8);
			value += (b[i + offset] & 0x00FF) << shift;
		}
		return value;
	}

	public static byte[] String2Bcd(String value) {
		int charpos = 0; //char where we start
		int bufpos = 0;
		byte[] buf = null;
		int buflen = 0;

		if(value==null){
			return buf;
		}
		//数组长度
		buflen +=value.length() / 2   +   value.length() % 2;
		buf = new byte[buflen];
		if (value.length() % 2 == 1) {
			//for odd lengths we encode just the first digit in the first byte
			buf[0] = (byte)(value.charAt(0) - 48);
			charpos = 1;
			bufpos = 1;
		}

		//encode the rest of the string
		while (charpos < value.length()) {
			buf[bufpos] = (byte)(((value.charAt(charpos) - 48) << 4)
					| (value.charAt(charpos + 1) - 48));
			charpos += 2;
			bufpos++;
		}
		return buf;
	}
	public static String Bcd2String(byte[] bcd){
		if(bcd==null){
			return null;
		}
		String tmpstr = "";
		byte[] tmp2 = new byte[2];
		for(int k=0;k<bcd.length;k++){
			tmp2[0]= (byte) ((((byte)bcd[k]&0xff)>>4) +0x30);
			tmp2[1]= (byte) (((byte)bcd[k]&0x0F) +0x30);
			System.out.println("H:"+(char)tmp2[0]);
			System.out.println("L:"+(char)tmp2[1]);
			tmpstr +=new String(tmp2);
		}
		return tmpstr;
	}
	public static void Bcd2StringBytes(byte[] dst,int max, byte[] bcd){
		if(bcd==null || dst==null){
			return;
		}
		String tmpstr = "";
		byte[] tmp2 = new byte[2];
		for(int k=0;k<bcd.length;k++){
			tmp2[0]= (byte) ((((byte)bcd[k]&0xff)>>4) +0x30);
			tmp2[1]= (byte) (((byte)bcd[k]&0x0F) +0x30);
			System.out.println("H:"+(char)tmp2[0]);
			System.out.println("L:"+(char)tmp2[1]);
			tmpstr +=new String(tmp2);
		}
		int len = tmpstr.getBytes().length;
		len = len>max?max:len;
		System.arraycopy(tmpstr.getBytes(), 0, dst, 0, len);
	}
	public static void CpString2Bytes(byte[] dst,String str ){
		if(dst==null || str==null){
			return;
		}

		int len = dst.length;
		len = len>str.length()?str.length():len;
		System.arraycopy(str.getBytes(), 0, dst, 0, len);
	}
	public static int atoi(byte[] data){
		int ret = 0;
		if(data==null){
			return ret;
		}

		String tmp = new String(data);
		if(tmp!=null){
			ret = Integer.parseInt(tmp);
		}

		return ret;
	}
	public static byte CalcLRC(byte[] buf,int buf_off,  int DataLen, byte pre_value)
	{
		byte ucLrc = pre_value;//0x00;
		int i = 0;
		ucLrc = buf[buf_off];
		for( i = 1 ; i < DataLen ; i ++)
		{
			ucLrc = (byte) (((byte)(buf[buf_off+i]&0x0ff)&0x0ff) ^ (byte)(ucLrc&0x0ff));
		}
		return ucLrc;
	}

}
