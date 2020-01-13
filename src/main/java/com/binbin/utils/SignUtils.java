package com.binbin.utils;


import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class SignUtils {
    private static final String[] hexStrings;

    static {
        hexStrings = new String[256];
        for (int i = 0; i < 256; i++) {
            StringBuilder d = new StringBuilder(2);
            char ch = Character.forDigit(((byte) i >> 4) & 0x0F, 16);
            d.append(Character.toUpperCase(ch));
            ch = Character.forDigit((byte) i & 0x0F, 16);
            d.append(Character.toUpperCase(ch));
            hexStrings[i] = d.toString();
        }
    }
    private static String[] IGNORE = new String[]{
           /* "sign_type",*/ "sign_data", "encrypt_type", "encrypt_data","salt"
    };
    private static String[] XJK_IGNORE = new String[]{
           /* "sign_type", */"sign_data"
    };
    /**
     * 计算签名
     * @param map 有key和value的map，使用=和&拼接所有参数，
     *            "sign_type", "sign_data", "encrypt_type", "encrypt_data"不参加计算
     * @param algorithm 签名算法 MD5, SHA-1, SHA-256
     * @param salt 签名密钥
     * @param charset 字符串编码
     * @return 签名
     */
    public static String sign(Map<String, String> map, String algorithm, String salt, String charset) throws UnsupportedEncodingException {
        String linkString = map2LinkString(map);
        String data = linkString + salt;
       // System.out.println("signData = " + data);
        return digestHex(algorithm, data, charset);
    }
    /**
     * 计算签名
     * @param map 有key和value的map，使用=和&拼接所有参数，
     *            "sign_type", "sign_data", "encrypt_type", "encrypt_data"不参加计算
     * @param algorithm 签名算法 MD5, SHA-1, SHA-256
     * @param salt 签名密钥
     * @param charset 字符串编码
     * @return 签名
     */
    public static String sign(Map<String, String> map, String algorithm, String salt, String charset,String type) throws UnsupportedEncodingException {
        String linkString = map2LinkString(map,type);
        String data = linkString + salt;
        System.out.println("signData = " + data);
        return digestHex(algorithm, data, charset);
    }
    /**
     * 将MAP数据用=和&拼接成String
     * @param map 数据
     * @return 字符串
     */
    public static String map2LinkString(Map<String, String> map,String type) {
        ArrayList<String> mapKeys = new ArrayList<String>(map.keySet());
        Collections.sort(mapKeys);
        StringBuilder link = new StringBuilder();
        boolean first = true;
        for_map_keys:
        for(String key: mapKeys) {
            String value = map.get(key);
            if (value==null || "".equals(value.trim())) continue;
            if("XJK".equals(type)){
                for(String i: XJK_IGNORE) {
                    if (i.equalsIgnoreCase(key)) continue for_map_keys;
                }
            }else if("NPP2".equals(type)){
                for(String i: IGNORE) {
                    if (i.equalsIgnoreCase(key)) continue for_map_keys;
                }
            }

            if (!first) link.append("&");
            link.append(key).append("=").append(value);
            if (first) first = false;
        }
        return link.toString();
    }
    /**
     * 验证签名正确性。
     * @param sign 签名数据
     * @param map 数据
     * @param algorithm 签名算法 MD5, SHA-1, SHA-256
     * @param salt 签名密钥
     * @param charset 字符串
     * @return 验证结果
     */
    public static boolean verify(String sign, Map<String, String> map, String algorithm, String salt,
                                 String charset) throws UnsupportedEncodingException {
        if (sign==null || "".equals(sign.trim()) || map.size()==0) return false;
        String newSign = sign(map, algorithm, salt, charset);
        System.out.println(newSign);
        return newSign.equals(sign);
    }

    /**
     * 将MAP数据用=和&拼接成String
     * @param map 数据
     * @return 字符串
     */
    public static String map2LinkString(Map<String, String> map) {
        ArrayList<String> mapKeys = new ArrayList<String>(map.keySet());
        Collections.sort(mapKeys);
        StringBuilder link = new StringBuilder();
        boolean first = true;
        for_map_keys:
        for(String key: mapKeys) {
            String value = map.get(key);
            if (value==null || "".equals(value.trim())) continue;
            for(String i: IGNORE) {
                if (i.equalsIgnoreCase(key)) continue for_map_keys;
            }
            if (!first) link.append("&");
            link.append(key).append("=").append(value);
            if (first) first = false;
        }
        return link.toString();
    }

    /**
     * 对数据进行指定算法的数据摘要
     *
     * @param algorithm 算法名，如MD2, MD5, SHA-1, SHA-256, SHA-512
     * @param data      待计算的数据
     * @param charset   字符串的编码
     * @return 摘要结果
     */
    public static String digestHex(String algorithm, String data, String charset) throws UnsupportedEncodingException {
        //byte[] digest = DigestUtils.getDigest(algorithm).digest(data.getBytes(charset));
    	byte[] digest = getDigest(algorithm).digest(data.getBytes(charset));
        return hexString(digest);
    }

    /**
     * 将字节数组转换成HEX String
     *
     * @param b
     * @return HEX String
     */
    public static String hexString(byte[] b) {
        StringBuilder d = new StringBuilder(b.length * 2);
        for (byte aB : b) {
            d.append(hexStrings[(int) aB & 0xFF]);
        }
        return d.toString();
    }
    
    public static MessageDigest getDigest(final String algorithm) {
        try {
            return MessageDigest.getInstance(algorithm);
        } catch (final NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("key4", "bb");
        map.put("key1", "中文值1");
        map.put("key2", null);
        map.put("key3", "0p");
        map.put("encrypt_type", "yyy");
        map.put("sign_type", "xxx");
        map.put("aa", "value2");
        map.put("_input_charset", "value2");
        String link = map2LinkString(map);
        System.out.println("link = " + link);
        String sign = sign(map, "SHA-256", "test", "utf8");
        System.out.println("sign = " + sign);
        boolean verify = verify(sign, map, "SHA-256", "test", "utf8");
        System.out.println("verify = " + verify);
    }
}
