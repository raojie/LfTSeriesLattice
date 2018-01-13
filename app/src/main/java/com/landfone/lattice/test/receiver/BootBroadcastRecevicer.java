package com.landfone.lattice.test.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.landfone.lattice.test.InitActivity;

/**
 * 开机自启广播
 * Created by raoj on 2017/7/11.
 */

public class BootBroadcastRecevicer extends BroadcastReceiver {
    public static final String action_boot = "android.intent.action.BOOT_COMPLETED";

    @Override
    public void onReceive(Context context, Intent intent) {
//        if (intent.getAction().equals(action_boot)) { // boot
//             /* 服务开机自启动 */
//            Intent service = new Intent(context, POSService.class);
//            context.startService(service);
//        }
        if (intent.getAction().equals(action_boot)) { // boot
            Log.d("AUTOSTART", "RECV ACTION:" + action_boot);
            Intent intent2 = new Intent(context, InitActivity.class);
            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent2);



//            final Intent service = new Intent();
//            service.setComponent( new ComponentName("com.landfone.lattice.tes", "landfone.pos2service.POSService"));
//            context.startService(service);
//            context.startService(new Intent("landfone.282pos.service"));
        } else {
            Log.d("AUTOSTART", "RECV ACTION:" + intent.getAction());
        }

    }
}
