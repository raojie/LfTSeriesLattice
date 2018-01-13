package com.landfoneapi.mispos;

import android.os.Message;

public interface ILfListener {
    void onCallback(Message msg);
}
