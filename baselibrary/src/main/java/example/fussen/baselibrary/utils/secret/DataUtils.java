package example.fussen.baselibrary.utils.secret;


public class DataUtils {

    /**
     * 整数转成byte数组
     */
    public static byte[] intToByteArray(int i) {
        byte[] result = new byte[4];
        result[0] = (byte) ((i >> 24) & 0xFF);
        result[1] = (byte) ((i >> 16) & 0xFF);
        result[2] = (byte) ((i >> 8) & 0xFF);
        result[3] = (byte) (i & 0xFF);
        return result;
    }

    /**
     * 短整型转到byte数组
     */
    public static byte[] shortToByteArray(short s) {
        byte[] shortBuf = new byte[2];
        shortBuf[0] = (byte) ((s >> 8) & 0xFF);
        shortBuf[1] = (byte) (s & 0xFF);

        return shortBuf;
    }

    /**
     * 将一个字节拆分高低位并转用字符串拼接
     */
    public static String byteToString(byte b) {
        byte a = (byte) ((b >> 4) & 0x0F);
        byte aa = (byte) (b & 0x0F);
        a = (byte) (a % 0xA);
        aa = (byte) (aa % 0xA);
        return String.valueOf(a) + String.valueOf(aa);
    }

    /**
     * 将一个整型拆分高低位并转用字符串拼接
     */
    public static String intToString(int i) {
        byte[] result = new byte[2];
        result[0] = (byte) ((i >> 8) & 0xFF);
        result[1] = (byte) (i & 0xFF);
        
        byte a = (byte) ((result[0] >> 4) & 0x0F);
        byte aa = (byte) (result[0] & 0x0F);
        String aaa =  String.valueOf(a).equals("0") ? String.valueOf(aa) : String.valueOf(a) + String.valueOf(aa);
        
        byte b = (byte) ((result[1] >> 4) & 0x0F);
        byte bb = (byte) (result[1] & 0x0F);
        String bbb =  String.valueOf(b) + String.valueOf(bb);
        return aaa + "." + bbb;
    }
    

    /**
     * byte转short,同时高低位互换
     */
    public static final int byteArrayToShort(byte[] b) {
        return (b[1] << 8) + (b[0] & 0xFF);
    }

    /**
     * byte[]转int 由高位到低位
     *
     * @param bytes
     * @return
     */
    public static int byteArrayToInt(byte[] bytes) {
        int value = 0;

        for (int i = 0; i < 4; i++) {
            int shift = (4 - 1 - i) * 8;
            value += (bytes[i] & 0x000000FF) << shift;//往高位游
        }
        return value;
    }

    /**
     * byte转int,同时高低位互换
     */
    public static final int byteArrayToIntH(byte[] b) {
        return (b[3] << 24) + (b[2] << 16) + (b[1] << 8) + (b[0] & 0xFF);
    }

    public static final int byteArrayToIntHto(byte[] b) {
        return (b[1] << 8) + (b[0] & 0xFF);
    }

    /**
     * 整数转byte, 同时，高低位互换
     */
    public static byte[] intLtoH(int n) {
        byte[] b = new byte[4];
        b[0] = (byte) (n & 0xff);
        b[1] = (byte) (n >> 8 & 0xff);
        b[2] = (byte) (n >> 16 & 0xff);
        b[3] = (byte) (n >> 24 & 0xff);
        return b;
    }


    /**
     * long转byte, 同时，高低位互换
     */
    public static byte[] longLtoByteH(long n) {
        byte[] b = new byte[4];
        b[0] = (byte) (n & 0xff);
        b[1] = (byte) (n >> 8 & 0xff);
        b[2] = (byte) (n >> 16 & 0xff);
        b[3] = (byte) (n >> 24 & 0xff);
        return b;
    }

    /**
     * long转byte, 同时，高低位互换
     */
    public static byte[] longLtoH(long n) {
        byte[] b = new byte[8];
        b[0] = (byte) (n & (long)0xff);
        b[1] = (byte) (n >> 8 & (long)0xff);
        b[2] = (byte) (n >> 16 & (long)0xff);
        b[3] = (byte) (n >> 24 & (long)0xff);
        b[4] = (byte) (n >> 32 & (long)0xff);
        b[5] = (byte) (n >> 40 & (long)0xff);
        b[6] = (byte) (n >> 48 & (long)0xff);
        b[7] = (byte) (n >> 56 & (long)0xff);
        return b;
    }

    /**
     * 将短整型高低位互换
     */
    public static byte[] shortLtoH(int n) {
        byte[] b = new byte[4];
        b[0] = (byte) (n & 0xff);
        b[1] = (byte) (n >> 8 & 0xff);
        return b;
    }

    /**
     * 单字节转无符号整数
     */
    public static int bytesToInt(byte b) {
        return ((int) b >= 0) ? (int) b : 256 + (int) b;
    }

    /**
     * 取得某一个data的第pos位的二进制位是0还是1
     *
     * @param data 要查的数据
     * @param pos  第几位
     * @return 0或1
     */
    public static int getBin(int data, int pos) {
        pos -= 1;
        if (pos < 0)
            return 0;
        if (((1 << pos) & data) == 0)
            return 0;
        else
            return 1;
    }
}
