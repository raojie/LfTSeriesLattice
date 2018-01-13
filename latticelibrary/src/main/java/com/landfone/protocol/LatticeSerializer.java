//package com.landfone.protocol;
//
//import android.util.Log;
//
//import com.landfone.utils.Converter;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.nio.BufferUnderflowException;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.TreeMap;
//
///**
// * Method: LatticeSerializer
// * Decription:
// * Author: raoj
// * Date: 2017/9/5
// **/
//public class LatticeSerializer {
//    // 方法===========================================================================
//    public static String format(HashMap<Integer, Field> message) {
//        StringBuilder sb = new StringBuilder();
//        // 从包对象中取出各域。
//        TreeMap<Integer, Field> treeMessage = new TreeMap<Integer, Field>(
//                message);
//
//        sb.append(System.getProperty("line.separator"));
//        for (Iterator<Map.Entry<Integer, Field>> iter = treeMessage.entrySet()
//                .iterator(); iter.hasNext(); ) {
//            Map.Entry<Integer, Field> entry = iter.next();
//            Integer tag = entry.getKey().intValue();
//            Field field = entry.getValue();
//            String value = "";
////            if (field != null) {
////                value = field.toString();
////                if (shouldEncrypt(tag)) {
////                    String ciphertext = Converter.bytesToHexString(Des.encrypt(
////                            field.getBytes(), Constant.array.LogKey));
////                    value = "[" + ciphertext + "]";
////                }
////            }
//
//            sb.append(String.format("[%1$04X] %2$s", tag, value));
//            sb.append(System.getProperty("line.separator"));
//        }
//        return sb.toString();
//    }
//
//    public static HashMap<Integer, Field> deserialize(byte[] data) {
//        ByteArrayInputStream stream = new ByteArrayInputStream(data);
//        HashMap<Integer, Field> tlvMessage = new HashMap<Integer, Field>();
//
//        // 循环读取每个域。
//        while (stream.available() > 0) {
//            // 与外设通信的TLV数据项接口。
//            Field field = new FieldImpl();
//
//            // 取TAG。
//            int tagHigh = stream.read();
//            int tagLow = stream.read();
//            if (tagHigh == -1 || tagLow == -1) {
//                throw new BufferUnderflowException();
//            }
//            int tag = (int) (tagHigh << 8 | tagLow);
//
//            // 取LENGTH。
//            int lengthHigh = stream.read();
//            int lengthLow = stream.read();
//            if (lengthHigh == -1 || lengthLow == -1) {
//                throw new BufferUnderflowException();
//            }
//            int length = lengthHigh << 8 | lengthLow;
//            if (length > 0xFFFF || length < 0) {
//                throw new BufferUnderflowException();
//            }
//
//            // 取VALUE。
//            byte[] value = new byte[length];
//            int actualReadCount;
//            try {
//                actualReadCount = stream.read(value);
//            } catch (IOException e) {
//                throw new IllegalStateException(e.getMessage(), e);
//            }
//
//            if (actualReadCount >= 0 && actualReadCount != length) {
//                throw new BufferUnderflowException();
//            }
//            field.setBytes(value);
//
//            // 放入包对象中。
//            tlvMessage.put(new Integer(tag), field);
//        }
//        return tlvMessage;
//    }
//
//    public static byte[] serialize(HashMap<Integer, Field> message)
//            throws IOException {
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        List<Map.Entry<Integer, Field>> arrayList = new ArrayList<Map.Entry<Integer, Field>>(
//                message.entrySet());
//        Collections.sort(arrayList,
//                new Comparator<Map.Entry<Integer, Field>>() {
//                    public int compare(Map.Entry<Integer, Field> o1,
//                                       Map.Entry<Integer, Field> o2) {
//                        return (o1.getKey()).compareTo(o2.getKey());
//                    }
//                });
//
//        // 从包对象中取出各域。
//        for (Iterator<Map.Entry<Integer, Field>> iter = arrayList.iterator(); iter
//                .hasNext(); ) {
//            Map.Entry<Integer, Field> entry = iter.next();
//            Integer tag = entry.getKey().intValue();
//            Field field = entry.getValue();
//
//            // 生成TAG。
//            byte tagHigh = (byte) (tag / 256);
//            byte tagLow = (byte) (tag % 256);
//            System.out.println("tagHigh:" + Converter.byteToHexString(tagHigh));
//            System.out.println("tagLow:" + Converter.byteToHexString(tagLow));
//            stream.write(tagHigh);
//            stream.write(tagLow);
//
//            // 生成LENGTH。
//            if (field != null) {
//                byte lengthHigh = (byte) (field.getBytes().length / 256);
//                byte lengthLow = (byte) (field.getBytes().length % 256);
//                System.out.println("lengthHigh:" + Converter.byteToHexString(lengthHigh));
//                System.out.println("lengthLow:" + Converter.byteToHexString(lengthLow));
//                stream.write(lengthHigh);
//                stream.write(lengthLow);
//
//                System.out.println("field:" + Converter.bytesToHexString(field.getBytes()));
//                // 写入VALUE。
//                stream.write(field.getBytes());
//            } else {
//                stream.write(0x00);
//                stream.write(0x00);
//            }
//
//        }
//
//        byte[] bytes = stream.toByteArray();
//        return bytes;
//    }
//}
