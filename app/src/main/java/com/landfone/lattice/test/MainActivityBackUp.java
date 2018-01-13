package com.landfone.lattice.test;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.landfone.lattice.LfLattice;
import com.landfone.mis.bank.BankDAO;
import com.landfone.mis.bank.ICallBack;
import com.landfone.mis.bean.RequestPojo;
import com.landfone.mis.bean.ResponsePojo;
import com.landfone.mis.bean.TransCfx;
import com.landfoneapi.mispos.DisplayType;

import java.io.UnsupportedEncodingException;

public class MainActivityBackUp extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private LfLattice lfLattice;
    private Button clearSrcBtn;
    private Button openSrcBtn;
    private Button closeSrcBtn;
    private Button showcontentBtn;
    private Button firstShowBtn;
    private Button secondShowBtn;
    private Button thirdShowBtn;
    private Button fourthShowBtn;
    private Button pos1PayBtn;
    private Button pos1SignBtn;
    private Button posQueryBtn;
    private Button posCancelPayBtn;
    private Button posRefundBtn;
    private Button posSettleBtn;
    private Switch hardSwth;
    private Spinner spinnerComPort;
    private Spinner spinnerComPort2;
    private TextView showContentTextView;
    private TextView showContentTextView2;
    private EditText inputEditText;
    private EditText posIPEditText;
    private EditText posPortEditText;
    private EditText posIPEditText2;
    private EditText posPortEditText2;

    private String[] showContent;
    private String[] showContent2;
    private boolean isRunningThread = false;
    private boolean isRunningPOSPAY1Thread = false;
    private boolean isRunningPOSPAY2Thread = false;
    private TransCfx transCfx = new TransCfx();
    private BankDAO bankDAO = new BankDAO();
    private RequestPojo req = new RequestPojo();
    private ResponsePojo response;

    private boolean isSignPOS1 = false;
    private boolean isSignPOS2 = false;
    private boolean isPOS1PAYING = false;
    private boolean isPOS2PAYING = false;
    private BankDAO pos1BankDAO = new BankDAO();
    private BankDAO pos2BankDAO = new BankDAO();
    private RequestPojo pos1Req = new RequestPojo();
    private RequestPojo pos2Req = new RequestPojo();
    private ResponsePojo pos1Response;
    private ResponsePojo pos2Response;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lfLattice = new LfLattice();
        lfLattice.InitLfLattice("/dev/ttyS3","115200");
        initView();
        bindEvent();
        initialize();
//        new Handler().postDelayed(showWelcomeRunnable, 500);
//        new Handler().postDelayed(closeSrcRunnable, 1000);
//        new Handler().postDelayed(openSrcRunnable, 1000);
//        new Handler().postDelayed(signRunnable, 2000);
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
//                    new Thread(signRunnable).start();
                    break;
            }
        }
    };

    private Handler autoSignHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    signRunnable.run();
                    break;
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        // activity启动两秒钟后，发送一个message
        autoShowWelcome.sendEmptyMessageDelayed(1, 2000);
//        autoShowWelcome.sendEmptyMessageDelayed(2, 1000);
        autoSignHandler.sendEmptyMessageDelayed(1, 5000);
    }

    Runnable closeSrcRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                lfLattice.closeSrc();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

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
                byte[] content = "欢迎使用演示程序".getBytes("gb2312");
//                while (result) {
                    result = lfLattice.showPreviousContent(content, content.length);
//                    if (result) {
//                        break;
//                    }
//                }
                System.out.println("result:" + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    Runnable signRunnable = new Runnable() {
        @Override
        public void run() {
            sign();
        }
    };

    private void initView() {
        clearSrcBtn = (Button) findViewById(R.id.clearSrcBtn);
        openSrcBtn = (Button) findViewById(R.id.openSrcBtn);
        closeSrcBtn = (Button) findViewById(R.id.closeSrcBtn);
        showcontentBtn = (Button) findViewById(R.id.showcontentBtn);
        firstShowBtn = (Button) findViewById(R.id.firstShowBtn);
        secondShowBtn = (Button) findViewById(R.id.secondShowBtn);
        thirdShowBtn = (Button) findViewById(R.id.thirdShowBtn);
        fourthShowBtn = (Button) findViewById(R.id.fourthShowBtn);
        pos1PayBtn = (Button) findViewById(R.id.pos1PayBtn);
//        pos2PayBtn = (Button) findViewById(R.id.pos2PayBtn);
        pos1SignBtn = (Button) findViewById(R.id.pos1SignBtn);
//        pos2SignBtn = (Button) findViewById(R.id.pos2SignBtn);
        posQueryBtn = (Button) findViewById(R.id.posQueryBtn);
        posCancelPayBtn = (Button) findViewById(R.id.posCancelPayBtn);
        posRefundBtn = (Button) findViewById(R.id.posRefundBtn);
        posSettleBtn = (Button) findViewById(R.id.posSettleBtn);
        hardSwth = (Switch) findViewById(R.id.hardSwth);
        inputEditText = (EditText) findViewById(R.id.inputEditText);
        inputEditText.setInputType(InputType.TYPE_NULL);
        posIPEditText = (EditText) findViewById(R.id.posIPEditText);
        posPortEditText = (EditText) findViewById(R.id.posPortEditText);
        posIPEditText2 = (EditText) findViewById(R.id.posIPEditText2);
        posPortEditText2 = (EditText) findViewById(R.id.posPortEditText2);
        showContentTextView = (TextView) findViewById(R.id.showContentTextView);
        showContentTextView2 = (TextView) findViewById(R.id.showContentTextView2);
        spinnerComPort = (Spinner) findViewById(R.id.comPort);
        hardSwth.setOnCheckedChangeListener(mOnCheckedChangeListener);
    }

    private void iniContentShow() {
        String result = "";
        for (int i = 0; i < showContent.length; i++) {
            showContent[i] = "";
            result = result + (i + 1) + "." + showContent[i] + "\n";
            showContentTextView.setText(result);
        }
        for (int i = 0; i < showContent2.length; i++) {
            showContent2[i] = "";
            result = result + (i + 1) + "." + showContent2[i] + "\n";
            showContentTextView2.setText(result);
        }

    }

    private void initialize() {
        showContent = new String[5];
        showContent2 = new String[5];
        iniContentShow();
    }

    private void updateContentTV() {
        String result = "";
        for (int i = 0; i < showContent.length; i++) {
            result = result + showContent[i] + "\n";
        }
        updateUI(result);
    }

    private void updateContentTV2() {
        String result = "";
        for (int i = 0; i < showContent2.length; i++) {
            result = result + showContent2[i] + "\n";
        }
        updateUI2(result);
    }

    private void updateUI(final String string) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showContentTextView.setText(string);
            }
        });
    }

    private void updateUI2(final String string) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showContentTextView2.setText(string);
            }
        });
    }

    private void bindEvent() {
        pos1SignBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        signPOS1();
//                        signPOS2();
                    }
                }).start();
            }
        });

        pos1PayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consume1();
            }
        });

        posCancelPayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel1Consume();
            }
        });

        posQueryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRecord();
            }
        });

        posRefundBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refund();
            }
        });

        posSettleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settle();
            }
        });

        firstShowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    byte[] content = inputEditText.getText().toString().getBytes("gb2312");
                    lfLattice.showPreviousContent(content,content.length);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        secondShowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    byte[] content = "演示程序".getBytes("gb2312");
                    lfLattice.showSecondContent(content,content.length);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thirdShowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    byte[] content = "爱我中华".getBytes("gb2312");
                    lfLattice.showThirdContent(content,content.length);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        fourthShowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    byte[] content = "虽远必诛".getBytes("gb2312");
                    lfLattice.showFourthContent(content,content.length);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        clearSrcBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    lfLattice.clearSrc();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        openSrcBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    lfLattice.openSrc();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        closeSrcBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    lfLattice.closeSrc();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        showcontentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    byte[] content = inputEditText.getText().toString().getBytes("gb2312");
//                    System.out.println(Converter.bytesToHexString(content));
//                    lfLattice.showContent(content,content.length);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 签到
     */
    private void sign() {
        signPOS1();
//        while (true) {
//            if (isSignPOS1 && isSignPOS2) {
//                consume1();
//                consume1();
//            }
//        }
    }

    /**
     * 签到
     */
    private void signPOS1() {
        isRunningThread = false;
        System.out.println("signPOS1");
        if (isRunningThread) {
//            System.out.println("isRunningThread1:" + isRunningThread);
            showContent[0] = "POS1在进行签到！！！";
            System.out.println("POS1在进行签到");
            updateContentTV();
            return;
        }
        showContent[0] = "POS1 签到指令";
        updateContentTV();
        initBaseValue();
        new Thread(new Runnable() {
            @Override
            public void run() {
//                isRunningThread = true;
                bankDAO.getCallBack(new BackCall());
                req.setTransType("05");
                req.setTransMemo("01");
                response = bankDAO.bankall(transCfx, req);
                System.out.println("signPOS1-response1:" + response.toString());
                if (response.getRspCode().equals("00")) {
                    System.out.println("signPOS1-response2.getRspCode:" + response.getRspCode());
                    isRunningThread = false;
                    isSignPOS1 = true;
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    signPOS2();
                } else {
                    signPOS1();
//                    bankDAO.BankCancel();
//                    isSignPOS1 = false;
                    System.out.println("signPOS1-response3.getRspCode:" + response.getRspCode());
                    return;
                }
                showContent[0] = response.toString();
                updateContentTV();
//                isRunningThread = false;
            }
        }).start();
    }

    /**
     * 签到
     */
    private void signPOS2() {
        isRunningThread = false;
        System.out.println("signPOS2");
        if (isRunningThread) {
            showContent[0] = "POS2在进行签到！！！";
            System.out.println("POS2在进行签到");
            updateContentTV();
            return;
        }
        showContent[0] = "POS2 签到指令";
        updateContentTV();
        initBaseValue2();
        new Thread(new Runnable() {
            @Override
            public void run() {
//                isRunningThread = true;
                bankDAO.getCallBack(new BackCall());
                req.setTransType("05");
                req.setTransMemo("01");
                response = bankDAO.bankall(transCfx, req);
                System.out.println("signPOS2-response:" + response.toString());
                if (response.getRspCode().equals("00")) {
                    isRunningThread = false;
                    isSignPOS2 = true;
                    System.out.println("isSignPOS1:" + isSignPOS1 + " ,isSignPOS2:" + isSignPOS2);
                    if (isSignPOS1 && isSignPOS2) {
                        while (true) {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    consume1();
                                    consume2();
                                }
                            }).start();
//                            new Thread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    consume2();
//                                }
//                            }).start();
                            consume1();
                            consume2();
                        }
                    }
                } else {
                    signPOS2();
//                    bankDAO.BankCancel();
//                    isSignPOS1 = false;
                    System.out.println("signPOS2-response3.getRspCode:" + response.getRspCode());
                    return;
                }
                showContent[0] = response.toString();
                updateContentTV();
//                isRunningThread = false;
            }
        }).start();
    }

    /**
     * 消费指令
     */
    private void consume1() {
//        if (isRunningPOSPAY1Thread) {
//            showContent[0] = "已经有交易在进行！！！";
//            updateContentTV();
//            return;
//        }
        showContent[0] = "POS1 消费指令";
        updateContentTV();
        new Thread(new Runnable() {
            @Override
            public void run() {
                isRunningPOSPAY1Thread = true;
                pos1BankDAO.getCallBack(new POSPAY1BackCall());
                pos1Req.setTransType("00");//交易类型
                pos1Req.setTransMemo("01");//交易附加域
                pos1Req.setAmount(1);
                pos1Response = pos1BankDAO.bankall(transCfx, pos1Req);
                if (pos1Response.getRspCode().equals("00")) {
                    isRunningPOSPAY1Thread = false;
                }
                showContent[0] = pos1Response.toString();
                updateContentTV();
                isRunningPOSPAY1Thread = false;
            }
        }).start();
    }

    /**
     * 消费指令
     */
    private void consume2() {
//        if (isRunningPOSPAY2Thread) {
//            showContent[0] = "已经有交易在进行！！！";
//            updateContentTV();
//            return;
//        }
        showContent[0] = "POS2 消费指令";
        updateContentTV();
        new Thread(new Runnable() {
            @Override
            public void run() {
                isRunningPOSPAY2Thread = true;
                pos2BankDAO.getCallBack(new POSPAY2BackCall());
                pos2Req.setTransType("00");//交易类型
                pos2Req.setTransMemo("01");//交易附加域
                pos2Req.setAmount(1);
                pos2Response = pos2BankDAO.bankall(transCfx, pos2Req);
                if (pos2Response.getRspCode().equals("00")) {
                    isRunningPOSPAY2Thread = false;
                }
                showContent[0] = pos2Response.toString();
                updateContentTV();
                isRunningPOSPAY2Thread = false;
            }
        }).start();
    }

    /**
     * 取消消费
     */
    private void cancel1Consume() {
        showContent[0] = "POS 取消消费指令";
        updateContentTV();
        int iRet = bankDAO.BankCancel();
        if (iRet == 0) {
            isRunningThread = false;
            Toast.makeText(getApplicationContext(), "取消成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "取消失败，不允许取消", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 取消消费
     */
    private void cancel2Consume() {
        showContent[0] = "POS 取消消费指令";
        updateContentTV();
        int iRet = bankDAO.BankCancel();
        if (iRet == 0) {
            isRunningThread = false;
            Toast.makeText(getApplicationContext(), "取消成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "取消失败，不允许取消", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 结算
     */
    private void settle() {
        if (isRunningThread) {
            showContent[0] = "已经有交易在进行！！！";
            updateContentTV();
            return;
        }
        showContent[0] = "POS 结算指令";
        updateContentTV();
        new Thread(new Runnable() {
            @Override
            public void run() {
                isRunningThread = true;
                bankDAO.getCallBack(new BackCall());
                req.setTransType("04");
                req.setTransMemo("01");
                response = bankDAO.bankall(transCfx, req);
                showContent[0] = response.toString();
                updateContentTV();
                isRunningThread = false;
            }
        }).start();
    }

    /**
     * 查询交易
     */
    private void getRecord() {
        if (isRunningThread) {
            showContent[0] = "已经有交易在进行！！！";
            updateContentTV();
            return;
        }
        showContent[0] = "POS 查询交易信息";
        updateContentTV();
        new Thread(new Runnable() {
            @Override
            public void run() {
                isRunningThread = true;
                bankDAO.getCallBack(new BackCall());
                req.setTransType("69");
                req.setTransMemo("01&000000");
                response = bankDAO.bankall(transCfx, req);
                showContent[0] = response.toString();
                updateContentTV();
                isRunningThread = false;
            }
        }).start();
    }

    /**
     * 退款指令
     */
    private void refund() {
        if (isRunningThread) {
            showContent[0] = "已经有交易在进行！！！";
            updateContentTV();
            return;
        }
        showContent[0] = "POS 退款指令";
        updateContentTV();
        new Thread(new Runnable() {
            @Override
            public void run() {
                isRunningThread = true;
                bankDAO.getCallBack(new BackCall());
                req.setTransType("01");//交易类型
                req.setTransMemo("01&" + transCfx.getMerId() + "&" + transCfx.getTmnId() + "&&      ");//交易类型&商户号&终端号&卡号&交易凭证号(六个空格表示上撤销一次交易)
                req.setAmount(1);
                response = bankDAO.bankall(transCfx, req);
                showContent[0] = response.toString();
                updateContentTV();
                isRunningThread = false;
            }
        }).start();
    }

    private class BackCall implements ICallBack {
        @Override
        public void getCallBack(String stateCode, String stateTips) {
            try {
                if (stateCode.equals(DisplayType._h.getType())) {
                    stateTips = "请移开手机或银行卡";
                } else if (stateCode.equals(DisplayType._d.getType())) {
                    stateTips = "二维码URL:" + stateTips;
                } else if (stateCode.equals("w")) {
                    byte[] content = stateTips.getBytes("gb2312");
                    int len = content.length;
                    lfLattice.showThirdContent(content, len);
                    showContent[0] = stateTips;
                    updateContentTV();
                } else if (stateCode.equals("x")) {
                    byte[] content = stateTips.getBytes("gb2312");
                    int len = content.length;
                    lfLattice.showFourthContent(content, len);
                    showContent[3] = stateTips;
                    updateContentTV();
                } else if(stateCode.equals("06")) {
                    return;
                }
//                else if (stateCode.equals("00")) {
//                    Log.d(TAG, "=-=-=stateCode=" + stateCode);
//                    signPOS2();
//                }
                Log.d(TAG, "stateCode=" + stateCode + "|" + "stateTips=" + stateTips);
                showContent[0] = " stateCode: " + stateCode + " | " + stateTips;
                updateContentTV();
                if ("01".equals(stateCode) || "03".equals(stateCode)) {
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class POSPAY1BackCall implements ICallBack {
        @Override
        public void getCallBack(String stateCode, String stateTips) {
            try {
                if (stateCode.equals(DisplayType._h.getType())) {
                    stateTips = "请移开手机或银行卡";
                    isPOS1PAYING = true;
                    pos2BankDAO.BankCancel();
                } else if (stateCode.equals(DisplayType._d.getType())) {
                    stateTips = "二维码URL:" + stateTips;
                } else if (stateCode.equals("w")) {
                    byte[] content = stateTips.getBytes("gb2312");
                    int len = content.length;
                    lfLattice.showThirdContent(content, len);
                    showContent[0] = stateTips;
                    updateContentTV();
                } else if (stateCode.equals("x")) {
                    byte[] content = stateTips.getBytes("gb2312");
                    int len = content.length;
                    lfLattice.showFourthContent(content, len);
                    showContent[3] = stateTips;
                    updateContentTV();
                } else if(stateCode.equals("06")) {
                    return;
                }
//                else if (stateCode.equals("00")) {
//                    Log.d(TAG, "=-=-=stateCode=" + stateCode);
//                    signPOS2();
//                }
                Log.d(TAG, "stateCode=" + stateCode + "|" + "stateTips=" + stateTips);
                showContent[0] = " stateCode: " + stateCode + " | " + stateTips;
                updateContentTV();
                if ("01".equals(stateCode) || "03".equals(stateCode)) {
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class POSPAY2BackCall implements ICallBack {
        @Override
        public void getCallBack(String stateCode, String stateTips) {
            try {
                if (stateCode.equals(DisplayType._h.getType())) {
                    stateTips = "请移开手机或银行卡";
                    isPOS2PAYING = true;
                    pos1BankDAO.BankCancel();
                } else if (stateCode.equals(DisplayType._d.getType())) {
                    stateTips = "二维码URL:" + stateTips;
                } else if (stateCode.equals("w")) {
                    byte[] content = stateTips.getBytes("gb2312");
                    int len = content.length;
                    lfLattice.showThirdContent(content, len);
                    showContent[0] = stateTips;
                    updateContentTV();
                } else if (stateCode.equals("x")) {
                    byte[] content = stateTips.getBytes("gb2312");
                    int len = content.length;
                    lfLattice.showFourthContent(content, len);
                    showContent[3] = stateTips;
                    updateContentTV();
                } else if(stateCode.equals("06")) {
                    return;
                }
//                else if (stateCode.equals("00")) {
//                    Log.d(TAG, "=-=-=stateCode=" + stateCode);
//                    signPOS2();
//                }
                Log.d(TAG, "stateCode=" + stateCode + "|" + "stateTips=" + stateTips);
                showContent[0] = " stateCode: " + stateCode + " | " + stateTips;
                updateContentTV();
                if ("01".equals(stateCode) || "03".equals(stateCode)) {
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void initBaseValue() {
        transCfx.setHard_ip("122.226.116.137");//硬加密走的主机ip地址
        transCfx.setHard_port(10003);//硬加密走的主机端口号
        transCfx.setHard_ip2(posIPEditText.getText().toString().trim());//硬加密走的主机ip地址
        transCfx.setHard_port2(10004);//硬加密走的主机端口号

        transCfx.setCom_port("/dev/ttyS1");//串口值
        transCfx.setBaudRate("9600");//波特率
        transCfx.setMerId("000000000000000");//商户号
        transCfx.setTmnId("00000000");//终端号
        transCfx.setContext(getApplicationContext());
        transCfx.setHard_on(0);
        transCfx.setSsl_cert("umscert.pem");//证书文件保存在assets文件夹中，参数填文件名
    }

    private void initBaseValue2() {
        transCfx.setHard_ip("122.226.116.137");//硬加密走的主机ip地址
        transCfx.setHard_port(10003);//硬加密走的主机端口号
        transCfx.setHard_ip2("122.226.116.137");//硬加密走的主机ip地址
        transCfx.setHard_port2(10004);//硬加密走的主机端口号

        transCfx.setCom_port("/dev/ttyS2");//串口值
        transCfx.setBaudRate("9600");//波特率
        transCfx.setMerId("000000000000000");//商户号
        transCfx.setTmnId("00000000");//终端号
        transCfx.setContext(getApplicationContext());
        transCfx.setHard_on(0);
        transCfx.setSsl_cert("umscert.pem");//证书文件保存在assets文件夹中，参数填文件名
    }

    private CompoundButton.OnCheckedChangeListener mOnCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (buttonView == hardSwth) {
                showContent[0] = "POS setHardEncrypt";
                updateContentTV();
                transCfx.setContext(getApplicationContext());
                transCfx.setHard_on(isChecked ? 0 : 1);
                transCfx.setSsl_cert("umscert.pem");//证书文件保存在assets文件夹中，参数填文件名
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            lfLattice.closeSrc();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
