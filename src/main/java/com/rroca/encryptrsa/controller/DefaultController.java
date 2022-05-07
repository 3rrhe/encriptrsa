package com.rroca.encryptrsa.controller;

import org.springframework.http.HttpStatus;
import com.rroca.encryptrsa.utils.ApiResponse;
import com.rroca.encryptrsa.utils.EncryptData;
import org.springframework.http.ResponseEntity;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import javax.crypto.Cipher;
import javax.validation.Valid;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
@RestController
@RequestMapping("/api")
public class DefaultController {
    @PostMapping("/encrypt")
    public ResponseEntity<ApiResponse> encryptTextAction(@Valid @RequestBody EncryptData data) {
        ApiResponse response;

        response = new ApiResponse(HttpStatus.OK.value(), "Encrypted text", encryptText(data));
        return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
    }

    @PostMapping("/decrypt")
    public ResponseEntity<ApiResponse> decryptTextAction(@Valid @RequestBody EncryptData data) {
        ApiResponse response;

        response = new ApiResponse(HttpStatus.OK.value(), "Decrypted text", decryptText(data));
        return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
    }

    /**
     * @param data the data
     * @return string
     */
    public String encryptText(EncryptData data) {
        // String seed =  data.getSeed();
        String seed = "3135";
        String text =  data.getText();
        String result = "";

        try {
            byte[] key = seed.getBytes(StandardCharsets.UTF_8);
            key = Arrays.copyOf(key, 16);
            SecretKeySpec aesKey = new SecretKeySpec(key, "RSA/ECB/PKCS8");
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
            byte[] encrypted = cipher.doFinal(text.getBytes());
            byte[] tmpByte = Base64.encodeBase64(encrypted);

            result = new String(tmpByte);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            result = text;
        }

        return result;
    }

    /**
     * @param data the data
     * @return string
     */
    public String decryptText(EncryptData data) {
        String seed =  data.getSeed();
        String encryptedText =  data.getText();
        String result = "";

        try {
            byte[] key = seed.getBytes(StandardCharsets.UTF_8);
            key = Arrays.copyOf(key, 16);
            SecretKeySpec aesKey = new SecretKeySpec(key, "RSA/ECB/PKCS8");
            Cipher cipher = Cipher.getInstance("RSA");
            byte[] tmpBClave = Base64.decodeBase64(encryptedText);
            cipher.init(Cipher.DECRYPT_MODE, aesKey);
            byte[] decrypted = cipher.doFinal(tmpBClave);

            result = new String(decrypted);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            result = encryptedText;
        }

        return result;
    }
}