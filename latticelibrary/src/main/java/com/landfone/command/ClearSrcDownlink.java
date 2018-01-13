package com.landfone.command;

/**
 * Method: ClearSrcDownlink
 * Decription:
 * Author: raoj
 * Date: 2017/9/7
 **/
public class ClearSrcDownlink extends DownlinkBaseDeviceProtocolInitiative {

    public ClearSrcDownlink() {
        setCommandNumber(new byte[]{0x10});
    }

}
