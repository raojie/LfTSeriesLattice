package com.landfone.common.utils;

import com.landfoneapi.mispos.Display;
import com.landfoneapi.protocol.pkg.REPLY;

/**
 * Created by yelz on 2016/8/15.
 */
public interface IUserCallback {

    void onResult(REPLY rst);

    void onProcess(Display dpl);

}
