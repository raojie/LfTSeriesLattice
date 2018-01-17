package com.landfone.lattice.test;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.landfone.lattice.LfLattice;
import com.landfone.lattice.test.config.PosConfig;
import com.landfone.lattice.test.utils.LfLog;
import com.landfone.lattice.test.utils.SleepUtils;
import com.landfone.mis.bean.TransCfx;
import com.landfoneapi.mispos.CallbackMsg;
import com.landfoneapi.mispos.Display;
import com.landfoneapi.mispos.DisplayType;
import com.landfoneapi.mispos.E_OP_TYPE;
import com.landfoneapi.mispos.E_REQ_RETURN;
import com.landfoneapi.mispos.ILfListener;
import com.landfoneapi.misposwa.MyApi;
import com.landfoneapi.misposwa.MyApi1;
import com.landfoneapi.protocol.pkg.REPLY;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class InitActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Context context;
    private int soundId;
    private LfLattice lfLattice;
    private Button pos1SignBtn;
    private Button pos2SignBtn;
    private Button pos1PayBtn;
    private Button pos2PayBtn;
    private TextView showContentTextView;
    private String[] showContent;

    private TransCfx pos1TransCfx = new TransCfx();
    private TransCfx pos2TransCfx = new TransCfx();

    private MyApi pos1MyApi = new MyApi();
    private MyApi1 pos2MyApi = new MyApi1();

    private long current_time = -1;
    private long lastPosPlaySound = -1;
    private MediaPlayer mediaPlayer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        current_time = System.currentTimeMillis();
        CrashHandler.getInstance().init(getApplicationContext());
        lfLattice = new LfLattice();
        lfLattice.InitLfLattice("/dev/ttyS3", "115200");
        initialize();
        bindEvent();
        playSound(getApplication(), R.raw.rebooting);
    }

//    public void playSound(Context context, int id) {
//        try {
//            mediaPlayer = MediaPlayer.create(context, id);
//            if (mediaPlayer != null) {
//                mediaPlayer.stop();
//                mediaPlayer.release();
//            }
//            mediaPlayer.start();
//        } catch (Throwable e) {
//            e.printStackTrace();
//        }

    public void playSound(Context context, int id) {
        this.context = context;
        this.soundId = id;
        (new Thread(playSoundThread)).start();

    }

    Runnable playSoundThread = new Runnable() {
        @Override
        public void run() {
            if (mediaPlayer != null) {
                mediaPlayer.release();
            }
            mediaPlayer = MediaPlayer.create(context, soundId);
            mediaPlayer.start();
        }
    };

//        try {
//            mediaPlayer = MediaPlayer.create(context, id);
//
//            mediaPlayer.prepare();
//            if (mediaPlayer.isPlaying()) {
//                mediaPlayer.stop();
//                mediaPlayer.release();
//                mediaPlayer = MediaPlayer.create(this, id);
//            }
//            mediaPlayer.start();
//        } catch (IllegalStateException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    showWelcomeRunnable.run();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    openSrcRunnable.run();
                    break;
                case 2:
                    LfLog.d(TAG, "deviceInitializeShowThread start...");
                    deviceInitializeShowThread.start();
                    break;
                case 3:
                    pos1MyApi.pos_init("222.76.113.29", 8000, "/dev/ttyS1", "9600");
                    pos2MyApi.pos_init("222.76.113.29", 8000, "/dev/ttyS2", "9600");
                    LfLog.d(TAG, "checkCurrentPosStatusThread start...");
                    checkCurrentPosStatusThread.start();
                    LfLog.d(TAG, "backgroundThread start...");
                    backgroundThread.start();
                    break;
            }
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("onPause");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        System.out.println("onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("onResume");
        // activity启动两秒钟后，发送一个message
        mHandler.sendEmptyMessageDelayed(1, 5000);
        mHandler.sendEmptyMessageDelayed(2, 10000);
        mHandler.sendEmptyMessageDelayed(3, 60000);
    }

    Runnable openSrcRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                lfLattice.openSrc();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    Runnable showWelcomeRunnable = new Runnable() {
        @Override
        public void run() {
            boolean result = false;
            try {
                byte[] content = "演示程序0.01元".getBytes("gb2312");
                result = lfLattice.showPreviousContent(content, content.length);
                System.out.println("result:" + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private void iniContentShow() {
        String result = "";
        for (int i = 0; i < showContent.length; i++) {
            showContent[i] = "";
            result = result + (i + 1) + "." + showContent[i] + "\n";
            showContentTextView.setText(result);
        }

    }

    Thread deviceInitializeShowThread = new Thread() {
        @Override
        public void run() {
            try {
                byte[] content = "设备启动".getBytes("gb2312");
                int len = content.length;
                lfLattice.showThirdContent(content, len);
                showContent[0] = "设备启动";
                updateContentTV();
                byte[] content1 = "".getBytes("gb2312");
                int len1 = content.length;
                lfLattice.showFourthContent(content1, len1);
                showContent[1] = "";
                updateContentTV();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private void bindEvent() {
        pos1SignBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        pos1Sign();
                    }
                }).start();
            }
        });

        pos2SignBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        pos2Sign();
                    }
                }).start();
            }
        });

        pos1PayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pos1Pay();
            }
        });

        pos2PayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pos2Pay();
            }
        });
    }

    private void initialize() {
        showContent = new String[5];
        showContentTextView = (TextView) findViewById(R.id.showContentTextView);
        pos1PayBtn = (Button) findViewById(R.id.pos1PayBtn);
        pos2PayBtn = (Button) findViewById(R.id.pos2PayBtn);
        pos1SignBtn = (Button) findViewById(R.id.pos1SignBtn);
        pos2SignBtn = (Button) findViewById(R.id.pos2SignBtn);
        iniContentShow();
        initializePos1();
        initializePos2();
    }

    private void initializePos1() {
        pos1TransCfx.setHard_ip2("222.76.113.29");//硬加密走的主机ip地址
        pos1TransCfx.setHard_port2(8000);//硬加密走的主机端口号
        pos1TransCfx.setMerId("000000000000000");//商户号
        pos1TransCfx.setTmnId("00000000");//终端号
        pos1TransCfx.setContext(getApplicationContext());
        pos1TransCfx.setHard_on(1);//非0为关
        pos1MyApi.setTransCfx(pos1TransCfx);
        pos1MyApi.setILfListener(pos1Listener);
    }

    private void initializePos2() {
        pos2TransCfx.setHard_ip2("222.76.113.29");//硬加密走的主机ip地址
        pos2TransCfx.setHard_port2(8000);//硬加密走的主机端口号
        pos2TransCfx.setMerId("000000000000000");//商户号
        pos2TransCfx.setTmnId("00000000");//终端号
        pos2TransCfx.setContext(getApplicationContext());
        pos2TransCfx.setHard_on(1);//非0为关
        pos2MyApi.setTransCfx(pos2TransCfx);
        pos2MyApi.setILfListener(pos2Listener);
    }

    private void updateContentTV() {
        String result = "";
        for (int i = 0; i < showContent.length; i++) {
            result = result + showContent[i] + "\n";
        }
        updateUI(result);
    }

    private void updateUI(final String string) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showContentTextView.setText(string);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer.release();
            }
        }
        if (pos1MyApi != null) {
            pos1MyApi.pos_release();
        }
        if (pos1MyApi != null) {
            pos2MyApi.pos_release();
        }

        if (lfLattice != null) {
            try {
                lfLattice.clearSrc();
                lfLattice.closeSrc();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private long background_current_time = -1;

    public void start() {
        SleepUtils._sleep(60000);
        Log.d(TAG, "start...");
        backgroundThread.start();
        checkCurrentPosStatusThread.start();
    }

    //检测当前pos的流程处于什么状态,并进行相应操作
    private Thread checkCurrentPosStatusThread = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                while (true) {
                    checkCurrentPay();

                    if (PosConfig.getInstance().isMightPosNetError()) {
                        checkNetWork();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    });

    public static boolean to(String ip, int port,int timeout_ms){
        SocketAddress address = new InetSocketAddress(ip,port);
        Socket socket=new Socket();
        if(socket!=null){
            try {
                socket.connect(address, timeout_ms);
                socket.close();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    //检测网络
    public boolean checkNetWork() {
        boolean result = false;
        while (true) {
                {
                    if (to("114.114.114.114", 53, 4000)) {//4秒超时ping dns//"8.8.8.8",53
                        SleepUtils._sleep(1000);
                        result = true;
                        PosConfig.getInstance().setMightPosNetError(false);
                        LfLog.e(TAG, "检测到网络.....");
                        break;
                    } else {
                        result = false;
                        PosConfig.getInstance().setMightPosNetError(false);
                    }
                }
                SleepUtils._sleep(1000);
        }
        return result;
    }

    //检测当前哪个pos正在消费
    private void checkCurrentPay() {
        if (PosConfig.getInstance().isMightPos1ReadCard() || PosConfig.getInstance().isMightPos2ReadCard()) {
            if (PosConfig.getInstance().isMightPos1ReadCard()) {
                PosConfig.getInstance().setMightPos1ReadCard(false);
                PosConfig.getInstance().setMightPos2PlaySound(false);
                LfLog.d(TAG, "pos1读到卡,pos2取消消费,设置pos2播放语音状态为false");
                pos2MyApi.pos_cancel();
            } else if(PosConfig.getInstance().isMightPos2ReadCard()) {
                PosConfig.getInstance().setMightPos2ReadCard(false);
                PosConfig.getInstance().setMightPos1PlaySound(false);
                LfLog.d(TAG, "pos2读到卡,pos1取消消费,设置pos1播放语音状态为false");
                pos1MyApi.pos_cancel();
            }
        }
    }

    private Thread backgroundThread = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                while (true) {
                    background_current_time = System.currentTimeMillis();

                    checkPos1Sign();

                    checkPos2Sign();

                    checkWhetherToStartPay();

//                    checkWhetherToStartPay1();

                    SleepUtils._sleep(1000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    });

    //检测pos1是否签到
    private long lastCheckPos1Sign = -1;

    private void checkPos1Sign() {
        boolean checkPos1Sign = false;
        if (PosConfig.getInstance().isMightPos1Signed()) {
            return;
        } else {
            if (!PosConfig.getInstance().isMightPos1Signing()) {
                if (lastCheckPos1Sign == -1) {
                    lastCheckPos1Sign = background_current_time;
                } else {//非初始状态，则做判断
//                    if ((background_current_time - lastCheckPos1Sign) > (15 * 1000)) {//15秒一次---测试用
                    if ((background_current_time - lastCheckPos1Sign) > (200)) {//200ms一次---测试用
                        checkPos1Sign = true;
                    }
                }
            }

            if (checkPos1Sign) {
                LfLog.d(TAG, "pos1未签到...");
//                initializePos1();
                pos1Sign();
                lastCheckPos1Sign = System.currentTimeMillis();
            }
        }
    }

    //检测pos1是否签到
    private long lastCheckPos2Sign = -1;

    private void checkPos2Sign() {
        boolean checkPos2Sign = false;
        if (PosConfig.getInstance().isMightPos2Signed()) {
            return;
        } else {
            if (!PosConfig.getInstance().isMightPos2Signing()) {
                if (lastCheckPos2Sign == -1) {
                    lastCheckPos2Sign = background_current_time;
                } else {//非初始状态，则做判断
//                    if ((background_current_time - lastCheckPos2Sign) > (15 * 1000)) {//15秒一次---测试用
                    if ((background_current_time - lastCheckPos2Sign) > (200)) {//200ms一次---测试用
                        checkPos2Sign = true;
                    }
                }
            }

            if (checkPos2Sign) {
                LfLog.d(TAG, "pos2未签到...");
//                initializePos2();
                pos2Sign();
                lastCheckPos2Sign = System.currentTimeMillis();
            }
        }
    }

    //检测两个pos是否已经签到完毕,签到完毕发起消费
    private long lastCheckWhetherToStartPay1 = -1;
    private void checkWhetherToStartPay1() {
        boolean checkWhetherToStartPay = false;
        if (PosConfig.getInstance().isMightPos1Signed()) { //pos1已经签到成功且pos2已经签到成功
            if (lastCheckWhetherToStartPay1 == -1) {
                lastCheckWhetherToStartPay1 = background_current_time;
            } else {//非初始状态，则做判断
                if ((background_current_time - lastCheckWhetherToStartPay1) > (200)) {//200ms一次---测试用
                    checkWhetherToStartPay = true;
                    LfLog.e(TAG, "checkWhetherToStartPay:" + checkWhetherToStartPay);
                }
            }
        }

        if (checkWhetherToStartPay) {
            if (PosConfig.getInstance().isMightCheckPos1Paying()) {
                lastCheckWhetherToStartPay = background_current_time;
            } else {
                if (PosConfig.getInstance().isMightPos1Signed()) {//如果pos1签到成功
                    if (!PosConfig.getInstance().isMightPos1Paying()) {//如果pos1不在消费状态
                        PosConfig.getInstance().setMightPos1PlaySound(true);
                        LfLog.d(TAG, "发起消费...");
                        pos1Pay();
                    }
                    lastCheckWhetherToStartPay1 = System.currentTimeMillis();
                }
            }
        }
    }

    //检测两个pos是否已经签到完毕,签到完毕发起消费
    private long lastCheckWhetherToStartPay = -1;
    private void checkWhetherToStartPay() {
        boolean checkWhetherToStartPay = false;
        if (PosConfig.getInstance().isMightPos1Signed() && PosConfig.getInstance().isMightPos2Signed()) { //pos1已经签到成功且pos2已经签到成功
            if (lastCheckWhetherToStartPay == -1) {
                lastCheckWhetherToStartPay = background_current_time;
            } else {//非初始状态，则做判断
                if ((background_current_time - lastCheckWhetherToStartPay) > (200)) {//200ms一次---测试用
                    checkWhetherToStartPay = true;
//                    LfLog.e(TAG, "checkWhetherToStartPay:" + checkWhetherToStartPay);
                }
            }
        }

        if (checkWhetherToStartPay) {
            if (PosConfig.getInstance().isMightPos1Signed() && PosConfig.getInstance().isMightPos2Signed()) {//如果pos1和pos2签到成功
                if (!PosConfig.getInstance().isMightPos1Paying() && !PosConfig.getInstance().isMightPos2Paying()) {//如果pos1和pos2不在消费状态
                    PosConfig.getInstance().setMightPos1PlaySound(true);
                    PosConfig.getInstance().setMightPos2PlaySound(true);
                    LfLog.d(TAG, "发起消费...");
                    pos1Pay();
                    pos2Pay();
                }
                lastCheckWhetherToStartPay = System.currentTimeMillis();
            }
        }
    }

    private void pos1Sign() {
        PosConfig.getInstance().setMightPos1Signing(true);
        pos1MyApi.setUseSynch(true);
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        byte[] content = "正在签到".getBytes("gb2312");
                        int len = content.length;
                        lfLattice.showThirdContent(content, len);
                        showContent[0] = new String(content, "utf-8");
                        updateContentTV();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    E_REQ_RETURN ret = pos1MyApi.pos_signin();
                    if (ret.getRst() == 0) {//请求成功
                        CallbackMsg callbackMsg = (CallbackMsg) ret.getObj();
                        Display dpl = callbackMsg.dsp;
                        if (callbackMsg == null || callbackMsg.op_type == null
                                || dpl.getType() == null || dpl.getMsg() == null) {
                            LfLog.d(TAG, "POS1签到返回为空");
                            return;
                        }
                        LfLog.d(TAG, "POS1签到返回, callbackMsg.reply:" + callbackMsg.reply
                                + ", op_type:"+ callbackMsg.op_type.getDesc()
                                + " ,dpl.getType:" + dpl.getType()
                                + " ,dpl.getMsg:" + dpl.getMsg());
                        if (callbackMsg.op_type.getDesc().equals(E_OP_TYPE.OP_POS_SIGNIN.getDesc())) { //判断签到成功
                            if (callbackMsg.reply == 0) {
                                if (dpl.getType().equals("00")) {
                                    LfLog.d(TAG, "POS1签到成功");
                                    PosConfig.getInstance().setMightPos1Signed(true);
                                    PosConfig.getInstance().setMightPos1Signing(false);
                                    showContent[0] = "POS1签到成功";
                                    updateContentTV();
                                    pos1MyApi.setUseSynch(false);
                                    LfLog.e(TAG, "POS1是否签到状态:" + PosConfig.getInstance().isMightPos1Signed()
                                            + " ,POS1是否处于签到状态:" + PosConfig.getInstance().isMightPos1Signing());
                                } else if (dpl.getType().equals("-3")
                                        || dpl.getType().equals("-4")) {
                                    LfLog.d(TAG, "POS1签到失败");
                                    PosConfig.getInstance().setMightPos1Signed(false);
                                    PosConfig.getInstance().setMightPos1Signing(false);
                                    showContent[0] = "POS1签到失败";
                                    updateContentTV();
                                    pos1MyApi.setUseSynch(false);
                                }
                            }else {
                                if (dpl.getType().equals("Z1") || dpl.getType().equals("XY")
                                        || dpl.getType().equals("X8") || dpl.getType().equals("X7")
                                        || dpl.getType().equals("A0") || dpl.getType().equals("X6")
                                        || dpl.getType().equals("XX") || dpl.getType().equals("-3")
                                        || dpl.getType().equals("-4") || dpl.getType().equals("Z0")) {
                                    LfLog.d(TAG, "POS1签到失败");
                                    PosConfig.getInstance().setMightPos1Signed(false);
                                    PosConfig.getInstance().setMightPos1Signing(false);
                                    showContent[0] = "POS1签到失败";
                                    updateContentTV();
//                                    pos1MyApi.pos_cancel();
                                    pos1MyApi.setUseSynch(false);
                                } else if(dpl.getType().equals("w") && dpl.getMsg().equals("欢迎使用银联云闪付")) {
                                    LfLog.d(TAG, dpl.getMsg() + "POS1签到失败,取消pos操作,设置pos状态");
                                    showContent[0] = dpl.getMsg() + "POS1签到失败,取消pos操作,设置pos状态";
                                    showContent[1] = "";
                                    updateContentTV();
                                    pos1MyApi.pos_cancel();
                                    SleepUtils._sleep(7000);
                                    PosConfig.getInstance().setMightPos1Signed(false);
                                    PosConfig.getInstance().setMightPos1Signing(false);
                                } else {
                                    LfLog.d(TAG, "POS1签到失败");
                                    PosConfig.getInstance().setMightPos1Signed(false);
                                    PosConfig.getInstance().setMightPos1Signing(false);
                                }
                            }
                        } else if (callbackMsg.op_type.getDesc().equals(E_OP_TYPE.OP_POS_DISPLAY.getDesc())) {
                            if (dpl.getType().equals("-3")
                                    || dpl.getType().equals("-4")) {
                                LfLog.d(TAG, "POS1签到失败");
                                PosConfig.getInstance().setMightPos1Signed(false);
                                PosConfig.getInstance().setMightPos1Signing(false);
                                showContent[0] = "POS1签到失败";
                                updateContentTV();
                                pos1MyApi.setUseSynch(false);
                                pos1MyApi.pos_release();
                                SleepUtils._sleep(5000);
                                initializePos1();
                            }
                        }
                    } else if (ret.getRst() == E_REQ_RETURN.REQ_BUSY.getRst()
                            || ret.getRst() == E_REQ_RETURN.REQ_DENY.getRst()) {//请求返回忙或请求返回拒绝
                        LfLog.e(TAG, ret.getRst() == E_REQ_RETURN.REQ_BUSY.getRst() ? "POS1签到请求返回忙" : "POS1签到请求返回请求拒绝");
                        pos1MyApi.pos_cancel();
                        SleepUtils._sleep(5000);
                        PosConfig.getInstance().setMightPos1Signing(false);
                    }
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void pos2Sign() {
        PosConfig.getInstance().setMightPos2Signing(true);
        pos2MyApi.setUseSynch(true);
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        byte[] content = "正在签到".getBytes("gb2312");
                        int len = content.length;
                        lfLattice.showThirdContent(content, len);
                        showContent[0] = new String(content, "utf-8");
                        updateContentTV();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    E_REQ_RETURN ret = pos2MyApi.pos_signin();
                    if (ret.getRst() == 0) {//请求成功
                        CallbackMsg callbackMsg = (CallbackMsg) ret.getObj();
                        Display dpl = callbackMsg.dsp;
                        if (callbackMsg == null || callbackMsg.op_type == null
                                || dpl.getType() == null || dpl.getMsg() == null) {
                            LfLog.d(TAG, "POS2签到返回为空");
                            return;
                        }
                        LfLog.d(TAG, "POS2签到返回, callbackMsg.reply:" + callbackMsg.reply
                                + ", op_type:"+ callbackMsg.op_type.getDesc()
                                + " ,dpl.getType:" + dpl.getType()
                                + " ,dpl.getMsg:" + dpl.getMsg());
                        if (callbackMsg.op_type.getDesc().equals(E_OP_TYPE.OP_POS_SIGNIN.getDesc())) { //判断签到成功
                            if (callbackMsg.reply == 0) {
                                if (dpl.getType().equals("00")) {
                                    LfLog.d(TAG, "POS2签到成功");
                                    PosConfig.getInstance().setMightPos2Signed(true);
                                    PosConfig.getInstance().setMightPos2Signing(false);
                                    showContent[0] = "POS1签到成功";
                                    updateContentTV();
                                    pos2MyApi.setUseSynch(false);
                                    LfLog.e(TAG, "POS2是否签到状态:" + PosConfig.getInstance().isMightPos1Signed()
                                            + " ,POS2是否处于签到状态:" + PosConfig.getInstance().isMightPos1Signing());
                                } else if (dpl.getType().equals("-3")
                                        || dpl.getType().equals("-4")) {
                                    LfLog.d(TAG, "POS2签到失败");
                                    PosConfig.getInstance().setMightPos2Signed(false);
                                    PosConfig.getInstance().setMightPos2Signing(false);
                                    showContent[0] = "POS2签到失败";
                                    updateContentTV();
                                    pos2MyApi.setUseSynch(false);
                                }
                            } else {
                                if (dpl.getType().equals("Z1") || dpl.getType().equals("XY")
                                        || dpl.getType().equals("X8") || dpl.getType().equals("X7")
                                        || dpl.getType().equals("A0") || dpl.getType().equals("X6")
                                        || dpl.getType().equals("XX") || dpl.getType().equals("-3")
                                        || dpl.getType().equals("-4") || dpl.getType().equals("Z0")) {
                                    LfLog.d(TAG, "POS2签到失败");
                                    PosConfig.getInstance().setMightPos2Signed(false);
                                    PosConfig.getInstance().setMightPos2Signing(false);
                                    showContent[0] = "POS2签到失败";
                                    updateContentTV();
//                                    pos2MyApi.pos_cancel();
                                    pos2MyApi.setUseSynch(false);
                                } else if(dpl.getType().equals("w") && dpl.getMsg().equals("欢迎使用银联云闪付")) {
                                    LfLog.d(TAG, dpl.getMsg() + "POS2签到失败,取消pos操作,设置pos状态");
                                    showContent[0] = dpl.getMsg() + "POS2签到失败,取消pos操作,设置pos状态";
                                    showContent[1] = "";
                                    updateContentTV();
                                    pos2MyApi.pos_cancel();
                                    SleepUtils._sleep(7000);
                                    PosConfig.getInstance().setMightPos2Signed(false);
                                    PosConfig.getInstance().setMightPos2Signing(false);
                                } else {
                                    LfLog.d(TAG, "POS2签到失败");
                                    PosConfig.getInstance().setMightPos2Signed(false);
                                    PosConfig.getInstance().setMightPos2Signing(false);
                                }
                            }
                        } else if (callbackMsg.op_type.getDesc().equals(E_OP_TYPE.OP_POS_DISPLAY.getDesc())) {
                            if (dpl.getType().equals("-3")
                                    || dpl.getType().equals("-4")) {
                                LfLog.d(TAG, "POS2签到失败");
                                PosConfig.getInstance().setMightPos2Signed(false);
                                PosConfig.getInstance().setMightPos2Signing(false);
                                showContent[0] = "POS2签到失败";
                                updateContentTV();
                                pos2MyApi.setUseSynch(false);
                                pos2MyApi.pos_release();
                                SleepUtils._sleep(5000);
                                initializePos2();
                            }
                        }
                    } else if (ret.getRst() == E_REQ_RETURN.REQ_BUSY.getRst()
                            || ret.getRst() == E_REQ_RETURN.REQ_DENY.getRst()) {//请求返回忙或请求返回拒绝
                        LfLog.e(TAG, ret.getRst() == E_REQ_RETURN.REQ_BUSY.getRst() ? "POS2签到请求返回忙" : "POS2签到请求返回请求拒绝");
                        pos2MyApi.pos_cancel();
                        SleepUtils._sleep(5000);
                        PosConfig.getInstance().setMightPos2Signing(false);
                    }
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void pos1Pay() {
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    PosConfig.getInstance().setMightPos1Paying(true);
                    pos1MyApi.setUseSynch(true);
                    E_REQ_RETURN ret = pos1MyApi.pos_purchase(1);
                    if (ret.getRst() == E_REQ_RETURN.REQ_OK.getRst()) {//请求成功
                        CallbackMsg callbackMsg = (CallbackMsg) ret.getObj();
                        REPLY mREPLY = (REPLY) ((CallbackMsg) ret.getObj()).mREPLY;
                        Display dpl = callbackMsg.dsp;
                        if (callbackMsg == null || callbackMsg.op_type == null
                                || dpl.getType() == null || dpl.getMsg() == null
                                || mREPLY == null) {
                            LfLog.d(TAG, "POS1消费返回为空");
                            return;
                        }
                        LfLog.d(TAG, "POS1消费返回, callbackMsg.reply:" + callbackMsg.reply
                                + ", op_type:"+ callbackMsg.op_type.getDesc()
                                + " ,dpl.getType:" + dpl.getType()
                                + " ,dpl.getMsg:" + dpl.getMsg());
                        if (callbackMsg.op_type.getDesc().equals(E_OP_TYPE.OP_POS_PURCHASE.getDesc())) {
                            if (callbackMsg.reply == 0) {
                                if (dpl.getType().equals("00") && dpl.getMsg().contains("交易成功")) {
                                    showContent[0] = "POS1消费成功";
                                    showContent[1] = "";
                                    updateContentTV();
                                    if (PosConfig.getInstance().isMightPos1PlaySound()) {
                                        LfLog.d(TAG, "POS1消费成功");
                                        playSound(getApplication(), R.raw.success);
                                    }
                                    SleepUtils._sleep(2000);
                                    PosConfig.getInstance().setMightPos1Paying(false);
                                }
                            } else {
                                if (dpl.getMsg().contains("XY交易人为取消")
                                        || dpl.getMsg().contains("XX交易异常")
                                        || dpl.getMsg().contains("X8数据接收有误")
                                        || dpl.getMsg().contains("串口收发超时")
                                        || dpl.getMsg().contains("串口IO错误")) {
                                    LfLog.d(TAG, "POS1交易失败, " + dpl.getMsg() + " ,设置POS1不在交易状态");
                                    showContent[0] = "POS1消费失败";
                                    showContent[1] = "";
                                    updateContentTV();
                                    if (PosConfig.getInstance().isMightPos1PlaySound()) {
                                        playSound(getApplication(), R.raw.fail);
                                        try {
                                            byte[] content = "交易失败".getBytes("gb2312");
                                            int len = content.length;
                                            lfLattice.showThirdContent(content, len);
                                            showContent[1] = dpl.getMsg();
                                            updateContentTV();
                                        } catch (UnsupportedEncodingException e) {
                                            e.printStackTrace();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    SleepUtils._sleep(2000);
                                    PosConfig.getInstance().setMightPos1Paying(false);
                                } else if (dpl.getMsg().contains("A0MAC校验错")) {
                                    LfLog.d(TAG, "A0MAC校验错2");
                                    LfLog.d(TAG, "POS1A0MAC校验错,请重新签到，设置POS1不在交易状态");
                                    if (PosConfig.getInstance().isMightPos1PlaySound()) {
                                        playSound(getApplication(), R.raw.mac_error);
                                    }
                                    SleepUtils._sleep(2000);
                                    PosConfig.getInstance().setMightPos1Paying(false);
                                    PosConfig.getInstance().setMightPos1Signed(false);
                                } else if(dpl.getMsg().contains("Z1交易超时")) {
                                    LfLog.d(TAG, "POS1交易失败, " + dpl.getMsg() + " ,设置POS1不在交易状态");
                                    PosConfig.getInstance().setMightPos1Paying(false);
                                } else {
                                    LfLog.d(TAG, "POS1交易失败, " + dpl.getMsg() + " ,设置POS1不在交易状态");
                                    if (dpl.getMsg().contains("读卡成功")
                                            || dpl.getMsg().contains("交易中")
                                            || dpl.getMsg().contains("请等待")) {
                                        PosConfig.getInstance().setMightPos1Signed(false);
                                    }
                                    if (PosConfig.getInstance().isMightPos1PlaySound()) {
                                        playSound(getApplication(), R.raw.fail);
                                    }
                                    SleepUtils._sleep(2000);
                                    PosConfig.getInstance().setMightPos1Paying(false);
                                }
                            }
                        } else if (callbackMsg.op_type.getDesc().equals(E_OP_TYPE.OP_POS_DISPLAY.getDesc())) {
                            if (dpl.getType().equals("-3")
                                    || dpl.getType().equals("-4")) {
                                if (PosConfig.getInstance().isMightPos1PlaySound()) {
                                    LfLog.d(TAG, "POS1消费失败，串口收发超时");
                                    playSound(getApplication(), R.raw.serial_port_timeout);
                                }
                                SleepUtils._sleep(2000);
                                PosConfig.getInstance().setMightPos1Signed(false);
                                PosConfig.getInstance().setMightPos1Paying(false);
                            }
                        }
                    } else if (ret.getRst() == E_REQ_RETURN.REQ_BUSY.getRst()
                            || ret.getRst() == E_REQ_RETURN.REQ_DENY.getRst()) {//请求返回忙或请求返回拒绝
                        LfLog.e(TAG, ret.getRst() == E_REQ_RETURN.REQ_BUSY.getRst() ? "POS1消费请求返回忙" : "POS1消费请求返回请求拒绝");
                        SleepUtils._sleep(2000);
                        PosConfig.getInstance().setMightPos1Paying(false);
                    }
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void pos2Pay() {
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    PosConfig.getInstance().setMightPos2Paying(true);
                    pos2MyApi.setUseSynch(true);
                    E_REQ_RETURN ret = pos2MyApi.pos_purchase(1);
                    if (ret.getRst() == E_REQ_RETURN.REQ_OK.getRst()) {//请求成功
                        CallbackMsg callbackMsg = (CallbackMsg) ret.getObj();
                        REPLY mREPLY = (REPLY) ((CallbackMsg) ret.getObj()).mREPLY;
                        Display dpl = callbackMsg.dsp;
                        if (callbackMsg == null || callbackMsg.op_type == null
                                || dpl.getType() == null || dpl.getMsg() == null
                                || mREPLY == null) {
                            LfLog.d(TAG, "POS1消费返回为空");
                            return;
                        }
                        LfLog.d(TAG, "POS2消费返回, callbackMsg.reply:" + callbackMsg.reply
                                + ", op_type:"+ callbackMsg.op_type.getDesc()
                                + " ,dpl.getType:" + dpl.getType()
                                + " ,dpl.getMsg:" + dpl.getMsg());
                        if (callbackMsg.op_type.getDesc().equals(E_OP_TYPE.OP_POS_PURCHASE.getDesc())) {
                            if (callbackMsg.reply == 0) {
                                if (dpl.getType().equals("00") && dpl.getMsg().contains("交易成功")) {
                                    showContent[0] = "POS2消费成功";
                                    showContent[1] = "";
                                    updateContentTV();
                                    if (PosConfig.getInstance().isMightPos2PlaySound()) {
                                        LfLog.d(TAG, "POS2消费成功");
                                        playSound(getApplication(), R.raw.success);
                                    }
                                    SleepUtils._sleep(2000);
                                    PosConfig.getInstance().setMightPos2Paying(false);
                                }
                            } else {
                                if (dpl.getMsg().contains("XY交易人为取消")
                                        || dpl.getMsg().contains("XX交易异常")
                                        || dpl.getMsg().contains("X8数据接收有误")
                                        || dpl.getMsg().contains("串口收发超时")
                                        || dpl.getMsg().contains("串口IO错误")) {
                                    LfLog.d(TAG, "POS2交易失败, " + dpl.getMsg() + " ,设置POS2不在交易状态");
                                    showContent[0] = "POS1消费失败";
                                    showContent[1] = "";
                                    updateContentTV();
                                    if (PosConfig.getInstance().isMightPos2PlaySound()) {
                                        playSound(getApplication(), R.raw.fail);
                                        try {
                                            byte[] content = "交易失败".getBytes("gb2312");
                                            int len = content.length;
                                            lfLattice.showThirdContent(content, len);
                                            showContent[1] = dpl.getMsg();
                                            updateContentTV();
                                        } catch (UnsupportedEncodingException e) {
                                            e.printStackTrace();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    SleepUtils._sleep(2000);
                                    PosConfig.getInstance().setMightPos2Paying(false);
                                } else if (dpl.getMsg().contains("A0MAC校验错")) {
                                    LfLog.d(TAG, "A0MAC校验错2");
                                    LfLog.d(TAG, "POS2A0MAC校验错,请重新签到，设置POS2不在交易状态");
                                    if (PosConfig.getInstance().isMightPos2PlaySound()) {
                                        playSound(getApplication(), R.raw.mac_error);
                                    }
                                    SleepUtils._sleep(2000);
                                    PosConfig.getInstance().setMightPos2Paying(false);
                                    PosConfig.getInstance().setMightPos2Signed(false);
                                } else if(dpl.getMsg().contains("Z1交易超时")) {
                                    LfLog.d(TAG, "POS2交易失败, " + dpl.getMsg() + " ,设置POS2不在交易状态");
                                    PosConfig.getInstance().setMightPos2Paying(false);
                                } else {
                                    LfLog.d(TAG, "POS2交易失败, " + dpl.getMsg() + " ,设置POS2不在交易状态");
                                    if (dpl.getMsg().contains("读卡成功")
                                            || dpl.getMsg().contains("交易中")
                                            || dpl.getMsg().contains("请等待")) {
                                        PosConfig.getInstance().setMightPos2Signed(false);
                                    }
                                    if (PosConfig.getInstance().isMightPos2PlaySound()) {
                                        playSound(getApplication(), R.raw.fail);
                                    }
                                    SleepUtils._sleep(2000);
                                    PosConfig.getInstance().setMightPos2Paying(false);
                                }
                            }
                        } else if (callbackMsg.op_type.getDesc().equals(E_OP_TYPE.OP_POS_DISPLAY.getDesc())) {
                            if (dpl.getType().equals("-3")
                                    || dpl.getType().equals("-4")) {
                                if (PosConfig.getInstance().isMightPos2PlaySound()) {
                                    LfLog.d(TAG, "POS2消费失败，串口收发超时");
                                    playSound(getApplication(), R.raw.serial_port_timeout);
                                }
                                SleepUtils._sleep(2000);
                                PosConfig.getInstance().setMightPos2Signed(false);
                                PosConfig.getInstance().setMightPos2Paying(false);
                            }
                        }
                    } else if (ret.getRst() == E_REQ_RETURN.REQ_BUSY.getRst()
                            || ret.getRst() == E_REQ_RETURN.REQ_DENY.getRst()) {//请求返回忙或请求返回拒绝
                        LfLog.e(TAG, ret.getRst() == E_REQ_RETURN.REQ_BUSY.getRst() ? "POS1消费请求返回忙" : "POS1消费请求返回请求拒绝");
                        SleepUtils._sleep(2000);
                        PosConfig.getInstance().setMightPos2Paying(false);
                    }
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ILfListener pos1Listener = new ILfListener() {
        @Override
        public void onCallback(Message msg) {
            CallbackMsg cbmsg = (CallbackMsg) msg.obj;
            Display dpl = cbmsg.dsp;
            if (cbmsg.op_type != null && dpl != null) {
                LfLog.d(TAG, "POS1---->回调:"
                        + ", cbmsg.op_type:" + cbmsg.op_type
                        + ", dpl.getType:" + dpl.getType()
                        + ", dpl.getMsg:" + dpl.getMsg());
            }
            switch (cbmsg.op_type) {
                case OP_POS_DISPLAY://POS提示信息
                    if (PosConfig.getInstance().isMightPos1PlaySound()) {
                        LfLog.d(TAG, "POS1---->OP_POS_DISPLAY:"
                                + ", cbmsg.op_type:" + cbmsg.op_type
                                + ", dpl.getType:" + dpl.getType()
                                + ", dpl.getMsg:" + dpl.getMsg());
                        if (dpl.getType().equals("w")) {
                            try {
                                byte[] content = dpl.getMsg().getBytes("gb2312");
                                int len = content.length;
                                lfLattice.showThirdContent(content, len);
                                showContent[0] = dpl.getMsg();
                                updateContentTV();
                                LfLog.d(TAG, "POS1提示信息wwwwwwwww" + ", dpl.getType:" + dpl.getType() + ", dpl.getMsg:" + dpl.getMsg());
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else if (dpl.getType().equals("x")) {
                            try {
                                byte[] content = dpl.getMsg().getBytes("gb2312");
                                int len = content.length;
                                lfLattice.showFourthContent(content, len);
                                showContent[1] = dpl.getMsg();
                                updateContentTV();
                                LfLog.d(TAG, "POS1提示信息xxxxxxxxx" + ", dpl.getType:" + dpl.getType() + ", dpl.getMsg:" + dpl.getMsg());
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else if (dpl.getType().endsWith(DisplayType._h.getType())) {//读卡提示信息
                            LfLog.d(TAG, "设置POS1读到卡，POS1正在支付状态");
                            PosConfig.getInstance().setMightPos1ReadCard(true);
                        }
                        break;
                    }
                default:
                    break;
            }
        }
    };

    private ILfListener pos2Listener = new ILfListener() {
        @Override
        public void onCallback(Message msg) {
            CallbackMsg cbmsg = (CallbackMsg) msg.obj;
            Display dpl = cbmsg.dsp;
            if (cbmsg.op_type != null && dpl != null) {
                LfLog.d(TAG, "POS2---->回调:"
                        + ", cbmsg.op_type:" + cbmsg.op_type
                        + ", dpl.getType:" + dpl.getType()
                        + ", dpl.getMsg:" + dpl.getMsg());
            }
            switch (cbmsg.op_type) {
                case OP_POS_DISPLAY://POS提示信息
                    if (PosConfig.getInstance().isMightPos2PlaySound()) {
                        LfLog.d(TAG, "POS2---->OP_POS_DISPLAY:"
                                + ", cbmsg.op_type:" + cbmsg.op_type
                                + ", dpl.getType:" + dpl.getType()
                                + ", dpl.getMsg:" + dpl.getMsg());
                        if (dpl.getType().equals("w")) {
                            try {
                                byte[] content = dpl.getMsg().getBytes("gb2312");
                                int len = content.length;
                                lfLattice.showThirdContent(content, len);
                                showContent[0] = dpl.getMsg();
                                updateContentTV();
                                LfLog.d(TAG, "POS2提示信息wwwwwwwww" + ", dpl.getType:" + dpl.getType() + ", dpl.getMsg:" + dpl.getMsg());
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else if (dpl.getType().equals("x")) {
                            try {
                                byte[] content = dpl.getMsg().getBytes("gb2312");
                                int len = content.length;
                                lfLattice.showFourthContent(content, len);
                                showContent[1] = dpl.getMsg();
                                updateContentTV();
                                LfLog.d(TAG, "POS2提示信息xxxxxxxxx" + ", dpl.getType:" + dpl.getType() + ", dpl.getMsg:" + dpl.getMsg());
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else if (dpl.getType().endsWith(DisplayType._h.getType())) {//读卡提示信息
                            LfLog.d(TAG, "设置POS2读到卡，POS1正在支付状态");
                            PosConfig.getInstance().setMightPos2ReadCard(true);
                        }
                        break;
                    }
                default:
                    break;
            }
        }
    };

//    private ILfListener pos2Listener = new ILfListener() {
//        @Override
//        public void onCallback(Message msg) {
//            CallbackMsg cbmsg = (CallbackMsg) msg.obj;
//            Display dpl = cbmsg.dsp;
//            if (cbmsg.op_type != null && dpl != null) {
//                LfLog.d(TAG, "POS1---->回调:"
//                        + ", cbmsg.op_type:" + cbmsg.op_type
//                        + ", dpl.getType:" + dpl.getType()
//                        + ", dpl.getMsg:" + dpl.getMsg());
//            }
//            switch (cbmsg.op_type) {
//                case OP_POS_DISPLAY://POS提示信息
//                    if (cbmsg.reply == 0) {//成功
//                        if (dpl.getType().endsWith(DisplayType._6.getType())) {
//                        } else if (dpl.getType().endsWith(DisplayType._4.getType())) {//通讯信息提示
//                            LfLog.d(TAG, "通讯信息提示" + ", dpl.getType::" + dpl.getType() + ", dpl.getMsg:" + dpl.getMsg());
//                        } else if (dpl.getType().endsWith(DisplayType._2.getType())) {//读卡提示信息
//                            LfLog.d(TAG, "请刷卡或刷手机" + ", dpl.getType::" + dpl.getType() + ", dpl.getMsg:" + dpl.getMsg());
//                            PosConfig.getInstance().setMightPos2Paying(true);
//                        } else if (dpl.getType().endsWith(DisplayType._h.getType())) {//读卡提示信息
//                            LfLog.d(TAG, "设置POS2读到卡，POS2正在支付状态");
//                            PosConfig.getInstance().setMightPos2ReadCard(true);
//                            PosConfig.getInstance().setMightPos2Paying(true);
//                        } else if (dpl.getType().equals("-3")) {
//                            LfLog.d(TAG, "POS2串口收发超时，设置POS2不在交易状态");
//                            PosConfig.getInstance().setMightPos2Paying(false);
//                        } else if (dpl.getType().equals("-4")) {
//                            LfLog.d(TAG, "POS2串口IO错误，设置POS2不在交易状态");
//                            PosConfig.getInstance().setMightPos2Paying(false);
//                        }
//                        try {
//                            if (dpl.getType().equals("w") && dpl.getMsg().equals("停车费用")) {
//                                byte[] content = dpl.getMsg().getBytes("gb2312");
//                                int len = content.length;
//                                lfLattice.showThirdContent(content, len);
//                                showContent[0] = dpl.getMsg();
//                                updateContentTV();
//                                LfLog.d(TAG, "POS2提示信息wwwwwwwww" + ", dpl.getType:" + dpl.getType() + ", dpl.getMsg:" + dpl.getMsg());
//                            } else if (dpl.getType().equals("w") && dpl.getMsg().equals("欢迎使用银联云闪付")) {
//                                SleepUtils._sleep(1000);
//                                byte[] content = dpl.getMsg().getBytes("gb2312");
//                                int len = content.length;
//                                lfLattice.showThirdContent(content, len);
//                                showContent[0] = dpl.getMsg();
//                                updateContentTV();
//                                LfLog.d(TAG, "POS2提示信息wwwwwwwww" + ", dpl.getType:" + dpl.getType() + ", dpl.getMsg:" + dpl.getMsg());
//                            } else if (dpl.getType().equals("w")) {
//                                SleepUtils._sleep(1000);
//                                byte[] content = dpl.getMsg().getBytes("gb2312");
//                                int len = content.length;
//                                lfLattice.showThirdContent(content, len);
//                                showContent[0] = dpl.getMsg();
//                                updateContentTV();
//                                LfLog.d(TAG, "POS1提示信息wwwwwwwww" + ", dpl.getType:" + dpl.getType() + ", dpl.getMsg:" + dpl.getMsg());
//                            }
//                            if (dpl.getType().equals("x")) {
//                                LfLog.d(TAG, "POS2提示信息xxxxxxxxx" + ", dpl.getType:" + dpl.getType() + ", dpl.getMsg:" + dpl.getMsg());
//                                byte[] content = dpl.getMsg().getBytes("gb2312");
//                                int len = content.length;
//                                lfLattice.showFourthContent(content, len);
//                                showContent[0] = dpl.getMsg();
//                                updateContentTV();
//                            }
//                        } catch (UnsupportedEncodingException e) {
//                            e.printStackTrace();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    break;
//                default:
//                    break;
//            }
//        }
//    };

}
