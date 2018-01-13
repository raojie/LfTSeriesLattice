package com.landfoneapi.misposwa;

//import com.landfone.protocol.LfPosApiSimple;
//import ISerialPort;
//import com.landfone.protocol.result.LFPOS_QUERY_SIMPLE_RESULT;
//import com.landfone.protocol.result.LFPOS_SIGNIN_SIMPLE_RESULT;
//import com.landfone.protocol.result.LFPOS_TRADE_SIMPLE_RESULT;
//import com.landfone.protocol.result.LfException;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class LfService extends Service {

	public static final int OP_INIT = 1;
	public static final int OP_SIGNIN = 2;
	public static final int OP_TRADE = 3;
	public static final int OP_QUERY = 4;

	//private MyHandler myHandler;
	private MyBinder mBinder = new MyBinder();

	@Override
	public IBinder onBind(Intent arg0) {
		return mBinder;
	}
	@Override
	public void onCreate(){
		Log.i("LfService","onCreate!!!!!!!!!!!!!!!!!!!!!!!!");
		super.onCreate();
	}
	@Override
	public void onDestroy(){
		Log.i("LfService","onDestroy!!!!!!!!!!!!!!!!!!!!!!!");
		super.onDestroy();
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		flags = START_STICKY;
		return super.onStartCommand(intent, flags, startId);
	}
	public class MyBinder extends Binder{
		private LfApi MyLfApi = null;

		 //获取当前Service的实例

		public LfService getService(){
			return LfService.this;
		}
		public LfApi getMyLfApi() {
			return MyLfApi;
		}

		public void setMyLfApi(LfApi myLfApi) {
			MyLfApi = myLfApi;
		}
		///////////////////////////////////////实现POS操作////////////////////////////////////////////
	}

}
