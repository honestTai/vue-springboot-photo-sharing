package com.app.server.util.encryption;

import org.springframework.util.DigestUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

public class AesUtil {

    //盐，用于混交md5
    private static final String slat = "aesjavaapi";

    /**
     * 生成md5
     *
     * @param string 需要签名的字符串
     * @return
     */
    public static String getMD5(String string) {
        String base = string + "/" + slat;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    // 加密函数
    public static String encryptText(String input, String key) {
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec keySpec = new PBEKeySpec(key.toCharArray(), new byte[16], 1000, 64);
            SecretKey secretKey = new SecretKeySpec(factory.generateSecret(keySpec).getEncoded(), "DES");
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            byte[] encryptedBytes = cipher.doFinal(input.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return "加密出错";
        }
    }

    // 解密函数
    public static String decryptText(String input, String key) {
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(input);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec keySpec = new PBEKeySpec(key.toCharArray(), new byte[16], 1000, 64);
            SecretKey secretKey = new SecretKeySpec(factory.generateSecret(keySpec).getEncoded(), "DES");
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            byte[] decryptedBytes = cipher.doFinal(decodedBytes);
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            return input;
        }
    }

    private static byte[] getKeyBytes(String key) throws  InvalidKeySpecException, NoSuchAlgorithmException {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(key.toCharArray(), new byte[16], 1000, 128);
        return factory.generateSecret(spec).getEncoded();
    }


    public static byte[] generateAESKey(int keySize) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(keySize, new SecureRandom());
        SecretKey secretKey = keyGenerator.generateKey();
        return secretKey.getEncoded();
    }

    public static String readFile(String filePath) {
        StringBuilder data = new StringBuilder();
        File resource = new File(filePath);

        // 使用try-with-resources确保文件流正确关闭
        try (BufferedReader br = new BufferedReader(new FileReader(resource))) {
            String line;
            while ((line = br.readLine()) != null) {
                // 每行用分号分割（如果需要）
                data.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return data.toString();
    }


    public static void decriptfile(String file, String fileKey, String aesPath) throws IOException {
        String data = decryptText(readFile(file), fileKey);
        File fileInfo = new File(aesPath);
        // 使用 try-with-resources 来确保文件流正确关闭
        try (FileOutputStream fileOutputStream = new FileOutputStream(fileInfo)) {
            fileOutputStream.write(data.getBytes());
            fileOutputStream.flush();
        }
    }

    public static void encryptfile(String file, String s, String fileAddress) throws FileNotFoundException {
        String data = encryptText(readFile(file), s);
        File fileInfo = new File(fileAddress);
        // 使用 try-with-resources 来确保文件流正确关闭
        try (FileOutputStream fileOutputStream = new FileOutputStream(fileInfo)) {
            fileOutputStream.write(data.getBytes());
            fileOutputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*****二进制加密****/

    public static byte[] readFileByte(String filePath) throws IOException {
        File file = new File(filePath);
        FileInputStream fis = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        fis.read(data);
        fis.close();
        return data;
    }

    /**
     * 解密
     * @param file
     * @param fileKey
     * @param aesPath
     * @throws Exception
     */
    public static void decryptFile(String file, byte[] fileKey, String aesPath) throws Exception {
        byte[] decryptedData = decryptData(readFileByte(file), fileKey);
        try (FileOutputStream fileOutputStream = new FileOutputStream(aesPath)) {
            fileOutputStream.write(decryptedData);
            fileOutputStream.flush();
        }
    }

    /**
     * 加密
     * @param file
     * @param fileKey
     * @param fileAddress
     * @throws Exception
     */
    public static void encryptFile(String file, byte[] fileKey, String fileAddress) throws Exception {
        byte[] encryptedData = encryptData(readFileByte(file), fileKey);
        try (FileOutputStream fileOutputStream = new FileOutputStream(fileAddress)) {
            fileOutputStream.write(encryptedData);
            fileOutputStream.flush();
        }
    }

    public static byte[] encryptData(byte[] data, byte[] key) throws Exception {
        // 创建一个AES密钥
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");

        // 创建并初始化cipher为加密模式
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(new byte[16]));

        // 加密数据
        return cipher.doFinal(data);
    }

    public static byte[] decryptData(byte[] data, byte[] key) throws Exception {
        // 创建一个AES密钥
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");

        // 创建并初始化cipher为解密模式
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(new byte[16]));

        // 解密数据
        return cipher.doFinal(data);
    }
}
