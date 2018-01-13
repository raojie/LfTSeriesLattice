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

import java.io.UnsupportedEncodingException;

public class InitActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
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
        lfLattice.InitLfLattice("/dev/ttyS3","115200");
        initialize();
        bindEvent();
        playSound(getApplication(), R.raw.rebooting);
    }

    public void playSound(Context context, int id) {
        try {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.release();
            }
            mediaPlayer = MediaPlayer.create(context, id);
            mediaPlayer.start();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    private Handler autoShowWelcome = new Handler() {
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
            }
        }
    };

    private Handler autoOpenHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    openSrcRunnable.run();
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
//        autoOpenHandler.sendEmptyMessageDelayed(1, 2000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("onResume");
        // activity启动两秒钟后，发送一个message
        autoShowWelcome.sendEmptyMessageDelayed(1, 1000);
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
        start();
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
        pos1MyApi.pos_init("222.76.113.29", 8000, "/dev/ttyS1", "9600");
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
        pos2MyApi.pos_init("222.76.113.29", 8000, "/dev/ttyS2", "9600");
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
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private long background_current_time = -1;
    public void start() {
        Log.d(TAG, "start...");
        backgroundThread.start();
        checkCurrentPosStatusThread.start();
    }

    //检测当前pos的流程处于什么状态,并进行相应操作
    private Thread checkCurrentPosStatusThread = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                while (true) {

                    checkCurrentPay();

//                    checkPos1MightSignBusy();

//                    checkCurrentPos2SignedStatus();

                    checkCurrentPos1PayStatus();

                    checkCurrentPos2PayStatus();

//                    SleepUtils._sleep(500);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    });

    //检测pos1是否签到接口忙.
    private long lastCheckPos1MightSignBusy = -1;
    private void checkPos1MightSignBusy() {
        if (PosConfig.getInstance().isMightPos1Signed()) {
            return;
        }

        if (lastCheckPos1MightSignBusy == -1) {
            lastCheckPos1MightSignBusy = background_current_time;
        } else {//非初始状态，则做判断
            if ((background_current_time - lastCheckPos1MightSignBusy) > (1 * 60 * 1000)) {//大于1分钟
                if (PosConfig.getInstance().isMightPos1SignedBusy()) {//pos1签到接口忙
                    if (PosConfig.getInstance().isMightPos2Signing() || PosConfig.getInstance().isMightPos2SignedBusy()) {
                        PosConfig.getInstance().setMightCheckPos2Signing(true);
                        if (PosConfig.getInstance().isMightPos2SignedBusy()) {
                            pos2MyApi.pos_release();
                            SleepUtils._sleep(3000);
                            PosConfig.getInstance().setMightPos2SignedBusy(false);
                        }
                    }
                    PosConfig.getInstance().setMightCheckPos2Signing(false);
                    lastCheckPos2Signing = System.currentTimeMillis();
                }
            }
        }
    }

    //检测当前pos1消费接口状态,当接口忙是释放pos1
    private void checkCurrentPos2PayStatus() {
        if (PosConfig.getInstance().isMightPos2Signed()) {
            if (!PosConfig.getInstance().isMightPos2PayBusy()) {
                return;
            }

            if (PosConfig.getInstance().isMightPos2PayBusy()) {
                PosConfig.getInstance().setMightCheckPos2Paying(true);
                LfLog.d(TAG, "pos2消费接口忙，调用取消消费接口");
                PosConfig.getInstance().setMightPos2PayBusy(false);
                PosConfig.getInstance().setMightPos2Paying(false);
                pos2MyApi.pos_release();
                SleepUtils._sleep(5000);
                PosConfig.getInstance().setMightCheckPos2Paying(false);
                initializePos2();
            }
        } else {
            return;
        }
    }

    //检测当前pos1消费接口状态,当接口忙是释放pos1
    private void checkCurrentPos1PayStatus() {
        if (PosConfig.getInstance().isMightPos1Signed()) {
            if (!PosConfig.getInstance().isMightPos1PayBusy()) {
                return;
            }

            if (PosConfig.getInstance().isMightPos1PayBusy()) {
                PosConfig.getInstance().setMightCheckPos1Paying(true);
                LfLog.d(TAG, "pos1消费接口忙，调用取消消费接口");
                PosConfig.getInstance().setMightPos1PayBusy(false);
                PosConfig.getInstance().setMightPos1Paying(false);
                pos1MyApi.pos_release();
                SleepUtils._sleep(5000);
                PosConfig.getInstance().setMightCheckPos1Paying(false);
                initializePos1();
            }
        } else {
            return;
        }
    }

//    //检测当前pos1消费接口状态,当接口忙是释放pos1
//    private long lastCheckPos1Paying = -1;
//    private void checkCurrentPos1PayStatus() {
//        if (PosConfig.getInstance().isMightPos1Signed()) {
//            if (!PosConfig.getInstance().isMightPos1PayBusy()) {
//                return;
//            }
//            boolean checkPos1Paying = false;
//            if (lastCheckPos1Paying == -1) {
//                lastCheckPos1Paying = background_current_time;
//            } else {//非初始状态，则做判断
//                if ((lastCheckPos1Paying - lastCheckPos1Paying) > (2 * 60 * 1000)) {//2分钟一次---测试用
//                    checkPos1Paying = true;
//                    LfLog.e(TAG, "checkPos1Paying:" + checkPos1Paying);
//                }
//            }
//
//            if (checkPos1Paying) {
//                if (PosConfig.getInstance().isMightPos1PayBusy()) {
////                    PosConfig.getInstance().setMightCheckPos1Paying(true);
//                    LfLog.d(TAG, "pos1消费接口忙，调用取消消费接口");
//                    PosConfig.getInstance().setMightPos1PayBusy(false);
//                    PosConfig.getInstance().setMightPos1Paying(false);
//                    pos1MyApi.pos_release();
//                    SleepUtils._sleep(3000);
////                    PosConfig.getInstance().setMightCheckPos1Paying(false);
//                }
//                lastCheckPos1Paying = System.currentTimeMillis();
//            }
//        } else {
//            return;
//        }
//    }

    //检测当前哪个pos正在消费
    private void checkCurrentPay() {
        if (PosConfig.getInstance().isMightPos1ReadCard() || PosConfig.getInstance().isMightPos2ReadCard()) {
            if (PosConfig.getInstance().isMightPos1ReadCard() && PosConfig.getInstance().isMightPos2Paying()) {
                PosConfig.getInstance().setMightPos1ReadCard(false);
                PosConfig.getInstance().setMightPos2PlaySound(false);
                LfLog.d(TAG, "pos1读到卡,pos2取消消费,设置pos2播放语音状态为false");
                pos2MyApi.pos_cancel();
            } else {
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
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                while (true) {
                    background_current_time = System.currentTimeMillis();

                    checkPos1Sign();

                    checkPos2Sign();

//                    checkCurrentPos1SigningStatus();

//                    checkCurrentPos2SigningStatus();

                    checkWhetherToStartPay();

                    SleepUtils._sleep(1000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    });

    //检测pos2正在签到状态,是否处于正在签到状态,pos1却没有签到成功
    private long lastCheckPos2Signing = -1;
    private void checkCurrentPos2SigningStatus() {
        if (PosConfig.getInstance().isMightPos2Signed()) {
            PosConfig.getInstance().setMightPos2Signing(false);
            return;
        }
        if (lastCheckPos2Signing == -1) {
            lastCheckPos2Signing = background_current_time;
        } else {//非初始状态，则做判断
            if ((background_current_time - lastCheckPos2Signing) > (2 * 60 * 1000)) {//大于2分钟
                if (!PosConfig.getInstance().isMightPos2Signed()) {//pos2不处于签到成功状态
                    if (PosConfig.getInstance().isMightPos2Signing() || PosConfig.getInstance().isMightPos2SignedBusy()) {
                        PosConfig.getInstance().setMightCheckPos2Signing(true);
                        if (PosConfig.getInstance().isMightPos2Signing()) {
                            PosConfig.getInstance().setMightPos2Signing(false);
                        }
                        if (PosConfig.getInstance().isMightPos2SignedBusy()) {
                            pos2MyApi.pos_release();
                            SleepUtils._sleep(3000);
                            PosConfig.getInstance().setMightPos2SignedBusy(false);
                        }
                    }
                    PosConfig.getInstance().setMightCheckPos2Signing(false);
                }
                lastCheckPos2Signing = System.currentTimeMillis();
            }
        }
    }

    //检测pos1正在签到状态,是否处于正在签到状态,pos1却没有签到成功
    private long lastCheckPos1Signing = -1;
    private void checkCurrentPos1SigningStatus() {
        if (PosConfig.getInstance().isMightPos1Signed()) {
            PosConfig.getInstance().setMightPos1Signing(false);
            return;
        }
        if (lastCheckPos1Signing == -1) {
            lastCheckPos1Signing = background_current_time;
        } else {//非初始状态，则做判断
            if ((background_current_time - lastCheckPos1Signing) > (2 * 60 * 1000)) {//大于2分钟
                if (!PosConfig.getInstance().isMightPos1Signed()) {//pos1不处于签到成功状态
                    if (PosConfig.getInstance().isMightPos1Signing() || PosConfig.getInstance().isMightPos1SignedBusy()) {
                        PosConfig.getInstance().setMightCheckPos1Signing(true);
                        if (PosConfig.getInstance().isMightPos1Signing()) {
                            PosConfig.getInstance().setMightPos1Signing(false);
                        }
                        if (PosConfig.getInstance().isMightPos1SignedBusy()) {
                            pos1MyApi.pos_release();
                            SleepUtils._sleep(3000);
                            PosConfig.getInstance().setMightPos1SignedBusy(false);
                        }
                    }
                    PosConfig.getInstance().setMightCheckPos1Signing(false);
                }
                lastCheckPos1Signing = System.currentTimeMillis();
            }
        }
    }

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
                    if ((background_current_time - lastCheckPos1Sign) > (15 * 1000)) {//15秒一次---测试用
                        checkPos1Sign = true;
                    }
                }
            }

            if (checkPos1Sign) {
                LfLog.d(TAG, "pos1未签到...");
                initializePos1();
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
                    if ((background_current_time - lastCheckPos2Sign) > (15 * 1000)) {//15秒一次---测试用
                        checkPos2Sign = true;
                    }
                }
            }

            if (checkPos2Sign) {
                LfLog.d(TAG, "pos2未签到...");
                initializePos2();
                pos2Sign();
                lastCheckPos2Sign = System.currentTimeMillis();
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
                if ((background_current_time - lastCheckWhetherToStartPay) > (0.25 * 60 * 1000)) {//15秒一次---测试用
                    checkWhetherToStartPay = true;
                    LfLog.e(TAG, "checkWhetherToStartPay:" + checkWhetherToStartPay);
                }
            }
        }

        if (checkWhetherToStartPay) {
            if (PosConfig.getInstance().isMightCheckPos1Paying() || PosConfig.getInstance().isMightCheckPos2Paying()) {
                lastCheckWhetherToStartPay = background_current_time;
            } else {
                if (PosConfig.getInstance().isMightPos1Signed() && PosConfig.getInstance().isMightPos2Signed()) {//如果pos1和pos2签到成功
                    if (!PosConfig.getInstance().isMightPos1Paying() && !PosConfig.getInstance().isMightPos2Paying()) {//如果pos1和pos2不在消费状态
//                        if (!PosConfig.getInstance().isMightPos1PayBusy() && !PosConfig.getInstance().isMightPos2PayBusy()) {//如果pos1和pos2消费接口不忙
                        PosConfig.getInstance().setMightPos1PlaySound(true);
                        PosConfig.getInstance().setMightPos2PlaySound(true);
                        LfLog.d(TAG, "发起消费...");
                        pos1Pay();
                        pos2Pay();
//                        }
                    }
                    lastCheckWhetherToStartPay = System.currentTimeMillis();
                }
            }
        }
    }

    private void pos1Sign() {
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        byte[] content = "正在签到".getBytes("gb2312");
                        int len = content.length;
                        lfLattice.showThirdContent(content, len);
                        showContent[0] = new String(content,"utf-8");
                        updateContentTV();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    pos1MyApi.setUseSynch(true);
                    E_REQ_RETURN ret = pos1MyApi.pos_signin();
                    if (ret.getRst() == 0) {//请求成功
                        PosConfig.getInstance().setMightPos1Signing(true);
                        CallbackMsg callbackMsg = (CallbackMsg) ret.getObj();
                        Display dpl = callbackMsg.dsp;
                        if (callbackMsg == null || callbackMsg.op_type == null
                                || dpl.getType() == null || dpl.getMsg() == null) {
                            LfLog.d(TAG, "POS1签到返回为空");
                            return;
                        }
                        LfLog.d(TAG, "POS1签到返回," + callbackMsg.op_type.getDesc() + " ,dpl.getType:" + dpl.getType()
                                + " ,dpl.getMsg:" + dpl.getMsg());
                        if (dpl.getType().equals("Z1") || dpl.getType().equals("XY")
                                || dpl.getType().equals("X8") || dpl.getType().equals("X7")
                                || dpl.getType().equals("A0") || dpl.getType().equals("X6")
                                || dpl.getType().equals("XX") || dpl.getType().equals("-3")
                                || dpl.getType().equals("-4")) {
                            LfLog.d(TAG, "POS1签到失败");
                            PosConfig.getInstance().setMightPos1Signed(false);
                            PosConfig.getInstance().setMightPos1Signing(false);
                            showContent[0] = "POS1签到失败";
                            updateContentTV();
                            pos1MyApi.setUseSynch(false);
                        }
                        if (callbackMsg.op_type.getDesc().equals(E_OP_TYPE.OP_POS_SIGNIN.getDesc())) { //判断签到成功
                            if (dpl.getType().equals("00")) {
                                LfLog.d(TAG, "POS1签到成功");
                                PosConfig.getInstance().setMightPos1Signed(true);
                                PosConfig.getInstance().setMightPos1Signing(false);
                                showContent[0] = "POS1签到成功";
                                updateContentTV();
                                pos1MyApi.setUseSynch(false);
                                LfLog.e(TAG, "POS1是否签到状态:" + PosConfig.getInstance().isMightPos1Signed()
                                        + " ,POS1是否处于签到状态:" + PosConfig.getInstance().isMightPos1Signing());
                            }
                        }
                    } else if(ret.getRst() == E_REQ_RETURN.REQ_BUSY.getRst()
                            || ret.getRst() == E_REQ_RETURN.REQ_DENY.getRst()) {//请求返回忙或请求返回拒绝
                        LfLog.e(TAG, ret.getRst() == E_REQ_RETURN.REQ_BUSY.getRst() ? "POS1签到请求返回忙" : "POS1签到请求返回请求拒绝");
                        PosConfig.getInstance().setMightPos1Signing(false);
                    }
//                    else if(ret.getRst() == E_REQ_RETURN.REQ_DENY.getRst()) {//请求返回拒绝
//                        LfLog.e(TAG, "POS1签到请求返回请求拒绝");
//                        PosConfig.getInstance().setMightPos1Signing(false);
//                    }
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void pos2Sign() {
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        byte[] content = "正在签到".getBytes("gb2312");
                        int len = content.length;
                        lfLattice.showThirdContent(content, len);
                        showContent[0] = new String(content,"utf-8");
                        updateContentTV();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    pos2MyApi.setUseSynch(true);
                    E_REQ_RETURN ret = pos2MyApi.pos_signin();
                    if (ret.getRst() == 0) {//请求成功
                        PosConfig.getInstance().setMightPos2Signing(true);
                        CallbackMsg callbackMsg = (CallbackMsg) ret.getObj();
                        Display dpl = callbackMsg.dsp;
                        if (callbackMsg == null || callbackMsg.op_type == null
                                || dpl.getType() == null || dpl.getMsg() == null) {
                            LfLog.d(TAG, "POS2签到返回为空");
                            return;
                        }
                        LfLog.d(TAG, "POS2签到返回," + callbackMsg.op_type.getDesc() + " ,dpl.getType:" + dpl.getType()
                                + " ,dpl.getMsg:" + dpl.getMsg());
                        if (dpl.getType().equals("Z1") || dpl.getType().equals("XY")
                                || dpl.getType().equals("X8") || dpl.getType().equals("X7")
                                || dpl.getType().equals("A0") || dpl.getType().equals("X6")
                                || dpl.getType().equals("XX") || dpl.getType().equals("-3")
                                || dpl.getType().equals("-4")) {
                            LfLog.d(TAG, "POS2签到失败");
                            PosConfig.getInstance().setMightPos2Signed(false);
                            PosConfig.getInstance().setMightPos2Signing(false);
                            showContent[0] = "POS2签到失败";
                            updateContentTV();
                            pos2MyApi.setUseSynch(false);
                        }
                        if (callbackMsg.op_type.getDesc().equals(E_OP_TYPE.OP_POS_SIGNIN.getDesc())) { //判断签到成功
                            if (dpl.getType().equals("00")) {
                                LfLog.d(TAG, "POS1签到成功");
                                PosConfig.getInstance().setMightPos2Signed(true);
                                PosConfig.getInstance().setMightPos2Signing(false);
                                showContent[0] = "POS2签到成功";
                                updateContentTV();
                                pos2MyApi.setUseSynch(false);
                                LfLog.e(TAG, "POS2是否签到状态:" + PosConfig.getInstance().isMightPos2Signed()
                                        + " ,POS2是否处于签到状态:" + PosConfig.getInstance().isMightPos2Signing());
                            }
                        }
                    } else if(ret.getRst() == E_REQ_RETURN.REQ_BUSY.getRst()
                            || ret.getRst() == E_REQ_RETURN.REQ_DENY.getRst()) {//请求返回忙或请求返回拒绝
                        LfLog.e(TAG, ret.getRst() == E_REQ_RETURN.REQ_BUSY.getRst() ? "POS2签到请求返回忙" : "POS2签到请求返回请求拒绝");
                        PosConfig.getInstance().setMightPos2Signing(false);
                    }
//                    else if(ret.getRst() == E_REQ_RETURN.REQ_DENY.getRst()) {//请求返回拒绝
//                        LfLog.e(TAG, "POS2签到请求返回请求拒绝");
//                        PosConfig.getInstance().setMightPos2Signing(false);
//                    }
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
                        LfLog.d(TAG, "POS1消费返回," + callbackMsg.op_type.getDesc() + " ,dpl.getType:" + dpl.getType()
                                + " ,dpl.getMsg:" + dpl.getMsg());
                        if (callbackMsg.op_type.getDesc().equals(E_OP_TYPE.OP_POS_PURCHASE.getDesc())) {
                            if (dpl.getType().equals("00") && dpl.getMsg().contains("交易成功")) {
                                if (PosConfig.getInstance().isMightPos1PlaySound()) {
                                    playSound(getApplication(), R.raw.success);
                                }
                                PosConfig.getInstance().setMightPos1Paying(false);
                            }
                            if (dpl.getMsg().contains("Z1交易超时")
                                    || dpl.getMsg().contains("XY交易人为取消")
                                    || dpl.getMsg().contains("XX交易异常")
                                    || dpl.getMsg().contains("X8数据接收有误")) {
                                LfLog.d(TAG, "POS1交易失败, " + dpl.getMsg() + " ,设置POS1不在交易状态");
                                PosConfig.getInstance().setMightPos1Paying(false);
                                if (PosConfig.getInstance().isMightPos1PlaySound()) {
                                    playSound(getApplication(), R.raw.fail);
                                    try {
                                        byte[] content = dpl.getMsg().getBytes("gb2312");
                                        int len = content.length;
                                        lfLattice.showThirdContent(content, len);
                                        showContent[0] = dpl.getMsg();
                                        updateContentTV();
                                    } catch (UnsupportedEncodingException e) {
                                        e.printStackTrace();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            } else if (dpl.getMsg().contains("A0MAC校验错")) {
                                LfLog.d(TAG, "A0MAC校验错2");
                                LfLog.d(TAG, "POS1A0MAC校验错,请重新签到，设置POS1不在交易状态");
                                PosConfig.getInstance().setMightPos1Paying(false);
                                PosConfig.getInstance().setMightPos1Signed(false);
                                if (PosConfig.getInstance().isMightPos1PlaySound()) {
                                    playSound(getApplication(), R.raw.mac_error);
                                }
                            }
                        }
                        if (callbackMsg.reply == 0) {//执行成功
                            if (mREPLY.code.equals("-3") || mREPLY.code.equals("-4")) {
                                LfLog.d(TAG, "POS1串口故障或超时，设置支付状态为false，设置支付接口为true");
                                PosConfig.getInstance().setMightPos1Paying(false);
                            }
                            LfLog.e(TAG, "POS1--->callbackMsg.reply1:" + callbackMsg.reply
                                    + ", mREPLY.code:" + mREPLY.code
                                    + ", mREPLY.code_info:" + mREPLY.code_info);
                            showContent[0] = "POS1消费成功";
                            updateContentTV();
                            LfLog.d(TAG, "POS1消费成功");
                        } else {//执行失败
                            showContent[0] = "POS1消费失败";
                            updateContentTV();
                            LfLog.d(TAG, "POS1消费失败");
                            PosConfig.getInstance().setMightPos1Paying(false);
                        }
                    } else if(ret.getRst() == E_REQ_RETURN.REQ_BUSY.getRst()
                            || ret.getRst() == E_REQ_RETURN.REQ_DENY.getRst()) {//请求返回忙或请求返回拒绝
                        LfLog.e(TAG, ret.getRst() == E_REQ_RETURN.REQ_BUSY.getRst() ? "POS1消费请求返回忙" : "POS1消费请求返回请求拒绝");
                        PosConfig.getInstance().setMightPos1Paying(false);
                    }
//                    else if(ret.getRst() == E_REQ_RETURN.REQ_DENY.getRst()) {//请求返回拒绝
//                        LfLog.e(TAG, "POS1消费请求返回请求拒绝");
//                        PosConfig.getInstance().setMightPos1Paying(false);
//                    }
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
                    pos2MyApi.setUseSynch(true);
                    E_REQ_RETURN ret = pos2MyApi.pos_purchase(1);
                    if (ret.getRst() == 0) {//请求成功
                        CallbackMsg callbackMsg = (CallbackMsg) ret.getObj();
                        REPLY mREPLY = (REPLY) ((CallbackMsg) ret.getObj()).mREPLY;
                        Display dpl = callbackMsg.dsp;
                        if (callbackMsg == null || callbackMsg.op_type == null
                                || dpl.getType() == null || dpl.getMsg() == null
                                || mREPLY == null) {
                            LfLog.d(TAG, "POS2消费返回为空");
                            return;
                        }
                        LfLog.d(TAG, "POS2消费返回," + callbackMsg.op_type.getDesc() + " ,dpl.getType:" + dpl.getType()
                                + " ,dpl.getMsg:" + dpl.getMsg());
                        if (callbackMsg.op_type.getDesc().equals(E_OP_TYPE.OP_POS_PURCHASE.getDesc())) {
                            if (dpl.getType().equals("00") && dpl.getMsg().contains("交易成功")) {
                                if (PosConfig.getInstance().isMightPos2PlaySound()) {
                                    playSound(getApplication(), R.raw.success);
                                }
                                PosConfig.getInstance().setMightPos2Paying(false);
                            }
                            if (dpl.getMsg().contains("Z1交易超时")
                                    || dpl.getMsg().contains("XY交易人为取消")
                                    || dpl.getMsg().contains("XX交易异常")
                                    || dpl.getMsg().contains("X8数据接收有误")) {
                                LfLog.d(TAG, "POS2交易失败, " + dpl.getMsg() + " ,设置POS2不在交易状态");
                                PosConfig.getInstance().setMightPos2Paying(false);
                                if (PosConfig.getInstance().isMightPos2PlaySound()) {
                                    playSound(getApplication(), R.raw.fail);
                                    try {
                                        byte[] content = dpl.getMsg().getBytes("gb2312");
                                        int len = content.length;
                                        lfLattice.showThirdContent(content, len);
                                        showContent[0] = dpl.getMsg();
                                        updateContentTV();
                                    } catch (UnsupportedEncodingException e) {
                                        e.printStackTrace();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            } else if (dpl.getMsg().contains("A0MAC校验错")) {
                                LfLog.d(TAG, "A0MAC校验错4");
                                LfLog.d(TAG, "POS2A0MAC校验错,请重新签到，设置POS2不在交易状态");
                                PosConfig.getInstance().setMightPos2Paying(false);
                                PosConfig.getInstance().setMightPos2Signed(false);
                                if (PosConfig.getInstance().isMightPos2PlaySound()) {
                                    playSound(getApplication(), R.raw.mac_error);
                                }
                            }
                        }
                        if (callbackMsg.reply == 0) {//执行成功
                            if (mREPLY.code.equals("-3") || mREPLY.code.equals("-4")) {
                                LfLog.d(TAG, "POS2串口故障或超时，设置支付状态为false，设置支付接口为true");
                                PosConfig.getInstance().setMightPos2Paying(false);
                                PosConfig.getInstance().setMightPos2PayBusy(true);
                            }
                            LfLog.e(TAG, "POS2--->callbackMsg.reply1:" + callbackMsg.reply
                                    + ", mREPLY.code:" + mREPLY.code
                                    + ", mREPLY.code_info:" + mREPLY.code_info);
                            showContent[0] = "POS2消费成功";
                            updateContentTV();
                            LfLog.d(TAG, "POS2消费成功");
                        } else {//执行失败
                            showContent[0] = "POS2消费失败";
                            updateContentTV();
                            LfLog.d(TAG, "POS2消费失败");
                            PosConfig.getInstance().setMightPos2Paying(false);
                        }
                    } else if(ret.getRst() == E_REQ_RETURN.REQ_BUSY.getRst()
                            || ret.getRst() == E_REQ_RETURN.REQ_DENY.getRst()) {//请求返回忙或请求返回拒绝
                        LfLog.e(TAG, ret.getRst() == E_REQ_RETURN.REQ_BUSY.getRst() ? "POS2消费请求返回忙" : "POS2消费请求返回请求拒绝");
                        PosConfig.getInstance().setMightPos2Paying(false);
                    }
//                    else if(ret.getRst() == E_REQ_RETURN.REQ_DENY.getRst()) {//请求返回拒绝
//                        LfLog.e(TAG, "POS1消费请求返回请求拒绝");
//                        PosConfig.getInstance().setMightPos2Paying(false);
//                    }
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
                case OP_POS_PURCHASE: //消费返回
                    LfLog.d(TAG, "POS1---->OP_POS_PURCHASE:"
                            + ", dpl.getType::" + dpl.getType()
                            + ", dpl.getMsg:" + dpl.getMsg());
                    try {
                        if (PosConfig.getInstance().isMightPos1PlaySound()) {
                            if (dpl.getMsg().equals("POS支付操作,交易成功")) {
                                LfLog.d(TAG, "设置pos1不在交易状态1");
                                PosConfig.getInstance().setMightPos1Paying(false);
                                playSound(getApplication(), R.raw.success);
                                byte[] content = "交易成功".getBytes("gb2312");
                                int len = content.length;
                                lfLattice.showThirdContent(content, len);
                                showContent[0] = dpl.getMsg();
                                updateContentTV();
                            }
//                            else if (dpl.getMsg().contains("Z1交易超时")) {
//                                PosConfig.getInstance().setMightPos1Paying(false);
//                            } else if (dpl.getMsg().contains("XY交易人为取消")) {
//                                LfLog.d(TAG, "POS1交易人为取消，设置POS1不在交易状态");
//                                PosConfig.getInstance().setMightPos1Paying(false);
//                            } else if (dpl.getMsg().contains("XX交易异常")) {
//                                LfLog.d(TAG, "POS1XX交易异常，设置POS1不在交易状态");
//                                PosConfig.getInstance().setMightPos1Paying(false);
//                            } else if (dpl.getMsg().contains("X8数据接收有误")) {
//                                LfLog.d(TAG, "POS1X8数据接收有误，设置POS1不在交易状态");
//                                PosConfig.getInstance().setMightPos1Paying(false);
//                                playSound(getApplication(), R.raw.fail);
//                                try {
//                                    byte[] content = dpl.getMsg().getBytes("gb2312");
//                                    int len = content.length;
//                                    lfLattice.showThirdContent(content, len);
//                                    showContent[0] = dpl.getMsg();
//                                    updateContentTV();
//                                } catch (UnsupportedEncodingException e) {
//                                    e.printStackTrace();
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//                            }
                            else if (dpl.getMsg().contains("A0MAC校验错")) {
                                LfLog.d(TAG, "A0MAC校验错1");
                                LfLog.d(TAG, "POS1A0MAC校验错,请重新签到，设置POS1不在交易状态");
                                PosConfig.getInstance().setMightPos1Paying(false);
                                PosConfig.getInstance().setMightPos1Signed(false);
                                playSound(getApplication(), R.raw.mac_error);
                            }
                            else {
                                LfLog.d(TAG, "设置pos1不在交易状态2");
                                PosConfig.getInstance().setMightPos1Paying(false);
                                playSound(getApplication(), R.raw.fail);
                                byte[] content = "交易失败".getBytes("gb2312");
                                int len = content.length;
                                lfLattice.showThirdContent(content, len);
                                showContent[0] = dpl.getMsg();
                                updateContentTV();
                            }
                        }
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case OP_POS_DISPLAY://POS提示信息
                    if (cbmsg.reply == 0) {//成功
                        if (dpl.getType().endsWith(DisplayType._6.getType())) {
                            if (dpl.getMsg().contains("Z1交易超时")
                                    || dpl.getMsg().contains("XY交易人为取消")
                                    || dpl.getMsg().contains("XX交易异常")
                                    || dpl.getMsg().contains("X8数据接收有误")) {
                                LfLog.d(TAG, "POS1交易失败, " + dpl.getMsg() + " ,设置POS1不在交易状态");
                                PosConfig.getInstance().setMightPos1Paying(false);
                                if (PosConfig.getInstance().isMightPos1PlaySound()) {
                                    playSound(getApplication(), R.raw.fail);
                                    try {
                                        byte[] content = dpl.getMsg().getBytes("gb2312");
                                        int len = content.length;
                                        lfLattice.showThirdContent(content, len);
                                        showContent[0] = dpl.getMsg();
                                        updateContentTV();
                                    } catch (UnsupportedEncodingException e) {
                                        e.printStackTrace();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            } else if (dpl.getMsg().contains("A0MAC校验错")) {
                                LfLog.d(TAG, "A0MAC校验错2");
                                LfLog.d(TAG, "POS1A0MAC校验错,请重新签到，设置POS1不在交易状态");
                                PosConfig.getInstance().setMightPos1Paying(false);
                                PosConfig.getInstance().setMightPos1Signed(false);
                                if (PosConfig.getInstance().isMightPos1PlaySound()) {
                                    playSound(getApplication(), R.raw.mac_error);
                                }
                            }
                        } else if (dpl.getType().endsWith(DisplayType._4.getType())) {//通讯信息提示
                            LfLog.d(TAG, "通讯信息提示" + ", dpl.getType:" + dpl.getType() + ", dpl.getMsg:" + dpl.getMsg());
                        } else if (dpl.getType().endsWith(DisplayType._2.getType())) {//读卡提示信息
                            LfLog.d(TAG, "请刷卡或刷手机" + ", dpl.getType:" + dpl.getType() + ", dpl.getMsg:" + dpl.getMsg());
                            PosConfig.getInstance().setMightPos1Paying(true);
                        } else if (dpl.getType().endsWith(DisplayType._h.getType())) {//读卡提示信息
                            LfLog.d(TAG, "设置POS1读到卡，POS1正在支付状态");
                            PosConfig.getInstance().setMightPos1ReadCard(true);
                            PosConfig.getInstance().setMightPos1Paying(true);
                        } else if (dpl.getType().equals("w")) {
                            try {
                                if (dpl.getMsg().equals("停车费用")) {
                                    byte[] content = dpl.getMsg().getBytes("gb2312");
                                    int len = content.length;
                                    lfLattice.showThirdContent(content, len);
                                    showContent[0] = dpl.getMsg();
                                    updateContentTV();
                                } else if (dpl.getMsg().equals("欢迎使用银联云闪付")) {
                                    PosConfig.getInstance().setMightPos1Paying(false);
                                    PosConfig.getInstance().setMightPos2Paying(false);
                                    byte[] content = dpl.getMsg().getBytes("gb2312");
                                    int len = content.length;
                                    lfLattice.showThirdContent(content, len);
                                    showContent[0] = dpl.getMsg();
                                    updateContentTV();
                                }
                                LfLog.d(TAG, "POS1提示信息wwwwwwwww" + ", dpl.getType::" + dpl.getType() + ", dpl.getMsg:" + dpl.getMsg());
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else if (dpl.getType().equals("x")) {
                            try {
                                LfLog.d(TAG, "POS1提示信息xxxxxxxxx" + ", dpl.getType::" + dpl.getType() + ", dpl.getMsg:" + dpl.getMsg());
                                byte[] content = dpl.getMsg().getBytes("gb2312");
                                int len = content.length;
                                lfLattice.showFourthContent(content, len);
                                showContent[0] = dpl.getMsg();
                                updateContentTV();
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else if (dpl.getType().equals("-3")) {
                            LfLog.d(TAG, "POS1串口收发超时，设置POS1不在交易状态");
                            PosConfig.getInstance().setMightPos1Paying(false);
                        } else if (dpl.getType().equals("-4")) {
                            LfLog.d(TAG, "POS1串口IO错误，设置POS1不在交易状态");
                            PosConfig.getInstance().setMightPos1Paying(false);
                        }
                    }
                    break;
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
                case OP_POS_PURCHASE: //签到返回
                    LfLog.d(TAG, "POS2---->OP_POS_PURCHASE:"
                            + ", dpl.getType::" + dpl.getType()
                            + ", dpl.getMsg:" + dpl.getMsg());
                    try {
                        if (PosConfig.getInstance().isMightPos2PlaySound()) {
                            if (dpl.getMsg().equals("POS支付操作,交易成功")) {
                                LfLog.d(TAG, "设置pos2不在交易状态1");
                                PosConfig.getInstance().setMightPos2Paying(false);
                                playSound(getApplication(), R.raw.success);
                                byte[] content = "交易成功".getBytes("gb2312");
                                int len = content.length;
                                lfLattice.showThirdContent(content, len);
                                showContent[0] = dpl.getMsg();
                                updateContentTV();
                            }
//                            else if (dpl.getMsg().contains("Z1交易超时")) {
//                                PosConfig.getInstance().setMightPos2Paying(false);
//                            } else if (dpl.getMsg().contains("XY交易人为取消")) {
//                                LfLog.d(TAG, "POS2交易人为取消，设置POS2不在交易状态");
//                                PosConfig.getInstance().setMightPos2Paying(false);
//                            } else if (dpl.getMsg().contains("XX交易异常")) {
//                                LfLog.d(TAG, "POS2XX交易异常，设置POS2不在交易状态");
//                                PosConfig.getInstance().setMightPos2Paying(false);
//                            } else if (dpl.getMsg().contains("X8数据接收有误")) {
//                                LfLog.d(TAG, "POS2X8数据接收有误，设置POS2不在交易状态");
//                                PosConfig.getInstance().setMightPos2Paying(false);
//                                playSound(getApplication(), R.raw.fail);
//                                try {
//                                    byte[] content = dpl.getMsg().getBytes("gb2312");
//                                    int len = content.length;
//                                    lfLattice.showThirdContent(content, len);
//                                    showContent[0] = dpl.getMsg();
//                                    updateContentTV();
//                                } catch (UnsupportedEncodingException e) {
//                                    e.printStackTrace();
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//                            }
                            else if (dpl.getMsg().contains("A0MAC校验错")) {
                                LfLog.d(TAG, "A0MAC校验错3");
                                LfLog.d(TAG, "POS2A0MAC校验错,请重新签到，设置POS2不在交易状态1");
                                PosConfig.getInstance().setMightPos2Paying(false);
                                PosConfig.getInstance().setMightPos2Signed(false);
                            }
                            else {
                                LfLog.d(TAG, "设置pos2不在交易状态2");
                                PosConfig.getInstance().setMightPos2Paying(false);
                                playSound(getApplication(), R.raw.fail);
                                byte[] content = "交易失败".getBytes("gb2312");
                                int len = content.length;
                                lfLattice.showThirdContent(content, len);
                                showContent[0] = dpl.getMsg();
                                updateContentTV();
                            }
                        }
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case OP_POS_DISPLAY://POS提示信息
                    if (cbmsg.reply == 0) {//成功
                        if (dpl.getType().endsWith(DisplayType._6.getType())) {
                            if (dpl.getMsg().contains("Z1交易超时")
                                    || dpl.getMsg().contains("XY交易人为取消")
                                    || dpl.getMsg().contains("XX交易异常")
                                    || dpl.getMsg().contains("X8数据接收有误")) {
                                LfLog.d(TAG, "POS2交易失败, " + dpl.getMsg() + " ,设置POS2不在交易状态");
                                PosConfig.getInstance().setMightPos2Paying(false);
                                if (PosConfig.getInstance().isMightPos2PlaySound()) {
                                    playSound(getApplication(), R.raw.fail);
                                    try {
                                        byte[] content = dpl.getMsg().getBytes("gb2312");
                                        int len = content.length;
                                        lfLattice.showThirdContent(content, len);
                                        showContent[0] = dpl.getMsg();
                                        updateContentTV();
                                    } catch (UnsupportedEncodingException e) {
                                        e.printStackTrace();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            } else if (dpl.getMsg().contains("A0MAC校验错")) {
                                LfLog.d(TAG, "A0MAC校验错4");
                                LfLog.d(TAG, "POS2A0MAC校验错,请重新签到，设置POS2不在交易状态2");
                                PosConfig.getInstance().setMightPos2Paying(false);
                                PosConfig.getInstance().setMightPos2Signed(false);
                                if (PosConfig.getInstance().isMightPos2PlaySound()) {
                                    playSound(getApplication(), R.raw.mac_error);
                                }
                            }
                        } else if (dpl.getType().endsWith(DisplayType._4.getType())) {//通讯信息提示
                            LfLog.d(TAG, "通讯信息提示" + ", dpl.getType::" + dpl.getType() + ", dpl.getMsg:" + dpl.getMsg());
                        } else if (dpl.getType().endsWith(DisplayType._2.getType())) {//读卡提示信息
                            LfLog.d(TAG, "请刷卡或刷手机" + ", dpl.getType::" + dpl.getType() + ", dpl.getMsg:" + dpl.getMsg());
                            PosConfig.getInstance().setMightPos2Paying(true);
                        } else if (dpl.getType().endsWith(DisplayType._h.getType())) {//读卡提示信息
                            LfLog.d(TAG, "设置POS2读到卡，POS2正在支付状态");
                            PosConfig.getInstance().setMightPos2ReadCard(true);
                            PosConfig.getInstance().setMightPos2Paying(true);
                        } else if (dpl.getType().equals("w")) {
                            try {
                                if (dpl.getMsg().equals("停车费用")) {
                                    byte[] content = dpl.getMsg().getBytes("gb2312");
                                    int len = content.length;
                                    lfLattice.showThirdContent(content, len);
                                    showContent[0] = dpl.getMsg();
                                    updateContentTV();
                                } else if (dpl.getMsg().equals("欢迎使用银联云闪付")) {
                                    PosConfig.getInstance().setMightPos1Paying(false);
                                    PosConfig.getInstance().setMightPos2Paying(false);
                                    byte[] content = dpl.getMsg().getBytes("gb2312");
                                    int len = content.length;
                                    lfLattice.showThirdContent(content, len);
                                    showContent[0] = dpl.getMsg();
                                    updateContentTV();
                                }

                                LfLog.d(TAG, "POS2提示信息wwwwwwwww" + ", dpl.getType::" + dpl.getType() + ", dpl.getMsg:" + dpl.getMsg());
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else if (dpl.getType().equals("x")) {
                            try {
                                LfLog.d(TAG, "POS2提示信息xxxxxxxxx" + ", dpl.getType::" + dpl.getType() + ", dpl.getMsg:" + dpl.getMsg());
                                byte[] content = dpl.getMsg().getBytes("gb2312");
                                int len = content.length;
                                lfLattice.showFourthContent(content, len);
                                showContent[0] = dpl.getMsg();
                                updateContentTV();
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else if (dpl.getType().equals("-3")) {
                            LfLog.d(TAG, "POS2串口收发超时，设置POS2不在交易状态");
                            PosConfig.getInstance().setMightPos2Paying(false);
                        } else if (dpl.getType().equals("-4")) {
                            LfLog.d(TAG, "POS2串口IO错误，设置POS2不在交易状态");
                            PosConfig.getInstance().setMightPos2Paying(false);
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    };

    private void pos1Test() {
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    pos1MyApi.setUseSynch(true);
                    E_REQ_RETURN ret = pos1MyApi.pos_conntest();
                    if (ret.getRst() == 0) {//请求成功
                        CallbackMsg callbackMsg = (CallbackMsg) ret.getObj();
                        REPLY mREPLY = (REPLY) ((CallbackMsg) ret.getObj()).mREPLY;
                        if (callbackMsg.reply == 0) {//执行成功
                            PosConfig.getInstance().setMightPos1HandOK(true);
                            showContent[0] = "POS1测试成功";
                            updateContentTV();
                            LfLog.d(TAG, "POS1测试成功");
                        } else {//执行失败
                            showContent[0] = "POS1测试失败";
                            updateContentTV();
                            LfLog.d(TAG, "POS1测试失败");
                            PosConfig.getInstance().setMightPos1HandOK(false);
                        }
                    } else {
                        LfLog.e(TAG, "POS1测试请求失败");
                        PosConfig.getInstance().setMightPos1HandOK(false);
                    }
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void pos2Test() {
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    pos2MyApi.setUseSynch(true);
                    E_REQ_RETURN ret = pos2MyApi.pos_conntest();
                    if (ret.getRst() == 0) {//请求成功
                        CallbackMsg callbackMsg = (CallbackMsg) ret.getObj();
                        REPLY mREPLY = (REPLY) ((CallbackMsg) ret.getObj()).mREPLY;
                        if (callbackMsg.reply == 0) {//执行成功
                            PosConfig.getInstance().setMightPos2HandOK(true);
                            showContent[0] = "POS2测试成功";
                            updateContentTV();
                            LfLog.d(TAG, "POS2测试成功");
                        } else {//执行失败
                            showContent[0] = "POS2测试失败";
                            updateContentTV();
                            LfLog.d(TAG, "POS2测试失败");
                            PosConfig.getInstance().setMightPos2HandOK(false);
                        }
                    } else {
                        LfLog.e(TAG, "POS2测试请求失败");
                        PosConfig.getInstance().setMightPos2HandOK(false);
                    }
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
