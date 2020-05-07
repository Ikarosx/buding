package cn.budingcc.auth;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.util.HashMap;

/**
 * @author Ikaros
 * @date 2020/3/26 8:53
 */
public class KeyTest {
    @Test
    public void generateKeyTest() {
        ClassPathResource classPathResource = new ClassPathResource("keystore");
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(classPathResource, "buding2019".toCharArray());
        KeyPair bdkey = keyStoreKeyFactory.getKeyPair("bdkey", "buding2019".toCharArray());
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) bdkey.getPrivate();
        HashMap<String, String> map = new HashMap<>();
        map.put("name", "Ikaros");
        map.put("num", "111");
        Jwt jwt = JwtHelper.encode(JSON.toJSONString(map), new RsaSigner(rsaPrivateKey));
        String token = jwt.getEncoded();
        System.out.println(token);
        String publicKey = "-----BEGIN PUBLIC KEY-----\n" + "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAq4OL2G8HWlSH4DJYIRPK\n" + "bru1gxYrHf+A8d71szxa3bOIgzpzfjV+2t5EonIyS4oWAroscztD1NFA3y/TOmEG\n" + "d5faTq/QpMrZ79S5jy/RLNIRWaVGLXAoEWvVRuGx+uw5akjdQXsLqh6r/baIADUe\n" + "NyyzvrW5QhvE11yjej+rGCuMD95moru3vdjfsVUqkl6wfXXjJnMKdUniZv+voPE4\n" + "h2E9d2AeqWdnl8A7HY+okMzJwt5+y8tWxz8sUH8NdmsE1Xe7Pr5CMQ4TMgVUD461\n" + "0pPYLPZGigtxx7mBxFGGw6ESgxODPpyA7yTVALyIdrWqwDuAhY9ikuXzkG3JTdFI\n" + "8wIDAQAB\n" + "-----END PUBLIC KEY-----";
        Jwt jwt1 = JwtHelper.decodeAndVerify(token, new RsaVerifier(publicKey));
        System.out.println(jwt1.getClaims());
    }
}
