package example.fussen.baselibrary.utils.secret;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class MCrypt {
    private String iv = "f3e00ec14c4cecba";//虚拟的 iv (需更改)
    private String SecretKey = "f3e00ec14c4cecba";//虚拟的 密钥 (需更改）


    public byte[] encrypt(String code) throws Exception {
        byte[] keyBytes = new byte[0];
        try {
            keyBytes = iv.getBytes("US-ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(keyBytes, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            byte[] encrypted = cipher.doFinal(code.getBytes("US-ASCII"));
            return encrypted;//new String(encrypted,"UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return keyBytes;
    }


    /**
     * 解密
     * @param code
     * @return
     * @throws Exception
     */
    public byte[] decrypt(String code) throws Exception {
        byte[] keyBytes = new byte[0];
        try {
            keyBytes = SecretKey.getBytes("US-ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(keyBytes, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] encrypted = cipher.doFinal(hexToBytes(code));
            return encrypted;//new String(encrypted,"UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return keyBytes;
    }


    /**
     * 加密
     * @param data
     * @return
     */
    public static String bytesToHex(byte[] data) {
        if (data == null) {
            return null;
        }

        int len = data.length;
        String str = "";
        for (int i = 0; i < len; i++) {
            if ((data[i] & 0xFF) < 16)
                str = str + "0" + Integer.toHexString(data[i] & 0xFF);
            else
                str = str + Integer.toHexString(data[i] & 0xFF);
        }
        return str;
    }


    public static byte[] hexToBytes(String str) {
        if (str == null) {
            return null;
        } else if (str.length() < 2) {
            return null;
        } else {
            int len = str.length() / 2;
            byte[] buffer = new byte[len];
            for (int i = 0; i < len; i++) {
                buffer[i] = (byte) Integer.parseInt(str.substring(i * 2, i * 2 + 2), 16);
            }
            return buffer;
        }
    }


    private static String padString(String source) {
        char paddingChar = ' ';
        int size = 16;
        int x = source.length() % size;
        int padLength = size - x;

        for (int i = 0; i < padLength; i++) {
            source += paddingChar;
        }

        return source;
    }

}
