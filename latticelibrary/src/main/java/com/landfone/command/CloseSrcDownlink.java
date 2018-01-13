package com.landfone.command;

/**
 * Method: CloseSrcDownlink
 * Decription:
 * Author: raoj
 * Date: 2017/9/7
 **/
public class CloseSrcDownlink extends DownlinkBaseDeviceProtocolInitiative {

    public CloseSrcDownlink() {
        setCommandNumber(new byte[]{0x00,0x0f});
    }

}
