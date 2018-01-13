package com.landfone.protocol;

/**
 * Method: Field
 * Decription:
 * Author: raoj
 * Date: 2017/9/5
 **/
public interface Field {
    //方法==========================================

    /**
     * 以字节数组的方式获得数据项的值。
     *
     * @return 数据项的值（字节数组的形式）。
     */
    byte[] getBytes();

    /**
     * 以byte型的方式获得数据项的值。
     *
     * @return 数据项的值（byte型的形式）。
     */
    byte getByteValue();

    /**
     * 以int型的方式获得数据项的值。
     *
     * @return 数据项的值（int型的形式）。
     */
    int getIntValue();

    /**
     * 以long型的方式获得数据项的值。
     *
     * @return 数据项的值（long型的形式）。
     */
    long getLongValue();

    /**
     * 以short型的方式获得数据项的值。
     *
     * @return 数据项的值（short型的形式）。
     */
    short getShortValue();

    /**
     * 以字符串的方式获得数据项的值。
     *
     * @return 数据项的值（String型的形式，采用系统缺省的编码）。
     */
    String getStringValue();

    /**
     * 以字节数组的方式设置数据项的值。
     *
     * @param bytes 数据项的值（字节数组的形式）。
     */
    void setBytes(byte[] bytes);

    void setByteValue(byte byteValue);

    /**
     * 以int型的方式设置数据项的值。
     *
     * @param intValue 数据项的值（int型的形式）。
     */
    void setIntValue(int intValue);

    /**
     * 以long型的方式设置数据项的值。
     *
     * @param longValue 数据项的值（long型的形式）。
     */
    void setLongValue(long longValue);

    /**
     * 以short型的方式设置数据项的值。
     *
     * @param shortValue 数据项的值（short型的形式）。
     */
    void setShortValue(short shortValue);

    /**
     * 以字符串的方式设置数据项的值。
     *
     * @param stringValue 数据项的值（String型的形式）。
     */
    void setStringValue(String stringValue);

    /**
     * 以BCD的方式设置数据项的值。
     *
     * @param stringValue
     */
    void setBcdValue(String stringValue);

    Field setEncoding(String string);
    //方法==========================================
}
