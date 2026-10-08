package com.app.server.util.encryption;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
public class RsaUtil {

    private static final String PUBLIC = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAl9hEAU06rSet/8asMSqcvwsiiWmXf2qO3vCwEcljZ2A/42xvrcSPcVvd1ocFm9i/XD7Bt6YcTZwaoI5SYnYHXmRJGpZIP6K8r7g8FGmg90CcIc64qmoIFOZ177tSzbFcGr5gkBVxjUFnyLhC1htzsZ+BhfMRVdkxrY5r2xu+N10UQ9tB9bXur9OVWOpXS/C5zgbZm+I8lgfAN2qoS+hZX/GKEAdUW6graWJlF+uHc2eEtwnIpWXlcy1LzLyBjAX6SfxE1LXQQLZ0204UUyN07s4fd+LaKTSvn/iPr1LYby+SvBZP5bDm6VezM/tIS5ZJ7n0b1ILevuOHUmTwcowEBwIDAQAB";
    private static final String PRIVATE="MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCX2EQBTTqtJ63/xqwxKpy/CyKJaZd/ao7e8LARyWNnYD/jbG+txI9xW93WhwWb2L9cPsG3phxNnBqgjlJidgdeZEkalkg/oryvuDwUaaD3QJwhzriqaggU5nXvu1LNsVwavmCQFXGNQWfIuELWG3Oxn4GF8xFV2TGtjmvbG743XRRD20H1te6v05VY6ldL8LnOBtmb4jyWB8A3aqhL6Flf8YoQB1RbqCtpYmUX64dzZ4S3CcilZeVzLUvMvIGMBfpJ/ETUtdBAtnTbThRTI3Tuzh934topNK+f+I+vUthvL5K8Fk/lsObpV7Mz+0hLlknufRvUgt6+44dSZPByjAQHAgMBAAECggEAc+LQ2/F2A6rR5/BXgFqWMFOuMxNMVSkdCtbEeX6qh730ZHxgq0zlcM18+/omdK+yNBONQrzawvyNcdXM2Gpd61M8W7cwQqjPuogwCiAngixkPIUJeL3dlx3gCmt7oZJnyd1b0vsao4S0UYMXN1LSNFCYyIMeytctS9jzRdDhllwdKx+KEhygYpmutLLx0k+K+mFAFqJ+ak37lTetF0JeGEqD02C3tFEdizbSVgsBtiUiqWFHRrr5Y414RmH1yKdHxfBk2+zX42SxjbiI1CoSwgvbjUs8tGRYpGapkD91Ut7L5CqCIK8TyJPLEXacQNZ/+Pa+uYPD7Lunf46Ndaol8QKBgQDNCYjIFiikQXHTQJprXt9ikKQ1+IjvaKfY+4dGIJ53MYBxHwYYyC6ijTX4BlDDHBoaIWAkwhQkvGEPlcnsUx/MmROxEFkHbWaUwcYyS1N5Ug2CF8DMrxTWMdmX3Xg2VHthu+qWQmzGA8CQzmz7RXSXrI3/jD6r7Z1dOG7qul8HSQKBgQC9lhwC/PPneTzDB5e2dup8kVGIFkqu975TU1ArplRIMfhjRkMS1vRuCzauI00/Prmtw6aJAG+ThwETtvBPPgWt3yLB6//wsLrmAlrYNHrgeDXCJ5Es2UhoACYCzdIbv5FJPa4UomJ+JJ+4MQYQgis2Tmiem3IjRAHUUss+bGwgzwKBgQDBShmuNmV940xA0IhCbB++tYh4cKH2v/xrq0MtMxbwWrQw8AQ5XI3KI8Ea3ilIpbddptUSEfwXXZdKr/S5WuuBX1WW+EhVnia0WbBUPXqlxlqBp47T5sGH41qztc7buOzPh+1wVZJhYawpVtCaWDG/wqXioP176vcMqGS315Y6MQKBgF7oTo2QY+l0394iZuiJD0nc1ZvyzOBaVYURKAkx8u9RVK8d9WXxBdww8Oar9Xe7xugeEbhz52dLJlbgNdz1h5JLKfM6WZ38WxPaCfBCFWFZzE+tzxdMjtrBeEXfE1egxvKLViIuSHAtlWd34zlQcKF3DxiXaMQOv3uzFtvhGyAxAoGAKfcGJ+acIxzpdVMdcyScxBHFcxtHbxJqj1ro47tHztA8qOp3T3dYyTb7kzK2nOR9NPhyAuYLeuH7BJYu4wcXiBFMwqD3qGiYcX50ZU8IqbULtbcqDZtdIWeORAZ47A32lbR88cdImI+4Irdjg7HnKFVJs7xH8+W9kG+NOVIHQQw=";


    public static void main(String[] args) throws NoSuchAlgorithmException {
//        // 生成RSA密钥对
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(2048);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        System.out.println("公钥: " + Base64.getEncoder().encodeToString(publicKey.getEncoded()));
        System.out.println("私钥: " + Base64.getEncoder().encodeToString(privateKey.getEncoded()));
    }


    /**
     * 加密
     * @param data 原文
     * @return
     * @throws Exception
     */
    public static byte[] encryptRsa(byte[] data) throws Exception {
        PublicKey publicKey = getPublicKey(PUBLIC);
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(data);
    }

    /**
     * 解密
     * @param data 原文
     * @return
     * @throws Exception
     */
    public static byte[] decryptRsa(byte[] data) throws Exception {
        PrivateKey privateKey = getPrivateKey(PRIVATE);
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }

    private static PublicKey getPublicKey(String base64PublicKey){
        PublicKey publicKey = null;
        try{
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(base64PublicKey.getBytes()));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            publicKey = keyFactory.generatePublic(keySpec);
            return publicKey;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return publicKey;
    }

    private static PrivateKey getPrivateKey(String base64PrivateKey){
        PrivateKey privateKey = null;
        try{
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(base64PrivateKey.getBytes()));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            privateKey = keyFactory.generatePrivate(keySpec);
            return privateKey;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return privateKey;
    }
}
