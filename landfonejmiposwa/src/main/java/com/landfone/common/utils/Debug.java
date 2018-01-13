/*package com.landfone.protocol.util;

import java.io.UnsupportedEncodingException;

public class Debug {
	public static final byte NORMAL = 0;
	
	private boolean dbgFlag = true;
	public void DisableDBG(){
		this.dbgFlag = false;
	}
	public void EnableDBG(){
		this.dbgFlag = true;
	}
	
	public int atoi(byte[] data){
		if(data!=null){
			return Integer.parseInt(newString(data));
		}
		return 0;
	}
	public void strcpy(byte[] dst, String str) throws LfException{
		if(dst!=null && str!=null && dst.length>=str.length()){
			LfUtils.sprintf(dst,"%s",str);
		}
	}
	public void CHECKRST(int rst) throws LfException{
		if(rst!= NORMAL){
			throw new LfException(-1,"func return EXCEPTION");
		}
	}
	public void ASSERT(boolean v) throws LfException{
		if(!v){
			throw new LfException(-1,"return EXCEPTION");
		}
	}
	public int min(int a, int b) {
		return (((a) < (b)) ? (a) : (b));
	}
	public boolean isprint(byte c){
		return ((int)(c&0x0FF) >= 0x20 && (int)(c&0x0FF) < 0x7f);
	}
	public boolean isdigit(byte c){
		return ((int)(c&0x0FF) >= 0x40 && (int)(c&0x0FF) <= 0x49);
	}
	public int strlen(byte[] data){
		if(data==null){
			return 0;
		}
		int i=0;
		for(i=0;i<data.length;i++){
			if(data[i]==0x00){
				return (i==0?0:(i));
			}
			if(!isprint(data[i])){
				return 0;
			}
		}
		return i;
	}
	public int strlen(byte[] data, int data_off){
		if(data==null){
			return 0;
		}
		int i=0;
		for(i=data_off;i<data.length;i++){
			if(data[i]==0x00){
				return (i==data_off?0:(i-data_off));
			}
			if(!isprint(data[i])){
				return 0;
			}
		}
		return (i-data_off);
	}
	public int sizeof(byte[] data){
		return data!=null?data.length:0;
	}
	
	public short General_memcpy(byte[] string1, int string1_off, byte[] string2,int string2_off, short len){
		short cpy_len = 0;
		int string1_i = string1_off, string2_i = string2_off;
		if(len==0){
			len = (short) strlen(string1,string1_off);
		}
		if(len<1){
			return 0;
		}
		cpy_len = len;
		
		while(len-->0){
			string1[string1_i++] = string2[string2_i++];
		}
		return cpy_len;
	}
	
	public void dbg_printfWHex(byte[] data, int len ,String format, Object... args)
	{
		int minlen = 0;
		String msg = "";
		msg = String.format(format, args);
		minlen = len<data.length?len:data.length;
		System.out.print(msg+":");
		for (int i = 0; i < minlen; i ++) {
            System.out.printf("%02X ", data[i]);
        }
		System.out.println("");
	}
	
	public void dbg_mPrintfWHex(byte[] data, int len ,String format, Object... args)
	{
		if(this.dbgFlag){
			dbg_printfWHex(data,len,format,args);
		}
	}
	public void dbg_printf(String format, Object... args)
	{
		int minlen = 0;
		String msg = "";
		msg = String.format(format, args);
		System.out.println(msg);
		
	}
	public void dbg_mPrintf(String format, Object... args)
	{
		if(this.dbgFlag){
			dbg_printf(format,args);
		}
	}
	//
	public long lf_Getsystime(){
		return System.currentTimeMillis();
	}
	//sleep
	public void Sleep(int ms) throws LfException{
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
			throw new LfException(e,-1,"sleep error");
		}
	}
	// �ڲ�API
	public long GetSysCount()
	{
		long count;
		count = lf_Getsystime();
		return count;
	}
	public String newString(byte[] inbuff, String charset){
		if(inbuff==null){
			return null;
		}else{
			int validlen = 0;
			for(int i=0;i<inbuff.length;i++){
				if(inbuff[i]==0x00){
					validlen = i;//����Ҫ\0
					break;
				}
			}
			if(validlen!=0){
				byte[] tmp = new byte[validlen];
				System.arraycopy(inbuff, 0, tmp, 0, validlen);
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
	public String newString(byte[] inbuff){
		
		return newString(inbuff,"GBK");
	}
}
*/
package com.landfone.common.utils;

import android.util.Log;

import java.io.UnsupportedEncodingException;

public class Debug {
    public static final byte NORMAL = 0;

    private boolean dbgFlag = true;

    public void DisableDBG() {
        this.dbgFlag = false;
    }

    public void EnableDBG() {
        this.dbgFlag = true;
    }

    public int atoi(byte[] data) {
        if (data != null) {
            return Integer.parseInt(newString(data));
        }
        return 0;
    }

    public void strcpy(byte[] dst, String str) throws LfException {
        if (dst != null && str != null && dst.length >= str.length()) {
            LfUtils.sprintf(dst, "%s", str);
        }
    }

    public void CHECKRST(int rst) throws LfException {
        if (rst != NORMAL) {
            throw new LfException(-1, "func return EXCEPTION");
        }
    }

    public void ASSERT(boolean v) throws LfException {
        if (!v) {
            throw new LfException(-1, "return EXCEPTION");
        }
    }

    public int min(int a, int b) {
        return (((a) < (b)) ? (a) : (b));
    }

    public boolean isprint(byte c) {
        return ((int) (c & 0x0FF) >= 0x20 && (int) (c & 0x0FF) < 0x7f);
    }

    public boolean isdigit(byte c) {
        return ((int) (c & 0x0FF) >= 0x40 && (int) (c & 0x0FF) <= 0x49);
    }

    public int strlen(byte[] data) {
        if (data == null) {
            return 0;
        }
        int i = 0;
        for (i = 0; i < data.length; i++) {
            if (data[i] == 0x00) {
                return (i == 0 ? 0 : (i));
            }
            if (!isprint(data[i])) {
                return 0;
            }
        }
        return i;
    }

    public int strlen(byte[] data, int data_off) {
        if (data == null) {
            return 0;
        }
        int i = 0;
        for (i = data_off; i < data.length; i++) {
            if (data[i] == 0x00) {
                return (i == data_off ? 0 : (i - data_off));
            }
            if (!isprint(data[i])) {
                return 0;
            }
        }
        return (i - data_off);
    }

    public int sizeof(byte[] data) {
        return data != null ? data.length : 0;
    }

    public short General_memcpy(byte[] string1, int string1_off, byte[] string2, int string2_off, short len) {
        short cpy_len = 0;
        int string1_i = string1_off, string2_i = string2_off;
        if (len == 0) {
            len = (short) strlen(string1, string1_off);
        }
        if (len < 1) {
            return 0;
        }
        cpy_len = len;

        while (len-- > 0) {
            string1[string1_i++] = string2[string2_i++];
        }
        return cpy_len;
    }

    public static void dbg_TprintfWHex(String tag, byte[] data, int len, String format, Object... args) {
        int minlen = 0;
        String msg = "";
        msg = String.format(format, args);
        minlen = len < data.length ? len : data.length;
        //System.out.print(msg+":");
        msg += ":";
        for (int i = 0; i < minlen; i++) {
            msg += String.format("%02X ", data[i]);
        }
        if (msg.length() > 4000) {
            for (int i = 0; i < msg.length(); i += 4000) {
                if (i + 4000 < msg.length())
                    Logz.i(tag + " " + i, msg.substring(i, i + 4000));
                else
                    Logz.i(tag + " " + i, msg.substring(i, msg.length()));
            }
        } else {
            Logz.i(tag, msg);
        }
    }

    public void dbg_printfWHex(byte[] data, int len, String format, Object... args) {
        int minlen = 0;
        String msg = "";
        msg = String.format(format, args);
        minlen = len < data.length ? len : data.length;
        System.out.print(msg + ":");
        for (int i = 0; i < minlen; i++) {
            System.out.printf("%02X ", data[i]);
        }
        System.out.println("");
    }

    public void dbg_mPrintfWHex(byte[] data, int len, String format, Object... args) {
        if (this.dbgFlag) {
            dbg_printfWHex(data, len, format, args);
        }
    }

    public void dbg_printf(String format, Object... args) {
        int minlen = 0;
        String msg = "";
        msg = String.format(format, args);
        System.out.println(msg);

    }

    public void dbg_mPrintf(String format, Object... args) {
        if (this.dbgFlag) {
            dbg_printf(format, args);
        }
    }

    //获取时间
    public long lf_Getsystime() {
        return System.currentTimeMillis();
    }

    //sleep
    public void Sleep(int ms) throws LfException {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new LfException(e, -1, "sleep error");
        }
    }

    // 内部API
    public long GetSysCount() {
        long count;
        count = lf_Getsystime();
        return count;
    }

    public String newString(byte[] inbuff, String charset) {
        if (inbuff == null) {
            return null;
        } else {
            int validlen = 0;
            for (int i = 0; i < inbuff.length; i++) {
                if (inbuff[i] == 0x00) {
                    validlen = i;//不需要\0
                    break;
                }
            }
            if (validlen != 0) {
                byte[] tmp = new byte[validlen];
                System.arraycopy(inbuff, 0, tmp, 0, validlen);
                String ret = null;
                try {
                    ret = new String(tmp, charset);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return ret;
            }
        }
        return null;
    }

    public String newString(byte[] inbuff) {

        return newString(inbuff, "GBK");
    }
}
