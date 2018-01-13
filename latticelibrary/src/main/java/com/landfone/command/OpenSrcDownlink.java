package com.landfone.command;

import java.util.Arrays;

/**
 * Method: OpenSrcDownlink
 * Decription:
 * Author: raoj
 * Date: 2017/9/6
 **/
public class OpenSrcDownlink extends DownlinkBaseDeviceProtocolInitiative {

    public OpenSrcDownlink() {
        setCommandNumber(new byte[]{0x00,0x00});
    }

}
