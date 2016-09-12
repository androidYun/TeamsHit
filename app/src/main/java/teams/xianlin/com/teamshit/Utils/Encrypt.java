package teams.xianlin.com.teamshit.Utils;

import java.security.MessageDigest;
import java.util.BitSet;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.SecretKeySpec;

import android.util.Log;

import teams.xianlin.com.teamshit.BaseInfor.Constant;

public class Encrypt {
    private static String TAG = "Encrypt";
    static String base64Tab = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
    public static final byte[] Key = "YHXT1SADF3S3HNHX".getBytes();// des秘钥
    private static final String Algorithm = "DES";

    public static String MD5(String s) {
        try {
            return Util.byte2HexString
                    (MessageDigest.getInstance("MD5").digest(
                            s.getBytes(Constant.ENCODING)));
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage(), ex);
            return null;
        }
    }

    public static String MD5(byte[] s) {
        try {
            return Util.byte2HexString(MessageDigest.getInstance("MD5").digest(
                    s));
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage(), ex);
            return null;
        }
    }

    public static void setDes3Key(String str) {
        try {
            byte[] abyte1 = str.getBytes(Constant.ENCODING);
            byte[] abyte0 = new byte[24];
            System.arraycopy(abyte1, 0, abyte0, 0,
                    Math.min(abyte1.length, abyte0.length));
            new DESedeKeySpec(abyte0);
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage(), ex);
        }
    }

    /**
     * ECB加密,不要IV
     *
     * @param data ：明文
     * @return Base64编码的密文：
     */

    public static byte[] des3EncodeECB(byte[] data) {
        return data;
    }

    /**
     * ECB解密,不要IV
     *
     * @param data ：Base64编码的密文
     * @return 明文
     */

    public static byte[] des3DecodeECB(byte[] data) {
        return data;
    }

    public static byte[] baseEncoding(byte[] sc) {
        int srcLength;

        srcLength = sc.length;

        if (srcLength > 3 || srcLength < 1)
            return null;

        byte rc[];

        byte t1, t2, t3, t4, t5, t6;
        byte tt1, tt2, tt3, tt4, tt5, tt6;

        byte b1 = (byte) 0xFC;
        byte b2 = (byte) 0x03;
        byte b3 = (byte) 0xF0;
        byte b4 = (byte) 0x0F;
        byte b5 = (byte) 0xC0;
        byte b6 = (byte) 0x3F;

        rc = new byte[srcLength + 1];

        switch (srcLength) {
            case 1:
                t1 = (byte) (sc[0] & b1);
                t2 = (byte) (sc[0] & b2);
                tt1 = bitMove(t1, 2);
                tt2 = (byte) (t2 << 4);
                rc[0] = tt1;
                rc[1] = tt2;
                break;

            case 2:
                t1 = (byte) (sc[0] & b1);
                t2 = (byte) (sc[0] & b2);
                t3 = (byte) (sc[1] & b3);
                t4 = (byte) (sc[1] & b4);
                tt1 = bitMove(t1, 2);
                tt2 = (byte) (t2 << 4);
                tt3 = bitMove(t3, 4);
                tt4 = (byte) (t4 << 2);
                rc[0] = tt1;
                rc[1] = (byte) (tt2 | tt3);
                rc[2] = tt4;
                break;

            case 3:
                t1 = (byte) (sc[0] & b1);
                t2 = (byte) (sc[0] & b2);
                t3 = (byte) (sc[1] & b3);
                t4 = (byte) (sc[1] & b4);
                t5 = (byte) (sc[2] & b5);
                t6 = (byte) (sc[2] & b6);
                tt1 = bitMove(t1, 2);
                tt2 = (byte) (t2 << 4);
                tt3 = bitMove(t3, 4);
                tt4 = (byte) (t4 << 2);
                tt5 = bitMove(t5, 6);
                tt6 = (byte) (t6);
                rc[0] = tt1;
                rc[1] = (byte) (tt2 | tt3);
                rc[2] = (byte) (tt4 | tt5);
                rc[3] = tt6;
                break;
        }
        return rc;
    }

    public static byte[] baseDecoding(byte[] sc) {
        int srcLength;

        srcLength = sc.length;

        if (srcLength > 4 || srcLength < 2)
            return null;

        byte rc[];

        byte t1, t2, t3, t4, t5, t6;
        byte tt1, tt2, tt3, tt4, tt5, tt6;

        byte b1 = (byte) 0x3F;
        byte b2 = (byte) 0x30;
        byte b3 = (byte) 0x0F;
        byte b4 = (byte) 0x3C;
        byte b5 = (byte) 0x03;
        byte b6 = (byte) 0x3F;

        rc = new byte[srcLength - 1];

        switch (srcLength) {
            case 2:
                t1 = (byte) (sc[0] & b1);
                t2 = (byte) (sc[1] & b2);
                tt1 = (byte) (t1 << 2);
                tt2 = bitMove(t2, 4);
                rc[0] = (byte) (tt1 | tt2);
                break;

            case 3:
                t1 = (byte) (sc[0] & b1);
                t2 = (byte) (sc[1] & b2);
                t3 = (byte) (sc[1] & b3);
                t4 = (byte) (sc[2] & b4);
                tt1 = (byte) (t1 << 2);
                tt2 = bitMove(t2, 4);
                tt3 = (byte) (t3 << 4);
                tt4 = bitMove(t4, 2);
                rc[0] = (byte) (tt1 | tt2);
                rc[1] = (byte) (tt3 | tt4);
                break;

            case 4:
                t1 = (byte) (sc[0] & b1);
                t2 = (byte) (sc[1] & b2);
                t3 = (byte) (sc[1] & b3);
                t4 = (byte) (sc[2] & b4);
                t5 = (byte) (sc[2] & b5);
                t6 = (byte) (sc[3] & b6);
                tt1 = (byte) (t1 << 2);
                tt2 = bitMove(t2, 4);
                tt3 = (byte) (t3 << 4);
                tt4 = bitMove(t4, 2);
                tt5 = (byte) (t5 << 6);
                tt6 = (byte) (t6);
                rc[0] = (byte) (tt1 | tt2);
                rc[1] = (byte) (tt3 | tt4);
                rc[2] = (byte) (tt5 | tt6);
                break;
        }
        return rc;
    }

    public static byte bitMove(byte sbyte, int count) {
        BitSet abit = new BitSet(8);

        byte rbyte = (byte) 0x00;
        byte bits[];

        bits = new byte[8];

        bits[0] = (byte) 0x80;
        bits[1] = (byte) 0x40;
        bits[2] = (byte) 0x20;
        bits[3] = (byte) 0x10;
        bits[4] = (byte) 0x08;
        bits[5] = (byte) 0x04;
        bits[6] = (byte) 0x02;
        bits[7] = (byte) 0x01;

        for (int i = 0; i < 8; i++) {
            if (i < count) {
                abit.clear(i);
                continue;
            }

            if (((byte) (sbyte & bits[i - count])) == bits[i - count]) {
                abit.set(i);
            } else {
                abit.clear(i);
            }
        }
        for (int i = 0; i < 8; i++) {
            if (abit.get(i))
                rbyte = (byte) (rbyte | bits[i]);
        }
        return rbyte;
    }

    /**
     * Base64 encoding 编码
     */
    public static String base64Encode(byte[] srcBytes) {
        int count;
        int numb;
        byte tBytes[];
        byte tmpBytes[];
        // String tChars;
        String rtnStr;

        count = 0;

        rtnStr = "";

        while (true) {
            if (srcBytes.length < (count + 3)) {
                numb = srcBytes.length % 3;
            } else {
                numb = 3;
            }

            tBytes = new byte[numb];

            for (int i = 0; i < numb; i++) {
                tBytes[i] = srcBytes[count + i];
            }

            // tChars = new String(tBytes);

            // 得出base64 bit的演算结果

            tmpBytes = baseEncoding(tBytes); // .getBytes();

            for (int i = 0; i < tmpBytes.length; i++) {
                rtnStr += base64Tab.charAt(tmpBytes[i]);
            }

            count += 3;

            // 用等号填充最后的空格
            if (numb < 3) {
                for (int i = 0; i < (3 - numb); i++) {
                    rtnStr += "=";
                }
                break;
            }
            if (count == srcBytes.length) {
                break;
            }
        }

        return rtnStr;
    }

    /**
     * Base64 decoding 解码
     */
    public static byte[] base64Decode(String encStr) {
        int cnt = 0;
        int idx;
        int numb;
        int count;
        byte encBytes[];
        byte fBytes[];
        byte bufBytes[] = new byte[10240];
        byte decBytes[];

        count = 0;
        encBytes = encStr.getBytes();

        while (true) {
            if (encBytes.length < (count + 4)) {
                numb = encBytes.length % 4;
            } else {
                numb = 4;
            }

            fBytes = new byte[numb];

            for (int i = 0; i < numb; i++) {
                idx = getTabIndex(encBytes[count + i]);
                if (idx == -1) {
                    return null;
                }
                if (idx == 64) {
                    byte[] tmpBytes = new byte[4];

                    tmpBytes = fBytes;

                    fBytes = new byte[i];

                    for (int j = 0; j < i; j++) {
                        fBytes[j] = tmpBytes[j];
                    }
                    break;
                }
                fBytes[i] = (byte) idx;
            }

            decBytes = baseDecoding(fBytes);
            if (decBytes == null)
                return null;
            for (int i = 0; i < decBytes.length; i++) {
                bufBytes[cnt++] = decBytes[i];
            }

            count += 4;

            if (numb < 4 || count == encBytes.length) {
                break;
            }
        }

        byte rtnBytes[] = new byte[cnt];

        System.arraycopy(bufBytes, 0, rtnBytes, 0, cnt);

        return rtnBytes;
    }

    private static int getTabIndex(byte b) {
        if (b >= 65 && b <= 90) {
            return (b - 65);
        }

        if (b >= 97 && b <= 122) {
            return (b - 97 + 26);
        }

        if (b >= 48 && b <= 57) {
            return (b - 48 + 52);

        }

        switch (b) {
            case '+':
                return 62;

            case '/':
                return 63;

            case '=':
                return 64;
        }

        return -1;
    }

    // 加密字符串
    public static byte[] encryptMode(byte[] keybyte, byte[] src) {
        try {
            // 生成密钥
            SecretKey deskey = new SecretKeySpec(keybyte, Algorithm); // 加密
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.ENCRYPT_MODE, deskey);
            return c1.doFinal(src);
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }

    // 解密字符串
    public static byte[] decryptMode(byte[] keybyte, byte[] src) {
        try { // 生成密钥
            SecretKey deskey = new SecretKeySpec(keybyte, Algorithm); // 解密
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.DECRYPT_MODE, deskey);
            return c1.doFinal(src);
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }
}
