package com.example.demo.common.utils.en_decode;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
public class AESUtils {

    public final static String ENCODING = "UTF-8";

    /**
     * 将二进制转换成16进制
     *
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     *
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    /**
     * 生成密钥
     * 自动生成base64 编码后的AES128位密钥
     *
     * @throws //NoSuchAlgorithmException
     * @throws //UnsupportedEncodingException
     */
    public static String getAESKey() throws Exception {
        KeyGenerator kg = KeyGenerator.getInstance("AES");
        kg.init(128);//要生成多少位，只需要修改这里即可128, 192或256
        SecretKey sk = kg.generateKey();
        byte[] b = sk.getEncoded();
        return parseByte2HexStr(b);
    }

    /**
     * AES 加密
     *
     * @param base64Key base64编码后的 AES key
     * @param text      待加密的字符串
     * @return 加密后的byte[] 数组
     * @throws Exception
     */
    public static byte[] getAESEncode(String base64Key, String text) throws Exception {
        byte[] key = parseHexStr2Byte(base64Key);
        SecretKeySpec sKeySpec = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, sKeySpec);
        byte[] bjiamihou = cipher.doFinal(text.getBytes(ENCODING));
        return bjiamihou;
    }

    /**
     * AES解密
     *
     * @param base64Key base64编码后的 AES key
     * @param text      待解密的字符串
     * @return 解密后的byte[] 数组
     * @throws Exception
     */
    public static byte[] getAESDecode(String base64Key, byte[] text) throws Exception {
        byte[] key = parseHexStr2Byte(base64Key);
        SecretKeySpec sKeySpec = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, sKeySpec);
        byte[] bjiemihou = cipher.doFinal(text);
        return bjiemihou;
    }
    
    /**
     * 16进制字符串转UTF-8
     * @param s
     * @return
     */
    public static String toStringHex1(String s) {
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
        try {
        baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(
        i * 2, i * 2 + 2), 16));
        } catch (Exception e) {
        e.printStackTrace();
        }
        }
        try {
        s = new String(baKeyword, "utf-8");// UTF-16le:Not
        } catch (Exception e1) {
        e1.printStackTrace();
        }
        return s;
    }
    
    
    /**
     * 解密
     * @param baby
     * @return
     */
	  public static String Decrypt(String baby,String key) {
    		
    		byte[] jiemi2=null;
        	String jiemi16=null;
        	String jiemi=null;
        	try {
        		//转成2进制
        		byte[]  jiami=parseHexStr2Byte(baby);
    			//解密
    			jiemi2=getAESDecode(key, jiami);
    			//转成16进制
    			jiemi16=parseByte2HexStr(jiemi2);
    			//转成utf-8
    			jiemi=AESUtils.toStringHex1(jiemi16);
    			
    			
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		return jiemi;
	
    }

}
