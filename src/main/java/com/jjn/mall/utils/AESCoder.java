package com.jjn.mall.utils;

import java.net.URLEncoder;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * AES对称加密算法,组件类
 * 
 */
public abstract class AESCoder {
	// 密钥算法
	public static final String ALGORITHM = "AES";
	// 密钥长度(java默认只能处理128位以内的长度,如果需要处理大于128位可以使用JCE解除密钥长度限制)
	public static final int KEY_SIZE = 128;

	/** 默认编码格式 */
	public static final String INPUT_CHARSET = "UTF-8";

	// 256位算法使用
	public static final String CIPHER_ALGORITHM_CBC = "AES/CBC/PKCS5Padding";
	public static final String CIPHER_ALGORITHM_ECB = "AES/ECB/PKCS5Padding";

    public static byte[] ivBytes = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };

    /**
     * AES256加密
     *
     * @param str
     * @param key
     * @return
     * @throws Exception
     */
    public static String encrypt256(String str, String key) throws Exception {

        byte[] textBytes = str.getBytes(INPUT_CHARSET);
        AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
        SecretKeySpec newKey = new SecretKeySpec(key.getBytes(INPUT_CHARSET), ALGORITHM);
        Cipher cipher = null;
        cipher = Cipher.getInstance(CIPHER_ALGORITHM_CBC);
        cipher.init(Cipher.ENCRYPT_MODE, newKey, ivSpec);
        return Base64.encodeBase64String(cipher.doFinal(textBytes));
    }

    /**
     * AES256解密
     *
     * @param str
     * @param key
     * @return
     * @throws Exception
     */
    public static String decrypt256(String str, String key)	throws Exception {

        byte[] textBytes = Base64.decodeBase64(str);
        AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
        SecretKeySpec newKey = new SecretKeySpec(key.getBytes(INPUT_CHARSET), ALGORITHM);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_CBC);
        cipher.init(Cipher.DECRYPT_MODE, newKey, ivSpec);
        return new String(cipher.doFinal(textBytes), INPUT_CHARSET);
    }

    //生成随机34位数字和字母  
    public static String getStringRandom(int length) {  
          
        String val = "";  
        Random random = new Random();  
          
        //参数length，表示生成几位随机数  
        for(int i = 0; i < length; i++) {  
              
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";  
            //输出字母还是数字  
            if( "char".equalsIgnoreCase(charOrNum) ) {  
                //输出是大写字母还是小写字母  
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;  
                val += (char)(random.nextInt(26) + temp);  
            } else if( "num".equalsIgnoreCase(charOrNum) ) {  
                val += String.valueOf(random.nextInt(10));  
            }  
        }  
        return val;  
    }  
    
	/**
	 * 转换密钥
	 * 
	 * @param key
	 *            二进制密钥
	 * @return Key 密钥
	 * @throws Exception
	 */
	private static Key toKey(byte[] key) throws Exception {
		// 实例化AES密钥材料
		SecretKey secretKey = new SecretKeySpec(key, ALGORITHM);
		return secretKey;
	}

	/**
	 * 解密
	 * 
	 * @param data
	 *            待解密数据
	 * @param key
	 *            密钥
	 * @return byte[] 解密数据
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
		// 还原密钥
		Key k = toKey(key);
		// 实例化
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		// 初始化,设置为解密模式
		cipher.init(Cipher.DECRYPT_MODE, k);
		// 执行操作
		return cipher.doFinal(data);
	}

	/**
	 * 解密
	 * 
	 * @param data
	 *            待解密数据
	 * @param key
	 *            密钥
	 * @return byte[] 解密数据
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data, String key) throws Exception {
		return decrypt(data, getKey(key));
	}
	
	/**
	 * 解密
	 * 
	 * @param data
	 *            待解密BASE64编码数据
	 * @param key
	 *            密钥
	 * @return byte[] 解密数据
	 * @throws Exception
	 */
	public static byte[] decrypt(String base64data, String key) throws Exception {
		return decrypt(Base64.decodeBase64(base64data), getKey(key));
	}

	/**
	 * 加密
	 * 
	 * @param data
	 *            待加密数据
	 * @param key
	 *            密钥
	 * @return byte[] 加密数据
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data, byte[] key) throws Exception {
		// 还原密钥
		Key k = toKey(key);
		// 实例化
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		// 初始化,设置为加密模式
		cipher.init(Cipher.ENCRYPT_MODE, k);
		// 执行操作
		return cipher.doFinal(data);
	}

	/**
	 * 加密
	 * 
	 * @param data
	 *            待加密数据
	 * @param key
	 *            密钥
	 * @return byte[] 加密数据
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data, String key) throws Exception {
		return encrypt(data, getKey(key));
	}
	
	/**
	 * 加密
	 * 
	 * @param data
	 *            待加密数据
	 * @param key
	 *            密钥
	 * @return string 加密数据
	 * @throws Exception
	 */
	public static String encrypt(String data, String key) throws Exception {
		return Base64.encodeBase64String(encrypt(data.getBytes("UTF-8"), getKey(key)));
	}


	/**
	 * 生成密钥
	 * 
	 * @return byte[] 二进制密钥
	 * @throws Exception
	 */
	public static byte[] initKey() throws Exception {
		// 实例化
		KeyGenerator kg = KeyGenerator.getInstance(ALGORITHM);
		// 初始化密钥长度
		kg.init(KEY_SIZE);
		// 生成秘密密钥
		SecretKey secretKey = kg.generateKey();
		// 获得密钥的二进制编码形式
		return secretKey.getEncoded();
	}

	/**
	 * 初始化密钥
	 * 
	 * @return String Base64编码密钥
	 * @throws Exception
	 */
	public static String initKeyString() throws Exception {
		return Base64.encodeBase64String(initKey());
	}
	
	public static String initKeyString16() throws Exception {
		return new String(Hex.encodeHex(initKey()));
	}

	/**
	 * 获取密钥
	 * 
	 * @param key
	 * @return byte[] 密钥
	 * @throws Exception
	 */
	public static byte[] getKey(String key) throws Exception {
		return Base64.decodeBase64(key);
	}

	/**
	 * 摘要处理
	 * 
	 * @param data
	 *            待摘要数据
	 * @return String 摘要字符串
	 */
	public static String shaHex(byte[] data) {
		return DigestUtils.md5Hex(data);
	}

	/**
	 * 验证
	 * 
	 * @param data
	 *            待摘要数据
	 * @param messageDigest
	 *            摘要字符串
	 * @return 验证结果
	 */
	public static boolean validate(byte[] data, String messageDigest) {
		return messageDigest.equals(shaHex(data));
	}
	
	 public static void main(String args[])    
     {    
         try {
        	 System.out.println(initKeyString());
        	 System.out.println(URLEncoder.encode(URLEncoder.encode("+","UTF-8"),"UTF-8"));
//        	 System.out.println(initKeyString());
//        	 System.out.println(System.currentTimeMillis());
//        	 for (int i = 0; i < 10; i++){
//        		 System.out.println("time:" + StringUtil.generateRandomCharAndNumber(8));
//        	 }
//        	 String strDate =String.format("%tj",new Date()); 
//        	 System.out.println(strDate);
//            //初始化密钥    
//            String secretKey=AESCoder.initKeyString();   
//            System.out.println("key:" + secretKey);
//            String secretKey16 = initKeyString16();
//            System.out.println("miyao:"+secretKey16);    
//            String s="加骄傲骄傲骄傲傲";    
//            //加密数据    
//            byte[] encryptData=AESCoder.encrypt(s.getBytes(), secretKey);   
//            System.out.println("jiami:" + new String(Hex.encodeHex(encryptData)) + "  length:" + Base64.encodeBase64String(encryptData).length());
//            //解密数据    
//            byte[] data=AESCoder.decrypt(encryptData, secretKey);    
//            //比较    
//            System.out.println("jiemi:" + new String(data)  + "  length:" + new String(data).length()); 
//        	 String s = "testtest$S00001$1.0$" + System.currentTimeMillis();
//        	 String s1 = "S00002$12345678901234567890123456789014$1100000000010010000000300004C7E7$0$" + System.currentTimeMillis();
//        	 JSONObject jb = new JSONObject();
//        	 jb.put("TOKENID", "9fQ7QFXF");
//        	 jb.put("TRANSACTIONID", "S0000100000000000000000000000001");
//        	 jb.put("CLIENTID", "3223000000001"); 
//        	 jb.put("PRODUCTID", "32000004"); 
//        	 jb.put("PROGRAMNAME", "黄飞鸿");
//        	 jb.put("PROGRAMID", "PR000000000000000000000000000001");
//        	 jb.put("TIMESTAMP",System.currentTimeMillis());
////        	 jb.put("COUPON","1");
//        	 jb.put("COLUMNID", "CO00000000000000000001"); 
//        	 jb.put("COLUMNNAME", "蓝光专区");
        	 
        	 //--------------------产品订购
//        	 jb.put("MAC", "00300004C7E5");
//        	 jb.put("PRODUCTID", "32000004");
//        	 jb.put("PACKAGEID", ""); 
//        	 jb.put("TIMESTAMP",System.currentTimeMillis());
        	//--------------------产品订购
        	 //jb.put("TOKENID", "1x3ZS1S5");
//        	 jb.put("TOKENID", "9fQ7QFXF");
//        	 jb.put("SN", "22345678901234567890123456789013");
//        	 jb.put("STBID", "2100000000010010000000300004C7E5"); 
//        	 jb.put("SOURCE", 1); 
//        	 jb.put("TYPE", 0); 
////        	 for (int i = 0; i <= 17; i++){
////        	 jb.put("productid" + i, "UUUUUUUUUUUUUUUU");
////        	 }
//        	 jb.put("TIMESTAMP",System.currentTimeMillis());
//            String jb = "012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012";
//        	System.out.println(jb.toString() + "length:" + jb.toString().length());
        	 JSONObject jb = new JSONObject();
        	 jb.put("SPID", "320002");
        	 jb.put("CPID", "320002,990001");
        	 jb.put("SIGNATURE", "B3EA6EA3A81CA37C4AECC3EC8D59F87A");
        	 jb.put("VERSION", "1.0.28");
        	 jb.put("SN", "12345678901234567890123456789013");
        	 jb.put("STBID", "00101080000378302569A089E420AEF1");
        	 //-------------------------------增值产品订购
//        	 jb.put("USERTOKEN", "O5x/FLvyXuIHfdcVSB4v0e+Cn5mU0zQq");
//        	 jb.put("CLIENTID", "3238000000054");
//        	 jb.put("PRODUCTID", "32000001");
//        	 jb.put("PACKAGEID", "");
//        	 jb.put("PROGRAMID", "00000000001");
//        	 jb.put("PROGRAMNAME", "黄飞鸿");
//        	 jb.put("COLUMNID", "C0000001");
//        	 jb.put("COLUMNNAME", "CP名称");
//        	 jb.put("TIMESTAMP", System.currentTimeMillis());
        	//-------------------------------增值产品订购
//        	 jb.put("SPID", "320013");
//        	 jb.put("CPID", "320002");
//        	 jb.put("SIGNATURE", "A8CD94660A279B648C4AE302CDD879CE");
//        	 jb.put("VERSION", "1.7.10");
//        	 jb.put("SN", "");
//        	 jb.put("STBID", "00000000000000000000123456789012");
//        	System.out.println(jb.toString());
//            byte[] encryptData=AESCoder.encrypt(jb.toString().getBytes(), "F51riKYgKK8PRrt+5IC6CQ==");   
////        	byte[] encryptData=AESCoder.encrypt("{\"SPID\":\"S00002\",\"SN\":\"1112233445566677\",\"STBID\":\"001010800003783025695CC6D0998A92\",\"SOURCE\":0,\"TYPE\":0,\"TIMESTAMP\":\"1392713537771\"}".getBytes(), "5io5yobf3j6z/VOPRerQSg==");  
//            System.out.println("jiami:" +  Base64.encodeBase64String(encryptData) + "  length:" + Base64.encodeBase64String(encryptData).length());
//            System.out.println( new String(decrypt("JoQQ+N0rA1zOk/ecU08Y+I9wz6+Kxkn6RGvl3ZYTQhlJvFHqvYDQS0lw4BR4+5y05sNFELyN1I1JW11wBse+6rQzMP6jjDVOjNwEA7/g1xU0U/c9vY9B3yWqhtfIZfCp", "ttpwVo58DwoidNrSeXDoZA=="),"UTF-8"));
         
        	// 必须为32位
//             String key = "694oGfu9ACDN4upu3pVTGQwhCRTJxsFK";
        	 String key = "GEPWm/kLMiWhFiqWXCSa/w==";//getStringRandom(32);
             System.out.println("key: " + key);

             String plainText;
             String encodeText;
//             String decodeText;
             // Encrypt
             plainText  = "imcore.net";
             plainText = "{bankCode:1,title:\\\"你好，朋友\\\",addtime:\\\"2010-05-03\\\",title:\\\"你好，朋友\\\",title:\\\"你好，朋友\\\",title:\\\"你好，朋友\\\",title:\\\"你好，朋友\\\",title:\\\"你好，朋友\\\",title:\\\"你好，朋友\\\",title:\\\"你好，朋友\\\",title:\\\"你好，朋友\\\"}";
             encodeText = encrypt256(plainText, key);
             System.out.println("AES256_Encode : "+encodeText);
         } catch (Exception e) {    
           // Auto-generated catch block    
           e.printStackTrace();    
       }    
     }

}
