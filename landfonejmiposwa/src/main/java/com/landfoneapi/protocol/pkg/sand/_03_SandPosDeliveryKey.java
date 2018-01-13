package com.landfoneapi.protocol.pkg.sand;

import com.landfone.common.utils.LfException;
import com.landfone.common.utils.LfUtils;
import com.landfoneapi.protocol.pkg.TradeType;
import com.landfoneapi.protocol.pkg._03_Common;

/**
 * Created by asus on 2017/5/2.
 */

public class _03_SandPosDeliveryKey extends _03_Common {
    private String ID = "";
    private String index = "";

    public _03_SandPosDeliveryKey() {
        this.setTradeType(TradeType.SAND_DELIVERY_KEY);
    }

    public void reset() {
        super.reset();
        this.setTradeType(TradeType.SAND_DELIVERY_KEY);
    }

    public byte[] getBytes() {
        int i = 0;
        byte[] retbb = null;
        byte[] tmpbb = new byte[128 * 4];

        i += LfUtils.memcpy(tmpbb, i, this.getTradeType().getCode().getBytes(), 0, this.getTradeType().getCode().getBytes().length);
        i += LfUtils.memcpy(tmpbb, i, FS, 0, FS.length);
        //商户ID
        i += LfUtils.memcpy(tmpbb, i, ID.getBytes(), 0, ID.getBytes().length);
        i += LfUtils.memcpy(tmpbb, i, FS, 0, FS.length);
        //秘钥索引
        i += LfUtils.memcpy(tmpbb, i, index.getBytes(), 0, index.getBytes().length);
        i += LfUtils.memcpy(tmpbb, i, FS, 0, FS.length);

        if (i > 0 && i < tmpbb.length) {
            retbb = new byte[i];
            LfUtils.memcpy(retbb, 0, tmpbb, 0, i);
        }
        return retbb;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) throws LfException {
        if (ID.getBytes().length != 1) {
            throw new LfException(-8, "ID length error, need = 1");
        }
        this.ID = ID;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) throws LfException {
        if (index.getBytes().length != 1) {
            throw new LfException(-8, "index length error, need = 1");
        }
        this.index = index;
    }
}
