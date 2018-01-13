package com.landfone.protocol;

import com.landfone.utils.Converter;

import java.io.UnsupportedEncodingException;
import java.nio.BufferUnderflowException;

/**
 * Method: FieldImpl
 * Decription:
 * Author: raoj
 * Date: 2017/9/5
 **/
public class FieldImpl implements Field {

    // 属性=========================================
    private byte[] bytes;

    private String encoding = "GBK";

    // 属性=========================================

    // 构造器=========================================
    public FieldImpl() {
    }

    public FieldImpl(String stringValue, String encoding) {
        this.setStringValue(stringValue);
        this.setEncoding(encoding);
    }

    public FieldImpl(int intValue) {
        this.setIntValue(intValue);
    }

    public FieldImpl(long longValue) {
        this.setLongValue(longValue);
    }

    public FieldImpl(short shortValue) {
        this.setShortValue(shortValue);
    }

    public FieldImpl(String stringValue) {
        this.setStringValue(stringValue);
    }

    public FieldImpl(byte[] bytes) {
        this.setBytes(bytes);
    }

    public FieldImpl(byte byteValue) {
        this.setByteValue(byteValue);
    }

    // 构造器=========================================

    // 方法=========================================
    @Override
    public byte[] getBytes() {
        return this.bytes;
    }

    @Override
    public byte getByteValue() {
        if (this.bytes.length != 1) {
            throw new BufferUnderflowException();
        }
        return this.bytes[0];
    }

    /**
     * @return the encoding
     */
    public String getEncoding() {
        return encoding;
    }

    @Override
    public int getIntValue() {
        if (this.bytes.length != 4) {
            throw new BufferUnderflowException();
        }

        int tempValue = 0;
        for (int i = 0; i < this.bytes.length; i++) {
            tempValue <<= 8;
            tempValue |= this.bytes[i] & 0xff;
        }
        return tempValue;
    }

    @Override
    public long getLongValue() {
        if (this.bytes.length != 8) {
            throw new BufferUnderflowException();
        }

        long tempValue = 0;
        for (int i = 0; i < this.bytes.length; i++) {
            tempValue <<= 8;
            tempValue |= this.bytes[i] & 0xff;
        }
        return tempValue;
    }

    @Override
    public short getShortValue() {
        if (this.bytes.length != 2) {
            throw new BufferUnderflowException();
        }

        short tempValue = 0;
        for (int i = 0; i < this.bytes.length; i++) {
            tempValue <<= 8;
            tempValue |= this.bytes[i] & 0xff;
        }
        return tempValue;
    }

    @Override
    public String getStringValue() {
        try {
            return new String(this.bytes, this.getEncoding());
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    @Override
    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    @Override
    public void setByteValue(byte byteValue) {
        this.bytes = new byte[] { byteValue };
    }

    /**
     * @param encoding
     *            the encoding to set
     */
    public Field setEncoding(String encoding) {
        this.encoding = encoding;
        return this;
    }

    @Override
    public void setIntValue(int intValue) {
        long tempValue = intValue;
        int length = 4;
        this.bytes = new byte[length];
        for (int i = 0; i < length; i++) {
            this.bytes[length - 1 - i] = (byte) (tempValue & 0xFF);
            tempValue = tempValue >> 8;
        }
    }

    @Override
    public void setLongValue(long longValue) {
        long tempValue = longValue;
        int length = 8;
        this.bytes = new byte[length];
        for (int i = 0; i < length; i++) {
            this.bytes[length - 1 - i] = (byte) (tempValue & 0xFF);
            tempValue = tempValue >> 8;
        }
    }

    @Override
    public void setShortValue(short shortValue) {
        short tempValue = shortValue;
        int length = 2;
        this.bytes = new byte[length];
        for (int i = 0; i < length; i++) {
            this.bytes[length - 1 - i] = (byte) (tempValue & 0xFF);
            tempValue = (short) (tempValue >> 8);
        }
    }

    @Override
    public void setStringValue(String stringValue) {
        try {
            this.bytes = stringValue.getBytes(this.getEncoding());
        } catch (UnsupportedEncodingException e) {
            this.bytes = null;
        }
    }

    @Override
    public String toString() {
        return Converter.bytesToHexString(this.bytes);
    }

    @Override
    public void setBcdValue(String stringValue) {
        try {
            byte[] stringBytes = stringValue.getBytes(this.getEncoding());
            this.bytes = Converter.asciiToBCD(stringBytes);
        } catch (UnsupportedEncodingException e) {
            this.bytes = null;
        }

    }
}
